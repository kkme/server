package com.hifun.soul.gameserver.mail;

public enum MailSendState {
	/** 未发送 */
	NOT_SEND(1),
	/** 已发送 */
	HAS_SENT(2),
	/** 已过期 */
	OVERDUE(3),
	;
	private int index;
	private MailSendState(int index){
		this.index = index;
	}
	public int getIndex(){
		return index;
	}
}
