package io.github.incohesions.sanguo_armory

import io.github.incohesions.sanguo_armory.registry.block.special.KilnRegistry
import io.github.incohesions.sanguo_armory.screen.KilnScreen
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.client.gui.screen.ingame.HandledScreens

@Environment(EnvType.CLIENT)
class SanguoArmoryClient : ClientModInitializer {
    override fun onInitializeClient() = HandledScreens.register(KilnRegistry.screenHandler, ::KilnScreen)
}