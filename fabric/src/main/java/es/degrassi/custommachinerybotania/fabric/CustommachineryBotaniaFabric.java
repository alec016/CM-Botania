package es.degrassi.custommachinerybotania.fabric;

import es.degrassi.custommachinerybotania.CustommachineryBotania;
import fr.frinn.custommachinery.common.init.Registration;
import net.fabricmc.api.ModInitializer;
import vazkii.botania.api.BotaniaFabricCapabilities;

public final class CustommachineryBotaniaFabric implements ModInitializer {
  @Override
  public void onInitialize() {
    CustommachineryBotania.init();
    registerCapabilities();
  }

  private void registerCapabilities() {
    BotaniaFabricCapabilities.MANA_RECEIVER.registerSelf(
      Registration.CUSTOM_MACHINE_TILE.get()
    );
    BotaniaFabricCapabilities.WANDABLE.registerSelf(
      Registration.CUSTOM_MACHINE_TILE.get()
    );
//    BotaniaFabricClientCapabilities.WAND_HUD.registerForBlockEntities(
//      (be, unit) -> be instanceof CustomMachineTile tile ? new CustomMachineTileWandHud(tile) : null,
//      Registration.CUSTOM_MACHINE_TILE.get()
//    );
  }
}
