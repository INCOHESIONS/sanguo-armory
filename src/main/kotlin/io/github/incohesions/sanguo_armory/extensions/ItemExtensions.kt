package io.github.incohesions.sanguo_armory.extensions

import io.github.incohesions.sanguo_armory.SanguoRegistry
import io.github.incohesions.sanguo_armory.components.HeldItemEffectComponent
import net.minecraft.component.ComponentType
import net.minecraft.component.DataComponentTypes
import net.minecraft.component.type.AttributeModifierSlot
import net.minecraft.component.type.AttributeModifiersComponent
import net.minecraft.component.type.DamageResistantComponent
import net.minecraft.component.type.LoreComponent
import net.minecraft.entity.attribute.EntityAttribute
import net.minecraft.entity.attribute.EntityAttributeModifier
import net.minecraft.entity.attribute.EntityAttributeModifier.Operation
import net.minecraft.entity.damage.DamageType
import net.minecraft.item.Item
import net.minecraft.registry.entry.RegistryEntry
import net.minecraft.registry.tag.DamageTypeTags
import net.minecraft.registry.tag.TagKey
import net.minecraft.text.Text
import net.minecraft.util.Identifier
import net.minecraft.util.Unit

fun Item.Settings.lore(vararg styledLines: Text): Item.Settings =
    component(DataComponentTypes.LORE, LoreComponent(emptyList(), styledLines.toList()))

fun Item.Settings.cooldown(cooldown: Float?): Item.Settings {
    return useCooldown(cooldown ?: return this)
}

fun Item.Settings.resistant(vararg tags: TagKey<DamageType>): Item.Settings {
    tags.forEach {
        component(DataComponentTypes.DAMAGE_RESISTANT, DamageResistantComponent(it))
    }
    return this
}

fun Item.Settings.booleans(vararg components: ComponentType<Boolean>): Item.Settings {
    components.forEach { component(it, true) }
    return this
}

fun Item.Settings.markers(vararg components: ComponentType<Unit>): Item.Settings {
    components.forEach { component(it, Unit.INSTANCE) }
    return this
}

fun Item.Settings.unbreakable(): Item.Settings =
    markers(DataComponentTypes.UNBREAKABLE)

fun Item.Settings.indestructible(): Item.Settings = this
    .booleans(SanguoRegistry.IMMUNE_TO_CACTI, SanguoRegistry.IMMUNE_TO_ANVILS)
    .resistant(DamageTypeTags.IS_FIRE, DamageTypeTags.IS_EXPLOSION, DamageTypeTags.IS_LIGHTNING)

fun Item.Settings.effect(id: String, amplifier: Int = 0): Item.Settings =
    component(HeldItemEffectComponent.TYPE, HeldItemEffectComponent(Identifier.ofVanilla(id), amplifier))

fun Item.Settings.effect(effect: Pair<String, Int>?): Item.Settings {
    return effect((effect ?: return this).first, effect.second)
}

// WARN: Should be used only AFTER .tool, .sword or any other similar methods since they also use modifiers.
fun AttributeModifiersComponent.Builder.add(
    attr: RegistryEntry<EntityAttribute>,
    id: Identifier? = null,
    value: Double = 0.0,
    operation: Operation = Operation.ADD_VALUE,
    slot: AttributeModifierSlot = AttributeModifierSlot.MAINHAND
): AttributeModifiersComponent.Builder =
    add(attr, EntityAttributeModifier(id ?: attr.key.get().registry, value, operation), slot)
