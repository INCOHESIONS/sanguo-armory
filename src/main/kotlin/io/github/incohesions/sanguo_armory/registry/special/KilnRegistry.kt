package io.github.incohesions.sanguo_armory.registry.special

import io.github.incohesions.sanguo_armory.block.kiln.KilnBlockEntity
import io.github.incohesions.sanguo_armory.block.kiln.KilnRecipe
import io.github.incohesions.sanguo_armory.registry.core.ISpecialRegistry
import io.github.incohesions.sanguo_armory.registry.core.extensions.register
import io.github.incohesions.sanguo_armory.block.kiln.KilnScreenHandler
import io.github.incohesions.sanguo_armory.registry.block.BlockRegistry
import net.fabricmc.fabric.api.`object`.builder.v1.block.entity.FabricBlockEntityTypeBuilder
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.recipe.AbstractCookingRecipe
import net.minecraft.recipe.RecipeType
import net.minecraft.recipe.book.RecipeBookCategory
import net.minecraft.registry.Registries
import net.minecraft.resource.featuretoggle.FeatureSet
import net.minecraft.screen.ScreenHandlerType

object KilnRegistry : ISpecialRegistry {
    const val KILN_ID = "kiln"

    val blockEntity: BlockEntityType<KilnBlockEntity> = Registries.BLOCK_ENTITY_TYPE.register(
        KILN_ID, FabricBlockEntityTypeBuilder.create(::KilnBlockEntity, BlockRegistry.kiln.block).build()
    )

    val screenHandler = Registries.SCREEN_HANDLER.register(
        KILN_ID, ScreenHandlerType(::KilnScreenHandler, FeatureSet.empty())
    )

    val recipeType = Registries.RECIPE_TYPE.register(
        KILN_ID, object : RecipeType<KilnRecipe> { override fun toString(): String = KILN_ID }
    )

    val recipeSerializer = Registries.RECIPE_SERIALIZER.register(
        KILN_ID, AbstractCookingRecipe.Serializer(::KilnRecipe, 200)
    )

    val recipeCategory = Registries.RECIPE_BOOK_CATEGORY.register(KILN_ID, RecipeBookCategory())
}