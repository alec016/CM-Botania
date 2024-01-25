package al.alec.custommachinerybotania.requirements;

import al.alec.custommachinerybotania.*;
import al.alec.custommachinerybotania.components.*;
import fr.frinn.custommachinery.api.codec.*;
import fr.frinn.custommachinery.api.component.*;
import fr.frinn.custommachinery.api.crafting.*;
import fr.frinn.custommachinery.api.requirement.*;
import fr.frinn.custommachinery.impl.requirement.*;

public class ManaRequirement extends AbstractRequirement<ManaMachineComponent> {
  public static final NamedCodec<ManaRequirement> CODEC = NamedCodec.record(manaRequirementInstance ->
    manaRequirementInstance.group(
      RequirementIOMode.CODEC.fieldOf("mode").forGetter(IRequirement::getMode),
      NamedCodec.longRange(0L, Long.MAX_VALUE).fieldOf("mana").forGetter(requirement -> requirement.mana)
    ).apply(manaRequirementInstance, ManaRequirement::new),
    "Mana requirement"
  );

  private final long mana;
  public ManaRequirement(RequirementIOMode mode, long mana) {
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
