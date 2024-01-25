package al.alec.custommachinerybotania.client.render.element;

import al.alec.custommachinerybotania.guielement.ManaGuiElement;
import al.alec.custommachinerybotania.Registration;
import fr.frinn.custommachinery.impl.guielement.TexturedGuiElementWidget;
import fr.frinn.custommachinery.api.guielement.IMachineScreen;
import net.minecraft.network.chat.Component;
import com.mojang.blaze3d.vertex.PoseStack;
import fr.frinn.custommachinery.client.ClientHandler;
import net.minecraft.client.gui.GuiComponent;

import java.util.Collections;
import java.util.List;
import fr.frinn.custommachinery.common.util.Utils;

public class ManaGuiElementWidget extends TexturedGuiElementWidget<ManaGuiElement> {

  public ManaGuiElementWidget(ManaGuiElement element, IMachineScreen screen) {
    super(element, screen, Component.literal("Mana"));
  }

  @Override
  public void renderButton(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
    super.renderButton(poseStack, mouseX, mouseY, partialTicks);
    this.getScreen().getTile().getComponentManager().getComponent(Registration.MANA_MACHINE_COMPONENT.get()).ifPresent(mana -> {
      double fillPercent = mana.getFillPercent();
      int manaHeight = (int)(fillPercent * (double)(this.height));
      ClientHandler.bindTexture(this.getElement().getFilledTexture());
      GuiComponent.blit(poseStack, this.x, this.y + this.height - manaHeight, 0, this.height - manaHeight, this.width, manaHeight, this.width, this.height);
    });
    if(this.isHoveredOrFocused() && this.getElement().highlight())
      ClientHandler.renderSlotHighlight(poseStack, this.x + 1, this.y + 1, this.width - 2, this.height - 2);
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
}
