package com.hifun.soul.gameserver.target.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 响应打开个人目标面板
 *
 * @author SevenSoul
 */
@Component
public class GCOpenTargetPanel extends GCMessage{
	
	/** 目标信息  */
	private com.hifun.soul.gameserver.target.msg.info.TargetInfo[] targetInfos;

	public GCOpenTargetPanel (){
	}
	
	public GCOpenTargetPanel (
			com.hifun.soul.gameserver.target.msg.info.TargetInfo[] targetInfos ){
			this.targetInfos = targetInfos;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		count = readShort();
		count = count < 0 ? 0 : count;
		targetInfos = new com.hifun.soul.gameserver.target.msg.info.TargetInfo[count];
		for(int i=0; i<count; i++){
			com.hifun.soul.gameserver.target.msg.info.TargetInfo objtargetInfos = new com.hifun.soul.gameserver.target.msg.info.TargetInfo();
			targetInfos[i] = objtargetInfos;
					objtargetInfos.setTargetId(readInteger());
							objtargetInfos.setTargetIcon(readInteger());
							objtargetInfos.setTargetName(readString());
							objtargetInfos.setTargetDesc(readString());
							objtargetInfos.setTargetTaskDesc(readString());
							objtargetInfos.setTargetType(readInteger());
							objtargetInfos.setTargetParam(readInteger());
							objtargetInfos.setRewardState(readInteger());
								{
	int subCountrewardInfos = readShort();
		com.hifun.soul.gameserver.target.msg.info.TargetRewardInfo[] subListrewardInfos = new com.hifun.soul.gameserver.target.msg.info.TargetRewardInfo[subCountrewardInfos];
		objtargetInfos.setRewardInfos(subListrewardInfos);
	for(int jrewardInfos = 0; jrewardInfos < subCountrewardInfos; jrewardInfos++){
						com.hifun.soul.gameserver.target.msg.info.TargetRewardInfo objrewardInfos = new com.hifun.soul.gameserver.target.msg.info.TargetRewardInfo();
		subListrewardInfos[jrewardInfos] = objrewardInfos;
						objrewardInfos.setItemNum(readInteger());
									{
	com.hifun.soul.gameserver.item.assist.SimpleCommonItem objcommonItem = new com.hifun.soul.gameserver.item.assist.SimpleCommonItem();
	objrewardInfos.setCommonItem(objcommonItem);
				objcommonItem.setUUID(readString());
				objcommonItem.setItemId(readInteger());
				objcommonItem.setName(readString());
				objcommonItem.setDesc(readString());
				objcommonItem.setIcon(readInteger());
			}
							}
	}
				}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
	writeShort(targetInfos.length);
	for(int i=0; i<targetInfos.length; i++){
	com.hifun.soul.gameserver.target.msg.info.TargetInfo objtargetInfos = targetInfos[i];
				writeInteger(objtargetInfos.getTargetId());
				writeInteger(objtargetInfos.getTargetIcon());
				writeString(objtargetInfos.getTargetName());
				writeString(objtargetInfos.getTargetDesc());
				writeString(objtargetInfos.getTargetTaskDesc());
				writeInteger(objtargetInfos.getTargetType());
				writeInteger(objtargetInfos.getTargetParam());
				writeInteger(objtargetInfos.getRewardState());
					com.hifun.soul.gameserver.target.msg.info.TargetRewardInfo[] rewardInfos_objtargetInfos=objtargetInfos.getRewardInfos();
	writeShort(rewardInfos_objtargetInfos.length);
	for(int jrewardInfos=0; jrewardInfos<rewardInfos_objtargetInfos.length; jrewardInfos++){
					com.hifun.soul.gameserver.target.msg.info.TargetRewardInfo rewardInfos_objtargetInfos_jrewardInfos = rewardInfos_objtargetInfos[jrewardInfos];
													writeInteger(rewardInfos_objtargetInfos_jrewardInfos.getItemNum());
														com.hifun.soul.gameserver.item.assist.SimpleCommonItem commonItem_rewardInfos_objtargetInfos_jrewardInfos = rewardInfos_objtargetInfos_jrewardInfos.getCommonItem();
					writeString(commonItem_rewardInfos_objtargetInfos_jrewardInfos.getUUID());
					writeInteger(commonItem_rewardInfos_objtargetInfos_jrewardInfos.getItemId());
					writeString(commonItem_rewardInfos_objtargetInfos_jrewardInfos.getName());
					writeString(commonItem_rewardInfos_objtargetInfos_jrewardInfos.getDesc());
					writeInteger(commonItem_rewardInfos_objtargetInfos_jrewardInfos.getIcon());
											}
	}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_OPEN_TARGET_PANEL;
	}
	
	@Override
	public String getTypeName() {
		return "GC_OPEN_TARGET_PANEL";
	}

	public com.hifun.soul.gameserver.target.msg.info.TargetInfo[] getTargetInfos(){
		return targetInfos;
	}

	public void setTargetInfos(com.hifun.soul.gameserver.target.msg.info.TargetInfo[] targetInfos){
		this.targetInfos = targetInfos;
	}	
}