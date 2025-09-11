package io.github.incohesions.sanguo_armory.registry.core.extensions

import io.github.incohesions.sanguo_armory.SanguoArmory
import net.minecraft.registry.Registry
import net.minecraft.registry.RegistryKey
import net.minecraft.registry.tag.TagKey

fun <T, V : T> Registry<T>.register(id: String, value: V): V = Registry.register(
    this, RegistryKey.of(key, SanguoArmory.id(id)), value
)

fun <T> RegistryKey<out Registry<T>>.tag(id: String): TagKey<T> = TagKey.of(this, SanguoArmory.id(id))

fun <T> RegistryKey<Registry<T>>.of(id: String): RegistryKey<T> = RegistryKey.of(this, SanguoArmory.id(id))
