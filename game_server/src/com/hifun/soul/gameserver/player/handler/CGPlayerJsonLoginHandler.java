package com.hifun.soul.gameserver.player.handler;

import java.util.List;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gamedb.callback.IMainThreadDBCallback;
import com.hifun.soul.gameserver.player.Player;
import com.hifun.soul.gameserver.player.auth.local.ILocalHandler;
import com.hifun.soul.gameserver.player.auth.local.LocalHandlerFactory;
import com.hifun.soul.gameserver.player.auth.local.LocalType;
import com.hifun.soul.gameserver.player.msg.CGPlayerCoolieLogin;

/**
 * 处理使用json字符串登录;
 * 
 * @author crazyjohn
 * 
 */
@Component
public class CGPlayerJsonLoginHandler implements
		IMessageHandlerWithType<CGPlayerCoolieLogin> {
	@Autowired
	private LocalHandlerFactory handlerFactory;
	private ILocalHandler authHandler;
	/** 通用登录逻辑 */
	@Autowired
	private CommonLoginLogic loginLogic;

	@Override
	public short getMessageType() {
		return MessageType.CG_PLAYER_COOLIE_LOGIN;
	}

	@Override
	public void execute(CGPlayerCoolieLogin message) {
		// TODO: crazyjohn 腾讯要求要验证openId格式
		String jsonValue = message.getJsonValue();
		if (jsonValue == null || jsonValue.equals("")) {
			return;
		}
		final String account = getAccount(message);

		final Player player = new Player(message.getSession());
		// 登录前检查
		loginLogic.doLoginCheck(player, message.getSession().getIp());
		// 获取
		if (authHandler == null) {
			// 设置平台类型
			authHandler = this.handlerFactory
					.createLocalFactory(LocalType.QZONE.getIndex());
		}
		// 平台相关信息
		player.setPlateformJosnValue(jsonValue);
		this.authHandler.doAuthAction(jsonValue,
				new IMainThreadDBCallback<List<?>>() {

					@Override
					public void onSucceed(List<?> result) {
						loginLogic.onLoginSuccess(player, account, result);

					}

					@Override
					public void onFailed(String errorMsg) {
						// TODO Auto-generated method stub

					}
				});
	}

	private String getAccount(CGPlayerCoolieLogin message) {
		JSONObject json = JSONObject.fromObject(message.getJsonValue());
		return (String) json.get("openid");
	}

}
