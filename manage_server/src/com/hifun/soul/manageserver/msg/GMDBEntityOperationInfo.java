package com.hifun.soul.manageserver.msg;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.async.IAsyncCallbackMessage;
import com.hifun.soul.core.msg.BaseSessionMessage;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.session.ServerSession;
import com.hifun.soul.manageserver.model.DBEntityOperationInfo;

@Component
public  class GMDBEntityOperationInfo extends BaseSessionMessage<ServerSession> implements IAsyncCallbackMessage{
	private long contextId;
	private DBEntityOperationInfo[] operationInfos;
	private Map<String,Integer> dbQueryTimes;
	
	@Override
	public void execute() {
		throw new UnsupportedOperationException();		
	}

	@Override
	public long getContextId() {		
		return this.contextId;
	}

	@Override
	public void setContextId(long id) {
		contextId = id;
	}
	
	@Override
	public short getType() {		
		return MessageType.GM_DB_ENTITY_OPERATION_INFO;
	}

	public DBEntityOperationInfo[] getOperationInfos() {
		return operationInfos;
	}

	public void setOperationInfos(DBEntityOperationInfo[] operationInfos) {
		this.operationInfos = operationInfos;
	}

	public Map<String, Integer> getDbQueryTimes() {
		return dbQueryTimes;
	}

	public void setDbQueryTimes(Map<String, Integer> dbQueryTimes) {
		this.dbQueryTimes = dbQueryTimes;
	}

	@Override
	protected boolean readImpl() {
		contextId = readLong();
		int count = readInteger();
		count = count < 0 ? 0 : count;
		operationInfos = new DBEntityOperationInfo[count];
		for(int i=0;i<count;i++){
			operationInfos[i] = new DBEntityOperationInfo();
			operationInfos[i].setClassName(readString());
			operationInfos[i].setUpdateCount(readInteger());
			operationInfos[i].setInsertCount(readInteger());
			operationInfos[i].setDeleteCount(readInteger());
			operationInfos[i].setGetCount(readInteger());
			operationInfos[i].setQueryCount(readInteger());
		}
		count = readInteger();
		count = count < 0 ? 0 : count;
		dbQueryTimes = new HashMap<String, Integer>();
		for(int i=0;i<count;i++){
			String key = readString();
			Integer value = readInteger();
			dbQueryTimes.put(key, value);
		}
		return true;
	}

	@Override
	protected boolean writeImpl() {
		writeLong(contextId);
		int count = operationInfos==null ? 0 : operationInfos.length;
		writeInteger(count);
		if(count > 0){
			for(int i=0;i<operationInfos.length;i++){				
				writeString(operationInfos[i].getClassName());
				writeInteger(operationInfos[i].getUpdateCount());
				writeInteger(operationInfos[i].getInsertCount());
				writeInteger(operationInfos[i].getDeleteCount());
				writeInteger(operationInfos[i].getGetCount());
				writeInteger(operationInfos[i].getQueryCount());
			}
		}
		count = dbQueryTimes==null ? 0 : dbQueryTimes.size();
		writeInteger(count);
		if(count>0){
			for(String key : dbQueryTimes.keySet()){
				writeString(key);
				writeInteger(dbQueryTimes.get(key));
			}
		}
		return true;
	}
	
}
