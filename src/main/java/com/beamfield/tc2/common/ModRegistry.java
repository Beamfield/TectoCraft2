package com.beamfield.tc2.common;

import com.beamfield.tc2.common.item.ItemIngot;
import com.beamfield.tc2.utils.Reference;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
public class ModRegistry {
	public static Item logo;
	public static void preInit(){
		logo = new Item().setTextureName(Reference.TEXTURE_NAME_PREFIX+"logo").setUnlocalizedName("tc2logo");
		GameRegistry.registerItem(logo, "logo");
		if (TC2.ingotGeneric == null)
		{
			TC2Config.CONFIGURATION.load();
			TC2.registerGenericIngots = TC2Config.CONFIGURATION.get("Recipes", "registerGenericIngots", true, "If set, the common Mod Ingots(for example tin, copper) will be registered in TC2 too.(Default: true)").getBoolean(true);
			TC2.ingotGeneric = new ItemIngot();
        		GameRegistry.registerItem(TC2.ingotGeneric, "TC2Ingot");
			String itemName;
			for(int i = 0; i < ItemIngot.TYPES.length; i++) {
				itemName = ItemIngot.TYPES[i];
				OreDictionary.registerOre(itemName, TC2.ingotGeneric.getItemStackForType(itemName));
			}
			
			TC2Config.CONFIGURATION.save();
		}
	}
	public static void init(){
		
	}
}
