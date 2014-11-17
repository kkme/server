package com.hifun.soul.gameserver.player.handler;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gamedb.callback.IMainThreadDBCallback;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.player.Player;
import com.hifun.soul.gameserver.player.auth.local.LocalHandlerFactory;
import com.hifun.soul.gameserver.player.msg.CGPlayerHeartBeat;
import com.hifun.soul.gameserver.rechargetx.IRechargeHandler;

/**
 * 玩家的心跳包;
 * 
 * @author crazyjohn
 * 
 */
@Component
public class CGPlayerHeartBeatHandler implements
		IMessageHandlerWithType<CGPlayerHeartBeat> {
	@Autowired
	private LocalHandlerFactory localHandlerFactory;

	@Override
	public short getMessageType() {
		return MessageType.CG_PLAYER_HEART_BEAT;
	}

	@Override
	public void execute(CGPlayerHeartBeat message) {
		Player player = message.getPlayer();
		IRechargeHandler rechargeHandler = localHandlerFactory.getRechargeHandler(
				GameServerAssist.getGameServerConfig().getLocalType());
		if(rechargeHandler == null){
			return;
		}
		if(player.getPlateformJosnValue() == null
				|| player.getPlateformJosnValue().isEmpty()){
			return;
		}
		// 保持平台登录连接
		rechargeHandler.checkPlateFormLoginState(player.getPlateformJosnValue(), 
				new IMainThreadDBCallback<List<?>>() {

					@Override
					public void onSucceed(List<?> results) {
					}

					@Override
					public void onFailed(String errorMsg) {
					}
				});
	}

}
