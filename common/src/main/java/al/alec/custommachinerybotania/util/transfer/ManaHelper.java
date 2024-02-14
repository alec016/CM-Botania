package al.alec.custommachinerybotania.util.transfer;


import al.alec.custommachinerybotania.components.ManaMachineComponent;
import fr.frinn.custommachinery.common.component.ItemMachineComponent;
import net.minecraft.world.item.ItemStack;
import vazkii.botania.xplat.XplatAbstractions;

public class ManaHelper implements IManaHelper {
  public static final ManaHelper INSTANCE = new ManaHelper();
  private static final int DEFAULT_TRANSFER = 1000;
  @Override
  public boolean isManaHandler(ItemStack stack) {
    return XplatAbstractions.INSTANCE.findManaItem(stack) != null;
  }

  @Override
  public void fillBufferFromStack(ManaMachineComponent buffer, ItemMachineComponent stack) {
    var manaItem = XplatAbstractions.INSTANCE.findManaItem(stack.getItemStack());
    assert manaItem != null;
    if (manaItem.getMana() >= DEFAULT_TRANSFER) {
      int received = buffer.receiveMana(DEFAULT_TRANSFER, true);
      if (received > 0) {
        buffer.receiveMana(received, false);
        manaItem.addMana(-received);
      }
    } else {
      int transfer = manaItem.getMana();
      if (transfer > 0) {
        int received = buffer.receiveMana(transfer, true);
        if (received > 0) {
          buffer.receiveMana(received, false);
          manaItem.addMana(-received);
        }
      }
    }
  }

  @Override
  public void fillStackFromBuffer(ItemMachineComponent stack, ManaMachineComponent buffer) {
    var manaItem = XplatAbstractions.INSTANCE.findManaItem(stack.getItemStack());
    assert manaItem != null;
    if (buffer.getMana() >= DEFAULT_TRANSFER) {
      int received = buffer.extractMana(DEFAULT_TRANSFER, true);
      if (received > 0) {
        buffer.extractMana(received, false);
        manaItem.addMana(received);
      }
    } else {
      int transfer = buffer.getMana();
      if (transfer > 0) {
        int received = buffer.extractMana(transfer, true);
        if (received > 0) {
          buffer.extractMana(received, false);
          manaItem.addMana(received);
        }
      }
    }
  }
}
