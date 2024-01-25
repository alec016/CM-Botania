package al.alec.custommachinerybotania.mixin;

import fr.frinn.custommachinery.common.integration.kubejs.CustomCraftRecipeJSBuilder;
import al.alec.custommachinerybotania.integration.kubejs.ManaRequirementJS;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ CustomCraftRecipeJSBuilder.class})
public abstract class CustomCraftRecipeJSBuilderMixin implements ManaRequirementJS {
}
