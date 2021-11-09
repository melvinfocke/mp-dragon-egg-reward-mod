package com.melvinfocke.idiotenspielplatz.screenHandler;

import com.melvinfocke.idiotenspielplatz.Idiotenspielplatz;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;

public class BigChestScreenHandler extends ScreenHandler {
    private final Inventory inventory;

    /*
     * This constructor gets called on the client when the server wants it to open the screenHandler,
     * this client will call the other constructor with an empty inventory and the screenHandler will automatically
     * sync this empty inventory with the inventory on the server.
     */
    public BigChestScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, new SimpleInventory(54));
    }

    /*
     * This constructor gets called from the BlockEntity on the server without calling the other constructor first, the server knows the inventory of the container
     * and can therefore directly provide it as an argument. This inventory will then be synced to the client.
     */
    public BigChestScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory) {
        super(Idiotenspielplatz.BIG_CHEST_SCREEN_HANDLER, syncId);
        checkSize(inventory, 54);
        this.inventory = inventory;
        // some inventories do custom logic when a player opens it.
        inventory.onOpen(playerInventory.player);

        /*
         * This will replace the slit in the correct locations for a 3x3 grid. The slots exist on with server and client!
         * This will not render the background of the slots however, this is the Screen job.
         */
        int m;
        int l;
        // our inventory
        for (m = 0; m < 6; ++m) {
            for (l = 0; l < 9; ++l) {
                this.addSlot(new Slot(inventory, l + m * 9, 8 + l * 18, -10 + m * 18));
            }
        }

        // the player inventory
        for (m = 0; m < 3; ++m) {
            for (l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, 9 + l + m * 9, 8 + l * 18, 112 + m * 18));
            }
        }

        // the player hotbar
        for (m = 0; m < 9; ++m) {
            this.addSlot(new Slot(playerInventory, m, 8 + m * 18, 170));
        }
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return this.inventory.canPlayerUse(player);
    }

    // Shift + Player Inv Slot
    @Override
    public ItemStack transferSlot(PlayerEntity player, int invSlot) {
        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(invSlot);
        if (slot.hasStack()) {
            ItemStack originalStack = slot.getStack();
            newStack = originalStack.copy();
            if (invSlot < this.inventory.size()) {
                if (!this.insertItem(originalStack, this.inventory.size(), this.slots.size(), true)) return ItemStack.EMPTY;
            } else if (!this.insertItem(originalStack, 0, this.inventory.size(), false)) {
                return ItemStack.EMPTY;
            }

            if (originalStack.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }
        }
        return newStack;
    }
}
