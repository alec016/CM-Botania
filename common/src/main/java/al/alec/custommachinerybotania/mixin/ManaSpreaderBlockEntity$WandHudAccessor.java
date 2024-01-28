package al.alec.custommachinerybotania.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import vazkii.botania.common.block.block_entity.mana.ManaSpreaderBlockEntity;
import vazkii.botania.common.block.block_entity.mana.ManaSpreaderBlockEntity.WandHud;

@Mixin({ WandHud.class })
public interface ManaSpreaderBlockEntity$WandHudAccessor {

  @Accessor(value = "spreader", remap = false)
  ManaSpreaderBlockEntity getSpreader();
}
