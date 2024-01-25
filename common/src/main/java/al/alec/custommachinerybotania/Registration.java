package al.alec.custommachinerybotania;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.Registries;
import dev.architectury.registry.registries.RegistrySupplier;
import fr.frinn.custommachinery.api.ICustomMachineryAPI;
import fr.frinn.custommachinery.api.component.MachineComponentType;
import fr.frinn.custommachinery.api.guielement.GuiElementType;
import fr.frinn.custommachinery.api.requirement.RequirementType;

public class Registration {
  public static final Registries REGISTRIES = Registries.get(CustomMachineryBotania.MODID);
  public static final DeferredRegister<GuiElementType<?>> GUI_ELEMENTS = DeferredRegister.create(ICustomMachineryAPI.INSTANCE.modid(), GuiElementType.REGISTRY_KEY);
  public static final DeferredRegister<MachineComponentType<?>> MACHINE_COMPONENTS = DeferredRegister.create(ICustomMachineryAPI.INSTANCE.modid(), MachineComponentType.REGISTRY_KEY);
  public static final DeferredRegister<RequirementType<?>> REQUIREMENTS = DeferredRegister.create(ICustomMachineryAPI.INSTANCE.modid(), RequirementType.REGISTRY_KEY);

}
