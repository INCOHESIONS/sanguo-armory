package io.github.incohesions.sanguo_armory

import net.fabricmc.api.ModInitializer
import net.minecraft.util.Identifier
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class SanguoArmory : ModInitializer {
    companion object {
        @get:JvmName("MOD_ID")
        const val MOD_ID = "sanguo_armory"

        @JvmStatic
        @get:JvmName("logger")
        val LOGGER: Logger = LoggerFactory.getLogger(MOD_ID)

        @JvmStatic
        fun id(path: String): Identifier = Identifier.of(MOD_ID, path)
    }

    override fun onInitialize() {
        SanguoRegistry()
        LOGGER.info("Sanguo Armory has been initialized!")
    }
}
