package com.gtelib.utils;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.registries.ForgeRegistries;

import com.gtelib.api.fluid.IFluid;

public class FluidUtils {

    private FluidUtils() {}

    public static String getId(Fluid fluid) {
        return getIdLocation(fluid).toString();
    }

    public static ResourceLocation getIdLocation(Fluid fluid) {
        return ((IFluid) fluid).gtelib$getIdLocation();
    }

    public static Fluid getFluid(String fluidName) {
        Fluid fluid = ForgeRegistries.FLUIDS.getValue(new ResourceLocation(fluidName));
        return fluid == null ? Fluids.EMPTY : fluid;
    }
}
