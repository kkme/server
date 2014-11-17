package com.hifun.soul.gameserver.nostrum.handler;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.nostrum.Nostrum;
import com.hifun.soul.gameserver.nostrum.msg.CGOpenNostrumPanel;
import com.hifun.soul.gameserver.nostrum.msg.GCOpenNostrumPanel;
import com.hifun.soul.gameserver.nostrum.msg.NostrumInfo;

@Component
public class CGOpenNostrumPanelHandler implements
		IMessageHandlerWithType<CGOpenNostrumPanel> {

	@Override
	public short getMessageType() {
		return MessageType.CG_OPEN_NOSTRUM_PANEL;
	}

	@Override
	public void execute(CGOpenNostrumPanel message) {
		Human human = message.getPlayer().getHuman();
		GCOpenNostrumPanel msg = new GCOpenNostrumPanel();
		List<Nostrum> nostrumList = human.getHumanNostrumManager()
				.getNostrumList();
		List<NostrumInfo> nostrumInfoList = new ArrayList<NostrumInfo>();
		for (Nostrum nostrum : nostrumList) {
			NostrumInfo nostrumInfo = new NostrumInfo();
			nostrumInfo.setPropertyId(nostrum.getPropertyId());
			nostrumInfo.setPropertyAmendRate(nostrum.getPropertyAmendRate());
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm");
			String endTime = format.format(new Date(nostrum.getEndTime()));
			nostrumInfo.setEndTime(endTime);
			nostrumInfoList.add(nostrumInfo);
		}
		msg.setNostrums(nostrumInfoList.toArray(new NostrumInfo[0]));
		human.sendMessage(msg);
	}

}
