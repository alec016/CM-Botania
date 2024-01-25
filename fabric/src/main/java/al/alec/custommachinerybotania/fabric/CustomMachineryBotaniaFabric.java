package al.alec.custommachinerybotania.fabric;

import al.alec.custommachinerybotania.CustomMachineryBotania;
import net.fabricmc.api.ModInitializer;

public class CustomMachineryBotaniaFabric implements ModInitializer {
  @Override
  public void onInitialize() {
    CustomMachineryBotania.init();
  }
}