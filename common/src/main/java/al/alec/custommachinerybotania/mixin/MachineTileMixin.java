package al.alec.custommachinerybotania.mixin;

import al.alec.custommachinerybotania.Registration;
import al.alec.custommachinerybotania.components.*;
import fr.frinn.custommachinery.api.component.*;
import fr.frinn.custommachinery.api.machine.*;
import fr.frinn.custommachinery.common.component.*;
import fr.frinn.custommachinery.common.init.*;
import java.util.*;
import net.minecraft.core.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.*;
import org.jetbrains.annotations.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import vazkii.botania.api.*;
import vazkii.botania.api.block.*;
import vazkii.botania.api.internal.*;
import vazkii.botania.api.mana.*;
import vazkii.botania.common.handler.*;

@Mixin({ CustomMachineTile.class })
public abstract class MachineTileMixin extends MachineTile implements ManaPool, Wandable {

  @Shadow
  public abstract MachineComponentManager getComponentManager();

  @Unique
  private int cmb$ticks = 0;
  @Unique
  private boolean cmb$sendPacket = false;

  public MachineTileMixin(BlockPos pos, BlockState blockState) {
    super(fr.frinn.custommachinery.common.init.Registration.CUSTOM_MACHINE_TILE.get(), pos, blockState);
  }

  @Override
  public void setRemoved() {
    super.setRemoved();
    if (!ManaNetworkHandler.instance.isPoolIn(level, this) && !isRemoved()) {
      BotaniaAPI.instance().getManaNetworkInstance().fireManaNetworkEvent(this, ManaBlockType.POOL, ManaNetworkAction.REMOVE);
    }
  }

  @Unique
  private void cmb$initManaNetwork() {
    if (!ManaNetworkHandler.instance.isPoolIn(level, this) && !isRemoved()) {
      BotaniaAPI.instance().getManaNetworkInstance().fireManaNetworkEvent(this, ManaBlockType.POOL, ManaNetworkAction.ADD);
    }
  }

  @Inject(method="clientTick", at=@At("HEAD"))
  private static void clientTick(Level level, BlockPos pos, BlockState state, CustomMachineTile self, CallbackInfo ci) {
    ((MachineTileMixin) (Object) self).cmb$initManaNetwork();
  }

  @Inject(method="serverTick", at=@At("HEAD"))
  private static void serverTick(Level level, BlockPos pos, BlockState state, CustomMachineTile self, CallbackInfo ci) {
    ((MachineTileMixin) (Object) self).cmb$initManaNetwork();

    if (((MachineTileMixin) (Object) self).cmb$sendPacket && ((MachineTileMixin) (Object) self).cmb$ticks % 10 == 0) {
      VanillaPacketDispatcher.dispatchTEToNearbyPlayers(self);
      ((MachineTileMixin) (Object) self).cmb$sendPacket = false;
    }

    ((MachineTileMixin) (Object) self).cmb$ticks++;
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
    return this.getComponentManager()
      .getComponent(Registration.MANA_MACHINE_COMPONENT.get())
      .map(ManaMachineComponent::isFull)
      .orElse(false);
  }

  @Override
  public void receiveMana(int mana) {
    if (mana >= 0) {
      this.getComponentManager()
        .getComponent(Registration.MANA_MACHINE_COMPONENT.get())
        .map(component -> component.receiveMana(mana));
      this.getComponentManager().markDirty();
    } else {
      this.getComponentManager()
        .getComponent(Registration.MANA_MACHINE_COMPONENT.get())
        .map(component -> component.extractMana(-mana));
      this.getComponentManager().markDirty();
    }
  }

  @Override
  public boolean canReceiveManaFromBursts() {
    return this.getComponentManager()
      .getComponent(Registration.MANA_MACHINE_COMPONENT.get())
      .map(ManaMachineComponent::getMode)
      .map(ComponentIOMode::isInput)
      .orElse(false);
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
    return this.getComponentManager()
      .getComponent(Registration.MANA_MACHINE_COMPONENT.get())
      .map(ManaMachineComponent::getCapacity)
      .orElse(0);
  }

  @Override
  public boolean onUsedByWand(@Nullable Player player, ItemStack stack, Direction side) {
    if (player == null || player.isShiftKeyDown()) {
      this.getComponentManager()
        .getComponent(Registration.MANA_MACHINE_COMPONENT.get())
        .map(component -> switch (component.getMode()) {
          case INPUT -> component.setMode(ComponentIOMode.OUTPUT);
          case OUTPUT -> component.setMode(ComponentIOMode.BOTH);
          case BOTH -> component.setMode(ComponentIOMode.NONE);
          case NONE -> component.setMode(ComponentIOMode.INPUT);
        });
      this.getComponentManager().markDirty();
      VanillaPacketDispatcher.dispatchTEToNearbyPlayers(this);
    }
    return true;
  }
}
