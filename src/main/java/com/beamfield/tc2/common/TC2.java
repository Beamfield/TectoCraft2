package com.beamfield.tc2.common;
import java.util.HashSet;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;

import com.beamfield.tc2.common.item.ItemIngot;
import com.beamfield.tc2.utils.Reference;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(modid=Reference.MODID, version=Reference.VERSION)
public class TC2 {
	public static final CreativeTabs TAB = new CreativeTabTC2(Reference.MODID);
	public static TC2 modInstance;
	@SidedProxy(clientSide=Reference.CLProxyLoc, serverSide=Reference.CProxyLoc)
	public static CommonProxy proxy;
	public static boolean enableWorldGen = true;
	public static boolean enableWorldGenInNegativeDimensions = false;
	public static boolean enableWorldRegeneration = true;
	public static boolean registerGenericIngots = true;
	public static HashSet<Integer> dimensionWhitelist = new HashSet<Integer>();
	public static ItemIngot ingotGeneric;
	@EventHandler
	public void preInit(FMLPreInitializationEvent event){
		ModRegistry.preInit();
		TC2Config.CONFIGURATION.load();
		enableWorldGen = TC2Config.CONFIGURATION.get("WorldGen", "enableWorldGen", true, "If false, disables all world gen from TectoCraft2; all other worldgen settings are automatically overridden").getBoolean(true);
		enableWorldGenInNegativeDimensions = TC2Config.CONFIGURATION.get("WorldGen", "enableWorldGenInNegativeDims", false, "Run TC2 world generation in negative dimension IDs? (default: false) If you don't know what this is, leave it alone.").getBoolean(false);
		enableWorldRegeneration = TC2Config.CONFIGURATION.get("WorldGen", "enableWorldRegeneration", false, "Run TC2 World Generation in chunks that have already been generated, but have not been modified by TectoCraft2 before. This is largely useful for worlds that existed before TectoCraft2 was released.").getBoolean(false);
		int[] worldGenDimensionWhitelist = TC2Config.CONFIGURATION.get("WorldGen", "dimensionWhitelist", new int[] {}, "If enableWorldGenInNegativeDimensions is false, you may add negative dimensions to this whitelist to selectively enable worldgen in them.").getIntList();
		for(int i : worldGenDimensionWhitelist) {
			dimensionWhitelist.add(i);
		}

		TC2Config.CONFIGURATION.save();
	}
	@EventHandler
	public void init(FMLInitializationEvent event){
		ModRegistry.init();
		proxy.registerRenderers();
	}
}
