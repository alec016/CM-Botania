package al.alec.custommachinerybotania.integration.crafttweaker.machine;


import al.alec.custommachinerybotania.requirements.ManaRequirement;
import al.alec.custommachinerybotania.requirements.ManaRequirementPerTick;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import fr.frinn.custommachinery.api.requirement.RequirementIOMode;
import fr.frinn.custommachinery.common.integration.crafttweaker.CTConstants;
import fr.frinn.custommachinery.common.integration.crafttweaker.CustomMachineRecipeCTBuilder;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Expansion(CTConstants.RECIPE_BUILDER_MACHINE)
public class ManaRequirementCT {

  @ZenCodeType.Method
  public static CustomMachineRecipeCTBuilder requireMana(CustomMachineRecipeCTBuilder builder, int mana) {
    return addManaRequirement(builder, RequirementIOMode.INPUT, mana, false);
  }

  @ZenCodeType.Method
  public static CustomMachineRecipeCTBuilder requireManaPerTick(CustomMachineRecipeCTBuilder builder, int mana) {
    return addManaRequirement(builder, RequirementIOMode.INPUT, mana, true);
  }

  @ZenCodeType.Method
  public static CustomMachineRecipeCTBuilder produceMana(CustomMachineRecipeCTBuilder builder, int mana) {
    return addManaRequirement(builder, RequirementIOMode.OUTPUT, mana, false);
  }

  @ZenCodeType.Method
  public static CustomMachineRecipeCTBuilder produceManaPerTick(CustomMachineRecipeCTBuilder builder, int mana) {
    return addManaRequirement(builder, RequirementIOMode.OUTPUT, mana, true);
  }

  private static CustomMachineRecipeCTBuilder addManaRequirement(CustomMachineRecipeCTBuilder builder, RequirementIOMode mode, int mana, boolean perTick) {
    if (mana < 0)
      return builder.error("Mana value cannot be negative");
    return builder.addRequirement(perTick ? new ManaRequirementPerTick(mode, mana) : new ManaRequirement(mode, mana));
  }
}
