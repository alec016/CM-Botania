package al.alec.custommachinerybotania.client.integration.jei.mana;

import al.alec.custommachinerybotania.client.integration.jei.CustomIngredientTypes;
import al.alec.custommachinerybotania.client.render.element.ManaGuiElementWidget;
import al.alec.custommachinerybotania.guielement.ManaGuiElement;
import com.mojang.blaze3d.vertex.PoseStack;
import fr.frinn.custommachinery.api.integration.jei.JEIIngredientRenderer;
import fr.frinn.custommachinery.common.util.Utils;
import java.util.ArrayList;
import java.util.List;
import mezz.jei.api.ingredients.IIngredientType;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.TooltipFlag;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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
        return this.element.getWidth() - 4;
    }

    @Override
    public int getHeight() {
        return this.element.getHeight() - 2;
    }

    @Override
    public void render(@NotNull PoseStack matrix, @Nullable Mana ingredient) {
        int width = this.element.getWidth() - 4;
        int height = this.element.getHeight() - 2;

        ManaGuiElementWidget.renderMana(matrix, height, 0, 0, width, height);
    }

    @Override
    public @NotNull List<Component> getTooltip(Mana ingredient, @NotNull TooltipFlag iTooltipFlag) {
        List<Component> tooltips = new ArrayList<>();
        String amount = Utils.format(ingredient.getAmount());
        if(ingredient.isPerTick())
            tooltips.add(Component.translatable("custommachinerybotania.jei.ingredient.mana.pertick", amount));
        else
            tooltips.add(Component.translatable("custommachinerybotania.jei.ingredient.mana", amount));
        return tooltips;
    }
}
