package com.hifun.soul.gameserver.mine.handler;

import org.springframework.stereotype.Component;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.guide.GuideType;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.mine.msg.CGUpdateMinePannel;

/**
 * 更新采矿面板
 * 
 * @author magicstone
 * 
 */
@Component
public class CGUpdateMinePannelHandler implements IMessageHandlerWithType<CGUpdateMinePannel> {
	@Override
	public short getMessageType() {
		return MessageType.CG_UPDATE_MINE_PANNEL;
	}

	@Override
	public void execute(CGUpdateMinePannel message) {
		if (message.getPlayer() == null) {
			return;
		}
		Human human = message.getPlayer().getHuman();
		if (human == null) {
			return;
		}
		// 发送矿坑列表;
		human.getHumanMineManager().sendMineFieldListMessage();
		human.getHumanMineManager().sendUpdateMinePanelMessage();
		human.getHumanGuideManager().showGuide(GuideType.OPEN_MINE_PANEL.getIndex());
	}

}
