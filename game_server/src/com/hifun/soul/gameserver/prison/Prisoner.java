package com.hifun.soul.gameserver.prison;

import java.util.LinkedList;

import org.apache.commons.lang.StringUtils;

import com.hifun.soul.common.constants.GameConstants;
import com.hifun.soul.common.constants.LangConstants;
import com.hifun.soul.core.time.TimeService;
import com.hifun.soul.core.util.TimeUtils;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.prison.manager.GlobalPrisonManager;
import com.hifun.soul.gameserver.prison.msg.GCPrisonIdentityChanged;

/**
 * 战俘营角色类
 * 
 * @author yandajun
 * 
 */
public class Prisoner {
	/** ----------共用--------------- */
	private long humanId;
	private String humanName;
	private int humanLevel;
	private int identityType;
	private boolean isFighting;
	private int offLineExperience;
	/** ----------共用--------------- */

	/** ----------非奴隶------------- */
	private int rescuedNum;
	private int buyArrestNum;
	private int arrestedNum;
	private String enemyIds;
	private String looserIds;
	/** 手下败将 */
	private LinkedList<Prisoner> looserList = new LinkedList<Prisoner>();
	/** 夺俘之敌 */
	private LinkedList<Prisoner> enemyList = new LinkedList<Prisoner>();
	/** ----------非奴隶------------- */

	/** ----------主人------------- */
	private int extractedExperience;
	/** ----------主人------------- */

	/** ----------奴隶------------- */
	private long masterId;
	private int revoltedNum;
	private int forHelpedNum;
	private long beSlaveTime;
	private long lastBeExtractedTime;
	private long lastBeInteractedTime;
	private long lastInteractTime;
	private int beSlaveSelfLevel;
	private int beSlaveMasterLevel;

	/** ----------奴隶------------- */

	/** 已用互动次数 */
	private int interactedNum;

	private TimeService timeService;
	private GameConstants gameConstants;
	private GlobalPrisonManager globalPrisonManager;

	public Prisoner() {
		timeService = GameServerAssist.getSystemTimeService();
		gameConstants = GameServerAssist.getGameConstants();
		globalPrisonManager = GameServerAssist.getGlobalPrisonManager();
	}

	public Prisoner(Human human) {
		this();
		this.humanId = human.getHumanGuid();
		this.humanName = human.getName();
		this.humanLevel = human.getLevel();
		// 初始化为自由人
		identityType = IdentityType.FREEMAN.getIndex();
	}

	/**
	 * 初始化奴隶信息
	 */
	public void initSlave(Prisoner master, int slaveLevel) {
		// 如果之前是自由人，向客户端发送身份变更通知
		if (identityType == IdentityType.FREEMAN.getIndex()) {
			Human slaveHuman = GameServerAssist.getGameWorld()
					.getSceneHumanManager().getHumanByGuid(humanId);
			if (slaveHuman != null) {
				slaveHuman
						.sendImportantMessage(LangConstants.PRISON_FREEMAN_TO_SLAVE);
				GCPrisonIdentityChanged msg = new GCPrisonIdentityChanged();
				msg.setIdentityType(IdentityType.SLAVE.getIndex());
				slaveHuman.sendMessage(msg);
			}
		}
		identityType = IdentityType.SLAVE.getIndex();
		this.masterId = master.getHumanId();
		beSlaveTime = timeService.now();
		lastBeExtractedTime = beSlaveTime;
		beSlaveSelfLevel = slaveLevel;
		beSlaveMasterLevel = master.getHumanLevel();
	}

	/**
	 * 零点重置
	 * 
	 */
	public void reset() {
		// 抓捕次数
		arrestedNum = 0;
		// 购买抓捕次数
		buyArrestNum = 0;
		// 解救次数
		rescuedNum = 0;
		// 求救次数
		forHelpedNum = 0;
		// 互动次数
		interactedNum = 0;
		// 反抗次数
		revoltedNum = 0;
		// 已提取经验
		extractedExperience = 0;
	}

