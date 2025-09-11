package io.github.incohesions.sanguo_armory.utils

import net.minecraft.component.ComponentType
import net.minecraft.entity.damage.DamageSource
import net.minecraft.entity.damage.DamageTypes
import net.minecraft.item.ItemStack
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable
import java.lang.Boolean as JBoolean

object Utils {
    @JvmStatic
    fun checkBool(stack: ItemStack, type: ComponentType<Boolean>): JBoolean =
        JBoolean.TRUE.equals(stack.getComponents().get(type)) as JBoolean

    @JvmStatic
    fun cancelIf(cir: CallbackInfoReturnable<Boolean>, b: JBoolean) =
        if (b.booleanValue()) cir.cancel() else Unit

    @JvmStatic
    fun isAnyExplosion(source: DamageSource): JBoolean =
        arrayOf(DamageTypes.EXPLOSION, DamageTypes.PLAYER_EXPLOSION).any(source::isOf) as JBoolean
}
