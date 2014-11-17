package com.hifun.soul.gameserver.player.state;

/**
 * 玩家状态接口;
 * 
 * @author crazyjohn
 * 
 */
public interface IPlayerState {
	/**
	 * 是否可以处理指定的消息类型;
	 * 
	 * @param messageType
	 *            指定的消息类型;
	 * @return true表示可以处理;false 表示不可以处理;
	 */
	public boolean canProcessMessage(short messageType);

	/**
	 * 是否可以切换到指定的状态;
	 * 
	 * @param target
	 *            指定的状态;
	 * @return true 表示可以切换到指定状态; false 表示不可以;
	 */
	public boolean canTransferToState(IPlayerState target);
}
