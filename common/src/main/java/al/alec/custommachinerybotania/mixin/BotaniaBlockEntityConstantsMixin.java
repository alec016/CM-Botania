package al.alec.custommachinerybotania.mixin;

import fr.frinn.custommachinery.common.init.*;
import java.util.*;
import net.minecraft.world.level.block.entity.*;
import org.spongepowered.asm.mixin.*;
import vazkii.botania.common.block.block_entity.*;

@Mixin({ BlockEntityConstants.class})
public class BotaniaBlockEntityConstantsMixin {

  @Shadow @Final public static Set<BlockEntityType<?>> SELF_MANA_RECEIVER_BES;

  @Shadow @Final public static Set<BlockEntityType<?>> SELF_WANDADBLE_BES;

  static {
    SELF_WANDADBLE_BES.add(Registration.CUSTOM_MACHINE_TILE.get());
    SELF_MANA_RECEIVER_BES.add(Registration.CUSTOM_MACHINE_TILE.get());
  }
}
