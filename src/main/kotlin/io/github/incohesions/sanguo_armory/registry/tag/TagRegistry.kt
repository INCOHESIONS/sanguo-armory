package io.github.incohesions.sanguo_armory.registry.tag

import io.github.incohesions.sanguo_armory.registry.core.IRegistry
import io.github.incohesions.sanguo_armory.registry.core.extensions.tag
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.registry.tag.TagKey

object TagRegistry : IRegistry {
    data class TagWrapper<T>(val registry: Registry<T>, val tag: TagKey<T>) {
        fun includes(value: T): Boolean = registry.getEntry(value).isIn(tag)
    }

    val immuneToViperLance = Registries.ENTITY_TYPE.tag("immune_to_viper_lance")

    fun <T> Registry<T>.tag(id: String): TagWrapper<T> = TagWrapper(this, key.tag(id))
}