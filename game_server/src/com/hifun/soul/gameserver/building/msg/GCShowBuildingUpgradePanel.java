package com.hifun.soul.gameserver.building.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 打开建筑升级面板
 *
 * @author SevenSoul
 */
@Component
public class GCShowBuildingUpgradePanel extends GCMessage{
	
	/** 建筑类型(建筑id) */
	private int buildingType;
	/** 功能id */
	private int funcId;
	/** 升级需要的货币类型 */
	private short currencyType;
	/** 升级需要的货币数量 */
	private int currencyNum;
	/** 升级需要的木材 */
	private int woodNum;
	/** 升级需要的石矿 */
	private int mineNum;
	/** 升级需要的宝石矿 */
	private int gemNum;

	public GCShowBuildingUpgradePanel (){
	}
	
	public GCShowBuildingUpgradePanel (
			int buildingType,
			int funcId,
			short currencyType,
			int currencyNum,
			int woodNum,
			int mineNum,
			int gemNum ){
			this.buildingType = buildingType;
			this.funcId = funcId;
			this.currencyType = currencyType;
			this.currencyNum = currencyNum;
			this.woodNum = woodNum;
			this.mineNum = mineNum;
			this.gemNum = gemNum;
	}

	@Override
	protected boolean readImpl() {
		buildingType = readInteger();
		funcId = readInteger();
		currencyType = readShort();
		currencyNum = readInteger();
		woodNum = readInteger();
		mineNum = readInteger();
		gemNum = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(buildingType);
		writeInteger(funcId);
		writeShort(currencyType);
		writeInteger(currencyNum);
		writeInteger(woodNum);
		writeInteger(mineNum);
		writeInteger(gemNum);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_SHOW_BUILDING_UPGRADE_PANEL;
	}
	
	@Override
	public String getTypeName() {
		return "GC_SHOW_BUILDING_UPGRADE_PANEL";
	}

	public int getBuildingType(){
		return buildingType;
	}
		
	public void setBuildingType(int buildingType){
		this.buildingType = buildingType;
	}

	public int getFuncId(){
		return funcId;
	}
		
	public void setFuncId(int funcId){
		this.funcId = funcId;
	}

	public short getCurrencyType(){
		return currencyType;
	}
		
	public void setCurrencyType(short currencyType){
		this.currencyType = currencyType;
	}

	public int getCurrencyNum(){
		return currencyNum;
	}
		
	public void setCurrencyNum(int currencyNum){
		this.currencyNum = currencyNum;
	}

	public int getWoodNum(){
		return woodNum;
	}
		
	public void setWoodNum(int woodNum){
		this.woodNum = woodNum;
	}

	public int getMineNum(){
		return mineNum;
	}
		
	public void setMineNum(int mineNum){
		this.mineNum = mineNum;
	}

	public int getGemNum(){
		return gemNum;
	}
		
	public void setGemNum(int gemNum){
		this.gemNum = gemNum;
	}
}