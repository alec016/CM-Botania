package al.alec.custommachinerybotania.util.transfer;

import al.alec.custommachinerybotania.components.ManaMachineComponent;
import fr.frinn.custommachinery.common.component.ItemMachineComponent;
import net.minecraft.world.item.ItemStack;

public interface IManaHelper {
  boolean isManaHandler(ItemStack stack);
  void fillBufferFromStack(ManaMachineComponent buffer, ItemMachineComponent stack);
  void fillStackFromBuffer(ItemMachineComponent stack, ManaMachineComponent buffer);
}
