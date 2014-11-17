package com.hifun.soul.logserver.model;
import com.hifun.soul.logserver.MessageType;
import com.hifun.soul.logserver.BaseLogMessage;

/**
 * This is an auto generated source,please don't modify it.
 */
public class BattleLog extends BaseLogMessage{
       private int stageId;
       private int result;
       private String itemIds;
    
    public BattleLog() {    	
    }

    public BattleLog(
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
			int stageId,			int result,			String itemIds            ) {
        super(MessageType.LOG_BATTLE_RECORD,logTime,regionId,serverId,accountId,accountName,charId,charName,level,allianceId,vipLevel,reason,param);
            this.stageId =  stageId;
            this.result =  result;
            this.itemIds =  itemIds;
    }

       public int getStageId() {
	       return stageId;
       }
       public int getResult() {
	       return result;
       }
       public String getItemIds() {
	       return itemIds;
       }
        
       public void setStageId(int stageId) {
	       this.stageId = stageId;
       }
       public void setResult(int result) {
	       this.result = result;
       }
       public void setItemIds(String itemIds) {
	       this.itemIds = itemIds;
       }
    
    @Override
    protected boolean readLogContent() {
	        stageId =  readInt();
	        result =  readInt();
	        itemIds =  readString();
        return true;
    }

    @Override
    protected boolean writeLogContent() {
	        writeInt(stageId);
	        writeInt(result);
	        writeString(itemIds);
        return true;
    }
    
    @Override
    public short getType() {
        return MessageType.LOG_BATTLE_RECORD;
    }

    @Override
    public String getTypeName() {
        return "LOG_BATTLE_RECORD";
    }
}