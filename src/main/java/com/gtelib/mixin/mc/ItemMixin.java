package com.gtelib.mixin.mc;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.ForgeRegistries;

import com.gtelib.api.item.IItem;
import com.gtelib.utils.RLUtils;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(value = Item.class, priority = 0)
public class ItemMixin implements IItem {

    @Unique
    private ResourceLocation gtelib$id;

    @Override
    public @NotNull ResourceLocation gtelib$getIdLocation() {
        if (gtelib$id == null) {
            gtelib$id = ForgeRegistries.ITEMS.getKey((Item) (Object) this);
            if (gtelib$id == null) gtelib$id = RLUtils.mc("air");
        }
        return gtelib$id;
    }
}
