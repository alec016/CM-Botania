package al.alec.custommachinerybotania.mixin;

import fr.frinn.custommachinery.common.init.CustomMachineTile;
import fr.frinn.custommachinery.common.init.CustomMachineItem;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.At;
import vazkii.botania.api.block.WandHUD;
import vazkii.botania.common.block.block_entity.mana.ManaSpreaderBlockEntity;
import vazkii.botania.common.block.block_entity.mana.ManaSpreaderBlockEntity.WandHud;

@Mixin(value = WandHud.class, remap = false)
public abstract class ManaSpreaderBlockEntity$WandHudMixin implements WandHUD {
  @Final
  @Shadow
  private ManaSpreaderBlockEntity spreader;

  @ModifyVariable(method = "renderHUD", at = @At(value = "STORE"), ordinal = 1)
  private ItemStack cmbotania$replaceItemByMachineItem(@NotNull ItemStack recieverStack) {
    if(recieverStack.getItem() instanceof CustomMachineItem
      && this.spreader.getLevel() != null
      && this.spreader.getBinding() != null
      && this.spreader.getLevel().getBlockEntity(this.spreader.getBinding()) instanceof CustomMachineTile machine)
      return CustomMachineItem.makeMachineItem(machine.getId());
    return recieverStack;
  }
}
