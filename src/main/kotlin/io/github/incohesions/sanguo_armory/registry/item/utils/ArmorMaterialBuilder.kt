package io.github.incohesions.sanguo_armory.registry.item.utils

import io.github.incohesions.sanguo_armory.SanguoArmory
import net.minecraft.item.Item
import net.minecraft.item.equipment.ArmorMaterial
import net.minecraft.item.equipment.ArmorMaterials
import net.minecraft.item.equipment.EquipmentAsset
import net.minecraft.item.equipment.EquipmentAssetKeys
import net.minecraft.item.equipment.EquipmentType
import net.minecraft.registry.RegistryKey
import net.minecraft.registry.RegistryKeys
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
    private var repairTag: TagKey<Item> = base.repairIngredient
    private var assetKey: RegistryKey<EquipmentAsset> = base.assetId

    fun durability(durability: Int) = apply { this.durability = durability }

    fun enchantability(enchantability: Int) = apply { this.enchantability = enchantability }

    fun toughness(toughness: Float) = apply { this.toughness = toughness }

    fun knockbackResistance(knockbackResistance: Float) = apply { this.knockbackResistance = knockbackResistance }

    fun sound(sound: RegistryEntry<SoundEvent>) = apply { this.sound = sound }

    fun defense(boots: Int, leggings: Int, chestplate: Int, helmet: Int, body: Int) = apply {
        defense = mapOf(
            EquipmentType.BOOTS to boots,
            EquipmentType.LEGGINGS to leggings,
            EquipmentType.CHESTPLATE to chestplate,
            EquipmentType.HELMET to helmet,
            EquipmentType.BODY to body
        )
    }

    fun repairTag(id: String) = apply {
        repairTag = TagKey.of(RegistryKeys.ITEM, SanguoArmory.id(id))
    }

    fun assetKey(id: String) = apply {
        assetKey = RegistryKey.of(EquipmentAssetKeys.REGISTRY_KEY, SanguoArmory.id(id))
    }

    fun build() = ArmorMaterial(
        durability,
        defense,
        enchantability,
        sound,
        toughness,
        knockbackResistance,
        repairTag,
        assetKey,
    )
}
