package com.hifun.soul.gameserver.function.manager;

import java.util.ArrayList;
import java.util.List;

import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.crystalexchange.msg.GCCrystalExchangeTimes;
import com.hifun.soul.gameserver.event.EventType;
import com.hifun.soul.gameserver.event.IEvent;
import com.hifun.soul.gameserver.event.IEventListener;
import com.hifun.soul.gameserver.event.VipLevelChangeEvent;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.human.ILoginManager;
import com.hifun.soul.gameserver.human.INotifyManager;
import com.hifun.soul.gameserver.human.msg.FuncNotifyInfo;
import com.hifun.soul.gameserver.human.msg.GCCloseGameFuncs;
import com.hifun.soul.gameserver.human.msg.GCFuncNotifies;
import com.hifun.soul.gameserver.human.msg.GCFuncNotify;
import com.hifun.soul.gameserver.human.msg.GCFunctionInfo;
import com.hifun.soul.gameserver.human.msg.GCFunctionInfo.FunctionInfo;
import com.hifun.soul.gameserver.human.msg.GCOpenGameFuncs;

public class HumanFunctionManager implements ILoginManager, IEventListener {
	private Human human;
	private List<Integer> openFuncs = new ArrayList<Integer>();

	public HumanFunctionManager(Human human) {
		this.human = human;

		human.registerLoginManager(this);
		human.getEventBus().addListener(EventType.LEVEL_UP_EVENT, this);
		human.getEventBus().addListener(EventType.VIP_LEVEL_CHANGE_EVENT, this);
	}

	public Human getHuman() {
		return human;
	}

	@Override
	public void onLogin() {
		// 下发功能等级信息
		GCFunctionInfo functionMsg = new GCFunctionInfo();
		List<FunctionInfo> functions = GameServerAssist.getGameFuncService()
				.getFunctionInfo();
		functionMsg.setFunctionInfos(functions.toArray(new FunctionInfo[0]));
		human.sendMessage(functionMsg);
		// 根据当前等级和vip等级发送所有开发的功能列表
		int[] openGameFuncs = GameServerAssist.getGameFuncService()
				.getOpenedGameFuncs(human);
		for (int funcId : openGameFuncs) {
			openFuncs.add(funcId);
		}
		// 检查兑换是否开启, 开启的话下发剩余兑换次数信息;
		boolean isExchangeOpen = false;
		for (int funcId : openGameFuncs) {
			if (funcId == GameFuncType.CRYSTAL_EXCHANGE.getIndex()) {
				isExchangeOpen = true;
				break;
			}
		}
		if (isExchangeOpen) {
			GCCrystalExchangeTimes timesMsg = new GCCrystalExchangeTimes();
			timesMsg.setLeftTimes(human.getHumanCrystalExchangeManager()
					.getLeftExchangeTimes());
			human.sendMessage(timesMsg);
		}
		sendOpenGameFuncs(openGameFuncs, false);
		// 发送功能提示里列表
		sendNotifies();
	}

	@Override
	public void onEvent(IEvent event) {
		List<Integer> closeFuncs = new ArrayList<Integer>();
		int[] suitGameFuncs = GameServerAssist.getGameFuncService()
				.getSuitableGameFuncs(openFuncs, human, closeFuncs);
		if (suitGameFuncs.length > 0) {
			for (int funcId : suitGameFuncs) {
				openFuncs.add(funcId);
			}
			sendOpenGameFuncs(suitGameFuncs, true);
		}
		if (event.getType() == EventType.VIP_LEVEL_CHANGE_EVENT) {
			// 检查是否有关闭的功能
			VipLevelChangeEvent vipLevelChangEvent = (VipLevelChangeEvent) event;
			if (vipLevelChangEvent.getChangeLevel() < 0
					&& closeFuncs.size() > 0) {
				int[] closeGameFuncs = new int[closeFuncs.size()];
				for (int i = 0; i < closeGameFuncs.length; i++) {
					closeGameFuncs[i] = closeFuncs.get(i);
					openFuncs.remove((Object) closeGameFuncs[i]);
				}
				sendCloseGameFuncs(closeGameFuncs);

			}
		}
	}

	private void sendOpenGameFuncs(int[] openGameFuncs, boolean isNew) {
		GCOpenGameFuncs gcMsg = new GCOpenGameFuncs();
		gcMsg.setFuncs(openGameFuncs);
		gcMsg.setIsNewOpen(isNew);
		human.sendMessage(gcMsg);
	}

	/**
	 * 发送功能关闭消息
	 * 
	 * @param closedGameFuncs
	 */
	private void sendCloseGameFuncs(int[] closedGameFuncs) {
		GCCloseGameFuncs gcMsg = new GCCloseGameFuncs();
		gcMsg.setFuncs(closedGameFuncs);
		human.sendMessage(gcMsg);
	}

	/**
	 * 发送功能提示列表，客户端显示特效
	 */
	public void sendNotifies() {
		GCFuncNotifies msg = new GCFuncNotifies();
		List<FuncNotifyInfo> notifyInfoList = new ArrayList<FuncNotifyInfo>();
		for (INotifyManager manager : human.getNeedNotifyManagers()) {
			FuncNotifyInfo info = new FuncNotifyInfo();
			info.setFuncType(manager.getFuncType().getIndex());
			info.setNotify(manager.isNotify());
			notifyInfoList.add(info);
		}
		msg.setFuncNotifyInfos(notifyInfoList.toArray(new FuncNotifyInfo[0]));
		human.sendMessage(msg);
	}

	/**
	 * 发送单个功能提示
	 */
	public void sendNotify(GameFuncType funcType, boolean notify) {
		GCFuncNotify msg = new GCFuncNotify();
		msg.setFuncType(funcType.getIndex());
		msg.setNotify(notify);
		human.sendMessage(msg);
	}
}
