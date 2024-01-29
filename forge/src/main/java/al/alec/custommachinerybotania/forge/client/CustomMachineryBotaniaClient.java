package al.alec.custommachinerybotania.forge.client;

import al.alec.custommachinerybotania.*;
import al.alec.custommachinerybotania.client.integration.botania.*;
import com.google.common.base.*;
import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;
import net.minecraft.world.level.block.entity.*;
import net.minecraftforge.api.distmarker.*;
import net.minecraftforge.common.*;
import net.minecraftforge.event.*;
import net.minecraftforge.eventbus.api.*;
import net.minecraftforge.fml.common.*;
import net.minecraftforge.fml.event.lifecycle.*;
import vazkii.botania.api.*;
import vazkii.botania.api.block.*;
import static vazkii.botania.common.lib.ResourceLocationHelper.*;
import vazkii.botania.forge.*;

@Mod.EventBusSubscriber(modid = CustomMachineryBotania.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class CustomMachineryBotaniaClient {
  private static final Supplier<Map<BlockEntityType<?>, Function<BlockEntity, WandHUD>>> WAND_HUD = Suppliers.memoize(() -> {
    var ret = new IdentityHashMap<BlockEntityType<?>, Function<BlockEntity, WandHUD>>();
    CMBEntities.registerWandHudCaps((factory, types) -> {
      for (var type : types) {
        ret.put(type, factory);
      }
    });
    return Collections.unmodifiableMap(ret);
  });

  @SubscribeEvent
  public static void clientInit(FMLClientSetupEvent event) {
    var bus = MinecraftForge.EVENT_BUS;
    bus.addGenericListener(BlockEntity.class, CustomMachineryBotaniaClient::attachBeCapabilities);
  }

  private static void attachBeCapabilities(AttachCapabilitiesEvent<BlockEntity> e) {
    var be = e.getObject();

    var makeWandHud = WAND_HUD.get().get(be.getType());
    if (makeWandHud != null) {
      e.addCapability(prefix("wand_hud"),
        CapabilityUtil.makeProvider(BotaniaForgeClientCapabilities.WAND_HUD, makeWandHud.apply(be)));
    }
  }
}
