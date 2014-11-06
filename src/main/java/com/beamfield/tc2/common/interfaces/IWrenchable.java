package com.beamfield.tc2.common.interfaces;

import net.minecraft.entity.player.EntityPlayer;

public interface IWrenchable {
	public boolean onWrench(EntityPlayer player, int hitSide);
}
