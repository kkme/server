package com.hifun.soul.gameserver.player.handler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hifun.soul.common.model.human.CharacterInfo;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.bag.service.BagService;
import com.hifun.soul.gameserver.battle.template.BattlePopTemplate;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.compass.ICompassService;
import com.hifun.soul.gameserver.gameworld.GameWorld;
import com.hifun.soul.gameserver.guide.GuideType;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.human.msg.GCCharacterInfo;
import com.hifun.soul.gameserver.human.quest.QuestInfo;
import com.hifun.soul.gameserver.legion.Legion;
import com.hifun.soul.gameserver.logger.Loggers;
import com.hifun.soul.gameserver.player.Player;
import com.hifun.soul.gameserver.player.msg.BattlePopInfo;
import com.hifun.soul.gameserver.player.msg.CGEnterSceneReady;
import com.hifun.soul.gameserver.player.msg.GCBattlePop;
import com.hifun.soul.gameserver.player.state.PlayerState;
import com.hifun.soul.gameserver.quest.msg.GCQuestList;
import com.hifun.soul.gameserver.skill.ISkill;
import com.hifun.soul.gameserver.skill.SkillType;
import com.hifun.soul.gameserver.skill.msg.GCBattleCarriedSkills;
import com.hifun.soul.gameserver.skill.template.SkillInfo;

/**
 * 角色进入场景处理器;
 * 
 * @author crazyjohn
 * 
 */
@Component
public class CGEnterSceneReadyHandler implements
		IMessageHandlerWithType<CGEnterSceneReady> {
	private static Logger logger = Loggers.HUMAN_LOGIN_LOGGER();
	@Autowired
	private GameWorld sceneManager;
	@Autowired
	BagService bagService;
	/** 罗盘服务接口 */
	private ICompassService compassService;

	@Override
	public short getMessageType() {
		return MessageType.CG_ENTER_SCENE_READY;
	}

	@Override
	public void execute(CGEnterSceneReady message) {
		final Player player = message.getPlayer();
		if (player == null) {
			if (logger.isErrorEnabled()) {
				logger.error(String.format(
						"Player can not be null, session: %s", player));
			}
			return;
		}
		Human human = player.getHuman();
		if (human == null) {
			if (logger.isErrorEnabled()) {
				logger.error(String.format(
						"Human can not be null, player: %s, player: %d",
						player, player.getPassportId()));
			}
			return;
		}

		// 切换状态
		if (!player.canTransferStateTo(PlayerState.GAMEING)) {
			logger.error(String.format(
					"Can not transfer player state from %s to %s",
					player.getState(), PlayerState.GAMEING));
			return;
		}
		player.transferStateTo(PlayerState.GAMEING);

		// 罗盘汇报
		if (compassService == null) {
			compassService = GameServerAssist.getCompassService();
		}
		if (this.compassService != null) {
			compassService.login((int) human.getHumanGuid(),
					player.getAccount(), human.getLevel());
		}
		// 下发角色信息
		GCCharacterInfo charInfoMsg = new GCCharacterInfo();
		CharacterInfo charInfo = new CharacterInfo();
		charInfo.setGuid(human.getHumanGuid());
		charInfo.setLevel(human.getLevel());
		charInfo.setName(human.getName());
		charInfo.setOccupation(human.getOccupation().getIndex());
			Legion legion = GameServerAssist.getGlobalLegionManager()
					.getLegion(human.getHumanGuid());
			if (legion != null) {
				charInfo.setLegionId(legion.getId());
				charInfo.setLegionName(legion.getLegionName());
			} else {
				charInfo.setLegionName("");
			}
		int title = human.getCurrentTitle();
		if (title > 0) {
			String titleName = GameServerAssist.getTitleTemplateManager()
					.getHumanTitleTemplate(title).getTitleName();
			charInfo.setTitleName(titleName);
		} else {
			charInfo.setTitleName("");
		}

		charInfoMsg.setHuman(charInfo);
		player.sendMessage(charInfoMsg);
		// 添加到玩家管理器
		sceneManager.humanEnter(human);

		// Editby: crazyjohn 2013-04-25这里有并发问题, 注释掉;让场景去处理; 角色登陆时各个管理器的特殊处理
		// human.onLogin();

		// 下发主线任务信息
		GCQuestList questsMsg = new GCQuestList();
		List<QuestInfo> questList = human.getHumanQuestManager()
				.getAllMainQuest();
		// 任务排序
		Collections.sort(questList, new Comparator<QuestInfo>() {

			@Override
			public int compare(QuestInfo o1, QuestInfo o2) {
				if (o1.getQuestState() >= o2.getQuestState()) {
					return -1;
				}
				return 1;
			}
		});
		questsMsg.setQuestList(questList.toArray(new QuestInfo[0]));
		player.sendMessage(questsMsg);
		// 下发所有技能信息
		human.getSkillManager().sendAllSkillInfos();
		// 下发战斗携带的技能
		GCBattleCarriedSkills carriedSkillsMsg = new GCBattleCarriedSkills();
		List<SkillInfo> carriedSkills = new ArrayList<SkillInfo>();
		for (ISkill skill : human.getSkillManager().getCarriedSkills()) {
			if (skill.getSkillTemplate().getSkillType() != SkillType.NORMAL_ATTACK
					.getIndex()
					&& skill.getSkillTemplate().getSkillType() != SkillType.COMBO_ATTACK
							.getIndex()) {
				SkillInfo skillInfo = skill.toSkillInfo();
				skillInfo.setIsCarried(true);
				carriedSkills.add(skillInfo);
			}
		}
		// 排序
		Collections.sort(carriedSkills, new Comparator<SkillInfo>() {

			@Override
			public int compare(SkillInfo o1, SkillInfo o2) {
				if (o1.getSkillId() < o2.getSkillId()) {
					return -1;
				}
				if (o1.getSkillId() > o2.getSkillId()) {
					return 1;
				}
				return 0;
			}
		});
		carriedSkillsMsg.setSkills(carriedSkills.toArray(new SkillInfo[0]));
		player.sendMessage(carriedSkillsMsg);

		// 下发聊天泡泡信息
		GCBattlePop popMsg = new GCBattlePop();
		getPopInfo(popMsg);
		player.sendMessage(popMsg);
		human.getHumanGuideManager().showGuide(GuideType.ENTER_GAME.getIndex());

	}

	private void getPopInfo(GCBattlePop popMsg) {
		Map<Integer, BattlePopTemplate> templates = GameServerAssist
				.getTemplateService().getAll(BattlePopTemplate.class);
		List<BattlePopInfo> infoList = new ArrayList<BattlePopInfo>();
		for (BattlePopTemplate each : templates.values()) {
			infoList.add(each.toInfo());
		}
		popMsg.setAllPops(infoList.toArray(new BattlePopInfo[0]));
		popMsg.setMaxPveRound(GameServerAssist.getGameConstants()
				.getMaxPveBattleRound());
		popMsg.setMaxPvpRound(GameServerAssist.getGameConstants()
				.getMaxPvpBattleRound());
	}
}
