package al.alec.custommachinerybotania.fabric.client;

import al.alec.custommachinerybotania.client.integration.botania.CMBEntities;
import al.alec.custommachinerybotania.client.render.CustomMachineTileWandHud;
import fr.frinn.custommachinery.common.init.CustomMachineTile;
import fr.frinn.custommachinery.common.init.Registration;
import net.fabricmc.api.ClientModInitializer;
import vazkii.botania.api.BotaniaFabricClientCapabilities;

public class CustomMachineryBotaniaClient implements ClientModInitializer {
  @Override
  public void onInitializeClient() {
    registerCapabilities();
  }

  public void registerCapabilities() {
    BotaniaFabricClientCapabilities.WAND_HUD.registerForBlockEntities(
      (be, unit) -> be instanceof CustomMachineTile tile ? new CustomMachineTileWandHud(tile) : null,
      Registration.CUSTOM_MACHINE_TILE.get()
    );
//    CMBEntities.registerWandHudCaps((factory, types) -> BotaniaFabricClientCapabilities.WAND_HUD.registerForBlockEntities((be, c) -> factory.apply(be), types));
  }
}
