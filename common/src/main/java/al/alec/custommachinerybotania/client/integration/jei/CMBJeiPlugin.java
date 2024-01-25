package al.alec.custommachinerybotania.client.integration.jei;

import al.alec.custommachinerybotania.*;
import al.alec.custommachinerybotania.client.integration.jei.mana.*;
import fr.frinn.custommachinery.client.integration.jei.*;
import java.util.*;
import mezz.jei.api.*;
import mezz.jei.api.registration.*;
import net.minecraft.resources.*;

@JeiPlugin
public class CMBJeiPlugin implements IModPlugin {

  public static final ResourceLocation PLUGIN_ID = new ResourceLocation(CustomMachineryBotania.MODID, "jei_plugin");

  @Override
  public ResourceLocation getPluginUid() {
    return PLUGIN_ID;
  }

  @Override
  public void registerIngredients(IModIngredientRegistration registration) {
    registration.register(CustomIngredientTypes.MANA, Collections.emptyList(), new ManaIngredientHelper(), new DummyIngredientRenderer<>());
  }
}