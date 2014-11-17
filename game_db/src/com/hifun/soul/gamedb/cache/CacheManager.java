package com.hifun.soul.gamedb.cache;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.hifun.soul.gamedb.cache.ICachableComponent;

@Scope("singleton")
@Component
public class CacheManager {

	/** 所有与玩家绑定的有缓存需求的管理器 */
	private List<ICachableComponent> humanCacheableManagers = new ArrayList<ICachableComponent>();
	/** 所有全局的有缓存需求的管理器 */
	private List<ICachableComponent> otherCacheableManagers = new ArrayList<ICachableComponent>();	
	
	public void registerHumanCachableComponent(ICachableComponent component){
		humanCacheableManagers.add(component);
	}
	
	public void registerOtherCachableComponent(ICachableComponent component){
		otherCacheableManagers.add(component);
	}

	public List<ICachableComponent> getHumanCaches(){
		return humanCacheableManagers;
	}
	
	public List<ICachableComponent> getOtherCaches(){
		return otherCacheableManagers;
	}
	
	public List<ICachableComponent> getAllCaches(){
		List<ICachableComponent> allCaches = new ArrayList<ICachableComponent>();
		allCaches.addAll(humanCacheableManagers);
		allCaches.addAll(otherCacheableManagers);
		return allCaches;
	}
}
