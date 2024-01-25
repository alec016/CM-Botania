package al.alec.custommachinerybotania.client;

import al.alec.custommachinerybotania.*;
import al.alec.custommachinerybotania.client.integration.jei.element.*;
import al.alec.custommachinerybotania.client.render.element.*;
import com.mojang.blaze3d.systems.*;
import com.mojang.blaze3d.vertex.*;
import dev.architectury.platform.*;
import fr.frinn.custommachinery.api.guielement.*;
import fr.frinn.custommachinery.api.integration.jei.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.*;
import net.minecraft.resources.*;

public class ClientHandler {

  public static void clientInit() {
    RegisterGuiElementWidgetSupplierEvent.EVENT.register(ClientHandler::registerGuiElementWidgets);

    if(Platform.isModLoaded("jei"))
      RegisterGuiElementJEIRendererEvent.EVENT.register(ClientHandler::registerGuiElementJeiRenderers);
  }

  private static void registerGuiElementWidgets(RegisterGuiElementWidgetSupplierEvent event) {
    event.register(Registration.MANA_GUI_ELEMENT.get(), ManaGuiElementWidget::new);
  }

  private static void registerGuiElementJeiRenderers(RegisterGuiElementJEIRendererEvent event) {
    event.register(Registration.MANA_GUI_ELEMENT.get(), new ManaGuiElementJeiRenderer());
  }

  public static void bindTexture(ResourceLocation texture) {
    RenderSystem.setShader(GameRenderer::getPositionTexShader);
    RenderSystem.setShaderTexture(0, texture);
  }

  public static void renderSlotHighlight(PoseStack pose, int x, int y, int width, int height) {
    RenderSystem.disableDepthTest();
    RenderSystem.colorMask(true, true, true, false);
    GuiComponent.fill(pose, x, y, x + width, y + height, -2130706433);
    RenderSystem.colorMask(true, true, true, true);
    RenderSystem.enableDepthTest();
  }
}
