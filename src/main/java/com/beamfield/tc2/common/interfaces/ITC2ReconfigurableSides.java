package com.beamfield.tc2.common.interfaces;

import net.minecraft.util.IIcon;
import cofh.api.tileentity.IReconfigurableSides;

public interface ITC2ReconfigurableSides extends IReconfigurableSides{
	public IIcon getIconForSide(int referenceSide);
}
