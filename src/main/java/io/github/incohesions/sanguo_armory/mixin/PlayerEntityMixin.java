package io.github.incohesions.sanguo_armory.mixin;

import io.github.incohesions.sanguo_armory.SanguoRegistry;
import io.github.incohesions.sanguo_armory.utils.Utils;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.registry.Registries;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {
    protected PlayerEntityMixin(final EntityType<? extends LivingEntity> entityType, final World world) { super(entityType, world); }

    @Final
    @Shadow
    @NotNull
    PlayerInventory inventory;

    @Inject(at = @At("HEAD"), method = "tick")
    final void tick(final CallbackInfo ci) {
        if (getWorld().isClient) return;

        final var component = inventory.getSelectedStack().getComponents().get(SanguoRegistry.getHeldEffectComponent());

        if (component != null) {
            final var key = Registries.STATUS_EFFECT.getEntry(component.effectId());
            if (key.isEmpty()) return;
            addStatusEffect(new StatusEffectInstance(key.get(), 20, component.amplifier()), null);
        }
    }

    @Inject(at = @At("HEAD"), method = "damage", cancellable = true)
    final void damage(final ServerWorld world, final DamageSource source, final float amount, final CallbackInfoReturnable<Boolean> cir) {
        if (
            Utils.checkComponent(inventory.getSelectedStack(), SanguoRegistry.getProtectsAgainstExplosions()) &&
            source == world.getDamageSources().explosion(null)
        ) {
            cir.cancel();
        }
    }
}
