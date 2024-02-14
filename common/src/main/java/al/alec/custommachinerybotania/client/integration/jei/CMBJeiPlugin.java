package al.alec.custommachinerybotania.client.integration.jei;


import al.alec.custommachinerybotania.CustomMachineryBotania;
import al.alec.custommachinerybotania.client.integration.jei.mana.ManaIngredientHelper;
import fr.frinn.custommachinery.client.integration.jei.DummyIngredientRenderer;
import java.util.Collections;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IModIngredientRegistration;
import net.minecraft.resources.ResourceLocation;

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