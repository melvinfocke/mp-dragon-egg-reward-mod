package com.melvinfocke.idiotenspielplatz.inventories;

import com.melvinfocke.idiotenspielplatz.items.BackpackItem;
import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;

public class BackpackSlot extends Slot {
    public BackpackSlot(Inventory inventory, int index, int x, int y) {
        super(inventory, index, x, y);
    }

    @Override
    public boolean canInsert(ItemStack stack) {
        if (stack.getItem() instanceof BackpackItem) return false;

        if (stack.getItem() instanceof final BlockItem blockItem) {
            return !(blockItem.getBlock() instanceof ShulkerBoxBlock);
        }
        return true;
    }
}
