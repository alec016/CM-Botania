package al.alec.custommachinerybotania.integration.kubejs;

import al.alec.custommachinerybotania.requirements.ManaRequirementPerTick;
import fr.frinn.custommachinery.api.integration.kubejs.RecipeJSBuilder;
import fr.frinn.custommachinery.api.requirement.RequirementIOMode;

public interface ManaRequirementPerTickJS extends RecipeJSBuilder {

  default RecipeJSBuilder requireManaPerTick(int mana) {
    return addManaPerTickRequirement(RequirementIOMode.INPUT, mana);
  }

  default RecipeJSBuilder produceManaPerTick(int mana) {
    return addManaPerTickRequirement(RequirementIOMode.OUTPUT, mana);
  }

  default RecipeJSBuilder addManaPerTickRequirement(RequirementIOMode mode, int mana) {
    if (mana < 0)
      return error("Mana value cannot be negative");
    return addRequirement(new ManaRequirementPerTick(mode, mana));
  }
}
