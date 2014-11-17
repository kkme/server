package com.hifun.soul.gameserver.util;

public class ColorUtils {
	public static final String RGB_WHITE = "ffffff";
	public static final String RGB_GREEN = "22ac38";
	public static final String RGB_BLUE = "00aeef";
	public static final String RGB_PURPLE = "ff00ff";
	public static final String RGB_YELLOW = "fff100";
	public static String getQualityColor(int quality){
		switch (quality) {
		case 1:
			return RGB_WHITE;
		case 2:
			return RGB_GREEN;
		case 3:
			return RGB_BLUE;
		case 4:
			return RGB_PURPLE;
		case 5:
			return RGB_YELLOW;
		default:
			return ColorUtils.RGB_WHITE;
		}
	}
}
