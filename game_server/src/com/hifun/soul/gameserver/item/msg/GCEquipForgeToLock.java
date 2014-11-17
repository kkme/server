package com.hifun.soul.gameserver.item.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 装备洗炼锁定
 *
 * @author SevenSoul
 */
@Component
public class GCEquipForgeToLock extends GCMessage{
	
	/** 灵石洗炼需要灵石图标 */
	private int stoneIcon;
	/** 灵石洗炼需要灵石名称 */
	private String stoneName;
	/** 灵石洗炼需要灵石数量 */
	private int stoneNum;
	/** 锁定需要魔晶数量 */
	private int crystalNum;
	/** 灵石洗炼需要灵石id */
	private int stoneId;
	/** 洗炼模式(1:免费洗炼;2:灵石洗炼) */
	private int forgeType;

	public GCEquipForgeToLock (){
	}
	
	public GCEquipForgeToLock (
			int stoneIcon,
			String stoneName,
			int stoneNum,
			int crystalNum,
			int stoneId,
			int forgeType ){
			this.stoneIcon = stoneIcon;
			this.stoneName = stoneName;
			this.stoneNum = stoneNum;
			this.crystalNum = crystalNum;
			this.stoneId = stoneId;
			this.forgeType = forgeType;
	}

	@Override
	protected boolean readImpl() {
		stoneIcon = readInteger();
		stoneName = readString();
		stoneNum = readInteger();
		crystalNum = readInteger();
		stoneId = readInteger();
		forgeType = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(stoneIcon);
		writeString(stoneName);
		writeInteger(stoneNum);
		writeInteger(crystalNum);
		writeInteger(stoneId);
		writeInteger(forgeType);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_EQUIP_FORGE_TO_LOCK;
	}
	
	@Override
	public String getTypeName() {
		return "GC_EQUIP_FORGE_TO_LOCK";
	}

	public int getStoneIcon(){
		return stoneIcon;
	}
		
	public void setStoneIcon(int stoneIcon){
		this.stoneIcon = stoneIcon;
	}

	public String getStoneName(){
		return stoneName;
	}
		
	public void setStoneName(String stoneName){
		this.stoneName = stoneName;
	}

	public int getStoneNum(){
		return stoneNum;
	}
		
	public void setStoneNum(int stoneNum){
		this.stoneNum = stoneNum;
	}

	public int getCrystalNum(){
		return crystalNum;
	}
		
	public void setCrystalNum(int crystalNum){
		this.crystalNum = crystalNum;
	}

	public int getStoneId(){
		return stoneId;
	}
		
	public void setStoneId(int stoneId){
		this.stoneId = stoneId;
	}

	public int getForgeType(){
		return forgeType;
	}
		
	public void setForgeType(int forgeType){
		this.forgeType = forgeType;
	}
}