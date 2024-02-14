package al.alec.custommachinerybotania.client.integration.jei.element;


import al.alec.custommachinerybotania.client.ClientHandler;
import al.alec.custommachinerybotania.guielement.ManaGuiElement;
import com.mojang.blaze3d.vertex.PoseStack;
import fr.frinn.custommachinery.api.crafting.IMachineRecipe;
import fr.frinn.custommachinery.api.integration.jei.IJEIElementRenderer;
import net.minecraft.client.gui.GuiComponent;

public class ManaGuiElementJeiRenderer implements IJEIElementRenderer<ManaGuiElement> {
  @Override
  public void renderElementInJEI(PoseStack matrix, ManaGuiElement element, IMachineRecipe recipe, int mouseX, int mouseY) {
    int posX = element.getX();
    int posY = element.getY();
    int width = element.getWidth();
    int height = element.getHeight();

    ClientHandler.bindTexture(element.getEmptyTexture());
    GuiComponent.blit(matrix, posX, posY, 0, 0, width, height, width, height);
  }
}
