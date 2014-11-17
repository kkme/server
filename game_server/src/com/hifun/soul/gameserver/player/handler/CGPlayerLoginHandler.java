package com.hifun.soul.gameserver.player.handler;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gamedb.callback.IMainThreadDBCallback;
import com.hifun.soul.gameserver.player.Player;
import com.hifun.soul.gameserver.player.auth.strategy.IAuthStrategy;
import com.hifun.soul.gameserver.player.msg.CGPlayerLogin;

/**
 * 登录消息处理器;<BR>
 * 1. 根据认证的方式不同,可以采用不同的认证策略{@link IAuthStrategy};<br>
 * 2. 出了使用策略以外,还有一种更纯粹的方法就是使用aspectj这种切面语言;<br>
 * 
 * @author crazyjohn
 * 
 */
@Component
public class CGPlayerLoginHandler implements
		IMessageHandlerWithType<CGPlayerLogin> {
	/** 认证策略 */
	@Autowired
	private IAuthStrategy authController;
	/** 通用登录逻辑 */
	@Autowired
	private CommonLoginLogic loginLogic;

	@Override
	public short getMessageType() {
		return MessageType.CG_PLAYER_LOGIN;
	}

	@Override
	public void execute(final CGPlayerLogin message) {
//		final GameServerConfig config = (GameServerConfig) ApplicationContext
//				.getApplicationContext().getDefaultListableBeanFactory()
//				.getSingleton(GameServerConfig.class.getSimpleName());
//		if (config.isUseLocalAuthorize()) {
//			// 非法情况, 不许登录
//			throw new RuntimeException(
//					"Can not use name&pass way to login, localSwitch is: "
//							+ config.isUseLocalAuthorize());
//		}
		if (message.getSession() == null) {
			return;
		}
		// 用户名密码是否为空;
		if (message.getAccount() == null || message.getAccount().equals("")) {
			return;
		}
		if (message.getPassword() == null || message.getPassword().equals("")) {
			return;
		}

		final Player player = new Player(message.getSession());
		loginLogic.doLoginCheck(player, message.getSession().getIp());

		// 进行认证;
		this.authController.doAuthAction(message.getAccount(),
				message.getPassword(), message.getSession().getIp(),
				new IMainThreadDBCallback<List<?>>() {

					@Override
					public void onSucceed(final List<?> result) {
						loginLogic.onLoginSuccess(player, message.getAccount(),
								result);
					}

					@Override
					public void onFailed(String errorMsg) {
						// TODO Auto-generated method stub

					}

				});

	}
}
