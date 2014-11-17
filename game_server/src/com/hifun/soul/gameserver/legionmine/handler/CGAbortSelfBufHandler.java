package com.hifun.soul.gameserver.legionmine.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.legionmine.LegionMineMember;
import com.hifun.soul.gameserver.legionmine.SelfBuf;
import com.hifun.soul.gameserver.legionmine.enums.OperateType;
import com.hifun.soul.gameserver.legionmine.enums.SelfBufType;
import com.hifun.soul.gameserver.legionmine.manager.GlobalLegionMineWarManager;
import com.hifun.soul.gameserver.legionmine.msg.CGAbortSelfBuf;
import com.hifun.soul.gameserver.legionmine.msg.GCAbortSelfBuf;
import com.hifun.soul.gameserver.legionmine.msg.GCUpdateLegionMineList;
import com.hifun.soul.gameserver.legionmine.msg.info.LegionMineInfo;

@Component
public class CGAbortSelfBufHandler implements
		IMessageHandlerWithType<CGAbortSelfBuf> {
	@Autowired
	private GlobalLegionMineWarManager globalLegionMineWarManager;

	@Override
	public short getMessageType() {
		return MessageType.CG_ABORT_SELF_BUF;
	}

	@Override
	public void execute(CGAbortSelfBuf message) {
		Human human = message.getPlayer().getHuman();
		LegionMineMember mineMember = globalLegionMineWarManager
				.getJoinLegionMineMember(human.getHumanGuid());
		if (mineMember == null) {
			return;
		}
		SelfBufType bufType = SelfBufType.indexOf(message.getSelfBufType());
		if (bufType == null || bufType == SelfBufType.DISTURB) {
			return;
		}
		SelfBuf buf = mineMember.getSelfBuf(bufType);
		if (buf == null || !buf.getUsing()) {
			return;
		}
		buf.setUsing(false);
		// 发送矿坑列表
		GCUpdateLegionMineList mineListMsg = new GCUpdateLegionMineList();
		mineListMsg.setMineInfos(globalLegionMineWarManager
				.generateLegionMineInfos(mineMember, OperateType.COMMON)
				.toArray(new LegionMineInfo[0]));
		human.sendMessage(mineListMsg);
		// 返回消息
		GCAbortSelfBuf msg = new GCAbortSelfBuf();
		msg.setSelfBufType(bufType.getIndex());
		msg.setUsing(false);
		human.sendMessage(msg);
	}

}
