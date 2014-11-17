package com.hifun.soul.manageweb.character;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hifun.soul.core.context.ApplicationContext;
import com.hifun.soul.core.template.TemplateService;
import com.hifun.soul.core.util.KeyValuePair;
import com.hifun.soul.gameserver.costnotify.template.CostNotifyTemplate;
import com.hifun.soul.gameserver.horoscope.HoroscopeBagType;
import com.hifun.soul.gameserver.horoscope.msg.HoroscopeInfo;
import com.hifun.soul.gameserver.horoscope.service.HoroscopeTemplateManager;
import com.hifun.soul.gameserver.human.quest.aim.AimInfo;
import com.hifun.soul.gameserver.human.quest.aim.MainQuestAimType;
import com.hifun.soul.gameserver.human.quest.manager.QuestTemplateManager;
import com.hifun.soul.gameserver.human.quest.template.QuestTemplate;
import com.hifun.soul.gameserver.item.EquipItem;
import com.hifun.soul.gameserver.item.Item;
import com.hifun.soul.gameserver.item.ItemDetailType;
import com.hifun.soul.gameserver.item.assist.GemItemInfo;
import com.hifun.soul.gameserver.item.feature.ConsumeItemFeature;
import com.hifun.soul.gameserver.item.feature.EquipItemFeature;
import com.hifun.soul.gameserver.item.feature.MaterialItemFeature;
import com.hifun.soul.gameserver.mine.template.MineFieldTypeTemplate;
import com.hifun.soul.gameserver.role.properties.HumanIntProperty;
import com.hifun.soul.gameserver.role.properties.Level1Property;
import com.hifun.soul.gameserver.role.properties.Level2Property;
import com.hifun.soul.gameserver.role.properties.PropertyType;
import com.hifun.soul.gameserver.skill.template.SkillTemplate;
import com.hifun.soul.gameserver.skill.template.SkillTemplateManager;
import com.hifun.soul.proto.common.Quests.QuestAimCounterData;

public class CharacterHelper {
	
	
	private static Map<Integer,String> propertyNames  = initPropertyNames();
	
