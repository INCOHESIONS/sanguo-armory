package io.github.incohesions.sanguo_armory.utils;

import net.minecraft.component.ComponentType;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public final class Utils {
    private Utils() { }

    public static boolean checkComponent(@NotNull ItemStack stack, @NotNull ComponentType<Boolean> type) {
        return Boolean.TRUE.equals(stack.getComponents().get(type));
    }
}
