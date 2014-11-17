package com.hifun.soul.gameserver.building.assist;

import com.hifun.soul.common.constants.Loggers;
import com.hifun.soul.gameserver.building.AbstractBuilding;
import com.hifun.soul.gameserver.building.BuildingType;

/**
 * 建筑工厂
 * 
 * @author magicstone
 *
 */
public class BuildingFactory {
	/** 类默认构造器 */
	private BuildingFactory() {
		
	}

	/**
	 * 创建建筑对象 
	 * 
	 * @param buildingIndex
	 * @return
	 */
	public static AbstractBuilding createBuilding(int buildingType) {
		// 定义建筑对象
		AbstractBuilding building = null;
		
		BuildingType type = BuildingType.indexOf(buildingType);

		if (type != null) {
			try {
				building = type.getClazz().newInstance();
			} catch (Exception ex) {
				logError(ex.getMessage());
			}
		} else {
			return null;
		}

		building.init();
		
		return building;
	}

	/**
	 * 记录错误日志
	 * 
	 * @param msg
	 */
	private static void logError(String msg) {
		if (Loggers.BUILDING_LOGGER.isErrorEnabled()) {
			Loggers.BUILDING_LOGGER.error(msg);
		}
	}
}
