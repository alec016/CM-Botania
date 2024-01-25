package al.alec.custommachinerybotania.requirements;

import al.alec.custommachinerybotania.*;
import al.alec.custommachinerybotania.components.*;
import fr.frinn.custommachinery.api.codec.*;
import fr.frinn.custommachinery.api.component.*;
import fr.frinn.custommachinery.api.crafting.*;
import fr.frinn.custommachinery.api.requirement.*;
import fr.frinn.custommachinery.impl.requirement.*;

public class ManaRequirementPerTick extends AbstractRequirement<ManaMachineComponent> implements ITickableRequirement<ManaMachineComponent> {
  public static final NamedCodec<ManaRequirementPerTick> CODEC = NamedCodec.record(manaRequirementInstance ->
      manaRequirementInstance.group(
        RequirementIOMode.CODEC.fieldOf("mode").forGetter(IRequirement::getMode),
        NamedCodec.longRange(0L, Long.MAX_VALUE).fieldOf("mana").forGetter(requirement -> requirement.mana)
      ).apply(manaRequirementInstance, ManaRequirementPerTick::new),
    "Mana requirement"
  );

  private final long mana;
  public ManaRequirementPerTick(RequirementIOMode mode, long mana) {
    super(mode);
    this.mana = mana;
  }

  @Override
  public CraftingResult processTick(ManaMachineComponent component, ICraftingContext context) {
    return null;
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
    return false;
  }

  @Override
  public CraftingResult processStart(ManaMachineComponent component, ICraftingContext context) {
    return null;
  }

  @Override
  public CraftingResult processEnd(ManaMachineComponent component, ICraftingContext context) {
    return null;
  }
}
