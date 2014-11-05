package com.beamfield.tc2.common;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;

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
	@EventHandler
	public void preInit(FMLPreInitializationEvent event){
		ModRegistry.preInit();
		TC2Config.CONFIGURATION.load();
		TC2Config.CONFIGURATION.save();
	}
	@EventHandler
	public void init(FMLInitializationEvent event){
		ModRegistry.init();
		proxy.registerRenderers();
	}
}
