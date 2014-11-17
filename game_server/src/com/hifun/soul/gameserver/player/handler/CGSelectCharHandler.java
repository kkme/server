package com.hifun.soul.gameserver.player.handler;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hifun.soul.common.auth.LoginChar;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gamedb.callback.IMainThreadDBCallback;
import com.hifun.soul.gamedb.entity.HumanEntity;
import com.hifun.soul.gamedb.service.IDataService;
import com.hifun.soul.gameserver.guide.GuideType;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.logger.Loggers;
import com.hifun.soul.gameserver.player.Player;
import com.hifun.soul.gameserver.player.msg.CGSelectChar;
import com.hifun.soul.gameserver.player.msg.GCDirectToAreaScene;
import com.hifun.soul.gameserver.player.msg.GCEnterScene;
import com.hifun.soul.gameserver.player.state.PlayerState;

/**
 * 客户端选取角色逻辑;
 * 
 * @author crazyjohn
 * 
 */
@Component
public class CGSelectCharHandler implements
		IMessageHandlerWithType<CGSelectChar> {
	private static Logger logger = Loggers.HUMAN_LOGIN_LOGGER();
	/** 数据服务 */
	@Autowired
	private IDataService dataService;

	@Override
	public short getMessageType() {
		return MessageType.CG_SELECT_CHAR;
	}

	@Override
	public void execute(CGSelectChar message) {
		// 去DB加载指定角色的详细信息; 加载成功的话给client发送GCEnterScene
		final Player player = message.getPlayer();
		if (player == null) {
			if (logger.isErrorEnabled()) {
				logger.error(String.format(
						"Player can not be null, session: %s",
						message.getSession()));
			}
			return;
		}
		// 绑定玩家和角色
		final Human human = new Human(player);
		player.setHuman(human);

		// 获取指定索引的loginChar
		LoginChar currentChar = player.getLoginCharByIndex(message
				.getCharIndex());
		if (currentChar == null) {
			if (logger.isErrorEnabled()) {
				logger.error(String.format(
						"CurrentLoginChar can not be null, session: %s",
						message.getSession()));
			}
			return;
		}
		player.setCurrentLoginChar(currentChar);

		// 从数据库加载角色信息, 并进行反序列化成Human对象
		dataService.get(currentChar.getHumanGuid(), HumanEntity.class,
				new IMainThreadDBCallback<HumanEntity>() {

					@Override
					public void onSucceed(HumanEntity result) {
						if (result == null) {
							return;
						}

						human.onLoad(result);
						// 切换状态
						if (!player.canTransferStateTo(PlayerState.ENTERING)) {
							logger.error(String
									.format("Can not transfer player state from %s to %s",
											player.getState(),
											PlayerState.ENTERING));
							return;
						}
						player.transferStateTo(PlayerState.ENTERING);
						// 下发第一次进入游戏新手引导
						sendFirstEnterGame(human);
						// 通知客户端可以进场景了
						GCEnterScene enterSceneMsg = new GCEnterScene();
						player.sendMessage(enterSceneMsg);
					}

					@Override
					public void onFailed(String errorMsg) {
						// TODO Auto-generated method stub

					}

				});
	}
	
	private void sendFirstEnterGame(Human human) {
		if (!human.getHumanGuideManager().isFinishedGuide(GuideType.ENTER_GAME.getIndex())) {
			GCDirectToAreaScene toAreaScene = new GCDirectToAreaScene();
			human.sendMessage(toAreaScene);
		}
		
	}


}
