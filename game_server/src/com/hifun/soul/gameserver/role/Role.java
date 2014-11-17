package com.hifun.soul.gameserver.role;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hifun.soul.common.constants.SharedConstants;
import com.hifun.soul.core.msg.IMessage;
import com.hifun.soul.gameserver.battle.Battle;
import com.hifun.soul.gameserver.battle.IBattleUnit;
import com.hifun.soul.gameserver.battle.ai.BaseMonsterAI;
import com.hifun.soul.gameserver.battle.ai.IBattleAI;
import com.hifun.soul.gameserver.battle.context.BattleContext;
import com.hifun.soul.gameserver.battle.context.IBattleContext;
import com.hifun.soul.gameserver.battle.gem.EnergyType;
import com.hifun.soul.gameserver.battle.gem.GemPosition;
import com.hifun.soul.gameserver.battle.gem.MagicSlotInfo;
import com.hifun.soul.gameserver.battle.msg.ChessBoardSnap;
import com.hifun.soul.gameserver.battle.msg.GCStartActionTimer;
import com.hifun.soul.gameserver.battle.msg.GCStartBattleAction;
import com.hifun.soul.gameserver.battle.property.BattleProperty;
import com.hifun.soul.gameserver.common.obj.DynamicObject;
import com.hifun.soul.gameserver.role.properties.HumanIntProperty;
import com.hifun.soul.gameserver.role.properties.Level2Property;
import com.hifun.soul.gameserver.role.properties.PropertyType;
import com.hifun.soul.gameserver.role.properties.manager.RolePropertyManager;
import com.hifun.soul.gameserver.skill.ISkill;
import com.hifun.soul.gameserver.skill.buff.BuffType;
import com.hifun.soul.gameserver.skill.msg.MagicChange;

/**
 * 
 * 角色的抽象对象<br>
 * 从此处衍生出的类有: Human, Monster;
 * 
 * @author magicstone
 * @author crazyjohn
 * 
 */
