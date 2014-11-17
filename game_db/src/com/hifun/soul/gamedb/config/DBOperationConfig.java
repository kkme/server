package com.hifun.soul.gamedb.config;

/**
 * DB操作配置;
 * 
 * @author crazyjohn
 * 
 */
public class DBOperationConfig {
	private static boolean operationSwitch = false;

	public static void turnOn() {
		operationSwitch = true;
	}

	public static void turnOff() {
		operationSwitch = false;
	}

	public static boolean isOpen() {
		return operationSwitch;
	}
}
