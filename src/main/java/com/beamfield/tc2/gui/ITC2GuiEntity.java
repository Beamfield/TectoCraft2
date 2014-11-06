package com.beamfield.tc2.gui;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;

public interface ITC2GuiEntity {
	public GuiScreen getGUI(EntityPlayer player);
	public Container getContainer(EntityPlayer player);
	public void beginUpdatingPlayer(EntityPlayer player);
	public void stopUpdatingPlayer(EntityPlayer player);
}
