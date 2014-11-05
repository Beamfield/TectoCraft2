package com.beamfield.tc2.common.block;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraftforge.oredict.OreDictionary;

import com.beamfield.tc2.common.TC2;
import com.beamfield.tc2.common.item.ItemIngot;
import com.beamfield.tc2.utils.Reference;

import cpw.mods.fml.common.registry.GameRegistry;

public class BlockTC2Metal extends Block{
	public static final int METADATA_COPPER 	= 0;
	public static final int METADATA_TIN 		= 1;
	public static final int METADATA_STEEL 		= 2;
	public static final int METADATA_BRONZE 	= 3;
	public static final int METADATA_SILVER		= 4;
	public static final int METADATA_ALUMINUM	= 5;
	public static final int METADATA_NICKEL		= 6;
	public static final int METADATA_LEAD		= 7;
	
	private static final String[] _subBlocks = new String[] { "blockCopper", "blockTin", "blockSteel", "blockBronze", "blockSilver", "blockAluminum", "blockNickel", "blockLead" };
	private static final String[] _materials = new String[] { "Copper", "Tin", "Steel", "Bronze", "Silver", "Aluminum", "Nickel", "Lead" };
	private IIcon[] _icons = new IIcon[_subBlocks.length];
	private static final int NUM_BLOCKS = _subBlocks.length;
	
	public BlockTC2Metal() {
		super(Material.iron);
		this.setCreativeTab(TC2.TAB);
		this.setBlockName("tc2Metal");
		this.setHardness(2f);
	}

	@Override
	public IIcon getIcon(int side, int metadata)
	{
		metadata = Math.max(0, Math.min(NUM_BLOCKS, metadata));
		return _icons[metadata];
	}
	
	@Override
	public void registerBlockIcons(IIconRegister iconRegister)
	{
		for(int i = 0; i < NUM_BLOCKS; i++) {
			_icons[i] = iconRegister.registerIcon(Reference.TEXTURE_NAME_PREFIX + getUnlocalizedName() + "." + _subBlocks[i]);
		}
	}

	@Override
	public int damageDropped(int metadata)
	{
		return metadata;
	}

	@Override
	public void getSubBlocks(Item item, CreativeTabs par2CreativeTabs, List par3List)
	{
		for(int i = 0; i < NUM_BLOCKS; i++) {
			par3List.add(new ItemStack(item, 1, i));
		}
	}
	
	public ItemStack getItemStackForMaterial(String name) {
		int i = 0;

		for(i = 0; i < NUM_BLOCKS; i++) {
			if(name.equals(_materials[i])) {
				break;
			}
		}
		
		return new ItemStack(this, 1, i);
	}
	
	public void registerOreDictEntries() {
		for(int i = 0; i < NUM_BLOCKS; i++) {
			OreDictionary.registerOre(_subBlocks[i], new ItemStack(this, 1, i));
		}
	}

	public void registerIngotRecipes(ItemIngot ingotItem) {
		for(int i = 0; i < NUM_BLOCKS; i++) {
			ItemStack block = new ItemStack(this, 1, i);
			ItemStack ingot = ingotItem.getIngotItem(_materials[i]);
			GameRegistry.addShapelessRecipe(block, ingot, ingot, ingot, ingot, ingot, ingot, ingot, ingot, ingot);
			ingot.stackSize = 9;
			GameRegistry.addShapelessRecipe(ingot, block);
		}
	}
}
