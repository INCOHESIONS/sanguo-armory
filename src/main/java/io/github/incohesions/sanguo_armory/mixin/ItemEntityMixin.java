package io.github.incohesions.sanguo_armory.mixin;

import io.github.incohesions.sanguo_armory.SanguoRegistry;
import io.github.incohesions.sanguo_armory.utils.Utils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemEntity.class)
public abstract class ItemEntityMixin extends Entity {
    public ItemEntityMixin(final EntityType<?> type, final World world) { super(type, world); }

    @Shadow
    public abstract ItemStack getStack();

    @Inject(at = @At("HEAD"), method = "damage", cancellable = true)
    final void damage(final ServerWorld world, final DamageSource source, final float amount, final CallbackInfoReturnable<Boolean> cir) {
        final var immuneToAnvils = Utils.checkComponent(getStack(), SanguoRegistry.getImmuneToAnvilsComponent());
        final var immuneToCacti = Utils.checkComponent(getStack(), SanguoRegistry.getImmuneToCactiComponent());

        if (
            immuneToAnvils && source == world.getDamageSources().fallingAnvil(source.getAttacker()) ||
            immuneToCacti && source == world.getDamageSources().cactus()
        ) {
            cir.cancel();
        }
    }
}