	private static Map<Integer,String> initPropertyNames(){
		Map<Integer,String> nameMap = new HashMap<Integer, String>();
		int key = PropertyType.genPropertyKey(HumanIntProperty.EXPERIENCE,
				PropertyType.HUMAN_INT_PROPERTY);
		nameMap.put(key, "经验");
		key = PropertyType.genPropertyKey(HumanIntProperty.COIN,
				PropertyType.HUMAN_INT_PROPERTY);
		nameMap.put(key, "金币");
		key = PropertyType.genPropertyKey(HumanIntProperty.CRYSTAL,
				PropertyType.HUMAN_INT_PROPERTY);
		nameMap.put(key, "魔晶");
		key = PropertyType.genPropertyKey(HumanIntProperty.TECHNOLOGY_POINT,
				PropertyType.HUMAN_INT_PROPERTY);
		nameMap.put(key, "冥想力");
		key = PropertyType.genPropertyKey(HumanIntProperty.ARENA_HONOR,
				PropertyType.HUMAN_INT_PROPERTY);
		nameMap.put(key, "荣誉");
		key = PropertyType.genPropertyKey(HumanIntProperty.SKILL_POINTS,
				PropertyType.HUMAN_INT_PROPERTY);
		nameMap.put(key, "技能点");
		key = PropertyType.genPropertyKey(Level1Property.FIRE,
				PropertyType.LEVEL1_PROPERTY);
		nameMap.put(key, "火焰");
		key = PropertyType.genPropertyKey(Level1Property.ICE,
				PropertyType.LEVEL1_PROPERTY);
		nameMap.put(key, "冰霜");
		key = PropertyType.genPropertyKey(Level1Property.LIGHT,
				PropertyType.LEVEL1_PROPERTY);
		nameMap.put(key, "光明");
		key = PropertyType.genPropertyKey(Level1Property.SHADOW,
				PropertyType.LEVEL1_PROPERTY);
		nameMap.put(key, "暗影");
		key = PropertyType.genPropertyKey(Level1Property.NATURE,
				PropertyType.LEVEL1_PROPERTY);
		nameMap.put(key, "自然");
		key = PropertyType.genPropertyKey(Level2Property.MAX_HP,
				PropertyType.LEVEL2_PROPERTY);
		nameMap.put(key, "最大hp");
		key = PropertyType.genPropertyKey(Level2Property.ATTACK,
				PropertyType.LEVEL2_PROPERTY);
		nameMap.put(key, "技能攻击");
		key = PropertyType.genPropertyKey(Level2Property.DEFENSE,
				PropertyType.LEVEL2_PROPERTY);
		nameMap.put(key, "技能防御力");
		key = PropertyType.genPropertyKey(Level2Property.HIT,
				PropertyType.LEVEL2_PROPERTY);
		nameMap.put(key, "命中");
		key = PropertyType.genPropertyKey(Level2Property.DODGE,
				PropertyType.LEVEL2_PROPERTY);
		nameMap.put(key, "闪避");
		key = PropertyType.genPropertyKey(Level2Property.CRITICAL,
				PropertyType.LEVEL2_PROPERTY);
		nameMap.put(key, "暴击");
		key = PropertyType.genPropertyKey(Level2Property.UNCRITICAL,
				PropertyType.LEVEL2_PROPERTY);
		nameMap.put(key, "韧性");
		key = PropertyType.genPropertyKey(Level2Property.CRITICAL_DAMAGE,
				PropertyType.LEVEL2_PROPERTY);
		nameMap.put(key, "暴击伤害");
		key = PropertyType.genPropertyKey(Level2Property.UNCRITICAL_DAMAGE,
				PropertyType.LEVEL2_PROPERTY);
		nameMap.put(key, "韧性伤害");
		key = PropertyType.genPropertyKey(Level2Property.PARRY,
				PropertyType.LEVEL2_PROPERTY);
		nameMap.put(key, "格挡");
		key = PropertyType.genPropertyKey(Level2Property.PARRY_DAMAGE,
				PropertyType.LEVEL2_PROPERTY);
		nameMap.put(key, "格挡伤害");
		key = PropertyType.genPropertyKey(Level2Property.UNPARRY,
				PropertyType.LEVEL2_PROPERTY);
		nameMap.put(key, "破击");
		key = PropertyType.genPropertyKey(Level2Property.UNPARRY_DAMAGE,
				PropertyType.LEVEL2_PROPERTY);
		nameMap.put(key, "破击伤害");
		key = PropertyType.genPropertyKey(Level2Property.RED_MAX,
				PropertyType.LEVEL2_PROPERTY);
		nameMap.put(key, "红魔上限");
		key = PropertyType.genPropertyKey(Level2Property.RED_INIT_VALUE,
				PropertyType.LEVEL2_PROPERTY);
		nameMap.put(key, "红魔初始值");
		key = PropertyType.genPropertyKey(Level2Property.RED_ELIMINATE_BONUS,
				PropertyType.LEVEL2_PROPERTY);
		nameMap.put(key, "消除红魔加成值");
		key = PropertyType.genPropertyKey(Level2Property.YELLOW_MAX,
				PropertyType.LEVEL2_PROPERTY);
		nameMap.put(key, "黄魔上限");
		key = PropertyType.genPropertyKey(Level2Property.YELLOW_INIT_VALUE,
				PropertyType.LEVEL2_PROPERTY);
		nameMap.put(key, "黄魔初始值");
		key = PropertyType.genPropertyKey(Level2Property.YELLOW_ELIMINATE_BONUS,
				PropertyType.LEVEL2_PROPERTY);
		nameMap.put(key, "消除黄魔加成");
		key = PropertyType.genPropertyKey(Level2Property.BLUE_MAX,
				PropertyType.LEVEL2_PROPERTY);
		nameMap.put(key, "蓝魔上限");
		key = PropertyType.genPropertyKey(Level2Property.BLUE_INIT_VALUE,
				PropertyType.LEVEL2_PROPERTY);
		nameMap.put(key, "蓝魔初始值");
		key = PropertyType.genPropertyKey(Level2Property.BLUE_ELIMINATE_BONUS,
				PropertyType.LEVEL2_PROPERTY);
		nameMap.put(key, "消除蓝魔加成值");
		key = PropertyType.genPropertyKey(Level2Property.GREEN_MAX,
				PropertyType.LEVEL2_PROPERTY);
		nameMap.put(key, "绿魔上限");
		key = PropertyType.genPropertyKey(Level2Property.GREEN_INIT_VALUE,
				PropertyType.LEVEL2_PROPERTY);		
		nameMap.put(key, "绿魔初始值");
		key = PropertyType.genPropertyKey(Level2Property.GREEN_ELIMINATE_BONUS,
				PropertyType.LEVEL2_PROPERTY);
		nameMap.put(key, "消除绿魔获得的加成");
		key = PropertyType.genPropertyKey(Level2Property.PURPLE_MAX,
				PropertyType.LEVEL2_PROPERTY);
		nameMap.put(key, "紫魔上限");
		key = PropertyType.genPropertyKey(Level2Property.PURPLE_INIT_VALUE,
				PropertyType.LEVEL2_PROPERTY);
		nameMap.put(key, "紫魔初始值");
		key = PropertyType.genPropertyKey(Level2Property.PURPLE_ELIMINATE_BONUS,
				PropertyType.LEVEL2_PROPERTY);
		nameMap.put(key, "消除紫魔加成值");
		key = PropertyType.genPropertyKey(Level2Property.GEM_ATTACK,
				PropertyType.LEVEL2_PROPERTY);
		nameMap.put(key, "宝石攻击力");		
		key = PropertyType.genPropertyKey(Level2Property.GEM_DEFENSE,
				PropertyType.LEVEL2_PROPERTY);
		nameMap.put(key, "宝石防御力");
		key = PropertyType.genPropertyKey(Level2Property.FIRST_ATTACK,
				PropertyType.LEVEL2_PROPERTY);
		nameMap.put(key, "先攻");
		key = PropertyType.genPropertyKey(Level2Property.PHYSICAL_RESISTANCE,
				PropertyType.LEVEL2_PROPERTY);
		nameMap.put(key, "物理抗性");
		key = PropertyType.genPropertyKey(Level2Property.MAGIC_RESISTANCE,
				PropertyType.LEVEL2_PROPERTY);		
		nameMap.put(key, "法术抗性");
		key = PropertyType.genPropertyKey(Level2Property.BLACK_GEM_ATTACK_PER_ADD_RATE,
				PropertyType.LEVEL2_PROPERTY);
		nameMap.put(key, "黑宝石攻击加成百分比");
		key = PropertyType.genPropertyKey(Level2Property.WHITE_GEM_RECOVER_ENERGY_RATE,
				PropertyType.LEVEL2_PROPERTY);
		nameMap.put(key, "白宝石恢复能量上限百分比");		
		return nameMap;
	}
	
