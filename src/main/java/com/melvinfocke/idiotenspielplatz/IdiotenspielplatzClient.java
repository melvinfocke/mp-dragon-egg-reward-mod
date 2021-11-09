package com.melvinfocke.idiotenspielplatz;

import com.melvinfocke.idiotenspielplatz.screen.BackpackScreen;
import com.melvinfocke.idiotenspielplatz.screen.BigChestScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;

@Environment(EnvType.CLIENT)
public class IdiotenspielplatzClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ScreenRegistry.register(Idiotenspielplatz.BIG_CHEST_SCREEN_HANDLER, BigChestScreen::new);
        ScreenRegistry.register(Idiotenspielplatz.BACKPACK_SCREEN_HANDLER, BackpackScreen::new);
    }
}
