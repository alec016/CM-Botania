package al.alec.custommachinerybotania.fabric;

import al.alec.custommachinerybotania.CustomMachineryBotania;
import fr.frinn.custommachinery.common.init.*;
import net.fabricmc.api.ModInitializer;
import vazkii.botania.api.*;

public class CustomMachineryBotaniaFabric implements ModInitializer {
  @Override
  public void onInitialize() {
    CustomMachineryBotania.init();
    registerCapabilities();
  }

  private void registerCapabilities() {
    BotaniaFabricCapabilities.MANA_RECEIVER.registerSelf(
      Registration.CUSTOM_MACHINE_TILE.get()
    );
    BotaniaFabricCapabilities.WANDABLE.registerSelf(
      Registration.CUSTOM_MACHINE_TILE.get()
    );
  }
}