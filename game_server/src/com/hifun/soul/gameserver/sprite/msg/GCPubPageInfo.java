package com.hifun.soul.gameserver.sprite.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 服务器下发的页签信息列表
 *
 * @author SevenSoul
 */
@Component
public class GCPubPageInfo extends GCMessage{
	
	/** 普通对酒信息 */
	private com.hifun.soul.gameserver.sprite.model.SpritePubPageInfo[] commonOperationInfo;
	/** 必胜消耗的魔晶数量 */
	private int succeedCrystalCost;

	public GCPubPageInfo (){
	}
	
	public GCPubPageInfo (
			com.hifun.soul.gameserver.sprite.model.SpritePubPageInfo[] commonOperationInfo,
			int succeedCrystalCost ){
			this.commonOperationInfo = commonOperationInfo;
			this.succeedCrystalCost = succeedCrystalCost;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		count = readShort();
		count = count < 0 ? 0 : count;
		commonOperationInfo = new com.hifun.soul.gameserver.sprite.model.SpritePubPageInfo[count];
		for(int i=0; i<count; i++){
			com.hifun.soul.gameserver.sprite.model.SpritePubPageInfo objcommonOperationInfo = new com.hifun.soul.gameserver.sprite.model.SpritePubPageInfo();
			commonOperationInfo[i] = objcommonOperationInfo;
					objcommonOperationInfo.setPageId(readInteger());
							objcommonOperationInfo.setPageOpenLevel(readInteger());
							objcommonOperationInfo.setCommonCostType(readInteger());
							objcommonOperationInfo.setCommonCostNum(readInteger());
							objcommonOperationInfo.setSuperCostType(readInteger());
							objcommonOperationInfo.setSuperCostNum(readInteger());
							objcommonOperationInfo.setSuperFingerGuessOpenLevel(readInteger());
							objcommonOperationInfo.setSuperFingerGuessVipOpenLevel(readInteger());
				}
		succeedCrystalCost = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
	writeShort(commonOperationInfo.length);
	for(int i=0; i<commonOperationInfo.length; i++){
	com.hifun.soul.gameserver.sprite.model.SpritePubPageInfo objcommonOperationInfo = commonOperationInfo[i];
				writeInteger(objcommonOperationInfo.getPageId());
				writeInteger(objcommonOperationInfo.getPageOpenLevel());
				writeInteger(objcommonOperationInfo.getCommonCostType());
				writeInteger(objcommonOperationInfo.getCommonCostNum());
				writeInteger(objcommonOperationInfo.getSuperCostType());
				writeInteger(objcommonOperationInfo.getSuperCostNum());
				writeInteger(objcommonOperationInfo.getSuperFingerGuessOpenLevel());
				writeInteger(objcommonOperationInfo.getSuperFingerGuessVipOpenLevel());
	}
		writeInteger(succeedCrystalCost);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_PUB_PAGE_INFO;
	}
	
	@Override
	public String getTypeName() {
		return "GC_PUB_PAGE_INFO";
	}

	public com.hifun.soul.gameserver.sprite.model.SpritePubPageInfo[] getCommonOperationInfo(){
		return commonOperationInfo;
	}

	public void setCommonOperationInfo(com.hifun.soul.gameserver.sprite.model.SpritePubPageInfo[] commonOperationInfo){
		this.commonOperationInfo = commonOperationInfo;
	}	

	public int getSucceedCrystalCost(){
		return succeedCrystalCost;
	}
		
	public void setSucceedCrystalCost(int succeedCrystalCost){
		this.succeedCrystalCost = succeedCrystalCost;
	}
}