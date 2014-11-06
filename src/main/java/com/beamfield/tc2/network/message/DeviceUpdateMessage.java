package com.beamfield.tc2.network.message;

import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

import com.beamfield.tc2.common.tileentity.TileEntityTC2Base;
import com.beamfield.tc2.network.message.base.WorldMessageClient;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class DeviceUpdateMessage extends WorldMessageClient{
	private NBTTagCompound compound;

    public DeviceUpdateMessage() { super(); compound = null; }
    
    public DeviceUpdateMessage(int x, int y, int z, NBTTagCompound compound) {
    	super(x, y, z);
        this.compound = compound;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
    	super.fromBytes(buf);
        compound = ByteBufUtils.readTag(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
    	super.toBytes(buf);
        ByteBufUtils.writeTag(buf, compound);
    }

    public static class Handler extends WorldMessageClient.Handler<DeviceUpdateMessage> {
        @Override
        public IMessage handleMessage(DeviceUpdateMessage message, MessageContext ctx, TileEntity te) {
            if(te instanceof TileEntityTC2Base) {
                ((TileEntityTC2Base)te).onReceiveUpdate(message.compound);
            }
            return null;
        }
    }
}
