package io.github.incohesions.sanguo_armory.utils

import net.minecraft.component.ComponentType
import net.minecraft.entity.damage.DamageSource
import net.minecraft.entity.damage.DamageTypes
import net.minecraft.item.ItemStack
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable
import java.lang.Boolean

object Utils {
    @JvmStatic
    fun checkBool(stack: ItemStack, type: ComponentType<Boolean>): Boolean =
        Boolean.TRUE.equals(stack.getComponents().get(type)) as Boolean

    @JvmStatic
    fun cancelIf(cir: CallbackInfoReturnable<Boolean>, b: Boolean) =
        if (b.booleanValue()) cir.cancel() else Unit

    @JvmStatic
    fun isAnyExplosion(source: DamageSource): Boolean =
        (source.isOf(DamageTypes.PLAYER_EXPLOSION) || source.isOf(DamageTypes.EXPLOSION)) as Boolean
}
