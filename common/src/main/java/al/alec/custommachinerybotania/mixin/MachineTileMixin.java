package al.alec.custommachinerybotania.mixin;

import al.alec.custommachinerybotania.Registration;
import al.alec.custommachinerybotania.components.*;
import fr.frinn.custommachinery.api.component.*;
import fr.frinn.custommachinery.api.machine.*;
import fr.frinn.custommachinery.common.init.*;
import java.util.*;
import net.minecraft.core.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.*;
import net.minecraft.world.level.block.state.*;
import org.jetbrains.annotations.*;
import org.spongepowered.asm.mixin.*;
import vazkii.botania.api.block.*;
import vazkii.botania.api.internal.*;
import vazkii.botania.api.mana.*;

@Mixin({ CustomMachineTile.class })
public abstract class MachineTileMixin extends MachineTile implements ManaPool, Wandable {
  public MachineTileMixin(BlockEntityType<?> type, BlockPos pos, BlockState state) {
    super(type, pos, state);
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
      VanillaPacketDispatcher.dispatchTEToNearbyPlayers((CustomMachineTile) (Object) this);
    }
    return true;
  }
}
