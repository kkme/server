package com.hifun.soul.gameserver.recharge.handler;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.recharge.RechargeActivityState;
import com.hifun.soul.gameserver.recharge.RechargeActivityType;
import com.hifun.soul.gameserver.recharge.msg.CGOpenRechargeActivityPanel;
import com.hifun.soul.gameserver.recharge.msg.GCOpenRechargeActivityPanel;

@Component
public class CGOpenRechargeActivityPanelHandler implements
		IMessageHandlerWithType<CGOpenRechargeActivityPanel> {

	@Override
	public short getMessageType() {
		return MessageType.CG_OPEN_RECHARGE_ACTIVITY_PANEL;
	}

	@Override
	public void execute(CGOpenRechargeActivityPanel message) {
		Human human = message.getPlayer().getHuman();
		// 查出已开启的活动类型
		GCOpenRechargeActivityPanel msg = new GCOpenRechargeActivityPanel();
		List<Integer> activityTypeList = new ArrayList<Integer>();
		for (RechargeActivityType activityType : RechargeActivityType.values()) {
			if (activityType != RechargeActivityType.FIRST_RECHARGE
					&& GameServerAssist.getRechargeTemplateManager()
							.getRechargeActivityState(activityType) != RechargeActivityState.PRE_TIME) {
				activityTypeList.add(activityType.getIndex());
			}
		}
		int[] activityTypes = new int[activityTypeList.size()];
		for (int i = 0; i < activityTypes.length; i++) {
			activityTypes[i] = activityTypeList.get(i).intValue();
		}
		msg.setRechargeActivityTypes(activityTypes);
		human.sendMessage(msg);
	}

}
