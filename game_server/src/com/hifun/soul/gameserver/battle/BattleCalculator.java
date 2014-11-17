package com.hifun.soul.gameserver.battle;

import com.hifun.soul.common.constants.SharedConstants;
import com.hifun.soul.gameserver.battle.template.ComboTemplate;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.role.Occupation;
import com.hifun.soul.gameserver.role.Role;
import com.hifun.soul.gameserver.role.properties.HumanIntProperty;
import com.hifun.soul.gameserver.role.properties.Level2Property;
import com.hifun.soul.gameserver.role.properties.PropertyType;
import com.hifun.soul.gameserver.skill.EffectResult;

/**
 * 战斗计算器;
 * 
 * @author crazyjohn
 * 
 */
public class BattleCalculator {

	/**
	 * 为Mock战斗写的方法, 比如boss战的充能打击;基本和下面的代码重复, 但是在敏感时期, 避免重构引发战斗bug;<br>
	 * editby: crazyjohn 2013-04-09;
	 * 
	 * @param useSkillAttack
	 * @param attacker
	 * @param target
	 * @param skillDamageFactor
	 * @param skillAddedDamage
	 * @return
	 */
	public static EffectResult calculateSkillFinalDamageForMockBattle(
			boolean useSkillAttack, IBattleUnit attacker, IBattleUnit target,
			float skillDamageFactor, int skillAddedDamage) {
		/*
		 * 策划战斗计算例子： 举例 LV25战士技能攻击LV20法师 等级差加成=（25-20）*0.1%=0.5% 假设命中 假设暴击 假设格挡
		 * 假设战士宝石攻击力=800
		 * ，技能攻击力750，技能额外伤害系数20%，额外伤害值20点；法师宝石防御400，技能防御450，法师物理抗性20%
		 * 技能攻击伤害=1*（800+750*（1+20%））*（1-（400+450）/（400+450+20*20*2））=824.24
		 * 物理技能伤害=824.2424*（1-20%）=659.39 最终技能伤害=（659.39+20）*1.5/1.5=679
		 */

		EffectResult result = new EffectResult();
		// 根据策划规则，计算最终的伤害值
		// 1. 计算等级差
		float levelDiffer = getLevelDifferForMockBattle(attacker, target,
				GameServerAssist.getGameConstants().getLevelDifferFactor());

		// 1.1. 等级差极值判断
		if (levelDiffer > GameServerAssist.getGameConstants().getMaxLevelDiffer()) {
			levelDiffer = GameServerAssist.getGameConstants().getMaxLevelDiffer();
		}
		if (levelDiffer < GameServerAssist.getGameConstants().getMinLevelDiffer()) {
			levelDiffer = GameServerAssist.getGameConstants().getMinLevelDiffer();

		}

		// 2. 暴击判断
		float critRate = (attacker.getPropertyManager()
				.getIntPropertySet(PropertyType.LEVEL2_PROPERTY)
				.getPropertyValue(Level2Property.CRITICAL) - target
				.getPropertyManager()
				.getIntPropertySet(PropertyType.LEVEL2_PROPERTY)
				.getPropertyValue(Level2Property.UNCRITICAL))
				/ SharedConstants.PROPERTY_DIVISOR + levelDiffer;
		if (critRate < 0) {
			critRate = 0;
		}
		if (critRate > 1) {
			critRate = 1;
		}
		float critDamageFactor = 1.0f;
		boolean crit = diceRate(critRate);
		if (crit) {
			critDamageFactor = GameServerAssist.getGameConstants().getBaseCritFactor()
					+ (attacker.getPropertyManager()
							.getIntPropertySet(PropertyType.LEVEL2_PROPERTY)
							.getPropertyValue(Level2Property.CRITICAL_DAMAGE) - target
							.getPropertyManager()
							.getIntPropertySet(PropertyType.LEVEL2_PROPERTY)
							.getPropertyValue(Level2Property.UNCRITICAL_DAMAGE))
					/ SharedConstants.PROPERTY_DIVISOR + levelDiffer;
			if (critDamageFactor < GameServerAssist.getGameConstants().getBaseCritFactor()) {
				critDamageFactor = GameServerAssist.getGameConstants().getBaseCritFactor();
			}
		}
		// 3. 格挡判断
		float parryRate = (target.getPropertyManager()
				.getIntPropertySet(PropertyType.LEVEL2_PROPERTY)
				.getPropertyValue(Level2Property.PARRY) - attacker
				.getPropertyManager()
				.getIntPropertySet(PropertyType.LEVEL2_PROPERTY)
				.getPropertyValue(Level2Property.UNPARRY))
				/ SharedConstants.PROPERTY_DIVISOR + levelDiffer;
		if (parryRate < 0) {
			parryRate = 0;
		}
		if (parryRate > 1) {
			parryRate = 1;
		}
		float parryDamageFactor = 1.0f;
		boolean parry = diceRate(parryRate);
		if (parry) {
			parryDamageFactor = GameServerAssist.getGameConstants().getBaseParryFactor()
					+ (target.getPropertyManager()
							.getIntPropertySet(PropertyType.LEVEL2_PROPERTY)
							.getPropertyValue(Level2Property.PARRY_DAMAGE) - attacker
							.getPropertyManager()
							.getIntPropertySet(PropertyType.LEVEL2_PROPERTY)
							.getPropertyValue(Level2Property.UNPARRY_DAMAGE))
					/ SharedConstants.PROPERTY_DIVISOR + levelDiffer;
			if (parryDamageFactor < GameServerAssist.getGameConstants().getBaseParryFactor()) {
				parryDamageFactor = GameServerAssist.getGameConstants().getBaseParryFactor();
			}
		}
		// 计算攻击者攻击力
		float attack = 0;
		if (useSkillAttack) {
			// 技能攻击
			attack = attacker.getPropertyManager()
					.getIntPropertySet(PropertyType.LEVEL2_PROPERTY)
					.getPropertyValue(Level2Property.ATTACK);
		} else {
			// 物理攻击
			attack = attacker.getPropertyManager()
					.getIntPropertySet(PropertyType.LEVEL2_PROPERTY)
					.getPropertyValue(Level2Property.GEM_ATTACK);
		}
		// 计算被攻击者防御力
		float defense = 0;
		if (useSkillAttack) {
			// 技能防御
			defense = target.getPropertyManager()
					.getIntPropertySet(PropertyType.LEVEL2_PROPERTY)
					.getPropertyValue(Level2Property.DEFENSE);
		} else {
			// 物理防御
			defense = target.getPropertyManager()
					.getIntPropertySet(PropertyType.LEVEL2_PROPERTY)
					.getPropertyValue(Level2Property.GEM_DEFENSE);
		}

		// 3. 计算基础伤害.宝石伤害和技能伤害的公式分开
		float baseDamage = 0;
		// 倍数系数
		int rate = 2;
		if (useSkillAttack) {
			// 物理攻击
			float gemAttack = attacker.getPropertyManager()
					.getIntPropertySet(PropertyType.LEVEL2_PROPERTY)
					.getPropertyValue(Level2Property.GEM_ATTACK);
			// 物理防御
			float gemDefense = target.getPropertyManager()
					.getIntPropertySet(PropertyType.LEVEL2_PROPERTY)
					.getPropertyValue(Level2Property.GEM_DEFENSE);
			// 技能伤害
			baseDamage = GameServerAssist.getGameConstants().getAttackFactor()
					* (gemAttack + attack * (1 + skillDamageFactor))
					* (1 - ((gemDefense + defense) / ((gemDefense + defense) + target
							.getPropertyManager()
							.getIntPropertySet(PropertyType.HUMAN_INT_PROPERTY)
							.getPropertyValue(HumanIntProperty.LEVEL)
							* GameServerAssist.getGameConstants().getDefenseFactor() * rate)));
		} else {
			// 宝石伤害
			baseDamage = GameServerAssist.getGameConstants().getAttackFactor()
					* attack
					* (1 - (defense / (defense + target.getPropertyManager()
							.getIntPropertySet(PropertyType.HUMAN_INT_PROPERTY)
							.getPropertyValue(HumanIntProperty.LEVEL)
							* GameServerAssist.getGameConstants().getDefenseFactor())));
		}
		// 3. 计算最终伤害(抗性计算)
		float finalDamage = 0;
		// 抗性
		float resistance = 0;
		if (attacker.getOccupation() == Occupation.MAGICIAN) {
			// 法师使用法抗
			resistance = target.getPropertyManager()
					.getIntPropertySet(PropertyType.LEVEL2_PROPERTY)
					.getPropertyValue(Level2Property.MAGIC_RESISTANCE)
					/ SharedConstants.PROPERTY_DIVISOR;
		} else {
			// 其它两个职业使用物抗
			resistance = target.getPropertyManager()
					.getIntPropertySet(PropertyType.LEVEL2_PROPERTY)
					.getPropertyValue(Level2Property.PHYSICAL_RESISTANCE)
					/ SharedConstants.PROPERTY_DIVISOR;
		}
		// 进行抗性溢出判断
		if (resistance > 1) {
			resistance = 1;
		}
		// 抗性计算
		finalDamage = baseDamage * (1 - resistance);
		// 4. 计算最终伤害(添加技能额外伤害)
		finalDamage = (finalDamage + skillAddedDamage) * critDamageFactor
				/ parryDamageFactor;
		if (finalDamage <= 1) {
			finalDamage = 1;
		}
		// 设置结果
		result.setFinalDamage((int) finalDamage);
		result.setCrit(crit);
		result.setParry(parry);
		return result;
	}

