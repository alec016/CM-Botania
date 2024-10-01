package es.degrassi.custommachinerybotania.fabric.client;

import es.degrassi.custommachinerybotania.client.render.CustomMachineTileWandHud;
import fr.frinn.custommachinery.common.init.CustomMachineTile;
import fr.frinn.custommachinery.common.init.Registration;
import net.fabricmc.api.ClientModInitializer;
import vazkii.botania.api.BotaniaFabricClientCapabilities;

public final class CustommachineryBotaniaFabricClient implements ClientModInitializer {
  @Override
  public void onInitializeClient() {
    registerCapabilities();
  }

  public void registerCapabilities() {
    BotaniaFabricClientCapabilities.WAND_HUD.registerForBlockEntities(
      (be, unit) -> be instanceof CustomMachineTile tile ? new CustomMachineTileWandHud(tile) : null,
      Registration.CUSTOM_MACHINE_TILE.get()
    );
    // This entrypoint is suitable for setting up client-specific logic, such as rendering.
  }
}
