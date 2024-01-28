package al.alec.custommachinerybotania.mixin;

import fr.frinn.custommachinery.common.init.*;
import net.minecraft.core.*;
import net.minecraft.world.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.phys.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import vazkii.botania.common.item.*;

@Mixin({ CustomMachineBlock.class })
public class CustomMachineBlockMixin {
  @Inject(method = "use", at = @At("HEAD"), cancellable = true)
  private void use (BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit, CallbackInfoReturnable<InteractionResult> cir) {
    if (player.getItemInHand(hand).is(BotaniaItems.twigWand) || player.getItemInHand(hand).is(BotaniaItems.dreamwoodWand)) cir.setReturnValue(InteractionResult.PASS);
  }
}
