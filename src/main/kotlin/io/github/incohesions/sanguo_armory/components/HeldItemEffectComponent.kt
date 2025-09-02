package io.github.incohesions.sanguo_armory.components

import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import net.minecraft.component.ComponentType
import net.minecraft.network.RegistryByteBuf
import net.minecraft.network.codec.PacketCodec
import net.minecraft.network.codec.PacketCodecs
import net.minecraft.util.Identifier

@JvmRecord
data class HeldItemEffectComponent(
    val effectId: Identifier,
    val amplifier: Int
) {
    companion object {
        val CODEC: Codec<HeldItemEffectComponent> = RecordCodecBuilder.create {
            it.group(
                Identifier.CODEC.fieldOf("effect").forGetter(HeldItemEffectComponent::effectId),
                Codec.INT.fieldOf("amplifier").forGetter(HeldItemEffectComponent::amplifier)
            ).apply(it, ::HeldItemEffectComponent)
        }

        val PACKET_CODEC: PacketCodec<RegistryByteBuf, HeldItemEffectComponent> = PacketCodec.tuple(
            Identifier.PACKET_CODEC, HeldItemEffectComponent::effectId,
            PacketCodecs.VAR_INT, HeldItemEffectComponent::amplifier,
            ::HeldItemEffectComponent
        )

        val TYPE: ComponentType<HeldItemEffectComponent> = ComponentType.builder<HeldItemEffectComponent>()
            .codec(CODEC)
            .packetCodec(PACKET_CODEC)
            .build()
    }
}