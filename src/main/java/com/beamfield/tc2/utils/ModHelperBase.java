package com.beamfield.tc2.utils;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.ModAPIManager;

public class ModHelperBase {
	public static boolean useCofh;
	
	public void register() {}
	
	public static void detectMods() {
		useCofh = Loader.isModLoaded("CoFHCore"); 
	}
}
