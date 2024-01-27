package al.alec.custommachinerybotania.components;

import al.alec.custommachinerybotania.client.integration.jei.mana.*;
import fr.frinn.custommachinery.api.codec.NamedCodec;
import fr.frinn.custommachinery.api.component.*;
import fr.frinn.custommachinery.api.network.*;
import fr.frinn.custommachinery.common.network.syncable.*;
import fr.frinn.custommachinery.impl.component.config.SideConfig;
import al.alec.custommachinerybotania.Registration;
import java.util.*;
import java.util.function.*;
import net.minecraft.nbt.*;
import vazkii.botania.api.*;
import vazkii.botania.api.mana.*;

public class ManaMachineComponent implements IMachineComponent, ITickableComponent, ISerializableComponent, ISyncableStuff, IComparatorInputComponent, ISideConfigComponent, IDumpComponent {
  private int mana;
  private final SideConfig config;
  private final int capacity, maxIn, maxOut;

  private final IMachineComponentManager manager;
  private ComponentIOMode mode;

  public ManaMachineComponent(IMachineComponentManager manager) {
    this(manager, ComponentIOMode.BOTH, SideConfig.Template.DEFAULT_ALL_BOTH, 10000, 10000, 10000);
  }

  public ManaMachineComponent(IMachineComponentManager manager, ComponentIOMode mode, SideConfig.Template config, int capacity, int maxInput, int maxOutput) {
    this.manager = manager;
    this.mode = mode;
    this.config = config.build(this);
    this.capacity = capacity;
    this.maxIn = maxInput;
    this.maxOut = maxOutput;
  }

  @Override
  public void init() {
//    if (!ManaNetworkHandler.instance.isPoolIn(getManager().getTile().getLevel(), (ManaPool) getManager().getTile()) && !getManager().getTile().isRemoved()) {
      BotaniaAPI.instance().getManaNetworkInstance().fireManaNetworkEvent(
        (ManaPool) getManager().getTile(), ManaBlockType.POOL, ManaNetworkAction.ADD
      );
//    }
  }

  @Override
  public void onRemoved() {
    BotaniaAPI.instance().getManaNetworkInstance().fireManaNetworkEvent(
      (ManaPool) getManager().getTile(), ManaBlockType.POOL, ManaNetworkAction.REMOVE
    );
  }

  @Override
  public ComponentIOMode getMode() {
    return this.mode;
  }

  public ComponentIOMode setMode(ComponentIOMode mode) {
    this.mode = mode;
    return mode;
  }

  @Override
  public IMachineComponentManager getManager() {
    return this.manager;
  }

  public int getMana () {
    return this.mana;
  }

  public void setMana (int mana) {
    this.mana = mana;
    getManager().markDirty();
  }

  public int receiveMana(int receive) {
    return receiveMana(receive, false);
  }

  public int receiveMana (int maxReceive, boolean simulate) {
    if (this.getMaxInput() <= 0) return 0;
    int manaReceived = Math.min(this.getCapacity() - this.getMana(), Math.min(this.getMaxInput(), maxReceive));
    if (!simulate && manaReceived > 0) {
      this.setMana(this.getMana() + manaReceived);
      this.getManager().markDirty();
    }
    return manaReceived;
  }

  public int extractMana(int extract) {
    return extractMana (extract, false);
  }

  public int extractMana (int maxExtract, boolean simulate) {
    if (this.getMaxOutput() <= 0) return 0;
    int manaExtracted = Math.min(this.getMana(), Math.min(this.getMaxOutput(), maxExtract));
    if (!simulate && manaExtracted > 0) {
      this.setMana(this.getMana() - manaExtracted);
      this.getManager().markDirty();
    }
    return manaExtracted;
  }

  @Override
  public void serialize(CompoundTag nbt) {
    nbt.putLong("mana", this.mana);
    nbt.put("config", this.config.serialize());
    nbt.putString("mode", this.mode.toString());
  }

  @Override
  public void deserialize(CompoundTag nbt) {
    if (nbt.contains("mana", Tag.TAG_INT))
      this.mana = Math.min(nbt.getInt("mana"), this.capacity);
    if (nbt.contains("config"))
      this.config.deserialize(nbt.get("config"));
    if (nbt.contains("mode", Tag.TAG_STRING))
      this.mode = ComponentIOMode.value(nbt.getString("mode"));
  }

  @Override
  public void getStuffToSync(Consumer<ISyncable<?, ?>> container) {
    container.accept(IntegerSyncable.create(() -> this.mana, mana -> this.mana = mana));
    container.accept(SideConfigSyncable.create(this::getConfig, this.config::set));
  }
  @Override
  public void dump(List<String> ids) {
    setMana(0);
  }

  @Override
  public int getComparatorInput() {
    return (int) (15 * ((double)this.mana / (double)this.capacity));
  }

  public double getFillPercent() {
    return (double)this.mana / this.capacity;
  }

  public boolean isFull() {
    return this.capacity == this.mana;
  }

  public int getCapacity () {
    return this.capacity;
  }

  public int getMaxInput () {
    return maxIn;
  }

  public int getMaxOutput () {
    return maxOut;
  }

  @Override
  public MachineComponentType<ManaMachineComponent> getType() {
    return Registration.MANA_MACHINE_COMPONENT.get();
  }

  @Override
  public SideConfig getConfig() {
    return this.config;
  }

  @Override
  public String getId() {
    return "mana";
  }

  public static class Template implements IMachineComponentTemplate<ManaMachineComponent> {

    public static final NamedCodec<Template> CODEC = NamedCodec.record(templateInstance ->
      templateInstance.group(
        SideConfig.Template.CODEC.optionalFieldOf("config", SideConfig.Template.DEFAULT_ALL_BOTH).forGetter(template -> template.config),
        NamedCodec.enumCodec(ComponentIOMode.class).optionalFieldOf("mode", ComponentIOMode.BOTH).forGetter(template -> template.mode),
        NamedCodec.intRange(1, Integer.MAX_VALUE).fieldOf("capacity").forGetter(template -> template.capacity),
        NamedCodec.intRange(0, Integer.MAX_VALUE).optionalFieldOf("maxInput").forGetter(template -> Optional.of(template.maxInput)),
        NamedCodec.intRange(0, Integer.MAX_VALUE).optionalFieldOf("maxOutput").forGetter(template -> Optional.of(template.maxOutput))
      ).apply(templateInstance, (config, mode, capacity, maxIn, maxOut) -> new Template(mode, config, capacity, maxIn.orElse(capacity), maxOut.orElse(capacity))), "Mana machine component"
    );

    private final int capacity, maxInput, maxOutput;

    private final SideConfig.Template config;
    private final ComponentIOMode mode;

    private Template(ComponentIOMode mode, SideConfig.Template config, int capacity, int maxInput, int maxOutput) {
      this.config = config;
      this.capacity = capacity;
      this.maxInput = maxInput;
      this.maxOutput = maxOutput;
      this.mode = mode;
    }

    @Override
    public MachineComponentType<ManaMachineComponent> getType() {
      return Registration.MANA_MACHINE_COMPONENT.get();
    }

    @Override
    public String getId() {
      return "";
    }

    @Override
    public boolean canAccept(Object ingredient, boolean isInput, IMachineComponentManager manager) {
      return ingredient instanceof Mana;
    }

    @Override
    public ManaMachineComponent build(IMachineComponentManager manager) {
      return new ManaMachineComponent(manager, this.mode, this.config, this.capacity, this.maxInput, this.maxOutput);
    }
  }
}
