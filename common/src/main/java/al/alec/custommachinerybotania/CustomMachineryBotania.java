package al.alec.custommachinerybotania;

import al.alec.custommachinerybotania.client.*;
import dev.architectury.utils.*;

public class CustomMachineryBotania {
  public static final String MODID = "custommachinerybotania";

  public static void init() {
    Registration.GUI_ELEMENTS.register();
    Registration.MACHINE_COMPONENTS.register();
    Registration.REQUIREMENTS.register();
    EnvExecutor.runInEnv(Env.CLIENT, () -> ClientHandler::clientInit);
  }
}
