package com.hifun.soul.gameserver.battle.gem.function;

import java.util.ArrayList;
import java.util.List;

import com.hifun.soul.common.constants.SharedConstants;
import com.hifun.soul.gameserver.battle.IBattleUnit;
import com.hifun.soul.gameserver.battle.gem.EnergyType;
import com.hifun.soul.gameserver.battle.gem.GemPosition;
import com.hifun.soul.gameserver.battle.gem.GemType;
import com.hifun.soul.gameserver.battle.msg.ChessBoardSnap;
import com.hifun.soul.gameserver.role.properties.Level2Property;

/**
 * 全能量功能宝石;
 * 
 * @author crazyjohn
 * 
 */
public class AllTypeFunctionGem implements IFunctionGems {

	@Override
	public void doEffect(IBattleUnit unit, ChessBoardSnap chessBoardSnap) {
		// 该玩家的所有能量都加最大能量上限的百分比
		// 获取消除白宝石的数量
		List<GemPosition> gems = new ArrayList<GemPosition>();
		for (GemPosition gem : chessBoardSnap.getErasableGems()) {
			gems.add(gem);
		}

		int counter = 0;
		for (GemPosition gem : gems) {
			if (gem.getType() == GemType.ALL.getIndex()) {
				counter++;
			}
		}

		// 没有宝石的话直接跳出;
		if (counter == 0) {
			return;
		}
		
		for (EnergyType type : EnergyType.values()) {
			int addValue = (int) (counter * (type.getMaxEnergyValue(unit)
					* unit.getBattleContext()
							.getBattleProperty()
							.getBattleFinalPropertyByIndex(
									Level2Property.WHITE_GEM_RECOVER_ENERGY_RATE) / SharedConstants.PROPERTY_DIVISOR));
			unit.addMagicEnergy(type, addValue);
		}

	}
}
