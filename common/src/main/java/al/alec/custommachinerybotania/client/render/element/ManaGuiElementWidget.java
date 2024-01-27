package al.alec.custommachinerybotania.client.render.element;

import al.alec.custommachinerybotania.guielement.ManaGuiElement;
import al.alec.custommachinerybotania.Registration;
import com.mojang.blaze3d.systems.*;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.*;
import fr.frinn.custommachinery.impl.guielement.TexturedGuiElementWidget;
import fr.frinn.custommachinery.api.guielement.IMachineScreen;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.network.chat.Component;
import fr.frinn.custommachinery.client.ClientHandler;
import java.util.Collections;
import java.util.List;
import fr.frinn.custommachinery.common.util.Utils;
import net.minecraft.world.inventory.*;
import vazkii.botania.client.core.handler.*;

public class ManaGuiElementWidget extends TexturedGuiElementWidget<ManaGuiElement> {
  private static final int TEXTURE_SIZE = 16;

  public ManaGuiElementWidget(ManaGuiElement element, IMachineScreen screen) {
    super(element, screen, Component.literal("Mana"));
  }

  @Override
  public void renderButton(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
    super.renderButton(poseStack, mouseX, mouseY, partialTicks);
    this.getScreen().getTile().getComponentManager().getComponent(Registration.MANA_MACHINE_COMPONENT.get()).ifPresent(mana -> {
      double fillPercent = mana.getFillPercent();
      int manaHeight = (int)(fillPercent * (double)(this.height - 2));

      renderMana(poseStack, manaHeight, x + 2, y, width - 4, height - 2);
    });
    if(this.isHoveredOrFocused() && this.getElement().highlight())
      ClientHandler.renderSlotHighlight(poseStack, this.x + 2, this.y, this.width - 4, this.height - 2);
  }

  @Override
  public List<Component> getTooltips() {
    if(!this.getElement().getTooltips().isEmpty())
      return this.getElement().getTooltips();
    return this.getScreen().getTile().getComponentManager()
      .getComponent(Registration.MANA_MACHINE_COMPONENT.get())
      .map(component -> Collections.singletonList((Component)
        Component.translatable(
          "custommachinerybotania.gui.element.mana.tooltip",
          Utils.format(component.getMana()),
          Utils.format(component.getCapacity())
        )
      ))
      .orElse(Collections.emptyList());
  }

  public static void renderMana(PoseStack poseStack, int manaHeight, int x, int y, int width, int height) {
    RenderSystem.enableBlend();

    poseStack.pushPose();
    poseStack.translate(x, y, 0);

    TextureAtlasSprite sprite = MiscellaneousModels.INSTANCE.manaWater.sprite();

    drawTiledSprite(poseStack, width, height, manaHeight, sprite);

    poseStack.popPose();

    RenderSystem.disableBlend();
  }

  private static void drawTiledSprite(PoseStack poseStack, final int tiledWidth, final int tiledHeight, int scaledAmount, TextureAtlasSprite sprite) {
    RenderSystem.setShader(GameRenderer::getPositionTexShader);
    RenderSystem.setShaderTexture(0, InventoryMenu.BLOCK_ATLAS);
    Matrix4f matrix = poseStack.last().pose();

    float r = ((0xFFFFFF >> 16) & 0xFF) / 255.0F;
    float g = ((0xFFFFFF >> 8) & 0xFF) / 255.0F;
    float b = ((0xFFFFFF) & 0xFF) / 255.0F;

    RenderSystem.setShaderColor(r, g, b, 1F);

    final int xTileCount = tiledWidth / TEXTURE_SIZE;
    final int xRemainder = tiledWidth - (xTileCount * TEXTURE_SIZE);
    final int yTileCount = scaledAmount / TEXTURE_SIZE;
    final int yRemainder = scaledAmount - (yTileCount * TEXTURE_SIZE);

    for (int xTile = 0; xTile <= xTileCount; xTile++) {
      for (int yTile = 0; yTile <= yTileCount; yTile++) {
        int width = (xTile == xTileCount) ? xRemainder : TEXTURE_SIZE;
        int height = (yTile == yTileCount) ? yRemainder : TEXTURE_SIZE;
        int x = (xTile * TEXTURE_SIZE);
        int y = tiledHeight - ((yTile + 1) * TEXTURE_SIZE);
        if (width > 0 && height > 0) {
          int maskTop = TEXTURE_SIZE - height;
          int maskRight = TEXTURE_SIZE - width;

          drawTextureWithMasking(matrix, x, y, sprite, maskTop, maskRight, 100);
        }
      }
    }

    RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
  }

  private static void drawTextureWithMasking(Matrix4f matrix, float xCoord, float yCoord, TextureAtlasSprite textureSprite, int maskTop, int maskRight, float zLevel) {
    float uMin = textureSprite.getU0();
    float uMax = textureSprite.getU1();
    float vMin = textureSprite.getV0();
    float vMax = textureSprite.getV1();
    uMax = uMax - (maskRight / 16F * (uMax - uMin));
    vMax = vMax - (maskTop / 16F * (vMax - vMin));

    Tesselator tessellator = Tesselator.getInstance();
    BufferBuilder bufferBuilder = tessellator.getBuilder();
    bufferBuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
    bufferBuilder.vertex(matrix, xCoord, yCoord + 16, zLevel).uv(uMin, vMax).endVertex();
    bufferBuilder.vertex(matrix, xCoord + 16 - maskRight, yCoord + 16, zLevel).uv(uMax, vMax).endVertex();
    bufferBuilder.vertex(matrix, xCoord + 16 - maskRight, yCoord + maskTop, zLevel).uv(uMax, vMin).endVertex();
    bufferBuilder.vertex(matrix, xCoord, yCoord + maskTop, zLevel).uv(uMin, vMin).endVertex();
    tessellator.end();
  }
}
