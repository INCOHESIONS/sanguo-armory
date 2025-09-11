package io.github.incohesions.sanguo_armory.integration.rei

import io.github.incohesions.sanguo_armory.block.kiln.KilnRecipe
import io.github.incohesions.sanguo_armory.block.kiln.KilnScreenHandler
import io.github.incohesions.sanguo_armory.integration.rei.category.KilnCategory
import io.github.incohesions.sanguo_armory.integration.rei.display.KilnDisplay
import io.github.incohesions.sanguo_armory.registry.block.BlockRegistry
import me.shedaniel.rei.api.client.plugins.REIClientPlugin
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry
import me.shedaniel.rei.api.client.registry.transfer.TransferHandlerRegistry
import me.shedaniel.rei.api.client.registry.transfer.simple.SimpleTransferHandler
import me.shedaniel.rei.api.common.util.EntryIngredients
import me.shedaniel.rei.api.common.util.EntryStacks
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment

@Environment(EnvType.CLIENT)
class SanguoArmoryREIClient : REIClientPlugin {
    override fun registerDisplays(registry: DisplayRegistry) {
        registry.beginFiller<_, KilnDisplay>(KilnRecipe::class.java).fill {
            KilnDisplay(EntryIngredients.ofIngredient(it.ingredient()), EntryIngredients.of(it.result))
        }
        registry.add(KilnDisplay::class)
    }

    override fun registerCategories(registry: CategoryRegistry) {
        registry.add(KilnCategory())
        registry.addWorkstations(SanguoArmoryREICommon.kilnDisplayCategory, EntryStacks.of(BlockRegistry.kiln))
    }

    @Suppress("UnstableApiUsage")
    override fun registerTransferHandlers(registry: TransferHandlerRegistry) =
        registry.register(
            SimpleTransferHandler.create(
                KilnScreenHandler::class.java,
                SanguoArmoryREICommon.kilnDisplayCategory,
                SimpleTransferHandler.IntRange(0, 1)
            )
        )
}
