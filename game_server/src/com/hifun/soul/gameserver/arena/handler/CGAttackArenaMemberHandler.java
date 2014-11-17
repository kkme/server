package com.hifun.soul.gameserver.arena.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hifun.soul.common.constants.LangConstants;
import com.hifun.soul.common.constants.SharedConstants;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.core.template.TemplateService;
import com.hifun.soul.core.util.TimeUtils;
import com.hifun.soul.gameserver.arena.ArenaMember;
import com.hifun.soul.gameserver.arena.manager.HumanArenaManager;
import com.hifun.soul.gameserver.arena.msg.CGAttackArenaMember;
import com.hifun.soul.gameserver.arena.service.ArenaService;
import com.hifun.soul.gameserver.arena.template.ArenaConfigTemplate;
import com.hifun.soul.gameserver.battle.callback.ArenaBattleFinishedCallback;
import com.hifun.soul.gameserver.battle.manager.IBattleManager;
import com.hifun.soul.gameserver.cd.CdType;
import com.hifun.soul.gameserver.cd.manager.HumanCdManager;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.function.service.GameFuncService;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.player.Player;

@Component
public class CGAttackArenaMemberHandler implements
		IMessageHandlerWithType<CGAttackArenaMember> {

	@Autowired
	private IBattleManager battleManager;
	@Autowired
	private ArenaService arenaService;
	@Autowired
	private TemplateService templateService;
	@Autowired
	private GameFuncService gameFuncService;
	
	@Override
	public short getMessageType() {
		return MessageType.CG_ATTACK_ARENA_MEMBER;
	}

	@Override
	public void execute(CGAttackArenaMember message) {
		Player player = message.getPlayer();
		if(player == null){
			return;
		}
		
		Human human = player.getHuman();
		if(human == null){
			return;
		}
		
		// 判断功能是否开放
		if(!gameFuncService.gameFuncIsOpen(human, GameFuncType.ARENA, true)){
			return;
		}
		
		// 判断当前是否处于cd中
		ArenaConfigTemplate configTemplate 
			= templateService.get(SharedConstants.CONFIG_TEMPLATE_DEFAULT_ID, ArenaConfigTemplate.class);
		if(configTemplate == null){
			return;
		}
		HumanCdManager cdManager = human.getHumanCdManager();
		long spendTime = cdManager.getSpendTime(CdType.ARENA_BATTLE, configTemplate.getBattleCd() * TimeUtils.SECOND);
		if(!cdManager.canAddCd(CdType.ARENA_BATTLE, spendTime)){
			human.sendWarningMessage(LangConstants.CD_LIMIT);
			return;
		}
		
		HumanArenaManager arenaManager = human.getHumanArenaManager();
		// 判断当前玩家是否还有战斗次数
		if(arenaManager.getArenaBattleTime() <= 0){
			human.sendErrorMessage(LangConstants.NO_BATTLE_TIME);
			return;
		}
		
		// 判断当前玩家排名和要攻打的玩家排名是否超出了允许的范围
		ArenaMember self = arenaService.getArenaMember(human.getHumanGuid());
		ArenaMember other = arenaService.getArenaMember(message.getHumanGuid());
		if(self == null
				|| other == null){
			return;
		}
		// 如果进入前5名则可以互相挑战
		boolean isUpFive = self.getRank() < SharedConstants.ARENA_VISIBLE_NUM; 
		if((self.getRank() - other.getRank()) > arenaService.getInterval(self.getRank())*SharedConstants.ARENA_VISIBLE_NUM ||
				!isUpFive && self.getRank() <= other.getRank()){
			human.sendErrorMessage(LangConstants.ARENA_MEMBER_OUT);
			return;
		}
		// 是否是前5名，且高排名挑战低排名
		boolean isUpFiveAndHigherVsLower = isUpFive && self.getRank() < other.getRank();
		// 进入战斗
		battleManager.pvpBattleEnter(human, message.getHumanGuid(), new ArenaBattleFinishedCallback(human, isUpFiveAndHigherVsLower));
	}

}
