package com.hifun.soul.gameserver.scene;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

import com.hifun.soul.common.IHeartBeatable;
import com.hifun.soul.gameserver.human.Human;

/**
 * 游戏角色管理器;
 * <p>
 * 线程安全的;
 * 
 * @author crazyjohn
 * 
 */
@Component
public class SceneHumanManager implements IHeartBeatable{
	private Map<Long, Human> humans = new ConcurrentHashMap<Long, Human>();

	public void addHuman(Human human) {
		this.humans.put(human.getHumanGuid(), human);
	}

	/**
	 * 移除指定的角色;
	 * 
	 * @param humanGuid
	 *            角色的GUID;
	 * @return 如果移除成功则返回被移除的角色; 否则返回NULL;
	 */
	public Human removeHuman(long humanGuid) {
		return this.humans.remove(humanGuid);
	}

	public Human getHumanByGuid(long humanGuid) {
		return humans.get(humanGuid);
	}

	public Collection<Human> getAllHumans() {
		return Collections.unmodifiableCollection(humans.values());
	}

	@Override
	public void heartBeat() {
		for (Human human : this.humans.values()) {
			human.heartBeat();
		}
	}
}
