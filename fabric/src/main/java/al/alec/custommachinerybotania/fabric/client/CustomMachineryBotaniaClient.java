package al.alec.custommachinerybotania.fabric.client;

import al.alec.custommachinerybotania.client.integration.botania.*;
import net.fabricmc.api.*;
import vazkii.botania.api.*;
public class CustomMachineryBotaniaClient implements ClientModInitializer {
  @Override
  public void onInitializeClient() {
    registerCapabilities();
  }

  public void registerCapabilities() {
    CMBEntities.registerWandHudCaps((factory, types) -> BotaniaFabricClientCapabilities.WAND_HUD.registerForBlockEntities((be, c) -> factory.apply(be), types));
  }
}
