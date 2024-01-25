package al.alec.custommachinerybotania.client.integration.jei;

import al.alec.custommachinerybotania.client.integration.jei.mana.Mana;
import mezz.jei.api.ingredients.IIngredientType;

public class CustomIngredientTypes {
  public static final IIngredientType<Mana> MANA = () -> Mana.class;
}
