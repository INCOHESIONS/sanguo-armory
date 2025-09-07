package io.github.incohesions.sanguo_armory

import io.github.incohesions.sanguo_armory.registry.SanguoRegistry
import net.fabricmc.api.ModInitializer
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
        SanguoRegistry.init()
        logger.info("Sanguo Armory has been initialized!")
    }
}
