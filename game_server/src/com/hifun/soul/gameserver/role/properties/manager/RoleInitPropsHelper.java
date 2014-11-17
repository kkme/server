package com.hifun.soul.gameserver.role.properties.manager;

import com.hifun.soul.common.constants.SharedConstants;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.human.occupation.template.CharacterOccupationInitPropTemplate;
import com.hifun.soul.gameserver.human.template.HumanLevelUpTemplate;
import com.hifun.soul.gameserver.monster.Monster;
import com.hifun.soul.gameserver.monster.template.MonsterTemplate;
import com.hifun.soul.gameserver.role.Occupation;
import com.hifun.soul.gameserver.role.properties.HumanIntProperty;
import com.hifun.soul.gameserver.role.properties.Level1Property;
import com.hifun.soul.gameserver.role.properties.Level2Property;
import com.hifun.soul.gameserver.role.properties.PropertyType;
import com.hifun.soul.gameserver.skill.SkillDevelopType;
import com.hifun.soul.gameserver.skill.template.SkillOccupationTemplate;

/**
 * 角色初始化帮助类;
 * 
 * @author crazyjohn
 */
class RoleInitPropsHelper {
	/**
	 * 初始化玩家角色属性;
	 * 
	 * @param humanPropertyManager
	 * @param initPropTemplate
	 */
	public static void initHumanProps(
			HumanPropertyManager humanPropertyManager,
			CharacterOccupationInitPropTemplate initPropTemplate) {
		// ---- 一级裸身属性 ----//
		// 火焰
		humanPropertyManager
				.setInitLevel1Property(
						Level1Property.FIRE,
						initPropTemplate.getPower()
								+ humanPropertyManager
										.getHumanIntProperty(HumanIntProperty.SYSTEM_FIRE));
		// 寒冰
		humanPropertyManager
				.setInitLevel1Property(
						Level1Property.ICE,
						initPropTemplate.getAgile()
								+ humanPropertyManager
										.getHumanIntProperty(HumanIntProperty.SYSTEM_ICE));
		// 光明
		humanPropertyManager
				.setInitLevel1Property(
						Level1Property.LIGHT,
						initPropTemplate.getStamina()
								+ humanPropertyManager
										.getHumanIntProperty(HumanIntProperty.SYSTEM_LIGHT));
		// 暗影
		humanPropertyManager
				.setInitLevel1Property(
						Level1Property.SHADOW,
						initPropTemplate.getIntelligence()
								+ humanPropertyManager
										.getHumanIntProperty(HumanIntProperty.SYSTEM_SHADOW));
		// 自然
		humanPropertyManager
				.setInitLevel1Property(
						Level1Property.NATURE,
						initPropTemplate.getSpirit()
								+ humanPropertyManager
										.getHumanIntProperty(HumanIntProperty.SYSTEM_NATURE));
		// 武力
		humanPropertyManager.setInitLevel1Property(Level1Property.FORCE,
				humanPropertyManager
						.getHumanIntProperty(HumanIntProperty.FOSTER_FORCE));
		// 敏捷
		humanPropertyManager.setInitLevel1Property(Level1Property.AGILE,
				humanPropertyManager
						.getHumanIntProperty(HumanIntProperty.FOSTER_AGILE));
		// 智力
		humanPropertyManager
				.setInitLevel1Property(
						Level1Property.INTELLIGENCE,
						humanPropertyManager
								.getHumanIntProperty(HumanIntProperty.FOSTER_INTELLIGENCE));
		// 体力
		humanPropertyManager.setInitLevel1Property(Level1Property.STAMINA,
				humanPropertyManager
						.getHumanIntProperty(HumanIntProperty.FOSTER_STAMINA));
		// 精力
		humanPropertyManager.setInitLevel1Property(Level1Property.SPIRIT,
				humanPropertyManager
						.getHumanIntProperty(HumanIntProperty.FOSTER_SPIRIT));

		// ---- 二级裸身属性 ----//

		// hp
		// 取出升级模版;
		int level = humanPropertyManager.getIntPropertySet(
				PropertyType.HUMAN_INT_PROPERTY).getPropertyValue(
				HumanIntProperty.LEVEL);
		int levelAddHp = 0;
		if (level >= SharedConstants.INIT_HUMAN_LEVEL) {
			HumanLevelUpTemplate levelUpTemplate = GameServerAssist
					.getTemplateService()
					.get(level, HumanLevelUpTemplate.class);
			// hp等级加成值
			if (levelUpTemplate != null) {
				levelAddHp = levelUpTemplate.getHp();
			}
		}
		humanPropertyManager.setInitLevel2Property(Level2Property.MAX_HP,
				initPropTemplate.getHp() + levelAddHp);

		// 技能攻击
		humanPropertyManager.setInitLevel2Property(Level2Property.ATTACK,
				initPropTemplate.getSkillAttack());
		// 技能防御
		humanPropertyManager.setInitLevel2Property(Level2Property.DEFENSE,
				initPropTemplate.getSkillDefense());
		// 宝石攻击
		humanPropertyManager.setInitLevel2Property(Level2Property.GEM_ATTACK,
				initPropTemplate.getGemAttack());
		// 宝石防御
		humanPropertyManager.setInitLevel2Property(Level2Property.GEM_DEFENSE,
				initPropTemplate.getGemDefense());
		// 命中
		humanPropertyManager.setInitLevel2Property(Level2Property.HIT,
				initPropTemplate.getHit());
		// 闪避
		humanPropertyManager.setInitLevel2Property(Level2Property.DODGE,
				initPropTemplate.getDodge());
		// 暴擊
		humanPropertyManager.setInitLevel2Property(Level2Property.CRITICAL,
				initPropTemplate.getCritical());
		// 韧性
		humanPropertyManager.setInitLevel2Property(Level2Property.UNCRITICAL,
				initPropTemplate.getUncritical());
		// 暴击伤害
		humanPropertyManager.setInitLevel2Property(
				Level2Property.CRITICAL_DAMAGE,
				initPropTemplate.getCriticalDamage());
		// 韧性伤害
		humanPropertyManager.setInitLevel2Property(
				Level2Property.UNCRITICAL_DAMAGE,
				initPropTemplate.getUncriticalDamage());
		// 格挡
		humanPropertyManager.setInitLevel2Property(Level2Property.PARRY,
				initPropTemplate.getParry());
		// 格擋伤害
		humanPropertyManager.setInitLevel2Property(Level2Property.PARRY_DAMAGE,
				initPropTemplate.getParryDamage());
		// 破击
		humanPropertyManager.setInitLevel2Property(Level2Property.UNPARRY,
				initPropTemplate.getUnparry());
		// 破击伤害
		humanPropertyManager.setInitLevel2Property(
				Level2Property.UNPARRY_DAMAGE,
				initPropTemplate.getUnparryDamage());
		// ===== 魔法属性 =====
		// 根据职业和当前技能系取出想要模版
		Occupation occupationType = Occupation.typeOf(humanPropertyManager
				.getIntPropertySet(PropertyType.HUMAN_INT_PROPERTY)
				.getPropertyValue(HumanIntProperty.OCCUPATION));
		SkillDevelopType skillDevelopType = SkillDevelopType
				.typeOf(humanPropertyManager.getIntPropertySet(
						PropertyType.HUMAN_INT_PROPERTY).getPropertyValue(
						HumanIntProperty.SKILL_DEVELOP_TYPE));
		SkillOccupationTemplate magicPropTemplate = GameServerAssist
				.getSkillTemplateManager().getLevelMagicPropTemplate(level,
						occupationType, skillDevelopType);

		// 红魔上限
		humanPropertyManager.setInitLevel2Property(Level2Property.RED_MAX,
				magicPropTemplate.getRedMax());
		// 红魔初始值
		humanPropertyManager.setInitLevel2Property(
				Level2Property.RED_INIT_VALUE, magicPropTemplate.getRedInit());
		// 红魔加成值
		humanPropertyManager.setInitLevel2Property(
				Level2Property.RED_ELIMINATE_BONUS,
				magicPropTemplate.getRedEliminateBonus());
		// 黄魔最大值
		humanPropertyManager.setInitLevel2Property(Level2Property.YELLOW_MAX,
				magicPropTemplate.getYellowMax());
		// 黄魔初始值
		humanPropertyManager.setInitLevel2Property(
				Level2Property.YELLOW_INIT_VALUE,
				magicPropTemplate.getYellowInit());
		// 黄魔加成值
		humanPropertyManager.setInitLevel2Property(
				Level2Property.YELLOW_ELIMINATE_BONUS,
				magicPropTemplate.getYellowEliminateBonus());

		// 蓝魔最大值
		humanPropertyManager.setInitLevel2Property(Level2Property.BLUE_MAX,
				magicPropTemplate.getBlueMax());
		// 蓝魔初始值
		humanPropertyManager
				.setInitLevel2Property(Level2Property.BLUE_INIT_VALUE,
						magicPropTemplate.getBlueInit());
		// 蓝魔加成值
		humanPropertyManager.setInitLevel2Property(
				Level2Property.BLUE_ELIMINATE_BONUS,
				magicPropTemplate.getBlueEliminateBonus());

		// 绿魔最大值
		humanPropertyManager.setInitLevel2Property(Level2Property.GREEN_MAX,
				magicPropTemplate.getGreenMax());
		// 绿魔初始值
		humanPropertyManager.setInitLevel2Property(
				Level2Property.GREEN_INIT_VALUE,
				magicPropTemplate.getGreenInit());
		// 绿魔加成值
		humanPropertyManager.setInitLevel2Property(
				Level2Property.GREEN_ELIMINATE_BONUS,
				magicPropTemplate.getGreenEliminateBonus());

		// 紫魔最大值
		humanPropertyManager.setInitLevel2Property(Level2Property.PURPLE_MAX,
				magicPropTemplate.getPurpleMax());
		// 紫魔初始值
		humanPropertyManager.setInitLevel2Property(
				Level2Property.PURPLE_INIT_VALUE,
				magicPropTemplate.getPurpleInit());
		// 紫魔加成值
		humanPropertyManager.setInitLevel2Property(
				Level2Property.PURPLE_ELIMINATE_BONUS,
				magicPropTemplate.getPurpleEliminateBonus());
		// 先攻
		humanPropertyManager.setInitLevel2Property(Level2Property.FIRST_ATTACK,
				initPropTemplate.getFirstAttack());

		// 物抗
		humanPropertyManager.setInitLevel2Property(
				Level2Property.PHYSICAL_RESISTANCE,
				initPropTemplate.getPhysicalResistance());
		// 魔抗
		humanPropertyManager.setInitLevel2Property(
				Level2Property.MAGIC_RESISTANCE,
				initPropTemplate.getMagicResistance());
		// 黑宝石攻击加成值
		humanPropertyManager.setInitLevel2Property(
				Level2Property.BLACK_GEM_ATTACK_PER_ADD_RATE, GameServerAssist
						.getGameConstants().getBlackGemAttackPerAddRate());
		// 白宝石恢复能量上限百分比
		humanPropertyManager.setInitLevel2Property(
				Level2Property.WHITE_GEM_RECOVER_ENERGY_RATE, GameServerAssist
						.getGameConstants().getWhiteGemRecoverEnergyRate());

		// 角色的最大经验值从模版中取
		HumanLevelUpTemplate levelTemplate = GameServerAssist
				.getTemplateService()
				.get((humanPropertyManager
						.getIntPropertySet(PropertyType.HUMAN_INT_PROPERTY)
						.getPropertyValue(HumanIntProperty.LEVEL)),
						HumanLevelUpTemplate.class);
		humanPropertyManager.getIntPropertySet(PropertyType.HUMAN_INT_PROPERTY)
				.setPropertyValue(HumanIntProperty.MAX_EXPERIENCE,
						levelTemplate.getExperience());

	}

