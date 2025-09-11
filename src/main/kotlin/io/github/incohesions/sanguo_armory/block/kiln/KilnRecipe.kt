package io.github.incohesions.sanguo_armory.block.kiln

import io.github.incohesions.sanguo_armory.registry.block.BlockRegistry
import io.github.incohesions.sanguo_armory.registry.special.KilnRegistry
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.recipe.AbstractCookingRecipe
import net.minecraft.recipe.Ingredient
import net.minecraft.recipe.RecipeSerializer
import net.minecraft.recipe.RecipeType
import net.minecraft.recipe.book.CookingRecipeCategory
import net.minecraft.recipe.book.RecipeBookCategories
import net.minecraft.recipe.book.RecipeBookCategory

class KilnRecipe(
    group: String,
    category: CookingRecipeCategory,
    ingredient: Ingredient,
    result: ItemStack,
    experience: Float,
    cookingTime: Int
) : AbstractCookingRecipe(group, category, ingredient, result, experience, cookingTime) {
    val result: ItemStack
        get() = result()

    override fun getSerializer(): RecipeSerializer<out AbstractCookingRecipe> = KilnRegistry.recipeSerializer

    override fun getType(): RecipeType<out AbstractCookingRecipe> = KilnRegistry.recipeType

    override fun getCookerItem(): Item = BlockRegistry.kiln

    override fun getRecipeBookCategory(): RecipeBookCategory = when (category) {
        BLOCKS -> RecipeBookCategories.FURNACE_BLOCKS
        FOOD -> RecipeBookCategories.FURNACE_FOOD
        MISC -> RecipeBookCategories.FURNACE_MISC
    }
}