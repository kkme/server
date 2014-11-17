package com.hifun.soul.gameserver.player.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 战斗中的战斗pop信息
 *
 * @author SevenSoul
 */
@Component
public class GCBattlePop extends GCMessage{
	
	/** 所有的战斗泡泡信息  */
	private com.hifun.soul.gameserver.player.msg.BattlePopInfo[] allPops;
	/** PVE最大的回合数  */
	private int maxPveRound;
	/** PVP最大回合数  */
	private int maxPvpRound;

	public GCBattlePop (){
	}
	
	public GCBattlePop (
			com.hifun.soul.gameserver.player.msg.BattlePopInfo[] allPops,
			int maxPveRound,
			int maxPvpRound ){
			this.allPops = allPops;
			this.maxPveRound = maxPveRound;
			this.maxPvpRound = maxPvpRound;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		count = readShort();
		count = count < 0 ? 0 : count;
		allPops = new com.hifun.soul.gameserver.player.msg.BattlePopInfo[count];
		for(int i=0; i<count; i++){
			com.hifun.soul.gameserver.player.msg.BattlePopInfo objallPops = new com.hifun.soul.gameserver.player.msg.BattlePopInfo();
			allPops[i] = objallPops;
					objallPops.setRoleId(readLong());
								{
	int subCountwords = readShort();
		String[] subListwords = new String[subCountwords];
		objallPops.setWords(subListwords);
	for(int jwords = 0; jwords < subCountwords; jwords++){
						subListwords[jwords] = readString();
			}
	}
				}
		maxPveRound = readInteger();
		maxPvpRound = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
	writeShort(allPops.length);
	for(int i=0; i<allPops.length; i++){
	com.hifun.soul.gameserver.player.msg.BattlePopInfo objallPops = allPops[i];
				writeLong(objallPops.getRoleId());
					String[] words_objallPops=objallPops.getWords();
	writeShort(words_objallPops.length);
	for(int jwords=0; jwords<words_objallPops.length; jwords++){
					writeString(words_objallPops[jwords]);
			}
	}
		writeInteger(maxPveRound);
		writeInteger(maxPvpRound);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_BATTLE_POP;
	}
	
	@Override
	public String getTypeName() {
		return "GC_BATTLE_POP";
	}

	public com.hifun.soul.gameserver.player.msg.BattlePopInfo[] getAllPops(){
		return allPops;
	}

	public void setAllPops(com.hifun.soul.gameserver.player.msg.BattlePopInfo[] allPops){
		this.allPops = allPops;
	}	

	public int getMaxPveRound(){
		return maxPveRound;
	}
		
	public void setMaxPveRound(int maxPveRound){
		this.maxPveRound = maxPveRound;
	}

	public int getMaxPvpRound(){
		return maxPvpRound;
	}
		
	public void setMaxPvpRound(int maxPvpRound){
		this.maxPvpRound = maxPvpRound;
	}
}