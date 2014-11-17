package com.hifun.soul.gameserver.matchbattle.battle;

import com.hifun.soul.common.constants.SharedConstants;
import com.hifun.soul.gameserver.battle.BattleType;
import com.hifun.soul.gameserver.battle.IBattleUnit;
import com.hifun.soul.gameserver.battle.callback.ClientGameSceneType;
import com.hifun.soul.gameserver.battle.callback.PVPBattleCallback;
import com.hifun.soul.gameserver.battle.msg.GCExitBattle;
import com.hifun.soul.gameserver.cd.CdType;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.matchbattle.MatchBattleRole;
import com.hifun.soul.gameserver.matchbattle.msg.GCUpdateMatchBattleResult;
import com.hifun.soul.gameserver.matchbattle.report.MatchBattleReportInfo;
import com.hifun.soul.gameserver.matchbattle.report.MatchBattleReportService;
import com.hifun.soul.gameserver.matchbattle.template.MatchBattleRewardTemplate;
import com.hifun.soul.gameserver.matchbattle.template.MatchBattleStreakWinTemplate;
import com.hifun.soul.gameserver.role.properties.Level2Property;
import com.hifun.soul.gameserver.role.properties.PropertyType;
import com.hifun.soul.gameserver.role.properties.amend.AmendMethod;

public class MatchBattleCallback extends PVPBattleCallback {
	private MatchBattleRole firstRole;
	private MatchBattleRole secondRole;
	
	private MatchBattleRole winner;
	private MatchBattleRole loser;
	
	public MatchBattleCallback(MatchBattleRole challenger,MatchBattleRole otherRole) {
		super(challenger.getHuman());	
		this.firstRole = challenger;
		this.secondRole = otherRole;
	}
	
	@Override
	public void beforeBattleStart(Human challenger, IBattleUnit beChallenged) {
		// 宝石攻击加成公式改为:encourageRate/10000.0*宝石攻击力
		// 技能攻击加成公式改为:encourageRate/10000.0*技能攻击力
		if (firstRole.getEncourageRate() != 0) {
			int skillAttack = firstRole.getHuman().getHumanPropertiesManager().getIntPropertySet(PropertyType.LEVEL2_PROPERTY)
					.getPropertyValue(Level2Property.ATTACK);
			skillAttack = (int) (firstRole.getEncourageRate() / SharedConstants.DEFAULT_FLOAT_BASE * skillAttack);
			int gemAttack = firstRole.getHuman().getHumanPropertiesManager().getIntPropertySet(PropertyType.LEVEL2_PROPERTY)
					.getPropertyValue(Level2Property.GEM_ATTACK);
			gemAttack = (int) (firstRole.getEncourageRate() / SharedConstants.DEFAULT_FLOAT_BASE * gemAttack);
			firstRole.getHuman()
					.getBattleContext()
					.getBattleProperty()
					.amendProperty(AmendMethod.ADD,
							PropertyType.genPropertyKey(Level2Property.ATTACK, PropertyType.LEVEL2_PROPERTY),
							skillAttack);
			firstRole.getHuman()
					.getBattleContext()
					.getBattleProperty()
					.amendProperty(AmendMethod.ADD,
							PropertyType.genPropertyKey(Level2Property.GEM_ATTACK, PropertyType.LEVEL2_PROPERTY),
							gemAttack);
		}
		if(secondRole.getEncourageRate()!=0){			
			int skillAttack = secondRole.getHuman().getHumanPropertiesManager().getIntPropertySet(PropertyType.LEVEL2_PROPERTY)
					.getPropertyValue(Level2Property.ATTACK);
			skillAttack = (int) (secondRole.getEncourageRate() / SharedConstants.DEFAULT_FLOAT_BASE * skillAttack);
			int gemAttack = secondRole.getHuman().getHumanPropertiesManager().getIntPropertySet(PropertyType.LEVEL2_PROPERTY)
					.getPropertyValue(Level2Property.GEM_ATTACK);
			gemAttack = (int) (secondRole.getEncourageRate() / SharedConstants.DEFAULT_FLOAT_BASE * gemAttack);
			secondRole.getHuman()
					.getBattleContext()
					.getBattleProperty()
					.amendProperty(AmendMethod.ADD,
							PropertyType.genPropertyKey(Level2Property.ATTACK, PropertyType.LEVEL2_PROPERTY),
							skillAttack);
			secondRole.getHuman()
					.getBattleContext()
					.getBattleProperty()
					.amendProperty(AmendMethod.ADD,
							PropertyType.genPropertyKey(Level2Property.GEM_ATTACK, PropertyType.LEVEL2_PROPERTY),
							gemAttack);
		}	
	}

	@Override
	public void onChallengerWin(Human challenger, IBattleUnit beChallenged) {
		winner = firstRole;
		loser = secondRole;
				
	}

	@Override
	public void onChallengerLose(Human challenger, IBattleUnit beChallenged) {
		winner = secondRole;
		loser = firstRole;				
	}

	@Override
	public BattleType getBattleType() {		
		return BattleType.PVP_MATCH_BATTLE;
	}

	@Override
	protected void setExitToSceneType(GCExitBattle exitBattle) {
		exitBattle.setGameSceneType(ClientGameSceneType.MATCH_BATTLE_VIEW.getIndex());
	}
	
