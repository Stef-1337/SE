package de.ostfalia.s1.lamp;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;
import javax.inject.Named;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.awt.*;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;


@Named
@SessionScoped
public class Java2NodeRedLampAdapter implements ILamp, Serializable {

    private static Lamp lampe = new Lamp();
    HashMap<List<Float>, String> xyWerte = new HashMap<>();
    private Requester requester = new Requester();
    private List<SelectItem> colors;
    private String selection;

    public Java2NodeRedLampAdapter() {
        init();
        initHashmap();
        try {
            getRequest();
        } catch (Exception ignored) {

        }
    }

    public List<SelectItem> getColors() {
        return colors;
    }

    public void setColors(List<SelectItem> colors) {
        this.colors = colors;
    }

    public String getSelection() {
        return selection;
    }

    public void setSelection(String selection) {
        this.selection = selection;
    }

    @PostConstruct
    public void init() {
        colors = new ArrayList<>();
        SelectItemGroup group1 = new SelectItemGroup("Rot");
        SelectItemGroup group2 = new SelectItemGroup("Orange");
        SelectItemGroup group3 = new SelectItemGroup("Gelb");
        SelectItemGroup group4 = new SelectItemGroup("Grün");
        SelectItemGroup group5 = new SelectItemGroup("Cyan");
        SelectItemGroup group6 = new SelectItemGroup("Blau");
        SelectItemGroup group7 = new SelectItemGroup("Lila");
        SelectItemGroup group8 = new SelectItemGroup("Pink");
        SelectItemGroup group9 = new SelectItemGroup("Weiß");

        SelectItem option11 = new SelectItem(new HueColor("lightsalmon", "#FFA07A", 255, 160, 122, (float) 0.5015, (float) 0.3530, Colors.RED), "lightsalmon");
        SelectItem option12 = new SelectItem(new HueColor("salmon", "	#FA8072", 250, 128, 114, (float) 0.5347, (float) 0.3256, Colors.RED), "salmon");
        SelectItem option13 = new SelectItem(new HueColor("darksalmon", "#E9967A", 233, 150, 122, (float) 0.4849, (float) 0.3476, Colors.RED), "darksalmon");
        SelectItem option14 = new SelectItem(new HueColor("lightcoral", "#F08080", 240, 128, 128, (float) 0.5065, (float) 0.3145, Colors.RED), "lightcoral");
        SelectItem option15 = new SelectItem(new HueColor("indianred", "#CD5C5C", 205, 92, 92, (float) 0.5475, (float) 0.3113, Colors.RED), "indianred");
        SelectItem option16 = new SelectItem(new HueColor("crimson", "#DC143C", 220, 20, 60, (float) 0.6435, (float) 0.3045, Colors.RED), "crimson");
        SelectItem option17 = new SelectItem(new HueColor("firebrick", "#B22222", 178, 34, 34, (float) 0.6554, (float) 0.3111, Colors.RED), "firebrick");
        SelectItem option18 = new SelectItem(new HueColor("red", "	#FF0000", 255, 0, 0, (float) 0.675, (float) 0.322, Colors.RED), "red");
        SelectItem option19 = new SelectItem(new HueColor("darkred", "#8B0000", 139, 0, 0, (float) 0.675, (float) 0.322, Colors.RED), "darkred");
        group1.setSelectItems(new SelectItem[]{option11, option12, option13, option14, option15, option16, option17, option18, option19});


        SelectItem option21 = new SelectItem(new HueColor("coral", "#FF7F50", 255, 127, 80, (float) 0.5754, (float) 0.3480, Colors.ORANGE), "coral");
        SelectItem option22 = new SelectItem(new HueColor("tomato", "#FF6347", 255, 99, 71, (float) 0.6111, (float) 0.3260, Colors.ORANGE), "tomato");
        SelectItem option23 = new SelectItem(new HueColor("orangered", "#FF4500", 255, 69, 0, (float) 0.6725, (float) 0.3230, Colors.ORANGE), "orangered");
        SelectItem option24 = new SelectItem(new HueColor("gold", "#FFD700", 255, 215, 0, (float) 0.4852, (float) 0.4619, Colors.ORANGE), "gold");
        SelectItem option25 = new SelectItem(new HueColor("orange", "#FFA500", 255, 165, 0, (float) 0.5567, (float) 0.4091, Colors.ORANGE), "orange");
        SelectItem option26 = new SelectItem(new HueColor("darkorange", "#FF8C00", 255, 140, 0, (float) 0.5921, (float) 0.3830, Colors.ORANGE), "darkorange");
        group2.setSelectItems(new SelectItem[]{option21, option22, option23, option24, option25, option26});


        SelectItem option31 = new SelectItem(new HueColor("lightyellow", "#FFFFE0", 255, 255, 224, (float) 0.3435, (float) 0.3612, Colors.YELLOW), "lightyellow");
        SelectItem option32 = new SelectItem(new HueColor("lemonchiffon", "#FFFACD", 255, 250, 205, (float) 0.3594, (float) 0.3756, Colors.YELLOW), "lemonchiffon");
        SelectItem option33 = new SelectItem(new HueColor("lightgoldenrodyellow", "#FAFAD2", 250, 250, 210, (float) 0.3502, (float) 0.3715, Colors.YELLOW), "lightgoldenrodyellow");
        SelectItem option34 = new SelectItem(new HueColor("papayawhip", "#FFEFD5", 255, 239, 213, (float) 0.3598, (float) 0.3546, Colors.YELLOW), "papayawhip");
        SelectItem option35 = new SelectItem(new HueColor("moccasin", "#FFE4B5", 255, 228, 181, (float) 0.3913, (float) 0.3755, Colors.YELLOW), "moccasin");
        SelectItem option36 = new SelectItem(new HueColor("peachpuff", "#FFDAB9", 255, 218, 185, (float) 0.3948, (float) 0.3597, Colors.YELLOW), "peachpuff");
        SelectItem option37 = new SelectItem(new HueColor("palegoldenrod", "#EEE8AA", 238, 232, 170, (float) 0.3762, (float) 0.3978, Colors.YELLOW), "palegoldenrod");
        SelectItem option38 = new SelectItem(new HueColor("khaki", "#F0E68C", 240, 230, 140, (float) 0.4023, (float) 0.4267, Colors.YELLOW), "khaki");
        SelectItem option39 = new SelectItem(new HueColor("darkkhaki", "#BDB76B", 189, 183, 107, (float) 0.4019, (float) 0.4324, Colors.YELLOW), "darkkhaki");
        SelectItem option310 = new SelectItem(new HueColor("yellow", "#FFFF00", 255, 255, 0, (float) 0.4325, (float) 0.5007, Colors.YELLOW), "yellow");
        group3.setSelectItems(new SelectItem[]{option31, option32, option33, option34, option35, option36, option37, option38, option39, option310});


        SelectItem option41 = new SelectItem(new HueColor("lawngreen", "#7CFC00", 124, 252, 0, (float) 0.4091, (float) 0.518, Colors.GREEN), "lawngreen");
        SelectItem option42 = new SelectItem(new HueColor("chartreuse", "#7FFF00", 127, 255, 0, (float) 0.4091, (float) 0.518, Colors.GREEN), "chartreuse");
        SelectItem option43 = new SelectItem(new HueColor("limegreen", "#32CD32", 50, 205, 50, (float) 0.4091, (float) 0.518, Colors.GREEN), "limegreen");
        SelectItem option44 = new SelectItem(new HueColor("lime", "#00FF00", 0, 255, 0, (float) 0.4091, (float) 0.518, Colors.GREEN), "lime");
        SelectItem option45 = new SelectItem(new HueColor("forestgreen", "#228B22", 34, 139, 34, (float) 0.4091, (float) 0.518, Colors.GREEN), "forestgreen");
        SelectItem option46 = new SelectItem(new HueColor("green", "#008000", 0, 128, 0, (float) 0.4091, (float) 0.518, Colors.GREEN), "green");
        SelectItem option47 = new SelectItem(new HueColor("darkgreen", "#006400", 0, 100, 0, (float) 0.4091, (float) 0.518, Colors.GREEN), "darkgreen");
        SelectItem option48 = new SelectItem(new HueColor("greenyellow", "#ADFF2F", 173, 255, 47, (float) 0.4091, (float) 0.518, Colors.GREEN), "greenyellow");
        SelectItem option49 = new SelectItem(new HueColor("yellowgreen", "#9ACD32", 154, 205, 50, (float) 0.4091, (float) 0.518, Colors.GREEN), "yellowgreen");
        SelectItem option410 = new SelectItem(new HueColor("springgreen", "#00FF7F", 0, 255, 127, (float) 0.3883, (float) 0.4771, Colors.GREEN), "springgreen");
        SelectItem option411 = new SelectItem(new HueColor("mediumspringgreen", "#00FA9A", 0, 250, 154, (float) 0.3620, (float) 0.4250, Colors.GREEN), "mediumspringgreen");
        SelectItem option412 = new SelectItem(new HueColor("lightgreen", "#90EE90", 144, 238, 144, (float) 0.3673, (float) 0.4356, Colors.GREEN), "lightgreen");
        SelectItem option413 = new SelectItem(new HueColor("palegreen", "#98FB98", 152, 251, 152, (float) 0.3674, (float) 0.4358, Colors.GREEN), "palegreen");
        SelectItem option414 = new SelectItem(new HueColor("darkseagreen", "#8FBC8F", 143, 188, 143, (float) 0.3423, (float) 0.3862, Colors.GREEN), "darkseagreen");
        SelectItem option415 = new SelectItem(new HueColor("mediumseagreen", "#3CB371", 60, 179, 113, (float) 0.3584, (float) 0.4180, Colors.GREEN), "mediumseagreen");
        SelectItem option416 = new SelectItem(new HueColor("seagreen", "#2E8B57", 46, 139, 87, (float) 0.3580, (float) 0.4172, Colors.GREEN), "seagreen");
        SelectItem option417 = new SelectItem(new HueColor("olive", "#808000", 128, 128, 0, (float) 0.4325, (float) 0.5007, Colors.GREEN), "olive");
        SelectItem option418 = new SelectItem(new HueColor("darkolivegreen", "#556B2F", 85, 107, 47, (float) 0.3886, (float) 0.4776, Colors.GREEN), "darkolivegreen");
        SelectItem option419 = new SelectItem(new HueColor("olivedrab", "#6B8E23", 107, 142, 35, (float) 0.4091, (float) 0.518, Colors.GREEN), "olivedrab");
        group4.setSelectItems(new SelectItem[]{option41, option42, option43, option44, option45, option46, option47, option48, option49, option410, option411, option412, option413, option414, option415, option416, option417, option418, option419});

        SelectItem option51 = new SelectItem(new HueColor("lightcyan", "#E0FFFF", 224, 255, 255, (float) 0.3096, (float) 0.3216, Colors.CYAN), "lightcyan");
        SelectItem option52 = new SelectItem(new HueColor("cyan", "#00FFFF", 0, 255, 255, (float) 0.2857, (float) 0.2744, Colors.CYAN), "cyan");
        SelectItem option53 = new SelectItem(new HueColor("aqua", "#00FFFF", 0, 255, 255, (float) 0.2857, (float) 0.2744, Colors.CYAN), "aqua");
        SelectItem option54 = new SelectItem(new HueColor("aquamarine", "#7FFFD4", 127, 255, 212, (float) 0.3230, (float) 0.3480, Colors.CYAN), "aquamarine");
        SelectItem option55 = new SelectItem(new HueColor("mediumaquamarine", "#66CDAA", 102, 205, 170, (float) 0.3231, (float) 0.3483, Colors.CYAN), "mediumaquamarine");
        SelectItem option56 = new SelectItem(new HueColor("paleturquoise", "#AFEEEE", 175, 238, 238, (float) 0.3032, (float) 0.3090, Colors.CYAN), "paleturquoise");
        SelectItem option57 = new SelectItem(new HueColor("turquoise", "#40E0D0", 64, 224, 208, (float) 0.3005, (float) 0.3036, Colors.CYAN), "turquoise");
        SelectItem option58 = new SelectItem(new HueColor("mediumturquoise", "#48D1CC", 72, 209, 204, (float) 0.2937, (float) 0.2902, Colors.CYAN), "mediumturquoise");
        SelectItem option59 = new SelectItem(new HueColor("darkturquoise", "#00CED1", 0, 206, 209, (float) 0.2834, (float) 0.2698, Colors.CYAN), "darkturquoise");
        SelectItem option510 = new SelectItem(new HueColor("lightseagreen", "#20B2AA", 32, 178, 170, (float) 0.2944, (float) 0.2916, Colors.CYAN), "lightseagreen");
        SelectItem option511 = new SelectItem(new HueColor("cadetblue", "#5F9EA0", 95, 158, 160, (float) 0.2963, (float) 0.2953, Colors.CYAN), "cadetblue");
        SelectItem option512 = new SelectItem(new HueColor("darkcyan", "#008B8B", 0, 139, 139, (float) 0.2857, (float) 0.2744, Colors.CYAN), "darkcyan");
        SelectItem option513 = new SelectItem(new HueColor("teal", "#008080", 0, 128, 128, (float) 0.2857, (float) 0.2744, Colors.CYAN), "teal");
        group5.setSelectItems(new SelectItem[]{option51, option52, option53, option54, option55, option56, option57, option58, option59, option510, option511, option512, option513});

        SelectItem option61 = new SelectItem(new HueColor("powderblue", "#B0E0E6", 176, 224, 230, (float) 0.3015, (float) 0.3057, Colors.BLUE), "powderblue");
        SelectItem option62 = new SelectItem(new HueColor("lightblue", "#ADD8E6", 173, 216, 230, (float) 0.2969, (float) 0.2964, Colors.BLUE), "lightblue");
        SelectItem option63 = new SelectItem(new HueColor("lightskyblue", "#87CEFA", 135, 206, 250, (float) 0.2706, (float) 0.2447, Colors.BLUE), "lightskyblue");
        SelectItem option64 = new SelectItem(new HueColor("skyblue", "#87CEEB", 135, 206, 235, (float) 0.2788, (float) 0.2630, Colors.BLUE), "skyblue");
        SelectItem option65 = new SelectItem(new HueColor("deepskyblue", "#00BFFF", 0, 191, 255, (float) 0.2425, (float) 0.1892, Colors.BLUE), "deepskyblue");
        SelectItem option66 = new SelectItem(new HueColor("lightsteelblue", "#B0C4DE", 176, 196, 222, (float) 0.2926, (float) 0.2880, Colors.BLUE), "lightsteelblue");
        SelectItem option67 = new SelectItem(new HueColor("dodgerblue", "#1E90FF", 30, 144, 255, (float) 0.2124, (float) 0.1297, Colors.BLUE), "dodgerblue");
        SelectItem option68 = new SelectItem(new HueColor("cornflowerblue", "#6495ED", 100, 149, 237, (float) 0.2355, (float) 0.1753, Colors.BLUE), "cornflowerblue");
        SelectItem option69 = new SelectItem(new HueColor("steelblue", "#4682B4", 70, 130, 180, (float) 0.2491, (float) 0.2021, Colors.BLUE), "steelblue");
        SelectItem option610 = new SelectItem(new HueColor("royalblue", "#4169E1", 65, 105, 225, (float) 0.2051, (float) 0.1152, Colors.BLUE), "royalblue");
        SelectItem option611 = new SelectItem(new HueColor("blue", "#0000FF", 0, 0, 255, (float) 0.167, (float) 0.04, Colors.BLUE), "blue");
        SelectItem option612 = new SelectItem(new HueColor("mediumblue", "#0000CD", 0, 0, 205, (float) 0.167, (float) 0.04, Colors.BLUE), "mediumblue");
        SelectItem option613 = new SelectItem(new HueColor("darkblue", "#00008B", 0, 0, 139, (float) 0.167, (float) 0.04, Colors.BLUE), "darkblue");
        SelectItem option614 = new SelectItem(new HueColor("navy", "#000080", 0, 0, 128, (float) 0.167, (float) 0.04, Colors.BLUE), "navy");
        SelectItem option615 = new SelectItem(new HueColor("midnightblue", "#191970", 25, 25, 112, (float) 0.1821, (float) 0.0698, Colors.BLUE), "midnightblue");
        SelectItem option616 = new SelectItem(new HueColor("mediumslateblue", "#7B68EE", 123, 104, 238, (float) 0.2186, (float) 0.1419, Colors.BLUE), "mediumslateblue");
        SelectItem option617 = new SelectItem(new HueColor("slateblue", "#6A5ACD", 106, 90, 205, (float) 0.2198, (float) 0.1443, Colors.BLUE), "slateblue");
        SelectItem option618 = new SelectItem(new HueColor("darkslateblue", "#483D8B", 72, 61, 139, (float) 0.2235, (float) 0.1502, Colors.BLUE), "darkslateblue");
        group6.setSelectItems(new SelectItem[]{option61, option62, option63, option64, option65, option66, option67, option68, option69, option610, option611, option612, option613, option614, option615, option616, option617, option618});

        SelectItem option71 = new SelectItem(new HueColor("lavender", "#E6E6FA", 230, 230, 250, (float) 0.3085, (float) 0.3071, Colors.PURPLE), "lavender");
        SelectItem option72 = new SelectItem(new HueColor("thistle", "#D8BFD8", 216, 191, 216, (float) 0.3342, (float) 0.2970, Colors.PURPLE), "thistle");
        SelectItem option73 = new SelectItem(new HueColor("plum", "#DDA0DD", 221, 160, 221, (float) 0.3495, (float) 0.2545, Colors.PURPLE), "plum");
        SelectItem option74 = new SelectItem(new HueColor("violet", "#EE82EE", 238, 130, 238, (float) 0.3645, (float) 0.2128, Colors.PURPLE), "violet");
        SelectItem option75 = new SelectItem(new HueColor("orchid", "#DA70D6", 218, 112, 214, (float) 0.3716, (float) 0.2102, Colors.PURPLE), "orchid");
        SelectItem option76 = new SelectItem(new HueColor("fuchsia", "#FF00FF", 255, 0, 255, (float) 0.3826, (float) 0.1597, Colors.PURPLE), "fuchsia");
        SelectItem option77 = new SelectItem(new HueColor("magenta", "#FF00FF", 255, 0, 255, (float) 0.3826, (float) 0.1597, Colors.PURPLE), "magenta");
        SelectItem option78 = new SelectItem(new HueColor("mediumorchid", "#BA55D3", 186, 85, 211, (float) 0.3362, (float) 0.1743, Colors.PURPLE), "mediumorchid");
        SelectItem option79 = new SelectItem(new HueColor("mediumpurple", "#9370DB", 147, 112, 219, (float) 0.2629, (float) 0.1772, Colors.PURPLE), "mediumpurple");
        SelectItem option710 = new SelectItem(new HueColor("blueviolet", "#8A2BE2", 138, 43, 226, (float) 0.2524, (float) 0.1062, Colors.PURPLE), "blueviolet");
        SelectItem option711 = new SelectItem(new HueColor("darkviolet", "#9400D3", 148, 0, 211, (float) 0.2852, (float) 0.1086, Colors.PURPLE), "darkviolet");
        SelectItem option712 = new SelectItem(new HueColor("darkorchid", "#9932CC", 153, 50, 204, (float) 0.2986, (float) 0.1335, Colors.PURPLE), "darkorchid");
        SelectItem option713 = new SelectItem(new HueColor("darkmagenta", "#8B008B", 139, 0, 139, (float) 0.3826, (float) 0.1597, Colors.PURPLE), "darkmagenta");
        SelectItem option714 = new SelectItem(new HueColor("purple", "#800080", 128, 0, 128, (float) 0.3826, (float) 0.1597, Colors.PURPLE), "purple");
        SelectItem option715 = new SelectItem(new HueColor("indigo", "#4B0082", 75, 0, 130, (float) 0.2485, (float) 0.0917, Colors.PURPLE), "indigo");
        group7.setSelectItems(new SelectItem[]{option71, option72, option73, option74, option75, option76, option77, option78, option79, option710, option711, option712, option713, option714, option715});

        SelectItem option81 = new SelectItem(new HueColor("pink", "#FFC0CB", 255, 192, 203, (float) 0.3947, (float) 0.3114, Colors.PINK), "pink");
        SelectItem option82 = new SelectItem(new HueColor("lightpink", "#FFB6C1", 255, 182, 193, (float) 0.4105, (float) 0.3102, Colors.PINK), "lightpink");
        SelectItem option83 = new SelectItem(new HueColor("hotpink", "#FF69B4", 255, 105, 180, (float) 0.4691, (float) 0.2468, Colors.PINK), "hotpink");
        SelectItem option84 = new SelectItem(new HueColor("deeppink", "#FF1493", 255, 20, 147, (float) 0.5388, (float) 0.2464, Colors.PINK), "deeppink");
        SelectItem option85 = new SelectItem(new HueColor("palevioletred", "#DB7093", 219, 112, 147, (float) 0.4657, (float) 0.2773, Colors.PINK), "palevioletred");
        SelectItem option86 = new SelectItem(new HueColor("mediumvioletred", "#C71585", 199, 21, 133, (float) 0.4997, (float) 0.2247, Colors.PINK), "mediumvioletred");
        group8.setSelectItems(new SelectItem[]{option81, option82, option83, option84, option85, option86});

        SelectItem option91 = new SelectItem(new HueColor("white", "#FFFFFF", 255, 255, 255, (float) 0.3227, (float) 0.3287, Colors.WHITE), "white");
        SelectItem option92 = new SelectItem(new HueColor("snow", "#FFFAFA", 255, 250, 250, (float) 0.3280, (float) 0.3286, Colors.WHITE), "snow");
        SelectItem option93 = new SelectItem(new HueColor("honeydew", "#F0FFF0", 240, 255, 240, (float) 0.3210, (float) 0.3441, Colors.WHITE), "honeydew");
        SelectItem option94 = new SelectItem(new HueColor("mintcream", "#F5FFFA", 245, 255, 250, (float) 0.3162, (float) 0.3346, Colors.WHITE), "mintcream");
        SelectItem option95 = new SelectItem(new HueColor("azure", "#F0FFFF", 240, 255, 255, (float) 0.3125, (float) 0.3274, Colors.WHITE), "azure");
        SelectItem option96 = new SelectItem(new HueColor("aliceblue", "#F0F8FF", 240, 248, 255, (float) 0.3098, (float) 0.3220, Colors.WHITE), "aliceblue");
        SelectItem option97 = new SelectItem(new HueColor("ghostwhite", "#F8F8FF", 248, 248, 255, (float) 0.3098, (float) 0.3220, Colors.WHITE), "ghostwhite");
        SelectItem option98 = new SelectItem(new HueColor("whitesmoke", "#F5F5F5", 245, 245, 245, (float) 0.3227, (float) 0.3290, Colors.WHITE), "whitesmoke");
        SelectItem option99 = new SelectItem(new HueColor("seashell", "#FFF5EE", 255, 245, 238, (float) 0.3385, (float) 0.3353, Colors.WHITE), "seashell");
        SelectItem option910 = new SelectItem(new HueColor("beige", "#F5F5DC", 245, 245, 220, (float) 0.3401, (float) 0.3559, Colors.WHITE), "beige");
        SelectItem option911 = new SelectItem(new HueColor("oldlace", "#FDF5E6", 253, 245, 230, (float) 0.3377, (float) 0.3376, Colors.WHITE), "oldlace");
        SelectItem option912 = new SelectItem(new HueColor("floralwhite", "#FFFAF0", 255, 250, 240, (float) 0.3349, (float) 0.3388, Colors.WHITE), "floralwhite");
        SelectItem option913 = new SelectItem(new HueColor("ivory", "#FFFFF0", 255, 255, 240, (float) 0.3327, (float) 0.3444, Colors.WHITE), "ivory");
        SelectItem option914 = new SelectItem(new HueColor("antiquewhite", "#FAEBD7", 250, 235, 215, (float) 0.3546, (float) 0.3488, Colors.WHITE), "antiquewhite");
        SelectItem option915 = new SelectItem(new HueColor("linen", "#FAF0E6", 250, 240, 230, (float) 0.3410, (float) 0.3386, Colors.WHITE), "linen");
        SelectItem option916 = new SelectItem(new HueColor("lavenderblush", "#FFF0F5", 255, 240, 245, (float) 0.3357, (float) 0.3226, Colors.WHITE), "lavenderblush");
        SelectItem option917 = new SelectItem(new HueColor("mistyrose", "#FFE4E1", 255, 228, 225, (float) 0.4212, (float) 0.1823, Colors.WHITE), "mistyrose");
        group9.setSelectItems(new SelectItem[]{option91, option92, option93, option94, option95, option96, option97, option98, option99, option910, option911, option912, option913, option914, option915, option916, option917});

        colors.add(group1);
        colors.add(group2);
        colors.add(group3);
        colors.add(group4);
        colors.add(group5);
        colors.add(group6);
        colors.add(group7);
        colors.add(group8);
        colors.add(group9);
    }

