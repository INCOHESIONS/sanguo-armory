package io.github.incohesions.sanguo_armory.registry.core.extensions

import io.github.incohesions.sanguo_armory.SanguoArmory
import io.github.incohesions.sanguo_armory.component.HeldItemEffectComponent
import io.github.incohesions.sanguo_armory.registry.component.ComponentRegistry
import net.minecraft.component.ComponentType
import net.minecraft.component.DataComponentTypes
import net.minecraft.component.type.DamageResistantComponent
import net.minecraft.component.type.LoreComponent
import net.minecraft.entity.damage.DamageType
import net.minecraft.item.Item
import net.minecraft.registry.tag.DamageTypeTags
import net.minecraft.registry.tag.TagKey
import net.minecraft.text.Text
import net.minecraft.util.Formatting
import net.minecraft.util.Identifier
import net.minecraft.util.Unit

fun Item.Settings.lore(vararg styledLines: Text): Item.Settings =
    component(DataComponentTypes.LORE, LoreComponent(emptyList(), styledLines.toList()))

fun Item.Settings.defaultItemLore(id: String): Item.Settings =
    lore(Text.translatable("item.${SanguoArmory.MOD_ID}.$id.lore").formatted(Formatting.GRAY))

fun Item.Settings.cooldown(cooldown: Float?): Item.Settings {
    return useCooldown(cooldown ?: return this)
}

fun Item.Settings.resistant(vararg tags: TagKey<DamageType>): Item.Settings = apply {
    tags.map(::DamageResistantComponent).forEach { component(DataComponentTypes.DAMAGE_RESISTANT, it) }
}

fun Item.Settings.booleans(vararg components: ComponentType<Boolean>): Item.Settings = apply {
    components.forEach { component(it, true) }
}

fun Item.Settings.markers(vararg components: ComponentType<Unit>): Item.Settings = apply {
    components.forEach { component(it, Unit.INSTANCE) }
}

fun Item.Settings.unbreakable(): Item.Settings =
    markers(DataComponentTypes.UNBREAKABLE)

fun Item.Settings.indestructible(): Item.Settings = this
    .booleans(ComponentRegistry.immuneToCacti, ComponentRegistry.immuneToAnvils)
    .resistant(DamageTypeTags.IS_FIRE, DamageTypeTags.IS_EXPLOSION, DamageTypeTags.IS_LIGHTNING)

fun Item.Settings.effect(id: String, amplifier: Int = 0): Item.Settings =
    component(HeldItemEffectComponent.TYPE, HeldItemEffectComponent(Identifier.ofVanilla(id), amplifier))
