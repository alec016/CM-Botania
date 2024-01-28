package al.alec.custommachinerybotania.mixin;

import com.mojang.blaze3d.vertex.*;
import fr.frinn.custommachinery.common.init.*;
import java.util.*;
import net.minecraft.client.*;
import net.minecraft.world.item.*;
import org.spongepowered.asm.mixin.*;
import vazkii.botania.api.*;
import vazkii.botania.api.block.*;
import vazkii.botania.client.core.helper.*;
import vazkii.botania.common.block.block_entity.mana.ManaSpreaderBlockEntity.WandHud;

@Mixin(WandHud.class)
public abstract class ManaSpreaderBlockEntity$WandHudMixin implements WandHUD {
  @Override
  public void renderHUD(PoseStack ms, Minecraft mc) {
    String spreaderName = new ItemStack(((ManaSpreaderBlockEntity$WandHudAccessor) this).getSpreader().getBlockState().getBlock()).getHoverName().getString();

    ItemStack lensStack = ((ManaSpreaderBlockEntity$WandHudAccessor) this).getSpreader().getItemHandler().getItem(0);
    ItemStack recieverStack;
    if (((ManaSpreaderBlockEntityAccessor) ((ManaSpreaderBlockEntity$WandHudAccessor) this).getSpreader()).getReceiver() instanceof CustomMachineTile tile) {
      recieverStack = CustomMachineItem.makeMachineItem(tile.getId());
    } else {
      recieverStack = ((ManaSpreaderBlockEntityAccessor) ((ManaSpreaderBlockEntity$WandHudAccessor) this).getSpreader()).getReceiver() == null ? ItemStack.EMPTY : new ItemStack(((ManaSpreaderBlockEntity$WandHudAccessor) this).getSpreader().getLevel().getBlockState(((ManaSpreaderBlockEntityAccessor) ((ManaSpreaderBlockEntity$WandHudAccessor) this).getSpreader()).getReceiver().getManaReceiverPos()).getBlock());
    }

    int width = 4 + Collections.max(Arrays.asList(
      102, // Mana bar width
      mc.font.width(spreaderName),
      RenderHelper.itemWithNameWidth(mc, lensStack),
      RenderHelper.itemWithNameWidth(mc, recieverStack)
    ));
    int height = 22 + (lensStack.isEmpty() ? 0 : 18) + (recieverStack.isEmpty() ? 0 : 18);

    int centerX = mc.getWindow().getGuiScaledWidth() / 2;
    int centerY = mc.getWindow().getGuiScaledHeight() / 2;
    RenderHelper.renderHUDBox(ms, centerX - width / 2, centerY + 8, centerX + width / 2, centerY + 8 + height);

    int color = ((ManaSpreaderBlockEntity$WandHudAccessor) this).getSpreader().getVariant().hudColor;
    BotaniaAPIClient.instance().drawSimpleManaHUD(ms, color, ((ManaSpreaderBlockEntity$WandHudAccessor) this).getSpreader().getCurrentMana(), ((ManaSpreaderBlockEntity$WandHudAccessor) this).getSpreader().getMaxMana(), spreaderName);
    RenderHelper.renderItemWithNameCentered(ms, mc, recieverStack, centerY + 30, color);
    RenderHelper.renderItemWithNameCentered(ms, mc, lensStack, centerY + (recieverStack.isEmpty() ? 30 : 48), color);
  }
}