	/**
	 * Mock Battle获取等级差的方法;
	 * 
	 * @param attacker
	 * @param target
	 * @param levelDifferFactor
	 * @return
	 */
	private static float getLevelDifferForMockBattle(IBattleUnit attacker,
			IBattleUnit target, int levelDifferFactor) {
		float result = ((attacker.getPropertyManager()
				.getIntPropertySet(PropertyType.HUMAN_INT_PROPERTY)
				.getPropertyValue(HumanIntProperty.LEVEL) - target
				.getPropertyManager()
				.getIntPropertySet(PropertyType.HUMAN_INT_PROPERTY)
				.getPropertyValue(HumanIntProperty.LEVEL)) * levelDifferFactor)
				/ SharedConstants.PROPERTY_DIVISOR;
		return result;
	}

	/**
	 * 计算最终伤害;
	 * 
	 * @param useSkillAttack
	 *            ; 是否使用技能攻击;
	 * 
	 * @param attacker
	 *            攻击者;
	 * @param target
	 *            目标;
	 * @param skillDamageFactor
	 *            技能伤害加成系数;
	 * @param skillAddedDamage
	 *            技能伤害加成值;
	 * @return
	 */
	public static EffectResult calculateSkillFinalDamage(
			boolean useSkillAttack, IBattleUnit attacker, IBattleUnit target,
			float skillDamageFactor, int skillAddedDamage) {
		/*
		 * 策划战斗计算例子： 举例 LV25战士技能攻击LV20法师 等级差加成=（25-20）*0.1%=0.5% 假设命中 假设暴击 假设格挡
		 * 假设战士宝石攻击力=800
		 * ，技能攻击力750，技能额外伤害系数20%，额外伤害值20点；法师宝石防御400，技能防御450，法师物理抗性20%
		 * 技能攻击伤害=1*（800+750*（1+20%））*（1-（400+450）/（400+450+20*20*2））=824.24
		 * 物理技能伤害=824.2424*（1-20%）=659.39 最终技能伤害=（659.39+20）*1.5/1.5=679
		 */

		EffectResult result = new EffectResult();
		// 根据策划规则，计算最终的伤害值
		// 1. 计算等级差
		float levelDiffer = getLevelDiffer(attacker, target,
				GameServerAssist.getGameConstants().getLevelDifferFactor());

		// 1.1. 等级差极值判断
		if (levelDiffer > GameServerAssist.getGameConstants().getMaxLevelDiffer()) {
			levelDiffer = GameServerAssist.getGameConstants().getMaxLevelDiffer();
		}
		if (levelDiffer < GameServerAssist.getGameConstants().getMinLevelDiffer()) {
			levelDiffer = GameServerAssist.getGameConstants().getMinLevelDiffer();

		}

		// 2. 暴击判断
		float critRate = (attacker.getBattleContext().getBattleProperty()
				.getBattleFinalPropertyByIndex(Level2Property.CRITICAL) - target
				.getBattleContext().getBattleProperty()
				.getBattleFinalPropertyByIndex(Level2Property.UNCRITICAL))
				/ SharedConstants.PROPERTY_DIVISOR + levelDiffer;
		if (critRate < 0) {
			critRate = 0;
		}
		if (critRate > 1) {
			critRate = 1;
		}
		float critDamageFactor = 1.0f;
		boolean crit = diceRate(critRate);
		if (crit) {
			critDamageFactor = GameServerAssist.getGameConstants().getBaseCritFactor()
					+ (attacker
							.getBattleContext()
							.getBattleProperty()
							.getBattleFinalPropertyByIndex(
									Level2Property.CRITICAL_DAMAGE) - target
							.getBattleContext()
							.getBattleProperty()
							.getBattleFinalPropertyByIndex(
									Level2Property.UNCRITICAL_DAMAGE))
					/ SharedConstants.PROPERTY_DIVISOR + levelDiffer;
			if (critDamageFactor < GameServerAssist.getGameConstants().getBaseCritFactor()) {
				critDamageFactor = GameServerAssist.getGameConstants().getBaseCritFactor();
			}
		}
		// 3. 格挡判断
		float parryRate = (target.getBattleContext().getBattleProperty()
				.getBattleFinalPropertyByIndex(Level2Property.PARRY) - attacker
				.getBattleContext().getBattleProperty()
				.getBattleFinalPropertyByIndex(Level2Property.UNPARRY))
				/ SharedConstants.PROPERTY_DIVISOR + levelDiffer;
		if (parryRate < 0) {
			parryRate = 0;
		}
		if (parryRate > 1) {
			parryRate = 1;
		}
		float parryDamageFactor = 1.0f;
		boolean parry = diceRate(parryRate);
		if (parry) {
			parryDamageFactor = GameServerAssist.getGameConstants().getBaseParryFactor()
					+ (target
							.getBattleContext()
							.getBattleProperty()
							.getBattleFinalPropertyByIndex(
									Level2Property.PARRY_DAMAGE) - attacker
							.getBattleContext()
							.getBattleProperty()
							.getBattleFinalPropertyByIndex(
									Level2Property.UNPARRY_DAMAGE))
					/ SharedConstants.PROPERTY_DIVISOR + levelDiffer;
			if (parryDamageFactor < GameServerAssist.getGameConstants().getBaseParryFactor()) {
				parryDamageFactor = GameServerAssist.getGameConstants().getBaseParryFactor();
			}
		}
		// 计算攻击者攻击力
		float attack = 0;
		if (useSkillAttack) {
			// 技能攻击
			attack = attacker.getBattleContext().getBattleProperty()
					.getBattleFinalPropertyByIndex(Level2Property.ATTACK);
		} else {
			// 物理攻击
			attack = attacker.getBattleContext().getBattleProperty()
					.getBattleFinalPropertyByIndex(Level2Property.GEM_ATTACK);
		}
		// 计算被攻击者防御力
		float defense = 0;
		if (useSkillAttack) {
			// 技能防御
			defense = target.getBattleContext().getBattleProperty()
					.getBattleFinalPropertyByIndex(Level2Property.DEFENSE);
		} else {
			// 物理防御
			defense = target.getBattleContext().getBattleProperty()
					.getBattleFinalPropertyByIndex(Level2Property.GEM_DEFENSE);
		}

		// 3. 计算基础伤害.宝石伤害和技能伤害的公式分开
		float baseDamage = 0;
		// 倍数系数
		int rate = 2;
		if (useSkillAttack) {
			// 物理攻击
			float gemAttack = attacker.getBattleContext().getBattleProperty()
					.getBattleFinalPropertyByIndex(Level2Property.GEM_ATTACK);
			// 物理防御
			float gemDefense = target.getBattleContext().getBattleProperty()
					.getBattleFinalPropertyByIndex(Level2Property.GEM_DEFENSE);
			// 技能伤害
			baseDamage = GameServerAssist.getGameConstants().getAttackFactor()
					* (gemAttack + attack * (1 + skillDamageFactor))
					* (1 - ((gemDefense + defense) / ((gemDefense + defense) + target
							.getBattleContext().getBattleProperty().getLevel()
							* GameServerAssist.getGameConstants().getDefenseFactor() * rate)));
		} else {
			// 宝石伤害
			baseDamage = GameServerAssist.getGameConstants().getAttackFactor()
					* attack
					* (1 - (defense / (defense + target.getBattleContext()
							.getBattleProperty().getLevel()
							* GameServerAssist.getGameConstants().getDefenseFactor())));
		}
		// 3. 计算最终伤害(抗性计算)
		float finalDamage = 0;
		// 抗性
		float resistance = 0;
		if (attacker.getOccupation() == Occupation.MAGICIAN) {
			// 法师使用法抗
			resistance = target
					.getBattleContext()
					.getBattleProperty()
					.getBattleFinalPropertyByIndex(
							Level2Property.MAGIC_RESISTANCE)
					/ SharedConstants.PROPERTY_DIVISOR;
		} else {
			// 其它两个职业使用物抗
			resistance = target
					.getBattleContext()
					.getBattleProperty()
					.getBattleFinalPropertyByIndex(
							Level2Property.PHYSICAL_RESISTANCE)
					/ SharedConstants.PROPERTY_DIVISOR;
		}
		// 进行抗性溢出判断
		if (resistance > 1) {
			resistance = 1;
		}
		// 抗性计算
		finalDamage = baseDamage
				* (1 - resistance + (attacker.getBattleContext()
						.getBattleProperty().getAttackAddedRate() / SharedConstants.PROPERTY_DIVISOR));
		// 4. 计算最终伤害(添加技能额外伤害)
		finalDamage = (finalDamage + skillAddedDamage) * critDamageFactor
				/ parryDamageFactor;
		if (finalDamage <= 1) {
			finalDamage = 1;
		}
		// 设置结果
		result.setFinalDamage((int) finalDamage);
		result.setCrit(crit);
		result.setParry(parry);
		return result;
	}

