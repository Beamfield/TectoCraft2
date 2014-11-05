package com.beamfield.tc2.common.item;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.util.IIcon;

import com.beamfield.tc2.common.TC2;
import com.beamfield.tc2.utils.Reference;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemBase extends Item{
	protected IIcon[] icons;

	public ItemBase(String name)
	{
		super();
		this.setUnlocalizedName(name);
		this.setCreativeTab(TC2.TAB);
		icons = new IIcon[getNumberOfSubItems()];
	}

	protected int getNumberOfSubItems() {
		return 0;
	}
	
	protected String[] getSubItemNames() {
		return null;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister iconRegister)
	{
		String[] subItemNames = getSubItemNames();
		if(subItemNames != null) {
			for(int i = 0; i < subItemNames.length; i++) {
				icons[i] = iconRegister.registerIcon(Reference.TEXTURE_NAME_PREFIX + subItemNames[i]);
			}
		}
		else
		{
			this.itemIcon = iconRegister.registerIcon(this.getUnlocalizedName().replace("item.", Reference.TEXTURE_NAME_PREFIX));
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamage(int damage)
	{
		if(icons.length > damage && !this.isDamageable()) {
			return icons[damage];
		}

		return super.getIconFromDamage(damage);
	}
	
	@Override
	public int getMetadata(int metadata) {
		return metadata;
	}
}
