package com.hifun.soul.gameserver.stage.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 地图完美通关奖励的状态
 *
 * @author SevenSoul
 */
@Component
public class GCUpdatePerfectRewardState extends GCMessage{
	
	/** 地图完美通关奖励状态 */
	private com.hifun.soul.gameserver.stage.model.PerfectMapRewardStateInfo[] perfectMapRewardStateInfos;

	public GCUpdatePerfectRewardState (){
	}
	
	public GCUpdatePerfectRewardState (
			com.hifun.soul.gameserver.stage.model.PerfectMapRewardStateInfo[] perfectMapRewardStateInfos ){
			this.perfectMapRewardStateInfos = perfectMapRewardStateInfos;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		count = readShort();
		count = count < 0 ? 0 : count;
		perfectMapRewardStateInfos = new com.hifun.soul.gameserver.stage.model.PerfectMapRewardStateInfo[count];
		for(int i=0; i<count; i++){
			com.hifun.soul.gameserver.stage.model.PerfectMapRewardStateInfo objperfectMapRewardStateInfos = new com.hifun.soul.gameserver.stage.model.PerfectMapRewardStateInfo();
			perfectMapRewardStateInfos[i] = objperfectMapRewardStateInfos;
					objperfectMapRewardStateInfos.setMapId(readInteger());
							objperfectMapRewardStateInfos.setPerfectRewardState(readInteger());
				}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
	writeShort(perfectMapRewardStateInfos.length);
	for(int i=0; i<perfectMapRewardStateInfos.length; i++){
	com.hifun.soul.gameserver.stage.model.PerfectMapRewardStateInfo objperfectMapRewardStateInfos = perfectMapRewardStateInfos[i];
				writeInteger(objperfectMapRewardStateInfos.getMapId());
				writeInteger(objperfectMapRewardStateInfos.getPerfectRewardState());
	}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_UPDATE_PERFECT_REWARD_STATE;
	}
	
	@Override
	public String getTypeName() {
		return "GC_UPDATE_PERFECT_REWARD_STATE";
	}

	public com.hifun.soul.gameserver.stage.model.PerfectMapRewardStateInfo[] getPerfectMapRewardStateInfos(){
		return perfectMapRewardStateInfos;
	}

	public void setPerfectMapRewardStateInfos(com.hifun.soul.gameserver.stage.model.PerfectMapRewardStateInfo[] perfectMapRewardStateInfos){
		this.perfectMapRewardStateInfos = perfectMapRewardStateInfos;
	}	
}