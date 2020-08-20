package com.datdeveloper.datfactionsdynmap;

import com.demmodders.datmoddingapi.structures.ChunkLocation;
import com.demmodders.factions.faction.Faction;
import com.demmodders.factions.faction.FactionManager;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.FMLCommonHandler;
import org.dynmap.DynmapCommonAPI;
import org.dynmap.DynmapCommonAPIListener;
import org.dynmap.markers.AreaMarker;
import org.dynmap.markers.MarkerAPI;
import org.dynmap.markers.MarkerSet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class DynmapAPI {
    private DynmapCommonAPI commonAPI = null;
    private MarkerAPI markerAPI = null;
    private MarkerSet markerSet = null;

    private HashMap<Integer, String> dimToWorld = new HashMap<>();

    private class Listener extends DynmapCommonAPIListener {
        @Override
        public void apiEnabled(DynmapCommonAPI api) {
            if (api != null) {
                DatFactionsDynmap.logs.info("API Enabled");
                commonAPI = api;
                markerAPI = api.getMarkerAPI();
                createMarkerSet();
            }
        }
    }

    public DynmapAPI() {
        DynmapCommonAPIListener.register(new Listener());
    }

    private void createMarkerSet() {
        markerSet = markerAPI.getMarkerSet(DatFactionsDynmap.MOD_ID);

        if (markerSet != null) {
            markerSet.setMarkerSetLabel("Faction Claims");
        } else {
            markerSet = markerAPI.createMarkerSet(DatFactionsDynmap.MOD_ID, "Faction Claims", null, false);
        }
    }

    public void mapWorldIDToName() {
        WorldServer[] worldsList = FMLCommonHandler.instance().getMinecraftServerInstance().worlds;

        for (WorldServer world : worldsList) {
            dimToWorld.put(world.provider.getDimension(), world.getWorldInfo().getWorldName());
            DatFactionsDynmap.logs.info("ID: " + world.provider.getDimension() + " Name: " + world.getWorldInfo().getWorldName());
        }
    }

    private int getColour(UUID FactionID) {
        String colour;
        if (FactionID == FactionManager.SAFEID) colour = DatConfig.generalSubCat.safeFillColour;
        else if (FactionID == FactionManager.WARID) colour = DatConfig.generalSubCat.warFillColour;
        else colour = DatConfig.generalSubCat.fillColour;
        return Integer.parseInt(colour, 16);
    }

    public void addMarkers(UUID FactionID, ArrayList<ChunkLocation> Locations) {
        FactionManager fMan = FactionManager.getInstance();
        String tooltip = "<div class=\"infowindow\">" +
                "<span>" + fMan.getFaction(FactionID).name + "</span>" +
                "<span>" + fMan.getFaction(FactionID).desc + "</span>" +
                "</div>";
        for (ChunkLocation location : Locations) {
            String world = dimToWorld.get(location.dim);
            String markerID = world + location.dim + "_" + FactionManager.makeChunkKey(location.x, location.z);

            if (world == null) {
                DatFactionsDynmap.logs.warn("Failed to find world that dimension " + location.dim + " maps to");
                continue;
            }
            double[] x = {(double) (location.x << 4), (double) ((location.x << 4) + 16)};
            double[] z = {(double) (location.z << 4), (double) ((location.z << 4) + 16)};

            AreaMarker marker = markerSet.createAreaMarker(markerID, tooltip, true, world, x, z, false);

            if (marker != null) {
                int colour = getColour(FactionID);
                marker.setLineStyle(DatConfig.generalSubCat.borderWeight, DatConfig.generalSubCat.borderOpacity, colour);
                marker.setFillStyle(DatConfig.generalSubCat.opacity, colour);
            } else {
                DatFactionsDynmap.logs.warn("Failed to create marker " + markerID);
            }
        }
    }



}
