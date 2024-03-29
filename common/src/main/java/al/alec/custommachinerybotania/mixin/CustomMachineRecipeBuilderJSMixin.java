package al.alec.custommachinerybotania.mixin;

import fr.frinn.custommachinery.common.integration.kubejs.CustomMachineRecipeBuilderJS;
import al.alec.custommachinerybotania.integration.kubejs.ManaRequirementJS;
import al.alec.custommachinerybotania.integration.kubejs.ManaRequirementPerTickJS;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ CustomMachineRecipeBuilderJS.class})
public abstract class CustomMachineRecipeBuilderJSMixin implements ManaRequirementJS, ManaRequirementPerTickJS {
}