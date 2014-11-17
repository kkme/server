package com.hifun.soul.gameserver.skill;

import java.util.List;

import com.hifun.soul.common.constants.Loggers;
import com.hifun.soul.common.constants.SharedConstants;
import com.hifun.soul.gameserver.battle.Battle;
import com.hifun.soul.gameserver.battle.BattleCalculator;
import com.hifun.soul.gameserver.battle.IBattleUnit;
import com.hifun.soul.gameserver.battle.context.IBattleContext;
import com.hifun.soul.gameserver.battle.counter.AbstractRoudListener;
import com.hifun.soul.gameserver.battle.gem.EnergyType;
import com.hifun.soul.gameserver.battle.gem.GemPosition;
import com.hifun.soul.gameserver.battle.gem.filter.GemEraseFilterChain;
import com.hifun.soul.gameserver.battle.msg.ChessBoardSnap;
import com.hifun.soul.gameserver.item.template.ItemTemplate;
import com.hifun.soul.gameserver.skill.effect.AbstractDamageEffector;
import com.hifun.soul.gameserver.skill.effect.INoneAttackEffector;
import com.hifun.soul.gameserver.skill.effect.IPreProcessSecondEffector;
import com.hifun.soul.gameserver.skill.effect.ISkillEffector;
import com.hifun.soul.gameserver.skill.effect.SkillEffectType;
import com.hifun.soul.gameserver.skill.effect.manager.ISkillEffectManager;
import com.hifun.soul.gameserver.skill.effect.manager.SkillEffectManager;
import com.hifun.soul.gameserver.skill.msg.GCBattleMagicChanged;
import com.hifun.soul.gameserver.skill.msg.GCEffectDirect;
import com.hifun.soul.gameserver.skill.msg.GCEffectMiss;
import com.hifun.soul.gameserver.skill.msg.GCUpdateSkillState;
import com.hifun.soul.gameserver.skill.msg.GCUseSkill;
import com.hifun.soul.gameserver.skill.template.SkillEffectTemplate;
import com.hifun.soul.gameserver.skill.template.SkillInfo;
import com.hifun.soul.gameserver.skill.template.SkillScrollTemplate;
import com.hifun.soul.gameserver.skill.template.SkillTemplate;

/**
 * 技能BO;
 * 
 * @author crazyjohn
 * 
 */
public class SkillInstance extends AbstractRoudListener implements ISkill {
	private static final int ZERO = 0;
	private static final int CURRENT_ACTION_OVER = 1;
	private SkillTemplate template;
	private SkillScrollTemplate scrollTemplate;
	private ISkillEffectManager effectorManager;
	/** 距离上次使用此技能过了多少个回合 */
	private int afterLastUseRound;
	private IBattleUnit owner;
	/** 技能栏索引 */
	private int slotIndex = SharedConstants.INVALID_SKILL_SLOT_INDEX;
	/** 默认的技能状态 */
	private int skillState = SkillStateType.NOT_STUDY.getIndex();
	/** 宝石消除过滤器 */
	protected GemEraseFilterChain filterChain = new GemEraseFilterChain();

	public int getSlotIndex() {
		return slotIndex;
	}

	public void setSlotIndex(int slotIndex) {
		this.slotIndex = slotIndex;
	}

	public SkillInstance(SkillTemplate template, IBattleUnit owner) {
		this.template = template;
		this.scrollTemplate = template.getScrollTemplate();
		effectorManager = new SkillEffectManager();
		this.owner = owner;
		// 初始化等待回合数; 保证技能可以使用
		this.afterLastUseRound = template.getCooldownRound();
	}

	@Override
	public boolean canUseSkill() {
		return this.isCooldown();
	}

	@Override
	public SkillTemplate getSkillTemplate() {
		return template;
	}

	@Override
	public int getSkillId() {
		return template.getId();
	}

	@Override
	public String getSkillName() {
		return template.getSkillName();
	}

