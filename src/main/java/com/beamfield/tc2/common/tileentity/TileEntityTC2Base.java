package com.beamfield.tc2.common.tileentity;

import java.util.HashSet;
import java.util.Set;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import cofh.api.tileentity.IReconfigurableFacing;
import cofh.core.block.TileCoFHBase;
import cofh.lib.util.helpers.BlockHelper;

import com.beamfield.tc2.common.interfaces.ITC2ReconfigurableSides;
import com.beamfield.tc2.common.interfaces.IWrenchable;
import com.beamfield.tc2.gui.ITC2GuiEntity;
import com.beamfield.tc2.network.CommonPacketHandler;
import com.beamfield.tc2.network.message.DeviceUpdateExposureMessage;
import com.beamfield.tc2.network.message.DeviceUpdateMessage;
import com.beamfield.tc2.network.message.DeviceUpdateRotationMessage;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public abstract class TileEntityTC2Base extends TileCoFHBase implements ITC2GuiEntity, IReconfigurableFacing, IWrenchable, ITC2ReconfigurableSides{
	private Set<EntityPlayer> updatePlayers;
	private int ticksSinceLastUpdate;
	private static final int ticksBetweenUpdates = 3;
	
	protected static final int SIDE_UNEXPOSED = -1;
	protected static final int[] kEmptyIntArray = new int[0];
	protected int facing;
	int[] exposures;
	public TileEntityTC2Base() {
		super();

		facing = ForgeDirection.NORTH.ordinal();

		exposures = new int[6];
		for(int i = 0; i < exposures.length; i++) {
			exposures[i] = SIDE_UNEXPOSED;
		}

		ticksSinceLastUpdate = 0;
		updatePlayers = new HashSet<EntityPlayer>();
	}
	@Override
	public int getFacing() { return facing; }
	@Override
	public boolean setFacing(int newFacing) {
		if(facing == newFacing) { return false; }

		if(!allowYAxisFacing() && (newFacing == ForgeDirection.UP.ordinal() || newFacing == ForgeDirection.DOWN.ordinal())) {
			return false;
		}
		
		facing = newFacing;
		if(!worldObj.isRemote) {
            CommonPacketHandler.INSTANCE.sendToAllAround(new DeviceUpdateRotationMessage(xCoord, yCoord, zCoord, facing), new NetworkRegistry.TargetPoint(worldObj.provider.dimensionId, xCoord, yCoord, zCoord, 50));
            this.markChunkDirty();
		}

		this.callNeighborBlockChange();
		return true;
	}
	public int getRotatedSide(int side) {
		return BlockHelper.ICON_ROTATION_MAP[facing][side];
	}
	
	@Override
	public boolean rotateBlock() {
		return setFacing(BlockHelper.SIDE_LEFT[facing]);
	}
	
	@Override
	public boolean onWrench(EntityPlayer player, int hitSide) {
		return rotateBlock();
	}
	
	@Override
	public boolean allowYAxisFacing() { return false; }
	@Override
	public void readFromNBT(NBTTagCompound tag) {
		super.readFromNBT(tag);
		
		if(tag.hasKey("facing")) {
			facing = Math.max(0, Math.min(5, tag.getInteger("facing")));
		}
		else {
			facing = 2;
		}
		if(tag.hasKey("exposures")) {
			int[] tagExposures = tag.getIntArray("exposures");
			assert(tagExposures.length == exposures.length);
			System.arraycopy(tagExposures, 0, exposures, 0, exposures.length);
		}
	}
	
	@Override
	public void writeToNBT(NBTTagCompound tag) {
		super.writeToNBT(tag);
		
		tag.setInteger("facing", facing);
		tag.setIntArray("exposures", exposures);
	}

	@Override
	public Packet getDescriptionPacket()
	{
		NBTTagCompound tagCompound = new NBTTagCompound();
		this.writeToNBT(tagCompound);
		
		return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 0, tagCompound);
	}

	@Override
	public void onDataPacket(NetworkManager network, S35PacketUpdateTileEntity packet) {
		this.readFromNBT(packet.func_148857_g());
	}
	
	@Override
	public void updateEntity() {
		super.updateEntity();
		
		if(!this.worldObj.isRemote && this.updatePlayers.size() > 0) {
			ticksSinceLastUpdate++;
			if(ticksSinceLastUpdate >= ticksBetweenUpdates) {
				sendUpdatePacket();
				ticksSinceLastUpdate = 0;
			}
		}
	}

	public abstract boolean isActive();
	
	@Override
	public void beginUpdatingPlayer(EntityPlayer player) {
		updatePlayers.add(player);
		sendUpdatePacketToClient(player);
	}

	@Override
	public void stopUpdatingPlayer(EntityPlayer player) {
		updatePlayers.remove(player);
	}
	
	protected IMessage getUpdatePacket() {
		NBTTagCompound childData = new NBTTagCompound();
		onSendUpdate(childData);
		
		return new DeviceUpdateMessage(xCoord, yCoord, zCoord, childData);
	}
	
	private void sendUpdatePacketToClient(EntityPlayer recipient) {
		if(this.worldObj.isRemote) { return; }

        CommonPacketHandler.INSTANCE.sendTo(getUpdatePacket(), (EntityPlayerMP)recipient);
		
	}
	
	private void sendUpdatePacket() {
		if(this.worldObj.isRemote) { return; }
		if(this.updatePlayers.size() <= 0) { return; }

		for(EntityPlayer player : updatePlayers) {
            CommonPacketHandler.INSTANCE.sendTo(getUpdatePacket(), (EntityPlayerMP)player);
		}
	}
	
	@Override
	public boolean setSide(int side, int config) {
		int rotatedSide = this.getRotatedSide(side);

		int numConfig = getNumConfig(side);
		if(config >= numConfig || config < -1) { config = SIDE_UNEXPOSED; }

		exposures[rotatedSide] = config;
		sendExposureUpdate();
		return true;
	}
	
	protected int getExposure(int worldSide) {
		return exposures[getRotatedSide(worldSide)];
	}

	public void setSides(int[] newExposures) {
		assert(newExposures.length == exposures.length);
		System.arraycopy(newExposures, 0, exposures, 0, newExposures.length);
		sendExposureUpdate(); 
	}
	
	@Override
	public boolean incrSide(int side) {
		return changeSide(side, 1);
	}
	
	@Override
	public boolean decrSide(int side) {
		return changeSide(side, -1);
	}
	
	private boolean changeSide(int side, int amount) {
		int rotatedSide = this.getRotatedSide(side);
		
		int numConfig = getNumConfig(side);
		if(numConfig <= 0) { return false; }
		
		int newConfig = exposures[rotatedSide] + amount;
		if(newConfig >= numConfig) { newConfig = SIDE_UNEXPOSED; }

		return setSide(side, newConfig);
	}
	
	@Override
	public boolean resetSides() {
		boolean changed = false;
		
		for(int i = 0; i < exposures.length; i++) {
			if(exposures[i] != SIDE_UNEXPOSED) {
				changed = true;
				exposures[i] = SIDE_UNEXPOSED;
			}
		}
		
		if(changed) {
			sendExposureUpdate();
		}
		
		return true;
	}
	
	private void sendExposureUpdate() {
		if(!this.worldObj.isRemote) {
            CommonPacketHandler.INSTANCE.sendToAllAround(new DeviceUpdateExposureMessage(xCoord, yCoord, zCoord, exposures), new NetworkRegistry.TargetPoint(worldObj.provider.dimensionId, xCoord, yCoord, zCoord, 50));
            this.markChunkDirty();
		}
		else {
			worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
		}

		this.callNeighborTileChange();
		this.callNeighborBlockChange();
	}
	protected void onSendUpdate(NBTTagCompound updateTag) {}
	
	public void onReceiveUpdate(NBTTagCompound updateTag) {}

	public String getName() {
		return this.getBlockType().getUnlocalizedName();
	}
	
	public int getType() {
		return getBlockMetadata();
	}
}
