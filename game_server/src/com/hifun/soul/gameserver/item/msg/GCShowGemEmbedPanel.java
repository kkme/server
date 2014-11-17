package com.hifun.soul.gameserver.item.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 打开宝石镶嵌面板
 *
 * @author SevenSoul
 */
@Component
public class GCShowGemEmbedPanel extends GCMessage{
	

	public GCShowGemEmbedPanel (){
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
		return MessageType.GC_SHOW_GEM_EMBED_PANEL;
	}
	
	@Override
	public String getTypeName() {
		return "GC_SHOW_GEM_EMBED_PANEL";
	}
}