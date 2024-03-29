package al.alec.custommachinerybotania.integration.kubejs;

import al.alec.custommachinerybotania.requirements.ManaRequirement;
import fr.frinn.custommachinery.api.integration.kubejs.RecipeJSBuilder;
import fr.frinn.custommachinery.api.requirement.RequirementIOMode;

public interface ManaRequirementJS extends RecipeJSBuilder {

  default RecipeJSBuilder requireMana(int mana) {
    return addManaRequirement(RequirementIOMode.INPUT, mana);
  }

  default RecipeJSBuilder produceMana(int mana) {
    return addManaRequirement(RequirementIOMode.OUTPUT, mana);
  }

  default RecipeJSBuilder addManaRequirement(RequirementIOMode mode, int mana) {
    if (mana < 0)
      return error("Mana value cannot be negative");
    return addRequirement(new ManaRequirement(mode, mana));
  }
}
