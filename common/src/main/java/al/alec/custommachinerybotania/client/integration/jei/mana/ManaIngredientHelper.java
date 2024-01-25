package al.alec.custommachinerybotania.client.integration.jei.mana;

import al.alec.custommachinerybotania.client.integration.jei.CustomIngredientTypes;
import fr.frinn.custommachinery.*;
import mezz.jei.api.ingredients.*;
import mezz.jei.api.ingredients.subtypes.*;
import net.minecraft.network.chat.*;
import net.minecraft.resources.*;
import org.jetbrains.annotations.*;

public class ManaIngredientHelper implements IIngredientHelper<Mana> {
  @Override
  public IIngredientType<Mana> getIngredientType() {
    return CustomIngredientTypes.MANA;
  }

  @Override
  public String getDisplayName(Mana energy) {
    return Component.translatable("custommachinerybotania.jei.ingredient.mana", energy.getAmount()).getString();
  }

  @Override
  public String getUniqueId(Mana mana, UidContext context) {
    return "" + mana.getAmount() + mana.isPerTick();
  }

  @Override
  public Mana copyIngredient(Mana energy) {
    return new Mana(energy.getAmount(), energy.isPerTick());
  }

  @Override
  public String getErrorInfo(@Nullable Mana energy) {
    return "";
  }

  @Override
  public ResourceLocation getResourceLocation(Mana ingredient) {
    return new ResourceLocation(CustomMachinery.MODID, "mana");
  }
}
