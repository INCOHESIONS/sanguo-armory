package io.github.incohesions.sanguo_armory.registry.item

import io.github.incohesions.sanguo_armory.registry.core.extensions.cooldown
import io.github.incohesions.sanguo_armory.registry.core.extensions.effect
import io.github.incohesions.sanguo_armory.registry.core.extensions.indestructible
import io.github.incohesions.sanguo_armory.registry.core.extensions.unbreakable
import io.github.incohesions.sanguo_armory.item.BlazeStaffItem
import io.github.incohesions.sanguo_armory.item.FangtianJiItem
import io.github.incohesions.sanguo_armory.item.FieryItem
import io.github.incohesions.sanguo_armory.item.ViperLance
import io.github.incohesions.sanguo_armory.registry.core.ConfigureItem
import io.github.incohesions.sanguo_armory.registry.core.IItemRegistry
import io.github.incohesions.sanguo_armory.registry.component.ComponentRegistry
import io.github.incohesions.sanguo_armory.registry.core.extensions.defaultItemLore
import io.github.incohesions.sanguo_armory.registry.item.utils.withType
import net.minecraft.component.type.AttributeModifierSlot
import net.minecraft.component.type.AttributeModifiersComponent
import net.minecraft.entity.attribute.EntityAttribute
import net.minecraft.entity.attribute.EntityAttributeModifier
import net.minecraft.entity.attribute.EntityAttributeModifier.Operation
import net.minecraft.entity.attribute.EntityAttributes
import net.minecraft.item.Item
import net.minecraft.item.ToolMaterial
import net.minecraft.registry.entry.RegistryEntry
import net.minecraft.registry.tag.BlockTags
import net.minecraft.util.Identifier
import net.minecraft.util.Rarity

object WeaponRegistry : IItemRegistry {
    private const val POLEARM_DAMAGE = 11.0F
    private const val LIGHT_SWORD_DAMAGE = 9.0F
    private const val HEAVY_SWORD_DAMAGE = 8.0

    private val combatMaterial = ToolMaterial(BlockTags.INCORRECT_FOR_NETHERITE_TOOL, 2031, 3.0F, 0.0F, 15, null)

    override fun registerAll(): Array<Item> =
        arrayOf(
            weapon<BlazeStaffItem>("blaze_staff", 0.0F, 3.0F, 2.5F, "fire_resistance" to 0) { it
                .component(ComponentRegistry.protectsAgainstExplosions, true)
                .heavyCombatItemModifiers(attackDamage = 12.0)
            },

            polearm<FangtianJiItem>("fangtian_ji", "strength" to 0),
            polearm<ViperLance>("viper_lance", "resistance" to 0),
            polearm<FieryItem>("gun", "fire_resistance" to 0),
            polearm("guandao", "strength" to 0),
            polearm("qiang", "speed" to 1),
            polearm("ge", "strength" to 1),

            heavySword("podao", "strength" to 0),
            heavySword<FieryItem>("huya_jian"),

            lightSword("yitian_ji", "absorption" to 1),
            lightSword("yu_jian", "absorption" to 1),
            lightSword("daun_ji", "haste" to 0)
        )

    private inline fun <reified T : Item> weapon(
        id: String,
        attackDamage: Float = 9.0F,
        attackSpeed: Float = -2.4F,
        useCooldown: Float? = null,
        effect: Pair<String, Int>? = null,
        configure: ConfigureItem = { it }
    ): T = withType(id) {
        val settings = it
            .sword(combatMaterial, attackDamage, attackSpeed)
            .cooldown(useCooldown)
            .defaultItemLore(id)
            .indestructible()
            .unbreakable()
            .rarity(Rarity.EPIC)

        if (effect != null) it.effect(effect.first, effect.second)

        if (useCooldown != null) it.useCooldown(useCooldown)

        configure(settings)
    }

    private inline fun <reified T : Item> polearm(id: String, effect: Pair<String, Int>? = null): T =
        weapon(id, POLEARM_DAMAGE, effect = effect)

    private inline fun <reified T : Item> heavySword(id: String, effect: Pair<String, Int>? = null): T =
        weapon(id, 0.0F, effect = effect) { it.heavyCombatItemModifiers(attackDamage = HEAVY_SWORD_DAMAGE) }

    private inline fun <reified T : Item> lightSword(id: String, effect: Pair<String, Int>? = null): T =
        weapon(id, LIGHT_SWORD_DAMAGE, effect = effect)

    private fun Item.Settings.heavyCombatItemModifiers(attackDamage: Double): Item.Settings =
        attributeModifiers(AttributeModifiersComponent.builder()
            .add(EntityAttributes.ATTACK_DAMAGE, id = Item.BASE_ATTACK_DAMAGE_MODIFIER_ID, value = attackDamage)
            .add(EntityAttributes.ATTACK_SPEED, id = Item.BASE_ATTACK_SPEED_MODIFIER_ID, value = -3.0)
            .add(EntityAttributes.MOVEMENT_SPEED, operation = Operation.ADD_MULTIPLIED_TOTAL, value = -0.15)
            .build()
        )

    private fun AttributeModifiersComponent.Builder.add(
        attr: RegistryEntry<EntityAttribute>,
        id: Identifier? = null,
        value: Double = 0.0,
        operation: Operation = Operation.ADD_VALUE,
        slot: AttributeModifierSlot = AttributeModifierSlot.MAINHAND
    ): AttributeModifiersComponent.Builder =
        add(attr, EntityAttributeModifier(id ?: attr.key.get().registry, value, operation), slot)
}