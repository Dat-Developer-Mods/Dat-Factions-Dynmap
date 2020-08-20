package com.datdeveloper.datfactionsdynmap;

import com.demmodders.factions.api.event.InFactionEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(
        modid = DatFactionsDynmap.MOD_ID,
        name = DatFactionsDynmap.MOD_NAME,
        version = DatFactionsDynmap.VERSION,
        dependencies = "required-after:demfactions; required-after:dynmap",
        acceptableRemoteVersions = "*",
        serverSideOnly = true
)
public class DatFactionsDynmap {

    public static final String MOD_ID = "datfactionsdynmap";
    public static final String MOD_NAME = "Datfactions Dynmap Integration";
    public static final String VERSION = "0.1.0";

    public static final Logger logs = LogManager.getLogger(MOD_ID);

    private DynmapAPI dynmap;

    /**
     * This is the instance of your mod as created by Forge. It will never be null.
     */
    @Mod.Instance(MOD_ID)
    public static DatFactionsDynmap INSTANCE;

    /**
     * This is the second initialization event. Register custom recipes
     */
    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        dynmap = new DynmapAPI();
    }

    @Mod.EventHandler
    public void serverStart(FMLServerStartedEvent event) {
        logs.info("Mapping IDs");
        dynmap.mapWorldIDToName();
    }

    @SubscribeEvent
    public void FactionClaim(InFactionEvent.ChunkEvent.FactionClaimEvent event) {
        event.
    }
}
