package al.alec.custommachinerybotania.client.integration.jei.element;

import al.alec.custommachinerybotania.guielement.*;
import com.mojang.blaze3d.vertex.*;
import fr.frinn.custommachinery.api.crafting.*;
import fr.frinn.custommachinery.api.integration.jei.*;
import fr.frinn.custommachinery.client.*;
import net.minecraft.client.gui.*;

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