	@Override
	public boolean useSkill(IBattleUnit attacker, IBattleUnit target,
			int combo, List<ChessBoardSnap> snaps, int selectedRow,
			int selectedCol) {
		// editby: crazyjohn 跟策划商定第一效果可以是任何效果;但是第二效果不可以是伤害类效果;
		// 技能是否可用
		if (!this.canUseSkill()) {
			return false;
		}
		if (!isEnergyEnough(attacker)) {
			return false;
		}
		// 通知客户端使用技能
		noticeClientUseSkill(attacker, target);
		// 置零
		addRoundListener(attacker.getBattleContext().getBattle());
		// 如果是普通技能
		if (this.getSkillTemplate().getSkillType() != SkillType.OTHER
				.getIndex()) {
			// 触发过滤器
			this.filterChain.doFilter(snaps, attacker);
		}
		// 使用技能逻辑
		int type = this.template.getEffects().get(0).getEffectId();
		SkillEffectType effectType = SkillEffectType.typeOf(type);
		if (effectType == null) {
			return false;
		}
		// 发送技能效果消息
		GCEffectDirect directMsg = new GCEffectDirect();
		directMsg.setAttackerId(attacker.getUnitGuid());
		directMsg.setTargetId(target.getUnitGuid());
		directMsg.setCombo(combo);
		directMsg.setSkillId(this.getSkillId());
		// 技能第一效果
		ISkillEffector effector = effectorManager.getSkillEffector(effectType);
		if (effector == null) {
			return false;
		}
		// 首先判断第一效果是否伤害效果, 如果伤害效果, 是否命中;
		boolean isFirstEffectHit = true;
		// 是否是伤害类效果;
		if (effector instanceof AbstractDamageEffector) {
			// 1. 是否命中
			if (!BattleCalculator.hit(attacker, target)) {
				// 设置未命中标记位
				isFirstEffectHit = false;
			}
		}
		// 第一效果是否命中;
		EffectResult firstResult = null;
		if (isFirstEffectHit) {
			// 计算第一效果
			firstResult = effector.effect(attacker, target, this, this.template
					.getEffects().get(0).formParams(), combo, snaps,
					GemPosition.createPosition(selectedRow, selectedCol));
			directMsg.setParry(firstResult.isParry());
			directMsg.setCrit(firstResult.isCrit());
		}

		// 技能第二效果, 肯定是非攻击技能效果(buff + 控制); 而且可以为空
		int buffType = this.template.getEffects().get(1).getEffectId();
		SkillEffectType buffEffectType = SkillEffectType.typeOf(buffType);
		if (buffEffectType == null
				|| buffEffectType == SkillEffectType.NORMAL_ATTACK) {
			// 第二效果为空的时候不认为技能解释失败;
			afterEffectOver(isFirstEffectHit, directMsg, attacker, target,
					snaps);
			return true;
		}
		// 技能第二效果
		ISkillEffector secondEffector = effectorManager
				.getSkillEffector(buffEffectType);
		// 如果第一效果被闪避了, 判断第二效果是否会被闪避
		if (!isFirstEffectHit) {
			// 如果可以被闪避就直接跳出
			if (SecondEffectDodgeType.typeOf(this.getSkillTemplate()
					.getSecondEffectDodgeType()) == SecondEffectDodgeType.YES) {
				afterEffectOver(isFirstEffectHit, directMsg, attacker, target,
						snaps);
				return true;
			}
		}
		// 检查是否为非攻击技能效果(buff + 控制)
		INoneAttackEffector realBuffEffector = null;
		if (secondEffector instanceof INoneAttackEffector) {
			realBuffEffector = (INoneAttackEffector) secondEffector;
			// 进行预处理类型的第二效果的处理
			if (realBuffEffector instanceof IPreProcessSecondEffector) {
				IPreProcessSecondEffector preProcessSecondEffector = (IPreProcessSecondEffector) realBuffEffector;
				preProcessSecondEffector.setFirstEffectResult(firstResult);
			}
		} else {
			Loggers.Battle_Logger
					.error(String
							.format("The skill id: %d, sencond effect is not a IControlableEffector type.",
									this.getSkillId()));
			return false;
		}
		realBuffEffector.effect(attacker, target, this, this.template
				.getEffects().get(1).formParams(), combo, snaps,
				GemPosition.createPosition(selectedRow, selectedCol));

		// 技能效果解释完毕以后的处理;
		afterEffectOver(isFirstEffectHit, directMsg, attacker, target, snaps);
		return true;
	}

