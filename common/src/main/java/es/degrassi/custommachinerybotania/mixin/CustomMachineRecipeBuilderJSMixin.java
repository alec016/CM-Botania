package es.degrassi.custommachinerybotania.mixin;

import es.degrassi.custommachinerybotania.integration.kubejs.ManaRequirementJS;
import es.degrassi.custommachinerybotania.integration.kubejs.ManaRequirementPerTickJS;
import fr.frinn.custommachinery.common.integration.kubejs.CustomMachineRecipeBuilderJS;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ CustomMachineRecipeBuilderJS.class})
public abstract class CustomMachineRecipeBuilderJSMixin implements ManaRequirementJS, ManaRequirementPerTickJS {
}