package com.melvinfocke.idiotenspielplatz.items;

import com.melvinfocke.idiotenspielplatz.Idiotenspielplatz;
import com.melvinfocke.idiotenspielplatz.inventories.BackpackInventory;
import com.melvinfocke.idiotenspielplatz.screenHandler.BackpackScreenHandler;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class BackpackItem extends Item {
    private final int width;
    private final int height;

    public BackpackItem(int width, int height, Settings settings) {
        super(settings.group(Idiotenspielplatz.ITEM_GROUP).maxCount(1));
        this.width = width;
        this.height = height;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        final ItemStack stack = user.getStackInHand(hand);
        if (!user.isSneaking()) {
            if (!world.isClient()) {
                openScreen(user, stack);
            } else {
                user.playSound(SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 1.0f, 1.0f);
            }
            return TypedActionResult.pass(stack);
        } else if (world.isClient()) {
            //openRenameScreen(hand, stack.getName());
            return TypedActionResult.pass(stack);
        }
        return super.use(world, user, hand);
    }

    public static void openScreen(PlayerEntity user, ItemStack stack) {
        final BackpackItem backpackItem = (BackpackItem) stack.getItem();

        user.openHandledScreen(new ExtendedScreenHandlerFactory() {
            @Override
            public Text getDisplayName() {
                return stack.getName();
            }

            @Override
            public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
                return new BackpackScreenHandler(inv, syncId, new BackpackInventory(backpackItem.width, backpackItem.height, stack));
            }

            @Override
            public void writeScreenOpeningData(ServerPlayerEntity serverPlayerEntity, PacketByteBuf buf) {
                buf.writeInt(backpackItem.width);
                buf.writeInt(backpackItem.height);
            }
        });
    }
}
