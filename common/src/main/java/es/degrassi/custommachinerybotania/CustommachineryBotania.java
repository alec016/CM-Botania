package es.degrassi.custommachinerybotania;

import dev.architectury.event.EventResult;
import dev.architectury.event.events.common.InteractionEvent;
import dev.architectury.utils.Env;
import dev.architectury.utils.EnvExecutor;
import es.degrassi.custommachinerybotania.client.ClientHandler;
import fr.frinn.custommachinery.api.component.variant.RegisterComponentVariantEvent;
import fr.frinn.custommachinery.common.init.CustomMachineTile;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import vazkii.botania.common.item.WandOfTheForestItem;

public final class CustommachineryBotania {
  public static final String MODID = "custommachinerybotania";

  public static void init() {
    Registration.GUI_ELEMENTS.register();
    Registration.MACHINE_COMPONENTS.register();
    Registration.REQUIREMENTS.register();

    RegisterComponentVariantEvent.EVENT.register(Registration::registerComponentVariants);

    EnvExecutor.runInEnv(Env.CLIENT, () -> ClientHandler::clientInit);

    InteractionEvent.RIGHT_CLICK_BLOCK.register(CustommachineryBotania::handleWandClick);
  }

  private static EventResult handleWandClick(Player player, InteractionHand hand, BlockPos pos, Direction face) {
    if (
      !player.isShiftKeyDown() &&
        !player.level().isClientSide() &&
        player.level().getBlockEntity(pos) instanceof CustomMachineTile tile &&
        player.getItemInHand(hand).getItem() instanceof WandOfTheForestItem
    ) {
      tile.getComponentManager().markDirty();
      return EventResult.interrupt(true);
    }
    return EventResult.pass();
  }
}
