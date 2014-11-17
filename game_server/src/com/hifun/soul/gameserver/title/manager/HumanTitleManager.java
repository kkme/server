package com.hifun.soul.gameserver.title.manager;

import java.util.ArrayList;
import java.util.List;

import com.hifun.soul.common.LogReasons.MoneyLogReason;
import com.hifun.soul.common.LogReasons.PropertyLogReason;
import com.hifun.soul.common.constants.LangConstants;
import com.hifun.soul.gamedb.entity.HumanEntity;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.currency.CurrencyType;
import com.hifun.soul.gameserver.event.TitleUpEvent;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.human.AbstractNotifyManager;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.human.IHumanPropertiesLoadForBattle;
import com.hifun.soul.gameserver.human.ILoginManager;
import com.hifun.soul.gameserver.role.properties.HumanIntProperty;
import com.hifun.soul.gameserver.role.properties.HumanLongProperty;
import com.hifun.soul.gameserver.role.properties.PropertyType;
import com.hifun.soul.gameserver.role.properties.amend.AmendMethod;
import com.hifun.soul.gameserver.role.properties.manager.HumanPropertyManager;
import com.hifun.soul.gameserver.title.HumanTitle;
import com.hifun.soul.gameserver.title.TitleTemplateManager;
import com.hifun.soul.gameserver.title.msg.GCGetTitleSalary;
import com.hifun.soul.gameserver.title.msg.GCOpenTitlePanel;
import com.hifun.soul.gameserver.title.msg.GCTitleLevelUp;
import com.hifun.soul.gameserver.title.template.HumanTitleProperty;
import com.hifun.soul.gameserver.title.template.HumanTitleTemplate;

/**
 * 角色军衔管理器
 * 
 * @author yandajun
 * 
 */
