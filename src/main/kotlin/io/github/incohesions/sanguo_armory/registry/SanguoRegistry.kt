package io.github.incohesions.sanguo_armory.registry

import io.github.incohesions.sanguo_armory.SanguoArmory
import io.github.incohesions.sanguo_armory.registry.item.extensions.register
import io.github.incohesions.sanguo_armory.registry.component.ComponentRegistry
import io.github.incohesions.sanguo_armory.registry.item.ArmorRegistry
import io.github.incohesions.sanguo_armory.registry.item.WeaponRegistry
import io.github.incohesions.sanguo_armory.registry.item.ItemRegistry
import io.github.incohesions.sanguo_armory.registry.item.MaterialRegistry
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup
import net.minecraft.registry.Registries
import net.minecraft.text.Text

object SanguoRegistry {
    private val registries = arrayOf(WeaponRegistry, MaterialRegistry, ArmorRegistry, ComponentRegistry)

    fun init() {
        val items = registries
            .filterIsInstance<ItemRegistry>()
            .map { it.registerAll() }
            .flatMap { it.asIterable() }
            .toTypedArray()

        val group = FabricItemGroup.builder()
            .displayName(Text.translatable("item_group.${SanguoArmory.MOD_ID}"))
            .icon { items[0].defaultStack }
            .entries { _, entries -> entries.addAll(items.map { it.defaultStack }) }
            .build()

        Registries.ITEM_GROUP.register("item_group", group)
    }
}