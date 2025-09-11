package io.github.incohesions.sanguo_armory.registry.component

import com.mojang.serialization.Codec
import io.github.incohesions.sanguo_armory.component.HeldItemEffectComponent
import io.github.incohesions.sanguo_armory.registry.core.extensions.register
import io.github.incohesions.sanguo_armory.registry.core.IRegistry
import net.minecraft.component.ComponentType
import net.minecraft.network.codec.PacketCodecs
import net.minecraft.registry.Registries

object ComponentRegistry : IRegistry {
    @JvmStatic
    @get:JvmName("getHeldItemEffectComponent")
    val heldItemEffect = component("held_item_effect", HeldItemEffectComponent.TYPE)

    @JvmStatic
    @get:JvmName("getImmuneToCactiComponent")
    val immuneToCacti = component("immune_to_cacti", booleanComponent())

    @JvmStatic
    @get:JvmName("getImmuneToAnvilsComponent")
    val immuneToAnvils = component("immune_to_anvils", booleanComponent())

    @JvmStatic
    @get:JvmName("getProtectsAgainstExplosions")
    val protectsAgainstExplosions = component("protects_against_explosions", booleanComponent())

    fun <T> component(id: String, type: ComponentType<T>): ComponentType<T> =
        Registries.DATA_COMPONENT_TYPE.register(id, type)

    fun booleanComponent(): ComponentType<Boolean> = ComponentType.builder<Boolean>()
        .codec(Codec.BOOL)
        .packetCodec(PacketCodecs.BOOLEAN)
        .build()
}