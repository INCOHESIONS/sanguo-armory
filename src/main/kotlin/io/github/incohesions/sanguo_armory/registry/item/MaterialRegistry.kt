package io.github.incohesions.sanguo_armory.registry.item

import net.minecraft.item.Item
import net.minecraft.item.SmithingTemplateItem
import net.minecraft.text.Text
import net.minecraft.util.Identifier
import net.minecraft.util.Rarity
import net.minecraft.util.Rarity.*

object MaterialRegistry : ItemRegistry() {
    override fun registerAll(): Array<Item> =
        arrayOf(
            // Not actually a template. Just a base for the other templates.
            item("stone_template", defaultLore = true),

            template("spearhead_template"),
            template("crescent_template"),

            item("viper_blade", RARE),
            item("crescent_blade", RARE),
            item("guandao_blade", RARE),
            item("spearhead", RARE),

            item("blue_steel_ingot", RARE),
            item("mild_steel_ingot", UNCOMMON),

            item("explosion_core", EPIC, defaultLore = true),
            item("pole"),
        )

    private fun item(
        id: String,
        rarity: Rarity = COMMON,
        defaultLore: Boolean = false,
        configure: Configure = { it }
    ): Item = withType(id) {
        val settings = configure(it.rarity(rarity))
        if (defaultLore) settings.defaultItemLore(id) else settings
    }

    // Currently the same for all templates
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