    public void initHashmap() {
        try {
            InputStream stream = getClass().getResourceAsStream("/Farbcodes.csv");

            BufferedReader br = new BufferedReader(new InputStreamReader(stream));

            String line;
            int i = 0;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(";");
                List<Float> floatList = new ArrayList<>(2);
                floatList.add(Float.valueOf(values[5]));
                floatList.add(Float.valueOf(values[6]));
                xyWerte.put(floatList, values[0]);
            }
//            List<Float> floatList = new ArrayList<>(2);
//            floatList.add((float) 0.4212);
//            floatList.add((float) 0.1823);

            System.out.println(xyWerte.size());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void commitXY(String string) {
    }

    public Lamp getLampe() {
        return lampe;
    }

    public void setLampe(Lamp lampe) {
        this.lampe = lampe;
    }

    public Requester getRequester() {
        return requester;
    }

    public void setRequester(Requester requester) {
        this.requester = requester;
    }

    @Override
    public void switchOn() {
        lampe.switchOn();
    }

    @Override
    public void switchOn(float intensity) {
        lampe.switchOn();
    }

    @Override
    public void switchOn(Color color) {
        lampe.switchOn();
    }

    @Override
    public void switchOff() {
        lampe.switchOn();
    }

    @Override
    public Color getColor() {
        return lampe.getColor();
    }

    @Override
    public void setColor(Color color) {
        lampe.setColor(color);
    }

    @Override
    public float getIntensity() {
        return lampe.getIntensity();
    }

    @Override
    public void setIntensity(float intensity) {
        lampe.setIntensity(intensity);
    }

    @Override
    public boolean getState() {
        return lampe.getState();
    }

    public void putRequest() {
        String result = "{" + "\"name\":" + "\"" + lampe.getName() + "\"" + "," + "\"on\": {\"on\": " + lampe.getState() + "}," + " \"dimming\":{\"brightness\":" +
                lampe.getIntensity() + "}," + "\"color\":{\"xy\":{\"x\":" + lampe.getX() + ",\"y\":" + lampe.getY() + "}}}";
        requester.setLampState(3, result);
    }

    public Lamp fetchCurrentLampStatus() {
        try {

            JsonObject request = requester.getState(new URL(requester.base));
            Lamp lamp = new Lamp();

            String dataString = request.get("data").toString();
            dataString = dataString.substring(1, dataString.length() - 1);

            JsonReader reader = Json.createReader(new ByteArrayInputStream(dataString.getBytes()));
            JsonObject data = reader.readObject();

            JsonObject metadata = data.getJsonObject("metadata");

            lamp.setName(metadata.getString("name"));
            lamp.setState(data.getJsonObject("on").getBoolean("on"));
            lamp.setIntensity(data.getJsonObject("dimming").getInt("brightness"));

            JsonObject xyObject = data.getJsonObject("color").getJsonObject("xy");

            float x = Float.parseFloat(xyObject.get("x").toString()), y = Float.parseFloat(xyObject.get("y").toString());

            lamp.setX(x);
            lamp.setY(y);

            new Color(0, 0, 0);

            String colorName = "null";

            if (xyWerte.isEmpty()) {
                System.out.println("Init hashmap");
                initHashmap();
            }

            for (List<Float> keys : xyWerte.keySet())
                if (keys.contains(x) && keys.contains(y)) {
                    colorName = xyWerte.get(keys);

                    break;
                }

            lamp.setColorName(colorName);

            return lamp;
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
        }

        return null;
    }

    public void getRequest() throws Exception {
        JsonObject request = requester.getState(new URL(requester.base));
        String string = "{" + request.toString().substring(86, 934) + "}";
        InputStream is = new ByteArrayInputStream(string.getBytes(StandardCharsets.UTF_8));
        JsonReader jsonReader = Json.createReader(is);
        JsonObject o = jsonReader.readObject();
        System.out.println(o.toString());
        JsonObject o2 = o.getJsonObject("metadata");
        lampe.setName(o.getJsonObject("metadata").getString("name"));
        lampe.setState(o.getJsonObject("on").getBoolean("on"));
        lampe.setIntensity(o.getJsonObject("dimming").getInt("brightness"));
        String s = o.getJsonObject("color").getJsonObject("xy").toString().substring(1, 22);
        List<Float> fl = new ArrayList<>(2);
        String[] strings = s.split(",");
        for (String e :
                strings) {
            e = e.substring(4);
            fl.add(Float.parseFloat(e));
        }
        lampe.setX(fl.get(0));
        lampe.setY(fl.get(1));
        System.out.println(xyWerte.get(fl));
        lampe.setColorName(xyWerte.get(fl));
        System.out.println("Name :" + lampe.getColorName());
    }

    public static void main(String[] args) throws Exception {
        File f = new File("src/main/resources/Farbcodes.csv");
        System.out.println(f.getAbsolutePath());

        //j.getRequest();
    }

}
