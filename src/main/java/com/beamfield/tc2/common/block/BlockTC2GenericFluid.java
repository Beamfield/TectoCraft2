package com.beamfield.tc2.common.block;

import com.beamfield.tc2.utils.Reference;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockTC2GenericFluid extends BlockFluidClassic{
	private IIcon _iconFlowing;
	private IIcon _iconStill;
	
	public BlockTC2GenericFluid(Fluid fluid, String unlocalizedName) {
		super(fluid, Material.water);

		setBlockName("fluid." + unlocalizedName + ".still");
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister iconRegistry) {
		_iconStill   = iconRegistry.registerIcon(Reference.TEXTURE_NAME_PREFIX + getUnlocalizedName());
		_iconFlowing = iconRegistry.registerIcon(Reference.TEXTURE_NAME_PREFIX + getUnlocalizedName().replace(".still", ".flowing"));

		this.stack.getFluid().setIcons(_iconStill, _iconFlowing);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(int side, int metadata) {
		return side <= 1 ? _iconStill : _iconFlowing;
	}
}
