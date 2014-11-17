package com.hifun.soul.gameserver.battle.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 服务器通知客户端行动超时
 *
 * @author SevenSoul
 */
@Component
public class GCChooseActionTimeout extends GCMessage{
	
	/** 行动单元GUID */
	private long actionUnitGuid;
	/** 超时被扣除的血量 */
	private int changeHp;

	public GCChooseActionTimeout (){
	}
	
	public GCChooseActionTimeout (
			long actionUnitGuid,
			int changeHp ){
			this.actionUnitGuid = actionUnitGuid;
			this.changeHp = changeHp;
	}

	@Override
	protected boolean readImpl() {
		actionUnitGuid = readLong();
		changeHp = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeLong(actionUnitGuid);
		writeInteger(changeHp);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_CHOOSE_ACTION_TIMEOUT;
	}
	
	@Override
	public String getTypeName() {
		return "GC_CHOOSE_ACTION_TIMEOUT";
	}

	public long getActionUnitGuid(){
		return actionUnitGuid;
	}
		
	public void setActionUnitGuid(long actionUnitGuid){
		this.actionUnitGuid = actionUnitGuid;
	}

	public int getChangeHp(){
		return changeHp;
	}
		
	public void setChangeHp(int changeHp){
		this.changeHp = changeHp;
	}
}