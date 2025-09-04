package io.github.incohesions.sanguo_armory

import com.mojang.serialization.Codec
import io.github.incohesions.sanguo_armory.components.HeldItemEffectComponent
import io.github.incohesions.sanguo_armory.extensions.*
import io.github.incohesions.sanguo_armory.items.BlazeStaffItem
import io.github.incohesions.sanguo_armory.items.FangtianJiItem
import io.github.incohesions.sanguo_armory.items.FieryItem
import io.github.incohesions.sanguo_armory.items.ViperLance
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
        val lamellarArmorMaterial = ArmorMaterial(
            35, // Durability
            createDefenseMap(3, 6, 8, 3, 11),
            13, // Enchantability
            SoundEvents.ITEM_ARMOR_EQUIP_NETHERITE,
            2.5f, // Toughness
            0.05f, // Knockback Resistance
            TagKey.of(RegistryKeys.ITEM, SanguoArmory.id("repairs_lamellar_armor")),
            RegistryKey.of(EquipmentAssetKeys.REGISTRY_KEY, SanguoArmory.id("lamellar"))
        )

        val items = arrayOf(
            combat<BlazeStaffItem>(
                "blaze_staff",
                attackDamage = 12.0F,
                attackSpeed = 3.0F,
                useCooldown = 2.5F,
                effect = "fire_resistance" to 0
            ) { it
                .component(PROTECTS_AGAINST_EXPLOSIONS, true)
                .attributeModifiers(heavyCombatItemModifiers(attackDamage = 12.0))
            },

            combat<ViperLance>("viper_lance", 12.0F, effect = "resistance" to 0),
            combat<Item>("guandao", 12.0F, effect = "strength" to 0),
            combat<Item>("qiang", 12.0F, effect = "speed" to 1),
            combat<Item>("podao", 12.0F, effect = "strength" to 0) {
                it.attributeModifiers(heavyCombatItemModifiers())
            },
            combat<FieryItem>("gun", 12.0F, effect = "fire_resistance" to 0),

            combat<FieryItem>("huya_jian", 10.0F) { it.attributeModifiers(heavyCombatItemModifiers()) },
            combat<Item>("yu_jian", 9.0F, effect = "absorption" to 1),
            combat<Item>("yitian_ji", 9.0F, effect = "absorption" to 1),
            combat<FangtianJiItem>("fangtian_ji", 9.0F, effect = "strength" to 0),

            *entireArmor("lamellar", lamellarArmorMaterial, EPIC),

            // Not actually a template. Just a base for the other templates.
            item("stone_template"),

            template("spearhead_template"),
            template("crescent_template"),

            item("blue_steel_ingot", RARE),
            item("mild_steel_ingot", UNCOMMON),

            item("viper_blade", RARE),
            item("crescent_blade", RARE),
            item("guandao_blade", RARE),
            item("spearhead", RARE),

            item("explosion_core", EPIC),
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

    private inline fun <reified T : Item> combat(
        id: String,
        attackDamage: Float = 9.0F,
        attackSpeed: Float = -2.4F,
        effect: Pair<String, Int>? = null,
        useCooldown: Float? = null,
        configure: ConfigureItem = { it }
    ): T = withType(id) {
        configure( it
            .sword(combatMaterial, attackDamage, attackSpeed)
            .lore(Text.translatable("item.${SanguoArmory.MOD_ID}.$id.lore").formatted(Formatting.GRAY))
            .cooldown(useCooldown)
            .effect(effect)
            .unbreakable()
            .indestructible()
            .rarity(EPIC)
        )
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

    private fun createDefenseMap(boots: Int, leggings: Int, chestplate: Int, helmet: Int, body: Int) =
        mutableMapOf(
            EquipmentType.BOOTS to boots,
            EquipmentType.LEGGINGS to leggings,
            EquipmentType.CHESTPLATE to chestplate,
            EquipmentType.HELMET to helmet,
            EquipmentType.BODY to body
        )


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

    private fun heavyCombatItemModifiers(attackDamage: Double = 10.0): AttributeModifiersComponent {
        return AttributeModifiersComponent.builder()
            .add(EntityAttributes.ATTACK_DAMAGE, id = Item.BASE_ATTACK_DAMAGE_MODIFIER_ID, value = attackDamage)
            .add(EntityAttributes.ATTACK_SPEED, id = Item.BASE_ATTACK_SPEED_MODIFIER_ID, value = -3.0)
            .add(EntityAttributes.MOVEMENT_SPEED, operation = Operation.ADD_MULTIPLIED_TOTAL, value = -0.15)
            .build()
    }
}