	/**
	 * 获取一二级属性的名称
	 * 
	 * @param propertyType
	 * @param propertyKey
	 * @return
	 */
	public static String getCharacterLevel1And2PropertyName(int propertyType,int propertyKey){
		int finalKey = PropertyType.genPropertyKey(propertyKey, propertyType);
		return getCharacterPropertyName(finalKey);
	}
	
	/**
	 * 获取一二级属性的名称
	 * 
	 * @param propertyFinalKey
	 * @return
	 */
	public static String getCharacterPropertyName(int propertyFinalKey){			
		if(propertyNames.containsKey(propertyFinalKey)){
			return propertyNames.get(propertyFinalKey);
		}
		else{
			return "unknown";	
		}
	}
	
	/**
	 * 生成物品的特殊属性
	 * @param item
	 * @return
	 */
	public static String getItemSpecialPropertyInfo(Item item){
		String result = "";
		StringBuilder sbElseProp= new StringBuilder();
		if(item.isEquip()){
			EquipItem equipitem = (EquipItem)item;
			EquipItemFeature equipItemFeature = (EquipItemFeature)item.getFeature();						
			sbElseProp.append("[等级限制：");
			sbElseProp.append(equipitem.getLimitLevel());
			sbElseProp.append("]; [职业限制：");
			sbElseProp.append(equipitem.getLimitOccupation());
			sbElseProp.append("]; [性别限制：");
			sbElseProp.append(equipitem.getLimitSex());
			sbElseProp.append("]; [装备耐久：");
			sbElseProp.append(equipItemFeature.getEndure());
			sbElseProp.append("]; [强化等级：");
			sbElseProp.append(equipItemFeature.getLevel());
			sbElseProp.append("]; [宝石孔数：");
			sbElseProp.append(equipItemFeature.getEquipHole());
			sbElseProp.append("]; [基础属性：");
			sbElseProp.append(CharacterHelper.getKeyValuePairPropertiesString(equipItemFeature.getEquipBaseAttributes()));
			sbElseProp.append("]; [特殊属性：");
			sbElseProp.append(CharacterHelper.getKeyValuePairPropertiesString(equipItemFeature.getEquipSpecialAttributes()));
			sbElseProp.append("]; [强化属性：");
			sbElseProp.append(CharacterHelper.getKeyValuePairPropertiesString(equipItemFeature.getEquipUpgradeAttributes()));
			sbElseProp.append("]; [宝石信息：");
			for(GemItemInfo gemItemInfo : equipItemFeature.getGemItemInfos()){
				if(gemItemInfo==null){
					break;
				}
				sbElseProp.append("[宝石id：");
				sbElseProp.append(gemItemInfo.getItemId());
				sbElseProp.append("]; [宝石位置：");
				sbElseProp.append(gemItemInfo.getIndex());
				sbElseProp.append("]; [宝石属性：");
				sbElseProp.append(CharacterHelper.getKeyValuePairPropertiesString(gemItemInfo.getEquipGemAttributes()));
				sbElseProp.append("]");
			}			
			sbElseProp.append("]");
			result = sbElseProp.toString();
		}
		if(item.getType()==ItemDetailType.GEM.getIndex()){
			MaterialItemFeature materialItemFeature = (MaterialItemFeature)item.getFeature();
			List<KeyValuePair<Integer,Integer>> gemAttrList = materialItemFeature.getGemAttributes();
			result = CharacterHelper.getKeyValuePairPropertiesString(gemAttrList);
		}
		else if(item.getType()==ItemDetailType.FORTUNESTONE.getIndex()){
			ConsumeItemFeature consumeItemFeature = (ConsumeItemFeature)item.getFeature();
			sbElseProp.append("成功率加成："+consumeItemFeature.getExtraSuccessRate());
			result = sbElseProp.toString();
		}
		return result;
	}
	
