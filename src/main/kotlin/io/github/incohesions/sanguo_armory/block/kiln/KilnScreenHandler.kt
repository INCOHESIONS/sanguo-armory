package io.github.incohesions.sanguo_armory.block.kiln

import io.github.incohesions.sanguo_armory.registry.block.special.KilnRegistry
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.inventory.Inventory
import net.minecraft.recipe.RecipePropertySet
import net.minecraft.recipe.book.RecipeBookType
import net.minecraft.screen.AbstractFurnaceScreenHandler
import net.minecraft.screen.PropertyDelegate

class KilnScreenHandler : AbstractFurnaceScreenHandler {
    constructor(
        syncId: Int,
        playerInventory: PlayerInventory
    ) : super(
        KilnRegistry.screenHandler,
        KilnRegistry.recipeType,
        RecipePropertySet.FURNACE_INPUT,
        RecipeBookType.FURNACE,
        syncId,
        playerInventory
    )

    constructor(
        syncId: Int,
        playerInventory: PlayerInventory,
        inventory: Inventory,
        propertyDelegate: PropertyDelegate
    ) : super(
        KilnRegistry.screenHandler,
        KilnRegistry.recipeType,
        RecipePropertySet.FURNACE_INPUT,
        RecipeBookType.FURNACE,
        syncId,
        playerInventory,
        inventory,
        propertyDelegate
    )
}