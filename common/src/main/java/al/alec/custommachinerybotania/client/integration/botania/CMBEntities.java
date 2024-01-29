package al.alec.custommachinerybotania.client.integration.botania;

import al.alec.custommachinerybotania.client.render.*;
import fr.frinn.custommachinery.common.init.*;
import vazkii.botania.api.block.*;
import vazkii.botania.common.block.block_entity.*;

public class CMBEntities {
  public static void registerWandHudCaps (BotaniaBlockEntities.BECapConsumer<WandHUD> consumer) {
    consumer.accept(be -> new CustomMachineTileWandHud((CustomMachineTile) be), Registration.CUSTOM_MACHINE_TILE.get());
  }
}
