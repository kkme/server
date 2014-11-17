package com.hifun.soul.logserver;

import org.apache.mina.common.ByteBuffer;

import com.hifun.soul.core.msg.IMessage;
import com.hifun.soul.core.msg.recognizer.IMessageRecognizer;
import com.hifun.soul.core.msg.MessageParseException;
	import com.hifun.soul.logserver.model.BasicPlayerLog;
	import com.hifun.soul.logserver.model.MoneyLog;
	import com.hifun.soul.logserver.model.ItemLog;
	import com.hifun.soul.logserver.model.PropertyLog;
	import com.hifun.soul.logserver.model.GmCommandLog;
	import com.hifun.soul.logserver.model.OnlineTimeLog;
	import com.hifun.soul.logserver.model.ChatLog;
	import com.hifun.soul.logserver.model.BattleLog;
	import com.hifun.soul.logserver.model.FriendLog;
	import com.hifun.soul.logserver.model.HoroscopeLog;
	import com.hifun.soul.logserver.model.QuestLog;
	import com.hifun.soul.logserver.model.RechargeLog;
	import com.hifun.soul.logserver.model.EnergyLog;
	import com.hifun.soul.logserver.model.HonourLog;
	import com.hifun.soul.logserver.model.TechPointLog;
	import com.hifun.soul.logserver.model.ExperienceLog;
	import com.hifun.soul.logserver.model.SkillPointLog;

/**
 * This is an auto generated source,please don't modify it.
 */
public class LogMessageRecognizer implements IMessageRecognizer {


	@Override
	public int recognizePacketLen(final ByteBuffer buff) {
		// 消息头还未读到,返回null
		if (buff.remaining() < IMessage.MIN_MESSAGE_LENGTH) {
			return -1;
		}
		return IMessage.Packet.peekPacketLength(buff);
	}


	public IMessage recognize(ByteBuffer buf) throws MessageParseException {
		/* 长度尚不足 */
		if (buf.remaining() < IMessage.MIN_MESSAGE_LENGTH) {
			return null;
		}

		/* 长度不足实际命令 */
		int len = buf.getShort(); // 预期长度
		if (buf.remaining() < len - 2) {
			return null;
		}

		short type = buf.getShort();
		return createMessage(type);
	}

	public static IMessage createMessage(int type)
			throws MessageParseException {

		switch (type) {
			case MessageType.LOG_BASICPLAYER_RECORD: {
				return new BasicPlayerLog();
			}
			case MessageType.LOG_MONEY_RECORD: {
				return new MoneyLog();
			}
			case MessageType.LOG_ITEM_RECORD: {
				return new ItemLog();
			}
			case MessageType.LOG_PROPERTY_RECORD: {
				return new PropertyLog();
			}
			case MessageType.LOG_GMCOMMAND_RECORD: {
				return new GmCommandLog();
			}
			case MessageType.LOG_ONLINETIME_RECORD: {
				return new OnlineTimeLog();
			}
			case MessageType.LOG_CHAT_RECORD: {
				return new ChatLog();
			}
			case MessageType.LOG_BATTLE_RECORD: {
				return new BattleLog();
			}
			case MessageType.LOG_FRIEND_RECORD: {
				return new FriendLog();
			}
			case MessageType.LOG_HOROSCOPE_RECORD: {
				return new HoroscopeLog();
			}
			case MessageType.LOG_QUEST_RECORD: {
				return new QuestLog();
			}
			case MessageType.LOG_RECHARGE_RECORD: {
				return new RechargeLog();
			}
			case MessageType.LOG_ENERGY_RECORD: {
				return new EnergyLog();
			}
			case MessageType.LOG_HONOUR_RECORD: {
				return new HonourLog();
			}
			case MessageType.LOG_TECHPOINT_RECORD: {
				return new TechPointLog();
			}
			case MessageType.LOG_EXPERIENCE_RECORD: {
				return new ExperienceLog();
			}
			case MessageType.LOG_SKILLPOINT_RECORD: {
				return new SkillPointLog();
			}

		default: {
			// TODO::考虑不要死机，可能要特殊处理
			throw new MessageParseException("Unrecognized message :" + type);
		}
		}

	}

}