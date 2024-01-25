package al.alec.custommachinerybotania.forge;

import dev.architectury.platform.forge.EventBuses;
import al.alec.custommachinerybotania.CustomMachineryBotania;
import net.minecraft.core.Registry;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.RegisterEvent;

@Mod(CustomMachineryBotania.MODID)
public class CustomMachineryBotaniaForge {

  public CustomMachineryBotaniaForge() {
    final IEventBus MOD_BUS = FMLJavaModLoadingContext.get().getModEventBus();
    EventBuses.registerModEventBus(CustomMachineryBotania.MODID, MOD_BUS);
    MOD_BUS.addListener(this::onRegister);

    //CustomMachineryCreate.init();
  }

  /**
   * Delay the initialization of the deferred registries because CM might not have created the registries at that time.
   */
  private void onRegister(final RegisterEvent event) {
    if(event.getRegistryKey() == Registry.BLOCK_REGISTRY)
      CustomMachineryBotania.init();
  }
}