package com.hifun.soul.gameserver.autobattle.manager;

import java.util.ArrayList;
import java.util.List;

import com.hifun.soul.core.msg.SceneScheduleMessage;
import com.hifun.soul.core.util.TimeUtils;
import com.hifun.soul.gameserver.autobattle.AutoBattleStateType;
import com.hifun.soul.gameserver.autobattle.msg.GCStageAutoBattleState;
import com.hifun.soul.gameserver.cd.CdType;
import com.hifun.soul.gameserver.cd.manager.HumanCdManager;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.player.state.PlayerState;
import com.hifun.soul.gameserver.vip.template.VipPrivilegeTemplate;

public class HumanAutoBattleManager {

	private Human human;
	/** 自动扫荡的任务 */
	private List<SceneScheduleMessage> sceneScheduleMessages = new ArrayList<SceneScheduleMessage>();
	
	public HumanAutoBattleManager(Human human) {
		this.human = human;
	}
	
	public Human getHuman() {
		return human;
	}
	
	/**
	 * 增加某个扫荡任务调度
	 * 
	 * @param message
	 */
	public void addAutoBattleTask(SceneScheduleMessage message) {
		sceneScheduleMessages.add(message);
	}
	
	/**
	 * 执行完remove
	 * 
	 * @param message
	 */
	public void removeAutoBattleTask(SceneScheduleMessage message) {
		sceneScheduleMessages.remove(message);
	}
	
	/**
	 * 取消所有扫荡任务调度
	 * 
	 */
	private void cancelTasks() {
		for(SceneScheduleMessage message : sceneScheduleMessages){
			message.cancel();
		}
		sceneScheduleMessages.clear();
	}
	
	/**
	 * 判断是否执行完
	 * 
	 * @return
	 */
	public boolean isExecuteComplated() {
		return sceneScheduleMessages.size() <= 0;
	}
	
	/**
	 * 扫荡单位cd
	 * @param cdManager
	 * @param templateManager
	 * @return
	 */
	public long getAutoBattleCd(HumanCdManager cdManager, int star) {
		long time = cdManager.getSpendTime(CdType.AUTO_BATTLE, 0)
				-GameServerAssist.getStageTemplateManager().getReduceCdByStar(star)*TimeUtils.MIN;
		VipPrivilegeTemplate template = GameServerAssist.getVipPrivilegeTemplateManager().getVipTemplate(human.getVipLevel());
		if(template != null){
			time -= template.getAutoBattleCd()*TimeUtils.MIN;
		}
		return time>0?time:0;
	}
	
	/**
	 * 取消扫荡任务
	 * 
	 */
	public void cancelAutoBattle() {
		// 状态由扫荡中切换到游戏中
		human.getPlayer().transferStateTo(PlayerState.GAMEING);
		
		// 清除cd(不消耗金币)
		HumanCdManager cdManager = human.getHumanCdManager();
		cdManager.removeCdFree(CdType.AUTO_BATTLE);
		
		// 取消扫荡任务调度
		cancelTasks();
		
		// 取消完成
		GCStageAutoBattleState gcMsg = new GCStageAutoBattleState();
		gcMsg.setState(AutoBattleStateType.INIT.getIndex());
		human.sendMessage(gcMsg);
	}
}
