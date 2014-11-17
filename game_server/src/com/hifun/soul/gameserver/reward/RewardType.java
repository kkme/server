package com.hifun.soul.gameserver.reward;

/**
 * 奖励类型
 * @author magicstone
 *
 */
public interface RewardType {
	/**竞技场排名奖励 */
	public static final int ARENA_RANK_REWARD=1;
	/**boss战击杀奖励 */
	public static final int BOSS_KILL_REWARD=2;
	/**boss战伤害奖励 */
	public static final int BOSS_WAR_DAMAGE_REWARD=3;
	/**boss战排名奖励 */
	public static final int BOSS_WAR_RANK_REWARD=4;
	/**匹配战连胜排名奖励 */
	public static final int MATCH_BATTLE_STREAK_WIN_RANK_REWARD=5;
	/**战神之巅击杀奖励(次日领取) */
	public static final int MARS_KILL_REWARD=6;
	/**军团boss战伤害奖励 */
	public static final int LEGION_BOSS_DAMAGE_REWARD=7;
	/** 军团矿战排名奖励 */
	public static final int LEGION_MINE_RANK_REWARD=8;
	/** 军团矿战战斗奖励 */
	public static final int LEGION_MINE_BATTLE_REWARD=9;
}
