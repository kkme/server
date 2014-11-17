package com.hifun.soul.gameserver.legion.enums;

/**
 * 军团科技类型
 * 
 * @author yandajun
 * 
 */
public enum LegionTechnologyType {
	/** 关卡打怪 */
	STAGE(1), 
	/** 战神之巅 */
	MARS(2), 
	/** 主城盗贼 */
	MAIN_CITY_MONSTER(3), 
	/** 奇遇答题 */
	QUESTION(4), 
	/** 战俘营互动 */
	PRISON(5), 
	/** 日常任务 */
	DAILY_QUEST(6), 
	/** 主城税收 */
	MAIN_CITY_LEVY(7), 
	/** 精炼厂 */
	MINE(8), 
	/** 通灵 */
	MAIN_CITY_COLLECT_STONE(9), 
	/** 巫术套现 */
	CRYSTAL_EXCHANGE(10), 
	/** 跑商 */
	ESCORT(11), 
	/** 训练场 */
	TRAINING(12);
	private int index;

	private LegionTechnologyType(int index) {
		this.index = index;
	}

	public int getIndex() {
		return index;
	}
}
