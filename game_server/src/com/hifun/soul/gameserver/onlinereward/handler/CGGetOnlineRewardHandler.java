package com.hifun.soul.gameserver.onlinereward.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hifun.soul.common.LogReasons.ItemLogReason;
import com.hifun.soul.common.constants.LangConstants;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.core.time.SystemTimeService;
import com.hifun.soul.core.util.TimeUtils;
import com.hifun.soul.gameserver.bag.BagType;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.item.assist.CommonItemBuilder;
import com.hifun.soul.gameserver.item.assist.SimpleCommonItem;
import com.hifun.soul.gameserver.onlinereward.manager.HumanOnlineRewardManager;
import com.hifun.soul.gameserver.onlinereward.msg.CGGetOnlineReward;
import com.hifun.soul.gameserver.onlinereward.msg.GCOnlineRewardEnd;
import com.hifun.soul.gameserver.onlinereward.msg.GCShowOnlineReward;
import com.hifun.soul.gameserver.onlinereward.service.OnlineRewardTemplateManager;
import com.hifun.soul.gameserver.onlinereward.template.OnlineRewardTemplate;
import com.hifun.soul.gameserver.player.Player;

@Component
public class CGGetOnlineRewardHandler implements IMessageHandlerWithType<CGGetOnlineReward> {
	
	@Autowired
	private SystemTimeService systemTimeService;
	@Autowired
	private OnlineRewardTemplateManager templateManager;
	
	@Override
	public short getMessageType() {
		return MessageType.CG_GET_ONLINE_REWARD;
	}

	@Override
	public void execute(CGGetOnlineReward message) {
		Player player = message.getPlayer();
		if(player == null){
			return;
		}
		
		Human human = player.getHuman();
		if(human == null){
			return;
		}
		
		HumanOnlineRewardManager manager = human.getHumanOnlineRewardManager();
		OnlineRewardTemplate template = templateManager.getOnlineRewardTemplate(manager.getTimes()+1, human.getOccupation().getIndex());
		if(template == null){
			return;
		}
		
		HumanOnlineRewardManager onlineRewardManager = human.getHumanOnlineRewardManager();
		// 判断是否还有在线奖励
		if(onlineRewardManager.canGet()){
			// 判断背包是否已满
			if(human.getBagManager().isFull(BagType.MAIN_BAG)){
				human.sendWarningMessage(LangConstants.BAG_IS_FULL);
				return;
			}
			
			// 给奖励
			human.getBagManager().putItems(BagType.MAIN_BAG, template.getItemId(), 1, ItemLogReason.ONLINE_REWARD, "");
			
			// 设置领取次数和上次领取时间
			onlineRewardManager.setTimes(onlineRewardManager.getTimes()+1);
			onlineRewardManager.setLastGetTime(systemTimeService.now());
			
			// 判断是否还有奖励
			OnlineRewardTemplate nextTemplate = templateManager.getOnlineRewardTemplate(manager.getTimes()+1, human.getOccupation().getIndex());
			if(nextTemplate != null){
				GCShowOnlineReward gcMsg = new GCShowOnlineReward();
				gcMsg.setSeconds((int) (nextTemplate.getCd()*TimeUtils.MIN/TimeUtils.SECOND));
				SimpleCommonItem commonItem = CommonItemBuilder.genSimpleCommonItem(nextTemplate.getItemId());
				if(commonItem != null){
					SimpleCommonItem[] commonItems = new SimpleCommonItem[1];
					commonItems[0] = commonItem;
					gcMsg.setReward(commonItems);
				}
				else{
					gcMsg.setReward(new SimpleCommonItem[0]);
				}
				
				human.sendMessage(gcMsg);
			}
			else{
				GCOnlineRewardEnd gcMsg = new GCOnlineRewardEnd();
				human.sendMessage(gcMsg);
			}
			
		}
	}

}
