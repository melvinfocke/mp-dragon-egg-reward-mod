package com.melvinfocke.idiotenspielplatz.screen;

import com.melvinfocke.idiotenspielplatz.Idiotenspielplatz;
import com.melvinfocke.idiotenspielplatz.screenHandler.BackpackScreenHandler;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;

public class BackpackScreen extends HandledScreen<BackpackScreenHandler> {
    // See: https://github.com/SpyMan10/backpackmod/blob/1.17/src/main/java/net/spyman/backpackmod/client/gui/screen/BackpackScreen.java
    private static final Identifier GENERIC_54 = new Identifier("minecraft", "textures/gui/container/generic_54.png");

    public BackpackScreen(BackpackScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
        this.backgroundWidth = handler.inventory().width() * 18 + 17;
        this.backgroundHeight = (handler.inventory().height() + 4) * 18 + 41;
        this.playerInventoryTitleY = handler.inventory().height() * 18 + 20;
    }

    @Override
    protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.setShaderTexture(0, GENERIC_54);
        matrices.push();
        matrices.translate(this.x, this.y, 0.0d);
        // Background storage texture pseudo-generation

        // Upper-left corner
        this.drawTexture(matrices, 0, 0, 0, 0, 7, 17);

        // Top
        drawTexture(matrices, 7, 0, handler.inventory().width() * 18, 17, 7, 0, 1, 17, 256, 256);

        // Upper-right corner
        this.drawTexture(matrices, 7 + handler.inventory().width() * 18, 0, 169, 0, 7, 17);

        // Left
        drawTexture(matrices, 0, 17, 7, (handler.inventory().height() + 4) * 18 + 22, 0, 17, 7, 1, 256, 256);

        // Lower-left corner
        this.drawTexture(matrices, 0, (handler.inventory().height() + 4) * 18 + 34, 0, 215, 7, 7);

        // Lower
        drawTexture(matrices, 7, (handler.inventory().height() + 4) * 18 + 34, handler.inventory().width() * 18, 7, 7, 215, 1, 7, 256, 256);

        // Lower-right corner
        this.drawTexture(matrices, (handler.inventory().width() * 18 + 7), (handler.inventory().height() + 4) * 18 + 34, 169, 215, 7, 7);

        // Right
        drawTexture(matrices, (handler.inventory().width() * 18 + 7), 17, 7, (handler.inventory().height() + 4) * 18 + 17, 169, 17, 7, 1, 256, 256);

        // Background fill
        String backgroundString =  new TranslatableText("container." + Idiotenspielplatz.MOD_ID + ".backpackBackgroundColor").getString();
        int backgroundColor = (int) Long.parseLong(backgroundString, 16);
        fill(matrices, 7, 17, this.backgroundWidth - 10, this.backgroundHeight - 7, backgroundColor);

        this.handler.slots.forEach(s -> this.drawTexture(matrices, s.x - 1, s.y - 1, 7, 17, 18, 18));

        matrices.pop();

    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        renderBackground(matrices);
        super.render(matrices, mouseX, mouseY, delta);
        drawMouseoverTooltip(matrices, mouseX, mouseY);
    }

    /*
    @Override
    protected void init() {
        super.init();
        // Center the title
        //titleX = (backgroundWidth - textRenderer.getWidth(title)) / 2;
        titleX = 8;
        titleY = -22;
        playerInventoryTitleX = 8;
        playerInventoryTitleY = 101;
        System.out.printf("playerInventoryTitleX: %s; playerInventoryTitleY: %s%n", playerInventoryTitleX, playerInventoryTitleY);
    }
    */

}
