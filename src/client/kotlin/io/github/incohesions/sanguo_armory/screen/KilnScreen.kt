package io.github.incohesions.sanguo_armory.screen

import io.github.incohesions.sanguo_armory.block.kiln.KilnScreenHandler
import io.github.incohesions.sanguo_armory.registry.item.MaterialRegistry
import io.github.incohesions.sanguo_armory.registry.special.KilnRegistry
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.client.gui.screen.ingame.AbstractFurnaceScreen
import net.minecraft.client.gui.screen.recipebook.RecipeBookWidget
import net.minecraft.entity.player.PlayerInventory
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
            RecipeBookWidget.Tab(MaterialRegistry.blueSteelIngot, KilnRegistry.recipeCategory),
        )
    )