package com.hifun.soul.gameserver.currency.manager;


import com.hifun.soul.common.LogReasons.MoneyLogReason;
import com.hifun.soul.common.constants.LangConstants;
import com.hifun.soul.gameserver.currency.CurrencyService;
import com.hifun.soul.gameserver.currency.CurrencyType;
import com.hifun.soul.gameserver.currency.ICurrencyService;
import com.hifun.soul.gameserver.human.Human;

/**
 * 货币管理器
 * 
 * @author magicstone
 * 
 */
public class Wallet implements IWallet {
	private Human human;
	private ICurrencyService currencyService;

	public Wallet(Human human) {
		this.human = human;
		currencyService = CurrencyService.createInstance();
	}

	@Override
	public boolean isEnough(CurrencyType type, int costCount) {
		if (human == null) {
			throw new NullPointerException("human 不能为null");
		}
		if (costCount < 0) {
			throw new IllegalArgumentException("costCount 不能小于0");
		}
		return currencyService.isEnough(human, type, costCount);

	}

	@Override
	public boolean costMoney(CurrencyType type, int costCount,MoneyLogReason reason,String param) {
		if(costCount==0){
			return true;
		}
		if (human == null) {
			throw new NullPointerException("human can not be null");
		}
		if(costCount<0){
			throw new IllegalArgumentException("The argument 'addCount' can not be smaller than 0");
		}
		if(!currencyService.isEnough(human, type, costCount)){
			human.sendWarningMessage(LangConstants.COMMON_NOT_ENOUGH, type.getDesc());
			return false;
		}
		currencyService.ChangeMoney(human, type, -costCount, reason, param);
		return true;
		
	}

	@Override
	public void addMoney(CurrencyType type, int addCount,boolean notice,MoneyLogReason reason, String param) {
		if (addCount == 0) {
			return;
		}
		if (human == null) {
			throw new NullPointerException("human can not be null");
		}
		if (addCount < 0) {
			throw new IllegalArgumentException("The argument 'addCount' can not be smaller than 0");
		}
		currencyService.ChangeMoney(human, type, addCount, reason, param);
		if(notice){
			sendMoneyAddMessage(type, addCount);
		}
	}
	
	private void sendMoneyAddMessage(CurrencyType type, int addCount){
		String operate = addCount>=0?"+":"-";
		human.sendImportantMessage(LangConstants.COMMON_OBTAIN,addCount,type.getDesc(), operate);
	}

}
