package com.hifun.soul.gameserver.arena.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.core.template.TemplateService;
import com.hifun.soul.gameserver.arena.ArenaMember;
import com.hifun.soul.gameserver.arena.manager.HumanArenaManager;
import com.hifun.soul.gameserver.arena.msg.CGOpenArenaPanel;
import com.hifun.soul.gameserver.arena.service.ArenaService;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.function.service.GameFuncService;
import com.hifun.soul.gameserver.guide.GuideType;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.player.Player;

@Component
public class CGOpenArenaPanelHandler implements
		IMessageHandlerWithType<CGOpenArenaPanel> {

	@Autowired
	private TemplateService templateService;
	@Autowired
	private GameFuncService gameFuncService;
	@Autowired
	private ArenaService arenaService;
	
	@Override
	public short getMessageType() {
		return MessageType.CG_OPEN_ARENA_PANEL;
	}

	@Override
	public void execute(CGOpenArenaPanel message) {
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
		
		HumanArenaManager arenaManager = human.getHumanArenaManager();
		// 如果是第一次进入,将角色信息添加到竞技场
		ArenaMember arenaMember = arenaService.getArenaMember(human.getHumanGuid());
		if(arenaMember == null){
			arenaMember = new ArenaMember();
			arenaMember.setIcon(human.getIcon());
			arenaMember.setLevel(human.getLevel());
			arenaMember.setName(human.getName());
			arenaMember.setRank(arenaService.getNextRank());
			arenaMember.setRoleId(human.getHumanGuid());
			arenaMember.setOccupation(human.getOccupation().getIndex());
			
			arenaService.addArenaMember(arenaMember);
			
			// 第一次进入竞技场的时候玩家的给玩家设置可战斗的次数
			arenaManager.resetBattleTime();
		}
		
		arenaManager.updateArenaPanel(arenaMember, arenaService, templateService);
		
		human.getHumanGuideManager().showGuide(GuideType.OPEN_ARENA_PANEL.getIndex());
	}

}
