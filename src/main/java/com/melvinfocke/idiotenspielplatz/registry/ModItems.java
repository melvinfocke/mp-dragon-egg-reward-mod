package com.melvinfocke.idiotenspielplatz.registry;

import com.melvinfocke.idiotenspielplatz.Idiotenspielplatz;
import com.melvinfocke.idiotenspielplatz.items.BackpackItem;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.registry.Registry;

public class ModItems {
    // Items
    public static final Item RUBY = new Item(new Item.Settings().group(Idiotenspielplatz.ITEM_GROUP));
    public static final Item BACKPACK = new BackpackItem(9, 6, new Item.Settings().group(Idiotenspielplatz.ITEM_GROUP).rarity(Rarity.COMMON));

    // Block Items
    public static final BlockItem RUBY_BLOCK = new BlockItem(ModBlocks.RUBY_BLOCK, new Item.Settings().group(Idiotenspielplatz.ITEM_GROUP));
    //public static final BlockItem BIG_CHEST = new BlockItem(ModBlocks.BIG_CHEST, new Item.Settings().group(FirstFabricMod.ITEM_GROUP));


    public static void registerItems() {
        Registry.register(Registry.ITEM, new Identifier(Idiotenspielplatz.MOD_ID, "ruby"), RUBY);
        Registry.register(Registry.ITEM, new Identifier(Idiotenspielplatz.MOD_ID, "backpack"), BACKPACK);
        Registry.register(Registry.ITEM, new Identifier(Idiotenspielplatz.MOD_ID, "ruby_block"), RUBY_BLOCK);
        //Registry.register(Registry.ITEM, new Identifier(FirstFabricMod.MOD_ID, "big_chest"), BIG_CHEST);
    }
}
