package io.github.incohesions.sanguo_armory

import com.mojang.serialization.Codec
import io.github.incohesions.sanguo_armory.components.HeldItemEffectComponent
import io.github.incohesions.sanguo_armory.extensions.*
import io.github.incohesions.sanguo_armory.items.BlazeStaffItem
import io.github.incohesions.sanguo_armory.items.FangtianJiItem
import io.github.incohesions.sanguo_armory.items.FieryItem
import io.github.incohesions.sanguo_armory.items.ViperLance
import io.github.incohesions.sanguo_armory.utils.ArmorMaterialBuilder
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup
import net.minecraft.component.ComponentType
import net.minecraft.component.type.AttributeModifiersComponent
import net.minecraft.entity.attribute.EntityAttributeModifier.Operation
import net.minecraft.entity.attribute.EntityAttributes
import net.minecraft.item.Item
import net.minecraft.item.SmithingTemplateItem
import net.minecraft.item.ToolMaterial
import net.minecraft.item.equipment.ArmorMaterial
import net.minecraft.item.equipment.EquipmentAssetKeys
import net.minecraft.item.equipment.EquipmentType
import net.minecraft.network.codec.PacketCodecs
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.registry.RegistryKey
import net.minecraft.registry.RegistryKeys
import net.minecraft.registry.tag.BlockTags
import net.minecraft.registry.tag.TagKey
import net.minecraft.sound.SoundEvents
import net.minecraft.text.Text
import net.minecraft.util.Formatting
import net.minecraft.util.Identifier
import net.minecraft.util.Rarity
import net.minecraft.util.Rarity.*

class SanguoRegistry {
    companion object {
        @JvmStatic
        @get:JvmName("getHeldItemEffectComponent")
        val HELD_EFFECT = component("held_item_effect", HeldItemEffectComponent.TYPE)

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

    private typealias ConfigureItem = (Item.Settings) -> Item.Settings

    private val combatMaterial = ToolMaterial(BlockTags.INCORRECT_FOR_NETHERITE_TOOL, 2031, 3.0F, 0.0F, 15, null)

    init {
        val lamellarArmorMaterial = ArmorMaterialBuilder()
            .defense(3, 6, 8, 3, 11)
            .knockbackResistance(0.05F)
            .enchantability(13)
            .toughness(2.5F)
            .durability(35)
            .ingredient(TagKey.of(RegistryKeys.ITEM, SanguoArmory.id("repairs_lamellar_armor")))
            .asset(RegistryKey.of(EquipmentAssetKeys.REGISTRY_KEY, SanguoArmory.id("lamellar")))
            .sound(SoundEvents.ITEM_ARMOR_EQUIP_NETHERITE)
            .build()

        val items = arrayOf(
            combat<BlazeStaffItem>("blaze_staff", 11.0F, 3.0F, 2.5F, "fire_resistance" to 0) { it
                .component(PROTECTS_AGAINST_EXPLOSIONS, true)
                .attributeModifiers(heavyCombatItemModifiers(attackDamage = 12.0))
            },

            polearm<FangtianJiItem>("fangtian_ji", "strength" to 0),
            polearm<ViperLance>("viper_lance", "resistance" to 0),
            polearm<FieryItem>("gun", "fire_resistance" to 0),
            polearm("guandao", "strength" to 0),
            polearm("qiang", "speed" to 1),

            heavySword("podao", "strength" to 0),
            heavySword<FieryItem>("huya_jian"),
            lightSword("yitian_ji", "absorption" to 1),
            lightSword("yu_jian", "absorption" to 1),

            *entireArmor("lamellar", lamellarArmorMaterial, EPIC),

            // Not actually a template. Just a base for the other templates.
            item("stone_template") { it.defaultItemLore("stone_template") },

            template("spearhead_template"),
            template("crescent_template"),

            item("viper_blade", RARE),
            item("crescent_blade", RARE),
            item("guandao_blade", RARE),
            item("spearhead", RARE),

            item("blue_steel_ingot", RARE),
            item("mild_steel_ingot", UNCOMMON),

            item("explosion_core", EPIC) { it.defaultItemLore("explosion_core") },
            item("pole"),
        )

        Registry.register(
            Registries.ITEM_GROUP,
            RegistryKey.of(RegistryKeys.ITEM_GROUP, SanguoArmory.id("item_group")),
            FabricItemGroup
                .builder()
                .displayName(Text.translatable("item_group.${SanguoArmory.MOD_ID}"))
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
        configure: ConfigureItem = { it }
    ): T = factory(id) {
        T::class.constructors.first().call(configure(it))
    }

    private fun item(
        id: String,
        rarity: Rarity = COMMON,
        configure: ConfigureItem = { it }
    ): Item = withType(id) { configure(it.rarity(rarity)) }

    private fun entireArmor(
        name: String,
        material: ArmorMaterial,
        rarity: Rarity
    ): Array<Item> =
        arrayListOf("helmet", "chestplate", "leggings", "boots").map { type ->
            item("${name}_$type", rarity) {
                it.armor(material, EquipmentType.valueOf(type.uppercase()))
            }
        }.toTypedArray()

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

    private inline fun <reified T : Item> combat(
        id: String,
        attackDamage: Float = 9.0F,
        attackSpeed: Float = -2.4F,
        useCooldown: Float? = null,
        effect: Pair<String, Int>? = null,
        configure: ConfigureItem = { it }
    ): T = withType(id) {
        configure( it
            .sword(combatMaterial, attackDamage, attackSpeed)
            .cooldown(useCooldown)
            .defaultItemLore(id)
            .effect(effect)
            .indestructible()
            .unbreakable()
            .rarity(EPIC)
        )
    }

    private inline fun <reified T : Item> polearm(id: String, effect: Pair<String, Int>? = null): T =
        combat(id, 11.0F, effect = effect)

    private inline fun <reified T : Item> heavySword(id: String, effect: Pair<String, Int>? = null): T =
        combat(id, 9.0F, effect = effect) { it.attributeModifiers(heavyCombatItemModifiers(attackDamage = 9.0)) }

    private inline fun <reified T : Item> lightSword(id: String, effect: Pair<String, Int>? = null): T =
        combat(id, 8.0F, effect = effect)

    private fun heavyCombatItemModifiers(attackDamage: Double): AttributeModifiersComponent {
        return AttributeModifiersComponent.builder()
            .add(EntityAttributes.ATTACK_DAMAGE, id = Item.BASE_ATTACK_DAMAGE_MODIFIER_ID, value = attackDamage)
            .add(EntityAttributes.ATTACK_SPEED, id = Item.BASE_ATTACK_SPEED_MODIFIER_ID, value = -3.0)
            .add(EntityAttributes.MOVEMENT_SPEED, operation = Operation.ADD_MULTIPLIED_TOTAL, value = -0.15)
            .build()
    }

    fun Item.Settings.defaultItemLore(id: String): Item.Settings =
        lore(Text.translatable("item.${SanguoArmory.MOD_ID}.$id.lore").formatted(Formatting.GRAY))
}
