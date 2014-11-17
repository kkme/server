package com.hifun.soul.gameserver.rank;

/**
 * 排序接口(需要实时排序的排行榜实现这个接口)
 * 
 * @author yandajun
 * 
 */
public interface Sortable {
	/**
	 * 排序
	 */
	public abstract void sort();
}
