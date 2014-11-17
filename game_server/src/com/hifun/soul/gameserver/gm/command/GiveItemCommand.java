package com.hifun.soul.gameserver.gm.command;

import com.hifun.soul.common.LogReasons.ItemLogReason;
import com.hifun.soul.common.constants.Loggers;
import com.hifun.soul.core.command.ICommand;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.gm.CommandConstants;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.item.template.ItemTemplate;
import com.hifun.soul.gameserver.startup.MinaGameClientSession;

/**
 * 给物品的GM命令;
 * 
 * @author crazyjohn
 * 
 */
public class GiveItemCommand implements ICommand<MinaGameClientSession> {
	@Override
	public void execute(MinaGameClientSession player, String[] commands) {
		if (player == null) {
			return;
		}
		Human human = player.getPlayer().getHuman();
		if (human == null) {
			return;
		}
		if (commands.length < 2) {
			return;
		}

		int itemId = Integer.parseInt(commands[0]);
		int count = Integer.parseInt(commands[1]);
		try {
			ItemTemplate itemTemplate = GameServerAssist.getItemTemplateManager().getItemTemplate(itemId);
			if (itemTemplate == null) {
				return;
			}
			boolean result = human.getBagManager().giveItem(itemTemplate, count, ItemLogReason.GM_COMMAND, "");
			if (! result) {
				Loggers.GM_LOGGER.error(String.format(
						"Give item error, iteId: %d, count: %d", itemId, count));
			}
		} catch (Exception e) {
			Loggers.GM_LOGGER.error(String.format(
					"Give item error, iteId: %d, count: %d", itemId, count), e);
		}

	}

	@Override
	public String getCommandName() {
		return CommandConstants.GM_CMD_GIVE_ITEM;
	}

}
