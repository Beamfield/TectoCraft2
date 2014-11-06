package com.beamfield.tc2.network;

import com.beamfield.tc2.network.message.DeviceUpdateExposureMessage;
import com.beamfield.tc2.network.message.DeviceUpdateRotationMessage;
import com.beamfield.tc2.utils.Reference;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;

public class CommonPacketHandler {
	 public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(Reference.CHANNEL.toLowerCase());
	 public static void init() {
		 INSTANCE.registerMessage(DeviceUpdateExposureMessage.Handler.class, DeviceUpdateExposureMessage.class, 5, Side.CLIENT);
		 INSTANCE.registerMessage(DeviceUpdateRotationMessage.Handler.class, DeviceUpdateRotationMessage.class, 3, Side.CLIENT);
	 }
}
