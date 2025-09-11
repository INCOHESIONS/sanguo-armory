package io.github.incohesions.sanguo_armory.registry.core

import io.github.incohesions.sanguo_armory.registry.component.ComponentRegistry
import io.github.incohesions.sanguo_armory.extensions.flatMapArray
import io.github.incohesions.sanguo_armory.registry.tag.TagRegistry

interface IRegistry {
    companion object : IRegistryProvider {
        private val providers = arrayOf(
            IItemRegistry, ISpecialRegistry, IRegistryProvider { arrayOf(ComponentRegistry, TagRegistry) }
        )

        override fun registries(): Array<out IRegistry> = providers.flatMapArray { it.registries() }
    }
}