package io.github.incohesions.sanguo_armory.components

import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import net.minecraft.component.ComponentType
import net.minecraft.network.RegistryByteBuf
import net.minecraft.network.codec.PacketCodec
import net.minecraft.network.codec.PacketCodecs
import net.minecraft.util.Identifier

@JvmRecord
data class HeldEffectComponent(
    val effectId: Identifier,
    val amplifier: Int
) {
    companion object {
        val CODEC: Codec<HeldEffectComponent> = RecordCodecBuilder.create { inst ->
            inst.group(
                Identifier.CODEC.fieldOf("effect").forGetter { it.effectId },
                Codec.INT.optionalFieldOf("amplifier", 0).forGetter { it.amplifier }
            ).apply(inst, ::HeldEffectComponent)
        }

        val PACKET_CODEC: PacketCodec<RegistryByteBuf, HeldEffectComponent> = PacketCodec.tuple(
            Identifier.PACKET_CODEC, HeldEffectComponent::effectId,
            PacketCodecs.VAR_INT, HeldEffectComponent::amplifier,
            ::HeldEffectComponent
        )

        val TYPE: ComponentType<HeldEffectComponent> = ComponentType.builder<HeldEffectComponent>()
            .codec(CODEC)
            .packetCodec(PACKET_CODEC)
            .build()
    }
}