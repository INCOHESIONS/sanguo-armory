package io.github.incohesions.sanguo_armory.registry.core

import io.github.incohesions.sanguo_armory.registry.block.special.KilnRegistry

/*
  For registries that do more than one thing, because having Block Entity, Recipe Type, Recipe Serializer and
  Screen Handler registries and packages just for the Kiln block would be stupid.
*/
interface ISpecialRegistry : IRegistry {
    companion object : IRegistryProvider {
        override fun registries(): Array<IRegistry> = arrayOf(KilnRegistry)
    }
}