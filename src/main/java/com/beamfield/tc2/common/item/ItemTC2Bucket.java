package com.beamfield.tc2.common.item;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBucket;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.beamfield.tc2.common.TC2;
import com.beamfield.tc2.utils.Reference;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemTC2Bucket extends ItemBucket{
private Block _fluid;
	
	public ItemTC2Bucket(Block fluid) {
		super(fluid);
		setCreativeTab(TC2.TAB);
		_fluid = fluid;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerIcons(IIconRegister iconRegistry) {
		this.itemIcon = iconRegistry.registerIcon(Reference.TEXTURE_NAME_PREFIX + getUnlocalizedName());
	}

	@Override
	public boolean tryPlaceContainedLiquid(World world, int x, int y, int z) {
		if(_fluid == null) {
			return false;
		}
		else if(!world.isAirBlock(x, y, z) && world.getBlock(x, y, z).getMaterial().isSolid()) {
			return false;
		}
		else {
			world.setBlock(x, y, z, _fluid, 0, 3);
			return true;
		}
	}
	
	@Override
	public void getSubItems(Item item, CreativeTabs creativeTab, List subTypes) {
		subTypes.add(new ItemStack(item, 1, 0));
	}
}
