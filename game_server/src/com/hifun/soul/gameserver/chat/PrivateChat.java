package com.hifun.soul.gameserver.chat;

import com.hifun.soul.gameserver.chat.msg.CGChatMsg;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.player.Player;


/**
 * 私聊
 * @author magicstone
 */
public class PrivateChat extends AbstractChatStrategy {

	@Override
	protected int getChatInterval() {
		return GameServerAssist.getGameConstants().getPrivateChatInterval();
	}
	
	@Override
	protected int getChatLength() {
		return GameServerAssist.getGameConstants().getPrivateChatLength();
	}

	@Override
	protected String getContent(CGChatMsg msg) {
		String content = msg.getContent();
		if(content == null
				|| content.trim().length() == 0){
			return null;
		}
		
		if(content.startsWith(GameServerAssist.getGameConstants().getPrivateChatPrfix())){
			content = parsePrivateChat(content);
		}
		else{
			return null;
		}
		
		return content;
	}

	@Override
	protected Player[] getDestPlayers(long passportId) {
		Player player = GameServerAssist.getGameWorld().getPlayerByPassportId(passportId);
		if(player != null){
			Player[] players = new Player[1];
			players[0] = player;
			
			return players;
		}
		
		return null;
	}

	@Override
	protected ChatType getChatType() {
		return ChatType.PRIVATE;
	}
	
	/**
	 * 从私聊的内容中解析出聊天的内容
	 * 
	 * @param chatContent
	 * @return
	 */
	private String parsePrivateChat(final String chatContent) {
		// 检查命令的前缀
		final String _content = chatContent
				.startsWith(GameServerAssist.getGameConstants().getPrivateChatPrfix()) ? chatContent
				.substring(GameServerAssist.getGameConstants().getPrivateChatPrfix().length()) : null;
		if (_content == null) {
			return null;
		}

		final int _size = _content.length();
		int _index = -1;
		for (int i = 0; i < _size; i++) {
			final char _c = _content.charAt(i);
			if (_c == ' ' || _c == '　') {
				// 当遇到半角空格或者全角空格时,私聊的第一段已经解析到了
				_index = i;
				break;
			}
		}

		if (_index < 0) {
			// 未找到私聊的目标名称
			return null;
		}

		// 继续忽略所有的前导空格
		for (int i = _index + 1; i < _size; i++) {
			final char _c = _content.charAt(i);
			if (_c != ' ' && _c != '　') {
				_index = i;
				break;
			}
		}

		final String _toContent = _content.substring(
				Math.min(_index, _content.length())).trim();

		if (_toContent.isEmpty()) {
			return null;
		}
		return _toContent;
	}

}
