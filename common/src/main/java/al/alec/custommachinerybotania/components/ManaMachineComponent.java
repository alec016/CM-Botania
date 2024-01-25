package al.alec.custommachinerybotania.components;

import fr.frinn.custommachinery.api.codec.NamedCodec;
import fr.frinn.custommachinery.api.component.*;
import fr.frinn.custommachinery.api.network.*;
import fr.frinn.custommachinery.common.network.syncable.*;
import fr.frinn.custommachinery.impl.component.AbstractMachineComponent;
import fr.frinn.custommachinery.impl.component.config.SideConfig;
import al.alec.custommachinerybotania.Registration;
import java.util.*;
import java.util.function.*;
import net.minecraft.nbt.*;

public class ManaMachineComponent extends AbstractMachineComponent implements ITickableComponent, ISerializableComponent, ISyncableStuff, IComparatorInputComponent, ISideConfigComponent, IDumpComponent {
  private long mana;
  private final SideConfig config;
  private final long capacity, maxIn, maxOut;

  public ManaMachineComponent(IMachineComponentManager manager) {
    this(manager, SideConfig.Template.DEFAULT_ALL_BOTH, 10000L, 10000L, 10000L);
  }

  public ManaMachineComponent(IMachineComponentManager manager, SideConfig.Template config, long capacity, long maxInput, long maxOutput) {
    super(manager, ComponentIOMode.BOTH);
    this.config = config.build(this);
    this.capacity = capacity;
    this.maxIn = maxInput;
    this.maxOut = maxOutput;
  }

  public long getMana () {
    return this.mana;
  }

  public void setMana (long mana) {
    this.mana = mana;
    getManager().markDirty();
  }

  public long receiveMana (long maxReceive, boolean simulate) {
    if (this.getMaxInput() <= 0) return 0;
    long manaReceived = Math.min(this.getCapacity() - this.getMana(), Math.min(this.getMaxInput(), maxReceive));
    if (!simulate && manaReceived > 0) {
      this.setMana(this.getMana() + manaReceived);
      this.getManager().markDirty();
    }
    return manaReceived;
  }

  public long extractMana (long maxExtract, boolean simulate) {
    if (this.getMaxOutput() <= 0) return 0;
    long manaExtracted = Math.min(this.getMana(), Math.min(this.getMaxOutput(), maxExtract));
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
  }

  @Override
  public void deserialize(CompoundTag nbt) {
    if (nbt.contains("mana", Tag.TAG_LONG))
      this.mana = Math.min(nbt.getLong("mana"), this.capacity);
    if (nbt.contains("config"))
      this.config.deserialize(nbt.get("config"));
  }

  @Override
  public void getStuffToSync(Consumer<ISyncable<?, ?>> container) {
    container.accept(LongSyncable.create(() -> this.mana, mana -> this.mana = mana));
    container.accept(SideConfigSyncable.create(this::getConfig, this.config::set));
  }
  @Override
  public void dump(List<String> ids) {
    setMana(0L);
  }

  @Override
  public int getComparatorInput() {
    return (int) (15 * ((double)this.mana / (double)this.capacity));
  }

  public double getFillPercent() {
    return (double)this.mana / this.capacity;
  }

  public long getCapacity () {
    return this.capacity;
  }

  public long getMaxInput () {
    return maxIn;
  }

  public long getMaxOutput () {
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

  @Override
  public void serverTick() {
    this.getManager().markDirty();
  }

  @Override
  public void init() {
  }

  @Override
  public void onRemoved() {
  }

  public static class Template implements IMachineComponentTemplate<ManaMachineComponent> {

    public static final NamedCodec<Template> CODEC = NamedCodec.record(templateInstance ->
      templateInstance.group(
        SideConfig.Template.CODEC.optionalFieldOf("config", SideConfig.Template.DEFAULT_ALL_BOTH).forGetter(template -> template.config),
        NamedCodec.longRange(1, Long.MAX_VALUE).fieldOf("capacity").forGetter(template -> template.capacity),
        NamedCodec.longRange(0, Long.MAX_VALUE).optionalFieldOf("maxInput").forGetter(template -> Optional.of(template.maxInput)),
        NamedCodec.longRange(0, Long.MAX_VALUE).optionalFieldOf("maxOutput").forGetter(template -> Optional.of(template.maxOutput))
      ).apply(templateInstance, (config, capacity, maxIn, maxOut) -> new Template(config, capacity, maxIn.orElse(capacity), maxOut.orElse(capacity))), "Mana machine component"
    );

    private final long capacity, maxInput, maxOutput;

    private final SideConfig.Template config;

    private Template(SideConfig.Template config, long capacity, long maxInput, long maxOutput) {
      this.config = config;
      this.capacity = capacity;
      this.maxInput = maxInput;
      this.maxOutput = maxOutput;
    }

    @Override
    public MachineComponentType<ManaMachineComponent> getType() {
      return Registration.MANA_MACHINE_COMPONENT.get();
    }

    @Override
    public String getId() {
      return "contraption";
    }

    @Override
    public boolean canAccept(Object ingredient, boolean isInput, IMachineComponentManager manager) {
      return false;
    }

    @Override
    public ManaMachineComponent build(IMachineComponentManager manager) {
      return new ManaMachineComponent(manager, this.config, this.capacity, this.maxInput, this.maxOutput);
    }
  }
}
