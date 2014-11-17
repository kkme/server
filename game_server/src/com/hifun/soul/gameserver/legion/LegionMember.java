package com.hifun.soul.gameserver.legion;

import com.hifun.soul.common.constants.LangConstants;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.legion.manager.GlobalLegionManager;

/**
 * 军团成员
 * 
 * @author yandajun
 * 
 */
public class LegionMember implements Comparable<LegionMember> {
	private long humanGuid;
	private long legionId;
	private String memberName;
	private int level;
	private int position;
	private String positionName;
	private int totalContribution;
	private int todayContribution;
	private long offLineTime;
	private int todayTaskScore;
	private int medal;
	private int yesterdayScoreRank;

	/**
	 * 1、在线优先排序 2、按照职位排序 3、同职位按照贡献值由高到低排序
	 */
	@Override
	public int compareTo(LegionMember o) {
		GlobalLegionManager manager = GameServerAssist.getGlobalLegionManager();
		if (manager.isOnline(this.humanGuid)
				&& !manager.isOnline(o.getHumanGuid())) {
			return -1;
		} else if (!manager.isOnline(this.humanGuid)
				&& manager.isOnline(o.getHumanGuid())) {
			return 1;
		} else {
			if (this.position > o.getPosition()) {
				return -1;
			} else if (this.position < o.getPosition()) {
				return 1;
			} else {
				if (this.totalContribution > o.getTotalContribution()) {
					return -1;
				} else if (this.totalContribution < o.getTotalContribution()) {
					return 1;
				} else {
					return 0;
				}

			}
		}
	}

	public long getHumanGuid() {
		return humanGuid;
	}

	public void setHumanGuid(long humanGuid) {
		this.humanGuid = humanGuid;
	}

	public long getLegionId() {
		return legionId;
	}

	public void setLegionId(long legionId) {
		this.legionId = legionId;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public String getPositionName() {
		return positionName;
	}

	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}

	public int getTotalContribution() {
		return totalContribution;
	}

	public void setTotalContribution(int totalContribution) {
		this.totalContribution = totalContribution;
	}

	public int getTodayContribution() {
		return todayContribution;
	}

	public void setTodayContribution(int todayContribution) {
		this.todayContribution = todayContribution;
	}

	public long getOffLineTime() {
		return offLineTime;
	}

	public void setOffLineTime(long offLineTime) {
		this.offLineTime = offLineTime;
	}

	public int getTodayTaskScore() {
		return todayTaskScore;
	}

	public void setTodayTaskScore(int todayTaskScore) {
		this.todayTaskScore = todayTaskScore;
	}

	public int getMedal() {
		return medal;
	}

	public void setMedal(int medal) {
		this.medal = medal;
	}

	public int getYesterdayScoreRank() {
		return yesterdayScoreRank;
	}

	public void setYesterdayScoreRank(int yesterdayScoreRank) {
		this.yesterdayScoreRank = yesterdayScoreRank;
	}

	/**
	 * 更新成员信息
	 */
	public void updateMember() {
		GameServerAssist.getGlobalLegionManager().updateLegionMember(this);
	}

	/**
	 * 增加个人贡献
	 */
	public void addSelfContribution(Human human, int addContribution,
			boolean notify) {
		if (addContribution <= 0) {
			return;
		}
		this.todayContribution += addContribution;
		this.totalContribution += addContribution;
		updateMember();
		// 悬浮提示
		if (notify && human != null) {
			String desc = GameServerAssist.getSysLangService().read(
					LangConstants.LEGION_SELF_CONTRIBUTION);
			String operate = addContribution >= 0 ? "+" : "-";
			human.sendImportantMessage(LangConstants.COMMON_OBTAIN,
					addContribution, desc, operate);
		}
	}

	/**
	 * 消耗个人勋章
	 */
	public boolean costMedal(Human human, int costNum, boolean notify) {
		if (this.medal < costNum) {
			human.sendErrorMessage(LangConstants.LEGION_MEDAL_NOT_ENOUGH);
			return false;
		}
		this.medal -= costNum;
		updateMember();
		return true;
	}

	/**
	 * 增加个人勋章
	 */
	public void addMedal(Human human, int addMedal, boolean notify) {
		if (addMedal <= 0) {
			return;
		}
		this.medal += addMedal;
		updateMember();
		// 悬浮提示
		if (notify && human != null) {
			String desc = GameServerAssist.getSysLangService().read(
					LangConstants.LEGION_MEDAL);
			String operate = addMedal >= 0 ? "+" : "-";
			human.sendImportantMessage(LangConstants.COMMON_OBTAIN, addMedal,
					desc, operate);
		}
	}

	/**
	 * 增加任务积分
	 */
	public void addTodayTaskScore(Human human, int addScore, boolean notify) {
		if (addScore <= 0) {
			return;
		}
		this.todayTaskScore += addScore;
		updateMember();
		// 悬浮提示
		if (notify && human != null) {
			String desc = GameServerAssist.getSysLangService().read(
					LangConstants.LEGION_TASK_SCORE);
			String operate = addScore >= 0 ? "+" : "-";
			human.sendImportantMessage(LangConstants.COMMON_OBTAIN, addScore,
					desc, operate);
		}
	}
}
