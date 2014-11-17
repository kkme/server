package com.hifun.soul.gameserver.rechargetx.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 充值
 *
 * @author SevenSoul
 */
@Component
public class GCRecharge extends GCMessage{
	
	/** 充值档位 */
	private int id;
	/** 是否可以继续充值(大于0就可以) */
	private boolean canGoOn;
	/** 腾讯服务器发过来的url参数，客户端用 */
	private String urlParams;

	public GCRecharge (){
	}
	
	public GCRecharge (
			int id,
			boolean canGoOn,
			String urlParams ){
			this.id = id;
			this.canGoOn = canGoOn;
			this.urlParams = urlParams;
	}

	@Override
	protected boolean readImpl() {
		id = readInteger();
		canGoOn = readBoolean();
		urlParams = readString();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(id);
		writeBoolean(canGoOn);
		writeString(urlParams);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_RECHARGE;
	}
	
	@Override
	public String getTypeName() {
		return "GC_RECHARGE";
	}

	public int getId(){
		return id;
	}
		
	public void setId(int id){
		this.id = id;
	}

	public boolean getCanGoOn(){
		return canGoOn;
	}
		
	public void setCanGoOn(boolean canGoOn){
		this.canGoOn = canGoOn;
	}

	public String getUrlParams(){
		return urlParams;
	}
		
	public void setUrlParams(String urlParams){
		this.urlParams = urlParams;
	}
}