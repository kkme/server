package com.hifun.soul.gameserver.battle.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 服务器通知客户端更新棋盘
 *
 * @author SevenSoul
 */
@Component
public class GCUpdateChessBoard extends GCMessage{
	
	/** 棋盘快照 */
	private com.hifun.soul.gameserver.battle.msg.ChessBoardSnap[] chessBoardSnaps;
	/** 是否由client的移动触发 */
	private boolean isTriggerByClientMove;

	public GCUpdateChessBoard (){
	}
	
	public GCUpdateChessBoard (
			com.hifun.soul.gameserver.battle.msg.ChessBoardSnap[] chessBoardSnaps,
			boolean isTriggerByClientMove ){
			this.chessBoardSnaps = chessBoardSnaps;
			this.isTriggerByClientMove = isTriggerByClientMove;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		count = readShort();
		count = count < 0 ? 0 : count;
		chessBoardSnaps = new com.hifun.soul.gameserver.battle.msg.ChessBoardSnap[count];
		for(int i=0; i<count; i++){
			com.hifun.soul.gameserver.battle.msg.ChessBoardSnap objchessBoardSnaps = new com.hifun.soul.gameserver.battle.msg.ChessBoardSnap();
			chessBoardSnaps[i] = objchessBoardSnaps;
						{
	int subCounterasableGems = readShort();
		com.hifun.soul.gameserver.battle.gem.GemPosition[] subListerasableGems = new com.hifun.soul.gameserver.battle.gem.GemPosition[subCounterasableGems];
		objchessBoardSnaps.setErasableGems(subListerasableGems);
	for(int jerasableGems = 0; jerasableGems < subCounterasableGems; jerasableGems++){
						com.hifun.soul.gameserver.battle.gem.GemPosition objerasableGems = new com.hifun.soul.gameserver.battle.gem.GemPosition();
		subListerasableGems[jerasableGems] = objerasableGems;
						objerasableGems.setRow(readInteger());
								objerasableGems.setCol(readInteger());
								objerasableGems.setType(readInteger());
							}
	}
								{
	int subCountgeneratedGems = readShort();
		com.hifun.soul.gameserver.battle.gem.   ColNewGems[] subListgeneratedGems = new com.hifun.soul.gameserver.battle.gem.   ColNewGems[subCountgeneratedGems];
		objchessBoardSnaps.setGeneratedGems(subListgeneratedGems);
	for(int jgeneratedGems = 0; jgeneratedGems < subCountgeneratedGems; jgeneratedGems++){
						com.hifun.soul.gameserver.battle.gem.   ColNewGems objgeneratedGems = new com.hifun.soul.gameserver.battle.gem.   ColNewGems();
		subListgeneratedGems[jgeneratedGems] = objgeneratedGems;
						objgeneratedGems.setCol(readInteger());
									{
	int subCountgems = readShort();
		int[] subListgems = new int[subCountgems];
		objgeneratedGems.setGems(subListgems);
	for(int jgems = 0; jgems < subCountgems; jgems++){
						subListgems[jgems] = readInteger();
			}
	}
							}
	}
							objchessBoardSnaps.setEraseCount(readInteger());
							objchessBoardSnaps.setTriggerNewRound(readBoolean());
				}
		isTriggerByClientMove = readBoolean();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
	writeShort(chessBoardSnaps.length);
	for(int i=0; i<chessBoardSnaps.length; i++){
	com.hifun.soul.gameserver.battle.msg.ChessBoardSnap objchessBoardSnaps = chessBoardSnaps[i];
					com.hifun.soul.gameserver.battle.gem.GemPosition[] erasableGems_objchessBoardSnaps=objchessBoardSnaps.getErasableGems();
	writeShort(erasableGems_objchessBoardSnaps.length);
	for(int jerasableGems=0; jerasableGems<erasableGems_objchessBoardSnaps.length; jerasableGems++){
					com.hifun.soul.gameserver.battle.gem.GemPosition erasableGems_objchessBoardSnaps_jerasableGems = erasableGems_objchessBoardSnaps[jerasableGems];
													writeInteger(erasableGems_objchessBoardSnaps_jerasableGems.getRow());
													writeInteger(erasableGems_objchessBoardSnaps_jerasableGems.getCol());
													writeInteger(erasableGems_objchessBoardSnaps_jerasableGems.getType());
									}
					com.hifun.soul.gameserver.battle.gem.   ColNewGems[] generatedGems_objchessBoardSnaps=objchessBoardSnaps.getGeneratedGems();
	writeShort(generatedGems_objchessBoardSnaps.length);
	for(int jgeneratedGems=0; jgeneratedGems<generatedGems_objchessBoardSnaps.length; jgeneratedGems++){
					com.hifun.soul.gameserver.battle.gem.   ColNewGems generatedGems_objchessBoardSnaps_jgeneratedGems = generatedGems_objchessBoardSnaps[jgeneratedGems];
													writeInteger(generatedGems_objchessBoardSnaps_jgeneratedGems.getCol());
														int[] gems_generatedGems_objchessBoardSnaps_jgeneratedGems=generatedGems_objchessBoardSnaps_jgeneratedGems.getGems();
	writeShort(gems_generatedGems_objchessBoardSnaps_jgeneratedGems.length);
	for(int jgems=0; jgems<gems_generatedGems_objchessBoardSnaps_jgeneratedGems.length; jgems++){
					writeInteger(gems_generatedGems_objchessBoardSnaps_jgeneratedGems[jgems]);
			}
									}
				writeInteger(objchessBoardSnaps.getEraseCount());
				writeBoolean(objchessBoardSnaps.getTriggerNewRound());
	}
		writeBoolean(isTriggerByClientMove);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_UPDATE_CHESS_BOARD;
	}
	
	@Override
	public String getTypeName() {
		return "GC_UPDATE_CHESS_BOARD";
	}

	public com.hifun.soul.gameserver.battle.msg.ChessBoardSnap[] getChessBoardSnaps(){
		return chessBoardSnaps;
	}

	public void setChessBoardSnaps(com.hifun.soul.gameserver.battle.msg.ChessBoardSnap[] chessBoardSnaps){
		this.chessBoardSnaps = chessBoardSnaps;
	}	

	public boolean getIsTriggerByClientMove(){
		return isTriggerByClientMove;
	}
		
	public void setIsTriggerByClientMove(boolean isTriggerByClientMove){
		this.isTriggerByClientMove = isTriggerByClientMove;
	}
}