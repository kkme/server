package com.hifun.soul.gameserver.battle.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 服务器通知更新黑宝石攻击加成
 *
 * @author SevenSoul
 */
@Component
public class GCUpdateBlackGemAttackAddRate extends GCMessage{
	
	/** 加成单元GUID */
	private long targetGuid;
	/** 是否同意战斗 */
	private int currentAddRate;

	public GCUpdateBlackGemAttackAddRate (){
	}
	
	public GCUpdateBlackGemAttackAddRate (
			long targetGuid,
			int currentAddRate ){
			this.targetGuid = targetGuid;
			this.currentAddRate = currentAddRate;
	}

	@Override
	protected boolean readImpl() {
		targetGuid = readLong();
		currentAddRate = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeLong(targetGuid);
		writeInteger(currentAddRate);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_UPDATE_BLACK_GEM_ATTACK_ADD_RATE;
	}
	
	@Override
	public String getTypeName() {
		return "GC_UPDATE_BLACK_GEM_ATTACK_ADD_RATE";
	}

	public long getTargetGuid(){
		return targetGuid;
	}
		
	public void setTargetGuid(long targetGuid){
		this.targetGuid = targetGuid;
	}

	public int getCurrentAddRate(){
		return currentAddRate;
	}
		
	public void setCurrentAddRate(int currentAddRate){
		this.currentAddRate = currentAddRate;
	}
}