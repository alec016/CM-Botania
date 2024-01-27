package al.alec.custommachinerybotania.client.render;

import al.alec.custommachinerybotania.Registration;
import al.alec.custommachinerybotania.components.*;
import com.mojang.blaze3d.systems.*;
import com.mojang.blaze3d.vertex.*;
import fr.frinn.custommachinery.api.component.*;
import fr.frinn.custommachinery.common.init.*;
import net.minecraft.client.*;
import net.minecraft.world.item.*;
import org.lwjgl.opengl.*;
import vazkii.botania.api.*;
import vazkii.botania.api.block.*;
import vazkii.botania.api.mana.*;
import vazkii.botania.client.core.helper.*;
import vazkii.botania.client.gui.*;
import vazkii.botania.common.item.*;

public class CustomMachineTileWandHud implements WandHUD {
  private final CustomMachineTile pool;

  public CustomMachineTileWandHud(CustomMachineTile pool) {
    this.pool = pool;
  }

  @Override
  public void renderHUD(PoseStack ms, Minecraft mc) {
    ItemStack poolStack = new ItemStack(pool.getBlockState().getBlock());
    String name = poolStack.getHoverName().getString();

    int centerX = mc.getWindow().getGuiScaledWidth() / 2;
    int centerY = mc.getWindow().getGuiScaledHeight() / 2;

    int width = Math.max(102, mc.font.width(name)) + 4;

    RenderHelper.renderHUDBox(ms, centerX - width / 2, centerY + 8, centerX + width / 2, centerY + 48);

    BotaniaAPIClient.instance().drawSimpleManaHUD(ms, 0x0095FF, ((ManaPool) pool).getCurrentMana(), ((ManaPool) pool).getMaxMana(), name);

    RenderSystem.enableBlend();
    RenderSystem.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

    int arrowU = pool
      .getComponentManager()
      .getComponent(Registration.MANA_MACHINE_COMPONENT.get())
      .map(ManaMachineComponent::getMode)
      .map(ComponentIOMode::isOutput)
      .orElse(false) ? 22 : 0;
    int arrowV = 38;
    RenderSystem.setShaderTexture(0, HUDHandler.manaBar);
    RenderHelper.drawTexturedModalRect(ms, centerX - 11, centerY + 30, arrowU, arrowV, 22, 15);
    RenderSystem.setShaderColor(1F, 1F, 1F, 1F);

    ItemStack tablet = new ItemStack(BotaniaItems.manaTablet);
    ManaTabletItem.setStackCreative(tablet);

    mc.getItemRenderer().renderAndDecorateItem(tablet, centerX - 31, centerY + 30);
    mc.getItemRenderer().renderAndDecorateItem(poolStack, centerX + 15, centerY + 30);

    RenderSystem.disableBlend();
  }
}
