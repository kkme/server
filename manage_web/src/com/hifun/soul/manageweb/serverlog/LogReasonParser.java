package com.hifun.soul.manageweb.serverlog;

import java.util.HashMap;
import java.util.Map;

import com.hifun.soul.common.LogReasons.*;
import com.hifun.soul.logserver.MessageType;

public class LogReasonParser {
	/**
	 * 获取日志类型的描述字段
	 * @param logReason
	 * @return
	 */
	public String getReasonDesc(ILogReason logReason){
		String reasonDesc="";
		try {
			reasonDesc = logReason.getClass().getDeclaredField(logReason.toString()).getAnnotation(ReasonDesc.class).value();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		}
		return reasonDesc;
	}
	
	public String getReasonDesc(int type,int reasonId){
		String reasonDesc = "";
		LogReasonKey key = new LogReasonKey(type,reasonId);
		if(logMaps.containsKey(key)){
			reasonDesc = getReasonDesc(logMaps.get(key));
		}
		return reasonDesc;
	}
	
	private static Map<LogReasonKey,ILogReason> logMaps = new HashMap<LogReasonKey,ILogReason>();
	
	public static LogReasonParser getInstance(){
		return parser;
	}
	
	private static LogReasonParser parser = new LogReasonParser();
	private LogReasonParser(){
		for(BasicPlayerLogReason reason : BasicPlayerLogReason.values()){
			logMaps.put(new LogReasonKey(MessageType.LOG_BASICPLAYER_RECORD, reason.reason),reason);
		}
		for(BattleLogReason reason : BattleLogReason.values()){
			logMaps.put(new LogReasonKey(MessageType.LOG_BATTLE_RECORD, reason.reason),reason);
		}
		for(ChatLogReason reason : ChatLogReason.values()){
			logMaps.put(new LogReasonKey(MessageType.LOG_CHAT_RECORD, reason.reason),reason);
		}
		for(FriendLogReason reason : FriendLogReason.values()){
			logMaps.put(new LogReasonKey(MessageType.LOG_FRIEND_RECORD, reason.reason),reason);
		}
		for(GmCommandLogReason reason : GmCommandLogReason.values()){
			logMaps.put(new LogReasonKey(MessageType.LOG_GMCOMMAND_RECORD, reason.reason),reason);
		}
		for(HoroscopeLogReason reason : HoroscopeLogReason.values()){
			logMaps.put(new LogReasonKey(MessageType.LOG_HOROSCOPE_RECORD, reason.reason),reason);
		}
		for(ItemLogReason reason : ItemLogReason.values()){
			logMaps.put(new LogReasonKey(MessageType.LOG_ITEM_RECORD, reason.reason),reason);
		}
		for(MoneyLogReason reason : MoneyLogReason.values()){
			logMaps.put(new LogReasonKey(MessageType.LOG_MONEY_RECORD, reason.reason),reason);
		}
		for(OnlineTimeLogReason reason : OnlineTimeLogReason.values()){
			logMaps.put(new LogReasonKey(MessageType.LOG_ONLINETIME_RECORD, reason.reason),reason);
		}
		for(PropertyLogReason reason : PropertyLogReason.values()){
			logMaps.put(new LogReasonKey(MessageType.LOG_PROPERTY_RECORD, reason.reason),reason);
		}
		for(QuestLogReason reason : QuestLogReason.values()){
			logMaps.put(new LogReasonKey(MessageType.LOG_QUEST_RECORD, reason.reason),reason);
		}
		for(RechargeLogReason reason : RechargeLogReason.values()){
			logMaps.put(new LogReasonKey(MessageType.LOG_RECHARGE_RECORD, reason.reason),reason);
		}
		for(EnergyLogReason reason : EnergyLogReason.values()){
			logMaps.put(new LogReasonKey(MessageType.LOG_ENERGY_RECORD, reason.reason),reason);
		}
		for(ExperienceLogReason reason : ExperienceLogReason.values()){
			logMaps.put(new LogReasonKey(MessageType.LOG_EXPERIENCE_RECORD, reason.reason),reason);
		}
		for(HonourLogReason reason : HonourLogReason.values()){
			logMaps.put(new LogReasonKey(MessageType.LOG_HONOUR_RECORD, reason.reason),reason);
		}
		for(TechPointLogReason reason : TechPointLogReason.values()){
			logMaps.put(new LogReasonKey(MessageType.LOG_TECHPOINT_RECORD, reason.reason),reason);
		}
		for(SkillPointLogReason reason : SkillPointLogReason.values()){
			logMaps.put(new LogReasonKey(MessageType.LOG_SKILLPOINT_RECORD, reason.reason),reason);
		}
	}
	
	class LogReasonKey{
		
		public int logType;
		public int reasonId;
		public LogReasonKey(int type,int id){
			logType = type;
			reasonId = id;
		}
		
		@Override
		public int hashCode(){
			return logType*10000+reasonId;
		}
		
		@Override
		public boolean equals(Object obj) {
			if (obj instanceof LogReasonKey) {
				return this.hashCode() == obj.hashCode();
			} else {
				return false;
			}
		}
		
		public String toString(){
			return "logType:"+logType+",reasonId:"+reasonId;
		}
	}
}
