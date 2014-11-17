package com.hifun.soul.gameserver.player.auth.local;

import java.util.HashMap;
import java.util.Map;

import com.hifun.soul.core.enums.IndexedEnum;
import com.hifun.soul.gameserver.rechargetx.IRechargeHandler;
import com.hifun.soul.gameserver.rechargetx.RechargeHandlerFactory;

/**
 * 平台类型;
 * 
 * @author crazyjohn
 * 
 */
public enum LocalType implements IndexedEnum {
	/** QQ空间 */
	QZONE(1) {
		@Override
		public ILocalHandler createLocalHandler() {
			return new QQLocalHandler();
		}

		@Override
		public IRechargeHandler getRechargeHandler() {
			return RechargeHandlerFactory.getQQRechargeHandler();
		}
	},
	;

	private int type;
	private static Map<Integer, LocalType> types = new HashMap<Integer, LocalType>();

	static {
		for (LocalType each : LocalType.values()) {
			types.put(each.getIndex(), each);
		}
	}

	LocalType(int type) {
		this.type = type;
	}

	@Override
	public int getIndex() {
		return type;
	}

	public static LocalType typeOf(int type) {
		return types.get(type);
	}

	public abstract ILocalHandler createLocalHandler();
	
	public abstract IRechargeHandler getRechargeHandler();
}
