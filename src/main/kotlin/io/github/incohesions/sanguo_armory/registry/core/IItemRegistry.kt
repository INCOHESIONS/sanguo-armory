package io.github.incohesions.sanguo_armory.registry.core

import io.github.incohesions.sanguo_armory.registry.block.BlockRegistry
import io.github.incohesions.sanguo_armory.registry.item.ArmorRegistry
import io.github.incohesions.sanguo_armory.registry.item.MaterialRegistry
import io.github.incohesions.sanguo_armory.registry.item.WeaponRegistry
import net.minecraft.item.Item

typealias ConfigureItem = (Item.Settings) -> Item.Settings

fun interface IItemRegistry : IRegistry {
    companion object : IRegistryProvider {
        override fun registries(): Array<IItemRegistry> =
            arrayOf(WeaponRegistry, MaterialRegistry, ArmorRegistry, BlockRegistry)
    }

    fun registerAll(): Array<Item>
}