	/**
	 * 计算基础攻击最终伤害值;
	 * 
	 * @param attacker
	 * @param target
	 * @param skillDamageFactor
	 * @param skillAddedDamage
	 * @param combo
	 * @return
	 */
	public static EffectResult calculateNormalAttakFinalDamage(
			IBattleUnit attacker, IBattleUnit target, int skillDamageFactor,
			int skillAddedDamage, int combo) {
		// 计算连击伤害
		EffectResult result = calculateSkillFinalDamage(false, attacker,
				target, skillDamageFactor, skillAddedDamage);
		result.setFinalDamage(getComboDamage(result.getFinalDamage(), combo));
		return result;
	}

	protected static int getSuitPropValue(boolean isInRealBattle,
			int battlePropIndex, Role<?> battleRole) {
		if (isInRealBattle) {
			return battleRole.getBattleContext().getBattleProperty()
					.getBattleFinalPropertyByIndex(battlePropIndex);
		} else {
			return battleRole.getPropertyManager()
					.getIntPropertySet(PropertyType.LEVEL2_PROPERTY)
					.getPropertyValue(battlePropIndex);
		}
	}

	/**
	 * 获取combo伤害;
	 * 
	 * @param finalDamage
	 * @param combo
	 * @return
	 */
	private static int getComboDamage(int finalDamage, int combo) {
		return (int) Math
				.floor((finalDamage
						* (SharedConstants.PROPERTY_DIVISOR + getComboAddValue(
								combo).getDamageRate())
						/ SharedConstants.PROPERTY_DIVISOR + getComboAddValue(
						combo).getDamage()));
	}

