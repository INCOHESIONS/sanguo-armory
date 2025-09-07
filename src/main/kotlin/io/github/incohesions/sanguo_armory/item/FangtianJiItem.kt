package io.github.incohesions.sanguo_armory.item

import net.minecraft.entity.Entity
import net.minecraft.entity.EquipmentSlot
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.particle.DustParticleEffect
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.math.ColorHelper
import kotlin.math.cos
import kotlin.math.sin

class FangtianJiItem(settings: Settings) : Item(settings) {
    companion object {
        val particles = arrayOf(
            coloredDust(0, 0, 0),
            coloredDust(72, 72, 72),
            DustParticleEffect.DEFAULT
        )
        val stops = arrayOf(0, 90, 180, 270)

        const val DIST = 0.75
        const val SPEED = 2.0

        fun coloredDust(r: Int, g: Int, b: Int): DustParticleEffect =
            DustParticleEffect(ColorHelper.getArgb(r, g, b), 1.0F)
    }

    override fun inventoryTick(stack: ItemStack, world: ServerWorld, entity: Entity, slot: EquipmentSlot?) {
        super.inventoryTick(stack, world, entity, slot)

        if (entity !is PlayerEntity || !isHoldingItem(slot)) return

        stops.forEach { stop ->
            arrayOf(::sin, ::cos).forEach { func ->
                val angle = Math.toRadians((world.server.ticks * SPEED) % 360 + stop)
                val pos = entity.pos.add(cos(angle) * DIST, func(angle) + 1.0, sin(angle) * DIST)

                world.spawnParticles(
                    particles.random(),
                    pos.x, pos.y, pos.z,
                    1, // Count
                    0.0, 0.0, 0.0,
                    0.0 // Speed
                )
            }
        }
    }

    fun isHoldingItem(slot: EquipmentSlot?): Boolean = when (slot) {
        EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND -> true
        else -> false
    }
}