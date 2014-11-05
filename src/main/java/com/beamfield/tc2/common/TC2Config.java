package com.beamfield.tc2.common;

import java.io.File;

import cpw.mods.fml.common.Loader;
import net.minecraftforge.common.config.Configuration;

public class TC2Config {
	public static final String VERSION = "@VERSION@";
	public static final String MINECRAFT_VERSION = "[1.7.10]";
	public static final Configuration CONFIGURATION = new Configuration(new File(Loader.instance().getConfigDir(), "TectoCraft2" + File.separator + "TectoCraft2.cfg"));
	static
	{
		CONFIGURATION.load();
		CONFIGURATION.save();
	}
}