public abstract class Role<M extends RolePropertyManager<?>> extends
		DynamicObject implements IBattleUnit {
	/** 魔法槽 */
	protected List<MagicSlotInfo> slots;
	protected M propertyManager;
	/** 战斗中行动是否完成 */
	protected boolean isCurrentActionFinished = false;
	/** 战斗上下文;保证可视性 */
	protected volatile IBattleContext context;
	/** 角色是否处于战斗状态 */
	protected boolean isInBattleState;
	/** 战斗AI */
	protected IBattleAI battleAI = new BaseMonsterAI(this);
	/** 默认是1,深林地图 */
	protected int bgId = 1;

	@Override
	public int getBattleBgId() {
		return bgId;
	}

	@Override
	public void setBattleBgId(int bgId) {
		this.bgId = bgId;
	}

	@Override
	public M getPropertyManager() {
		return this.propertyManager;
	}

	@Override
	public String getUnitName() {
		return this.getName();
	}

	@Override
	public int getLevel() {
		return this.propertyManager.getIntPropertySet(
				PropertyType.HUMAN_INT_PROPERTY).getPropertyValue(
				HumanIntProperty.LEVEL);
	}

	/**
	 * 构建魔法槽;
	 * 
	 * @return
	 */
	protected List<MagicSlotInfo> buildMagicSlots() {
		List<MagicSlotInfo> result = new ArrayList<MagicSlotInfo>();
		// 红
		MagicSlotInfo redSlot = new MagicSlotInfo();
		redSlot.setEnergyType(EnergyType.RED.getIndex());
		redSlot.setCapacity(this.propertyManager.getIntPropertySet(
				PropertyType.LEVEL2_PROPERTY).getPropertyValue(
				Level2Property.RED_MAX));
		redSlot.setCurrentSize(this.propertyManager.getIntPropertySet(
				PropertyType.LEVEL2_PROPERTY).getPropertyValue(
				Level2Property.RED_INIT_VALUE));
		redSlot.setAddValue(this.propertyManager.getIntPropertySet(
				PropertyType.LEVEL2_PROPERTY).getPropertyValue(
				Level2Property.RED_ELIMINATE_BONUS));
		// 黄
		MagicSlotInfo yellowSlot = new MagicSlotInfo();
		yellowSlot.setEnergyType(EnergyType.YELLOW.getIndex());
		yellowSlot.setCapacity(this.propertyManager.getIntPropertySet(
				PropertyType.LEVEL2_PROPERTY).getPropertyValue(
				Level2Property.YELLOW_MAX));
		yellowSlot.setCurrentSize(this.propertyManager.getIntPropertySet(
				PropertyType.LEVEL2_PROPERTY).getPropertyValue(
				Level2Property.YELLOW_INIT_VALUE));
		yellowSlot.setAddValue(this.propertyManager.getIntPropertySet(
				PropertyType.LEVEL2_PROPERTY).getPropertyValue(
				Level2Property.YELLOW_ELIMINATE_BONUS));
		// 蓝
		MagicSlotInfo blueSlot = new MagicSlotInfo();
		blueSlot.setEnergyType(EnergyType.BLUE.getIndex());
		blueSlot.setCapacity(this.propertyManager.getIntPropertySet(
				PropertyType.LEVEL2_PROPERTY).getPropertyValue(
				Level2Property.BLUE_MAX));
		blueSlot.setCurrentSize(this.propertyManager.getIntPropertySet(
				PropertyType.LEVEL2_PROPERTY).getPropertyValue(
				Level2Property.BLUE_INIT_VALUE));
		blueSlot.setAddValue(this.propertyManager.getIntPropertySet(
				PropertyType.LEVEL2_PROPERTY).getPropertyValue(
				Level2Property.BLUE_ELIMINATE_BONUS));
		// 绿
		MagicSlotInfo greenSlot = new MagicSlotInfo();
		greenSlot.setEnergyType(EnergyType.GREEN.getIndex());
		greenSlot.setCapacity(this.propertyManager.getIntPropertySet(
				PropertyType.LEVEL2_PROPERTY).getPropertyValue(
				Level2Property.GREEN_MAX));
		greenSlot.setCurrentSize(this.propertyManager.getIntPropertySet(
				PropertyType.LEVEL2_PROPERTY).getPropertyValue(
				Level2Property.GREEN_INIT_VALUE));
		greenSlot.setAddValue(this.propertyManager.getIntPropertySet(
				PropertyType.LEVEL2_PROPERTY).getPropertyValue(
				Level2Property.GREEN_ELIMINATE_BONUS));
		// 紫
		MagicSlotInfo purpleSlot = new MagicSlotInfo();
		purpleSlot.setEnergyType(EnergyType.PURPLE.getIndex());
		purpleSlot.setCapacity(this.propertyManager.getIntPropertySet(
				PropertyType.LEVEL2_PROPERTY).getPropertyValue(
				Level2Property.PURPLE_MAX));
		purpleSlot.setCurrentSize(this.propertyManager.getIntPropertySet(
				PropertyType.LEVEL2_PROPERTY).getPropertyValue(
				Level2Property.PURPLE_INIT_VALUE));
		purpleSlot.setAddValue(this.propertyManager.getIntPropertySet(
				PropertyType.LEVEL2_PROPERTY).getPropertyValue(
				Level2Property.PURPLE_ELIMINATE_BONUS));
		result.add(redSlot);
		result.add(yellowSlot);
		result.add(blueSlot);
		result.add(greenSlot);
		result.add(purpleSlot);
		return result;
	}

	@Override
	public List<MagicSlotInfo> getAllMagicSlots() {
		return slots;
	}

	@Override
	public boolean isCurrentActionFinished() {
		return isCurrentActionFinished;
	}

	@Override
	public void finishCurrentAction() {
		isCurrentActionFinished = true;
	}

	@Override
	public void resetFinishActionState() {
		this.isCurrentActionFinished = false;
	}

	@Override
	public boolean isDead() {
		if (this.getBattleContext() == null) {
			return false;
		}
		if (this.getBattleContext().getBattleProperty() == null) {
			return false;
		}
		return this.getBattleContext().getBattleProperty().getHp() <= 0;
	}

	@Override
	public IBattleContext buildBattleContext(Battle aBattle) {
		// 构建魔法槽
		this.slots = this.buildMagicSlots();
		// 构建战斗属性
		BattleProperty battleProperty = this.propertyManager
				.buildBattleProperty();
		context = new BattleContext(aBattle, this, battleProperty);
		// 构造技能
		context.buildBattleSkill(this.getCarriedSkills());
		return context;
	}

	@Override
	public IBattleContext getBattleContext() {
		return context;
	}

	@Override
	public void notifyAction() {
		// 不在战斗状态的话直接退出
		if (!this.isInBattleState()) {
			return;
		}
		// 战斗上下文;
		IBattleContext context = this.getBattleContext();
		if (context == null) {
			return;
		}
		Battle battle = context.getBattle();
		if (battle == null) {
			return;
		}
		// 通知新回合
		// battle.notifyNewRound();
		// 广播开始行动消息
		// 通知客户端可以开始行动了
		GCStartBattleAction startActionMsg = new GCStartBattleAction();
		startActionMsg.setGuid(this.getId());
		battle.broadcastToBattleUnits(startActionMsg);
		// 引导中不走倒计时
		if(!battle.isGuideing()){
			// 广播行动倒计时消息
			GCStartActionTimer timerMsg = new GCStartActionTimer();
			timerMsg.setTimeout(SharedConstants.BATTLE_ACTION_TIMEOUT);
			battle.broadcastToBattleUnits(timerMsg);
			battle.startChooseActionTimeoutSchedule(this);
		}
	}

	@Override
	public long getUnitGuid() {
		return this.getId();
	}

	@Override
	public MagicChange updateMagicSlots(List<ChessBoardSnap> snaps) {
		MagicChange change = new MagicChange();
		Map<Integer, Integer> result = new HashMap<Integer, Integer>();
		List<GemPosition> gems = new ArrayList<GemPosition>();
		for (ChessBoardSnap snap : snaps) {
			for (GemPosition gem : snap.getErasableGems()) {
				gems.add(gem);
			}
		}
		for (MagicSlotInfo slot : this.slots) {
			slot.putAll(gems);
			result.put(slot.getEnergyType(), slot.getCurrentSize());
		}
		change.setChangeBlue(result.get(EnergyType.BLUE.getIndex()));
		change.setChangeGreen(result.get(EnergyType.GREEN.getIndex()));
		change.setChangePurple(result.get(EnergyType.PURPLE.getIndex()));
		change.setChangeRed(result.get(EnergyType.RED.getIndex()));
		change.setChangeYellow(result.get(EnergyType.YELLOW.getIndex()));
		return change;
	}

	@Override
	public List<ISkill> getCarriedSkills() {
		List<ISkill> skills = new ArrayList<ISkill>();
		skills.add(this.getComboAttackSkill());
		skills.add(this.getNormalAttackSkill());
		return skills;
	}

	@Override
	public void deductHp(int changeHp) {
		this.context.getBattleProperty().setHp(
				this.context.getBattleProperty().getHp() - changeHp);
	}

	@Override
	public void addMagicEnergy(EnergyType energyType, int value) {
		for (MagicSlotInfo slot : this.slots) {
			if (energyType.getIndex() == slot.getEnergyType()) {
				slot.addEnergy(value);
			}
		}
	}

	@Override
	public void reduceMagicEnergy(EnergyType energyType, int value) {
		for (MagicSlotInfo slot : this.slots) {
			if (energyType.getIndex() == slot.getEnergyType()) {
				slot.reduceEnergy(value);
			}
		}
	}

	@Override
	public boolean canAction() {
		if (this.context == null) {
			return false;
		}
		return !this.context.getBuffManager().hasBuff(BuffType.STUN);
	}

	@Override
	public boolean forbidMagic() {
		return this.context.getBuffManager().hasBuff(BuffType.FORBID_MAGIC);
	}

	@Override
	public boolean hasEnoughMagicToUseSuchSkill(ISkill skill) {
		// 红
		for (MagicSlotInfo slot : this.slots) {
			int needValue = EnergyType.typeOf(slot.getEnergyType())
					.getTemplateEnergyValue(skill.getSkillTemplate());
			if (slot.getCurrentSize() < needValue) {
				return false;
			}
		}
		return true;
	}

	@Override
	public MagicChange getCurrentMagicSnap() {
		MagicChange result = new MagicChange();
		for (MagicSlotInfo slot : this.slots) {
			EnergyType energyType = EnergyType.typeOf(slot.getEnergyType());
			energyType.setMagicValue(slot, result);
		}
		return result;
	}

	@Override
	public void heartBeat() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void onNormalActionInvalid(int row1, int col1, int row2, int col2) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void enterBattleState() {
		this.isInBattleState = true;
	}

	@Override
	public void exitBattleState() {
		this.isInBattleState = false;
	}

	@Override
	public void onExitBattle() {
		this.context = null;
		this.isInBattleState = false;
		this.resetFinishActionState();
	}

	@Override
	public boolean isInBattleState() {
		return isInBattleState;
	}

	@Override
	public void sendMessage(IMessage message) {
		// do nothing
	}

}
