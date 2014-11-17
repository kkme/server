package com.hifun.soul.gameserver.boss.handler;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.boss.msg.CGGetBossReward;

@Component
public class CGGetBossRewardHandler implements
		IMessageHandlerWithType<CGGetBossReward> {

//	@Autowired
//	private BossWarService bossWarService;
//	@Autowired
//	private BossWarTemplateManager templateManager;
//	@Autowired
//	private IDataService dataService;
//	@Autowired
//	private GameFuncService gameFuncService;
	
	@Override
	public short getMessageType() {
		return MessageType.CG_GET_BOSS_REWARD;
	}

	@Override
	public void execute(CGGetBossReward message) {
//		Player player = message.getPlayer();
//		if(player == null){
//			return;
//		}
//		
//		Human human = player.getHuman();
//		if(human == null){
//			return;
//		}
//		
//		// 判断功能是否开放
//		if(!gameFuncService.gameFuncIsOpen(human, GameFuncType.DAILY_ACTIVITY, true)){
//			return;
//		}
//		
//		// boss如果还活着不可以领奖
//		BossInfo bossInfo = bossWarService.getBossInfo();
//		if(bossInfo == null){
//			return;
//		}
//		
//		// 判断boss的状态是否可以领奖
//		if(BossState.LIVE.getIndex() == bossInfo.getBossState()){
//			human.sendErrorMessage(LangConstants.BOSS_WAR_NOT_REWARD);
//			return;
//		}
//		
//		// 判断当前玩家是否有可以领取的奖励
//		BossRoleInfo bossRoleInfo = bossWarService.getBossRoleInfo(human.getHumanGuid());
//		if(bossRoleInfo == null){
//			return;
//		}
//		if(bossRoleInfo.getRank() <= 0
//				|| !bossRoleInfo.getHasReward()){
//			human.sendErrorMessage(LangConstants.BOSS_WAR_NOT_REWARD);
//			return;
//		}
//		
//		// 领取奖励(击杀奖励、排名奖励、伤害奖励)
//		BossTemplate bossTemplate = bossWarService.getBossTemplate();
//		if(bossTemplate == null){
//			return;
//		}
//		List<Integer> itemIds = new ArrayList<Integer>();
//		// 击杀奖励
//		if(human.getHumanGuid() == bossInfo.getKillerId()){
//			itemIds.add(bossTemplate.getGiftId());
//		}
//		// 排名奖励
//		BossRankRewardTemplate template = templateManager.getSuitableRewardTemplate(bossRoleInfo.getRank());
//		if(template != null){
//			itemIds.add(template.getGiftId());
//		}
//		// 判断是否有足够的背包格子
//		if(human.getBagManager().getFreeSize(BagType.MAIN_BAG) < itemIds.size()){
//			human.sendErrorMessage(LangConstants.BAG_FREE_UNIT_NOT_ENOUGH);
//			return;
//		}
//		for(Integer itemId : itemIds){
//			human.getBagManager().putItems(BagType.MAIN_BAG, itemId, 1, ItemLogReason.BOSS_REWARD, "");
//		}
//		int money = (int) (bossRoleInfo.getDamage()*1.0f*bossTemplate.getTotalCoin()/bossInfo.getTotalBlood());
//		human.getWallet().addMoney(CurrencyType.COIN, money, MoneyLogReason.BOSS_REWARD, "");
//		
//		// 修改状态
//		bossRoleInfo.setHasReward(false);
//		dataService.update(new BossRoleInfoToEntityConverter().convert(bossRoleInfo));
//		
//		GCGetBattleReward gcMsg = new GCGetBattleReward();
//		human.sendMessage(gcMsg);
	}

}
