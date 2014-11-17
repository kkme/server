package com.hifun.soul.gameserver.chat.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hifun.soul.common.LogReasons.ChatLogReason;
import com.hifun.soul.common.LogReasons.GmCommandLogReason;
import com.hifun.soul.core.command.ICommand;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gamedb.PlayerPermissionType;
import com.hifun.soul.gameserver.chat.ChatType;
import com.hifun.soul.gameserver.chat.IChatStrategy;
import com.hifun.soul.gameserver.chat.msg.CGChatMsg;
import com.hifun.soul.gameserver.chat.service.ChatService;
import com.hifun.soul.gameserver.common.log.LogService;
import com.hifun.soul.gameserver.gm.service.GmCommandService;
import com.hifun.soul.gameserver.player.Player;
import com.hifun.soul.gameserver.startup.MinaGameClientSession;

@Component
public class CGChatMsgHandler implements IMessageHandlerWithType<CGChatMsg> {

	@Autowired
	private GmCommandService gmCommandService;
	@Autowired
	private ChatService chatService;
	@Autowired
	private LogService logService;
	
	@Override
	public short getMessageType() {
		return MessageType.CG_CHAT_MSG;
	}

	@Override
	public void execute(CGChatMsg message) {
		String msgContent = message.getContent();
		if (null == msgContent
				|| (msgContent = msgContent.trim()).length() == 0) {
			return;
		}
		
		// 判断用户是否有gm权限
		Player player = message.getSession().getPlayer();
		if(player.getPermissionType() == PlayerPermissionType.GM_PLAYER){
			if(msgContent.startsWith(ICommand.CMD_PREFIX)){
				gmCommandService.getProcessor().execute((MinaGameClientSession) player.getSession(), msgContent);
				
				// gm命令日志
				logService.sendGmCommandLog(player.getHuman(), GmCommandLogReason.REASON_VALID_USE_GMCMD, "", msgContent);
				//editby: crazyjohn 如果是gm命令, 则直接返回, 不要广播出去了;
				return;
			}
		}
		
		// 执行消息
		IChatStrategy strategy = chatService.getChatStrategy(ChatType.indexOf(message.getChatType()));
		if(strategy != null){
			strategy.execute(message);
		}
		
		// 发送聊天日志
		logService.sendChatLog(player.getHuman(), 
				ChatLogReason.REASON_CHAT_COMMON, 
				"", 
				message.getDestPassportId(), 
				message.getDestRoleName(), 
				message.getChatType(), 
				msgContent);
		
	}

}
