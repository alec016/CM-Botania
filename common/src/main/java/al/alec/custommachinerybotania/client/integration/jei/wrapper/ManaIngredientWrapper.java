package al.alec.custommachinerybotania.client.integration.jei.wrapper;

import al.alec.custommachinerybotania.Registration;
import al.alec.custommachinerybotania.client.integration.jei.CustomIngredientTypes;
import al.alec.custommachinerybotania.client.integration.jei.mana.Mana;
import al.alec.custommachinerybotania.client.integration.jei.mana.ManaJEIIngredientRenderer;
import al.alec.custommachinerybotania.guielement.ManaGuiElement;
import fr.frinn.custommachinery.api.guielement.IGuiElement;
import fr.frinn.custommachinery.api.integration.jei.IJEIIngredientWrapper;
import fr.frinn.custommachinery.api.integration.jei.IRecipeHelper;
import fr.frinn.custommachinery.api.requirement.RequirementIOMode;
import fr.frinn.custommachinery.common.util.Utils;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import net.minecraft.network.chat.Component;

public class ManaIngredientWrapper implements IJEIIngredientWrapper<Mana> {

  private final RequirementIOMode mode;
  private final int recipeTime;
  private final Mana mana;

  public ManaIngredientWrapper(RequirementIOMode mode, int amount, boolean isPerTick, int recipeTime) {
    this.mode = mode;
    this.recipeTime = recipeTime;
    this.mana = new Mana(amount, isPerTick);
  }

  @Override
  public boolean setupRecipe(IRecipeLayoutBuilder builder, int xOffset, int yOffset, IGuiElement element, IRecipeHelper helper) {
    if(!(element instanceof ManaGuiElement manaElement) || element.getType() != Registration.MANA_GUI_ELEMENT.get())
      return false;

    builder.addSlot(roleFromMode(this.mode), element.getX() - xOffset + 2, element.getY() - yOffset)
      .setCustomRenderer(CustomIngredientTypes.MANA, new ManaJEIIngredientRenderer(manaElement))
      .addIngredient(CustomIngredientTypes.MANA, this.mana)
      .addTooltipCallback((recipeSlotView, tooltip) -> {
        Component component;
        String amount = Utils.format(this.mana.getAmount());
        if(this.mana.isPerTick()) {
          String totalMana = Utils.format(this.mana.getAmount() * this.recipeTime);
          if(this.mode == RequirementIOMode.INPUT)
            component = Component.translatable("custommachinerybotania.jei.ingredient.mana.pertick.input", totalMana, amount);
          else
            component = Component.translatable("custommachinerybotania.jei.ingredient.mana.pertick.output", totalMana, amount);
        } else {
          if(this.mode == RequirementIOMode.INPUT)
            component = Component.translatable("custommachinerybotania.jei.ingredient.mana.input", amount);
          else
            component = Component.translatable("custommachinerybotania.jei.ingredient.mana.output", amount);
        }
        tooltip.set(0, component);
      });
    return true;
  }
}
