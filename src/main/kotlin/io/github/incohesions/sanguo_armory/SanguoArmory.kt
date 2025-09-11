package io.github.incohesions.sanguo_armory

import io.github.incohesions.sanguo_armory.registry.core.IItemRegistry
import io.github.incohesions.sanguo_armory.registry.core.IRegistry
import io.github.incohesions.sanguo_armory.registry.core.extensions.register
import io.github.incohesions.sanguo_armory.extensions.flatMapArray
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup
import net.minecraft.registry.Registries
import net.minecraft.text.Text
import net.minecraft.util.Identifier
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class SanguoArmory : ModInitializer {
    companion object {
        const val MOD_ID = "sanguo_armory"

        @JvmStatic
        @get:JvmName("logger")
        val logger: Logger = LoggerFactory.getLogger(MOD_ID)

        @JvmStatic
        fun id(path: String): Identifier = Identifier.of(MOD_ID, path)
    }

    override fun onInitialize() {
        val items = IRegistry.registries()
            .filterIsInstance<IItemRegistry>()
            .flatMapArray { it.registerAll() }

        val group = FabricItemGroup.builder()
            .displayName(Text.translatable("item_group.${MOD_ID}"))
            .icon { items[0].defaultStack }
            .entries { _, entries -> entries.addAll(items.map { it.defaultStack }) }
            .build()

        Registries.ITEM_GROUP.register("item_group", group)

        logger.info("Sanguo Armory has been initialized!")
    }
}
