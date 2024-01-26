package al.alec.custommachinerybotania.integration.crafttweaker.craft;


import al.alec.custommachinerybotania.requirements.*;
import com.blamejared.crafttweaker.api.annotation.*;
import fr.frinn.custommachinery.api.integration.kubejs.*;
import fr.frinn.custommachinery.api.requirement.*;
import fr.frinn.custommachinery.common.integration.crafttweaker.*;
import org.openzen.zencode.java.*;

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
