package io.github.incohesions.sanguo_armory.integration.rei.category

import io.github.incohesions.sanguo_armory.integration.rei.SanguoArmoryREICommon
import io.github.incohesions.sanguo_armory.integration.rei.display.KilnDisplay
import io.github.incohesions.sanguo_armory.registry.block.BlockRegistry
import me.shedaniel.rei.api.client.gui.Renderer
import me.shedaniel.rei.api.client.registry.display.DisplayCategory
import me.shedaniel.rei.api.common.category.CategoryIdentifier
import me.shedaniel.rei.api.common.util.EntryStacks
import net.minecraft.item.ItemStack
import net.minecraft.text.Text
import net.minecraft.util.Identifier

class KilnCategory : DisplayCategory<KilnDisplay> {
    override fun getCategoryIdentifier(): CategoryIdentifier<KilnDisplay> = SanguoArmoryREICommon.kilnDisplayCategory

    override fun getIdentifier(): Identifier = SanguoArmoryREICommon.kilnDisplayCategory.identifier

    override fun getIcon(): Renderer = EntryStacks.of(ItemStack(BlockRegistry.kiln))

    override fun getTitle(): Text = Text.translatable("item.sanguo_armory.kiln")
}
