package com.hifun.soul.gameserver.prison.manager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.math.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.hifun.soul.common.IInitializeRequired;
import com.hifun.soul.common.LogReasons.ExperienceLogReason;
import com.hifun.soul.common.LogReasons.MoneyLogReason;
import com.hifun.soul.common.constants.GameConstants;
import com.hifun.soul.common.constants.LangConstants;
import com.hifun.soul.core.orm.IDBService;
import com.hifun.soul.core.orm.IEntity;
import com.hifun.soul.core.time.TimeService;
import com.hifun.soul.core.util.TimeUtils;
import com.hifun.soul.gamedb.agent.query.DataQueryConstants;
import com.hifun.soul.gamedb.cache.CacheEntry;
import com.hifun.soul.gamedb.cache.CacheManager;
import com.hifun.soul.gamedb.cache.ICachableComponent;
import com.hifun.soul.gamedb.callback.IDBCallback;
import com.hifun.soul.gamedb.entity.PrisonLogEntity;
import com.hifun.soul.gamedb.entity.PrisonerEntity;
import com.hifun.soul.gamedb.service.IDataService;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.currency.CurrencyType;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.legion.Legion;
import com.hifun.soul.gameserver.legion.enums.LegionTechnologyType;
import com.hifun.soul.gameserver.mail.service.MailService;
import com.hifun.soul.gameserver.prison.IdentityType;
import com.hifun.soul.gameserver.prison.MasterExtractType;
import com.hifun.soul.gameserver.prison.PrisonLog;
import com.hifun.soul.gameserver.prison.PrisonLogType;
import com.hifun.soul.gameserver.prison.Prisoner;
import com.hifun.soul.gameserver.prison.converter.PrisonLogToEntityConverter;
import com.hifun.soul.gameserver.prison.converter.PrisonerToEntityConverter;
import com.hifun.soul.gameserver.prison.converter.PrisonerToInfoConverter;
import com.hifun.soul.gameserver.prison.msg.GCBuyArrestNum;
import com.hifun.soul.gameserver.prison.msg.GCMasterExtractExperience;
import com.hifun.soul.gameserver.prison.msg.GCMasterInteract;
import com.hifun.soul.gameserver.prison.msg.GCMasterRelease;
import com.hifun.soul.gameserver.prison.msg.GCOpenPrisonPanel;
import com.hifun.soul.gameserver.prison.msg.GCPrisonIdentityChanged;
import com.hifun.soul.gameserver.prison.msg.GCSlaveForHelp;
import com.hifun.soul.gameserver.prison.msg.GCSlaveForHelped;
import com.hifun.soul.gameserver.prison.msg.GCSlaveInteract;
import com.hifun.soul.gameserver.prison.template.PrisonExperienceTemplate;

