package com.zerofall.ezstorage;

import com.zerofall.ezstorage.config.EZConfig;
import com.zerofall.ezstorage.crafting.CraftingManager;
import com.zerofall.ezstorage.events.CoreEvents;
import com.zerofall.ezstorage.events.SecurityEvents;
import com.zerofall.ezstorage.gui.GuiHandler;
import com.zerofall.ezstorage.init.EZBlocks;
import com.zerofall.ezstorage.init.EZItems;
import com.zerofall.ezstorage.network.EZNetwork;
import com.zerofall.ezstorage.proxy.CommonProxy;
import com.zerofall.ezstorage.ref.EZTab;
import com.zerofall.ezstorage.ref.Log;
import com.zerofall.ezstorage.ref.RefStrings;
import com.zerofall.ezstorage.util.EZStorageUtils;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;

/** EZStorage main mod class */
@Mod(modid = RefStrings.MODID, name = RefStrings.NAME, version = RefStrings.VERSION)
public class EZStorage {

	@Mod.Instance(RefStrings.MODID)
	public static EZStorage instance;

	@SidedProxy(clientSide = RefStrings.CLIENT_PROXY, serverSide = RefStrings.SERVER_PROXY)
	public static CommonProxy proxy;

	public static SimpleNetworkWrapper nw;
	public static Configuration config;

	public EZTab creativeTab;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		config = new Configuration(event.getSuggestedConfigurationFile());
		EZConfig.syncConfig();
		this.creativeTab = new EZTab();
		EZBlocks.mainRegistry();
		EZItems.mainRegistry();
		NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandler());
		nw = EZNetwork.registerNetwork();
		MinecraftForge.EVENT_BUS.register(new CoreEvents());
		MinecraftForge.EVENT_BUS.register(new SecurityEvents());
		proxy.registerRenders();
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		CraftingManager.mainRegistry();
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		EZStorageUtils.getModNameFromID(RefStrings.MODID); // build the mod map
		Log.logger.info("Loading complete.");
	}

}
