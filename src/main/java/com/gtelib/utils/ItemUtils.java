package com.gtelib.utils;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;

import com.gtelib.api.item.IItem;

public final class ItemUtils {

    private ItemUtils() {}

    public static ItemStack getFirst(Ingredient ingredient) {
        for (ItemStack stack : ingredient.getItems()) {
            if (!stack.isEmpty()) {
                return stack;
            }
        }
        return ItemStack.EMPTY;
    }

    public static String getId(Block block) {
        return getIdLocation(block.asItem()).toString();
    }

    public static String getId(ItemStack item) {
        return getIdLocation(item.getItem()).toString();
    }

    public static String getId(Item item) {
        return getIdLocation(item).toString();
    }

    public static ResourceLocation getIdLocation(Block block) {
        return ((IItem) block.asItem()).gtelib$getIdLocation();
    }

    public static ResourceLocation getIdLocation(Item item) {
        return ((IItem) item).gtelib$getIdLocation();
    }
}
