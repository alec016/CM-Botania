package es.degrassi.custommachinerybotania.util.transfer;

import es.degrassi.custommachinerybotania.components.ManaMachineComponent;
import fr.frinn.custommachinery.common.component.ItemMachineComponent;
import net.minecraft.world.item.ItemStack;

public interface IManaHelper {
  boolean isManaHandler(ItemStack stack);
  void fillBufferFromStack(ManaMachineComponent buffer, ItemMachineComponent stack);
  void fillStackFromBuffer(ItemMachineComponent stack, ManaMachineComponent buffer);
}
