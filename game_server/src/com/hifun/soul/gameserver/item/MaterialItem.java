package com.hifun.soul.gameserver.item;

import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.item.feature.MaterialItemFeature;
import com.hifun.soul.gameserver.item.template.MaterialItemTemplate;


/**
 * 
 * 材料类道具
 * 
 * @author magicstone
 *
 */
public class MaterialItem extends Item {
	private MaterialItemTemplate materialItemTemplate;
	
	public MaterialItem(String UUID, Integer itemId){
		super(UUID, itemId);
	}
	
	public MaterialItem(Human human, String UUID, Integer itemId){
		super(human, UUID, itemId);
	}
	
	@Override
	protected void initTemplate() {
		materialItemTemplate = GameServerAssist.getItemTemplateManager().getMaterialItemTemplate(getItemId());
	}

	@Override
	public MaterialItemTemplate getTemplate() {
		return materialItemTemplate;
	}

	@Override
	protected void initFeature(Item item) {
		itemFeature = new MaterialItemFeature(item);
	}

	@Override
	public boolean isEquip() {
		return false;
	}

	@Override
	public String getName() {
		return materialItemTemplate.getName();
	}

	@Override
	public String getDesc() {
		return materialItemTemplate.getDesc();
	}

	@Override
	public int getType() {
		return materialItemTemplate.getType();
	}

	@Override
	public int getIcon() {
		return materialItemTemplate.getIcon();
	}

	@Override
	public int getRarity() {
		return materialItemTemplate.getRarity();
	}

	@Override
	public int getBind() {
		return materialItemTemplate.getBind();
	}

	@Override
	public int getMaxOverlap() {
		return materialItemTemplate.getMaxOverlap();
	}
	
	@Override
	public short getSellCurrencyType() {
		return materialItemTemplate.getCurrencyType();
	}

	@Override
	public int getSellCurrencyNum() {
		return materialItemTemplate.getCurrencyNum();
	}

	@Override
	public boolean isOverlapable() {
		return materialItemTemplate.isOverlapable();
	}

	@Override
	public boolean isCanSell() {
		return materialItemTemplate.isSell();
	}
}
