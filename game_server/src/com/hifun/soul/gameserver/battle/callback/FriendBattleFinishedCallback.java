package com.hifun.soul.gameserver.battle.callback;

import com.hifun.soul.common.constants.LangConstants;
import com.hifun.soul.gameserver.battle.BattleType;
import com.hifun.soul.gameserver.battle.IBattleUnit;
import com.hifun.soul.gameserver.battle.msg.GCExitBattle;
import com.hifun.soul.gameserver.battle.msg.GCJoinBattleRequest;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.event.ClickFriendMiniBattleEvent;
import com.hifun.soul.gameserver.friend.FriendBattleInfo;
import com.hifun.soul.gameserver.friend.converter.FriendBattleToEntityConverter;
import com.hifun.soul.gameserver.friend.msg.GCAddFriendBattleinfo;
import com.hifun.soul.gameserver.friend.msg.GCFriendBattleResult;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.tencent.TencentUserInfo;

/**
 * 好友战斗回调;
 * 
 * @author crazyjohn
 * 
 */
public class FriendBattleFinishedCallback extends PVPBattleCallback {
	private FriendBattleToEntityConverter converter = new FriendBattleToEntityConverter();
	private Human human;	

	public FriendBattleFinishedCallback(Human challenger) {
		super(challenger);
		human = challenger;
	}

	@Override
	public void onChallengerWin(Human challenger, IBattleUnit beChallenged) {
		fireBattleWithFriendEvent();
		refreshFriendBattleInfo(challenger,beChallenged,true);
	}

	@Override
	public void onChallengerLose(Human challenger, IBattleUnit beChallenged) {
		fireBattleWithFriendEvent();
		refreshFriendBattleInfo(challenger,beChallenged,false);
	}
	
	

	private void fireBattleWithFriendEvent() {
		ClickFriendMiniBattleEvent event = new ClickFriendMiniBattleEvent();
		challenger.getEventBus().fireEvent(event);
	}
	
	private void refreshFriendBattleInfo(Human challenger, IBattleUnit beChallenged, boolean isWin) {
		long now = GameServerAssist.getSystemTimeService().now();
		FriendBattleInfo friendBattleInfo = new FriendBattleInfo();
		friendBattleInfo.setRoleId(challenger.getHumanGuid());
		friendBattleInfo.setRoleName(challenger.getName());
		friendBattleInfo.setOtherRoleId(beChallenged.getUnitGuid());
		friendBattleInfo.setOtherRoleName(beChallenged.getUnitName());
		friendBattleInfo.setWin(isWin);
		friendBattleInfo.setBattleTime(now);
		friendBattleInfo.setIsChallenger(true);
		challenger.getHumanFriendManager().addFriendBattleInfo(friendBattleInfo);
		sendBattleInfo(challenger,friendBattleInfo);
		
		FriendBattleInfo beFriendBattleInfo = new FriendBattleInfo();
		beFriendBattleInfo.setRoleId(beChallenged.getUnitGuid());
		beFriendBattleInfo.setRoleName(beChallenged.getUnitName());
		beFriendBattleInfo.setOtherRoleId(challenger.getHumanGuid());
		beFriendBattleInfo.setOtherRoleName(challenger.getName());
		beFriendBattleInfo.setWin(!isWin);
		beFriendBattleInfo.setBattleTime(now);
		beFriendBattleInfo.setIsChallenger(false);
		Human beChallenger = GameServerAssist.getGameWorld().getSceneHumanManager().getHumanByGuid(beChallenged.getUnitGuid());
		if(beChallenger != null){
			beChallenger.getHumanFriendManager().addFriendBattleInfo(beFriendBattleInfo);
			sendBattleInfo(beChallenger,beFriendBattleInfo);
		}
		GameServerAssist.getDataService().insert(converter.convert(friendBattleInfo), null);
		GameServerAssist.getDataService().insert(converter.convert(beFriendBattleInfo), null);
	}

	@Override
	public BattleType getBattleType() {
		return BattleType.PVP_FRIEND;
	}

	@Override
	protected void setExitToSceneType(GCExitBattle exitBattle) {
		// TODO Auto-generated method stub
		
	}
	
	private void sendBattleInfo(Human human, FriendBattleInfo friendBattleInfo) {
		TencentUserInfo txUserInfo = GameServerAssist.getTencentUserInfoManager().getTencentUserInfo(friendBattleInfo.getOtherRoleId());
		if(txUserInfo != null){
			GCFriendBattleResult gcBattleResult = new GCFriendBattleResult();
			gcBattleResult.setBattleResult(friendBattleInfo.getWin());
			gcBattleResult.setFriendOpenId(txUserInfo.getOpenId());
			human.sendMessage(gcBattleResult);
		}
		// 增加好友切磋记录消息
		GCAddFriendBattleinfo gcMsg = new GCAddFriendBattleinfo();
		gcMsg.setFriendBattleInfo(friendBattleInfo);
		human.sendMessage(gcMsg);
	}

	@Override
	public void sendJoinBattleRequest(Human beChallenge){
		GCJoinBattleRequest requestBattle = new GCJoinBattleRequest();
		requestBattle.setChallengerGuid(human.getHumanGuid());
		requestBattle.setChallengerName(human.getName());
		requestBattle.setContent(GameServerAssist.getSysLangService().read(
				LangConstants.FRIEND_BATTLE));
		beChallenge.sendMessage(requestBattle);
	}

}
