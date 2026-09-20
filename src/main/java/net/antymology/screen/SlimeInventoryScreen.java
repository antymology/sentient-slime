package net.antymology.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.antymology.SentientSlime;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class SlimeInventoryScreen extends HandledScreen<SlimeInventoryScreenHandler> {

    private static final Identifier TEXTURE =
        new Identifier(SentientSlime.MOD_ID, "textures/gui/slime_inventory.png");

    private static final Identifier OVERLAY_TEXTURE =
        new Identifier(SentientSlime.MOD_ID, "textures/gui/slime_inventory_overlay.png");

    public SlimeInventoryScreen(
        SlimeInventoryScreenHandler handler,
        PlayerInventory inventory,
        Text title
    ) {
        super(handler, inventory, title);
        backgroundWidth = 176;
        backgroundHeight = 166;
    }

    @Override
    protected void drawBackground(
        DrawContext context,
        float delta,
        int mouseX,
        int mouseY
    ) {
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;

        context.drawTexture(
            TEXTURE,
            x,
            y,
            0,
            0,
            backgroundWidth,
            backgroundHeight,
            176,
            166
        );
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);

        int x = this.x;
        int y = this.y;

        RenderSystem.disableDepthTest();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();

        context.drawTexture(OVERLAY_TEXTURE, x, y, 0, 0, backgroundWidth, backgroundHeight, 176, 166);

        RenderSystem.disableBlend();
        RenderSystem.enableDepthTest();
    }
}