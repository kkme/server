package com.hifun.soul.gameserver.battle.template;

import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.gameserver.player.msg.BattlePopInfo;

@ExcelRowBinding
public class BattlePopTemplate extends BattlePopTemplateVO {

	@Override
	public void check() throws TemplateConfigException {
		// TODO Auto-generated method stub

	}

	public BattlePopInfo toInfo() {
		BattlePopInfo info = new BattlePopInfo();
		info.setRoleId(this.id);
		info.setWords(this.words.toArray(new String[0]));
		return info;
	}

}
