package com.hifun.soul.gameserver.manage.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.async.IAsyncCallbackMessage;
import com.hifun.soul.core.msg.BaseSessionMessage;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.session.ServerSession;

@Component
public class MGUpdateMarketActSetting extends BaseSessionMessage<ServerSession> implements IAsyncCallbackMessage{

	private long contextId;
	private int marketActType;
	private boolean enable;
	private int roleLevel;
	private int vipLevel;
	
	
	@Override
	public void execute() {
		throw new UnsupportedOperationException();
	}

	@Override
	public long getContextId() {		
		return contextId;
	}

	@Override
	public void setContextId(long id) {
		contextId = id;
	}

	@Override
	public short getType() {
		return MessageType.MG_UPDATE_MARKET_ACT_SETING;
	}

	public int getMarketActType() {
		return marketActType;
	}

	public void setMarketActType(int marketActType) {
		this.marketActType = marketActType;
	}

	public boolean isEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	public int getRoleLevel() {
		return roleLevel;
	}

	public void setRoleLevel(int roleLevel) {
		this.roleLevel = roleLevel;
	}

	public int getVipLevel() {
		return vipLevel;
	}

	public void setVipLevel(int vipLevel) {
		this.vipLevel = vipLevel;
	}

	@Override
	protected boolean readImpl() {
		this.contextId = readLong();
		this.marketActType = readInt();
		this.enable = readBoolean();
		this.roleLevel = readInt();
		this.vipLevel = readInt();
		return true;
	}

	@Override
	protected boolean writeImpl() {
		writeLong(this.contextId);
		writeInt(this.marketActType);
		writeBoolean(this.enable);
		writeInt(this.roleLevel);
		writeInt(this.vipLevel);
		return true;
	}

}
