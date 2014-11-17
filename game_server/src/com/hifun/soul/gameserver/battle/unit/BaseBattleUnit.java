package com.hifun.soul.gameserver.battle.unit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.hifun.soul.gameserver.battle.property.BattleProperty;
import com.hifun.soul.gameserver.role.Role;
import com.hifun.soul.gameserver.role.properties.Level2Property;
import com.hifun.soul.gameserver.role.properties.PropertyType;
import com.hifun.soul.gameserver.skill.ISkill;
import com.hifun.soul.gameserver.skill.buff.BuffType;
import com.hifun.soul.gameserver.skill.msg.MagicChange;

/**
 * 基础的战斗单元;
 * 
 * @author crazyjohn
 * 
 */
@Deprecated
public abstract class BaseBattleUnit<R extends Role<?>> implements IBattleUnit {
	/** 魔法槽 */
	protected List<MagicSlotInfo> slots;
	/** 战斗中行动是否完成 */
	protected boolean isCurrentActionFinished = false;
	/** 战斗上下文;保证可视性 */
	protected volatile IBattleContext context;
	/** 角色是否处于战斗状态 */
	protected boolean isInBattleState;
	/** 战斗AI */
	protected IBattleAI battleAI = new BaseMonsterAI(this);
	protected long unitGuid;
	protected String name;
	protected int resourceId;
	/** 保证可视性 */
	protected volatile R role;
	
	public BaseBattleUnit(R role) {
		this.role = role;
	}

	@Override
	public List<MagicSlotInfo> getAllMagicSlots() {
		return slots;
	}

	public abstract List<ISkill> getCarriedSkills();

	@Override
	public IBattleContext getBattleContext() {
		return context;
	}

	public abstract void notifyAction();

	@Override
	public IBattleContext buildBattleContext(Battle aBattle) {
		// 构建魔法槽
		this.slots = this.buildMagicSlots();
		// 构建战斗属性
		BattleProperty battleProperty = role.getPropertyManager()
				.buildBattleProperty();
		context = new BattleContext(aBattle, this, battleProperty);
		return context;
	}

	private List<MagicSlotInfo> buildMagicSlots() {
		List<MagicSlotInfo> result = new ArrayList<MagicSlotInfo>();
		// 红
		MagicSlotInfo redSlot = new MagicSlotInfo();
		redSlot.setEnergyType(EnergyType.RED.getIndex());
		redSlot.setCapacity(role.getPropertyManager().getIntPropertySet(
				PropertyType.LEVEL2_PROPERTY).getPropertyValue(
				Level2Property.RED_MAX));
		redSlot.setCurrentSize(role.getPropertyManager().getIntPropertySet(
				PropertyType.LEVEL2_PROPERTY).getPropertyValue(
				Level2Property.RED_INIT_VALUE));
		redSlot.setAddValue(role.getPropertyManager().getIntPropertySet(
				PropertyType.LEVEL2_PROPERTY).getPropertyValue(
				Level2Property.RED_ELIMINATE_BONUS));
		// 黄
		MagicSlotInfo yellowSlot = new MagicSlotInfo();
		yellowSlot.setEnergyType(EnergyType.YELLOW.getIndex());
		yellowSlot.setCapacity(role.getPropertyManager().getIntPropertySet(
				PropertyType.LEVEL2_PROPERTY).getPropertyValue(
				Level2Property.YELLOW_MAX));
		yellowSlot.setCurrentSize(role.getPropertyManager().getIntPropertySet(
				PropertyType.LEVEL2_PROPERTY).getPropertyValue(
				Level2Property.YELLOW_INIT_VALUE));
		yellowSlot.setAddValue(role.getPropertyManager().getIntPropertySet(
				PropertyType.LEVEL2_PROPERTY).getPropertyValue(
				Level2Property.YELLOW_ELIMINATE_BONUS));
		// 蓝
		MagicSlotInfo blueSlot = new MagicSlotInfo();
		blueSlot.setEnergyType(EnergyType.BLUE.getIndex());
		blueSlot.setCapacity(role.getPropertyManager().getIntPropertySet(
				PropertyType.LEVEL2_PROPERTY).getPropertyValue(
				Level2Property.BLUE_MAX));
		blueSlot.setCurrentSize(role.getPropertyManager().getIntPropertySet(
				PropertyType.LEVEL2_PROPERTY).getPropertyValue(
				Level2Property.BLUE_INIT_VALUE));
		blueSlot.setAddValue(role.getPropertyManager().getIntPropertySet(
				PropertyType.LEVEL2_PROPERTY).getPropertyValue(
				Level2Property.BLUE_ELIMINATE_BONUS));
		// 绿
		MagicSlotInfo greenSlot = new MagicSlotInfo();
		greenSlot.setEnergyType(EnergyType.GREEN.getIndex());
		greenSlot.setCapacity(role.getPropertyManager().getIntPropertySet(
				PropertyType.LEVEL2_PROPERTY).getPropertyValue(
				Level2Property.GREEN_MAX));
		greenSlot.setCurrentSize(role.getPropertyManager().getIntPropertySet(
				PropertyType.LEVEL2_PROPERTY).getPropertyValue(
				Level2Property.GREEN_INIT_VALUE));
		greenSlot.setAddValue(role.getPropertyManager().getIntPropertySet(
				PropertyType.LEVEL2_PROPERTY).getPropertyValue(
				Level2Property.GREEN_ELIMINATE_BONUS));
		// 紫
		MagicSlotInfo purpleSlot = new MagicSlotInfo();
		purpleSlot.setEnergyType(EnergyType.PURPLE.getIndex());
		purpleSlot.setCapacity(role.getPropertyManager().getIntPropertySet(
				PropertyType.LEVEL2_PROPERTY).getPropertyValue(
				Level2Property.PURPLE_MAX));
		purpleSlot.setCurrentSize(role.getPropertyManager().getIntPropertySet(
				PropertyType.LEVEL2_PROPERTY).getPropertyValue(
				Level2Property.PURPLE_INIT_VALUE));
		purpleSlot.setAddValue(role.getPropertyManager().getIntPropertySet(
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
	public void enterBattleState() {
		this.isInBattleState = true;

	}

	@Override
	public void exitBattleState() {
		this.isInBattleState = false;
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
		return this.getBattleContext().getBattleProperty().getHp() <= 0;
	}

	@Override
	public boolean isInBattleState() {
		return isInBattleState;
	}

	@Override
	public long getUnitGuid() {
		return unitGuid;
	}

	@Override
	public String getUnitName() {
		return name;
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
	public int getUnitModelId() {
		return resourceId;
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

	public void sendMessage(IMessage message) {
		role.sendMessage(message);
	}

	public abstract ISkill getComboAttackSkill();

	public abstract ISkill getNormalAttackSkill();

	public abstract void onExitBattle();
	
	@Override
	public void setBattleBgId(int bgId) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public int getBattleBgId() {
		// TODO Auto-generated method stub
		return 0;
	}

}
