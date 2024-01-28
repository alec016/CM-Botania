package al.alec.custommachinerybotania.mixin;

import al.alec.custommachinerybotania.client.render.*;
import fr.frinn.custommachinery.common.init.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import vazkii.botania.api.block.*;
import vazkii.botania.common.block.block_entity.*;

@Mixin(value = { BotaniaBlockEntities.class}, remap = false)
public abstract class BotaniaBlockEntitiesMixin {

  @Inject(method="registerWandHudCaps", at=@At("TAIL"))
  private static void registerWandHudCaps (BotaniaBlockEntities.BECapConsumer<WandHUD> consumer, CallbackInfo ci) {
    consumer.accept(be -> new CustomMachineTileWandHud((CustomMachineTile) be), Registration.CUSTOM_MACHINE_TILE.get());
  }
}
