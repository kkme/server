package com.hifun.soul.gameserver.arena.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.core.template.TemplateService;
import com.hifun.soul.gameserver.arena.ArenaMember;
import com.hifun.soul.gameserver.arena.msg.CGViewArenaRankRewardInfo;
import com.hifun.soul.gameserver.arena.msg.GCViewArenaRankRewardInfo;
import com.hifun.soul.gameserver.arena.service.ArenaService;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.function.service.GameFuncService;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.item.Item;
import com.hifun.soul.gameserver.item.assist.CommonItem;
import com.hifun.soul.gameserver.item.assist.CommonItemBuilder;
import com.hifun.soul.gameserver.item.assist.ItemFactory;
import com.hifun.soul.gameserver.player.Player;
import com.hifun.soul.gameserver.rank.template.RankRewardTemplate;

@Component
public class CGViewArenaRankRewardInfoHandler implements
		IMessageHandlerWithType<CGViewArenaRankRewardInfo> {
	
	@Autowired
	private TemplateService templateService;
	@Autowired
	private ArenaService arenaService;
	@Autowired
	private GameFuncService gameFuncService;

	@Override
	public short getMessageType() {
		return MessageType.CG_VIEW_ARENA_RANK_REWARD_INFO;
	}

	@Override
	public void execute(CGViewArenaRankRewardInfo message) {
		Player player = message.getPlayer();
		if(player == null){
			return;
		}
		
		Human human = player.getHuman();
		if(human == null){
			return;
		}
		
		if(!gameFuncService.gameFuncIsOpen(human, GameFuncType.ARENA, true)){
			return;
		}
		
		ArenaMember arenaMember = arenaService.getArenaMember(human.getHumanGuid());
		if(arenaMember == null){
			return;
		}
		
		int rankRewardId = arenaMember.getRankRewardId();
		if(rankRewardId <= 0){
			return;
		}
		
		RankRewardTemplate rankRewardTemplate 
			= templateService.get(rankRewardId, RankRewardTemplate.class);
		if(rankRewardTemplate == null){
			return;
		}
		
		GCViewArenaRankRewardInfo gcMsg = new GCViewArenaRankRewardInfo();
		Item item = ItemFactory.creatNewItem(human, rankRewardTemplate.getGiftId());
		if(item == null){
			gcMsg.setItems(new CommonItem[0]);
		}
		else{
			CommonItem[] commonItems = new CommonItem[1];
			commonItems[0] = CommonItemBuilder.converToCommonItem(item);
			gcMsg.setItems(commonItems);
		}
		human.sendMessage(gcMsg);
	}

}
