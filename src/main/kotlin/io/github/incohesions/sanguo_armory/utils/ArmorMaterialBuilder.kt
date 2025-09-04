package io.github.incohesions.sanguo_armory.utils

import net.minecraft.item.Item
import net.minecraft.item.equipment.ArmorMaterial
import net.minecraft.item.equipment.ArmorMaterials
import net.minecraft.item.equipment.EquipmentAsset
import net.minecraft.item.equipment.EquipmentType
import net.minecraft.registry.RegistryKey
import net.minecraft.registry.entry.RegistryEntry
import net.minecraft.registry.tag.TagKey
import net.minecraft.sound.SoundEvent

class ArmorMaterialBuilder(base: ArmorMaterial = ArmorMaterials.DIAMOND) {
    private var durability = base.durability
    private var enchantability = base.enchantmentValue
    private var toughness = base.toughness
    private var knockbackResistance = base.knockbackResistance

    private var sound: RegistryEntry<SoundEvent> = base.equipSound
    private var defense: Map<EquipmentType, Int> = base.defense
    private var ingredient: TagKey<Item> = base.repairIngredient
    private var asset: RegistryKey<EquipmentAsset> = base.assetId

    fun durability(durability: Int) = apply { this.durability = durability }

    fun enchantability(enchantability: Int) = apply { this.enchantability = enchantability }

    fun toughness(toughness: Float) = apply { this.toughness = toughness }

    fun knockbackResistance(knockbackResistance: Float) = apply { this.knockbackResistance = knockbackResistance }

    fun sound(sound: RegistryEntry<SoundEvent>) = apply { this.sound = sound }

    fun defense(boots: Int, leggings: Int, chestplate: Int, helmet: Int, body: Int) = apply {
        this.defense = mutableMapOf(
            EquipmentType.BOOTS to boots,
            EquipmentType.LEGGINGS to leggings,
            EquipmentType.CHESTPLATE to chestplate,
            EquipmentType.HELMET to helmet,
            EquipmentType.BODY to body
        )
    }

    fun ingredient(ingredient: TagKey<Item>) = apply { this.ingredient = ingredient }

    fun asset(asset: RegistryKey<EquipmentAsset>) = apply { this.asset = asset }

    fun build() = ArmorMaterial(
        durability,
        defense,
        enchantability,
        sound,
        toughness,
        knockbackResistance,
        ingredient,
        asset,
    )
}