public class HumanTitleManager extends AbstractNotifyManager implements
		ILoginManager, IHumanPropertiesLoadForBattle {
	/** 角色 */
	private Human human;
	/** 军衔模板 */
	private TitleTemplateManager titleTemplateManager;

	public HumanTitleManager(Human human) {
		super(human);
		this.human = human;
		this.human.registerLoginManager(this);
		titleTemplateManager = GameServerAssist.getTitleTemplateManager();
		this.human.addNotifyManager(this);
	}

	@Override
	public void onLogin() {
		// 计算属性加成
		this.amendTitleProperty(human.getPropertyManager());
	}

	/**
	 * 向客户端发送军衔列表信息
	 * 
	 */
	public void sendHumanTitleInfo() {
		// 1.获取当前的军衔id
		GCOpenTitlePanel gcMsg = new GCOpenTitlePanel();
		gcMsg.setCurrentTitleId(this.getCurrentTitle());
		gcMsg.setCurrentPrestige(human.getPrestige());
		gcMsg.setIsGotTitleSalary(false);
		// 无军衔时默认荣誉上限
		gcMsg.setDefaultMaxHonor(titleTemplateManager.getHumanTitleTemplate(0)
				.getMaxHonor());
		// 2.从模板中获取军衔列表
		List<HumanTitle> titleList = this.getTitleList();
		// 3.向客户端发送消息
		HumanTitle[] titles = new HumanTitle[titleList.size()];
		gcMsg.setTitleList(titleList.toArray(titles));
		human.sendMessage(gcMsg);
	}

	/**
	 * 是否可升军衔
	 */
	private boolean canUpgradeTitle() {
		int currentTitle = this.getCurrentTitle();
		if (currentTitle >= titleTemplateManager.getMaxTitleLevel()) {
			return false;
		}
		int currentPrestige = human.getPrestige();
		HumanTitleTemplate titleTemplate = titleTemplateManager
				.getHumanTitleTemplate(currentTitle);
		int needPrestige = titleTemplate.getNeedPrestige();
		if (currentPrestige < needPrestige) {
			return false;
		}
		return true;
	}

	/**
	 * 军衔升级
	 * 
	 */
	public void upgradeTitle() {
		// 1. 判断能否升级
		int currentTitle = this.getCurrentTitle();
		if (currentTitle >= titleTemplateManager.getMaxTitleLevel()) {
			human.sendErrorMessage(LangConstants.TITLE_LEVEL_UP_MAX);
			return;
		}
		int currentPrestige = human.getPrestige();
		HumanTitleTemplate titleTemplate = titleTemplateManager
				.getHumanTitleTemplate(currentTitle);
		int needPrestige = titleTemplate.getNeedPrestige();
		if (currentPrestige < needPrestige) {
			human.sendErrorMessage(LangConstants.TITLE_LEVEL_UP_PRESTIGE_NOT_ENOUGH);
			return;
		}
		human.setPrestige(human.getPrestige() - needPrestige);
		// 2. 升级处理(升衔、携带技能、属性加成)
		human.setCurrentTitle(currentTitle + 1);
		human.setTitleSkillNum(titleTemplate.getTitleSkillNum());
		this.upgradeTitleAmendProperty(currentTitle);
		// 3. 升衔之后可以领取新军衔对应的俸禄
		human.setTitleIsGotSalary(false);
		// 4. 发送响应升衔的事件、消息
		TitleUpEvent event = new TitleUpEvent();
		event.setCurrentTitle(currentTitle + 1);
		human.getEventBus().fireEvent(event);
		GCTitleLevelUp gcMsg = new GCTitleLevelUp();
		gcMsg.setCurrentTitleId(currentTitle + 1);
		gcMsg.setIsGotTitleSalary(false);
		human.sendMessage(gcMsg);
		sendNotify();
	}

	/**
	 * 领取每日俸禄
	 * 
	 */
	public void getTitleSalary() {
		boolean isGetSalary = human.getTitleIsGotSalary();
		if (isGetSalary) {
			human.sendErrorMessage(LangConstants.TITLE_GET_SALARY_FINISHED);
			return;
		}
		// 获取当前军衔
		int currentTitle = this.getCurrentTitle();
		if (currentTitle == 0) {
			return;
		}
		// 获取当前军衔对应的俸禄
		HumanTitleTemplate titleTemplate = titleTemplateManager
				.getHumanTitleTemplate(currentTitle);
		int titleSalary = titleTemplate.getTitleSalary();
		// 获取金币，添加军衔对应的俸禄
		human.getWallet().addMoney(CurrencyType.COIN, titleSalary, true,
				MoneyLogReason.GET_TITLE_SALARY, "");
		// 将俸禄领取标识置为true
		human.setTitleIsGotSalary(true);
		// 发送响应领取每日俸禄的消息
		GCGetTitleSalary gcMsg = new GCGetTitleSalary();
		gcMsg.setIsGotTitleSalary(true);
		human.sendMessage(gcMsg);
		sendNotify();
	}

	/**
	 * 过零点时更新俸禄领取状态
	 */
	public void updateIsGotSalary() {
		human.setTitleIsGotSalary(false);
		sendNotify();
	}

	/**
	 * 获取上次更新俸禄领取状态时间
	 */
	public long getLastUpdateGetSalaryTime() {
		return human.getHumanPropertiesManager().getLongPropertyValue(
				HumanLongProperty.LAST_GET_TITLE_SALARY_TIME);
	}

	/**
	 * 设置上次更新俸禄领取状态时间
	 */
	public void setLastUpdateGetSalaryTime(long time) {
		human.getHumanPropertiesManager().setLongPropertyValue(
				HumanLongProperty.LAST_GET_TITLE_SALARY_TIME, time);
	}

	/**
	 * 从模板中获取军衔列表信息
	 */
	private List<HumanTitle> getTitleList() {
		List<HumanTitle> titleList = new ArrayList<HumanTitle>();
		List<HumanTitleTemplate> titleTemplates = titleTemplateManager
				.getAllTitleTemplates();
		// 客户端不需要显示0级的军衔信息
		titleTemplates.remove(0);
		for (HumanTitleTemplate titleTemplate : titleTemplates) {
			HumanTitle title = new HumanTitle();
			title.setTitleId(titleTemplate.getId());
			// 由于配置的是升至下级需要威望，所以要往上级取值，才是升至当前级别需要威望
			HumanTitleTemplate previouseTemplate = titleTemplateManager
					.getHumanTitleTemplate(titleTemplate.getId() - 1);
			title.setNeedPrestige(previouseTemplate.getNeedPrestige());
			title.setTitleName(titleTemplate.getTitleName());
			title.setTitleSkillNum(titleTemplate.getTitleSkillNum());
			title.setTitleSalary(titleTemplate.getTitleSalary());
			title.setTitleProperties(titleTemplate.getTitleProperties());
			title.setMaxHonor(titleTemplate.getMaxHonor());
			titleList.add(title);
		}
		return titleList;
	}

	/**
	 * 当前军衔属性加成
	 */
	private void amendTitleProperty(HumanPropertyManager propertyManager) {
		int currentTitle = propertyManager.getIntPropertySet(
				PropertyType.HUMAN_INT_PROPERTY).getPropertyValue(
				HumanIntProperty.CURRENT_TITLE);
		if (currentTitle == 0) {
			return;
		}
		HumanTitleTemplate titleTemplate = titleTemplateManager
				.getHumanTitleTemplate(currentTitle);
		HumanTitleProperty[] titleProperties = titleTemplate
				.getTitleProperties();
		for (HumanTitleProperty titleProperty : titleProperties) {
			AmendMethod amendType = AmendMethod.valueOf(titleProperty
					.getAmendType());
			int propertyId = titleProperty.getPropertyId();
			int propertyValue = titleProperty.getPropertyValue();
			propertyManager.amendProperty(human, amendType, propertyId,
					propertyValue, PropertyLogReason.TITLE_UP, "");
		}
	}

	/**
	 * 升级后军衔属性加成
	 */
	private void upgradeTitleAmendProperty(int oldTitle) {
		HumanTitleTemplate upTemplate = titleTemplateManager
				.getHumanTitleTemplate(oldTitle + 1);
		for (HumanTitleProperty upTitleProperty : upTemplate
				.getTitleProperties()) {
			AmendMethod amendType = AmendMethod.valueOf(upTitleProperty
					.getAmendType());
			int propertyId = upTitleProperty.getPropertyId();
			int propertyValue = upTitleProperty.getPropertyValue();
			// 要减掉上个级别的
			HumanTitleTemplate oldTemplate = titleTemplateManager
					.getHumanTitleTemplate(oldTitle);
			if (oldTemplate != null) {
				for (HumanTitleProperty oldTitleProperty : oldTemplate
						.getTitleProperties()) {
					if (oldTitleProperty.getPropertyId() == propertyId) {
						propertyValue -= oldTitleProperty.getPropertyValue();
					}
				}
			}
			human.getPropertyManager().amendProperty(human, amendType,
					propertyId, propertyValue, PropertyLogReason.TITLE_UP, "");
		}
	}

	/**
	 * 获取当前的军衔
	 */
	private int getCurrentTitle() {
		int currentTitle = human.getCurrentTitle();
		return currentTitle;
	}

	/**
	 * 战斗属性加成
	 */
	@Override
	public void onBattlePropertiesLoad(HumanEntity humanEntity,
			HumanPropertyManager propertyManager) {
		amendTitleProperty(propertyManager);
	}

	@Override
	public GameFuncType getFuncType() {
		return GameFuncType.TITLE;
	}

	@Override
	public boolean isNotify() {
		// 可升衔或可领俸禄
		if (canUpgradeTitle() || !human.getTitleIsGotSalary()) {
			return true;
		}
		return false;
	}
}