	/**
	 * 技能效果及时完毕以后的处理;
	 * 
	 * @param isFirstEffectHit
	 * @param directMsg
	 * @param attacker
	 * @param target
	 * @param snaps
	 */
	private void afterEffectOver(boolean isFirstEffectHit,
			GCEffectDirect directMsg, IBattleUnit attacker, IBattleUnit target,
			List<ChessBoardSnap> snaps) {
		// 扣除技能消耗的能量
		costMagic(attacker, target);
		// 最后广播技能效果;如果第一效果命中则播命中消息;否则播未命中消息;
		if (isFirstEffectHit) {
			attacker.getBattleContext().getBattle()
					.broadcastToBattleUnits(directMsg);
		} else {
			// 广播未命中
			GCEffectMiss missMsg = new GCEffectMiss();
			missMsg.setAttackerId(attacker.getUnitGuid());
			missMsg.setTargetId(target.getUnitGuid());
			missMsg.setSkillId(this.getSkillId());
			// 广播miss
			attacker.getBattleContext().getBattle()
					.broadcastToBattleUnits(missMsg);
			// 发送魔法变化
			GCBattleMagicChanged magicChangeMsg = new GCBattleMagicChanged();
			magicChangeMsg.setTargetId(attacker.getUnitGuid());
			magicChangeMsg.setTargetMagicChange(attacker
					.updateMagicSlots(snaps));
			// 广播魔法变化
			attacker.getBattleContext().getBattle()
					.broadcastToBattleUnits(magicChangeMsg);
		}

	}

	/**
	 * 施法者是否有足够的魔法去释放技能;
	 * 
	 * @param attacker
	 * @return
	 */
	private boolean isEnergyEnough(IBattleUnit attacker) {
		return attacker.hasEnoughMagicToUseSuchSkill(this);
	}

	/**
	 * 添加回合的事件监听;
	 * 
	 * @param battle
	 */
	private void addRoundListener(Battle battle) {
		if (this.template.getCooldownRound() > ZERO
				&& this.template.getSkillType() == SkillType.OTHER.getIndex()) {
			// 重置
			this.afterLastUseRound = ZERO;
			battle.addRoundListener(this);
		}
	}

	/**
	 * 扣除技能消耗的魔法值;
	 * 
	 * @param attacker
	 *            攻击者;
	 * @param target
	 *            受击者;
	 */
	private void costMagic(IBattleUnit attacker, IBattleUnit target) {
		attacker.reduceMagicEnergy(EnergyType.RED, this.template.getRedCost());
		attacker.reduceMagicEnergy(EnergyType.YELLOW,
				this.template.getYellowCost());
		attacker.reduceMagicEnergy(EnergyType.BLUE, this.template.getBlueCost());
		attacker.reduceMagicEnergy(EnergyType.GREEN,
				this.template.getGreenCost());
		attacker.reduceMagicEnergy(EnergyType.PURPLE,
				this.template.getPurpleCost());

	}

	private void noticeClientUseSkill(IBattleUnit attacker, IBattleUnit target) {
		GCUseSkill useSkillMsg = new GCUseSkill();
		useSkillMsg.setAttackerId(attacker.getUnitGuid());
		useSkillMsg.setTargetId(target.getUnitGuid());
		useSkillMsg.setSkillId(this.getSkillId());
		IBattleContext context = target.getBattleContext();
		if (context == null) {
			return;
		}
		Battle battle = context.getBattle();
		if (battle == null) {
			return;
		}
		// editby: crazyjohn 如果是普通技能的话不发送GCUseSkill消息; 2012-10-06和dick订的此协议;
		if (SkillType.typeOf(this.template.getSkillType()) == SkillType.OTHER) {
			battle.broadcastToBattleUnits(useSkillMsg);
		}
	}

	@Override
	public AttackType getAttackType() {
		return AttackType.typeOf(this.template.getAttackType());
	}

