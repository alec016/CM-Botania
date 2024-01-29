package al.alec.custommachinerybotania.fabric;

import al.alec.custommachinerybotania.CustomMachineryBotania;
import al.alec.custommachinerybotania.client.render.*;
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
    BotaniaFabricClientCapabilities.WAND_HUD.registerForBlockEntities(
      (be, unit) -> new CustomMachineTileWandHud((CustomMachineTile) be),
      Registration.CUSTOM_MACHINE_TILE.get()
    );

//    BotaniaFabricClientCapabilities.WAND_HUD.registerForBlockEntity(
//      (be, unit) -> new CustomMachineTileWandHud(be),
//      Registration.CUSTOM_MACHINE_TILE.get()
//    );
  }
}