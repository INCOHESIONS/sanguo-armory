package io.github.incohesions.sanguo_armory.item

import io.github.incohesions.sanguo_armory.registry.tag.TagRegistry
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.effect.StatusEffectInstance
import net.minecraft.entity.effect.StatusEffects
import net.minecraft.item.Item
import net.minecraft.item.ItemStack

class ViperLance(settings: Settings) : Item(settings) {
    override fun postHit(stack: ItemStack, target: LivingEntity, attacker: LivingEntity) {
        if (!TagRegistry.immuneToViperLance.includes(target.type)) {
            target.addStatusEffect(StatusEffectInstance(StatusEffects.POISON, 10, 2))
        }
    }
}