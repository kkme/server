package com.hifun.soul.gameserver.prison.handler;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.hifun.soul.common.constants.LangConstants;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.legion.Legion;
import com.hifun.soul.gameserver.prison.IdentityType;
import com.hifun.soul.gameserver.prison.PrisonResultType;
import com.hifun.soul.gameserver.prison.Prisoner;
import com.hifun.soul.gameserver.prison.converter.PrisonerToInfoConverter;
import com.hifun.soul.gameserver.prison.manager.GlobalPrisonManager;
import com.hifun.soul.gameserver.prison.msg.CGSlaveShowSosTab;
import com.hifun.soul.gameserver.prison.msg.GCSlaveShowSosTab;
import com.hifun.soul.gameserver.prison.msg.HelperInfo;

/**
 * 展示求救列表
 * 
 * @author yandajun
 * 
 */
@Component
public class CGSlaveShowSosTabHandler implements
		IMessageHandlerWithType<CGSlaveShowSosTab> {

	@Override
	public short getMessageType() {
		return MessageType.CG_SLAVE_SHOW_SOS_TAB;
	}

	@Override
	public void execute(CGSlaveShowSosTab message) {
		Human human = message.getPlayer().getHuman();
		// 功能是否开启
		if (!GameServerAssist.getGameFuncService().gameFuncIsOpen(human,
				GameFuncType.PRISON, true)) {
			return;
		}
		// 判断身份是否合法
		GlobalPrisonManager manager = GameServerAssist.getGlobalPrisonManager();
		Prisoner slave = manager.getPrisoner(human.getHumanGuid());
		if (slave == null) {
			return;
		}
		if (slave.getIdentityType() != IdentityType.SLAVE.getIndex()) {
			human.sendErrorMessage(LangConstants.PRISON_IDENTITY_ILLEGAL);
			return;
		}
		// 军团内在线的非奴隶身份玩家
		GCSlaveShowSosTab msg = new GCSlaveShowSosTab();
		Legion legion = GameServerAssist.getGlobalLegionManager().getLegion(
				human.getHumanGuid());
		if (legion == null) {
			msg.setResult(PrisonResultType.NOT_JOINED_LEGION.getIndex());
			msg.setHelperInfoList(new HelperInfo[0]);
			human.sendMessage(msg);
			return;
		}
		List<Prisoner> arrestorList = GameServerAssist.getGlobalPrisonManager()
				.getLegionArrestorList(legion);
		// 筛选出在线的
		List<Prisoner> onlineArrestorList = new ArrayList<Prisoner>();
		for (Prisoner arrestor : arrestorList) {
			Human h = GameServerAssist.getGameWorld().getSceneHumanManager()
					.getHumanByGuid(arrestor.getHumanId());
			if (h != null) {
				onlineArrestorList.add(arrestor);
			}
		}
		if (onlineArrestorList.size() == 0) {
			msg.setResult(PrisonResultType.NO_MEMBER_HELP.getIndex());
			msg.setHelperInfoList(new HelperInfo[0]);
			human.sendMessage(msg);
			return;
		}
		// 数量限制
		int size = onlineArrestorList.size();
		int limit = GameServerAssist.getGameConstants()
				.getForHelpMemberNumLimit();
		for (int i = size - 1; onlineArrestorList.size() > limit; i--) {
			onlineArrestorList.remove(i);
		}
		msg.setResult(PrisonResultType.SUCCESS.getIndex());
		HelperInfo[] helperInfos = PrisonerToInfoConverter
				.convertToHelperInfoArray(onlineArrestorList);
		for (HelperInfo info : helperInfos) {
			info.setIsForHelped(true);
		}
		msg.setHelperInfoList(PrisonerToInfoConverter
				.convertToHelperInfoArray(onlineArrestorList));
		human.sendMessage(msg);
	}
}
