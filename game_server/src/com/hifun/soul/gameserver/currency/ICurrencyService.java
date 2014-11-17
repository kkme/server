package com.hifun.soul.gameserver.currency;

import com.hifun.soul.common.LogReasons.MoneyLogReason;
import com.hifun.soul.gameserver.human.Human;

/**
 * 货币服务接口
 * 
 * @author magicstone
 *
 */
public interface ICurrencyService {
	
	boolean isEnough(Human human, CurrencyType type,int costCount);
	/**
	 * 
	 * @param human 角色对象
	 * @param type 货币类型
	 * @param addCount 入账数额
	 * @return true表示入账成功，反之则表示失败
	 */
	void ChangeMoney(Human human,CurrencyType type,int changeNum, MoneyLogReason reason, String param);

}
