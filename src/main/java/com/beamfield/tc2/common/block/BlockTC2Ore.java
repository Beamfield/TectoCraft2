package com.beamfield.tc2.common.block;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import com.beamfield.tc2.common.TC2;
import com.beamfield.tc2.utils.Reference;

public class BlockTC2Ore extends Block{
	private IIcon iconCopper, iconTin, iconSilver, iconAluminum, iconNickel, iconLead;
	public BlockTC2Ore()
	{
		super(Material.rock);
		this.setCreativeTab(TC2.TAB);
		this.setBlockName("tc2Ore");
		this.setBlockTextureName(Reference.TEXTURE_NAME_PREFIX + this.getUnlocalizedName());
		this.setHardness(2f);
	}
	@Override
	public IIcon getIcon(int side, int metadata)
	{
		return metadata == 0 ? iconCopper : metadata == 1 ? iconTin : metadata == 2 ? iconSilver : metadata == 3 ? iconAluminum : metadata == 4 ? iconNickel : metadata == 5 ? iconLead : null; 
	}
	@Override
	public void registerBlockIcons(IIconRegister par1IconRegister)
	{
		this.iconCopper = par1IconRegister.registerIcon(Reference.TEXTURE_NAME_PREFIX + "oreCopper");
		this.iconTin = par1IconRegister.registerIcon(Reference.TEXTURE_NAME_PREFIX + "oreTin");
		this.iconSilver = par1IconRegister.registerIcon(Reference.TEXTURE_NAME_PREFIX + "oreSilver");
		this.iconAluminum = par1IconRegister.registerIcon(Reference.TEXTURE_NAME_PREFIX + "oreAluminum");
		this.iconNickel = par1IconRegister.registerIcon(Reference.TEXTURE_NAME_PREFIX + "oreNickel");
		this.iconLead = par1IconRegister.registerIcon(Reference.TEXTURE_NAME_PREFIX + "oreLead");
	}

	@Override
	public int damageDropped(int metadata)
	{
		return metadata;
	}

	@Override
	public void getSubBlocks(Item item, CreativeTabs par2CreativeTabs, List par3List)
	{
		par3List.add(new ItemStack(item, 1, 0));
	}
}
