package al.alec.custommachinerybotania.mixin;

import al.alec.custommachinerybotania.Registration;
import al.alec.custommachinerybotania.components.*;
import fr.frinn.custommachinery.api.component.*;
import fr.frinn.custommachinery.api.machine.*;
import fr.frinn.custommachinery.common.init.*;
import fr.frinn.custommachinery.common.util.*;
import java.util.*;
import java.util.concurrent.atomic.*;
import net.minecraft.core.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.*;
import net.minecraft.world.level.block.state.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import vazkii.botania.api.*;
import vazkii.botania.api.mana.*;
import vazkii.botania.common.handler.*;

@Mixin({ CustomMachineTile.class })
public abstract class MachineTileMixin extends MachineTile implements ManaPool {

  public MachineTileMixin(BlockEntityType<?> type, BlockPos pos, BlockState blockState) {
    super(type, pos, blockState);
  }

  @Override
  public void setRemoved() {
    super.setRemoved();
    BotaniaAPI.instance().getManaNetworkInstance().fireManaNetworkEvent(this, ManaBlockType.POOL, ManaNetworkAction.REMOVE);
  }

  @Unique
  private void cmb$initManaNetwork() {
    if (!ManaNetworkHandler.instance.isPoolIn(level, this) && !isRemoved()) {
      BotaniaAPI.instance().getManaNetworkInstance().fireManaNetworkEvent(this, ManaBlockType.POOL, ManaNetworkAction.ADD);
    }
  }

  @Inject(method="clientTick", at=@At("HEAD"))
  private static void clientTick(Level level, BlockPos pos, BlockState state, CustomMachineTile self, CallbackInfo ci) {
    MachineTileMixin tile = (MachineTileMixin) (Object) self;
    tile.cmb$initManaNetwork();
  }

  @Inject(method="serverTick", at=@At("HEAD"))
  private static void serverTick(Level level, BlockPos pos, BlockState state, CustomMachineTile self, CallbackInfo ci) {
    MachineTileMixin tile = (MachineTileMixin) (Object) self;
    tile.cmb$initManaNetwork();
  }

  @Override
  public Level getManaReceiverLevel() {
    return getLevel();
  }

  @Override
  public BlockPos getManaReceiverPos() {
    return getBlockPos();
  }

  @Override
  public int getCurrentMana() {
    return this.getComponentManager()
      .getComponent(Registration.MANA_MACHINE_COMPONENT.get())
      .map(ManaMachineComponent::getMana)
      .orElse(0);
  }

  @Override
  public boolean isFull() {
    AtomicBoolean full = new AtomicBoolean(true);
    this.getComponentManager()
      .getComponent(Registration.MANA_MACHINE_COMPONENT.get())
      .ifPresent(component -> full.set(component.getCapacity() != component.getMana()));
    return full.get();
  }

  @Override
  public void receiveMana(int mana) {
    this.getComponentManager()
      .getComponent(Registration.MANA_MACHINE_COMPONENT.get())
      .ifPresent(component -> {
        component.receiveMana(mana, false);
//        if (mana > 0) {
//          getManaReceiverLevel().blockEvent(tile.getBlockPos(), tile.getBlockState().getBlock(), SPARKLE_EVENT, 0);
//        }
      });
  }

  @Override
  public boolean canReceiveManaFromBursts() {
    return true;
  }

  @Override
  public boolean isOutputtingPower() {
    return this.getComponentManager()
      .getComponent(Registration.MANA_MACHINE_COMPONENT.get())
      .map(ManaMachineComponent::getMode)
      .map(ComponentIOMode::isOutput)
      .orElse(false);
  }

  @Override
  public Optional<DyeColor> getColor() {
    return Optional.empty();
  }

  @Override
  public void setColor(Optional<DyeColor> color) {}

  @Override
  public int getMaxMana() {
    CMLogger.INSTANCE.info("MachineTile$receiveMana");
    AtomicInteger capacity = new AtomicInteger();
    this.getComponentManager().getComponent(Registration.MANA_MACHINE_COMPONENT.get())
      .ifPresent(component -> capacity.set(component.getCapacity()));
    return capacity.get();
  }
}
