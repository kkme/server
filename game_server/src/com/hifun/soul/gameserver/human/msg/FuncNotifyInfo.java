package com.hifun.soul.gameserver.human.msg;

/**
 * 功能提示信息
 * 
 * @author yandajun
 * 
 */
public class FuncNotifyInfo {
	private int funcType;
	private boolean notify;

	public int getFuncType() {
		return funcType;
	}

	public void setFuncType(int funcType) {
		this.funcType = funcType;
	}

	public boolean getNotify() {
		return notify;
	}

	public void setNotify(boolean notify) {
		this.notify = notify;
	}
}
