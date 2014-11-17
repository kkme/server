package com.hifun.soul.gameserver.costnotify;

import java.util.List;

import com.hifun.soul.core.annotation.AutoCreateClientEnumType;
import com.hifun.soul.core.annotation.ClientEnumComment;
import com.hifun.soul.core.enums.IndexedEnum;
import com.hifun.soul.core.util.EnumUtil;

@AutoCreateClientEnumType
public enum CostNotifyType implements IndexedEnum {
	@ClientEnumComment(comment = "税收消除冷却")
	LEVY_CD(1),
	@ClientEnumComment(comment = "竞技场消除冷却")
	ARENA_CD(2),
	@ClientEnumComment(comment = "竞技场购买次数")
	ARENA_BUY_TIME(3),
	@ClientEnumComment(comment = "体力购买")
	BUY_ENERGY(4),
	@ClientEnumComment(comment = "巫术套现")
	CRYSTAL_EXCHANGE(5),
	@ClientEnumComment(comment = "商城购买")
	MALL_BUY(6),
	@ClientEnumComment(comment = "训练场魔晶训练")
	TRAIN(7),
	@ClientEnumComment(comment = "战争学院魔晶冥想")
	MUSE(8),
	@ClientEnumComment(comment = "Boss战魔晶鼓舞")
	BOSS_ENCOURAGE(9),
	@ClientEnumComment(comment = "Boss战充能冷却")
	BOSS_CHARGE_CD(10),
	@ClientEnumComment(comment = "精英副本刷新次数")
	ELITE_REFRESH(11),
	@ClientEnumComment(comment = "奇遇答题祈福")
	ANSWER_QUESTION(12),
	@ClientEnumComment(comment = "奇遇答题消除冷却")
	QUESTION_CD(13),
	@ClientEnumComment(comment = "背包开格子")
	OPEN_BAGUNIT(14),
	@ClientEnumComment(comment = "冥想格子魔晶解锁")
	MUSE_UNIT(15),
	@ClientEnumComment(comment = "装备开孔")
	GEM_PUNCH(16),
	@ClientEnumComment(comment = "宝石镶嵌")
	GEM_EMBED(17),
	@ClientEnumComment(comment = "宝石卸下")
	GEM_EXTRACT(18),
	@ClientEnumComment(comment = "星运仓库开格子")
	HOROSCOPE_STORAGE(19),
	@ClientEnumComment(comment = "重置技能点")
	RESET_SKILL_POINTS(20),
	@ClientEnumComment(comment = "矿场消除cd")
	MINE_FIELD_CD(21),
	@ClientEnumComment(comment = "矿场购买开矿次数")
	MINE_FIELD_OPEN(22),
	@ClientEnumComment(comment = "刷新神秘商店")
	REFRESH_SPECIAL_SHOP(23),
	@ClientEnumComment(comment = "魔晶拣选宝石")
	COLLECT_MAGIC_STONE(24),
	@ClientEnumComment(comment = "神秘商店")
	SPECIAL_SHOP(25),
	@ClientEnumComment(comment = "矿场一键加速消除冷却")
	REMOVE_ALL_MINE_CD(26),
	@ClientEnumComment(comment = "Boss攻击冷却")
	BOSS_ATTACK(27),
	@ClientEnumComment(comment = "魔晶洗炼")
	CRYSTAL_FORGE(28),
	@ClientEnumComment(comment = "魔晶培养")
	CRYSTAL_FOSTER(29),
	@ClientEnumComment(comment = "保存培养属性")
	SAVE_FOSTER_VALUE(30),
	@ClientEnumComment(comment = "自动试炼战斗")
	REFINE_BATTLE(31),
	@ClientEnumComment(comment = "一键试炼扫荡")
	REFINE_ALL_BATTLE(32),
	@ClientEnumComment(comment = "试炼魔晶刷新")
	REFINE_CRYSTAL_REFRESH(33),
	@ClientEnumComment(comment = "魔晶抽奖")
	CRYSTAL_LOTTERY(34),
	@ClientEnumComment(comment = "战俘营购买抓捕次数")
	PRISON_BUY_ARREST_NUM(35),
	@ClientEnumComment(comment = "战俘营提取经验")
	PRISON_EXTRACT_EXPERIENCE(36),
	@ClientEnumComment(comment = "角斗场购买角斗次数")
	BUY_ABATTOIR_WRESTLE_NUM(37),
	@ClientEnumComment(comment = "必胜猜拳消耗")
	SUCCEED_FINGER_GUESS(38),
	@ClientEnumComment(comment = "嗜血神殿购买角斗次数")
	BUY_BLOOD_TEMPLE_WRESTLE_NUM(39),
	@ClientEnumComment(comment = "购买精灵背包格子")
	BUY_SPRITE_BAG_CELL(40),
	@ClientEnumComment(comment = "切换技能系")
	CHANGE_SKILL_DEVELOP_TYPE(41),
	@ClientEnumComment(comment = "战神之巅刷新房间")
	MARS_REFRESH_ROOM(42),
	@ClientEnumComment(comment = "战神之巅解锁房间")
	MARS_UNLOCK_ROOM(43),
	@ClientEnumComment(comment = "战神之巅购买加倍次数")
	MARS_BUY_MULTIPLE_NUM(44),
	@ClientEnumComment(comment = "战神之巅加倍击杀")
	MARS_MULTIPLE_KILL(45),
	@ClientEnumComment(comment = "军团Boss战魔晶鼓舞")
	LEGION_BOSS_ENCOURAGE(46),
	@ClientEnumComment(comment = "军团Boss战充能冷却")
	LEGION_BOSS_CHARGE_CD(47),
	@ClientEnumComment(comment = "军团Boss战攻击冷却")
	LEGION_BOSS_ATTACK(48),
	@ClientEnumComment(comment = "军团矿战魔晶鼓舞")
	LEGION_MINE_WAR_ENCOURAGE(49),
	@ClientEnumComment(comment = "军团矿战侦查")
	LEGION_MINE_WAR_WATCH(50),
	@ClientEnumComment(comment = "主城税收押注必胜")
	LEVY_BET_CERTAIN_WIN(51),
	@ClientEnumComment(comment = "押运购买拦截次数")
	ESCORT_BUY_ROB_TIME(52),
	@ClientEnumComment(comment = "押运CD加速")
	ESCORT_CD(53),
	@ClientEnumComment(comment = "押运拦截CD加速")
	ESCORT_ROB_CD(54),
	@ClientEnumComment(comment = "押运召唤怪物")
	ESCORT_CALL_MONSTER(55),
	@ClientEnumComment(comment = "押运刷新怪物")
	ESCORT_REFRESH_MONSTER(56),
	@ClientEnumComment(comment = "押运鼓舞")
	ESCORT_ENCOURAGE(57),
	@ClientEnumComment(comment = "押运军团祈福")
	ESCORT_LEGION_PRAY(58),
	@ClientEnumComment(comment = "军团冥想")
	LEGION_MEDITATION(59),
	@ClientEnumComment(comment = "刷新军团任务")
	REFRESH_LEGION_TASK(60),
	@ClientEnumComment(comment = "攻打主城怪物冷却")
	MAIN_CITY_BATTLE_CD(61),
	@ClientEnumComment(comment = "刷新日常任务列表")
	REFRESH_DAILY_QUEST_LIST(62),
	@ClientEnumComment(comment = "接受日常任务")
	ACCEPT_DIALY_QUEST(63),
	@ClientEnumComment(comment = "加速完成日常任务")
	COMPLETE_DIALY_QUEST(64),
	@ClientEnumComment(comment = "一键答题")
	ONEKEY_ANSWER_QUESTION(65),
	@ClientEnumComment(comment = "恢复体力")
	RECOVER_ENERGY(66)
	;

	private int index;
	
	private static final List<CostNotifyType> indexes = IndexedEnumUtil
			.toIndexes(CostNotifyType.values());
	
	CostNotifyType(int index) {
		this.index = index;
	}
	
	@Override
	public int getIndex() {
		return index;
	}
	
	/**
	 * 根据指定的索引获取枚举的定义
	 * 
	 * @param index
	 * @return
	 */
	public static CostNotifyType indexOf(final int index) {
		return EnumUtil.valueOf(indexes, index);
	}

}
