package al.alec.custommachinerybotania;

import al.alec.custommachinerybotania.components.ManaMachineComponent;
import al.alec.custommachinerybotania.components.variant.item.*;
import al.alec.custommachinerybotania.guielement.*;
import al.alec.custommachinerybotania.requirements.*;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.Registries;
import dev.architectury.registry.registries.RegistrySupplier;
import fr.frinn.custommachinery.api.ICustomMachineryAPI;
import fr.frinn.custommachinery.api.component.MachineComponentType;
import fr.frinn.custommachinery.api.component.variant.*;
import fr.frinn.custommachinery.api.guielement.GuiElementType;
import fr.frinn.custommachinery.api.requirement.RequirementType;

public abstract class Registration {
  public static final Registries REGISTRIES = Registries.get(CustomMachineryBotania.MODID);
  public static final DeferredRegister<GuiElementType<?>> GUI_ELEMENTS = DeferredRegister.create(ICustomMachineryAPI.INSTANCE.modid(), GuiElementType.REGISTRY_KEY);
  public static final DeferredRegister<MachineComponentType<?>> MACHINE_COMPONENTS = DeferredRegister.create(ICustomMachineryAPI.INSTANCE.modid(), MachineComponentType.REGISTRY_KEY);
  public static final DeferredRegister<RequirementType<?>> REQUIREMENTS = DeferredRegister.create(ICustomMachineryAPI.INSTANCE.modid(), RequirementType.REGISTRY_KEY);

  public static final RegistrySupplier<GuiElementType<ManaGuiElement>> MANA_GUI_ELEMENT = GUI_ELEMENTS.register("mana", () -> GuiElementType.create(ManaGuiElement.CODEC));
  public static final RegistrySupplier<MachineComponentType<ManaMachineComponent>> MANA_MACHINE_COMPONENT = MACHINE_COMPONENTS.register("mana", () -> MachineComponentType.create(ManaMachineComponent.Template.CODEC));
  public static final RegistrySupplier<RequirementType<ManaRequirement>> MANA_REQUIREMENT = REQUIREMENTS.register("mana", () -> RequirementType.world(ManaRequirement.CODEC));
  public static final RegistrySupplier<RequirementType<ManaRequirementPerTick>> MANA_REQUIREMENT_PER_TICK = REQUIREMENTS.register("mana_per_tick", () -> RequirementType.world(ManaRequirementPerTick.CODEC));


  public static void registerComponentVariants(RegisterComponentVariantEvent event) {
    event.register(fr.frinn.custommachinery.common.init.Registration.ITEM_MACHINE_COMPONENT.get(), ManaItemComponentVariant.ID, ManaItemComponentVariant.CODEC);
  }
}
