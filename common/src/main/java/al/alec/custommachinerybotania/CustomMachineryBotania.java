package al.alec.custommachinerybotania;

import al.alec.custommachinerybotania.client.*;
import dev.architectury.event.*;
import dev.architectury.event.events.common.*;
import dev.architectury.utils.*;
import fr.frinn.custommachinery.common.init.*;
import net.minecraft.core.*;
import net.minecraft.world.*;
import net.minecraft.world.entity.player.*;
import vazkii.botania.common.item.*;

public class CustomMachineryBotania {
  public static final String MODID = "custommachinerybotania";

  public static void init() {
    Registration.GUI_ELEMENTS.register();
    Registration.MACHINE_COMPONENTS.register();
    Registration.REQUIREMENTS.register();
    EnvExecutor.runInEnv(Env.CLIENT, () -> ClientHandler::clientInit);

    InteractionEvent.RIGHT_CLICK_BLOCK.register(CustomMachineryBotania::handleWandClick);
  }

  private static EventResult handleWandClick(Player player, InteractionHand hand, BlockPos pos, Direction face) {
    if (
      !player.isShiftKeyDown() &&
      !player.level.isClientSide() &&
      player.level.getBlockEntity(pos) instanceof CustomMachineTile tile &&
      player.getItemInHand(hand).getItem() instanceof WandOfTheForestItem
    ) {
      tile.getComponentManager().markDirty();
      return EventResult.interrupt(true);
    }
    return EventResult.pass();
  }
}
