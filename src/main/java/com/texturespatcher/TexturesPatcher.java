package com.texturespatcher;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(
    modid = TexturesPatcher.MODID,
    name = TexturesPatcher.NAME,
    version = TexturesPatcher.VERSION,
    acceptableRemoteVersions = "*")
public class TexturesPatcher {

    public static final String MODID = "texturespatcher";
    public static final String NAME = "Textures Patcher";
    public static final String VERSION = "1.0.0";

    public static final Logger LOG = LogManager.getLogger(NAME);

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        LOG.info("{} {} Loaded: waiting for resourcepack loading to decide whether start fixing", NAME, VERSION);
    }
}
