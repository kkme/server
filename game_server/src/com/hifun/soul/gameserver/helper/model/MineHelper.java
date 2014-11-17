package com.hifun.soul.gameserver.helper.model;

import com.hifun.soul.gameserver.cd.CdType;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.helper.HelpStateType;
import com.hifun.soul.gameserver.helper.HelpType;
import com.hifun.soul.gameserver.helper.IHelper;
import com.hifun.soul.gameserver.mine.manager.HumanMineManager;
import com.hifun.soul.gameserver.vip.template.VipPrivilegeTemplate;

public class MineHelper implements IHelper {
	private HumanMineManager manager;

	public MineHelper(HumanMineManager manager) {
		this.manager = manager;
	}
	
	@Override
	public int getHelpType() {
		return HelpType.MINE.getIndex();
	}

	@Override
	public int getState() {
		// 功能未开放，返回已经结束
		if(!GameServerAssist.getGameFuncService().gameFuncIsOpen(manager.getOwner(), GameFuncType.MINE, false)){
			return HelpStateType.CLOSED.getIndex();
		}
		// 判断是否可以收获宝石
		else if(manager.getMineFields().size() > 0){
			// 判断开启的位置
			for(Integer index : manager.getMineFields().keySet()){
				if(manager.getMineFields().get(index).getType() == GameServerAssist.getGameConstants().getDefaultMineFieldType()){
					continue;
				}
				CdType cdType = manager.getCdType(index);
				if(manager.getOwner().getHumanCdManager().getRemainCd(cdType) <= 0){
					return HelpStateType.CAN_GET.getIndex();
				}
			}
			
			// 如果都在cd中则返回进行中
			return HelpStateType.RUNING.getIndex();
		}
		else{
			// 判断是否还可以开启
			VipPrivilegeTemplate template = GameServerAssist.getVipPrivilegeTemplateManager().getVipTemplate(manager.getOwner().getVipLevel());
			int vipBuyTimes = 0;
			if(template != null){
				vipBuyTimes = template.getMaxBuyMineFieldNum();
			}
			
			if(getUsedTimes() < getTotalTimes()
					&& manager.getRemainMineNum() + vipBuyTimes - manager.getBuyMineFieldNum() > 0){
				return HelpStateType.CAN_START.getIndex();
			}
		}
		
		return HelpStateType.CLOSED.getIndex();

	}

	@Override
	public int getUsedTimes() {
		return manager.getMineFields().size();
	}

	@Override
	public int getTotalTimes() {
		return HumanMineManager.MAX_MINE_FIELD_NUM;
	}

}
