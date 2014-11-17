package com.hifun.soul.gameserver.sprite.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 打开精灵面板响应
 *
 * @author SevenSoul
 */
@Component
public class GCOpenSpritePanel extends GCMessage{
	
	/** 精灵列表 */
	private com.hifun.soul.gameserver.sprite.model.SpriteInfo[] spriteList;
	/** 精灵背包格子列表 */
	private com.hifun.soul.gameserver.sprite.model.SpriteBagCellInfo[] spriteBagCellList;
	/** 出战装备位列表 */
	private com.hifun.soul.gameserver.sprite.model.SpriteEuqipSlotInfo[] equipSlotList;
	/** buff列表 */
	private com.hifun.soul.gameserver.sprite.model.SpriteBuffInfo[] buffList;
	/** 开启单个格子的魔晶消耗 */
	private int oneCellCrystalCost;

	public GCOpenSpritePanel (){
	}
	
	public GCOpenSpritePanel (
			com.hifun.soul.gameserver.sprite.model.SpriteInfo[] spriteList,
			com.hifun.soul.gameserver.sprite.model.SpriteBagCellInfo[] spriteBagCellList,
			com.hifun.soul.gameserver.sprite.model.SpriteEuqipSlotInfo[] equipSlotList,
			com.hifun.soul.gameserver.sprite.model.SpriteBuffInfo[] buffList,
			int oneCellCrystalCost ){
			this.spriteList = spriteList;
			this.spriteBagCellList = spriteBagCellList;
			this.equipSlotList = equipSlotList;
			this.buffList = buffList;
			this.oneCellCrystalCost = oneCellCrystalCost;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		count = readShort();
		count = count < 0 ? 0 : count;
		spriteList = new com.hifun.soul.gameserver.sprite.model.SpriteInfo[count];
		for(int i=0; i<count; i++){
			com.hifun.soul.gameserver.sprite.model.SpriteInfo objspriteList = new com.hifun.soul.gameserver.sprite.model.SpriteInfo();
			spriteList[i] = objspriteList;
					objspriteList.setUuid(readString());
							objspriteList.setSpriteId(readInteger());
							objspriteList.setIconId(readInteger());
							objspriteList.setLevel(readInteger());
							objspriteList.setName(readString());
							objspriteList.setQuality(readInteger());
							objspriteList.setSpriteType(readInteger());
							objspriteList.setLevelUpAura(readInteger());
							objspriteList.setPropId(readInteger());
							objspriteList.setPropValue(readInteger());
							objspriteList.setIsEquip(readBoolean());
							objspriteList.setDropReturnAura(readInteger());
				}
		count = readShort();
		count = count < 0 ? 0 : count;
		spriteBagCellList = new com.hifun.soul.gameserver.sprite.model.SpriteBagCellInfo[count];
		for(int i=0; i<count; i++){
			com.hifun.soul.gameserver.sprite.model.SpriteBagCellInfo objspriteBagCellList = new com.hifun.soul.gameserver.sprite.model.SpriteBagCellInfo();
			spriteBagCellList[i] = objspriteBagCellList;
					objspriteBagCellList.setIndex(readInteger());
							objspriteBagCellList.setOpen(readBoolean());
							objspriteBagCellList.setEquip(readBoolean());
							objspriteBagCellList.setSpriteUUID(readString());
				}
		count = readShort();
		count = count < 0 ? 0 : count;
		equipSlotList = new com.hifun.soul.gameserver.sprite.model.SpriteEuqipSlotInfo[count];
		for(int i=0; i<count; i++){
			com.hifun.soul.gameserver.sprite.model.SpriteEuqipSlotInfo objequipSlotList = new com.hifun.soul.gameserver.sprite.model.SpriteEuqipSlotInfo();
			equipSlotList[i] = objequipSlotList;
					objequipSlotList.setIndex(readInteger());
							objequipSlotList.setEquip(readBoolean());
							objequipSlotList.setSpriteUUID(readString());
				}
		count = readShort();
		count = count < 0 ? 0 : count;
		buffList = new com.hifun.soul.gameserver.sprite.model.SpriteBuffInfo[count];
		for(int i=0; i<count; i++){
			com.hifun.soul.gameserver.sprite.model.SpriteBuffInfo objbuffList = new com.hifun.soul.gameserver.sprite.model.SpriteBuffInfo();
			buffList[i] = objbuffList;
					objbuffList.setBuffId(readInteger());
							objbuffList.setName(readString());
							objbuffList.setPropId(readInteger());
							objbuffList.setPropValue(readInteger());
							objbuffList.setAmendType(readInteger());
							objbuffList.setActivateQuality(readInteger());
							objbuffList.setActivated(readBoolean());
				}
		oneCellCrystalCost = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
	writeShort(spriteList.length);
	for(int i=0; i<spriteList.length; i++){
	com.hifun.soul.gameserver.sprite.model.SpriteInfo objspriteList = spriteList[i];
				writeString(objspriteList.getUuid());
				writeInteger(objspriteList.getSpriteId());
				writeInteger(objspriteList.getIconId());
				writeInteger(objspriteList.getLevel());
				writeString(objspriteList.getName());
				writeInteger(objspriteList.getQuality());
				writeInteger(objspriteList.getSpriteType());
				writeInteger(objspriteList.getLevelUpAura());
				writeInteger(objspriteList.getPropId());
				writeInteger(objspriteList.getPropValue());
				writeBoolean(objspriteList.getIsEquip());
				writeInteger(objspriteList.getDropReturnAura());
	}
	writeShort(spriteBagCellList.length);
	for(int i=0; i<spriteBagCellList.length; i++){
	com.hifun.soul.gameserver.sprite.model.SpriteBagCellInfo objspriteBagCellList = spriteBagCellList[i];
				writeInteger(objspriteBagCellList.getIndex());
				writeBoolean(objspriteBagCellList.getOpen());
				writeBoolean(objspriteBagCellList.getEquip());
				writeString(objspriteBagCellList.getSpriteUUID());
	}
	writeShort(equipSlotList.length);
	for(int i=0; i<equipSlotList.length; i++){
	com.hifun.soul.gameserver.sprite.model.SpriteEuqipSlotInfo objequipSlotList = equipSlotList[i];
				writeInteger(objequipSlotList.getIndex());
				writeBoolean(objequipSlotList.getEquip());
				writeString(objequipSlotList.getSpriteUUID());
	}
	writeShort(buffList.length);
	for(int i=0; i<buffList.length; i++){
	com.hifun.soul.gameserver.sprite.model.SpriteBuffInfo objbuffList = buffList[i];
				writeInteger(objbuffList.getBuffId());
				writeString(objbuffList.getName());
				writeInteger(objbuffList.getPropId());
				writeInteger(objbuffList.getPropValue());
				writeInteger(objbuffList.getAmendType());
				writeInteger(objbuffList.getActivateQuality());
				writeBoolean(objbuffList.getActivated());
	}
		writeInteger(oneCellCrystalCost);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_OPEN_SPRITE_PANEL;
	}
	
