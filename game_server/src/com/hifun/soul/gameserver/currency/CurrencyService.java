package com.hifun.soul.gameserver.currency;

import com.hifun.soul.common.LogReasons.MoneyLogReason;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.compass.ICompassService;
import com.hifun.soul.gameserver.human.Human;

/**
 * 货币服务
 * 
 * @author magicstone
 * 
 */
public class CurrencyService implements ICurrencyService {

	private static CurrencyService instance;
	/** 罗盘服务接口 */
	private static ICompassService compassService;

	private CurrencyService() {
	}

	/**
	 * 创建实例
	 * 
	 * @return
	 */
	public static CurrencyService createInstance() {
		if (instance == null) {
			instance = new CurrencyService();
			compassService = GameServerAssist.getCompassService();
		}
		return instance;
	}

	@Override
	public void ChangeMoney(Human human, CurrencyType type, int changeNum,
			MoneyLogReason reason, String param) {
		if(changeNum==0){
			return;
		}
		if (type == CurrencyType.COIN) {
			int afterCoin = 0;
			if(changeNum>0){
				afterCoin = human.getCoin() + changeNum < 0 ? Integer.MAX_VALUE : human.getCoin() + changeNum;
			}else{
				afterCoin = human.getCoin() + changeNum < 0 ? 0 :  human.getCoin() + changeNum;
			}
			human.setCoin(afterCoin);
			sendMoneyLogMsg(human, type, changeNum, human.getCoin(), reason,
					param);
			if (compassService != null) {
				if (changeNum >= 0) {
					return;
				}
				compassService.consumeCoin((int) human.getHumanGuid(), human
						.getPlayer().getAccount(), reason.getReasonText(),
						String.valueOf(reason.getReason()), 1, changeNum, human
								.getCoin(), human.getLevel());

			}
		} else if (type == CurrencyType.CRYSTAL) {
			int afterCrystal = 0;
			if(changeNum>0){
				afterCrystal = human.getCrystal() + changeNum < 0 ? Integer.MAX_VALUE : human.getCrystal() + changeNum;
			}else{
				afterCrystal = human.getCrystal() + changeNum < 0 ? 0 :  human.getCrystal() + changeNum;
			}
			human.setCrystal(afterCrystal);
			sendMoneyLogMsg(human, type, changeNum, human.getCrystal(), reason,
					param);
			if (compassService != null) {
				if (changeNum >= 0) {
					return;
				}
				compassService.consumeCrystal((int) human.getHumanGuid(), human
						.getPlayer().getAccount(), 10 * -changeNum, reason
						.getReasonText(), String.valueOf(reason.getReason()),
						1, human.getCrystal(), human.getLevel());
			}
		}
	}

	@Override
	public boolean isEnough(Human human, CurrencyType type, int costCount) {
		if (type == CurrencyType.COIN) {
			if (human.getCoin() >= costCount) {
				return true;
			}
		} else if (type == CurrencyType.CRYSTAL) {
			if (human.getCrystal() >= costCount) {
				return true;
			}
		}
		return false;
	}

	private void sendMoneyLogMsg(Human human, CurrencyType type,
			int changeCount, int count, MoneyLogReason reason, String param) {
		GameServerAssist.getLogService().sendMoneyLog(human, reason, param,
				type.getIndex(), changeCount, count);
	}

}
