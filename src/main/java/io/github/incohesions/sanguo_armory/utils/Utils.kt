package io.github.incohesions.sanguo_armory.utils

import net.minecraft.component.ComponentType
import net.minecraft.item.ItemStack
import java.lang.Boolean

object Utils {
    @JvmStatic
    fun checkComponent(stack: ItemStack, type: ComponentType<Boolean>): Boolean {
        return Boolean.TRUE.equals(stack.getComponents().get(type)) as Boolean
    }
}