package com.hifun.soul.gameserver.gm;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.command.impl.CommandProcessorImpl;
import com.hifun.soul.core.session.ISession;
import com.hifun.soul.gamedb.PlayerPermissionType;
import com.hifun.soul.gameserver.player.Player;
import com.hifun.soul.gameserver.startup.GameClientSession;

/**
 * 
 * GameServer的命令处理器，客户端Debug
 * 
 * @param <T>
 * 
 */
@Component
public class ClientAdminCmdProcessor<T extends ISession> extends
		CommandProcessorImpl<T> {

	/**
	 * 检查权限是否足够执行命令
	 * 
	 * @return
	 */
	@Override
	protected boolean checkPermission(T sender, String cmd, String content) {
		if (!(sender instanceof GameClientSession)) {
			return false;
		}
		Player player = ((GameClientSession) sender).getPlayer();

		if (player.getPermissionType() != PlayerPermissionType.GM_PLAYER) {
			return false;
		}

		return true;
	}
}
