package io.github.incohesions.sanguo_armory.registry.core

fun interface IRegistryProvider {
    fun registries(): Array<out IRegistry>
}