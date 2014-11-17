package com.hifun.soul.gameserver.elitestage.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hifun.soul.common.LogReasons.MoneyLogReason;
import com.hifun.soul.common.constants.LangConstants;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.currency.CurrencyType;
import com.hifun.soul.gameserver.elitestage.HumanEliteStageManager;
import com.hifun.soul.gameserver.elitestage.msg.CGRefreshEliteStageChallengeState;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.function.service.GameFuncService;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.vip.model.CommonActionCounter;

/**
 * 刷新精英副本挑战状态
 * 
 * @author magicstone
 *
 */
@Component
public class CGRefreshEliteStageChallengeStateHandler implements
		IMessageHandlerWithType<CGRefreshEliteStageChallengeState> {

	@Autowired
	private GameFuncService gameFuncService;
	
	@Override
	public short getMessageType() {
		return MessageType.CG_REFRESH_ELITE_STAGE_CHALLENGE_STATE;
	}

	@Override
	public void execute(CGRefreshEliteStageChallengeState message) {
		Human human = message.getPlayer().getHuman();
		// 判断功能是否开放
		if(!gameFuncService.gameFuncIsOpen(human, GameFuncType.ELITE, true)){
			return;
		}
		if(message.getStageTypeId()<=0){
			return;
		}
		HumanEliteStageManager manager = human.getHumanEliteStageManager();
		CommonActionCounter counter = manager.getChallengeCounter();
		if(counter.getRemainCount()<=0){
			human.sendErrorMessage(LangConstants.ELITE_REFRESH_TIME_USE_OUT);
			return;
		}
		if(human.getWallet().costMoney(CurrencyType.indexOf(counter.getCurrencyType()),counter.getCurrencyNum(), MoneyLogReason.REFRESH_ELITE_STAGE_CHALLENGE_STATE, "")){
			manager.refreshChallengeState(message.getStageTypeId());
		}
	}

}
