package io.github.incohesions.sanguo_armory.registry.block

import io.github.incohesions.sanguo_armory.block.kiln.KilnBlock
import io.github.incohesions.sanguo_armory.registry.core.extensions.of
import io.github.incohesions.sanguo_armory.registry.core.ConfigureItem
import io.github.incohesions.sanguo_armory.registry.core.IItemRegistry
import io.github.incohesions.sanguo_armory.registry.special.KilnRegistry
import net.minecraft.block.AbstractBlock
import net.minecraft.block.Block
import net.minecraft.block.MapColor
import net.minecraft.block.enums.NoteBlockInstrument
import net.minecraft.block.Blocks
import net.minecraft.item.BlockItem
import net.minecraft.item.Item
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.registry.RegistryKeys
import net.minecraft.sound.BlockSoundGroup

typealias ConfigureBlock = (AbstractBlock.Settings) -> AbstractBlock.Settings

object BlockRegistry : IItemRegistry {
    val kiln = register<KilnBlock>(KilnRegistry.KILN_ID) { it
        .luminance(Blocks.createLightLevelFromLitBlockState(13))
        .instrument(NoteBlockInstrument.BASEDRUM)
        .mapColor(MapColor.TERRACOTTA_LIGHT_GRAY)
        .sounds(BlockSoundGroup.MUD_BRICKS)
        .strength(3.5F)
        .requiresTool()
    }

    override fun registerAll(): Array<Item> = arrayOf(kiln)

    private inline fun <reified T : Block> register(
        id: String,
        configureBlock: ConfigureBlock,
        configureItem: ConfigureItem
    ): BlockItem {
        val itemKey = RegistryKeys.ITEM.of(id)
        val blockKey = RegistryKeys.BLOCK.of(id)

        val block = T::class.constructors.first().call(
            configureBlock(AbstractBlock.Settings.create().registryKey(blockKey))
        )

        Registry.register(Registries.BLOCK, blockKey, block)

        return Registry.register(
            Registries.ITEM,
            itemKey,
            BlockItem(block, configureItem(Item.Settings().registryKey(itemKey)))
        )
    }

    private inline fun <reified T : Block> register(id: String, configureBlock: ConfigureBlock): BlockItem =
        register<T>(id, configureBlock) { it }
}