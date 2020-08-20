package com.datdeveloper.datfactionsdynmap;

import net.minecraftforge.common.config.Config;

@Config(modid = DatFactionsDynmap.MOD_ID)
public class DatConfig {
    public static General generalSubCat = new General();

    public static class General {
        @Config.Name("Border Weight")
        @Config.Comment("The thickness of the border to each claimed chunk")
        @Config.RangeInt(min=0, max=10)
        int borderWeight = 0;

        @Config.Name("Claim Opacity")
        @Config.Comment("The opacity of the claim overlay")
        @Config.RangeDouble(min=0.D, max=1.D)
        double opacity = 0.4;

        @Config.Name("Claim Border Opacity")
        @Config.Comment("The opacity of the claim border")
        @Config.RangeDouble(min=0.D, max=1.D)
        double borderOpacity = 0.5;

        @Config.Name("Safezone Fill Colour")
        @Config.Comment("The colour of the safezone")
        String safeFillColour = "FFA500";

        @Config.Name("Fill Colour")
        @Config.Comment("The colour of the warzone")
        String warFillColour = "FF0000";

        @Config.Name("Faction Fill Colour")
        @Config.Comment("The colour of faction claims")
        String fillColour = "00FF00";


    }
}
