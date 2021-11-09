package com.melvinfocke.idiotenspielplatz.registry;

import com.melvinfocke.idiotenspielplatz.Idiotenspielplatz;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModBlocks {
    public static final Block RUBY_BLOCK = new Block(FabricBlockSettings
            .of(Material.METAL)
            .breakByTool(FabricToolTags.PICKAXES, 2)
            .requiresTool()
            .strength(5.0f, 30.0f)
            .sounds(BlockSoundGroup.METAL)
            .luminance(2));

    /*
    public static final Block BIG_CHEST = new Block(FabricBlockSettings
            .of(Material.WOOD)
            .breakByTool(FabricToolTags.AXES, 0)
            .requiresTool()
            .strength(3.5f, 15.0f)
            .sounds(BlockSoundGroup.WOOD));
*/
    public static void registerBlocks() {
        Registry.register(Registry.BLOCK, new Identifier(Idiotenspielplatz.MOD_ID, "ruby_block"), RUBY_BLOCK);
        //Registry.register(Registry.BLOCK, new Identifier(FirstFabricMod.MOD_ID, "big_chest"), BIG_CHEST);
    }
}
