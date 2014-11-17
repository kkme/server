package com.hifun.soul.gameserver.item;

import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.item.feature.EquipItemFeature;
import com.hifun.soul.gameserver.item.template.EquipItemTemplate;


/**
 * 
 * 装备类物品
 * 
 * @author magicstone
 *
 */
public class EquipItem extends Item {
	private EquipItemTemplate equipTemplate;
	
	public EquipItem(String UUID, Integer itemId){
		super(UUID, itemId);
	}
	
	public EquipItem(Human human, String UUID, Integer itemId){
		super(human, UUID, itemId);
	}
	
	@Override
	protected void initTemplate() {
		equipTemplate = GameServerAssist.getItemTemplateManager().getEquipItemTemplate(getItemId());
	}

	@Override
	public EquipItemTemplate getTemplate() {
		return equipTemplate;
	}

	@Override
	protected void initFeature(Item item) {
		itemFeature = new EquipItemFeature(item);
	}

	@Override
	public boolean isEquip() {
		return true;
	}

	@Override
	public String getName() {
		return equipTemplate.getName();
	}

	@Override
	public String getDesc() {
		return equipTemplate.getDesc();
	}

	@Override
	public int getType() {
		return equipTemplate.getType();
	}

	@Override
	public int getIcon() {
		return equipTemplate.getIcon();
	}

	@Override
	public int getRarity() {
		return equipTemplate.getRarity();
	}

	@Override
	public int getBind() {
		return equipTemplate.getBind();
	}

	@Override
	public int getMaxOverlap() {
		return equipTemplate.getMaxOverlap();
	}
	
	/**
	 * 获取等级限制
	 * @return
	 */
	public int getLimitLevel(){
		return equipTemplate.getLimitLevel();
	}
	
	/**
	 * 获取职业限制
	 * @return
	 */
	public int getLimitOccupation(){
		return equipTemplate.getLimitOccupation();
	}
	/**
	 * 获取性别限制
	 * @return
	 */
	public int getLimitSex(){
		return equipTemplate.getLimitSex();
	}
	
	@Override
	public short getSellCurrencyType() {
		return equipTemplate.getCurrencyType();
	}

	@Override
	public int getSellCurrencyNum() {
		return equipTemplate.getCurrencyNum();
	}

	@Override
	public boolean isOverlapable() {		
		return equipTemplate.isOverlapable();
	}

	@Override
	public boolean isCanSell() {
		return equipTemplate.isSell();
	}
	
	public void setEquiped(boolean isEquiped){
		((EquipItemFeature)itemFeature).setEquiped(isEquiped);
	}
	
	public boolean isEquiped(){
		return ((EquipItemFeature)itemFeature).isEquiped();
	}
	
	public int getEquipPosition(){
		return equipTemplate.getPosition();
	}
}
