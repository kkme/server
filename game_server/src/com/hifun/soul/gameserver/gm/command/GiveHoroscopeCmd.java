package com.hifun.soul.gameserver.gm.command;

import com.hifun.soul.common.LogReasons.HoroscopeLogReason;
import com.hifun.soul.common.constants.Loggers;
import com.hifun.soul.core.command.IAdminCommand;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.gm.CommandConstants;
import com.hifun.soul.gameserver.horoscope.HoroscopeBagType;
import com.hifun.soul.gameserver.horoscope.msg.HoroscopeInfo;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.startup.MinaGameClientSession;


/**
 * 
 * 给星运的命令
 * 
 * @author magicstone
 *
 */
public class GiveHoroscopeCmd implements IAdminCommand<MinaGameClientSession> {

	@Override
	public void execute(MinaGameClientSession playerSession, String[] commands) {
		if (playerSession == null) {
			return;
		}
		
		Human human = playerSession.getPlayer().getHuman();
		if (human == null) {
			return;
		}
		if (commands.length < 2) {
			return;
		}

		int horoscopeId = Integer.parseInt(commands[0]);
		int num = Integer.parseInt(commands[1]);
		try {
			for(int i=0;i<num;i++){
				HoroscopeInfo horoscopeInfo = GameServerAssist.getHoroscopeTemplateManager().genHoroscopeInfo(horoscopeId, HoroscopeBagType.MAIN_BAG);
				if(horoscopeInfo == null){
					return;
				}
				human.getHumanHoroscopeManager().addHoroscopeInfo(HoroscopeBagType.MAIN_BAG, horoscopeInfo, HoroscopeLogReason.GM_GAMBLE, "");
			}
		} catch (Exception e) {
			Loggers.GM_LOGGER.error(String.format(
					"Give horoscope error, horoscopeId: %d, num: %d", horoscopeId, num), e);
		}
	}

	@Override
	public String getCommandName() {
		return CommandConstants.GM_CMD_GIVE_HOROSCOPE;
	}

}
