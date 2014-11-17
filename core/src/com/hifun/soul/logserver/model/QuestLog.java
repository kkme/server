package com.hifun.soul.logserver.model;
import com.hifun.soul.logserver.MessageType;
import com.hifun.soul.logserver.BaseLogMessage;

/**
 * This is an auto generated source,please don't modify it.
 */
public class QuestLog extends BaseLogMessage{
       private int questId;
    
    public QuestLog() {    	
    }

    public QuestLog(
                   long logTime,
                   int regionId,
                   int serverId,
                   long accountId,
                   String accountName,
                   long charId,
                   String charName,
                   int level,
                   int allianceId,
                   int vipLevel,
                   int reason,
                   String param,
			int questId            ) {
        super(MessageType.LOG_QUEST_RECORD,logTime,regionId,serverId,accountId,accountName,charId,charName,level,allianceId,vipLevel,reason,param);
            this.questId =  questId;
    }

       public int getQuestId() {
	       return questId;
       }
        
       public void setQuestId(int questId) {
	       this.questId = questId;
       }
    
    @Override
    protected boolean readLogContent() {
	        questId =  readInt();
        return true;
    }

    @Override
    protected boolean writeLogContent() {
	        writeInt(questId);
        return true;
    }
    
    @Override
    public short getType() {
        return MessageType.LOG_QUEST_RECORD;
    }

    @Override
    public String getTypeName() {
        return "LOG_QUEST_RECORD";
    }
}