	/**
	 * 初始化怪物属性;
	 * 
	 * @param monster
	 */
	public static void initMonsterProps(Monster monster) {

		MonsterPropertyManager monsterPropertyManager = monster
				.getPropertyManager();
		MonsterTemplate monsterTemplate = monster.getTemplate();
		// ---- 二级裸身属性 ----//
		// 等级
		monsterPropertyManager.getIntPropertySet(
				PropertyType.HUMAN_INT_PROPERTY).setPropertyValue(
				HumanIntProperty.LEVEL, monsterTemplate.getLevel());
		// hp
		monsterPropertyManager.setInitLevel2Property(Level2Property.MAX_HP,
				monsterTemplate.getHp());
		// 技能攻击
		monsterPropertyManager.setInitLevel2Property(Level2Property.ATTACK,
				monsterTemplate.getSkillAttack());
		// 技能防御
		monsterPropertyManager.setInitLevel2Property(Level2Property.DEFENSE,
				monsterTemplate.getSkillDefense());

		// 宝石攻击
		monsterPropertyManager.setInitLevel2Property(Level2Property.GEM_ATTACK,
				monsterTemplate.getGemAttack());
		// 宝石防御
		monsterPropertyManager.setInitLevel2Property(
				Level2Property.GEM_DEFENSE, monsterTemplate.getGemDefense());
		// 命中
		monsterPropertyManager.setInitLevel2Property(Level2Property.HIT,
				monsterTemplate.getHit());
		// 闪避
		monsterPropertyManager.setInitLevel2Property(Level2Property.DODGE,
				monsterTemplate.getDodge());
		// 暴擊
		monsterPropertyManager.setInitLevel2Property(Level2Property.CRITICAL,
				monsterTemplate.getCritical());
		// 韧性
		monsterPropertyManager.setInitLevel2Property(Level2Property.UNCRITICAL,
				monsterTemplate.getUncritical());
		// 暴击伤害
		monsterPropertyManager.setInitLevel2Property(
				Level2Property.CRITICAL_DAMAGE,
				monsterTemplate.getCriticalDamage());
		// 韧性伤害
		monsterPropertyManager.setInitLevel2Property(
				Level2Property.UNCRITICAL_DAMAGE,
				monsterTemplate.getUncriticalDamage());
		// 格挡
		monsterPropertyManager.setInitLevel2Property(Level2Property.PARRY,
				monsterTemplate.getParry());
		// 格擋伤害
		monsterPropertyManager.setInitLevel2Property(
				Level2Property.PARRY_DAMAGE, monsterTemplate.getParryDamage());
		// 破击
		monsterPropertyManager.setInitLevel2Property(Level2Property.UNPARRY,
				monsterTemplate.getUnparry());
		// 破击伤害
		monsterPropertyManager.setInitLevel2Property(
				Level2Property.UNPARRY_DAMAGE,
				monsterTemplate.getUnparryDamage());
		// 红魔上限
		monsterPropertyManager.setInitLevel2Property(Level2Property.RED_MAX,
				monsterTemplate.getRedMax());
		// 红魔初始值
		monsterPropertyManager.setInitLevel2Property(
				Level2Property.RED_INIT_VALUE, monsterTemplate.getRedInit());
		// 红魔加成值
		monsterPropertyManager.setInitLevel2Property(
				Level2Property.RED_ELIMINATE_BONUS,
				monsterTemplate.getRedEliminateBonus());
		// 黄魔最大值
		monsterPropertyManager.setInitLevel2Property(Level2Property.YELLOW_MAX,
				monsterTemplate.getYellowMax());
		// 黄魔初始值
		monsterPropertyManager.setInitLevel2Property(
				Level2Property.YELLOW_INIT_VALUE,
				monsterTemplate.getYellowInit());
		// 黄魔加成值
		monsterPropertyManager.setInitLevel2Property(
				Level2Property.YELLOW_ELIMINATE_BONUS,
				monsterTemplate.getYellowEliminateBonus());

		// 蓝魔最大值
		monsterPropertyManager.setInitLevel2Property(Level2Property.BLUE_MAX,
				monsterTemplate.getBlueMax());
		// 蓝魔初始值
		monsterPropertyManager.setInitLevel2Property(
				Level2Property.BLUE_INIT_VALUE, monsterTemplate.getBlueInit());
		// 蓝魔加成值
		monsterPropertyManager.setInitLevel2Property(
				Level2Property.BLUE_ELIMINATE_BONUS,
				monsterTemplate.getBlueEliminateBonus());

		// 绿魔最大值
		monsterPropertyManager.setInitLevel2Property(Level2Property.GREEN_MAX,
				monsterTemplate.getGreenMax());
		// 绿魔初始值
		monsterPropertyManager
				.setInitLevel2Property(Level2Property.GREEN_INIT_VALUE,
						monsterTemplate.getGreenInit());
		// 绿魔加成值
		monsterPropertyManager.setInitLevel2Property(
				Level2Property.GREEN_ELIMINATE_BONUS,
				monsterTemplate.getGreenEliminateBonus());

		// 紫魔最大值
		monsterPropertyManager.setInitLevel2Property(Level2Property.PURPLE_MAX,
				monsterTemplate.getPurpleMax());
		// 紫魔初始值
		monsterPropertyManager.setInitLevel2Property(
				Level2Property.PURPLE_INIT_VALUE,
				monsterTemplate.getPurpleInit());
		// 紫魔加成值
		monsterPropertyManager.setInitLevel2Property(
				Level2Property.PURPLE_ELIMINATE_BONUS,
				monsterTemplate.getPurpleEliminateBonus());
		// 先攻
		monsterPropertyManager.setInitLevel2Property(
				Level2Property.FIRST_ATTACK, monsterTemplate.getFirstAttack());

		// 物抗
		monsterPropertyManager.setInitLevel2Property(
				Level2Property.PHYSICAL_RESISTANCE,
				monsterTemplate.getPhysicalResistance());
		// 魔抗
		monsterPropertyManager.setInitLevel2Property(
				Level2Property.MAGIC_RESISTANCE,
				monsterTemplate.getMagicResistance());
		// 黑宝石攻击加成值
		monsterPropertyManager.setInitLevel2Property(
				Level2Property.BLACK_GEM_ATTACK_PER_ADD_RATE, GameServerAssist
						.getGameConstants().getBlackGemAttackPerAddRate());
		// 白宝石恢复能量上限百分比
		monsterPropertyManager.setInitLevel2Property(
				Level2Property.WHITE_GEM_RECOVER_ENERGY_RATE, GameServerAssist
						.getGameConstants().getWhiteGemRecoverEnergyRate());
	}

}
