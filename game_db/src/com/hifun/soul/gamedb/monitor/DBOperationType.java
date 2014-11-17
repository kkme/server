package com.hifun.soul.gamedb.monitor;

/**
 * 数据库请求类型;
 * 
 * @author crazyjohn
 * 
 */
public enum DBOperationType {
	/** get */
	GET(1),
	/** update */
	UPDATE(2),
	/** delete */
	DELETE(3),
	/** insert */
	INSERT(4),
	/** query */
	QUERY(5);

	private int type;

	DBOperationType(int type) {
		this.setType(type);
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
}
