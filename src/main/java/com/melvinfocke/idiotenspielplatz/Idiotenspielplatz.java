package com.melvinfocke.idiotenspielplatz;

import com.melvinfocke.idiotenspielplatz.blockEntity.BigChestBlockEntity;
import com.melvinfocke.idiotenspielplatz.blocks.BigChestBlock;
import com.melvinfocke.idiotenspielplatz.inventories.BackpackInventory;
import com.melvinfocke.idiotenspielplatz.registry.ModBlockEntities;
import com.melvinfocke.idiotenspielplatz.registry.ModBlocks;
import com.melvinfocke.idiotenspielplatz.registry.ModItems;
import com.melvinfocke.idiotenspielplatz.screenHandler.BackpackScreenHandler;
import com.melvinfocke.idiotenspielplatz.screenHandler.BigChestScreenHandler;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.*;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class Idiotenspielplatz implements ModInitializer {
    public static final Block BIG_CHEST_BLOCK;
    public static final BlockItem BIG_CHEST_BLOCK_ITEM;
    public static final BlockEntityType<BigChestBlockEntity> BIG_CHEST_BLOCK_ENTITY;

    public static final ScreenHandlerType<BigChestScreenHandler> BIG_CHEST_SCREEN_HANDLER;
    public static final ScreenHandlerType<BackpackScreenHandler> BACKPACK_SCREEN_HANDLER;

    public static final String MOD_ID = "idiotenspielplatz";
    public static final Identifier BIG_CHEST = new Identifier(MOD_ID, "big_chest");
    public static final Identifier BACKPACK = new Identifier(MOD_ID, "backpack");

    public static final ItemGroup ITEM_GROUP = FabricItemGroupBuilder.build(
            new Identifier(MOD_ID, "general"),
            () -> new ItemStack(ModItems.RUBY));

    public static final ItemGroup OTHER_GROUP = FabricItemGroupBuilder.create(
                    new Identifier(MOD_ID, "other"))
            .icon(() -> new ItemStack(Blocks.ENCHANTING_TABLE))
            .appendItems(stacks -> {
                stacks.add(new ItemStack(ModBlocks.RUBY_BLOCK));
                stacks.add(new ItemStack(Items.APPLE));
                stacks.add(new ItemStack(ModItems.RUBY));
                stacks.add(new ItemStack(Items.GLOWSTONE_DUST));
            })
            .build();


    static {
        BIG_CHEST_BLOCK = Registry.register(Registry.BLOCK, BIG_CHEST, new BigChestBlock(FabricBlockSettings.copyOf(Blocks.CHEST)
                .breakByTool(FabricToolTags.AXES, 0)
                .strength(2.5f, 12.5f)
                .sounds(BlockSoundGroup.WOOD)));

        BIG_CHEST_BLOCK_ITEM = Registry.register(Registry.ITEM, BIG_CHEST, new BlockItem(BIG_CHEST_BLOCK, new Item.Settings().group(ITEM_GROUP)));

        BIG_CHEST_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, BIG_CHEST, FabricBlockEntityTypeBuilder.create(BigChestBlockEntity::new, BIG_CHEST_BLOCK).build(null));
        /*
         * We use registerSimple here because our entity is not an ExtendedScreenHandlerFactory
         * but a NamedScreenHandlerFactory.
         */
        BIG_CHEST_SCREEN_HANDLER = ScreenHandlerRegistry.registerSimple(BIG_CHEST, BigChestScreenHandler::new);
        BACKPACK_SCREEN_HANDLER = ScreenHandlerRegistry.registerExtended(BACKPACK, (i, pinv, buf ) -> new BackpackScreenHandler(pinv, i, new BackpackInventory(buf.readInt(), buf.readInt(), null)));
    }

    @Override
    public void onInitialize() {
        ModItems.registerItems();
        ModBlocks.registerBlocks();
        ModBlockEntities.registerBlockEntities();
    }
}
