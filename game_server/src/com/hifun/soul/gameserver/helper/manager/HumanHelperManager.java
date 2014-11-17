package com.hifun.soul.gameserver.helper.manager;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.hifun.soul.gameserver.activity.ActivityType;
import com.hifun.soul.gameserver.activity.question.AnswerQuestionManager;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.helper.HelpInfo;
import com.hifun.soul.gameserver.helper.HelpStateType;
import com.hifun.soul.gameserver.helper.HelpType;
import com.hifun.soul.gameserver.helper.HelperConverter;
import com.hifun.soul.gameserver.helper.IHelper;
import com.hifun.soul.gameserver.helper.model.ArenaHelper;
import com.hifun.soul.gameserver.helper.model.AttributesHelper;
import com.hifun.soul.gameserver.helper.model.BossWarHelper;
import com.hifun.soul.gameserver.helper.model.ChooseHelper;
import com.hifun.soul.gameserver.helper.model.CrystalExchangeHelper;
import com.hifun.soul.gameserver.helper.model.EliteHelper;
import com.hifun.soul.gameserver.helper.model.HoroscopeHelper;
import com.hifun.soul.gameserver.helper.model.LevyHelper;
import com.hifun.soul.gameserver.helper.model.LoginRewardHelper;
import com.hifun.soul.gameserver.helper.model.MineHelper;
import com.hifun.soul.gameserver.helper.model.MuseHelper;
import com.hifun.soul.gameserver.helper.model.QuestionHelper;
import com.hifun.soul.gameserver.helper.model.RefineHelper;
import com.hifun.soul.gameserver.helper.model.SkillHelper;
import com.hifun.soul.gameserver.helper.model.StageHelper;
import com.hifun.soul.gameserver.helper.model.TechnologyHelper;
import com.hifun.soul.gameserver.helper.model.TrainingHelper;
import com.hifun.soul.gameserver.helper.model.WarriorHelper;
import com.hifun.soul.gameserver.human.Human;

public class HumanHelperManager {
	private Human human;
	private Set<IHelper> helpers = new HashSet<IHelper>();
	private HelperConverter converter = new HelperConverter();
	
	public HumanHelperManager(Human human) {
		this.human = human;
		
		registerHelpers();
	}
	
