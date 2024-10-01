package es.degrassi.custommachinerybotania.client.integration.jei.element;

import es.degrassi.custommachinerybotania.guielement.ManaGuiElement;
import fr.frinn.custommachinery.api.crafting.IMachineRecipe;
import fr.frinn.custommachinery.api.integration.jei.IJEIElementRenderer;
import net.minecraft.client.gui.GuiGraphics;

public class ManaGuiElementJeiRenderer implements IJEIElementRenderer<ManaGuiElement> {
  @Override
  public void renderElementInJEI(GuiGraphics matrix, ManaGuiElement element, IMachineRecipe recipe, int mouseX, int mouseY) {
    int posX = element.getX();
    int posY = element.getY();
    int width = element.getWidth();
    int height = element.getHeight();

    matrix.blit(element.getEmptyTexture(), posX, posY, 0, 0, width, height, width, height);
  }
}