	/**
	 * 获取连击伤害加成值;
	 * 
	 * @param combo
	 * @return
	 */
	private static ComboTemplate getComboAddValue(int combo) {
		return GameServerAssist.getComboTemplateManager().getComboDamage(combo);
	}

	/**
	 * 获取等级加成;
	 * 
	 * @param attacker
	 * @param target
	 * @return
	 */
	private static float getLevelDiffer(IBattleUnit attacker,
			IBattleUnit target, int levelDifferFactor) {
		float result = ((attacker.getBattleContext().getBattleProperty()
				.getLevel() - target.getBattleContext().getBattleProperty()
				.getLevel()) * levelDifferFactor)
				/ SharedConstants.PROPERTY_DIVISOR;
		return result;
	}

	/**
	 * 摇骰子;
	 * 
	 * @param rate
	 * @return
	 */
	public static boolean diceRate(double rate) {
		return Math.random() < rate;
	}

	/**
	 * 计算命中率;
	 * 
	 * @param attacker
	 * @param target
	 * @return true表示命中;
	 */
	public static boolean hit(IBattleUnit attacker, IBattleUnit target) {
		float hit = GameServerAssist.getGameConstants().getBaseHit()
				+ (attacker.getBattleContext().getBattleProperty()
						.getBattleFinalPropertyByIndex(Level2Property.HIT)
						- target.getBattleContext()
								.getBattleProperty()
								.getBattleFinalPropertyByIndex(
										Level2Property.DODGE) + getLevelDiffer(
							attacker, target, GameServerAssist.getGameConstants().getLevelDifferFactor()))
				/ SharedConstants.PROPERTY_DIVISOR;
		if (hit < 0) {
			hit = 0;
		}
		if (hit > 1) {
			hit = 1;
		}
		return diceRate(hit);
	}
}