	public void registerHelpers() {
		if(GameServerAssist.getHelperTemplateManager().helperIsOpen(HelpType.ARENA.getIndex())){
			ArenaHelper arenaHelper = new ArenaHelper(human.getHumanArenaManager());
			helpers.add(arenaHelper);
		}
		
		if(GameServerAssist.getHelperTemplateManager().helperIsOpen(HelpType.BOSSWAR.getIndex())){
			BossWarHelper bossWarHelper = new BossWarHelper(human.getHumanBossManager());
			helpers.add(bossWarHelper);
		}
		
		if(GameServerAssist.getHelperTemplateManager().helperIsOpen(HelpType.CHOOSE.getIndex())){
			ChooseHelper chooseHelper = new ChooseHelper(human.getLevyManager());
			helpers.add(chooseHelper);
		}
		
		if(GameServerAssist.getHelperTemplateManager().helperIsOpen(HelpType.CRYSTALEXCHANGE.getIndex())){
			CrystalExchangeHelper crystalExchangeHelper = new CrystalExchangeHelper(human.getHumanCrystalExchangeManager());
			helpers.add(crystalExchangeHelper);
		}
		
		if(GameServerAssist.getHelperTemplateManager().helperIsOpen(HelpType.ELITE.getIndex())){
			EliteHelper eliteHelper = new EliteHelper(human.getHumanEliteStageManager());
			helpers.add(eliteHelper);
		}
		
		if(GameServerAssist.getHelperTemplateManager().helperIsOpen(HelpType.HOROSCOPE.getIndex())){
			HoroscopeHelper horoscopeHelper = new HoroscopeHelper(human.getHumanHoroscopeManager());
			helpers.add(horoscopeHelper);
		}
		
		if(GameServerAssist.getHelperTemplateManager().helperIsOpen(HelpType.LEVY.getIndex())){
			LevyHelper levyHelper = new LevyHelper(human.getLevyManager());
			helpers.add(levyHelper);
		}
		
		if(GameServerAssist.getHelperTemplateManager().helperIsOpen(HelpType.LOGINREWARD.getIndex())){
			LoginRewardHelper loginRewardHelper = new LoginRewardHelper(human.getHumanLoginRewardManager());
			helpers.add(loginRewardHelper);
		}

//		if(templateManager.helperIsOpen(HelpType.LOTTERY.getIndex())){
//			LotteryHelper lotteryHelper = new LotteryHelper(human.getHumanTurntableManager());
//			helpers.add(lotteryHelper);
//		}
		
		if(GameServerAssist.getHelperTemplateManager().helperIsOpen(HelpType.MINE.getIndex())){
			MineHelper mineHelper = new MineHelper(human.getHumanMineManager());
			helpers.add(mineHelper);
		}
		
		if(GameServerAssist.getHelperTemplateManager().helperIsOpen(HelpType.MUSE.getIndex())){
			MuseHelper museHelper = new MuseHelper(human.getHumanMeditationManager());
			helpers.add(museHelper);
		}
		
		if(GameServerAssist.getHelperTemplateManager().helperIsOpen(HelpType.QUESTION.getIndex())){
			QuestionHelper questionHelper = new QuestionHelper((AnswerQuestionManager)human.getHumanActivityManager().getActivityManager(ActivityType.ANSWER_QUESTION));
			helpers.add(questionHelper);
		}
		
		if(GameServerAssist.getHelperTemplateManager().helperIsOpen(HelpType.STAGE.getIndex())){
			StageHelper stageHelper = new StageHelper(human.getHumanStageManager());
			helpers.add(stageHelper);
		}
		
		if(GameServerAssist.getHelperTemplateManager().helperIsOpen(HelpType.TECHNOLOGY.getIndex())){
			TechnologyHelper technologyHelper = new TechnologyHelper(human.getHumanTechnologyManager());
			helpers.add(technologyHelper);
		}
		
		if(GameServerAssist.getHelperTemplateManager().helperIsOpen(HelpType.TRAIN.getIndex())){
			TrainingHelper trainingHelper = new TrainingHelper(human.getHumanTrainingManager());
			helpers.add(trainingHelper);
		}
		
		if(GameServerAssist.getHelperTemplateManager().helperIsOpen(HelpType.ATTRIBUTES.getIndex())){
			AttributesHelper attributesHelper = new AttributesHelper(human.getPropertyManager());
			helpers.add(attributesHelper);
		}
		
		if(GameServerAssist.getHelperTemplateManager().helperIsOpen(HelpType.SKILL.getIndex())){
			SkillHelper skillHelper = new SkillHelper(human.getSkillManager());
			helpers.add(skillHelper);
		}
		if(GameServerAssist.getHelperTemplateManager().helperIsOpen(HelpType.REFINE.getIndex())){
			RefineHelper refineHelper = new RefineHelper(human.getHumanRefineManager());
			helpers.add(refineHelper);
		}
		if(GameServerAssist.getHelperTemplateManager().helperIsOpen(HelpType.WARRIOR.getIndex())){
			WarriorHelper warriorHelper = new WarriorHelper(human.getHumanWarriorManager());
			helpers.add(warriorHelper);
		}
	}
	
	public HelpInfo[] getHelpInfos() {
		List<HelpInfo> helpInfos = new ArrayList<HelpInfo>();
		for(IHelper helper : helpers){
			HelpInfo helpInfo = converter.converter(helper);
			if(helpInfo.getState() != HelpStateType.CLOSED.getIndex()){
				helpInfos.add(helpInfo);
			}
		}
		return helpInfos.toArray(new HelpInfo[0]);
	}
	
}
