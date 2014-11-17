package com.hifun.soul.gameserver.warrior.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 勇者之路任务是否已经完成
 *
 * @author SevenSoul
 */
@Component
public class GCWarriorState extends GCMessage{
	
	/** 勇者之路任务是否已经完成 */
	private boolean finish;

	public GCWarriorState (){
	}
	
	public GCWarriorState (
			boolean finish ){
			this.finish = finish;
	}

	@Override
	protected boolean readImpl() {
		finish = readBoolean();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeBoolean(finish);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_WARRIOR_STATE;
	}
	
	@Override
	public String getTypeName() {
		return "GC_WARRIOR_STATE";
	}

	public boolean getFinish(){
		return finish;
	}
		
	public void setFinish(boolean finish){
		this.finish = finish;
	}
}