package es.degrassi.custommachinerybotania.client.screen.creation.gui.buidler;

import es.degrassi.custommachinerybotania.Registration;
import es.degrassi.custommachinerybotania.guielement.ManaGuiElement;
import fr.frinn.custommachinery.api.guielement.GuiElementType;
import fr.frinn.custommachinery.client.screen.BaseScreen;
import fr.frinn.custommachinery.client.screen.creation.MachineEditScreen;
import fr.frinn.custommachinery.client.screen.creation.gui.GuiElementBuilderPopup;
import fr.frinn.custommachinery.client.screen.creation.gui.IGuiElementBuilder;
import fr.frinn.custommachinery.client.screen.creation.gui.MutableProperties;
import fr.frinn.custommachinery.client.screen.popup.PopupScreen;
import fr.frinn.custommachinery.impl.guielement.AbstractGuiElement;
import java.util.function.Consumer;
import net.minecraft.client.gui.components.Checkbox;
import net.minecraft.client.gui.components.StringWidget;
import net.minecraft.client.gui.layouts.GridLayout;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.Nullable;

public class ManaGuiElementBuilder implements IGuiElementBuilder<ManaGuiElement> {
  @Override
  public GuiElementType<ManaGuiElement> type() {
    return Registration.MANA_GUI_ELEMENT.get();
  }

  @Override
  public ManaGuiElement make(AbstractGuiElement.Properties properties, @Nullable ManaGuiElement from) {
    if (from != null)
      return new ManaGuiElement(properties, from.highlight());
    else
      return new ManaGuiElement(properties, true);
  }

  @Override
  public PopupScreen makeConfigPopup(MachineEditScreen parent, MutableProperties properties, @Nullable ManaGuiElement from, Consumer<ManaGuiElement> onFinish) {
    return new ManaGuiElementBuilderPopup(parent, properties, from, onFinish);
  }

  public static class ManaGuiElementBuilderPopup extends GuiElementBuilderPopup<ManaGuiElement> {
    private Checkbox highlight;

    public ManaGuiElementBuilderPopup(BaseScreen parent, MutableProperties properties, @Nullable ManaGuiElement from, Consumer<ManaGuiElement> onFinish) {
      super(parent, properties, from, onFinish);
    }

    @Override
    public ManaGuiElement makeElement() {
      return new ManaGuiElement(this.properties.build(), this.highlight.selected());
    }

    @Override
    public void addWidgets(GridLayout.RowHelper row) {
      this.addPriority(row);
      row.addChild(new StringWidget(Component.translatable("custommachinery.gui.creation.gui.highlight"), this.font));
      this.highlight = row.addChild(new Checkbox(0, 0, 20, 20, Component.translatable("custommachinery.gui.creation.gui.highlight"), this.baseElement == null || this.baseElement.highlight()));
    }
  }
}
