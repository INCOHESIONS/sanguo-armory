package io.github.incohesions.sanguo_armory.registry.item.utils

import io.github.incohesions.sanguo_armory.SanguoArmory
import io.github.incohesions.sanguo_armory.registry.core.ConfigureItem
import net.minecraft.item.Item
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.registry.RegistryKey
import net.minecraft.registry.RegistryKeys

inline fun <T : Item> factory(id: String, factory: (Item.Settings) -> T): T {
    val key = RegistryKey.of(RegistryKeys.ITEM, SanguoArmory.id(id))
    return Registry.register(Registries.ITEM, key, factory(Item.Settings().registryKey(key)))
}

inline fun <reified T : Item> withType(id: String, configure: ConfigureItem = { it }): T =
    factory(id) { T::class.constructors.first().call(configure(it)) }
