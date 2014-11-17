package com.hifun.soul.gameserver.helper.model;

import com.hifun.soul.gameserver.cd.CdType;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.helper.HelpStateType;
import com.hifun.soul.gameserver.helper.HelpType;
import com.hifun.soul.gameserver.helper.IHelper;
import com.hifun.soul.gameserver.training.manager.HumanTrainingManager;

public class TrainingHelper implements IHelper{
	private HumanTrainingManager manager;
	
	public TrainingHelper(HumanTrainingManager manager) {
		this.manager = manager;
	}
	
	@Override
	public int getHelpType() {
		return HelpType.TRAIN.getIndex();
	}

	@Override
	public int getState() {
		// 功能未开放，返回已经结束
		if(!GameServerAssist.getGameFuncService().gameFuncIsOpen(manager.getHuman(), GameFuncType.TRAINING, false)){
			return HelpStateType.CLOSED.getIndex();
		}
		// 判断玩家是否已经达到最大等级
		else if(manager.getHuman().getLevel() == GameServerAssist.getLevelUpTemplateManager().getHumanMaxLevel()){
			return HelpStateType.CLOSED.getIndex();
		}
		// 训练时间小于等于0并且不是正在训练返回结束
		else if(manager.getNormalTrainingTimeRemain() <= 0
				&& manager.getCurrentTrainingType() <= 0){
			return HelpStateType.CLOSED.getIndex();
		}
		// 正在训练的类型不为0并且没有cd则返回可以收获
		else if(manager.getCurrentTrainingType() > 0
				&& manager.getHuman().getHumanCdManager().getRemainCd(CdType.TRAINING) <= 0){
			return HelpStateType.CAN_GET.getIndex();
		}
		// 正在训练的类型不为0并且有cd则返回训练中
		else if(manager.getCurrentTrainingType() > 0
				&& manager.getHuman().getHumanCdManager().getRemainCd(CdType.TRAINING) > 0){
			return HelpStateType.RUNING.getIndex();
		}
		// 正在训练的类型为0并且有剩余训练时间则返回可以开始训练
		else if(manager.getCurrentTrainingType() <= 0
				&& manager.getNormalTrainingTimeRemain() > 0){
			return HelpStateType.CAN_START.getIndex();
		}
		
		return 0;
	}

	@Override
	public int getUsedTimes() {
		return 0;
	}

	@Override
	public int getTotalTimes() {
		return 0;
	}

}
