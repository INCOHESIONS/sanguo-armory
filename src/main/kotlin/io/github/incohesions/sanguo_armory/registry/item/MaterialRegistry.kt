package io.github.incohesions.sanguo_armory.registry.item

import io.github.incohesions.sanguo_armory.registry.core.ConfigureItem
import io.github.incohesions.sanguo_armory.registry.core.IItemRegistry
import io.github.incohesions.sanguo_armory.registry.core.extensions.defaultItemLore
import io.github.incohesions.sanguo_armory.registry.item.utils.factory
import io.github.incohesions.sanguo_armory.registry.item.utils.withType
import net.minecraft.item.Item
import net.minecraft.item.SmithingTemplateItem
import net.minecraft.text.Text
import net.minecraft.util.Identifier
import net.minecraft.util.Rarity
import net.minecraft.util.Rarity.*

object MaterialRegistry : IItemRegistry {
    val blueSteelIngot = material("blue_steel_ingot", RARE)

    override fun registerAll(): Array<Item> =
        arrayOf(
            // Not actually a template. Just a base for the other templates.
            material("stone_template", defaultLore = true),

            template("spearhead_template"),
            template("crescent_template"),

            material("viper_blade", RARE),
            material("crescent_blade", RARE),
            material("guandao_blade", RARE),
            material("spearhead", RARE),

            blueSteelIngot,
            material("mild_steel_ingot", UNCOMMON),

            material("explosion_core", EPIC, defaultLore = true),
            material("pole"),
        )

    private fun material(
        id: String,
        rarity: Rarity = COMMON,
        defaultLore: Boolean = false,
        configure: ConfigureItem = { it }
    ): Item = withType(id) {
        val settings = configure(it.rarity(rarity))
        if (defaultLore) settings.defaultItemLore(id) else settings
    }

    /* Currently the same for all templates */
    private fun template(id: String): SmithingTemplateItem = factory(id) {
        SmithingTemplateItem(
            Text.translatable("item.sanguo_armory.templates.applies_to"),
            Text.translatable("item.sanguo_armory.templates.ingredients"),
            Text.translatable("item.sanguo_armory.templates.base_slot"),
            Text.translatable("item.sanguo_armory.templates.addition_slot"),
            listOf(Identifier.ofVanilla("container/slot/cobblestone")),
            listOf(Identifier.ofVanilla("container/slot/iron_ingot")),
            it.rarity(RARE)
        )
    }
}