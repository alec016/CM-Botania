package al.alec.custommachinerybotania.client.render;

import al.alec.custommachinerybotania.CustomMachineryBotania;
import al.alec.custommachinerybotania.Registration;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import fr.frinn.custommachinery.common.init.CustomMachineItem;
import fr.frinn.custommachinery.common.init.CustomMachineTile;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.lwjgl.opengl.GL11;
import vazkii.botania.api.BotaniaAPIClient;
import vazkii.botania.api.block.WandHUD;
import vazkii.botania.api.mana.ManaPool;
import vazkii.botania.client.core.helper.RenderHelper;
import vazkii.botania.common.item.BotaniaItems;
import vazkii.botania.common.item.ManaTabletItem;

public class CustomMachineTileWandHud implements WandHUD {
  private static final ResourceLocation manaBar = new ResourceLocation(CustomMachineryBotania.MODID, "textures/mana_hud.png");
  private final CustomMachineTile pool;

  public CustomMachineTileWandHud(CustomMachineTile pool) {
    this.pool = pool;
  }

  @Override
  public void renderHUD(PoseStack ms, Minecraft mc) {
    ItemStack poolStack = CustomMachineItem.makeMachineItem(pool.getId());
    String name = pool.getMachine().getName().getString();

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
      .map(component -> switch(component.getMode()) {
        case INPUT -> 0;
        case OUTPUT -> 22;
        case BOTH -> 44;
        case NONE -> 66;
      }).orElse(66);

    int arrowV = 38;
    RenderSystem.setShaderTexture(0, manaBar);
    RenderHelper.drawTexturedModalRect(ms, centerX - 11, centerY + 30, arrowU, arrowV, 22, 15);
    RenderSystem.setShaderColor(1F, 1F, 1F, 1F);

    ItemStack tablet = new ItemStack(BotaniaItems.manaTablet);
    ManaTabletItem.setStackCreative(tablet);

    mc.getItemRenderer().renderAndDecorateItem(tablet, centerX - 31, centerY + 30);
    mc.getItemRenderer().renderAndDecorateItem(poolStack, centerX + 15, centerY + 30);

    RenderSystem.disableBlend();
  }
}
