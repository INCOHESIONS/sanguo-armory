package io.github.incohesions.sanguo_armory.registry.item.extensions

import io.github.incohesions.sanguo_armory.SanguoArmory
import net.minecraft.registry.Registry
import net.minecraft.registry.RegistryKey

fun <T, V : T> Registry<T>.register(id: String, value: V): V = Registry.register(
    this, RegistryKey.of(key, SanguoArmory.id(id)), value
)
