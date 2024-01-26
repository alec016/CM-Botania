package al.alec.custommachinerybotania.requirements;

import al.alec.custommachinerybotania.*;
import al.alec.custommachinerybotania.client.integration.jei.mana.*;
import al.alec.custommachinerybotania.client.integration.jei.wrapper.*;
import al.alec.custommachinerybotania.components.*;
import fr.frinn.custommachinery.api.codec.*;
import fr.frinn.custommachinery.api.component.*;
import fr.frinn.custommachinery.api.crafting.*;
import fr.frinn.custommachinery.api.integration.jei.*;
import fr.frinn.custommachinery.api.requirement.*;
import fr.frinn.custommachinery.impl.requirement.*;
import java.util.*;
import net.minecraft.network.chat.*;

public class ManaRequirement extends AbstractRequirement<ManaMachineComponent> implements IJEIIngredientRequirement<Mana> {
  public static final NamedCodec<ManaRequirement> CODEC = NamedCodec.record(manaRequirementInstance ->
    manaRequirementInstance.group(
      RequirementIOMode.CODEC.fieldOf("mode").forGetter(IRequirement::getMode),
      NamedCodec.intRange(0, Integer.MAX_VALUE).fieldOf("mana").forGetter(requirement -> requirement.mana)
    ).apply(manaRequirementInstance, ManaRequirement::new),
    "Mana requirement"
  );

  private final int mana;
  public ManaRequirement(RequirementIOMode mode, int mana) {
    super(mode);
    this.mana = mana;
  }

  @Override
  public RequirementType<ManaRequirement> getType() {
    return Registration.MANA_REQUIREMENT.get();
  }

  @Override
  public MachineComponentType<ManaMachineComponent> getComponentType() {
    return Registration.MANA_MACHINE_COMPONENT.get();
  }

  @Override
  public boolean test(ManaMachineComponent component, ICraftingContext context) {
    return switch (getMode()) {
      case INPUT -> component.getMana() >= mana;
      case OUTPUT -> (component.getCapacity() - component.getMana()) >= mana;
    };
  }

  @Override
  public CraftingResult processStart(ManaMachineComponent component, ICraftingContext context) {
    if (getMode() == RequirementIOMode.INPUT) {
      if (component.getMana() < mana)
        return CraftingResult.error(Component.translatable(
          "custommachinerybotania.requirements.mana.error.input",
          mana,
          component.getMana()
        ));
      component.extractMana(mana, false);
      return CraftingResult.success();
    }
    return CraftingResult.pass();
  }

  @Override
  public CraftingResult processEnd(ManaMachineComponent component, ICraftingContext context) {
    if (getMode() == RequirementIOMode.OUTPUT) {
      if ((component.getCapacity() - component.getMana()) < mana)
        return CraftingResult.error(Component.translatable(
          "custommachinerybotania.requirements.mana.error.output",
          mana
        ));
      component.receiveMana(mana, false);
      return CraftingResult.success();
    }
    return CraftingResult.pass();
  }

  @Override
  public List<IJEIIngredientWrapper<Mana>> getJEIIngredientWrappers(IMachineRecipe recipe) {
    return Collections.singletonList(new ManaIngredientWrapper(this.getMode(), this.mana, false, recipe.getRecipeTime()));
  }
}
