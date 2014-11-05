package com.beamfield.tc2.common;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;

public class CreativeTabTC2 extends CreativeTabs{
	public CreativeTabTC2(String par2Str){
		super(par2Str);
	}
	public Item getTabIconItem(){
		return Item.getItemFromBlock(Blocks.bedrock);
	}
}
