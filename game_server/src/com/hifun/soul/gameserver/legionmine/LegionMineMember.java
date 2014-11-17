package com.hifun.soul.gameserver.legionmine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hifun.soul.common.constants.LangConstants;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.legionmine.enums.JoinLegionType;
import com.hifun.soul.gameserver.legionmine.enums.SelfBufType;
import com.hifun.soul.gameserver.legionmine.msg.GCUseSelfBuf;
import com.hifun.soul.gameserver.legionmine.template.LegionMineSelfBufTemplate;

/**
 * 军团矿战玩家
 * 
 * @author yandajun
 * 
 */
public class LegionMineMember {
	private long humanId;
	private int mineIndex;
	private JoinLegionType legionType;
	private int occupyValue;
	private long occupyTime;
	/** 鼓舞加成 */
	private int encourageRate;
	/** 是否退出 */
	private boolean isQuit;
	/** 是否参与此次矿战(矿战开始置true，下次开始置false) */
	private boolean isJoin;
	/** 矿战军团内排名 */
	private int rank;
	/** 矿战所处军团是否取胜 */
	private boolean isLegionWin;
	/** 是否正在侦查 */
	private boolean isWatching;
	/** 个人buf */
	private Map<SelfBufType, SelfBuf> selfBufMap = new HashMap<SelfBufType, SelfBuf>();

	/**
	 * 增加个人buf
	 */
	public void addSelfBuf(int selfBufType) {
		SelfBufType bufType = SelfBufType.indexOf(selfBufType);
		if (bufType == null) {
			return;
		}
		// 如果有相同类型的buf，不添加
		if (selfBufMap.containsKey(bufType)) {
			return;
		}
		LegionMineSelfBufTemplate selfBufTemplate = GameServerAssist
				.getLegionMineWarTemplateManager().getSelfBufTemplate(
						bufType.getIndex());
		if (selfBufTemplate == null) {
			return;
		}
		SelfBuf selfBuf = new SelfBuf();
		selfBuf.setBufName(selfBufTemplate.getBufName());
		selfBuf.setSelfBufType(bufType.getIndex());
		selfBuf.setBufDesc(selfBufTemplate.getFunctionDesc());
		selfBuf.setBufIcon(selfBufTemplate.getBufIcon());
		selfBuf.setUsing(false);
		selfBufMap.put(bufType, selfBuf);
		// 发送个人buf列表
		Human beBattledHuman = GameServerAssist.getGameWorld()
				.getSceneHumanManager().getHumanByGuid(humanId);
		if (beBattledHuman != null) {
			GCUseSelfBuf useBufMsg = new GCUseSelfBuf();
			useBufMsg.setSelfBufs(getSelfBufList().toArray(new SelfBuf[0]));
			beBattledHuman.sendMessage(useBufMsg);
			// 被打败提示
			beBattledHuman.sendImportantMessage(
					LangConstants.LEGION_MINE_BE_BATTLED_FAILED,
					selfBuf.getBufName());
		}
	}

	/**
	 * 获取个人buf
	 */
	public SelfBuf getSelfBuf(SelfBufType bufType) {
		return selfBufMap.get(bufType);
	}

	/**
	 * 移除个人buf
	 */
	public void removeSelfBuf(SelfBufType selfBufType) {
		selfBufMap.remove(selfBufType);
	}

	/**
	 * 移除所有buf
	 */
	public void removeAllSelfBufs() {
		selfBufMap.clear();
	}

	/**
	 * 获取个人buf列表
	 */
	public List<SelfBuf> getSelfBufList() {
		return new ArrayList<SelfBuf>(selfBufMap.values());
	}

	/**
	 * 是否正在使用袭指令
	 */
	public boolean isUsingRaidBuf() {
		if (getSelfBuf(SelfBufType.RAID) != null
				&& getSelfBuf(SelfBufType.RAID).getUsing()) {
			return true;
		}
		return false;
	}

	/**
	 * 是否正在使用驰指令
	 */
	public boolean isUsingRunBuf() {
		if (getSelfBuf(SelfBufType.RUN) != null
				&& getSelfBuf(SelfBufType.RUN).getUsing()) {
			return true;
		}
		return false;
	}

	/**
	 * 重置数据
	 */
	public void resetDate() {
		mineIndex = 0;
		occupyValue = 0;
		encourageRate = 0;
		isQuit = false;
		isJoin = false;
		rank = -1;
	}

	public long getHumanId() {
		return humanId;
	}

	public void setHumanId(long humanId) {
		this.humanId = humanId;
	}

	public int getMineIndex() {
		return mineIndex;
	}

	public void setLegionType(JoinLegionType legionType) {
		this.legionType = legionType;
	}

	public JoinLegionType getLegionType() {
		return legionType;
	}

	public void setMineIndex(int mineIndex) {
		this.mineIndex = mineIndex;
	}

	public int getOccupyValue() {
		return occupyValue;
	}

	public void setOccupyValue(int occupyValue) {
		this.occupyValue = occupyValue;
	}

	public long getOccupyTime() {
		return occupyTime;
	}

	public void setOccupyTime(long occupyTime) {
		this.occupyTime = occupyTime;
	}

	public int getEncourageRate() {
		return encourageRate;
	}

	public void setEncourageRate(int encourageRate) {
		this.encourageRate = encourageRate;
	}

	/**
	 * 总攻击力加成
	 */
	public int getAttackRate() {
		return encourageRate + getLegionBufRate() + getMineSurroundRate();
	}

	/**
	 * 总防御力加成
	 */
	public int getDefenseRate() {
		return getLegionBufRate() + getMineSurroundRate();
	}

	/**
	 * 军团buf攻防加成
	 */
	public int getLegionBufRate() {
		return GameServerAssist.getGlobalLegionMineWarManager()
				.getLegionBufRate(this);
	}

	/**
	 * 矿位包围攻防加成
	 */
	public int getMineSurroundRate() {
		int surroundRate = 0;
		LegionMine legionMine = GameServerAssist
				.getGlobalLegionMineWarManager().getLegionMine(humanId);
		if (legionMine != null) {
			surroundRate = legionMine.getSurroundAmend();
		}
		return surroundRate;
	}

	public boolean isQuit() {
		return isQuit;
	}

	public void setQuit(boolean isQuit) {
		this.isQuit = isQuit;
	}

	public boolean isJoin() {
		return isJoin;
	}

	public void setJoin(boolean isJoin) {
		this.isJoin = isJoin;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public boolean isLegionWin() {
		return isLegionWin;
	}

	public void setLegionWin(boolean isLegionWin) {
		this.isLegionWin = isLegionWin;
	}

	public boolean isWatching() {
		return isWatching;
	}

	public void setWatching(boolean isWatching) {
		this.isWatching = isWatching;
	}
}
