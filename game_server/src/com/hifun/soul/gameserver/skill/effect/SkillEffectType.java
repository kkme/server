package com.hifun.soul.gameserver.skill.effect;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hifun.soul.core.enums.IndexedEnum;
import com.hifun.soul.gameserver.skill.effect.select.EraseAssignPosGemsAndDamageEffector;
import com.hifun.soul.gameserver.skill.effect.select.EraseAssignPositionGemsEffector;

/**
 * 技能效果类型;
 * 
 * @author crazyjohn
 * 
 */
public enum SkillEffectType implements IndexedEnum {
	/** 普通攻击效果 */
	NORMAL_ATTACK(0, new NormalAttackEffector()) {
		@Override
		public boolean isNoDamageEffect() {
			return false;
		}
	},
	/** 消除(随机/指定)位置范围内的宝石 */
	ERASE_ASSIGN_POSITION_RANGE_GEMS(1, new EraseAssignPositionGemsEffector()),
	/** 消除(随机/指定)位置范围内的宝石,并造成伤害 */
	ERASE_ASSIGN_POSITION_RANGE_GEMS_AND_DAMAGE(2,
			new EraseAssignPosGemsAndDamageEffector()) {
		@Override
		public boolean isNoDamageEffect() {
			return false;
		}
	},
	/** 消除棋盘中所有指定颜色的宝石 */
	ERASE_ALL_ASSIGN_COLOR_GEMS(3, new EraseAllGemsEffector()),
	/** 消除棋盘中所有指定颜色的宝石,并造成伤害 */
	ERASE_ALL_ASSIGN_COLOR_GEMS_AND_DAMAGE(4,
			new EraseAllGemsAndDamageEffector()),
	/** 魔法恢复值 */
	MAGIC_RECOVER_VALUE(5, new MagicRecoverValueEffector()),
	/** 魔法恢复比率 */
	MAGIC_RECOVER_RATE(6, new MagicRecoverRateEffector()),
	/** 魔法燃烧值 */
	MAGIC_BURN_VALUE(7, new MagicBurnValueEffector()),
	/** 魔法燃烧比率 */
	MAGIC_BURN_RATE(8, new MagicBurnRateEffector()),
	/** 魔法吸收值 */
	MAGIC_ABSORB_VALUE(9, new MagicAbsorbValueEffector()),
	/** 魔法吸收比率 */
	MAIGC_ABSORB_RATE(10, new MagicAbsorbRateEffector()),
	/** 魔法伤害值,额外加成看自己的魔法槽 */
	MAGIC_DAMAGE_VALUE(11, new MagicDamageValueEffector()) {
		@Override
		public boolean isNoDamageEffect() {
			return false;
		}
	},
	/** 魔法伤害比率,额外加成看自己的魔法槽 */
	MAGIC_DAMAGE_RATE(12, new MagicDamageRateEffector()) {
		@Override
		public boolean isNoDamageEffect() {
			return false;
		}
	},
	/** 宝石换色 */
	CHANGE_GEM_COLOR(13, new ChangeGemColorEffector()),
	/** 治疗 */
	HEAL(14, new HealEffector()),
	/** 一定几率的给buff */
	GIVE_BUFF(15, new GiveBuffRateEffector()),
	/** 一定几率的给buff并且加成 */
	GIVE_BUFF_ADDED(16, new GiveBuffRateAndAddedEffector()),
	/** 一定几率给敌方属性加成的buff */
	GIVE_TARGET_AMEND_BUFF(17, new GiveTargetAmendPropertyBuffEffector()),
	/** 一定几率自己属性加成的buff */
	GIVE_SELF_AMEND_BUFF(18, new GiveSelfAmendPropertyBuffEffector()),
	/** 魔法伤害值, 额外加成看棋盘, 而不是自己的魔法槽 */
	MAGIC_DAMAGE_VALUE_CHESSBOARD(19, new MagicDamageValueChessBoardEffector()) {
		@Override
		public boolean isNoDamageEffect() {
			return false;
		}
	},
	/** 随机生成指定数量的某种颜色宝石 */
	RANDOM_GENERATE_GEMS(20, new RandomGenerateGemsEffector()),
	/** 换色并且加hp */
	CHANGE_GEM_COLOR_ADD_HP(21, new ChangeGemColorAddHpEffector()),
	/** 换色并且伤害 */
	CHANGE_GEM_COLOR_ADD_DAMAGE(22, new ChangeGemColorAddDamageEffector()) {
		@Override
		public boolean isNoDamageEffect() {
			return false;
		}
	},
	/** 消耗魔法上buff */
	COST_MAGIC_ADD_BUFF(23, new CostMagicAddBuffEffector()),
	/** 消除所有指定颜色并且上buff */
	ERASE_ALL_ASSIGN_COLOR_ADD_BUFF(24, new EraseAllGemsAddBuffEffector()),
	/** 魔法伤害加成(加成取决于对方能量) */
	MAGIC_DAMAGE_RATE_DEPENDS_ENEMY(25,
			new MagicDamageRateDedpendsEnemyEffector()) {
		@Override
		public boolean isNoDamageEffect() {
			return false;
		}
	},
	/** 棋盘重置 */
	CHESSBOARD_RESET(26, new ChessBoardResetEffector()),
	/** 中毒buff(根据上次伤害值进行计算) */
	POISON_BUFF_BY_LAST_DAMAGE(27, new PoisonBuffEffector()),
	/** 治疗buff(根据当前指定的魔法值) */
	HEAL_BUFF_BY_CURRENT_ENERGY(28, new HealBuffByCurrentEnergy()),
	/** 清除所有buff */
	CLEAR_ALL_BUFF(29, new ClearAllBuffEffector()),
	/** 治疗(根据HP最大值进行计算) */
	HEAL_BY_MAX_HP(30, new HealByMaxHpEffector()), 
	/** 魔法伤害比率, 额外加成看棋盘, 而不是自己的魔法槽 */
	MAGIC_DAMAGE_RATE_CHESSBOARD(31, new MagicDamageRateChessBoardEffector()) {
		@Override
		public boolean isNoDamageEffect() {
			return false;
		}
	},;

	private int type;
	private ISkillEffector effector;
	private static Map<Integer, SkillEffectType> types = new HashMap<Integer, SkillEffectType>();
	private static List<SkillEffectType> secondEffectTypes = new ArrayList<SkillEffectType>();

	static {
		for (SkillEffectType effect : SkillEffectType.values()) {
			types.put(effect.getIndex(), effect);
			if (effect.isNoDamageEffect()) {
				secondEffectTypes.add(effect);
			}
		}
	}

	SkillEffectType(int type, ISkillEffector effector) {
		this.type = type;
		this.effector = effector;
	}

	public static SkillEffectType typeOf(int type) {
		return types.get(type);
	}

	@Override
	public int getIndex() {
		return type;
	}

	/**
	 * 创建相关的效果;
	 * 
	 * @return
	 */
	public ISkillEffector getRelatedEffector() {
		return this.effector;
	}

	public boolean isNoDamageEffect() {
		return true;
	}

	/**
	 * 是否是会产生buff效果的类型;
	 * 
	 * @return
	 */
	public boolean isBuffEffector() {
		return this.effector instanceof IBuffEffEctor;
	}

	public static List<SkillEffectType> getSecondEffectTypes() {
		return secondEffectTypes;
	}
}