	@Override
	public SkillInfo toSkillInfo() {
		SkillInfo info = new SkillInfo();
		info.setSkillId(this.getSkillId());
		info.setSkillName(this.getSkillName());
		info.setAttackType(this.getAttackType().getIndex());
		info.setBeHitedEffectId(this.template.getBeHitedEffectId());
		info.setBlueCost(this.template.getBlueCost());
		info.setCooldownRound(this.template.getCooldownRound());
		info.setFlyEffectId(this.template.getFlyEffectId());
		info.setGreenCost(this.template.getGreenCost());
		info.setPurpleCost(this.template.getPurpleCost());
		info.setRedCost(this.template.getRedCost());
		info.setSkillActionId(this.template.getSkillActionId());
		info.setSkillDesc(this.template.getSkillDesc());
		info.setYellowCost(this.template.getYellowCost());
		info.setSkillType(this.template.getSkillType());
		info.setNeedSelectGem(this.needSelectedGem());
		info.setSkillSound(this.template.getSkillSound());
		// 如果需要点选宝石的话,给传递下点选范围类型;
		if (this.needSelectedGem()) {
			for (SkillEffectTemplate effect : this.template.getEffects()) {
				if (effect.getEffectId() == SkillEffectType.ERASE_ASSIGN_POSITION_RANGE_GEMS
						.getIndex()
						|| effect.getEffectId() == SkillEffectType.ERASE_ASSIGN_POSITION_RANGE_GEMS_AND_DAMAGE
								.getIndex()) {
					info.setRangeType(effect.getParam2());
					break;
				}
			}

		}
		info.setSkillIcon(this.template.getSkillIcon());
		info.setSlotIndex(this.slotIndex);
		info.setEnemyBeHitedEffectId(this.template.getEnemyBeHitedEffectId());
		info.setSkillDevelopType(scrollTemplate == null ? SharedConstants.SKILL_DEVELOP_INIT
				: scrollTemplate.getSkillDevelopType());
		info.setSkillState(getSkillState());
		info.setNeedLevel(this.template.getOpenLevel());
		info.setNeedSkillPoints(scrollTemplate == null ? 0 : scrollTemplate
				.getNeedSkillPoint());
		info.setIsNeedSkillScroll(scrollTemplate == null ? false
				: scrollTemplate.getNeedSkillScrollId() <= 0 ? false : true);
		if (scrollTemplate != null && scrollTemplate.getNeedSkillScrollId() > 0) {
			ItemTemplate itemTemplate = this.template.getItemTemplate();
			if (itemTemplate != null) {
				info.setSkillScrollName(itemTemplate.getName());
			}
		}
		// 设置前置技能名称
		if (scrollTemplate != null && scrollTemplate.getPreSkillId() > 0) {
			SkillTemplate preSkillTemplate = this.template.getPreSkillTemplate();
			if (preSkillTemplate != null) {
				info.setPreSkillName(preSkillTemplate.getSkillName());
			}
		}

		return info;
	}

	@Override
	public boolean needSelectedGem() {
		// TODO: crazyjohn 编写枚举
		return this.template.getNeedSelectedGem() != 0;
	}

	@Override
	public boolean currentActionOver() {
		// TODO: crazyjohn 编写枚举
		return this.template.getUseRoundOver() == CURRENT_ACTION_OVER;
	}

	@Override
	public boolean isCooldown() {
		// 不是other技能, 直接返回true;
		if (this.getSkillTemplate().getSkillType() != SkillType.OTHER
				.getIndex()) {
			return true;
		}
		return this.template.getCooldownRound() <= afterLastUseRound;
	}

	@Override
	public void onRoundFinished() {
		this.afterLastUseRound++;
		// 如果可以冷却了
		if (isCooldown()) {
			IBattleContext context = owner.getBattleContext();
			if (context == null) {
				return;
			}
			Battle battle = context.getBattle();
			if (battle == null) {
				return;
			}
			// 移除事件监听
			battle.removeRoundListener(this);
			GCUpdateSkillState skillStateMsg = new GCUpdateSkillState();
			skillStateMsg.setOwnerId(this.owner.getUnitGuid());
			skillStateMsg.setIsCooldown(true);
			battle.broadcastToBattleUnits(skillStateMsg);
		}
	}

	@Override
	public List<Integer> getTriggerBuffResourceList() {
		return this.template.getTriggerBuffList();
	}

	@Override
	public ISkill copy() {
		return new SkillInstance(template, owner);
	}

	@Override
	public int getSkillState() {
		return skillState;
	}

	@Override
	public void setSkillState(int skillState) {
		this.skillState = skillState;
	}

	@Override
	public SkillScrollTemplate getSkillScrollTemplate() {
		return scrollTemplate;
	}

	@Override
	public int getCooldownRound() {
		return this.template.getCooldownRound() - this.afterLastUseRound;
	}
}
