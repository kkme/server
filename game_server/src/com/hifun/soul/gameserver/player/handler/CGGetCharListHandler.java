package com.hifun.soul.gameserver.player.handler;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hifun.soul.common.auth.LoginChar;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gamedb.agent.query.DataQueryConstants;
import com.hifun.soul.gamedb.callback.IMainThreadDBCallback;
import com.hifun.soul.gamedb.service.IDataService;
import com.hifun.soul.gameserver.player.Player;
import com.hifun.soul.gameserver.player.msg.CGGetCharList;
import com.hifun.soul.gameserver.player.msg.GCCharList;

/**
 * 客户端端请求角色列表处理;
 * 
 * @author crazyjohn
 * 
 */
@Component
public class CGGetCharListHandler implements
		IMessageHandlerWithType<CGGetCharList> {
	@Autowired
	private IDataService dataService;

	@Override
	public short getMessageType() {
		return MessageType.CG_GET_CHAR_LIST;
	}

	@Override
	public void execute(CGGetCharList message) {
		final Player player = message.getPlayer();
		if (player == null) {
			return;
		}
		// 查询玩家的所有角色
		dataService.query(DataQueryConstants.QUERY_ALL_CHARS,
				new String[] { "passportId" },
				new Object[] { player.getPassportId() },
				new IMainThreadDBCallback<List<?>>() {

					@Override
					public void onSucceed(List<?> result) {
						if (result == null) {
							return;
						}
						// 添加角色
						List<LoginChar> chars = new ArrayList<LoginChar>();
						for (int i = 0; i < result.size(); i++) {
							chars.add((LoginChar) result.get(i));
							player.addLoginChar((LoginChar) result.get(i));
						}
						GCCharList charListMsg = new GCCharList();
						charListMsg.setCharList(chars.toArray(new LoginChar[0]));
						player.sendMessage(charListMsg);

					}

					@Override
					public void onFailed(String errorMsg) {
						// TODO Auto-generated method stub

					}

				});

	}

}