	@Override
	public String getTypeName() {
		return "GC_OPEN_SPRITE_PANEL";
	}

	public com.hifun.soul.gameserver.sprite.model.SpriteInfo[] getSpriteList(){
		return spriteList;
	}

	public void setSpriteList(com.hifun.soul.gameserver.sprite.model.SpriteInfo[] spriteList){
		this.spriteList = spriteList;
	}	

	public com.hifun.soul.gameserver.sprite.model.SpriteBagCellInfo[] getSpriteBagCellList(){
		return spriteBagCellList;
	}

	public void setSpriteBagCellList(com.hifun.soul.gameserver.sprite.model.SpriteBagCellInfo[] spriteBagCellList){
		this.spriteBagCellList = spriteBagCellList;
	}	

	public com.hifun.soul.gameserver.sprite.model.SpriteEuqipSlotInfo[] getEquipSlotList(){
		return equipSlotList;
	}

	public void setEquipSlotList(com.hifun.soul.gameserver.sprite.model.SpriteEuqipSlotInfo[] equipSlotList){
		this.equipSlotList = equipSlotList;
	}	

	public com.hifun.soul.gameserver.sprite.model.SpriteBuffInfo[] getBuffList(){
		return buffList;
	}

	public void setBuffList(com.hifun.soul.gameserver.sprite.model.SpriteBuffInfo[] buffList){
		this.buffList = buffList;
	}	

	public int getOneCellCrystalCost(){
		return oneCellCrystalCost;
	}
		
	public void setOneCellCrystalCost(int oneCellCrystalCost){
		this.oneCellCrystalCost = oneCellCrystalCost;
	}
}