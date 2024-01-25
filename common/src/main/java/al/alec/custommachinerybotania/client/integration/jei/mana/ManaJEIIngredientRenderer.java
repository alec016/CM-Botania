package al.alec.custommachinerybotania.client.integration.jei.mana;

import al.alec.custommachinerybotania.client.integration.jei.CustomIngredientTypes;
import al.alec.custommachinerybotania.guielement.*;
import com.mojang.blaze3d.vertex.*;
import fr.frinn.custommachinery.api.integration.jei.*;
import fr.frinn.custommachinery.client.*;
import fr.frinn.custommachinery.common.util.*;
import java.util.*;
import mezz.jei.api.ingredients.*;
import net.minecraft.client.gui.*;
import net.minecraft.network.chat.*;
import net.minecraft.world.item.*;
import org.jetbrains.annotations.*;

public class ManaJEIIngredientRenderer extends JEIIngredientRenderer<Mana, ManaGuiElement> {

    public ManaJEIIngredientRenderer(ManaGuiElement element) {
        super(element);
    }

    @Override
    public IIngredientType<Mana> getType() {
        return CustomIngredientTypes.MANA;
    }

    @Override
    public int getWidth() {
        return this.element.getWidth() - 2;
    }

    @Override
    public int getHeight() {
        return this.element.getHeight() - 2;
    }

    @Override
    public void render(PoseStack matrix, @Nullable Mana ingredient) {
        int width = this.element.getWidth();
        int height = this.element.getHeight();

        ClientHandler.bindTexture(this.element.getFilledTexture());
        GuiComponent.blit(matrix, -1, -1,0, 0, width, height, width, height);
    }

    @Override
    public List<Component> getTooltip(Mana ingredient, TooltipFlag iTooltipFlag) {
        List<Component> tooltips = new ArrayList<>();
        String amount = Utils.format(ingredient.getAmount());
        if(ingredient.isPerTick())
            tooltips.add(Component.translatable("custommachinerybotania.jei.ingredient.mana.pertick", amount));
        else
            tooltips.add(Component.translatable("custommachinerybotania.jei.ingredient.mana", amount));
        return tooltips;
    }
}
