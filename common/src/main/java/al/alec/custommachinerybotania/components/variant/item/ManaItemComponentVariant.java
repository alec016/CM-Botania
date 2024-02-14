package al.alec.custommachinerybotania.components.variant.item;


import al.alec.custommachinerybotania.Registration;
import al.alec.custommachinerybotania.components.ManaMachineComponent;
import al.alec.custommachinerybotania.util.transfer.ManaHelper;
import fr.frinn.custommachinery.CustomMachinery;
import fr.frinn.custommachinery.api.codec.NamedCodec;
import fr.frinn.custommachinery.api.component.IMachineComponentManager;
import fr.frinn.custommachinery.api.component.variant.ITickableComponentVariant;
import fr.frinn.custommachinery.common.component.ItemMachineComponent;
import fr.frinn.custommachinery.impl.component.variant.ItemComponentVariant;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class ManaItemComponentVariant extends ItemComponentVariant implements ITickableComponentVariant<ItemMachineComponent> {

  public static final ManaItemComponentVariant INSTANCE = new ManaItemComponentVariant();
  public static final NamedCodec<ManaItemComponentVariant> CODEC = NamedCodec.unit(INSTANCE, "Mana item component");
  public static final ResourceLocation ID = new ResourceLocation(CustomMachinery.MODID, "mana");
  @Override
  public boolean canAccept(IMachineComponentManager manager, ItemStack stack) {
    return ManaHelper.INSTANCE.isManaHandler(stack);
  }

  @Override
  public ResourceLocation getId() {
    return ID;
  }

  @Override
  public NamedCodec<ManaItemComponentVariant> getCodec() {
    return CODEC;
  }

  @Override
  public void tick(ItemMachineComponent slot) {
    ItemStack stack = slot.getItemStack();
    if (stack.isEmpty() || !ManaHelper.INSTANCE.isManaHandler(stack) || slot.getManager().getComponent(Registration.MANA_MACHINE_COMPONENT.get()).isEmpty())
      return;

    ManaMachineComponent buffer = slot.getManager().getComponent(Registration.MANA_MACHINE_COMPONENT.get()).get();

    if (slot.getMode().isInput())
      ManaHelper.INSTANCE.fillBufferFromStack(buffer, slot);
    else if (slot.getMode().isOutput())
      ManaHelper.INSTANCE.fillStackFromBuffer(slot, buffer);
  }
}
