package com.icrazyblaze.twitchmod;

import com.icrazyblaze.twitchmod.chat.ChatPicker;
import com.icrazyblaze.twitchmod.command.TTVCommand;
import com.icrazyblaze.twitchmod.network.GuiMessage;
import com.icrazyblaze.twitchmod.network.GuiMessageHandler;
import com.icrazyblaze.twitchmod.network.PacketHandler;
import com.icrazyblaze.twitchmod.util.ConfigManager;
import com.icrazyblaze.twitchmod.util.Reference;
import com.icrazyblaze.twitchmod.util.TickHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.relauncher.Side;
import org.apache.logging.log4j.Logger;


@Mod(modid = Reference.MOD_ID, name = Reference.NAME, version = Reference.VERSION, updateJSON = Reference.UPDATE_JSON, guiFactory = Reference.GUI_FACTORY, dependencies = "after:vanillafix")
public class Main {

    public static Logger logger;
    public static Configuration config;

    @Instance
    public static Main instance;

    @EventHandler
    public static void preInit(FMLPreInitializationEvent event) {

        logger = event.getModLog();
        config = new Configuration(event.getSuggestedConfigurationFile());
        ConfigManager.initialize();

        PacketHandler.INSTANCE.registerMessage(GuiMessageHandler.class, GuiMessage.class, 0, Side.SERVER);

        ChatPicker.loadBlacklistFile(); // This will make sure we generate the blacklist file on startup
        //ChatPicker.initCommands(); // Register all commands

    }

    @EventHandler
    public static void serverStarting(FMLServerStartingEvent event) {

        event.registerServerCommand(new TTVCommand());

    }

    @EventHandler
    public void init(FMLInitializationEvent event) {

        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(new TickHandler());
        MinecraftForge.EVENT_BUS.register(new BotCommands());

    }

}