package com.hifun.soul.gameserver.chat;

import com.hifun.soul.common.constants.LangConstants;
import com.hifun.soul.core.command.ICommand;
import com.hifun.soul.core.util.TimeUtils;
import com.hifun.soul.gameserver.chat.msg.CGChatMsg;
import com.hifun.soul.gameserver.chat.msg.GCChatMsg;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.player.Player;


/**
 * 聊天抽象处理策略
 * 
 * @author magicstone
 *
 */
public abstract class AbstractChatStrategy implements IChatStrategy{
	@Override
	public void execute(CGChatMsg msg) {
		Player player = msg.getSession().getPlayer();
		// 发言时间间隔
		long lastChatTime = player.getHuman().getLastChatTime();
		if(lastChatTime < 0){
			lastChatTime = 0;
		}
		if(!isTimeOver(lastChatTime)){
			player.getHuman().sendGenericMessage(LangConstants.CHAT_CD);
			return;
		}
		// 判断是否还有其他发言限制
		if(!canChat(player.getHuman())){
			return;
		}
		// 发言内容
		String content = getContent(msg);
		if(content ==null ||
				content.trim().length() == 0){
			return;
		}
		// 信息长度
		content = checkContentLength(content);
		// 特殊处理: 以感叹号开头都如此处理
		if(content.startsWith(ICommand.CMD_PREFIX)){
			content = "!****";
		}
		// 发言内容过滤
		content = filterContent(content);
		// 消息接收对象
		Player[] players = getDestPlayers(msg.getDestPassportId());
		// 生成消息
		GCChatMsg gCChatMsg = genGCCHatMsg(msg,content);
		// 消息发送
		for(Player tempPlayer : players){
			tempPlayer.sendMessage(gCChatMsg);
		}
		
		player.getHuman().setLastChatTime(GameServerAssist.getSystemTimeService().now());
	}
	
	/**
	 * 是否到聊天时间间隔
	 * 
	 * @param lastChatTime
	 * @return
	 */
	private boolean isTimeOver(long lastChatTime){
		return GameServerAssist.getSystemTimeService().now() - lastChatTime >= (getChatInterval()*TimeUtils.SECOND);
	}
	
	/**
	 * 获取聊天的间隔
	 * 
	 * @return
	 */
	protected abstract int getChatInterval();
	
	/**
	 * 获取聊天内容
	 * 
	 * @param msg
	 * @return
	 */
	protected String getContent(CGChatMsg msg){
		return msg.getContent();
	}
	
	/**
	 * 根据具体消息的长度限制截取内容
	 * 
	 * @param content
	 * @return
	 */
	private String checkContentLength(String content){
		if (getChatLength() < content.length()) {
			content = content.substring(0, getChatLength());
		}
		return content;
	}
	
	/**
	 * 获取聊天长度限制
	 * 
	 * @return
	 */
	protected abstract int getChatLength();
	
	/**
	 * 过滤聊天内容
	 * 
	 * @param content
	 * @return
	 */
	private String filterContent(String content){
		String msgContent = GameServerAssist.getWordFilterService().filterHtmlTag(content);
		boolean hasDirtyWords = GameServerAssist.getWordFilterService().containKeywords(msgContent);
		if (hasDirtyWords) {
			msgContent = GameServerAssist.getWordFilterService().filter(msgContent);
		}
		return msgContent;
	}
	
	/**
	 * 获取消息接收玩家列表
	 * 
	 * @return
	 */
	protected abstract Player[] getDestPlayers(long destPassportId);
	
	/**
	 * 生成GCChatMsg
	 * 
	 * @return
	 */
	private GCChatMsg genGCCHatMsg(CGChatMsg msg,String content) {
		GCChatMsg gcChatMsg = new GCChatMsg();
		
		gcChatMsg.setChatType(getChatType().getIndex());
		gcChatMsg.setContent(content);
		gcChatMsg.setDestRoleName(msg.getDestRoleName());
		gcChatMsg.setDestPassportId(msg.getDestPassportId());
		gcChatMsg.setFromRoleName(msg.getFromRoleName());
		gcChatMsg.setFromPassportId(msg.getFromPassportId());
		
		return gcChatMsg;
	}
	
	/**
	 * 获取聊天类型
	 * 
	 * @return
	 */
	protected abstract ChatType getChatType();
	
	/**
	 * 是否可以聊天
	 * 
	 * @return
	 */
	protected boolean canChat(Human human){
		return true;
	}
	
}
