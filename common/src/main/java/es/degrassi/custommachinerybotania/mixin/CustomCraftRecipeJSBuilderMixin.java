package es.degrassi.custommachinerybotania.mixin;

import es.degrassi.custommachinerybotania.integration.kubejs.ManaRequirementJS;
import fr.frinn.custommachinery.common.integration.kubejs.CustomCraftRecipeJSBuilder;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ CustomCraftRecipeJSBuilder.class})
public abstract class CustomCraftRecipeJSBuilderMixin implements ManaRequirementJS {
}
