package io.github.incohesions.sanguo_armory.items

import net.minecraft.entity.LivingEntity
import net.minecraft.item.Item
import net.minecraft.item.ItemStack

class FieryItem(settings: Settings) : Item(settings) {
    override fun postHit(stack: ItemStack, target: LivingEntity, attacker: LivingEntity) = target.setOnFireFor(5.0F)
}
