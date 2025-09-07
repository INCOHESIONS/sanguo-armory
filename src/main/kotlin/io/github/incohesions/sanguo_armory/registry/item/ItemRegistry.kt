package io.github.incohesions.sanguo_armory.registry.item

import io.github.incohesions.sanguo_armory.SanguoArmory
import io.github.incohesions.sanguo_armory.registry.item.extensions.lore
import io.github.incohesions.sanguo_armory.registry.IRegistry
import net.minecraft.item.Item
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.registry.RegistryKey
import net.minecraft.registry.RegistryKeys
import net.minecraft.text.Text
import net.minecraft.util.Formatting

abstract class ItemRegistry : IRegistry {
    /*
      I'd like to make this typealias protected but sometimes the compiler errors out, and sometimes it doesn't.
      It's most certainly a bug (since it's inconsistent) so I'll just leave it public for the time being.
    */
    typealias Configure = (Item.Settings) -> Item.Settings

    abstract fun registerAll(): Array<Item>

    protected inline fun <T : Item> factory(id: String, factory: (Item.Settings) -> T): T {
        val key = RegistryKey.of(RegistryKeys.ITEM, SanguoArmory.id(id))
        return Registry.register(Registries.ITEM, key, factory(Item.Settings().registryKey(key)))
    }

    protected inline fun <reified T : Item> withType(
        id: String,
        configure: Configure = { it }
    ): T = factory(id) { T::class.constructors.first().call(configure(it)) }

    protected fun Item.Settings.defaultItemLore(id: String): Item.Settings =
        lore(Text.translatable("item.${SanguoArmory.MOD_ID}.$id.lore").formatted(Formatting.GRAY))
}