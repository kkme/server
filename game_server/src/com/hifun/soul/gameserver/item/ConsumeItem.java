package com.hifun.soul.gameserver.item;

import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.item.feature.ConsumeItemFeature;
import com.hifun.soul.gameserver.item.template.ConsumeItemTemplate;


/**
 * 
 * 消耗品道具
 * 
 * @author magicstone
 *
 */
public class ConsumeItem extends Item {
	private ConsumeItemTemplate consumeItemTemplate;
	
	public ConsumeItem(String UUID, Integer itemId){
		super(UUID, itemId);
	}
	
	public ConsumeItem(Human human, String UUID, Integer itemId){
		super(human, UUID, itemId);
	}
	
	@Override
	protected void initTemplate() {
		consumeItemTemplate = GameServerAssist.getItemTemplateManager().getConsumeItemTemplate(getItemId());
	}

	@Override
	public ConsumeItemTemplate getTemplate() {
		return consumeItemTemplate;
	}

	@Override
	protected void initFeature(Item item) {
		itemFeature = new ConsumeItemFeature(item);
	}

	@Override
	public boolean isEquip() {
		return false;
	}

	@Override
	public String getName() {
		return consumeItemTemplate.getName();
	}

	@Override
	public String getDesc() {
		return consumeItemTemplate.getDesc();
	}

	@Override
	public int getType() {
		return consumeItemTemplate.getType();
	}

	@Override
	public int getIcon() {
		return consumeItemTemplate.getIcon();
	}

	@Override
	public int getRarity() {
		return consumeItemTemplate.getRarity();
	}

	@Override
	public int getBind() {
		return consumeItemTemplate.getBind();
	}

	@Override
	public int getMaxOverlap() {		
		return consumeItemTemplate.getMaxOverlap();
	}

	@Override
	public short getSellCurrencyType() {
		return consumeItemTemplate.getCurrencyType();
	}

	@Override
	public int getSellCurrencyNum() {
		return consumeItemTemplate.getCurrencyNum();
	}

	@Override
	public boolean isOverlapable() {
		return consumeItemTemplate.isOverlapable();
	}

	@Override
	public boolean isCanSell() {
		return consumeItemTemplate.isSell();
	}

}
