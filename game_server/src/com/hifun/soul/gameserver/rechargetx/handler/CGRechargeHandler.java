package com.hifun.soul.gameserver.rechargetx.handler;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gamedb.callback.IMainThreadDBCallback;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.player.auth.local.LocalHandlerFactory;
import com.hifun.soul.gameserver.rechargetx.IRechargeHandler;
import com.hifun.soul.gameserver.rechargetx.msg.CGRecharge;
import com.hifun.soul.gameserver.rechargetx.msg.GCRecharge;

@Component
public class CGRechargeHandler implements IMessageHandlerWithType<CGRecharge> {
	@Autowired
	private LocalHandlerFactory localHandlerFactory;
	
	@Override
	public short getMessageType() {
		return MessageType.CG_RECHARGE;
	}

	@Override
	public void execute(CGRecharge message) {
		final Human human = message.getPlayer().getHuman();
		IRechargeHandler rechargeHandler = localHandlerFactory.getRechargeHandler(
				GameServerAssist.getGameServerConfig().getLocalType());
		if(rechargeHandler == null){
			return;
		}
		final int id = message.getId();
		// 判断用户发送过来充值档位是否合法
		if(!rechargeHandler.canRecharge(id)){
			return;
		}
		// 异步执行充值操作(有http请求的操作)
		rechargeHandler.doRecharge(human.getHumanGuid(), message.getId(), 
				message.getJsonInfo(), new IMainThreadDBCallback<List<?>>() {

					@Override
					public void onSucceed(List<?> results) {
						if(results == null
								|| results.size() < 2
								|| ((Boolean)(results.get(0))).booleanValue() == false){
							GCRecharge gcMsg = new GCRecharge();
							gcMsg.setId(id);
							gcMsg.setCanGoOn(false);
							gcMsg.setUrlParams("");
							human.sendMessage(gcMsg);
						}
						else{
							GCRecharge gcMsg = new GCRecharge();
							gcMsg.setId(id);
							gcMsg.setCanGoOn(true);
							gcMsg.setUrlParams((String)results.get(1));
							human.sendMessage(gcMsg);
						}
					}

					@Override
					public void onFailed(String errorMsg) {
						GCRecharge gcMsg = new GCRecharge();
						gcMsg.setId(id);
						gcMsg.setCanGoOn(false);
						gcMsg.setUrlParams("");
						human.sendMessage(gcMsg);
					}
				});
		
	}

}
