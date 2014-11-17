package com.hifun.soul.gameserver.godsoul.handler;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.function.service.GameFuncService;
import com.hifun.soul.gameserver.godsoul.info.GodsoulBuffInfo;
import com.hifun.soul.gameserver.godsoul.info.MagicPaperInfo;
import com.hifun.soul.gameserver.godsoul.msg.CGShowMagicPaperTab;
import com.hifun.soul.gameserver.godsoul.msg.GCShowMagicPaperTab;
import com.hifun.soul.gameserver.human.Human;

@Component
public class CGShowMagicPaperTabHandler implements
		IMessageHandlerWithType<CGShowMagicPaperTab> {
	@Autowired
	private GameFuncService gameFuncService;

	@Override
	public short getMessageType() {
		return MessageType.CG_SHOW_MAGIC_PAPER_TAB;
	}

	@Override
	public void execute(CGShowMagicPaperTab message) {
		if (message.getPlayer() == null) {
			return;
		}
		Human human = message.getPlayer().getHuman();
		if (human == null) {
			return;
		}
		// 判断功能是否开放
		if (!gameFuncService.gameFuncIsOpen(human, GameFuncType.GODSOUL, true)) {
			return;
		}
		GCShowMagicPaperTab msg = new GCShowMagicPaperTab();
		List<MagicPaperInfo> paperInfoList = human.getHumanGodsoulManager()
				.generateMagicPaperInfoList();
		msg.setMagicPaperInfos(paperInfoList.toArray(new MagicPaperInfo[0]));
		List<GodsoulBuffInfo> buffInfoList = human.getHumanGodsoulManager()
				.generateMagicPaperBuffInfoList();
		msg.setGodsoulBuffInfos(buffInfoList.toArray(new GodsoulBuffInfo[0]));
		human.sendMessage(msg);
	}

}
