package es.degrassi.custommachinerybotania.client;


import es.degrassi.custommachinerybotania.client.integration.jei.element.ManaGuiElementJeiRenderer;
import es.degrassi.custommachinerybotania.client.render.element.ManaGuiElementWidget;
import dev.architectury.platform.Platform;
import es.degrassi.custommachinerybotania.Registration;
import es.degrassi.custommachinerybotania.client.screen.creation.component.builder.ManaComponentBuilder;
import es.degrassi.custommachinerybotania.client.screen.creation.gui.buidler.ManaGuiElementBuilder;
import fr.frinn.custommachinery.api.guielement.RegisterGuiElementWidgetSupplierEvent;
import fr.frinn.custommachinery.api.integration.jei.RegisterGuiElementJEIRendererEvent;
import fr.frinn.custommachinery.client.screen.creation.component.RegisterComponentBuilderEvent;
import fr.frinn.custommachinery.client.screen.creation.gui.RegisterGuiElementBuilderEvent;

public class ClientHandler {

  public static void clientInit() {
    RegisterGuiElementWidgetSupplierEvent.EVENT.register(ClientHandler::registerGuiElementWidgets);
    RegisterComponentBuilderEvent.EVENT.register(ClientHandler::registerMachineComponentBuilders);
    RegisterGuiElementBuilderEvent.EVENT.register(ClientHandler::registerGuiElementBuilders);
    RegisterGuiElementJEIRendererEvent.EVENT.register(ClientHandler::registerGuiElementJeiRenderers);
  }

  private static void registerMachineComponentBuilders(final RegisterComponentBuilderEvent event) {
    event.register(Registration.MANA_MACHINE_COMPONENT.get(), new ManaComponentBuilder());
  }
  private static void registerGuiElementBuilders(final RegisterGuiElementBuilderEvent event) {
    event.register(Registration.MANA_GUI_ELEMENT.get(), new ManaGuiElementBuilder());
  }

  private static void registerGuiElementWidgets(RegisterGuiElementWidgetSupplierEvent event) {
    event.register(Registration.MANA_GUI_ELEMENT.get(), ManaGuiElementWidget::new);
  }

  private static void registerGuiElementJeiRenderers(RegisterGuiElementJEIRendererEvent event) {
    event.register(Registration.MANA_GUI_ELEMENT.get(), new ManaGuiElementJeiRenderer());
  }
}
