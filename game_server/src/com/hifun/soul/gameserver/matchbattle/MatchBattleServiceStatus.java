package com.hifun.soul.gameserver.matchbattle;

import com.hifun.soul.core.enums.IndexedEnum;

/**
 * 匹配战服务状态
 * @author magicstone
 *
 */
public enum MatchBattleServiceStatus implements IndexedEnum {
	/** 正在初始化 */
	INIT(1),
	/** 准备中 */
	READY(2),
	/** 进行中 */
	RUNNING(4),
	/** 已结束 */
	FINISH(8)
	;
	private int index;
	private MatchBattleServiceStatus(int index){
		this.index = index;
	}
	public int getIndex(){
		return index;
	}
}
