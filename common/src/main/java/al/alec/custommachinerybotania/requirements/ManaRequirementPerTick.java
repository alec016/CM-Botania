package al.alec.custommachinerybotania.requirements;


import al.alec.custommachinerybotania.Registration;
import al.alec.custommachinerybotania.client.integration.jei.mana.Mana;
import al.alec.custommachinerybotania.client.integration.jei.wrapper.ManaIngredientWrapper;
import al.alec.custommachinerybotania.components.ManaMachineComponent;
import fr.frinn.custommachinery.api.codec.NamedCodec;
import fr.frinn.custommachinery.api.component.MachineComponentType;
import fr.frinn.custommachinery.api.crafting.CraftingResult;
import fr.frinn.custommachinery.api.crafting.ICraftingContext;
import fr.frinn.custommachinery.api.crafting.IMachineRecipe;
import fr.frinn.custommachinery.api.integration.jei.IJEIIngredientRequirement;
import fr.frinn.custommachinery.api.integration.jei.IJEIIngredientWrapper;
import fr.frinn.custommachinery.api.requirement.IRequirement;
import fr.frinn.custommachinery.api.requirement.ITickableRequirement;
import fr.frinn.custommachinery.api.requirement.RequirementIOMode;
import fr.frinn.custommachinery.api.requirement.RequirementType;
import fr.frinn.custommachinery.impl.requirement.AbstractRequirement;
import java.util.Collections;
import java.util.List;
import net.minecraft.network.chat.Component;

public class ManaRequirementPerTick extends AbstractRequirement<ManaMachineComponent> implements ITickableRequirement<ManaMachineComponent>, IJEIIngredientRequirement<Mana> {
  public static final NamedCodec<ManaRequirementPerTick> CODEC = NamedCodec.record(manaRequirementInstance ->
      manaRequirementInstance.group(
        RequirementIOMode.CODEC.fieldOf("mode").forGetter(IRequirement::getMode),
        NamedCodec.intRange(0, Integer.MAX_VALUE).fieldOf("mana").forGetter(requirement -> requirement.mana)
      ).apply(manaRequirementInstance, ManaRequirementPerTick::new),
    "Mana requirement per tick"
  );

  private final int mana;
  public ManaRequirementPerTick(RequirementIOMode mode, int mana) {
    super(mode);
    this.mana = mana;
  }

  @Override
  public CraftingResult processTick(ManaMachineComponent component, ICraftingContext context) {
    if (getMode() == RequirementIOMode.OUTPUT) {
      if ((component.getCapacity() - component.getMana()) < mana)
        return CraftingResult.error(Component.translatable(
          "custommachinerybotania.requirements.manapertick.error.output",
          mana
        ));
      component.receiveMana(mana, false);
      return CraftingResult.success();
    } else if (getMode() == RequirementIOMode.INPUT) {
      if (component.getMana() < mana)
        return CraftingResult.error(Component.translatable(
          "custommachinerybotania.requirements.manapertick.error.input",
          mana,
          component.getMana()
        ));
      component.extractMana(mana, false);
      return CraftingResult.success();
    }
    return CraftingResult.pass();
  }

  @Override
  public RequirementType<ManaRequirementPerTick> getType() {
    return Registration.MANA_REQUIREMENT_PER_TICK.get();
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
    return CraftingResult.pass();
  }

  @Override
  public CraftingResult processEnd(ManaMachineComponent component, ICraftingContext context) {
    return CraftingResult.pass();
  }

  @Override
  public List<IJEIIngredientWrapper<Mana>> getJEIIngredientWrappers(IMachineRecipe recipe) {
    return Collections.singletonList(new ManaIngredientWrapper(this.getMode(), this.mana, true, recipe.getRecipeTime()));
  }
}
