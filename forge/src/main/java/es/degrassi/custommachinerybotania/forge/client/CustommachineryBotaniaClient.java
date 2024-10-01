package es.degrassi.custommachinerybotania.forge.client;

import es.degrassi.custommachinerybotania.CustommachineryBotania;
import es.degrassi.custommachinerybotania.client.render.CustomMachineTileWandHud;
import fr.frinn.custommachinery.common.init.CustomMachineTile;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import vazkii.botania.api.BotaniaForgeClientCapabilities;
import static vazkii.botania.common.lib.ResourceLocationHelper.prefix;
import vazkii.botania.forge.CapabilityUtil;

@Mod.EventBusSubscriber(modid = CustommachineryBotania.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class CustommachineryBotaniaClient {

  @SubscribeEvent
  public static void clientInit(FMLClientSetupEvent event) {
    var bus = MinecraftForge.EVENT_BUS;
    bus.addGenericListener(BlockEntity.class, CustommachineryBotaniaClient::attachBeCapabilities);
  }

  private static void attachBeCapabilities(final AttachCapabilitiesEvent<BlockEntity> e) {
    var be = e.getObject();

    if (be instanceof CustomMachineTile tile) {
      e.addCapability(
        prefix("wand_hud"),
        CapabilityUtil.makeProvider(
          BotaniaForgeClientCapabilities.WAND_HUD,
          new CustomMachineTileWandHud(tile)
        )
      );
    }
  }
}
