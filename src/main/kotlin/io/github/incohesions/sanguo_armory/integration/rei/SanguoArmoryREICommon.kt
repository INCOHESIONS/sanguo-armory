package io.github.incohesions.sanguo_armory.integration.rei

import io.github.incohesions.sanguo_armory.SanguoArmory
import io.github.incohesions.sanguo_armory.integration.rei.display.KilnDisplay
import io.github.incohesions.sanguo_armory.registry.block.special.KilnRegistry
import me.shedaniel.rei.api.common.category.CategoryIdentifier
import me.shedaniel.rei.api.common.plugins.REICommonPlugin

class SanguoArmoryREICommon : REICommonPlugin {
    companion object {
        val kilnDisplayCategory: CategoryIdentifier<KilnDisplay> =
            CategoryIdentifier.of(SanguoArmory.id(KilnRegistry.KILN_ID))
    }
}