package com.gtelib;

import net.minecraftforge.fml.common.Mod;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(GTECore.Lib_ID)
public class GTECore {

    public static final String Lib_ID = "gtelib";
    public static final String Lib_NAME = "GregTech Engineering Lib";
    public static final Logger LOGGER = LogManager.getLogger(Lib_NAME);

    public static final String Core_ID = "gtecore";
    public static final String Core_NAME = "GregTech Engineering Core";

    public static final String Config_ID = "GTEConfig";
    public static final String StartUpConfig_Name = "GTECoreStartUpConfig";

    public GTECore() {/* compiled code */}
}
