package io.github.incohesions.sanguo_armory.screen

import io.github.incohesions.sanguo_armory.block.kiln.KilnScreenHandler
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.client.gui.screen.ingame.AbstractFurnaceScreen
import net.minecraft.client.gui.screen.recipebook.RecipeBookWidget
import net.minecraft.client.recipebook.RecipeBookType
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.item.Items
import net.minecraft.recipe.book.RecipeBookCategories
import net.minecraft.text.Text
import net.minecraft.util.Identifier.ofVanilla as vanillaId

@Environment(EnvType.CLIENT)
class KilnScreen(handler: KilnScreenHandler, inventory: PlayerInventory, title: Text) :
    AbstractFurnaceScreen<KilnScreenHandler>(
        handler,
        inventory,
        title,
        Text.translatable("gui.recipebook.toggleRecipes.smeltable"),
        vanillaId("textures/gui/container/furnace.png"),
        vanillaId("container/furnace/lit_progress"),
        vanillaId("container/furnace/burn_progress"),
        mutableListOf(
            RecipeBookWidget.Tab(RecipeBookType.FURNACE),
            RecipeBookWidget.Tab(Items.PORKCHOP, RecipeBookCategories.FURNACE_FOOD),
            RecipeBookWidget.Tab(Items.STONE, RecipeBookCategories.FURNACE_BLOCKS),
            RecipeBookWidget.Tab(Items.LAVA_BUCKET, Items.EMERALD, RecipeBookCategories.FURNACE_MISC)
        )
    )