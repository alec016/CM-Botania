package al.alec.custommachinerybotania.components;

import al.alec.custommachinerybotania.client.integration.jei.mana.Mana;
import fr.frinn.custommachinery.api.codec.NamedCodec;
import al.alec.custommachinerybotania.Registration;
import fr.frinn.custommachinery.api.component.ComponentIOMode;
import fr.frinn.custommachinery.api.component.IComparatorInputComponent;
import fr.frinn.custommachinery.api.component.IDumpComponent;
import fr.frinn.custommachinery.api.component.IMachineComponent;
import fr.frinn.custommachinery.api.component.IMachineComponentManager;
import fr.frinn.custommachinery.api.component.IMachineComponentTemplate;
import fr.frinn.custommachinery.api.component.ISerializableComponent;
import fr.frinn.custommachinery.api.component.ITickableComponent;
import fr.frinn.custommachinery.api.component.MachineComponentType;
import fr.frinn.custommachinery.api.network.ISyncable;
import fr.frinn.custommachinery.api.network.ISyncableStuff;
import fr.frinn.custommachinery.common.network.syncable.IntegerSyncable;
import fr.frinn.custommachinery.common.network.syncable.StringSyncable;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.function.Consumer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.mana.ManaBlockType;
import vazkii.botania.api.mana.ManaNetworkAction;
import vazkii.botania.api.mana.ManaPool;

public class ManaMachineComponent implements IMachineComponent, ITickableComponent, ISerializableComponent, ISyncableStuff, IComparatorInputComponent, IDumpComponent {
  private int mana;
  private final int capacity, maxIn, maxOut;

  private final IMachineComponentManager manager;
  private ComponentIOMode mode;

  public ManaMachineComponent(IMachineComponentManager manager) {
    this(manager, ComponentIOMode.BOTH, 1, 0, 0);
  }

  public ManaMachineComponent(IMachineComponentManager manager, ComponentIOMode mode, int capacity, int maxInput, int maxOutput) {
    this.manager = manager;
    this.mode = mode;
    this.capacity = capacity;
    this.maxIn = maxInput;
    this.maxOut = maxOutput;
  }

  @Override
  public void init() {
    BotaniaAPI.instance().getManaNetworkInstance().fireManaNetworkEvent(
      (ManaPool) getManager().getTile(), ManaBlockType.POOL, ManaNetworkAction.ADD
    );
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
    this.getManager().markDirty();
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
    nbt.putInt("mana", this.mana);
    nbt.putString("mode", this.mode.toString());
  }

  @Override
  public void deserialize(CompoundTag nbt) {
    if (nbt.contains("mana", Tag.TAG_INT))
      this.mana = Math.min(nbt.getInt("mana"), this.capacity);
    if (nbt.contains("mode", Tag.TAG_STRING))
      this.mode = ComponentIOMode.value(nbt.getString("mode"));
  }

  @Override
  public void getStuffToSync(Consumer<ISyncable<?, ?>> container) {
    container.accept(IntegerSyncable.create(() -> this.mana, mana -> this.mana = mana));
    container.accept(StringSyncable.create(() -> this.getMode().toString().toLowerCase(Locale.ENGLISH), modeS -> this.mode = ComponentIOMode.value(modeS)));
  }
  @Override
  public void dump(List<String> ids) {
    setMana(0);
    this.getManager().markDirty();
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

  public static class Template implements IMachineComponentTemplate<ManaMachineComponent> {

    public static final NamedCodec<Template> CODEC = NamedCodec.record(templateInstance ->
      templateInstance.group(
        NamedCodec.enumCodec(ComponentIOMode.class).optionalFieldOf("mode", ComponentIOMode.BOTH).forGetter(template -> template.mode),
        NamedCodec.intRange(1, Integer.MAX_VALUE).fieldOf("capacity").forGetter(template -> template.capacity),
        NamedCodec.intRange(0, Integer.MAX_VALUE).optionalFieldOf("maxInput").forGetter(template -> Optional.of(template.maxInput)),
        NamedCodec.intRange(0, Integer.MAX_VALUE).optionalFieldOf("maxOutput").forGetter(template -> Optional.of(template.maxOutput))
      ).apply(templateInstance, (mode, capacity, maxIn, maxOut) -> new Template(mode, capacity, maxIn.orElse(capacity), maxOut.orElse(capacity))), "Mana machine component"
    );

    private final int capacity, maxInput, maxOutput;
    private final ComponentIOMode mode;

    private Template(ComponentIOMode mode, int capacity, int maxInput, int maxOutput) {
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
      return new ManaMachineComponent(manager, this.mode, this.capacity, this.maxInput, this.maxOutput);
    }
  }
}
