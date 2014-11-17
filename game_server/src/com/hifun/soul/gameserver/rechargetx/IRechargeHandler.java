package com.hifun.soul.gameserver.rechargetx;

import java.util.List;

import com.hifun.soul.gamedb.callback.IDBCallback;

public interface IRechargeHandler {

	/**
	 * 玩家是否可以充值，主要验证玩家传入的id是否是合法充值档位
	 * @param id
	 * @return
	 */
	public boolean canRecharge(int id);
	
	/**
	 * 玩家选择购买的处理
	 * @param jsonValue
	 * @param iMainThreadDBCallback
	 */
	public void doRecharge(long humanGUID, int id, String jsonValue, IDBCallback<List<?>> iMainThreadDBCallback);
	
	/**
	 * 平台已经确认购买的处理
	 * @param humanGUID
	 * @param jsonValue
	 * @param iMainThreadDBCallback
	 */
	public void doRechargeConfirm(long humanGUID, String jsonValue, IDBCallback<List<?>> iMainThreadDBCallback);
	
	/**
	 * 保持和平台之间的连接状态
	 */
	public void checkPlateFormLoginState(String jsonValue, IDBCallback<List<?>> iMainThreadDBCallback);
}
