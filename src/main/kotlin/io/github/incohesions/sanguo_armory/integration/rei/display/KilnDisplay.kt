package io.github.incohesions.sanguo_armory.integration.rei.display

import com.mojang.serialization.codecs.RecordCodecBuilder
import io.github.incohesions.sanguo_armory.integration.rei.SanguoArmoryREICommon
import me.shedaniel.rei.api.common.category.CategoryIdentifier
import me.shedaniel.rei.api.common.display.Display
import me.shedaniel.rei.api.common.display.DisplaySerializer
import me.shedaniel.rei.api.common.entry.EntryIngredient
import net.minecraft.network.codec.PacketCodec
import net.minecraft.util.Identifier
import java.util.*

class KilnDisplay(val input: EntryIngredient, val output: EntryIngredient) : Display {
    private val serializer: DisplaySerializer<KilnDisplay> = DisplaySerializer.of(
        RecordCodecBuilder.mapCodec { inst ->
            inst.group(
                EntryIngredient.codec().fieldOf("input").forGetter { it.inputEntries[0] },
                EntryIngredient.codec().fieldOf("output").forGetter { it.outputEntries[0] },
            ).apply(inst, ::KilnDisplay)
        },
        PacketCodec.tuple(
            EntryIngredient.streamCodec(), { it.inputEntries[0] },
            EntryIngredient.streamCodec(), { it.outputEntries[0] },
            ::KilnDisplay
        )
    )

    override fun getInputEntries(): MutableList<EntryIngredient> = mutableListOf(input)

    override fun getOutputEntries(): MutableList<EntryIngredient> = mutableListOf(output)

    override fun getCategoryIdentifier(): CategoryIdentifier<KilnDisplay> = SanguoArmoryREICommon.kilnDisplayCategory

    override fun getDisplayLocation(): Optional<Identifier> = Optional.empty()

    override fun getSerializer(): DisplaySerializer<out Display> = serializer
}