	@Override
	public void onEnterBattleFailed() {
		if(firstRole.getAutoJoinNextMatchScheduleMessage()!=null){
			firstRole.getAutoJoinNextMatchScheduleMessage().cancel();
		}
		if(secondRole.getAutoJoinNextMatchScheduleMessage() !=null){
			secondRole.getAutoJoinNextMatchScheduleMessage().cancel();
		}
		firstRole.getHuman().getHumanCdManager().removeCdFree(CdType.MATCH_BATTLE_CD);
		firstRole.getHuman().getHumanCdManager().snapCdQueueInfo(CdType.MATCH_BATTLE_CD);
		secondRole.getHuman().getHumanCdManager().removeCdFree(CdType.MATCH_BATTLE_CD);
		secondRole.getHuman().getHumanCdManager().snapCdQueueInfo(CdType.MATCH_BATTLE_CD);
		GameServerAssist.getMatchBattleService().onEnterBattleFailed(firstRole,secondRole);
	}
	
	/**
	 * 战斗结束的处理
	 * @param winner
	 * @param loser
	 */
	private void onBattleFinish(MatchBattleRole winner,MatchBattleRole loser){
		MatchBattleRewardTemplate winnerRewardTemplate = GameServerAssist.getMatchBattleTemplateManager().getMatchBattleRewardTemplate(winner.getLevel());
		MatchBattleRewardTemplate loserRewardTemplate = GameServerAssist.getMatchBattleTemplateManager().getMatchBattleRewardTemplate(loser.getLevel());
		//胜放金币奖励
		int winnerCoin = loserRewardTemplate.getCoinOfWin();
		//败方金币奖励
		int loserCoin = winnerRewardTemplate.getCoinOfLose();
		//胜方荣誉奖励
		int winnerHonour = loserRewardTemplate.getHonorOfWin();
		//败方荣誉奖励
		int loserHonour = winnerRewardTemplate.getHonorOfLose();
		int consecutveWinCount = winner.getConsecutiveWinCount();	
		MatchBattleStreakWinTemplate streakWinTemplate = GameServerAssist.getMatchBattleTemplateManager()
				.getMatchBattleStreakWinTemplate(consecutveWinCount+1);
		winnerHonour = streakWinTemplate.getHonour();
		winnerCoin += streakWinTemplate.getCoin();
		//是否有连胜终结
		boolean isTerminate = false;		
		if(loser.getConsecutiveWinCount()>1){
			isTerminate = true;
			MatchBattleStreakWinTemplate terminateStreakWinTemplate = GameServerAssist.getMatchBattleTemplateManager()
					.getMatchBattleStreakWinTemplate(loser.getConsecutiveWinCount());
			winnerHonour+=terminateStreakWinTemplate.getFinishOhterStreakWinHonour();			
		}		
		//添加战斗收益
		winner.addTotalRewardCoin(winnerCoin);
		winner.addTotalRewardHonour(winnerHonour);
		loser.addTotalRewardCoin(loserCoin);
		loser.addTotalRewardHonour(loserHonour);		
		// 组织战报内容
		MatchBattleReportInfo winnerReportInfo = new MatchBattleReportInfo();
		winnerReportInfo.setTerminate(isTerminate);
		winnerReportInfo.setCoinReward(winnerCoin);
		winnerReportInfo.setHonourReward(winnerHonour);
		winnerReportInfo.setLastConsecutiveWinCount(loser.getConsecutiveWinCount());
		MatchBattleReportInfo loserReportInfo = new MatchBattleReportInfo();
		loserReportInfo.setTerminate(isTerminate);
		loserReportInfo.setCoinReward(loserCoin);
		loserReportInfo.setHonourReward(loserHonour);
		loserReportInfo.setLastConsecutiveWinCount(loser.getConsecutiveWinCount());		
		//更新个人胜败数据
		winner.onBattleWin();
		loser.onBattleLose();		
		//生成战报内容
		String reportContent = MatchBattleReportService.generateBattleReport(winner, loser,winnerReportInfo);
		String winReportContent = MatchBattleReportService.generateSelfBattleReport(winner, loser, true, winnerReportInfo);
		String loseReportContent =  MatchBattleReportService.generateSelfBattleReport(loser, winner, false, loserReportInfo);
		//发送战报
		sendSelfBattleResultMessage(winner,winReportContent);
		sendSelfBattleResultMessage(loser,loseReportContent);
		//发送全员战报
		GameServerAssist.getMatchBattleService().sendBattleReport(reportContent);
		//发送状态更新消息,需要判断活动是否还在开启中
		GameServerAssist.getMatchBattleService().battleRoleStateChange(new MatchBattleRole[]{winner,loser});
	}
	
	/**
	 * 发送个人的战斗结果消息
	 * @param self
	 * @param reportContent
	 */
	private void sendSelfBattleResultMessage(MatchBattleRole self,String reportContent){
		GCUpdateMatchBattleResult gcMsg = new GCUpdateMatchBattleResult();		
		gcMsg.setCoinReward(self.getCoinReward());
		gcMsg.setHonourReward(self.getHonourReward());		
		gcMsg.setConsecutiveWinCount(self.getConsecutiveWinCount());
		gcMsg.setContent(reportContent);
		gcMsg.setLoseCount(self.getLoseCount());
		gcMsg.setMaxConsecutiveWinCount(self.getMaxConsecutiveWinCount());
		gcMsg.setWinCount(self.getWinCount());
		self.getHuman().sendMessage(gcMsg);
	}

	@Override
	public boolean requireSendJoinBattleRequest(){
		return false;
	}
	
	@Override
	public void onBattleExited() {
		onBattleFinish(winner,loser);
	}

}
