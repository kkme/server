package com.hifun.soul.gameserver.scene;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.hifun.soul.common.IHeartBeatable;
import com.hifun.soul.common.ITickable;
import com.hifun.soul.gameserver.player.Player;

/**
 * 游戏世界玩家管理器;<br>
 * 1. GUID到玩家的映射;<br>
 * 2. clientId到玩家的映射;<br>
 * FIXME: crazyjohn 如何控制spring的构建依赖时间, 来解决类似PlayerManager(int maxPlayerCount)<br>
 * 这种需要先构建好服务, 然后再通知spring构建, 否则在其它用到此服务的地方会报错;
 * 
 * @author crazyjohn
 * 
 */
public class ScenePlayerManager implements ITickable, IHeartBeatable {
	/** 所有在线玩家GUID到玩家的映射 */
	private Map<Long, Player> onlinePlayersMap;
	/** 游戏中最多的玩家数量,超过此数将不能进入游戏 */
	private int maxPlayerNumber;

	public ScenePlayerManager(int maxPlayerNum) {
		onlinePlayersMap = new ConcurrentHashMap<Long, Player>();
	}

	/**
	 * 添加一个在线用户;<br>
	 * 在玩家认证成功以后调用;<br>
	 * 
	 * @param player
	 */
	public void addOnlineAccount(Player player) {
		if (player != null) {
			onlinePlayersMap.put(player.getPassportId(), player);
		}
	}

	/**
	 * 删除一个在线用户
	 * 
	 * @param passportId
	 */
	public void removeOnlineAccount(long passportId) {
		if (onlinePlayersMap.containsKey(passportId)) {
			onlinePlayersMap.remove(passportId);
		}
	}

	public Player getPlayer(long passportId) {
		return onlinePlayersMap.get(passportId);
	}



	public int getMaxPlayerNum() {
		return this.maxPlayerNumber;
	}

	public Collection<Player> getAllPlayers() {
		return Collections.unmodifiableCollection(this.onlinePlayersMap
				.values());
	}
	
	public Player getPlayerByPassportId(long passportId) {
		return onlinePlayersMap.get(passportId);
	}

	@Override
	public void heartBeat() {
		for (Player eachPlayer : this.onlinePlayersMap.values()) {
			if (eachPlayer == null) {
				continue;
			}
			eachPlayer.heartBeat();
		}
	}

	@Override
	public void tick() {
		for (Player eachPlayer : this.onlinePlayersMap.values()) {
			if (eachPlayer == null) {
				continue;
			}
			eachPlayer.processMessage();
		}
	}
}
