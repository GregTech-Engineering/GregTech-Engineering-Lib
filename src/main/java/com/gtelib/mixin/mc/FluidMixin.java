package com.gtelib.mixin.mc;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.registries.ForgeRegistries;

import com.gtelib.api.fluid.IFluid;
import com.gtelib.utils.RLUtils;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(Fluid.class)
public class FluidMixin implements IFluid {

    @Unique
    private ResourceLocation gtelib$id;

    @Override
    public @NotNull ResourceLocation gtelib$getIdLocation() {
        if (gtelib$id == null) {
            gtelib$id = ForgeRegistries.FLUIDS.getKey((Fluid) (Object) this);
            if (gtelib$id == null) gtelib$id = RLUtils.mc("water");
        }
        return gtelib$id;
    }
}
