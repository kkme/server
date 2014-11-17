package com.hifun.soul.gameserver.battle.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 客户端通知服务器动重置棋盘画播放完了
 * 
 * @author SevenSoul
 */
@Component
public class CGResetChessboardAnimationOver extends CGMessage{
	
	
	public CGResetChessboardAnimationOver (){
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
		return MessageType.CG_RESET_CHESSBOARD_ANIMATION_OVER;
	}
	
	@Override
	public String getTypeName() {
		return "CG_RESET_CHESSBOARD_ANIMATION_OVER";
	}

	@Override
	public void execute() {
	}
}