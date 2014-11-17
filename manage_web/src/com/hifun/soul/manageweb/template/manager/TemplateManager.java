package com.hifun.soul.manageweb.template.manager;

import java.util.Map;

import com.hifun.soul.core.config.ConfigUtil;
import com.hifun.soul.core.context.ApplicationContext;
import com.hifun.soul.core.template.TemplateService;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.gift.template.GiftTemplate;
import com.hifun.soul.gameserver.horoscope.service.HoroscopeTemplateManager;
import com.hifun.soul.gameserver.human.quest.manager.QuestTemplateManager;
import com.hifun.soul.gameserver.item.service.EquipUpgradeTemplateManager;
import com.hifun.soul.gameserver.item.service.ItemTemplateManager;
import com.hifun.soul.gameserver.skill.template.SkillTemplateManager;
import com.hifun.soul.manageweb.config.ManageWebConfig;

public class TemplateManager {
	// 物品模版管理器
	private static ItemTemplateManager itemTemplateManager;
	// 装备升级模板管理器
	private static EquipUpgradeTemplateManager equipUpgradeTemplateManager;
	// 任务模板管理器
	private static QuestTemplateManager questTemplateManager;
	// 技能模板管理器
	private static SkillTemplateManager skillTemplateManager;
	// 星运模板管理器
	private static HoroscopeTemplateManager horoscopeTemplateManager;
	private static SimpleItemTemplateManager simpleItemTemplateManager;
	
	private static Map<Integer,GiftTemplate> giftTemplates;	
	
	
	public static void initTemplateService(ManageWebConfig config) {
		// 模版初始化
		TemplateService templateService = ApplicationContext.getApplicationContext().getBean(TemplateService.class);
		templateService.setResourceFolder(config.getTemplatesDirPath());
		templateService.setDebug(config.getIsDebug());
		templateService.init(ConfigUtil.getConfigURL("web-templates.xml"));
		// 物品模版初始化
		itemTemplateManager = ApplicationContext.getApplicationContext().getBean(ItemTemplateManager.class);
		itemTemplateManager.init();
		// 装备升级模板初始化
		equipUpgradeTemplateManager = ApplicationContext.getApplicationContext().getBean(
				EquipUpgradeTemplateManager.class);
		equipUpgradeTemplateManager.init();
		// 任务模板初始化
		questTemplateManager = ApplicationContext.getApplicationContext().getBean(QuestTemplateManager.class);
		questTemplateManager.init();
		// 技能模板初始化
		skillTemplateManager = ApplicationContext.getApplicationContext().getBean(SkillTemplateManager.class);
		skillTemplateManager.init();
		GameServerAssist.setItemTemplateManager(itemTemplateManager);
		GameServerAssist.setEquipUpgradeTemplateManager(equipUpgradeTemplateManager);
		// 天赋模板
		giftTemplates = templateService.getAll(GiftTemplate.class);		
		// 常用物品模板
		simpleItemTemplateManager = ApplicationContext.getApplicationContext().getBean(SimpleItemTemplateManager.class);
		simpleItemTemplateManager.init();
		
		horoscopeTemplateManager = ApplicationContext.getApplicationContext().getBean(HoroscopeTemplateManager.class);
		horoscopeTemplateManager.init();
	}

	public static ItemTemplateManager getItemTemplateManager() {
		return itemTemplateManager;
	}
	
	public static EquipUpgradeTemplateManager getEquipUpgradeTemplateManager(){
		return equipUpgradeTemplateManager;
	}
	
	public static QuestTemplateManager getQuestTemplateManager(){
		return questTemplateManager;
	}
	
	public static SkillTemplateManager getSkillTemplateManager(){
		return skillTemplateManager;
	}
	public static GiftTemplate getGiftTemplate(int giftId){
		return giftTemplates.get(giftId);
	}
	
}
