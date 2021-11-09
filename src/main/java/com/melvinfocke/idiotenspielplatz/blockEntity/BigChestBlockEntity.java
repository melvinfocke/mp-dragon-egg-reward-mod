package com.melvinfocke.idiotenspielplatz.blockEntity;

import com.melvinfocke.idiotenspielplatz.Idiotenspielplatz;
import com.melvinfocke.idiotenspielplatz.inventories.BigChestInventory;
import com.melvinfocke.idiotenspielplatz.screenHandler.BigChestScreenHandler;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;

public class BigChestBlockEntity extends BlockEntity implements NamedScreenHandlerFactory, BigChestInventory {
    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(54, ItemStack.EMPTY);

    public BigChestBlockEntity(BlockPos pos, BlockState state) {
        super(Idiotenspielplatz.BIG_CHEST_BLOCK_ENTITY, pos, state);
    }

    // From the BigChestInventory interface

    @Override
    public DefaultedList<ItemStack> getItems() {
        return inventory;
    }

    // These methods are from the NamedScreenHandlerFactory Interface
    // createMenu creates the ScreenHandler itself
    // get DisplayName will provide its name which is normally shown at the top

    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        // We provide *this* to the screenHandler as our class implements Inventory
        // Only the server has the inventory at the start, this will be synced ti the client in the ScreenHandler
        return new BigChestScreenHandler(syncId, playerInventory, this);
    }

    @Override
    public Text getDisplayName() {
        return new TranslatableText(getCachedState().getBlock().getTranslationKey());
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        Inventories.readNbt(nbt, this.inventory);
    }

    @Override
    public NbtCompound writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        Inventories.writeNbt(nbt, this.inventory);
        return nbt;
    }

    @Override
    public ItemStack getStack(int slot) {
        return inventory.get(slot);
    }
}