@Scope("singleton")
@Component
public class GlobalPrisonManager implements IInitializeRequired,
		ICachableComponent {
	private Map<Long, Prisoner> prisonersMap = new HashMap<Long, Prisoner>();
	private Map<Long, PrisonLog> prisonLogsMap = new HashMap<Long, PrisonLog>();
	private PrisonTemplateManager prisonTemplateManager;
	@Autowired
	private IDataService dataService;
	@Autowired
	private TimeService timeService;
	private GameConstants gameConstants;
	@Autowired
	private CacheManager cacheManager;
	private CacheEntry<Long, IEntity> cache = new CacheEntry<Long, IEntity>();
	private PrisonerToEntityConverter prisonerConverter;
	private PrisonLogToEntityConverter prisonLogconverter;

	@Override
	public void init() {
		prisonTemplateManager = GameServerAssist.getPrisonTemplateManager();
		gameConstants = GameServerAssist.getGameConstants();
		cacheManager.registerOtherCachableComponent(this);
		prisonerConverter = new PrisonerToEntityConverter();
		prisonLogconverter = new PrisonLogToEntityConverter();

	}

	public void start(IDBService dbService) {
		loadPrisonerInfo(dbService);
	}

	private void loadPrisonerInfo(IDBService dbService) {
		// 从数据库中查出战俘营角色信息
		List<?> prisonerListResult = dbService
				.findByNamedQuery(DataQueryConstants.QUERY_ALL_PRISONER);
		if (!prisonerListResult.isEmpty()) {
			for (Object o : prisonerListResult) {
				PrisonerEntity entity = (PrisonerEntity) o;
				Prisoner prisoner = prisonerConverter.reverseConvert(entity);
				prisonersMap.put(prisoner.getHumanId(), prisoner);
			}
		}
		// 生成手下败将和夺俘之敌列表
		for (long humanId : prisonersMap.keySet()) {
			Prisoner prisoner = prisonersMap.get(humanId);
			prisoner.generateLooserList();
			prisoner.generateEnemyList();
		}
		// 加载战俘营日志
		List<?> prisonLogListResult = dbService
				.findByNamedQuery(DataQueryConstants.QUERY_ALL_PRISON_LOG);
		if (!prisonerListResult.isEmpty()) {
			for (Object o : prisonLogListResult) {
				PrisonLogEntity entity = (PrisonLogEntity) o;
				PrisonLog prisonLog = prisonLogconverter.reverseConvert(entity);
				prisonLog
						.setFirstName(getPrisoner(entity.getFirstId()) == null ? ""
								: getPrisoner(entity.getFirstId())
										.getHumanName());
				prisonLog
						.setSecondName(getPrisoner(entity.getSecondId()) == null ? ""
								: getPrisoner(entity.getSecondId())
										.getHumanName());
				prisonLog
						.setThirdName(getPrisoner(entity.getThirdId()) == null ? ""
								: getPrisoner(entity.getThirdId())
										.getHumanName());
				prisonLogsMap.put(prisonLog.getId(), prisonLog);
			}
		}
	}

	@Override
	public List<IEntity> getUpdateEntities() {
		return cache.getAllUpdateData();
	}

	@Override
	public List<IEntity> getDeleteEntities() {
		return cache.getAllDeleteData();
	}

	/**
	 * 获取战俘营角色对象
	 */
	public Prisoner getPrisoner(long humanId) {
		return prisonersMap.get(humanId);
	}

	/**
	 * 打开面板
	 */
	public void openPrisonPanel(Human human) {
		Prisoner prisoner = getPrisoner(human.getHumanGuid());
		GCOpenPrisonPanel msg = new GCOpenPrisonPanel();
		msg.setIdentityType(prisoner.getIdentityType());
		human.sendMessage(msg);
	}

	/**
	 * 创建战俘营角色(玩家出生，即第一次登陆时)
	 */
	public void createPrisoner(Human human) {
		Prisoner prisoner = new Prisoner(human);
		dataService.insert(prisonerConverter.convert(prisoner));
		prisonersMap.put(human.getHumanGuid(), prisoner);
	}

	/**
	 * 更新战俘营角色信息(玩家升级)
	 */
	public void updatePrisoner(Prisoner prisoner) {
		cache.addUpdate(prisoner.getHumanId(),
				prisonerConverter.convert(prisoner));
	}

	/**
	 * 抓捕(自由人)战斗回调
	 */
	public void arrestCallBack(Human human, long beArrestedId, boolean isWin) {
		Prisoner prisoner = getPrisoner(human.getHumanGuid());
		prisoner.setFighting(false);
		prisoner.setArrestedNum(prisoner.getArrestedNum() + 1);
		Prisoner beArrested = getPrisoner(beArrestedId);
		beArrested.setFighting(false);
		if (!isWin) {// 如果失败
			addPrisonLog(PrisonLogType.ARREST, human.getHumanGuid(),
					beArrestedId, 0, false);
			human.sendImportantMessage(LangConstants.PRISON_ARREST_FAILED);
		} else { // 如果成功
			// 移除手下败将或夺俘之敌
			prisoner.removeLooserOrEnemy(beArrested);
			if (prisoner.getIdentityType() == IdentityType.FREEMAN.getIndex()) {
				prisoner.setIdentityType(IdentityType.MASTER.getIndex());
			}
			beArrested.initSlave(prisoner, beArrested.getHumanLevel());
			addPrisonLog(PrisonLogType.ARREST, human.getHumanGuid(),
					beArrestedId, 0, true);
			human.sendSuccessMessage(LangConstants.PRISON_ARREST_SUCCESS);
		}
		cache.addUpdate(prisoner.getHumanId(),
				prisonerConverter.convert(prisoner));
		cache.addUpdate(beArrestedId, prisonerConverter.convert(beArrested));
	}

	/**
	 * 抢夺奴隶战斗回调
	 */
	public void lootCallBack(Human human, Long masterId, Long slaveId,
			boolean isWin) {
		Prisoner prisoner = getPrisoner(human.getHumanGuid());
		Prisoner master = getPrisoner(masterId);
		prisoner.setFighting(false);
		master.setFighting(false);
		prisoner.setArrestedNum(prisoner.getArrestedNum() + 1);
		if (!isWin) {// 如果失败
			if (slaveId == null) {
				slaveId = 0L;
			}
			addPrisonLog(PrisonLogType.LOOT, human.getHumanGuid(), masterId,
					slaveId, false);
			human.sendImportantMessage(LangConstants.PRISON_ARREST_FAILED);
		} else {// 成功后，如果slaveId为null，是抢随机奴隶
			if (prisoner.getIdentityType() == IdentityType.FREEMAN.getIndex()) {
				prisoner.setIdentityType(IdentityType.MASTER.getIndex());
			}
			if (slaveId == null) {// 随机一个不在互动中的奴隶
				List<Prisoner> notInteractingSlaveList = getNotInteractingSlaveList(master);
				int slaveNum = notInteractingSlaveList.size();
				int ranNum = RandomUtils.nextInt(slaveNum);
				Prisoner slave = notInteractingSlaveList.get(ranNum);
				// 结算原主人获得的经验
				caculateExperience(master, slave, slave.getCurrentExperience());
				// 向原先主人发送邮件
				String theme = GameServerAssist.getSysLangService().read(
						LangConstants.PRISON_MAIL_THEME_SLAVE_LOOTED);
				String content = GameServerAssist.getSysLangService().read(
						LangConstants.PRISON_MAIL_CONTENT_SLAVE_LOOTED,
						slave.getHumanName(), human.getName(),
						slave.getCurrentExperience());
				MailService.sendSystemMail(masterId, theme, content, 0, null);

				slave.initSlave(prisoner, slave.getHumanLevel());
				addPrisonLog(PrisonLogType.LOOT, human.getHumanGuid(),
						masterId, slave.getHumanId(), true);
				cache.addUpdate(slave.getHumanId(),
						prisonerConverter.convert(slave));
				if (getSlaveList(master).size() == 0) {// 奴隶被抓完，才从手下败将或夺俘之敌中移除
					prisoner.removeLooserOrEnemy(master);
				}
			} else {// 抢某个奴隶
				Prisoner slave = getPrisoner(slaveId);
				// 结算原主人获得的经验
				caculateExperience(master, slave, slave.getCurrentExperience());
				// 向原先主人发送邮件
				String theme = GameServerAssist.getSysLangService().read(
						LangConstants.PRISON_MAIL_THEME_SLAVE_LOOTED);
				String content = GameServerAssist.getSysLangService().read(
						LangConstants.PRISON_MAIL_CONTENT_SLAVE_LOOTED,
						slave.getHumanName(), human.getName(),
						slave.getCurrentExperience());
				MailService.sendSystemMail(masterId, theme, content, 0, null);
				// 从手下败将或夺俘之敌中移除
				prisoner.removeLooserOrEnemy(slave);
				slave.initSlave(prisoner, slave.getHumanLevel());
				addPrisonLog(PrisonLogType.LOOT, human.getHumanGuid(),
						masterId, slaveId, true);
				cache.addUpdate(slave.getHumanId(),
						prisonerConverter.convert(slave));
			}
			masterToFreeman(master);
			// 增加原先主人的夺俘之敌
			master.addEnemy(human.getHumanGuid());
			cache.addUpdate(masterId, prisonerConverter.convert(master));
		}
		cache.addUpdate(prisoner.getHumanId(),
				prisonerConverter.convert(prisoner));

	}

	/**
	 * 解救奴隶战斗回调
	 */
	public void rescueCallBack(Human human, long slaveId, boolean isWin) {
		Prisoner prisoner = getPrisoner(human.getHumanGuid());
		Prisoner slave = getPrisoner(slaveId);
		Prisoner master = getPrisoner(slave.getMasterId());
		prisoner.setFighting(false);
		master.setFighting(false);
		prisoner.setRescuedNum(prisoner.getRescuedNum() + 1);
		if (!isWin) {// 如果失败
			addPrisonLog(PrisonLogType.RESCUE, human.getHumanGuid(), slaveId,
					0, false);
		} else {// 成功后，该奴隶获得自由身
			// 结算原主人获得的经验
			caculateExperience(master, slave, slave.getCurrentExperience());
			// 向原先主人发送邮件
			String theme = GameServerAssist.getSysLangService().read(
					LangConstants.PRISON_MAIL_THEME_SLAVE_RESCUED);
			String content = GameServerAssist.getSysLangService().read(
					LangConstants.PRISON_MAIL_CONTENT_SLAVE_RESCUED,
					slave.getHumanName(), human.getName(),
					slave.getCurrentExperience());
			MailService.sendSystemMail(master.getHumanId(), theme, content, 0,
					null);
			slaveToFreeman(slave);
			masterToFreeman(master);
			addPrisonLog(PrisonLogType.RESCUE, human.getHumanGuid(), slaveId,
					0, true);
			cache.addUpdate(slave.getHumanId(),
					prisonerConverter.convert(slave));
			cache.addUpdate(master.getHumanId(),
					prisonerConverter.convert(master));
		}
		cache.addUpdate(prisoner.getHumanId(),
				prisonerConverter.convert(prisoner));
	}

	/**
	 * 奴隶与主人互动
	 */
	public void interact(Human human, int interactType) {
		Prisoner slave = getPrisoner(human.getHumanGuid());
		slave.setInteractedNum(slave.getInteractedNum() + 1);
		long now = GameServerAssist.getSystemTimeService().now();
		slave.setLastInteractTime(now);
		// 根据奴隶被逮捕时主人等级获取经验
		int ranExp = getInteractExperience(slave.getBeSlaveMasterLevel());
		// 军团科技收益加成
		ranExp = human.getHumanLegionManager().getLegionTechnologyAmend(
				LegionTechnologyType.PRISON, ranExp);
		human.addExperience(ranExp, true, ExperienceLogReason.PRISON_GOT, "");
		GCSlaveInteract msg = new GCSlaveInteract();
		msg.setRemainInteractNum(slave.getTotalInteractNum()
				- slave.getInteractedNum());
		human.sendMessage(msg);
		addPrisonInteractLog(human.getHumanGuid(), slave.getMasterId(),
				interactType);
		cache.addUpdate(slave.getHumanId(), prisonerConverter.convert(slave));
	}

	/**
	 * 主人与奴隶互动
	 */
	public void interact(Human human, Prisoner slave, int interactType) {
		Prisoner master = getPrisoner(human.getHumanGuid());
		master.setInteractedNum(master.getInteractedNum() + 1);
		long now = GameServerAssist.getSystemTimeService().now();
		slave.setLastBeInteractedTime(now);
		// 根据奴隶被逮捕时等级获取经验
		int ranExp = getInteractExperience(slave.getBeSlaveSelfLevel());
		// 军团科技收益加成
		ranExp = human.getHumanLegionManager().getLegionTechnologyAmend(
				LegionTechnologyType.PRISON, ranExp);
		human.addExperience(ranExp, true, ExperienceLogReason.PRISON_GOT, "");
		GCMasterInteract msg = new GCMasterInteract();
		msg.setRemainInteractNum(master.getTotalInteractNum()
				- master.getInteractedNum());
		human.sendMessage(msg);
		addPrisonInteractLog(human.getHumanGuid(), slave.getHumanId(),
				interactType);
		cache.addUpdate(master.getHumanId(), prisonerConverter.convert(master));
		cache.addUpdate(slave.getHumanId(), prisonerConverter.convert(slave));
	}

	/**
	 * 奴隶反抗主人战斗回调
	 */
	public void revoltCallBack(Human human, boolean isWin) {
		Prisoner slave = getPrisoner(human.getHumanGuid());
		slave.setRevoltedNum(slave.getRevoltedNum() + 1);
		slave.setFighting(false);
		Prisoner master = getPrisoner(slave.getMasterId());
		master.setFighting(false);
		if (!isWin) {// 失败
			addPrisonLog(PrisonLogType.REVOLT, human.getHumanGuid(),
					master.getHumanId(), 0, false);
		} else {// 成功后，该奴隶获得自由身
			// 结算原主人获得的经验
			caculateExperience(master, slave, slave.getCurrentExperience());
			// 向原先主人发送邮件
			String theme = GameServerAssist.getSysLangService().read(
					LangConstants.PRISON_MAIL_THEME_SLAVE_REVOLTED);
			String content = GameServerAssist.getSysLangService().read(
					LangConstants.PRISON_MAIL_CONTENT_SLAVE_REVOLTED,
					slave.getHumanName(), slave.getCurrentExperience());
			MailService.sendSystemMail(master.getHumanId(), theme, content, 0,
					null);
			slave.setIdentityType(IdentityType.FREEMAN.getIndex());
			masterToFreeman(master);
			addPrisonLog(PrisonLogType.REVOLT, human.getHumanGuid(),
					master.getHumanId(), 0, true);
			cache.addUpdate(master.getHumanId(),
					prisonerConverter.convert(master));
		}
		cache.addUpdate(slave.getHumanId(), prisonerConverter.convert(slave));
	}

	/**
	 * 奴隶求救
	 */
	public void forHelp(Human human, long helperHumanId) {
		Prisoner prisoner = getPrisoner(human.getHumanGuid());
		prisoner.setForHelpedNum(prisoner.getForHelpedNum() + 1);
		GCSlaveForHelp msg = new GCSlaveForHelp();
		msg.setRemainSosNum(prisoner.getTotalForHelpNum()
				- prisoner.getForHelpedNum());
		human.sendMessage(msg);
		addPrisonLog(PrisonLogType.SOS, human.getHumanGuid(), helperHumanId, 0,
				true);
		// 向求助的玩家发消息
		Human forHelpHuman = GameServerAssist.getGameWorld()
				.getSceneHumanManager().getHumanByGuid(helperHumanId);
		if (forHelpHuman != null) {
			GCSlaveForHelped otherMsg = new GCSlaveForHelped();
			// 需要解救的信息
			otherMsg.setToRescuer(PrisonerToInfoConverter.convert(prisoner));
			forHelpHuman.sendMessage(otherMsg);
		}
		cache.addUpdate(prisoner.getHumanId(),
				prisonerConverter.convert(prisoner));
	}

	/**
	 * 获取主人的奴隶列表
	 */
	public List<Prisoner> getSlaveList(Prisoner master) {
		List<Prisoner> slaveList = new ArrayList<Prisoner>();
		for (long id : prisonersMap.keySet()) {
			Prisoner p = prisonersMap.get(id);
			if (p.getIdentityType() == IdentityType.SLAVE.getIndex()
					&& p.getMasterId() == master.getHumanId()) {
				slaveList.add(p);
			}
		}
		return slaveList;
	}

	/**
	 * 获取不在互动中的奴隶列表
	 */
	public List<Prisoner> getNotInteractingSlaveList(Prisoner master) {
		List<Prisoner> result = new ArrayList<Prisoner>();
		for (Prisoner slave : getSlaveList(master)) {
			if (!slave.isInteracting()) {
				result.add(slave);
			}
		}
		return result;
	}

	/**
	 * 根据等级获取互动经验
	 */
	private int getInteractExperience(int level) {
		// 获得区间内随机值
		PrisonExperienceTemplate template = this.prisonTemplateManager
				.getPrisonExperienceTemplate(level);
		int interactExp = template.getInteractExperience();
		// 双倍经验概率
		int doubleRate = GameServerAssist.getGameConstants()
				.getInteractDoubleExperience();
		if (RandomUtils.nextInt(10000) < doubleRate) {
			interactExp *= 2;
		}
		return interactExp;
	}

	/**
	 * 获取军团内奴隶列表
	 */
	public List<Prisoner> getLegionSlaveList(Legion legion) {
		List<Prisoner> slaveList = new ArrayList<Prisoner>();
		for (long id : prisonersMap.keySet()) {
			Prisoner p = prisonersMap.get(id);
			Legion prisonerLegion = GameServerAssist.getGlobalLegionManager()
					.getLegion(id);
			if (p.getIdentityType() == IdentityType.SLAVE.getIndex()
					&& prisonerLegion != null
					&& prisonerLegion.getId() == legion.getId()) {
				slaveList.add(p);
			}
		}
		return slaveList;
	}

	/**
	 * 获取军团内非奴隶列表
	 */
	public List<Prisoner> getLegionArrestorList(Legion legion) {
		List<Prisoner> arrestorList = new ArrayList<Prisoner>();
		for (long id : prisonersMap.keySet()) {
			Prisoner p = prisonersMap.get(id);
			Legion prisonerLegion = GameServerAssist.getGlobalLegionManager()
					.getLegion(id);
			if (p.getIdentityType() != IdentityType.SLAVE.getIndex()
					&& prisonerLegion != null
					&& prisonerLegion.getId() == legion.getId()) {
				arrestorList.add(p);
			}
		}
		return arrestorList;
	}

	/**
	 * 购买抓捕次数
	 */
	public void buyArrestNum(Human human) {
		Prisoner prisoner = getPrisoner(human.getHumanGuid());
		int costCrystal = prisonTemplateManager.getPrisonBuyArrestNumTemplate(
				prisoner.getBuyArrestNum() + 1).getCostCrystal();
		// 花费魔晶
		if (human.getWallet().costMoney(CurrencyType.CRYSTAL, costCrystal,
				MoneyLogReason.PRISON_BUY_ARREST_NUM, "")) {
			prisoner.setBuyArrestNum(prisoner.getBuyArrestNum() + 1);
			cache.addUpdate(prisoner.getHumanId(),
					prisonerConverter.convert(prisoner));
			GCBuyArrestNum msg = new GCBuyArrestNum();
			msg.setRemainArrestNum(prisoner.getTotalArrestNum()
					- prisoner.getArrestedNum());
			msg.setNextBuyCost(prisonTemplateManager
					.getPrisonBuyArrestNumTemplate(
							prisoner.getBuyArrestNum() + 1).getCostCrystal());
			human.sendMessage(msg);
		}
	}

	/**
	 * 提取经验入口
	 */
	public void extractExperience(Human human, long slaveHumanId,
			int extractType) {
		MasterExtractType type = MasterExtractType.indexOf(extractType);
		switch (type) {
		case EXTRACT_CURRENT:
			extractCurrentExperience(human, slaveHumanId);
			break;
		case EXTRACT_ONE_HOUR:
			extractOneHourExperience(human, slaveHumanId);
			break;
		case EXTRACT_TOTAL:
			extractTotalExperience(human, slaveHumanId);
			break;
		}
	}

	/**
	 * 提取当前经验
	 */
	private void extractCurrentExperience(Human human, long slaveHumanId) {
		Prisoner master = getPrisoner(human.getHumanGuid());
		Prisoner slave = getPrisoner(slaveHumanId);
		// 计算需要提取的经验
		int currentExperience = slave.getCurrentExperience();
		caculateExperience(master, slave, currentExperience);
		// 设置奴隶被提取时间
		slave.setLastBeExtractedTime(timeService.now());
		// 发送消息
		GCMasterExtractExperience msg = new GCMasterExtractExperience();
		msg.setExtractType(MasterExtractType.EXTRACT_CURRENT.getIndex());
		human.sendMessage(msg);
		// 更新缓存
		cache.addUpdate(master.getHumanId(), prisonerConverter.convert(master));
		cache.addUpdate(slave.getHumanId(), prisonerConverter.convert(slave));
	}

	/**
	 * 提取1小时经验
	 */
	private void extractOneHourExperience(Human human, long slaveHumanId) {
		Prisoner master = getPrisoner(human.getHumanGuid());
		Prisoner slave = getPrisoner(slaveHumanId);
		// 消费魔晶
		int costCrystal = gameConstants.getCostCrystalPerHourExp();
		// 花费魔晶
		if (human.getWallet().costMoney(CurrencyType.CRYSTAL, costCrystal,
				MoneyLogReason.PRISON_EXTRACT_EXPERIENCE, "")) {
			// 计算需要提取的经验
			int oneHourExperience = slave.getOneHourExperience();
			caculateExperience(master, slave, oneHourExperience);
			// 设置奴隶被提取时间、奴隶被捕时间提前一个点
			slave.setLastBeExtractedTime(timeService.now());
			slave.setBeSlaveTime(slave.getBeSlaveTime() - TimeUtils.HOUR);
			// 如果最后剩下不到1小时，奴隶被释放
			if (oneHourExperience >= slave.getTotalExperience()) {
				// 奴隶恢复自由
				slaveToFreeman(slave);
				// 解除互动保护
				removeInteractGuard(slave);
				// 如果主人没有了奴隶也变成自由人
				masterToFreeman(master);
			}
			// 发送消息
			GCMasterExtractExperience msg = new GCMasterExtractExperience();
			msg.setExtractType(MasterExtractType.EXTRACT_ONE_HOUR.getIndex());
			human.sendMessage(msg);
			// 更新缓存
			cache.addUpdate(master.getHumanId(),
					prisonerConverter.convert(master));
			cache.addUpdate(slave.getHumanId(),
					prisonerConverter.convert(slave));
		}

	}

	/**
	 * 提取全部经验
	 */
	public void extractTotalExperience(Human human, long slaveHumanId) {
		Prisoner master = getPrisoner(human.getHumanGuid());
		Prisoner slave = getPrisoner(slaveHumanId);
		// 消费魔晶
		int hour = (int) ((slave.getSlaveDueTime() - slave
				.getLastBeExtractedTime()) / TimeUtils.HOUR + 1);
		int costCrystal = hour * gameConstants.getCostCrystalPerHourExp();
		// 花费魔晶
		if (human.getWallet().costMoney(CurrencyType.CRYSTAL, costCrystal,
				MoneyLogReason.PRISON_EXTRACT_EXPERIENCE, "")) {
			// 计算需要提取的经验
			int totalExperience = slave.getTotalExperience();
			caculateExperience(master, slave, totalExperience);
			// 奴隶恢复自由
			slaveToFreeman(slave);
			// 解除互动保护
			removeInteractGuard(slave);
			// 如果主人没有了奴隶也变成自由人
			masterToFreeman(master);
			// 发送消息
			GCMasterExtractExperience msg = new GCMasterExtractExperience();
			msg.setExtractType(MasterExtractType.EXTRACT_TOTAL.getIndex());
			human.sendMessage(msg);
			// 更新缓存
			cache.addUpdate(master.getHumanId(),
					prisonerConverter.convert(master));
			cache.addUpdate(slave.getHumanId(),
					prisonerConverter.convert(slave));
		}
	}

	/**
	 * 结算主人可获得的奴隶经验(主动提取与被动活动统一调用)
	 * 
	 * @param master
	 *            主人
	 * @param slave
	 *            奴隶
	 * @param extractExperience
	 *            获得经验
	 */
	private void caculateExperience(Prisoner master, Prisoner slave,
			int gotExperience) {
		// 如果超过最大经验限制，余下的舍去
		int expLimit = prisonTemplateManager.getPrisonExperienceTemplate(
				master.getHumanLevel()).getExperienceLimit();
		Human human = GameServerAssist.getGameWorld().getSceneHumanManager()
				.getHumanByGuid(master.getHumanId());
		int addExperience = 0;
		if (master.getExtractedExperience() + gotExperience >= expLimit) {
			if (human != null) {
				human.sendImportantMessage(LangConstants.PRISON_OVER_MAX_EXPERIENCE);
			}
			master.setExtractedExperience(expLimit);
			addExperience = expLimit - master.getExtractedExperience();
		} else {
			master.setExtractedExperience(master.getExtractedExperience()
					+ gotExperience);
			addExperience = gotExperience;
		}
		if (human != null) {// 角色在线直接把经验加上
			human.addExperience(addExperience, true,
					ExperienceLogReason.PRISON_GOT, "");
		} else {// 不在线，先持久化，等下次登录加上
			master.setOffLineExperience(addExperience);
		}
	}

	/**
	 * 主人释放奴隶(经验不作处理)
	 */
	public void release(Human human, long slaveHumanId) {
		Prisoner master = getPrisoner(human.getHumanGuid());
		Prisoner slave = getPrisoner(slaveHumanId);
		slaveToFreeman(slave);
		// 如果主人没有了奴隶也变成自由人
		masterToFreeman(master);
		GCMasterRelease msg = new GCMasterRelease();
		human.sendMessage(msg);
		cache.addUpdate(master.getHumanId(), prisonerConverter.convert(master));
		cache.addUpdate(slave.getHumanId(), prisonerConverter.convert(slave));
	}

	/**
	 * 系统释放奴隶
	 */
	private void systemRelease(Prisoner slave) {
		Prisoner master = getPrisoner(slave.getMasterId());
		// 结算主人获得的经验
		caculateExperience(master, slave, slave.getCurrentExperience());
		// 向主人发送邮件
		String theme = GameServerAssist.getSysLangService().read(
				LangConstants.PRISON_MAIL_THEME_SLAVE_OVER_TIME);
		String content = GameServerAssist.getSysLangService().read(
				LangConstants.PRISON_MAIL_CONTENT_SLAVE_OVER_TIME,
				slave.getHumanName(), slave.getCurrentExperience());
		MailService
				.sendSystemMail(master.getHumanId(), theme, content, 0, null);
		slaveToFreeman(slave);
		// 解除互动保护
		removeInteractGuard(slave);
		// 如果主人没有了奴隶也变成自由人
		masterToFreeman(master);
		// 更新缓存
		cache.addUpdate(master.getHumanId(), prisonerConverter.convert(master));
		cache.addUpdate(slave.getHumanId(), prisonerConverter.convert(slave));
	}

	/**
	 * 解除奴隶的互动保护
	 */
	private void removeInteractGuard(Prisoner slave) {
		// 将互动时间消除
		slave.setLastInteractTime(timeService.now()
				- gameConstants.getInteractTimeLimit() * TimeUtils.MIN);
		slave.setLastBeInteractedTime(timeService.now()
				- gameConstants.getInteractTimeLimit() * TimeUtils.MIN);
	}

	/**
	 * 主人没有了奴隶，变成自由人
	 */
	private void masterToFreeman(Prisoner master) {
		if (getSlaveList(master).size() == 0) {
			master.setIdentityType(IdentityType.FREEMAN.getIndex());
			// 如果在线通知客户端，身份变更
			Human masterHuman = GameServerAssist.getGameWorld()
					.getSceneHumanManager().getHumanByGuid(master.getHumanId());
			if (masterHuman != null) {
				masterHuman
						.sendImportantMessage(LangConstants.PRISON_MASTER_TO_FREEMAN);
				GCPrisonIdentityChanged msg = new GCPrisonIdentityChanged();
				msg.setIdentityType(master.getIdentityType());
				masterHuman.sendMessage(msg);
			}
		}
	}

	/**
	 * 奴隶变成自由人
	 */
	private void slaveToFreeman(Prisoner slave) {
		slave.setIdentityType(IdentityType.FREEMAN.getIndex());
		// 如果在线通知客户端，身份变更
		Human slaveHuman = GameServerAssist.getGameWorld()
				.getSceneHumanManager().getHumanByGuid(slave.getHumanId());
		if (slaveHuman != null) {
			slaveHuman
					.sendImportantMessage(LangConstants.PRISON_SLAVE_TO_FREEMAN);
			GCPrisonIdentityChanged msg = new GCPrisonIdentityChanged();
			msg.setIdentityType(slave.getIdentityType());
			slaveHuman.sendMessage(msg);

		}
	}

	/**
	 * 定时任务:零点重置数据
	 */
	public void resetPrisonerData() {
		for (long humanId : prisonersMap.keySet()) {
			Prisoner prisoner = prisonersMap.get(humanId);
			prisoner.reset();
			cache.addUpdate(prisoner.getHumanId(),
					prisonerConverter.convert(prisoner));
		}
		// 清理战俘营日志
		for (long logId : prisonLogsMap.keySet()) {
			PrisonLog log = prisonLogsMap.get(logId);
			cache.addDelete(logId, prisonLogconverter.convert(log));
		}
		prisonLogsMap.clear();
	}

	/**
	 * 定时任务:到点释放奴隶
	 */
	public void releaseSlaves() {
		for (long humanId : prisonersMap.keySet()) {
			Prisoner prisoner = prisonersMap.get(humanId);
			if (prisoner.getIdentityType() == IdentityType.SLAVE.getIndex()) {
				// 到点
				int limitHour = GameServerAssist.getGameConstants()
						.getHoldSlaveTimeLimit();
				if (timeService.now() >= prisoner.getBeSlaveTime() + limitHour
						* TimeUtils.HOUR) {
					// 如果主人正在战斗，或者该奴隶正在反抗，不释放
					Prisoner master = getPrisoner(prisoner.getMasterId());
					if (master != null && master.isFighting()
							&& prisoner.isFighting()) {
						continue;
					}
					systemRelease(prisoner);
				}
			}
		}
	}

	/**
	 * 添加战俘营日志
	 */
	private void addPrisonLog(PrisonLogType logType, long firstId,
			long secondeId, long thirdId, boolean isWin) {
		final PrisonLog log = new PrisonLog();
		log.setLogType(logType.getIndex());
		log.setFirstId(firstId);
		log.setFirstName(getPrisoner(firstId).getHumanName());
		log.setSecondId(secondeId);
		log.setSecondName(getPrisoner(secondeId).getHumanName());
		log.setThirdId(thirdId);
		log.setThirdName(getPrisoner(thirdId) == null ? "" : getPrisoner(
				thirdId).getHumanName());
		log.setResult(isWin ? 1 : 0);
		log.setOperateTime(timeService.now());
		dataService.insert(prisonLogconverter.convert(log),
				new IDBCallback<Serializable>() {

					@Override
					public void onSucceed(Serializable result) {
						long id = (Long) result;
						log.setId(id);
						prisonLogsMap.put(id, log);
					}

					@Override
					public void onFailed(String errorMsg) {

					}
				});
	}

	/**
	 * 添加战俘营互动日志(单列出来)
	 */
	private void addPrisonInteractLog(long firstId, long secondeId,
			int inderactType) {
		final PrisonLog log = new PrisonLog();
		log.setLogType(PrisonLogType.INTERACT.getIndex());
		log.setFirstId(firstId);
		log.setFirstName(getPrisoner(firstId).getHumanName());
		log.setSecondId(secondeId);
		log.setSecondName(getPrisoner(secondeId).getHumanName());
		log.setOperateTime(timeService.now());
		log.setInteractType(inderactType);
		dataService.insert(prisonLogconverter.convert(log),
				new IDBCallback<Serializable>() {

					@Override
					public void onSucceed(Serializable result) {
						long id = (Long) result;
						log.setId(id);
						prisonLogsMap.put(id, log);
					}

					@Override
					public void onFailed(String errorMsg) {

					}
				});
	}

	/**
	 * 获取角色相关的战俘营日志
	 */
	public List<PrisonLog> getPrisonLogList(Human human) {
		List<PrisonLog> logList = new ArrayList<PrisonLog>();
		for (long id : prisonLogsMap.keySet()) {
			PrisonLog log = prisonLogsMap.get(id);
			if (log.getFirstId() == human.getHumanGuid()
					|| log.getSecondId() == human.getHumanGuid()
					|| log.getThirdId() == human.getHumanGuid()) {
				logList.add(log);
			}
		}
		return logList;
	}

	/**
	 * 添加手下败将
	 */
	public void addLooser(Human human, long looserId) {
		Prisoner prisoner = getPrisoner(human.getHumanGuid());
		prisoner.addLooser(looserId);
		cache.addUpdate(prisoner.getHumanId(),
				prisonerConverter.convert(prisoner));
	}

}