	/**
	 * 将KayValue形式的角色属性列表转换为用于显示的字符串
	 * 
	 * @param keyValuePairList
	 * @return
	 */
	public static <V> String getKeyValuePairPropertiesString(List<KeyValuePair<Integer,V>> keyValuePairList){
		StringBuilder sbElseProp= new StringBuilder();
		for(KeyValuePair<Integer,V> attr : keyValuePairList){
			sbElseProp.append("[");
			sbElseProp.append(CharacterHelper.getCharacterPropertyName(attr.getKey()));
			sbElseProp.append(":");
			sbElseProp.append(attr.getValue());
			sbElseProp.append("]; ");
		}
		return sbElseProp.toString();
	}
	
	/**
	 * 将KayValue形式的角色属性数组转换为用于显示的字符串
	 * 
	 * @param keyValuePairList
	 * @return
	 */
	public static <V> String getKeyValuePairPropertiesString(KeyValuePair<Integer,V>[] keyValuePairList){
		StringBuilder sbElseProp= new StringBuilder();
		for(KeyValuePair<Integer,V> attr : keyValuePairList){
			sbElseProp.append("[");
			sbElseProp.append(CharacterHelper.getCharacterPropertyName(attr.getKey()));
			sbElseProp.append(":");
			sbElseProp.append(attr.getValue());
			sbElseProp.append("]; ");
		}
		return sbElseProp.toString();
	}
	
