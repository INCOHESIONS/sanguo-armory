package io.github.incohesions.sanguo_armory.registry.item

import io.github.incohesions.sanguo_armory.registry.core.ConfigureItem
import io.github.incohesions.sanguo_armory.registry.core.IItemRegistry
import io.github.incohesions.sanguo_armory.registry.item.utils.ArmorMaterialBuilder
import io.github.incohesions.sanguo_armory.registry.item.utils.withType
import net.minecraft.item.Item
import net.minecraft.item.equipment.ArmorMaterial
import net.minecraft.item.equipment.EquipmentType
import net.minecraft.sound.SoundEvents
import net.minecraft.util.Rarity

object ArmorRegistry : IItemRegistry {
    private val lamellarArmorMaterial = ArmorMaterialBuilder()
        .defense(3, 6, 8, 3, 11)
        .toughness(2.5F)
        .durability(35)
        .repairTag("repairs_lamellar_armor")
        .assetKey("lamellar")
        .sound(SoundEvents.ITEM_ARMOR_EQUIP_NETHERITE)
        .build()

    override fun registerAll(): Array<Item> = entireArmor("lamellar", lamellarArmorMaterial, Rarity.EPIC)

    private fun entireArmor(
        name: String,
        material: ArmorMaterial,
        rarity: Rarity,
        configure: ConfigureItem = { it }
    ): Array<Item> =
        arrayListOf("helmet", "chestplate", "leggings", "boots").map { type ->
            withType<Item>("${name}_$type") {
                configure( it
                    .armor(material, EquipmentType.valueOf(type.uppercase()))
                    .rarity(rarity)
                )
            }
        }.toTypedArray()
}