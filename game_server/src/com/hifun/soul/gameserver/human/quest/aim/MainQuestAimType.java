package com.hifun.soul.gameserver.human.quest.aim;

import java.util.HashMap;
import java.util.Map;

import com.hifun.soul.gameserver.human.quest.IQuestAimType;
import com.hifun.soul.gameserver.human.quest.aim.counter.CollectItemAimCounter;
import com.hifun.soul.gameserver.human.quest.aim.counter.IQuestAimCounter;
import com.hifun.soul.gameserver.human.quest.aim.counter.QuestAimKillMonsterCounter;
import com.hifun.soul.gameserver.human.quest.aim.counter.UseItemAimCounter;

/**
 * 主线任务目标类型;
 * 
 * @author crazyjohn
 * 
 */
public enum MainQuestAimType implements IQuestAimType{
	/** 非法的目标类型 */
	INVALID_TYPE(0) {

		@Override
		public IQuestAimCounter createAimCounter(AimInfo aim, int index) {
			// TODO Auto-generated method stub
			return null;
		}

	},
	/** 杀怪目标 */
	AIM_KILL_MONSTER(1) {
		@Override
		public IQuestAimCounter createAimCounter(AimInfo aim, int index) {
			return new QuestAimKillMonsterCounter(AIM_KILL_MONSTER, aim, index);
		}

	},
	/** 收集物品类型 */
	COLLECT_ITEM(2) {

		@Override
		public IQuestAimCounter createAimCounter(AimInfo aim, int index) {
			return new CollectItemAimCounter(COLLECT_ITEM, aim, index);
		}

	},
	USE_ITEM(3) {

		@Override
		public IQuestAimCounter createAimCounter(AimInfo aim, int index) {
			return new UseItemAimCounter(USE_ITEM, aim, index);
		}

	},;

	private int aimType;
	private static Map<Integer, MainQuestAimType> aimTypes = new HashMap<Integer, MainQuestAimType>();

	static {
		for (MainQuestAimType type : MainQuestAimType.values()) {
			aimTypes.put(type.getAimType(), type);
		}
	}

	MainQuestAimType(int aimType) {
		this.aimType = aimType;
	}

	public int getAimType() {
		return this.aimType;
	}

	public abstract IQuestAimCounter createAimCounter(AimInfo aim, int index);

	public static MainQuestAimType typeOf(int aimType) {
		return aimTypes.get(aimType);
	}
}
