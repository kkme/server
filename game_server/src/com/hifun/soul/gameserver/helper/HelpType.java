package com.hifun.soul.gameserver.helper;

import java.util.List;

import com.hifun.soul.core.annotation.AutoCreateClientEnumType;
import com.hifun.soul.core.annotation.ClientEnumComment;
import com.hifun.soul.core.enums.IndexedEnum;
import com.hifun.soul.core.util.EnumUtil;

@AutoCreateClientEnumType
public enum HelpType implements IndexedEnum {
	
	/** 开始训练 */
	@ClientEnumComment(comment="开始训练")
	TRAIN(1),
	/** 开始冥想 */
	@ClientEnumComment(comment="开始冥想 ")
	MUSE(2),	
	/** 精炼宝石 */
	@ClientEnumComment(comment="精炼宝石")
	MINE(3),
	/** 免费抽奖 */
	@ClientEnumComment(comment="免费抽奖")
	LOTTERY(4),
	/** 免费刻印 */
	@ClientEnumComment(comment="免费刻印")
	HOROSCOPE(5),
	/** 免费拣选宝石 */
	@ClientEnumComment(comment="免费拣选宝石")
	CHOOSE(6),
	/** 奇遇答题 */
	@ClientEnumComment(comment="奇遇答题")
	QUESTION(7),
	/** 挑战关卡 */
	@ClientEnumComment(comment="挑战关卡")
	STAGE(8),
	/** 战争研习 */
	@ClientEnumComment(comment="战争研习")
	TECHNOLOGY(9),
	/** 巫术套现 */
	@ClientEnumComment(comment="巫术套现")
	CRYSTALEXCHANGE(10),
	/** 精英副本 */
	@ClientEnumComment(comment="精英副本")
	ELITE(11),
	/** 主城税收 */
	@ClientEnumComment(comment="主城税收 ")
	LEVY(12),
	/** 挑战竞技场 */
	@ClientEnumComment(comment="挑战竞技场")
	ARENA(13),
	/** 连续登陆奖励 */
	@ClientEnumComment(comment="连续登陆奖励")
	LOGINREWARD(14),
	/** boss战已开启 */
	@ClientEnumComment(comment="boss战已开启")
	BOSSWAR(15),
	/** boss战已开启 */
	@ClientEnumComment(comment="加属性点")
	ATTRIBUTES(16),
	/** boss战已开启 */
	@ClientEnumComment(comment="boss战已开启")
	SKILL(17),
	/** 挑战试炼副本 */
	@ClientEnumComment(comment="挑战试炼副本")
	REFINE(18),
	/** 勇者之路挑战 */
	@ClientEnumComment(comment="勇者之路挑战")
	WARRIOR(19),
	;
	
	private HelpType(int index) {
		this.index = index;
	}
	
	private static final List<HelpType> indexes = IndexedEnumUtil.toIndexes(HelpType.values());
	
	private int index;
	
	@Override
	public int getIndex() {
		return this.index;
	}
	
	public static HelpType indexOf(int index){
		return EnumUtil.valueOf(indexes,index);				
	}
}
