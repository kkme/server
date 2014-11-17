package com.hifun.soul.gameserver.godsoul.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 响应打开神魄面板
 *
 * @author SevenSoul
 */
@Component
public class GCOpenGodsoulPanel extends GCMessage{
	
	/** 装备位列表  */
	private com.hifun.soul.gameserver.godsoul.msg.EquipBitInfo[] equipBitInfos;
	/** 神魄buff信息  */
	private com.hifun.soul.gameserver.godsoul.info.GodsoulBuffInfo[] godsoulBuffInfos;

	public GCOpenGodsoulPanel (){
	}
	
	public GCOpenGodsoulPanel (
			com.hifun.soul.gameserver.godsoul.msg.EquipBitInfo[] equipBitInfos,
			com.hifun.soul.gameserver.godsoul.info.GodsoulBuffInfo[] godsoulBuffInfos ){
			this.equipBitInfos = equipBitInfos;
			this.godsoulBuffInfos = godsoulBuffInfos;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		count = readShort();
		count = count < 0 ? 0 : count;
		equipBitInfos = new com.hifun.soul.gameserver.godsoul.msg.EquipBitInfo[count];
		for(int i=0; i<count; i++){
			com.hifun.soul.gameserver.godsoul.msg.EquipBitInfo objequipBitInfos = new com.hifun.soul.gameserver.godsoul.msg.EquipBitInfo();
			equipBitInfos[i] = objequipBitInfos;
					objequipBitInfos.setEquipBitType(readInteger());
							objequipBitInfos.setCurrentLevel(readInteger());
							objequipBitInfos.setCurrentEffect(readInteger());
							objequipBitInfos.setNextEffect(readInteger());
							objequipBitInfos.setNeedCrystalNum(readInteger());
							objequipBitInfos.setSuccessRate(readInteger());
							objequipBitInfos.setAmendSuccessRate(readInteger());
							objequipBitInfos.setNeedCoin(readInteger());
							objequipBitInfos.setNeedHumanLevel(readInteger());
							objequipBitInfos.setIsMaxLevel(readBoolean());
				}
		count = readShort();
		count = count < 0 ? 0 : count;
		godsoulBuffInfos = new com.hifun.soul.gameserver.godsoul.info.GodsoulBuffInfo[count];
		for(int i=0; i<count; i++){
			com.hifun.soul.gameserver.godsoul.info.GodsoulBuffInfo objgodsoulBuffInfos = new com.hifun.soul.gameserver.godsoul.info.GodsoulBuffInfo();
			godsoulBuffInfos[i] = objgodsoulBuffInfos;
					objgodsoulBuffInfos.setBuffId(readInteger());
							objgodsoulBuffInfos.setNeedUpgradeLevel(readInteger());
							objgodsoulBuffInfos.setPropertyId(readInteger());
							objgodsoulBuffInfos.setAmendEffect(readInteger());
							objgodsoulBuffInfos.setValid(readBoolean());
				}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
	writeShort(equipBitInfos.length);
	for(int i=0; i<equipBitInfos.length; i++){
	com.hifun.soul.gameserver.godsoul.msg.EquipBitInfo objequipBitInfos = equipBitInfos[i];
				writeInteger(objequipBitInfos.getEquipBitType());
				writeInteger(objequipBitInfos.getCurrentLevel());
				writeInteger(objequipBitInfos.getCurrentEffect());
				writeInteger(objequipBitInfos.getNextEffect());
				writeInteger(objequipBitInfos.getNeedCrystalNum());
				writeInteger(objequipBitInfos.getSuccessRate());
				writeInteger(objequipBitInfos.getAmendSuccessRate());
				writeInteger(objequipBitInfos.getNeedCoin());
				writeInteger(objequipBitInfos.getNeedHumanLevel());
				writeBoolean(objequipBitInfos.getIsMaxLevel());
	}
	writeShort(godsoulBuffInfos.length);
	for(int i=0; i<godsoulBuffInfos.length; i++){
	com.hifun.soul.gameserver.godsoul.info.GodsoulBuffInfo objgodsoulBuffInfos = godsoulBuffInfos[i];
				writeInteger(objgodsoulBuffInfos.getBuffId());
				writeInteger(objgodsoulBuffInfos.getNeedUpgradeLevel());
				writeInteger(objgodsoulBuffInfos.getPropertyId());
				writeInteger(objgodsoulBuffInfos.getAmendEffect());
				writeBoolean(objgodsoulBuffInfos.getValid());
	}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_OPEN_GODSOUL_PANEL;
	}
	
	@Override
	public String getTypeName() {
		return "GC_OPEN_GODSOUL_PANEL";
	}

	public com.hifun.soul.gameserver.godsoul.msg.EquipBitInfo[] getEquipBitInfos (){
		return equipBitInfos;
	}

	public void setEquipBitInfos (com.hifun.soul.gameserver.godsoul.msg.EquipBitInfo[] equipBitInfos){
		this.equipBitInfos = equipBitInfos;
	}	

	public com.hifun.soul.gameserver.godsoul.info.GodsoulBuffInfo[] getGodsoulBuffInfos (){
		return godsoulBuffInfos;
	}

	public void setGodsoulBuffInfos (com.hifun.soul.gameserver.godsoul.info.GodsoulBuffInfo[] godsoulBuffInfos){
		this.godsoulBuffInfos = godsoulBuffInfos;
	}	
}