package al.alec.custommachinerybotania.client.integration.botania;


import al.alec.custommachinerybotania.client.render.CustomMachineTileWandHud;
import fr.frinn.custommachinery.common.init.CustomMachineTile;
import fr.frinn.custommachinery.common.init.Registration;
import vazkii.botania.api.block.WandHUD;
import vazkii.botania.common.block.block_entity.BotaniaBlockEntities;

public class CMBEntities {
  public static void registerWandHudCaps (BotaniaBlockEntities.BECapConsumer<WandHUD> consumer) {
    consumer.accept(be -> new CustomMachineTileWandHud((CustomMachineTile) be), Registration.CUSTOM_MACHINE_TILE.get());
  }
}
