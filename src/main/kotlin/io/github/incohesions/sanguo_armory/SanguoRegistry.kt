package io.github.incohesions.sanguo_armory

import com.mojang.serialization.Codec
import io.github.incohesions.sanguo_armory.components.HeldEffectComponent
import io.github.incohesions.sanguo_armory.extensions.*
import io.github.incohesions.sanguo_armory.items.BlazeStaffItem
import io.github.incohesions.sanguo_armory.items.FangtianJiItem
import io.github.incohesions.sanguo_armory.items.FieryItem
import io.github.incohesions.sanguo_armory.items.ViperLance
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup
import net.minecraft.component.ComponentType
import net.minecraft.item.Item
import net.minecraft.item.SmithingTemplateItem
import net.minecraft.item.ToolMaterial
import net.minecraft.network.codec.PacketCodecs
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.registry.RegistryKey
import net.minecraft.registry.RegistryKeys
import net.minecraft.registry.tag.BlockTags
import net.minecraft.text.Text
import net.minecraft.util.Formatting
import net.minecraft.util.Identifier
import net.minecraft.util.Rarity
import net.minecraft.util.Rarity.*

class SanguoRegistry {
    companion object {
        @JvmStatic
        @get:JvmName("getHeldEffectComponent")
        val HELD_EFFECT = component("held_effect", HeldEffectComponent.TYPE)

        @JvmStatic
        @get:JvmName("getImmuneToCactiComponent")
        val IMMUNE_TO_CACTI = component("immune_to_cacti", booleanComponent())

        @JvmStatic
        @get:JvmName("getImmuneToAnvilsComponent")
        val IMMUNE_TO_ANVILS = component("immune_to_anvils", booleanComponent())

        @JvmStatic
        @get:JvmName("getProtectsAgainstExplosions")
        val PROTECTS_AGAINST_EXPLOSIONS = component("protects_against_explosions", booleanComponent())

        fun <T> component(id: String, type: ComponentType<T>): ComponentType<T> = Registry.register(
            Registries.DATA_COMPONENT_TYPE,
            RegistryKey.of(RegistryKeys.DATA_COMPONENT_TYPE, SanguoArmory.id(id)),
            type
        )

        fun booleanComponent(): ComponentType<Boolean> = ComponentType.builder<Boolean>()
            .codec(Codec.BOOL)
            .packetCodec(PacketCodecs.BOOLEAN)
            .build()
    }

    private val combatMaterial = ToolMaterial(BlockTags.INCORRECT_FOR_NETHERITE_TOOL, 2031, 3.0F, 4.0F, 15, null)

    init {
        val items = arrayOf(
            combat<BlazeStaffItem>("blaze_staff", attackSpeed = -3.0F, useCooldown = 2.5F) {
                it.component(PROTECTS_AGAINST_EXPLOSIONS, true)
                    .effect("fire_resistance")
            },
            combat<FangtianJiItem>("fangtian_ji", "strength" to 0),
            combat<ViperLance>("viper_lance", "resistance" to 0),
            combat<Item>("guandao", "strength" to 0),
            combat<Item>("qiang", "speed" to 1),
            combat<Item>("yu_jian", "fire_resistance" to 0),
            combat<FieryItem>("gun", "absorption" to 1),
            combat<FieryItem>("huya_jian", "slowness" to 0),
            combat<Item>("yitian_ji", "absorption" to 1),
            combat<Item>("podao", "strength" to 0, "slowness" to 0),

            // Not actually a template. Just a base for the other templates.
            item("stone_template"),

            template("spearhead_template"),
            template("crescent_template"),

            item("blue_steel_ingot", RARE),
            item("mild_steel_ingot", UNCOMMON),

            item("viper_blade", RARE),
            item("crescent_blade", RARE),
            item("guandao_blade", RARE),
            item("heavenly_spearhead", RARE),
            item("pole"),
        )

        Registry.register(
            Registries.ITEM_GROUP,
            RegistryKey.of(RegistryKeys.ITEM_GROUP, SanguoArmory.id("itemGroup")),
            FabricItemGroup
                .builder()
                .displayName(Text.translatable("itemGroup.${SanguoArmory.MOD_ID}"))
                .icon { items[0].defaultStack }
                .entries { _, entries -> entries.addAll(items.map { it.defaultStack }) }
                .build()
        )
    }

    private inline fun <T : Item> factory(id: String, factory: (Item.Settings) -> T): T {
        val key = RegistryKey.of(RegistryKeys.ITEM, SanguoArmory.id(id))
        return Registry.register(Registries.ITEM, key, factory(Item.Settings().registryKey(key)))
    }

    private inline fun <reified T : Item> withType(
        id: String,
        configure: (Item.Settings) -> Item.Settings = { it }
    ): T = factory(id) {
        T::class.constructors.first().call(configure(it))
    }

    private inline fun <reified T : Item> combat(
        id: String,
        attackDamage: Float = 9.0F,
        attackSpeed: Float = -2.4F,
        useCooldown: Float? = null,
        configure: (Item.Settings) -> Item.Settings = { it }
    ): T =
        withType(id) {
            configure(
                it
                    .sword(combatMaterial, attackDamage, attackSpeed)
                    .lore(Text.translatable("item.${SanguoArmory.MOD_ID}.$id.lore").formatted(Formatting.GRAY))
                    .cooldown(useCooldown)
                    .unbreakable()
                    .indestructible()
                    .rarity(EPIC)
            )
        }

    private inline fun <reified T : Item> combat(
        id: String,
        vararg effects: Pair<String, Int>
    ): T = combat(id) { settings ->
        effects.forEach { settings.effect(it.first, it.second) }
        settings
    }

    private fun item(id: String, rarity: Rarity = COMMON): Item = withType(id) { it.rarity(rarity) }

    private fun template(id: String): SmithingTemplateItem = factory(id) {
        SmithingTemplateItem(  // Currently the same for all templates
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
