package com.hifun.soul.gameserver.legion;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import com.hifun.soul.common.constants.LangConstants;
import com.hifun.soul.common.constants.SharedConstants;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.human.Human;

/**
 * 军团
 * 
 * @author yandajun
 * 
 */
public class Legion {
	private long id;
	private String legionName;
	private long commanderId;
	private String commanderName;
	private int legionLevel;
	private int memberLimit;
	private int memberNum;
	private int experience;
	private String notice;
	private int legionCoin;
	private int levelMaxExperience;
	private int donateGetLegionExp;
	private int donateGetSelfContri;
	private int donateGetMedal;
	/** memberGuid - legionMember */
	private Map<Long, LegionMember> memberListMap;
	/** applyerGuid - legionApply */
	private Map<Long, LegionApply> applyListMap;
	private LinkedList<LegionLog> legionLogList;
	private LinkedList<LegionMeditationLog> meditationLogList;
	/** buildingType - legionBuilding */
	private Map<Integer, LegionBuilding> buildingListMap;
	/** itemId - legionBuilding */
	private Map<Integer, LegionShop> shopListMap;
	/** technologyType - legionBuilding */
	private Map<Integer, LegionTechnology> technologyListMap;
	/** titleId - legionBuilding */
	private Map<Integer, LegionHonor> honorListMap;

	public Legion() {
		memberListMap = new HashMap<Long, LegionMember>();
		applyListMap = new HashMap<Long, LegionApply>();
		legionLogList = new LinkedList<LegionLog>();
		meditationLogList = new LinkedList<LegionMeditationLog>();
		buildingListMap = new HashMap<Integer, LegionBuilding>();
		shopListMap = new HashMap<Integer, LegionShop>();
		technologyListMap = new HashMap<Integer, LegionTechnology>();
		honorListMap = new HashMap<Integer, LegionHonor>();
		legionLevel = 1;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getLegionName() {
		return legionName;
	}

	public void setLegionName(String legionName) {
		this.legionName = legionName;
	}

	public long getCommanderId() {
		return commanderId;
	}

	public void setCommanderId(long commanderId) {
		this.commanderId = commanderId;
	}

	public String getCommanderName() {
		return commanderName;
	}

	public void setCommanderName(String commanderName) {
		this.commanderName = commanderName;
	}

	public int getLegionLevel() {
		return legionLevel;
	}

	public void setLegionLevel(int legionLevel) {
		this.legionLevel = legionLevel;
	}

	public int getMemberLimit() {
		return memberLimit;
	}

	public void setMemberLimit(int memberLimit) {
		this.memberLimit = memberLimit;
	}

	public int getMemberNum() {
		return memberNum;
	}

	public void setMemberNum(int memberNum) {
		this.memberNum = memberNum;
	}

	public int getExperience() {
		return experience;
	}

	public void setExperience(int experience) {
		this.experience = experience;
	}

	public String getNotice() {
		return notice;
	}

	public void setNotice(String notice) {
		this.notice = notice;
	}

	public int getLegionCoin() {
		return legionCoin;
	}

	public void setLegionCoin(int legionCoin) {
		this.legionCoin = legionCoin;
	}

	public Map<Long, LegionMember> getMemberListMap() {
		return memberListMap;
	}

	public void setMemberListMap(Map<Long, LegionMember> memberListMap) {
		this.memberListMap = memberListMap;
	}

	public Map<Long, LegionApply> getApplyListMap() {
		return applyListMap;
	}

	public void setApplyListMap(Map<Long, LegionApply> applyListMap) {
		this.applyListMap = applyListMap;
	}

	public LinkedList<LegionLog> getLegionLogList() {
		return legionLogList;
	}

	public void setLegionLogList(LinkedList<LegionLog> legionLogList) {
		this.legionLogList = legionLogList;
	}

	public Map<Integer, LegionBuilding> getBuildingListMap() {
		return buildingListMap;
	}

	public void setBuildingListMap(Map<Integer, LegionBuilding> buildingListMap) {
		this.buildingListMap = buildingListMap;
	}

	public LinkedList<LegionMeditationLog> getMeditationLogList() {
		return meditationLogList;
	}

	public void setMeditationLogList(
			LinkedList<LegionMeditationLog> meditationLogList) {
		this.meditationLogList = meditationLogList;
	}

	public Map<Integer, LegionShop> getShopListMap() {
		return shopListMap;
	}

	public void setShopListMap(Map<Integer, LegionShop> shopListMap) {
		this.shopListMap = shopListMap;
	}

	public Map<Integer, LegionTechnology> getTechnologyListMap() {
		return technologyListMap;
	}

	public void setTechnologyListMap(
			Map<Integer, LegionTechnology> technologyListMap) {
		this.technologyListMap = technologyListMap;
	}

	public Map<Integer, LegionHonor> getHonorListMap() {
		return honorListMap;
	}

	public void setHonorListMap(Map<Integer, LegionHonor> honorListMap) {
		this.honorListMap = honorListMap;
	}

	public int getLevelMaxExperience() {
		if (legionLevel >= SharedConstants.LEGION_MAX_LEVEL) {
			return 0;
		}
		levelMaxExperience = GameServerAssist.getLegionTemplateManager()
				.getLegionLevelTemplate(legionLevel).getNeedExperience();
		return levelMaxExperience;
	}

	public void setLevelMaxExperience(int levelMaxExperience) {
		this.levelMaxExperience = levelMaxExperience;
	}

	/**
	 * 消耗军团资金
	 */
	public boolean costCoin(Human human, int costNum, boolean showLog) {
		if (this.legionCoin < costNum) {
			human.sendErrorMessage(LangConstants.LEGION_COIN_NOT_ENOUGH);
			return false;
		}
		this.legionCoin -= costNum;
		GameServerAssist.getGlobalLegionManager().updateLegion(this);
		return true;
	}

	/**
	 * 增加军团资金
	 */
	public void addCoin(Human human, int addCoin, boolean notify) {
		if (addCoin <= 0) {
			return;
		}
		this.legionCoin += addCoin;
		GameServerAssist.getGlobalLegionManager().updateLegion(this);
		// 悬浮提示
		if (notify && human != null) {
			String desc = GameServerAssist.getSysLangService().read(
					LangConstants.LEGION_COIN);
			String operate = addCoin >= 0 ? "+" : "-";
			human.sendImportantMessage(LangConstants.COMMON_OBTAIN, addCoin,
					desc, operate);
		}
	}

	public int getDonateGetLegionExp() {
		return donateGetLegionExp;
	}

	public void setDonateGetLegionExp(int donateGetLegionExp) {
		this.donateGetLegionExp = donateGetLegionExp;
	}

	public int getDonateGetSelfContri() {
		return donateGetSelfContri;
	}

	public void setDonateGetSelfContri(int donateGetSelfContri) {
		this.donateGetSelfContri = donateGetSelfContri;
	}

	public int getDonateGetMedal() {
		return donateGetMedal;
	}

	public void setDonateGetMedal(int donateGetMedal) {
		this.donateGetMedal = donateGetMedal;
	}

}
