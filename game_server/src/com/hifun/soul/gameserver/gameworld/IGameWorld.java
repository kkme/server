package com.hifun.soul.gameserver.gameworld;

import com.hifun.soul.core.msg.IMessage;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.player.Player;

/**
 * 游戏世界;<br>
 * 玩家登录选角色完成以后, 就会进入游戏世界, 玩家可以在游戏世界进行各种活动, 包括跟其他玩家的pvp等;<br>
 * 游戏世界管理所有进入世界中的玩家;所以有广播接口;<br>
 * 游戏世界是一个容器, 是场景的容器, 玩家可能会进入指定的场景; 受游戏类型的限制, 我们的游戏只有一个场景, 其实<br>
 * 也就是一个玩家的容器;<br>
 * 
 * @author crazyjohn
 * 
 */
public interface IGameWorld {
	/**
	 * 玩家进入游戏世界;
	 * 
	 * @param human
	 */
	public void humanEnter(Human human);

	/**
	 * 玩家离开游戏世界;
	 * 
	 * @param player
	 */
	public void playerLeve(Player player);

	/**
	 * 广播接口;
	 * 
	 * @param messge
	 */
	public void boradcast(IMessage messge);

	/**
	 * 向游戏世界投递消息;
	 * 
	 * @param msg
	 */
	void putMessage(IMessage msg);

	Player getPlayerByPassportId(long passportId);

	/**
	 * 关闭游戏世界所有服务;
	 */
	void stop();

	/**
	 * 启动游戏世界所有服务;
	 */
	void start();

	/**
	 * 游戏世界初始化;
	 * 
	 * @param maxPlayerCount
	 *            每个场景的玩家上限;
	 */
	void init(int maxPlayerCount, int tickIntervalForHuman, int tickIntervalForSystem);
}
