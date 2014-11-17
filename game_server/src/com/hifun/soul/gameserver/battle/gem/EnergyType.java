package com.hifun.soul.gameserver.battle.gem;

import java.util.HashMap;
import java.util.Map;

import com.hifun.soul.core.annotation.AutoCreateClientEnumType;
import com.hifun.soul.core.annotation.ClientEnumComment;
import com.hifun.soul.core.enums.IndexedEnum;
import com.hifun.soul.gameserver.battle.IBattleUnit;
import com.hifun.soul.gameserver.role.properties.CalculateSymbol;
import com.hifun.soul.gameserver.role.properties.Level2Property;
import com.hifun.soul.gameserver.skill.msg.MagicChange;
import com.hifun.soul.gameserver.skill.template.SkillTemplate;

/**
 * 能量类型;
 * 
 * @author crazyjohn
 * 
 */
@AutoCreateClientEnumType
public enum EnergyType implements IndexedEnum {
	@ClientEnumComment(comment = "红魔法")
	RED(0, "红") {

		@Override
		public int getTemplateEnergyValue(SkillTemplate skillTemplate) {
			return skillTemplate.getRedCost();
		}

		@Override
		public int getValueFromMagicChangeBean(CalculateSymbol calculateSymbol,
				MagicChange magicChangeBean) {
			return magicChangeBean.getChangeRed() * calculateSymbol.getValue();
		}

		@Override
		public void setMagicValue(MagicSlotInfo slot, MagicChange result) {
			result.setChangeRed(slot.getCurrentSize());
		}

		@Override
		public int getMaxEnergyValue(IBattleUnit unit) {
			return unit.getBattleContext().getBattleProperty()
					.getBattleFinalPropertyByIndex(Level2Property.RED_MAX);
		}
	},
	@ClientEnumComment(comment = "黄魔法")
	YELLOW(1, "黄") {

		@Override
		public int getTemplateEnergyValue(SkillTemplate skillTemplate) {
			return skillTemplate.getYellowCost();
		}

		@Override
		public int getValueFromMagicChangeBean(CalculateSymbol calculateSymbol,
				MagicChange magicChangeBean) {
			return magicChangeBean.getChangeYellow()
					* calculateSymbol.getValue();
		}

		@Override
		public void setMagicValue(MagicSlotInfo slot, MagicChange result) {
			result.setChangeYellow(slot.getCurrentSize());
		}

		@Override
		public int getMaxEnergyValue(IBattleUnit unit) {
			return unit.getBattleContext().getBattleProperty()
					.getBattleFinalPropertyByIndex(Level2Property.YELLOW_MAX);
		}
	},
	@ClientEnumComment(comment = "蓝魔法")
	BLUE(2, "蓝") {

		@Override
		public int getTemplateEnergyValue(SkillTemplate skillTemplate) {
			return skillTemplate.getBlueCost();
		}

		@Override
		public int getValueFromMagicChangeBean(CalculateSymbol calculateSymbol,
				MagicChange magicChangeBean) {
			return magicChangeBean.getChangeBlue() * calculateSymbol.getValue();
		}

		@Override
		public void setMagicValue(MagicSlotInfo slot, MagicChange result) {
			result.setChangeBlue(slot.getCurrentSize());
		}

		@Override
		public int getMaxEnergyValue(IBattleUnit unit) {
			return unit.getBattleContext().getBattleProperty()
					.getBattleFinalPropertyByIndex(Level2Property.BLUE_MAX);
		}
	},
	@ClientEnumComment(comment = "绿魔法")
	GREEN(3, "绿") {
		@Override
		public int getTemplateEnergyValue(SkillTemplate skillTemplate) {
			return skillTemplate.getGreenCost();
		}

		@Override
		public int getValueFromMagicChangeBean(CalculateSymbol calculateSymbol,
				MagicChange magicChangeBean) {
			return magicChangeBean.getChangeGreen()
					* calculateSymbol.getValue();
		}

		@Override
		public void setMagicValue(MagicSlotInfo slot, MagicChange result) {
			result.setChangeGreen(slot.getCurrentSize());
		}

		@Override
		public int getMaxEnergyValue(IBattleUnit unit) {
			return unit.getBattleContext().getBattleProperty()
					.getBattleFinalPropertyByIndex(Level2Property.GREEN_MAX);
		}
	},
	@ClientEnumComment(comment = "紫魔法")
	PURPLE(4, "紫") {

		@Override
		public int getTemplateEnergyValue(SkillTemplate skillTemplate) {
			return skillTemplate.getPurpleCost();
		}

		@Override
		public int getValueFromMagicChangeBean(CalculateSymbol calculateSymbol,
				MagicChange magicChangeBean) {
			return magicChangeBean.getChangePurple()
					* calculateSymbol.getValue();
		}

		@Override
		public void setMagicValue(MagicSlotInfo slot, MagicChange result) {
			result.setChangePurple(slot.getCurrentSize());
		}

		@Override
		public int getMaxEnergyValue(IBattleUnit unit) {
			return unit.getBattleContext().getBattleProperty()
					.getBattleFinalPropertyByIndex(Level2Property.PURPLE_MAX);
		}
	},
	;

	private int type;
	private String desc;
	private static Map<Integer, EnergyType> types = new HashMap<Integer, EnergyType>();

	static {
		for (EnergyType each : EnergyType.values()) {
			types.put(each.getIndex(), each);
		}
	}

	EnergyType(int type, String desc) {
		this.type = type;
		this.desc = desc;
	}

	@Override
	public int getIndex() {
		return type;
	}

	public String getDesc() {
		return this.desc;
	}

	public static EnergyType typeOf(int type) {
		return types.get(type);
	}

	public abstract int getTemplateEnergyValue(SkillTemplate skillTemplate);

	public abstract int getValueFromMagicChangeBean(
			CalculateSymbol calculateSymbol, MagicChange magicChangeBean);

	public abstract void setMagicValue(MagicSlotInfo slot, MagicChange result);

	public abstract int getMaxEnergyValue(IBattleUnit unit);
}