	/**
	 * 生成手下败将列表
	 * 
	 */
	public void generateLooserList() {
		looserList.clear();
		if (StringUtils.isNotEmpty(looserIds)) {
			String[] ids = looserIds.split(",");
			for (String id : ids) {
				Prisoner looser = globalPrisonManager.getPrisoner(Long
						.parseLong(id));
				if (looser != null) {
					looserList.add(looser);
				}
			}
		}
	}

	/**
	 * 生成夺俘之敌列表
	 */
	public void generateEnemyList() {
		enemyList.clear();
		if (StringUtils.isNotEmpty(enemyIds)) {
			String[] ids = enemyIds.split(",");
			for (String id : ids) {
				Prisoner enemy = globalPrisonManager.getPrisoner(Long
						.parseLong(id));
				enemyList.add(enemy);
			}
		}
	}

	/**
	 * 增加手下败将
	 */
	public void addLooser(Long humanId) {
		// 如果已在手下败将或夺俘之敌列表中，先移除掉
		Prisoner looser = globalPrisonManager.getPrisoner(humanId);
		if (looser == null) {
			return;
		}
		removeLooserOrEnemy(looser);
		if (looserList.size() >= gameConstants.getLooserNumLimit()) {
			looserList.removeLast();
		}
		looserList.addFirst(looser);
		resetLooserIds();
	}

	/**
	 * 获取某个夺俘之敌
	 */
	private Prisoner getEnemy(long humanId) {
		for (Prisoner enemy : enemyList) {
			if (humanId == enemy.getHumanId()) {
				return enemy;
			}
		}
		return null;
	}

	/**
	 * 增加夺俘之敌
	 */
	public void addEnemy(Long humanId) {
		// 如果已在手下败将或夺俘之敌列表中，先移除掉
		Prisoner enemy = globalPrisonManager.getPrisoner(humanId);
		removeLooserOrEnemy(enemy);
		if (enemyList.size() >= gameConstants.getEnemyNumLimit()) {
			enemyList.removeFirst();
		}
		enemyList.addLast(enemy);
		resetEnemyIds();
	}

	/**
	 * 获取某个手下败将
	 */
	private Prisoner getLooser(long humanId) {
		for (Prisoner looser : looserList) {
			if (humanId == looser.getHumanId()) {
				return looser;
			}
		}
		return null;
	}

	/**
	 * 重置手下败将IDs
	 */
	private void resetLooserIds() {
		looserIds = "";
		for (Prisoner looser : looserList) {
			looserIds += looser.getHumanId() + ",";
		}
	}

	/**
	 * 重置夺俘之敌IDs
	 */
	private void resetEnemyIds() {
		enemyIds = "";
		for (Prisoner enemy : enemyList) {
			enemyIds += enemy.getHumanId() + ",";
		}
	}

	/**
	 * 移除手下败将或夺俘之敌
	 * 
	 */
	public void removeLooserOrEnemy(Prisoner prisoner) {
		if (getLooser(prisoner.getHumanId()) != null) {
			removeLooser(prisoner);
		} else if (getEnemy(prisoner.getHumanId()) != null) {
			removeEnemy(prisoner);
		}
	}

	/**
	 * 移除手下败将
	 */
	private void removeLooser(Prisoner looser) {
		looserList.remove(looser);
		resetLooserIds();
	}

	/**
	 * 移除夺俘之敌
	 */
	private void removeEnemy(Prisoner looser) {
		enemyList.remove(looser);
		resetEnemyIds();
	}

	public long getHumanId() {
		return humanId;
	}

	public void setHumanId(long humanId) {
		this.humanId = humanId;
	}

	public String getHumanName() {
		return humanName;
	}

	public void setHumanName(String humanName) {
		this.humanName = humanName;
	}

	public int getHumanLevel() {
		return humanLevel;
	}

