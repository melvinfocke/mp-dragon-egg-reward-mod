package com.melvinfocke.mpdragoneggreward.mixin;

import com.google.gson.JsonParser;
import com.melvinfocke.mpdragoneggreward.RewardType;
import net.minecraft.block.Blocks;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.boss.dragon.EnderDragonFight;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Heightmap;
import net.minecraft.world.gen.feature.EndPortalFeature;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import com.google.gson.JsonArray;
import com.google.gson.JsonPrimitive;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

@SuppressWarnings("ALL")
@Mixin(EnderDragonFight.class)
public abstract class EnderDragonFightMixin {
    private final boolean PLACE_DRAGON_EGGS = true;

    @Shadow @Final private ServerWorld world;
    @Shadow private boolean previouslyKilled;

    @Inject(at = @At("HEAD"), method = "dragonKilled")
    private void dragonKilled(EnderDragonEntity dragon, CallbackInfo info) {
        this.previouslyKilled = true;
        world.setBlockState(world.getTopPosition(Heightmap.Type.MOTION_BLOCKING, EndPortalFeature.ORIGIN), Blocks.AIR.getDefaultState());

        /* JsonParser is deprecated */
        JsonParser jsonParser = new JsonParser();
        try (FileReader reader = new FileReader("previous-dragon-killers.json")) {
            JsonArray previousDragonKillers = jsonParser.parse(reader).getAsJsonArray();
            rewardDragonKillers(previousDragonKillers);

        } catch (IOException | ClassCastException e) {
            JsonArray previousDragonKillers = new JsonArray();
            rewardDragonKillers(previousDragonKillers);
        }
    }

    private void rewardDragonKillers(JsonArray previousDragonKillers) {
        RewardType rewardType = getRewardType();

        world.getPlayers().forEach(playerEntity -> {
            String uuidAsString = playerEntity.getUuidAsString();
            boolean isPreviousDragonKiller = previousDragonKillers.contains(new JsonPrimitive(uuidAsString));

            if (!isPreviousDragonKiller) {
                switch (rewardType) {
                    case PLACE_DRAGON_EGG -> {
                        BlockPos blockPos = world.getTopPosition(Heightmap.Type.MOTION_BLOCKING, EndPortalFeature.ORIGIN);
                        world.setBlockState(blockPos, Blocks.DRAGON_EGG.getDefaultState());
                    }
                    case GIVE_DRAGON_EGG -> {
                        ItemEntity itemEntity = new ItemEntity(world, playerEntity.getX(), playerEntity.getY(), playerEntity.getZ(), new ItemStack(Items.DRAGON_EGG));
                        world.spawnEntity(itemEntity);
                    }
                    case NOTHING -> {}
                }
                previousDragonKillers.add(new JsonPrimitive(playerEntity.getUuidAsString()));
            }

        });

        try (FileWriter file = new FileWriter("previous-dragon-killers.json")) {
            file.write(previousDragonKillers.toString());
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private RewardType getRewardType() {
        JsonParser jsonParser = new JsonParser();
        try (FileReader reader = new FileReader("dragon-reward-type.json")) {
            String rewardTypeAsString = jsonParser.parse(reader).getAsJsonPrimitive().getAsString();
            return RewardType.valueOf(rewardTypeAsString);

        } catch (IOException | ClassCastException e) {
            return RewardType.PLACE_DRAGON_EGG;
        }
    }
}
