package al.alec.custommachinerybotania.guielement;

import al.alec.custommachinerybotania.CustomMachineryBotania;
import al.alec.custommachinerybotania.Registration;
import al.alec.custommachinerybotania.components.ManaMachineComponent;
import fr.frinn.custommachinery.api.codec.NamedCodec;
import fr.frinn.custommachinery.api.component.MachineComponentType;
import fr.frinn.custommachinery.api.guielement.IComponentGuiElement;
import fr.frinn.custommachinery.api.guielement.GuiElementType;
import fr.frinn.custommachinery.impl.guielement.AbstractTexturedGuiElement;
import fr.frinn.custommachinery.impl.guielement.AbstractGuiElement;
import net.minecraft.resources.ResourceLocation;

public class ManaGuiElement extends AbstractTexturedGuiElement implements IComponentGuiElement<ManaMachineComponent> {
  private static final ResourceLocation BASE_MANA_STORAGE_EMPTY_TEXTURE = new ResourceLocation(CustomMachineryBotania.MODID, "textures/gui/base_mana_storage_empty.png");

  public static final NamedCodec<ManaGuiElement> CODEC = NamedCodec.record(manaGuiElement ->
    manaGuiElement.group(
      makePropertiesCodec().forGetter(AbstractGuiElement::getProperties),
      NamedCodec.BOOL.optionalFieldOf("highlight", true).forGetter(ManaGuiElement::highlight)
    ).apply(manaGuiElement, ManaGuiElement::new),
    "Mana gui element"
  );
  private final boolean highlight;

  public ManaGuiElement(Properties properties, boolean highlight) {
    super(properties, BASE_MANA_STORAGE_EMPTY_TEXTURE);
    this.highlight = highlight;
  }

  public ResourceLocation getEmptyTexture() {
    return BASE_MANA_STORAGE_EMPTY_TEXTURE;
  }

  public boolean highlight() {
    return this.highlight;
  }

  @Override
  public MachineComponentType<ManaMachineComponent> getComponentType() {
    return Registration.MANA_MACHINE_COMPONENT.get();
  }

  @Override
  public String getID() {
    return "";
  }

  @Override
  public GuiElementType<ManaGuiElement> getType() {
    return Registration.MANA_GUI_ELEMENT.get();
  }
}