	public void setHumanLevel(int humanLevel) {
		this.humanLevel = humanLevel;
	}

	public int getIdentityType() {
		return identityType;
	}

	public void setIdentityType(int identityType) {
		this.identityType = identityType;
	}

	public int getBuyArrestNum() {
		return buyArrestNum;
	}

	public void setBuyArrestNum(int buyArrestNum) {
		this.buyArrestNum = buyArrestNum;
	}

	public int getArrestedNum() {
		return arrestedNum;
	}

	public void setArrestedNum(int arrestedNum) {
		this.arrestedNum = arrestedNum;
	}

	public int getRescuedNum() {
		return rescuedNum;
	}

	public void setRescuedNum(int rescuedNum) {
		this.rescuedNum = rescuedNum;
	}

	public int getTotalArrestNum() {
		return gameConstants.getFreeArrestNum() + buyArrestNum;
	}

	public LinkedList<Prisoner> getLooserList() {
		return looserList;
	}

	public void setLooserList(LinkedList<Prisoner> looserList) {
		this.looserList = looserList;
	}

	public LinkedList<Prisoner> getEnemyList() {
		return enemyList;
	}

	public void setEnemyList(LinkedList<Prisoner> enemyList) {
		this.enemyList = enemyList;
	}

	public int getTotalInteractNum() {
		return gameConstants.getInteractNumLimit();
	}

	public int getInteractedNum() {
		return interactedNum;
	}

	public void setInteractedNum(int interactedNum) {
		this.interactedNum = interactedNum;
	}

	public int getTotalRevoltNum() {
		return gameConstants.getRevoltNumLimit();
	}

	public int getRevoltedNum() {
		return revoltedNum;
	}

	public void setRevoltedNum(int revoltedNum) {
		this.revoltedNum = revoltedNum;
	}

	/**
	 * 获取当前经验
	 */
	public int getCurrentExperience() {
		// 劳作时间 = 现在时间 - 上次被提取时间
		long time = timeService.now() - lastBeExtractedTime;
		// 换算成分钟
		int mins = (int) (time / TimeUtils.MIN);
		int currentExperience = mins
				* GameServerAssist.getPrisonTemplateManager()
						.getPrisonExperienceTemplate(beSlaveSelfLevel)
						.getExpPerMinute();
		return currentExperience;
	}

	/**
	 * 获取1小时经验
	 */
	public int getOneHourExperience() {
		// 劳作时间 = 1小时后时间 - 上次被提取时间
		long time = timeService.now() + TimeUtils.HOUR - lastBeExtractedTime;
		// 换算成分钟
		int mins = (int) (time / TimeUtils.MIN);
		int oneHourExperience = mins
				* GameServerAssist.getPrisonTemplateManager()
						.getPrisonExperienceTemplate(beSlaveSelfLevel)
						.getExpPerMinute();
		return oneHourExperience;
	}

	/**
	 * 获取全部经验
	 */
	public int getTotalExperience() {
		// 劳作时间 = 奴隶到期时间 - 上次被提取时间
		long time = getSlaveDueTime() - lastBeExtractedTime;
		// 换算成分钟
		int mins = (int) (time / TimeUtils.MIN);
		int totalExperience = mins
				* GameServerAssist.getPrisonTemplateManager()
						.getPrisonExperienceTemplate(beSlaveSelfLevel)
						.getExpPerMinute();
		return totalExperience;
	}

	public int getExtractedExperience() {
		return extractedExperience;
	}

	public void setExtractedExperience(int extractedExperience) {
		this.extractedExperience = extractedExperience;
	}

	public GameConstants getGameConstants() {
		return gameConstants;
	}

	public void setGameConstants(GameConstants gameConstants) {
		this.gameConstants = gameConstants;
	}

	public GlobalPrisonManager getGlobalPrisonManager() {
		return globalPrisonManager;
	}

