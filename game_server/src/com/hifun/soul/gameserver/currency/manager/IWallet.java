package com.hifun.soul.gameserver.currency.manager;

import com.hifun.soul.common.LogReasons.MoneyLogReason;
import com.hifun.soul.gameserver.currency.CurrencyType;

/**
 * 货币管理器接口
 * 
 * @author magicstone
 *
 */
public interface IWallet {
	/**
	 * 判断是否足够拥有足够的货币
	 * @param type 货币类型
	 * @param costCount 需要消费的金额
	 * @return true表示足够，false表示不够
	 */
	boolean isEnough(CurrencyType type,int costCount);
	
	/**
	 * 消费货币
	 * <pre>消费货币时会检查是否有足够的货币用于此次消费，若不足会直接返回false
	 * </pre>
	 * 
	 * @param type 货币类型
	 * @param costCount 消费数额
	 * @return true表示消费成功，反之则表示失败
	 */
	boolean costMoney(CurrencyType type,int costCount,MoneyLogReason reason,String param);
	
	/**
	 * 货币入账
	 * 
	 * @param type 货币类型
	 * @param addCount 入账数额
	 * @param notice 是否想客户端发送货币变化消息
	 * @return true表示入账成功，反之则表示失败
	 */
	void addMoney(CurrencyType type,int addCount,boolean notice,MoneyLogReason reason, String param);
}
