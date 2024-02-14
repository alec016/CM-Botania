package al.alec.custommachinerybotania.integration.crafttweaker.craft;


import al.alec.custommachinerybotania.requirements.ManaRequirement;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import fr.frinn.custommachinery.api.requirement.RequirementIOMode;
import fr.frinn.custommachinery.common.integration.crafttweaker.CTConstants;
import fr.frinn.custommachinery.common.integration.crafttweaker.CustomCraftRecipeCTBuilder;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Expansion(CTConstants.RECIPE_BUILDER_CRAFT)
public class ManaRequirementCT {

  @ZenCodeType.Method
  public static CustomCraftRecipeCTBuilder requireMana(CustomCraftRecipeCTBuilder builder, int mana) {
    return addManaRequirement(builder, RequirementIOMode.INPUT, mana);
  }

  @ZenCodeType.Method
  public static CustomCraftRecipeCTBuilder produceMana(CustomCraftRecipeCTBuilder builder, int mana) {
    return addManaRequirement(builder, RequirementIOMode.OUTPUT, mana);
  }

  private static CustomCraftRecipeCTBuilder addManaRequirement(CustomCraftRecipeCTBuilder builder, RequirementIOMode mode, int mana) {
    if (mana < 0)
      return builder.error("Mana value cannot be negative");
    return builder.addRequirement(new ManaRequirement(mode, mana));
  }
}
