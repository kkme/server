package com.hifun.soul.gameserver.item.template;

/**
 * 标记接口;
 * 
 * @author crazyjohn
 * 
 */
public interface ItemTemplate {
	
	public int getId();
	
	public String getName();
	
	public String getDesc();
	
	public int getIcon();

	public boolean isOverlapable();
	
	public boolean canSell();
	
	public int getRarity();
	
	public int getLevelLimit();
	
	public int getOccupationLimit();
}
