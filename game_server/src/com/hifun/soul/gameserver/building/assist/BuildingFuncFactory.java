package com.hifun.soul.gameserver.building.assist;

import com.hifun.soul.common.constants.Loggers;
import com.hifun.soul.gameserver.building.function.AbstractBuildingFunc;
import com.hifun.soul.gameserver.building.function.BuildingFuncType;

/**
 * 建筑功能工厂
 * 
 * @author magicstone
 *
 */
public class BuildingFuncFactory {
	/** 类默认构造器 */
	private BuildingFuncFactory() {
	}

	/**
	 * 创建建筑对象 
	 * 
	 * @param buildingIndex
	 * @return
	 */
	public static AbstractBuildingFunc createBuildingFunc(int funcId) {
		// 定义建筑对象
		AbstractBuildingFunc func = null;
		
		BuildingFuncType type = BuildingFuncType.indexOf(funcId);

		if (type != null) {
			try {
				func = type.getClazz().newInstance();
			} catch (Exception ex) {
				logError(ex.getMessage());
			}
		} else {
			return null;
		}

		func.init();
		
		return func;
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
