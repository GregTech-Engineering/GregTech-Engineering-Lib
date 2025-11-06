package com.gtelib.common;

import com.gtelib.GTECore;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public class CommonProxy {

    public CommonProxy() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        init();
    }

    private static void init() {
        GTECore.LibLOGGER.info("GTELib common proxy init!");
    }
}