	public void setGlobalPrisonManager(GlobalPrisonManager globalPrisonManager) {
		this.globalPrisonManager = globalPrisonManager;
	}

	public long getMasterId() {
		return masterId;
	}

	public void setMasterId(long masterId) {
		this.masterId = masterId;
	}

	public int getTotalRescueNum() {
		return gameConstants.getRescueNumLimit();
	}

	public int getTotalForHelpNum() {
		return gameConstants.getForHelpNumLimit();
	}

	public int getForHelpedNum() {
		return forHelpedNum;
	}

	public void setForHelpedNum(int forHelpedNum) {
		this.forHelpedNum = forHelpedNum;
	}

	/**
	 * 获取奴隶到期时间
	 */
	public long getSlaveDueTime() {
		return beSlaveTime + gameConstants.getHoldSlaveTimeLimit()
				* TimeUtils.HOUR;
	}

	public long getBeSlaveTime() {
		if (beSlaveTime == 0L) {
			beSlaveTime = timeService.now()
					- gameConstants.getHoldSlaveTimeLimit() * TimeUtils.HOUR;
		}
		return beSlaveTime;
	}

	public void setBeSlaveTime(long beSlaveTime) {
		this.beSlaveTime = beSlaveTime;
	}

	public long getLastBeExtractedTime() {
		return lastBeExtractedTime;
	}

	public void setLastBeExtractedTime(long lastBeExtractedTime) {
		this.lastBeExtractedTime = lastBeExtractedTime;
	}

	public String getEnemyIds() {
		return enemyIds;
	}

	public void setEnemyIds(String enemyIds) {
		this.enemyIds = enemyIds;
	}

	public String getLooserIds() {
		return looserIds;
	}

	public void setLooserIds(String looserIds) {
		this.looserIds = looserIds;
	}

	/**
	 * 判断奴隶与主人之间是否在互动中(双向)
	 */
	public boolean isInteracting() {
		if (identityType == IdentityType.SLAVE.getIndex()) {
			long now = timeService.now();
			if (lastInteractTime + gameConstants.getInteractTimeLimit()
					* TimeUtils.MIN - now > 0
					|| lastBeInteractedTime
							+ gameConstants.getInteractTimeLimit()
							* TimeUtils.MIN - now > 0) {
				return true;
			}
		}
		return false;
	}

	public long getLastBeInteractedTime() {
		if (lastBeInteractedTime == 0) {
			lastBeInteractedTime = timeService.now()
					- gameConstants.getInteractTimeLimit() * TimeUtils.MIN;
		}
		return lastBeInteractedTime;
	}

	public void setLastBeInteractedTime(long lastBeInteractedTime) {
		this.lastBeInteractedTime = lastBeInteractedTime;
	}

	public long getLastInteractTime() {
		if (lastInteractTime == 0) {
			lastInteractTime = timeService.now()
					- gameConstants.getInteractTimeLimit() * TimeUtils.MIN;
		}
		return lastInteractTime;
	}

	public void setLastInteractTime(long lastInteractTime) {
		this.lastInteractTime = lastInteractTime;
	}

	public boolean isFighting() {
		return isFighting;
	}

	public void setFighting(boolean isFighting) {
		this.isFighting = isFighting;
	}

	public int getBeSlaveSelfLevel() {
		return beSlaveSelfLevel;
	}

	public void setBeSlaveSelfLevel(int beSlaveSelfLevel) {
		this.beSlaveSelfLevel = beSlaveSelfLevel;
	}

	public int getBeSlaveMasterLevel() {
		return beSlaveMasterLevel;
	}

	public void setBeSlaveMasterLevel(int beSlaveMasterLevel) {
		this.beSlaveMasterLevel = beSlaveMasterLevel;
	}

	public int getOffLineExperience() {
		return offLineExperience;
	}

	public void setOffLineExperience(int offLineExperience) {
		this.offLineExperience = offLineExperience;
	}

}
