package com.hifun.soul.gameserver.levy.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 收集魔法石
 *
 * @author SevenSoul
 */
@Component
public class GCCollectMagicStone extends GCMessage{
	
	/** 新宝石 */
	private com.hifun.soul.gameserver.levy.MagicStoneInfo[] collected;
	/** 免费收集次数 */
	private int freeCollectNum;
	/** 收集需要消耗的魔晶 */
	private int costCrystalNum;
	/** 魔晶拣选剩余次数 */
	private int crystalCollectRemainNum;

	public GCCollectMagicStone (){
	}
	
	public GCCollectMagicStone (
			com.hifun.soul.gameserver.levy.MagicStoneInfo[] collected,
			int freeCollectNum,
			int costCrystalNum,
			int crystalCollectRemainNum ){
			this.collected = collected;
			this.freeCollectNum = freeCollectNum;
			this.costCrystalNum = costCrystalNum;
			this.crystalCollectRemainNum = crystalCollectRemainNum;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		count = readShort();
		count = count < 0 ? 0 : count;
		collected = new com.hifun.soul.gameserver.levy.MagicStoneInfo[count];
		for(int i=0; i<count; i++){
			com.hifun.soul.gameserver.levy.MagicStoneInfo objcollected = new com.hifun.soul.gameserver.levy.MagicStoneInfo();
			collected[i] = objcollected;
					objcollected.setId(readInteger());
							objcollected.setIcon(readInteger());
							objcollected.setCollected(readBoolean());
							objcollected.setTargetIndex(readInteger());
				}
		freeCollectNum = readInteger();
		costCrystalNum = readInteger();
		crystalCollectRemainNum = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
	writeShort(collected.length);
	for(int i=0; i<collected.length; i++){
	com.hifun.soul.gameserver.levy.MagicStoneInfo objcollected = collected[i];
				writeInteger(objcollected.getId());
				writeInteger(objcollected.getIcon());
				writeBoolean(objcollected.getCollected());
				writeInteger(objcollected.getTargetIndex());
	}
		writeInteger(freeCollectNum);
		writeInteger(costCrystalNum);
		writeInteger(crystalCollectRemainNum);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_COLLECT_MAGIC_STONE;
	}
	
	@Override
	public String getTypeName() {
		return "GC_COLLECT_MAGIC_STONE";
	}

	public com.hifun.soul.gameserver.levy.MagicStoneInfo[] getCollected(){
		return collected;
	}

	public void setCollected(com.hifun.soul.gameserver.levy.MagicStoneInfo[] collected){
		this.collected = collected;
	}	

	public int getFreeCollectNum(){
		return freeCollectNum;
	}
		
	public void setFreeCollectNum(int freeCollectNum){
		this.freeCollectNum = freeCollectNum;
	}

	public int getCostCrystalNum(){
		return costCrystalNum;
	}
		
	public void setCostCrystalNum(int costCrystalNum){
		this.costCrystalNum = costCrystalNum;
	}

	public int getCrystalCollectRemainNum(){
		return crystalCollectRemainNum;
	}
		
	public void setCrystalCollectRemainNum(int crystalCollectRemainNum){
		this.crystalCollectRemainNum = crystalCollectRemainNum;
	}
}