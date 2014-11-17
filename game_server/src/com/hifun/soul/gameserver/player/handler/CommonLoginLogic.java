package com.hifun.soul.gameserver.player.handler;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hifun.soul.common.auth.AccountInfo;
import com.hifun.soul.common.auth.IAuthResult;
import com.hifun.soul.common.constants.LangConstants;
import com.hifun.soul.common.constants.SystemMessageType;
import com.hifun.soul.core.context.ApplicationContext;
import com.hifun.soul.core.enums.AccountState;
import com.hifun.soul.core.i18n.SysLangService;
import com.hifun.soul.core.i18n.impl.SysLangServiceImpl;
import com.hifun.soul.gamedb.PlayerPermissionType;
import com.hifun.soul.gamedb.logger.Loggers;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.common.config.GameServerConfig;
import com.hifun.soul.gameserver.gameworld.GameWorld;
import com.hifun.soul.gameserver.human.occupation.OccupationTemplateManager;
import com.hifun.soul.gameserver.human.occupation.template.CharacterOccupationTemplate;
import com.hifun.soul.gameserver.player.Player;
import com.hifun.soul.gameserver.player.auth.LoginResultCode;
import com.hifun.soul.gameserver.player.msg.GCCharacterTemplate;
import com.hifun.soul.gameserver.player.msg.GCPlayerLoginResult;
import com.hifun.soul.gameserver.player.state.PlayerState;

/**
 * 通用的登录处理逻辑;
 * 
 * @author crazyjohn
 * 
 */

@Component
public class CommonLoginLogic {
	@Autowired
	private OccupationTemplateManager occupationDataManager;
	private static Logger logger = Loggers.DB_MAIN_LOGGER;
	@Autowired
	private GameWorld sceneManager;

	/**
	 * 处理重复登录;
	 * 
	 * @param exitPlayer
	 *            已经登录的玩家;
	 */
	protected void doRelogin(Player exitPlayer) {
		sendLoginResult(exitPlayer, LoginResultCode.RELOGIN);
		// 断开会话连接
		if (exitPlayer.getSession() != null) {
			exitPlayer.getSession().close();
		} else {
			if (logger.isErrorEnabled()) {
				sceneManager.playerLeve(exitPlayer);
				logger.error(String
						.format("The bitch is online, but has no attached session, so remove it! player: %s",
								exitPlayer.getAccount()));
			}

		}
	}

	/**
	 * 发送职业模版信息
	 * 
	 * @param player
	 */
	protected void sendCharacterTemplates(Player player) {
		GCCharacterTemplate msg = new GCCharacterTemplate();
		List<CharacterOccupationTemplate> characterTemplates = new ArrayList<CharacterOccupationTemplate>(
				occupationDataManager.getAll());
		msg.setCharacterTemplates(characterTemplates
				.toArray(new CharacterOccupationTemplate[0]));
		player.sendMessage(msg);
	}

	/**
	 * 发送登录结果
	 * 
	 * @param player
	 * @param resultCode
	 */
	protected void sendLoginResult(Player player, LoginResultCode resultCode) {
		GCPlayerLoginResult result = new GCPlayerLoginResult();
		result.setResultCode(resultCode.getResultCode());
		player.sendMessage(result);

	}

	/**
	 * 检查账号是否锁定;
	 * 
	 * @param player
	 * @param accountInfo
	 * @return true 表示锁定;
	 */
	protected boolean isAccountLocked(Player player, AccountInfo accountInfo) {
		AccountState state = AccountState.indexOf(accountInfo.getLockState());
		return state != AccountState.NORMAL;
	}

	/**
	 * 登录成功以后的回调;
	 * 
	 * @param player
	 * @param account
	 * @param result
	 */
	protected void onLoginSuccess(Player player, String account, List<?> result) {
		if (result == null || result.isEmpty()) {
			sendLoginResult(player, LoginResultCode.USERNAME_PASSWORD_WRONG);
			return;
		}
		IAuthResult authResult = null;
		if (!(result.get(0) instanceof IAuthResult)) {
			logger.error("Thre result type is not a IAuthResult type: "
					+ result.get(0).getClass());
			return;
		}
		authResult = (IAuthResult) result.get(0);
		if (!authResult.isSucceed()) {
			// send login error to client
			player.sendSystemMessage(SystemMessageType.ERROR,
					authResult.getErrorInfo());
			return;

		}

		AccountInfo accountInfo = authResult.getAccoutInfo();
		player.setPassportId(accountInfo.getPassportId());
		player.setAccount(account);
		// 设置权限
		player.setPermissionType(PlayerPermissionType.indexOf(accountInfo
				.getPermissionType()));
		// 设置平台数据信息
		player.setLocalProperties(accountInfo.getLocalProperties());
		// 检查账号是否被锁定;
		if (isAccountLocked(player, accountInfo)) {
			sendLoginResult(player, LoginResultCode.LOCKED);
			return;
		}
		// 检查是否重复登录
		Player exitPlayer = sceneManager.getPlayerByPassportId(accountInfo
				.getPassportId());
		if (exitPlayer != null) {
			// 对重复登录的处理;要把当前的角色踢掉;移除掉相应的回话信息;
			if (logger.isErrorEnabled()) {
				logger.error(String.format("Player relogin, passportId: %d",
						player.getPassportId()));
			}
			doRelogin(exitPlayer);
			sendLoginResult(player, LoginResultCode.RELOGIN);
			return;
		}
		GameServerConfig config = (GameServerConfig) ApplicationContext
				.getApplicationContext().getDefaultListableBeanFactory()
				.getSingleton(GameServerConfig.class.getSimpleName());
		// 登录墙不允许普通玩家登录;
		if (player.getPermissionType() != PlayerPermissionType.GM_PLAYER
				&& config.isLoginWallEnabled()) {
			SysLangService sysLangService = ApplicationContext
					.getApplicationContext().getBean(SysLangServiceImpl.class);
			player.sendSystemMessage(SystemMessageType.ERROR,
					sysLangService.read(LangConstants.LOGIN_ERROR_WALL_CLOSED));
			return;
		}
		if (logger.isInfoEnabled()) {
			logger.info(String.format(
					"Player login, passportId: %d, account: %s",
					player.getPassportId(), player.getAccount()));
		}
		sceneManager.addOnlineAccount(player);
		// 状态切换
		if (!player.canTransferStateTo(PlayerState.AUTHRONIZED)) {
			sendLoginResult(player, LoginResultCode.STATE_ERROR);
			return;
		}
		player.transferStateTo(PlayerState.AUTHRONIZED);
		player.setPassportId(accountInfo.getPassportId());

		sendLoginResult(player, LoginResultCode.SUCCESS);
		// 发送角色职业模版
		sendCharacterTemplates(player);
	}

	/**
	 * 登录前检查;
	 * 
	 * @param player
	 * @param ip
	 */
	protected void doLoginCheck(Player player, String ip) {
		// 服务器是否已经满员了
		if (sceneManager.isPlayerFull()) {
			// 提示服务器爆满;
			player.sendSystemMessage(
					SystemMessageType.ERROR,
					GameServerAssist.getSysLangService().read(
							LangConstants.LOGIN_ERROR_SERVER_FULL));
			player.disconnect();
			return;
		}
		if (player.transferStateTo(PlayerState.CONNECTED)) {
			player.setClientIp(ip);
		} else {
			logger.error(String.format(
					"Player can not transfer from state: %s to state: %s",
					player.getState(), PlayerState.CONNECTED));
		}

	}

}
