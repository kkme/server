package com.hifun.soul.gameserver.event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 事件总线接口的实现;
 * 
 * @author crazyjohn
 * 
 */
public class EventManager implements IEventBus {
	private Map<EventType, Set<IEventListener>> listeners = new HashMap<EventType, Set<IEventListener>>();

	public EventManager() {
		// 初始化listeners
		for (EventType type : EventType.values()) {
			listeners.put(type, new HashSet<IEventListener>());
		}
	}

	@Override
	public void addListener(EventType type, IEventListener listener) {
		this.listeners.get(type).add(listener);
	}

	@Override
	public void removeListener(EventType type, IEventListener listener) {
		this.listeners.get(type).remove(listener);
	}

	@Override
	public void fireEvent(IEvent event) {
		List<IEventListener> copyAvoidoncurrentModificationException = new ArrayList<IEventListener>();
		Set<IEventListener> listenerSet = this.listeners.get(event.getType());
		if (listenerSet.isEmpty()) {
			return;
		}
		copyAvoidoncurrentModificationException.addAll(listenerSet);
		for (IEventListener listener : copyAvoidoncurrentModificationException) {
			listener.onEvent(event);
		}
	}

}
