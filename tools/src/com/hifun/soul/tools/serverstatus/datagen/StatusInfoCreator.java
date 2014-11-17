package com.hifun.soul.tools.serverstatus.datagen;

import com.hifun.soul.common.constants.ServerTypes;
import com.hifun.soul.tools.serverstatus.AgentServerStatus;
import com.hifun.soul.tools.serverstatus.DbsServerStatus;
import com.hifun.soul.tools.serverstatus.GameServerStatus;
import com.hifun.soul.tools.serverstatus.LoginServerStatus;
import com.hifun.soul.tools.serverstatus.StatusInfo;
import com.hifun.soul.tools.serverstatus.WorldServerStatus;

/**
 *状态信息创建器
 * 
 * @author SevenSoul
 * 
 */
public class StatusInfoCreator {

	/**
	 * 根据服务器类型创建服务器状态
	 * 
	 * @param serverType
	 * @return
	 */
	public static StatusInfo createStatusInfo(String serverType) {
		int serverIntType = Integer.parseInt(serverType);
		switch (serverIntType) {
		case ServerTypes.DBS:
			return new DbsServerStatus();
		case ServerTypes.GAME:
			return new GameServerStatus();
		case ServerTypes.LOGIN:
			return new LoginServerStatus();
		case ServerTypes.WORLD:
			return new WorldServerStatus();
		case ServerTypes.AGENT:
			return new AgentServerStatus();
		default:
			return null;

		}
	}
}
