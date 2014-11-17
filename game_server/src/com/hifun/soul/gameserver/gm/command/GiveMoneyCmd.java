package com.hifun.soul.gameserver.gm.command;

import com.hifun.soul.common.LogReasons.MoneyLogReason;
import com.hifun.soul.common.constants.Loggers;
import com.hifun.soul.core.command.IAdminCommand;
import com.hifun.soul.gameserver.currency.CurrencyType;
import com.hifun.soul.gameserver.gm.CommandConstants;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.startup.MinaGameClientSession;
import com.hifun.soul.gameserver.vip.RechargeType;


/**
 * 
 * 给钱的gm命令
 * 
 * @author magicstone
 *
 */
public class GiveMoneyCmd implements IAdminCommand<MinaGameClientSession> {

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

		int currencyType = Integer.parseInt(commands[0]);
		int num = Integer.parseInt(commands[1]);
		
		try {			
			if(commands.length==3 && currencyType == CurrencyType.CRYSTAL.getIndex()){
				int rechargeRate = Integer.parseInt(commands[2]);
				human.getHumanVipManager().recharge(num, rechargeRate,RechargeType.GM_RECHARGE);				
			}
			else{
				human.getWallet().addMoney(CurrencyType.indexOf(currencyType), num, true, MoneyLogReason.GM_COMMAND, "");
			}
		} catch (Exception e) {
			Loggers.GM_LOGGER.error(String.format(
					"Give money error, currencyType: %d, num: %d", currencyType, num), e);
		}
	}

	@Override
	public String getCommandName() {
		return CommandConstants.GM_CMD_GIVE_MONEY;
	}

}
