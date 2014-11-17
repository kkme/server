package com.hifun.soul.gameserver.sprite.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 招募精灵配置
 *
 * @author SevenSoul
 */
@Component
public class GCRecruitConfig extends GCMessage{
	
	/** 招募配置列表 */
	private com.hifun.soul.gameserver.sprite.template.SpriteRecruitTemplate[] configList;

	public GCRecruitConfig (){
	}
	
	public GCRecruitConfig (
			com.hifun.soul.gameserver.sprite.template.SpriteRecruitTemplate[] configList ){
			this.configList = configList;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		count = readShort();
		count = count < 0 ? 0 : count;
		configList = new com.hifun.soul.gameserver.sprite.template.SpriteRecruitTemplate[count];
		for(int i=0; i<count; i++){
			com.hifun.soul.gameserver.sprite.template.SpriteRecruitTemplate objconfigList = new com.hifun.soul.gameserver.sprite.template.SpriteRecruitTemplate();
			configList[i] = objconfigList;
					objconfigList.setId(readInteger());
							objconfigList.setSoulType(readInteger());
							objconfigList.setSoulNum(readInteger());
							objconfigList.setOpenLevel(readInteger());
				}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
	writeShort(configList.length);
	for(int i=0; i<configList.length; i++){
	com.hifun.soul.gameserver.sprite.template.SpriteRecruitTemplate objconfigList = configList[i];
				writeInteger(objconfigList.getId());
				writeInteger(objconfigList.getSoulType());
				writeInteger(objconfigList.getSoulNum());
				writeInteger(objconfigList.getOpenLevel());
	}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_RECRUIT_CONFIG;
	}
	
	@Override
	public String getTypeName() {
		return "GC_RECRUIT_CONFIG";
	}

	public com.hifun.soul.gameserver.sprite.template.SpriteRecruitTemplate[] getConfigList(){
		return configList;
	}

	public void setConfigList(com.hifun.soul.gameserver.sprite.template.SpriteRecruitTemplate[] configList){
		this.configList = configList;
	}	
}