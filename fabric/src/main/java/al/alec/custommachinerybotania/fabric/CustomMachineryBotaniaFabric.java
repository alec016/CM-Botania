package al.alec.custommachinerybotania.fabric;

import al.alec.custommachinerybotania.CustomMachineryBotania;
import al.alec.custommachinerybotania.client.render.CustomMachineTileWandHud;
import fr.frinn.custommachinery.common.init.CustomMachineTile;
import fr.frinn.custommachinery.common.init.Registration;
import net.fabricmc.api.ModInitializer;
import vazkii.botania.api.BotaniaFabricCapabilities;
import vazkii.botania.api.BotaniaFabricClientCapabilities;

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
//    BotaniaFabricClientCapabilities.WAND_HUD.registerForBlockEntities(
//      (be, unit) -> be instanceof CustomMachineTile tile ? new CustomMachineTileWandHud(tile) : null,
//      Registration.CUSTOM_MACHINE_TILE.get()
//    );
  }
}