package com.hifun.soul.gameserver.manage.handler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gamedb.monitor.DBOperationType;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.manage.msg.GMDBEntityOperationInfo;
import com.hifun.soul.gameserver.manage.msg.MGDBEntityOperationInfo;
import com.hifun.soul.gameserver.manage.runtime.DBEntityOperationInfo;

@Component
public class MGDBEntityOperationInfoHandler implements IMessageHandlerWithType<MGDBEntityOperationInfo> {

	@Override
	public short getMessageType() {		
		return MessageType.MG_DB_ENTITY_OPERATION_INFO;
	}

	@Override
	public void execute(MGDBEntityOperationInfo message) {
		Map<Class<?>, Map<DBOperationType, Integer>> allInfos = GameServerAssist.getDbServiceManager().getEntityDBOperationInfos();
		List<DBEntityOperationInfo> operations = new ArrayList<DBEntityOperationInfo>();
		for(Class<?> clasz : allInfos.keySet()){
			Map<DBOperationType, Integer> entityOperate = allInfos.get(clasz);
			DBEntityOperationInfo info = new DBEntityOperationInfo();
			info.setClassName(clasz.getSimpleName());
			if(entityOperate.containsKey(DBOperationType.UPDATE)){
				info.setUpdateCount(entityOperate.get(DBOperationType.UPDATE));
			}
			if(entityOperate.containsKey(DBOperationType.INSERT)){
				info.setInsertCount(entityOperate.get(DBOperationType.INSERT));
			}
			if(entityOperate.containsKey(DBOperationType.DELETE)){
				info.setDeleteCount(entityOperate.get(DBOperationType.DELETE));
			}
			if(entityOperate.containsKey(DBOperationType.GET)){
				info.setGetCount(entityOperate.get(DBOperationType.GET));
			}
			if(entityOperate.containsKey(DBOperationType.QUERY)){
				info.setQueryCount(entityOperate.get(DBOperationType.QUERY));
			}
			operations.add(info);
		}
		Map<String,Integer> dbQueryInfo = GameServerAssist.getDbServiceManager().getDbQueryTimesInfo();		
		GMDBEntityOperationInfo gmMsg = new GMDBEntityOperationInfo();
		gmMsg.setContextId(message.getContextId());
		gmMsg.setOperationInfos(operations.toArray(new DBEntityOperationInfo[0]));
		gmMsg.setDbQueryTimes(dbQueryInfo);
		message.getSession().write(gmMsg);
	}

}
