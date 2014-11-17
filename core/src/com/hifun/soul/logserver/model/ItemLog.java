package com.hifun.soul.logserver.model;
import com.hifun.soul.logserver.MessageType;
import com.hifun.soul.logserver.BaseLogMessage;

/**
 * This is an auto generated source,please don't modify it.
 */
public class ItemLog extends BaseLogMessage{
       private int bagType;
       private int bagIndex;
       private int templateId;
       private String itemUUID;
       private int count;
    
    public ItemLog() {    	
    }

    public ItemLog(
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
			int bagType,			int bagIndex,			int templateId,			String itemUUID,			int count            ) {
        super(MessageType.LOG_ITEM_RECORD,logTime,regionId,serverId,accountId,accountName,charId,charName,level,allianceId,vipLevel,reason,param);
            this.bagType =  bagType;
            this.bagIndex =  bagIndex;
            this.templateId =  templateId;
            this.itemUUID =  itemUUID;
            this.count =  count;
    }

       public int getBagType() {
	       return bagType;
       }
       public int getBagIndex() {
	       return bagIndex;
       }
       public int getTemplateId() {
	       return templateId;
       }
       public String getItemUUID() {
	       return itemUUID;
       }
       public int getCount() {
	       return count;
       }
        
       public void setBagType(int bagType) {
	       this.bagType = bagType;
       }
       public void setBagIndex(int bagIndex) {
	       this.bagIndex = bagIndex;
       }
       public void setTemplateId(int templateId) {
	       this.templateId = templateId;
       }
       public void setItemUUID(String itemUUID) {
	       this.itemUUID = itemUUID;
       }
       public void setCount(int count) {
	       this.count = count;
       }
    
    @Override
    protected boolean readLogContent() {
	        bagType =  readInt();
	        bagIndex =  readInt();
	        templateId =  readInt();
	        itemUUID =  readString();
	        count =  readInt();
        return true;
    }

    @Override
    protected boolean writeLogContent() {
	        writeInt(bagType);
	        writeInt(bagIndex);
	        writeInt(templateId);
	        writeString(itemUUID);
	        writeInt(count);
        return true;
    }
    
    @Override
    public short getType() {
        return MessageType.LOG_ITEM_RECORD;
    }

    @Override
    public String getTypeName() {
        return "LOG_ITEM_RECORD";
    }
}