package com.hifun.soul.gameserver.godsoul.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 响应强化装备位
 *
 * @author SevenSoul
 */
@Component
public class GCUpgradeEquipBit extends GCMessage{
	
	/** 强化后装备位信息 */
	private com.hifun.soul.gameserver.godsoul.msg.EquipBitInfo equipBitInfo;
	/** 神魄buff信息  */
	private com.hifun.soul.gameserver.godsoul.info.GodsoulBuffInfo[] godsoulBuffInfos;

	public GCUpgradeEquipBit (){
	}
	
	public GCUpgradeEquipBit (
			com.hifun.soul.gameserver.godsoul.msg.EquipBitInfo equipBitInfo,
			com.hifun.soul.gameserver.godsoul.info.GodsoulBuffInfo[] godsoulBuffInfos ){
			this.equipBitInfo = equipBitInfo;
			this.godsoulBuffInfos = godsoulBuffInfos;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		equipBitInfo = new com.hifun.soul.gameserver.godsoul.msg.EquipBitInfo();
						equipBitInfo.setEquipBitType(readInteger());
						equipBitInfo.setCurrentLevel(readInteger());
						equipBitInfo.setCurrentEffect(readInteger());
						equipBitInfo.setNextEffect(readInteger());
						equipBitInfo.setNeedCrystalNum(readInteger());
						equipBitInfo.setSuccessRate(readInteger());
						equipBitInfo.setAmendSuccessRate(readInteger());
						equipBitInfo.setNeedCoin(readInteger());
						equipBitInfo.setNeedHumanLevel(readInteger());
						equipBitInfo.setIsMaxLevel(readBoolean());
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
		writeInteger(equipBitInfo.getEquipBitType());
		writeInteger(equipBitInfo.getCurrentLevel());
		writeInteger(equipBitInfo.getCurrentEffect());
		writeInteger(equipBitInfo.getNextEffect());
		writeInteger(equipBitInfo.getNeedCrystalNum());
		writeInteger(equipBitInfo.getSuccessRate());
		writeInteger(equipBitInfo.getAmendSuccessRate());
		writeInteger(equipBitInfo.getNeedCoin());
		writeInteger(equipBitInfo.getNeedHumanLevel());
		writeBoolean(equipBitInfo.getIsMaxLevel());
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
		return MessageType.GC_UPGRADE_EQUIP_BIT;
	}
	
	@Override
	public String getTypeName() {
		return "GC_UPGRADE_EQUIP_BIT";
	}

	public com.hifun.soul.gameserver.godsoul.msg.EquipBitInfo getEquipBitInfo(){
		return equipBitInfo;
	}
		
	public void setEquipBitInfo(com.hifun.soul.gameserver.godsoul.msg.EquipBitInfo equipBitInfo){
		this.equipBitInfo = equipBitInfo;
	}

	public com.hifun.soul.gameserver.godsoul.info.GodsoulBuffInfo[] getGodsoulBuffInfos (){
		return godsoulBuffInfos;
	}

	public void setGodsoulBuffInfos (com.hifun.soul.gameserver.godsoul.info.GodsoulBuffInfo[] godsoulBuffInfos){
		this.godsoulBuffInfos = godsoulBuffInfos;
	}	
}