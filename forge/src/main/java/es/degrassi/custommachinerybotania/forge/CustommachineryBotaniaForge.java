package es.degrassi.custommachinerybotania.forge;

import dev.architectury.platform.forge.EventBuses;
import es.degrassi.custommachinerybotania.CustommachineryBotania;
import fr.frinn.custommachinery.common.init.CustomMachineTile;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.RegisterEvent;
import vazkii.botania.api.BotaniaForgeCapabilities;
import vazkii.botania.api.block.Wandable;
import vazkii.botania.api.mana.ManaReceiver;
import static vazkii.botania.common.lib.ResourceLocationHelper.prefix;
import vazkii.botania.forge.CapabilityUtil;

@Mod(CustommachineryBotania.MODID)
@Mod.EventBusSubscriber(modid = CustommachineryBotania.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class CustommachineryBotaniaForge {

  public CustommachineryBotaniaForge() {
    final IEventBus MOD_BUS = FMLJavaModLoadingContext.get().getModEventBus();
    EventBuses.registerModEventBus(CustommachineryBotania.MODID, MOD_BUS);
    MOD_BUS.addListener(this::onRegister);
    FMLJavaModLoadingContext.get().getModEventBus().addListener(this::commonSetup);
  }

  private void commonSetup(FMLCommonSetupEvent evt) {
    registerEvents();
  }

  private void registerEvents () {
    IEventBus bus = MinecraftForge.EVENT_BUS;
    bus.addGenericListener(BlockEntity.class, this::attachBeCaps);
  }

  private void onRegister(final RegisterEvent event) {
    if(event.getRegistryKey() == Registries.BLOCK)
      CustommachineryBotania.init();
  }

  private void attachBeCaps(final AttachCapabilitiesEvent<BlockEntity> e) {
    var be = e.getObject();
    if (be instanceof CustomMachineTile tile) {
      e.addCapability(
        prefix( "mana_receiver"),
        CapabilityUtil.makeProvider(BotaniaForgeCapabilities.MANA_RECEIVER, (ManaReceiver) tile)
      );
      e.addCapability(
        prefix("wandable"),
        CapabilityUtil.makeProvider(BotaniaForgeCapabilities.WANDABLE, (Wandable) tile)
      );
    }
  }
}
