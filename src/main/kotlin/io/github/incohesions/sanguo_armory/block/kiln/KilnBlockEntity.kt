package io.github.incohesions.sanguo_armory.block.kiln

import io.github.incohesions.sanguo_armory.registry.block.special.KilnRegistry
import net.minecraft.block.BlockState
import net.minecraft.block.entity.AbstractFurnaceBlockEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.screen.ScreenHandler
import net.minecraft.text.Text
import net.minecraft.util.math.BlockPos

class KilnBlockEntity(pos: BlockPos, state: BlockState) : AbstractFurnaceBlockEntity(
    KilnRegistry.blockEntity, pos, state, KilnRegistry.recipeType
) {
    override fun getContainerName(): Text = Text.translatable("item.sanguo_armory.kiln")

    override fun createScreenHandler(syncId: Int, playerInventory: PlayerInventory): ScreenHandler =
        KilnScreenHandler(syncId, playerInventory, this, propertyDelegate)
}