	/**
	 * 将任务目标转换为字符串显示
	 * 
	 * @param aimCounterList
	 * @return
	 */
	public static String getQuestAimInfoListString(List<AimInfo> aimInfoList){
		StringBuilder sbQuestAim= new StringBuilder();
		sbQuestAim.append("[任务目标:");
		for(AimInfo aimInfo : aimInfoList){
			sbQuestAim.append("[目标类型：");
			sbQuestAim.append(MainQuestAimType.typeOf(aimInfo.getAimType()));			
			sbQuestAim.append("]; [目标参数1：");
			sbQuestAim.append(aimInfo.getParam1());
			sbQuestAim.append("]; [目标参数2：");
			sbQuestAim.append(aimInfo.getParam2());
			sbQuestAim.append("] ");
		}
		sbQuestAim.append("] ");
		return sbQuestAim.toString();
	}
	
	/**
	 * 将任务目标计数器转换为字符串显示
	 * 
	 * @param aimCounterList
	 * @return
	 */
	public static String getQuestAimCounterListString(List<QuestAimCounterData> aimCounterList){
		StringBuilder sbQuestAim= new StringBuilder();
		sbQuestAim.append("[任务目标计数器:");
		for(QuestAimCounterData aimCounter : aimCounterList){
			sbQuestAim.append("[目标索引：");
			sbQuestAim.append(aimCounter.getAimIndex());			
			sbQuestAim.append("]; [目标类型：");
			sbQuestAim.append(MainQuestAimType.typeOf(aimCounter.getAimType()));
			sbQuestAim.append("]; [完成值：");
			sbQuestAim.append(aimCounter.getValue());
			sbQuestAim.append("] ");
		}
		sbQuestAim.append("] ");
		return sbQuestAim.toString();
	}
	
	/**
	 * 获取任务模板
	 * 
	 * @param questId
	 * @return
	 */
	public static QuestTemplate getQuestTemplateByQuestId(int questId){
		QuestTemplateManager templateManager = ApplicationContext.getApplicationContext().getBean(QuestTemplateManager.class);
		return templateManager.getQuestTemplateByQuestId(questId);
	}
	
	/**
	 * 返回星运信息
	 * @param horoscopeId
	 * @param bagType
	 * @return
	 */
	public static HoroscopeInfo getHoroscopeInfo(int horoscopeId,int bagType){
		HoroscopeTemplateManager templateManager = ApplicationContext.getApplicationContext().getBean(HoroscopeTemplateManager.class);
		return templateManager.genHoroscopeInfo(horoscopeId, HoroscopeBagType.indexOf(bagType));
	}
	
	/**
	 * 获取技能名称
	 * @param skillId
	 * @return
	 */
	public static String getSkillName(int skillId){
		SkillTemplateManager templateManager = ApplicationContext.getApplicationContext().getBean(SkillTemplateManager.class);
		SkillTemplate template = templateManager.getSkillTemplate(skillId);
		return template!=null ? template.getSkillName() : "";
	}
	
	/**
	 * 获取矿坑类型名称
	 * @param mineType
	 * @return
	 */
	public static String getMineName(int mineType){
		TemplateService _templateService = ApplicationContext.getApplicationContext().getBean(TemplateService.class);
		MineFieldTypeTemplate template = _templateService.get(mineType,MineFieldTypeTemplate.class);
		return template!=null ? template.getName() : "";
	}
	
	/**
	 * 获取消费提醒的名称
	 * @param notifyType
	 * @return
	 */
	public static String getCostNotifyName(int notifyType){
		TemplateService _templateService = ApplicationContext.getApplicationContext().getBean(TemplateService.class);
		CostNotifyTemplate template = _templateService.get(notifyType, CostNotifyTemplate.class);		
		return template!=null ? template.getName() : "";
	}
	
}
