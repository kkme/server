package com.hifun.soul.gameserver.battle.msg.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.battle.PendingBattleRequest;
import com.hifun.soul.gameserver.battle.manager.IBattleManager;
import com.hifun.soul.gameserver.battle.msg.CGJoinBattleResponse;
import com.hifun.soul.gameserver.gameworld.GameWorld;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.battle.BattleType;
import com.hifun.soul.gameserver.battle.callback.PVPBattleCallback;
import com.hifun.soul.gameserver.mars.battle.MarsPVPBattleCallback;

/**
 * 客户端战斗请求的响应;
 * 
 * @author crazyjohn
 * 
 */

@Component
public class CGJoinBattleResponseHandler implements
		IMessageHandlerWithType<CGJoinBattleResponse> {
	@Autowired
	private IBattleManager battleManager;
	@Autowired
	private GameWorld sceneManager;

	@Override
	public short getMessageType() {
		return MessageType.CG_JOIN_BATTLE_RESPONSE;
	}

	@Override
	public void execute(CGJoinBattleResponse message) {
		Human beAttacked = message.getPlayer().getHuman();
		PendingBattleRequest battleRequest = battleManager
				.getBattleRequest(message.getChallengerGuid());
		if (battleRequest == null) {
			return;
		}
		if (battleRequest.getBeAttackedGuid() != beAttacked.getHumanGuid()) {
			return;
		}
		// 移除挂起的请求;
		PendingBattleRequest request = battleManager
				.removePendingBattleRequest(message.getChallengerGuid());
		if (request == null) {
			return;
		}

		long challengerGuid = message.getChallengerGuid();
		Human challenger = sceneManager.getSceneHumanManager().getHumanByGuid(
				challengerGuid);
		if (message.getIsAgree()) {
			// 如果是战神之巅的PVP战斗，接受有奖励
			PVPBattleCallback battleCallback = request.getCallback();
			if (battleCallback.getBattleType() == BattleType.PVP_MARS_BATTLE) {
				MarsPVPBattleCallback marsPVPBattleCallBack = (MarsPVPBattleCallback) battleCallback;
				marsPVPBattleCallBack.setIsAgree(true);
			}
			// 开始在线pvp
			battleManager.startBattleWithOnlineHuman(challenger, beAttacked,
					request.getCallback());
		} else {
			// 开始镜像战斗
			battleManager.startBattleWithHumanGuarder(challenger,
					beAttacked.getBattleGuarder(), request.getCallback());
		}
	}

}
