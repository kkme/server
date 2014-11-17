package com.hifun.soul.gameserver.gm.command;

import com.hifun.soul.core.command.ICommand;
import com.hifun.soul.core.context.ApplicationContext;
import com.hifun.soul.gameserver.bulletin.manager.BulletinManager;
import com.hifun.soul.gameserver.gm.CommandConstants;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.startup.MinaGameClientSession;

/**
 * 发公告;
 * @author crazyjohn
 *
 */
public class SendBulletinCommand implements ICommand<MinaGameClientSession> {
	@Override
	public void execute(MinaGameClientSession player, String[] commands) {
		if (player == null) {
			return;
		}
		Human human = player.getPlayer().getHuman();
		if (human == null) {
			return;
		}
		if (commands.length < 1) {
			return;
		}

		String content = commands[0];
		
		BulletinManager manager = ApplicationContext.getApplicationContext().getBean(BulletinManager.class);
		manager.sendSystemBulletin(content);
	}

	@Override
	public String getCommandName() {
		return CommandConstants.GM_CMD_SEND_BULLETIN;
	}

}
