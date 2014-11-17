package com.hifun.soul.gameserver.battle.callback;

import com.hifun.soul.core.annotation.AutoCreateClientEnumType;
import com.hifun.soul.core.annotation.ClientEnumComment;
import com.hifun.soul.core.enums.IndexedEnum;

/**
 * 客户端场景类型;
 * 
 * @author crazyjohn
 * 
 */
@AutoCreateClientEnumType
public enum ClientGameSceneType implements IndexedEnum {
	/** 主场景 */
	@ClientEnumComment(comment="主场景 ")
	TOWN_VIEW(1),
	/** 竞技场 */
	@ClientEnumComment(comment="竞技场")
	ARENA_VIEW(2),
	/** BOSS战 */
	@ClientEnumComment(comment="BOSS战")
	BOSS_WAR_VIEW(3),
	@ClientEnumComment(comment="关卡场景")
	AREA_VIEW(4),
	@ClientEnumComment(comment="矿场场景")
	MINE_VIEW(5),
	@ClientEnumComment(comment="精英场景")
	ELITE_STAGE_VIEW(6),
	@ClientEnumComment(comment="魔石混战场景")
	MATCH_BATTLE_VIEW(7),
	@ClientEnumComment(comment="战俘营场景")
	PRISON_VIEW(8),
	@ClientEnumComment(comment="角斗场场景")
	ABATTOIR_VIEW(9),
	@ClientEnumComment(comment="嗜血神殿场景")
	BLOOD_TEMPLE_VIEW(10),
	@ClientEnumComment(comment="战神之巅场景")
	MARS_VIEW(11),
	@ClientEnumComment(comment="军团BOSS战")
	LEGION_BOSS_VIEW(12),
	@ClientEnumComment(comment="军团矿战")
	LEGION_MINE_VIEW(13),
	@ClientEnumComment(comment="押运场景")
	ESCORT_VIEW(14),
	@ClientEnumComment(comment="试炼场景")
	REFINE_VIEW(15);;
	private int type;

	ClientGameSceneType(int type) {
		this.type = type;
	}

	@Override
	public int getIndex() {
		return type;
	}

}
