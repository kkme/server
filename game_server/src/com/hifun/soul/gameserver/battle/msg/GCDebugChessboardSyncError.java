package com.hifun.soul.gameserver.battle.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 服务器通知调试战斗棋盘不同步
 *
 * @author SevenSoul
 */
@Component
public class GCDebugChessboardSyncError extends GCMessage{
	

	public GCDebugChessboardSyncError (){
	}
	

	@Override
	protected boolean readImpl() {
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_DEBUG_CHESSBOARD_SYNC_ERROR;
	}
	
	@Override
	public String getTypeName() {
		return "GC_DEBUG_CHESSBOARD_SYNC_ERROR";
	}
}