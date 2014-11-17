package com.hifun.soul.gameserver.skill.effect.select;

import java.util.Random;

import com.hifun.soul.common.constants.SharedConstants;
import com.hifun.soul.gameserver.battle.IBattleUnit;
import com.hifun.soul.gameserver.battle.gem.GemPosition;
import com.hifun.soul.gameserver.battle.gem.erase.ErasePositionType;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.player.state.PlayerState;

/**
 * 选择宝石技能效果工具;
 * 
 * @author crazyjohn
 * 
 */
public class SelectGemUtil {
	private static Random random = new Random();

	/**
	 * 随机生成一个宝石;
	 * 
	 * @return
	 */
	public static GemPosition randomGem() {
		int row = random.nextInt(SharedConstants.GEM_MAX_ROW);
		int col = random.nextInt(SharedConstants.GEM_MAX_COL);
		return new GemPosition(row, col);
	}

	public static GemPosition getSelectedGem(IBattleUnit attacker,
			GemPosition selectedGem, ErasePositionType erasePosType) {
		if (erasePosType == ErasePositionType.ASSIGN) {
			if (attacker instanceof Human) {
				// 是否处于托管状态;托管状态宝石随机选取;
				Human battleHuman = (Human) attacker;
				if (battleHuman.getPlayer().getState() == PlayerState.HOSTING_BATTLING) {
					return randomGem();
				}
				return selectedGem;
			} else {
				return randomGem();
			}
		} else {
			return randomGem();
		}
	}
}
