package com.hifun.soul.gameserver.legion.handler;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

import org.springframework.stereotype.Component;

import com.hifun.soul.common.LogReasons.MoneyLogReason;
import com.hifun.soul.common.LogReasons.TechPointLogReason;
import com.hifun.soul.common.constants.LangConstants;
import com.hifun.soul.common.constants.SharedConstants;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.currency.CurrencyType;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.legion.Legion;
import com.hifun.soul.gameserver.legion.LegionMeditationLog;
import com.hifun.soul.gameserver.legion.enums.LegionBuildingType;
import com.hifun.soul.gameserver.legion.info.LegionBuildingInfo;
import com.hifun.soul.gameserver.legion.manager.GlobalLegionManager;
import com.hifun.soul.gameserver.legion.msg.CGLegionMeditation;
import com.hifun.soul.gameserver.legion.msg.GCLegionMeditation;
import com.hifun.soul.gameserver.legion.template.LegionMeditationTemplate;

@Component
public class CGLegionMeditationHandler implements
		IMessageHandlerWithType<CGLegionMeditation> {

	@Override
	public short getMessageType() {
		return MessageType.CG_LEGION_MEDITATION;
	}

	@Override
	public void execute(CGLegionMeditation message) {
		Human human = message.getPlayer().getHuman();
		GlobalLegionManager globalLegionManager = GameServerAssist
				.getGlobalLegionManager();
		// 校验军团是否存在
		Legion legion = globalLegionManager.getLegion(human.getHumanGuid());
		if (legion == null) {
			return;
		}
		// 军团子功能是否开启
		if (!globalLegionManager.legionFuncIsOpen(human,
				LegionBuildingType.MEDITATION, true)) {
			return;
		}
		LegionMeditationTemplate template = GameServerAssist
				.getLegionTemplateManager().getMeditationTemplate(
						message.getMeditationType());
		if (template == null) {
			return;
		}
		// 冥想
		CurrencyType currencyType = CurrencyType.indexOf(template
				.getCurrencyType());
		MoneyLogReason reason = MoneyLogReason.LEGION_MEDITATION;
		int costCount = template.getCostNum();
		if (human.getWallet().costMoney(currencyType, costCount, reason, "")) {
			// 增加冥想力
			int meditation = GameServerAssist.getLegionTemplateManager()
					.getLegionMeditation(human.getLevel(),
							message.getMeditationType());
			human.getHumanTechnologyManager().addTechnologyPoints(meditation,
					true, TechPointLogReason.LEGION_MEDITATION_ADD_TECH_POINT,
					"");
			// 根据建筑等级收益加成
			LegionBuildingInfo buildingInfo = globalLegionManager
					.generateBuildingInfo(legion, LegionBuildingType.MEDITATION);
			int amendRate = (int) (1 + buildingInfo.getCurrentNum()
					/ SharedConstants.DEFAULT_FLOAT_BASE);
			// 获得贡献
			int contribution = template.getContribution() * amendRate;
			globalLegionManager.addSelfContribution(human, contribution, true);
			// 获得勋章
			int medal = template.getMedal() * amendRate;
			globalLegionManager.addSelfMedal(human, medal, true);
			// 增加军团资金
			int legionCoin = template.getLegionCoin() * amendRate;
			globalLegionManager.addLegionCoin(human, legionCoin, true);
			// 添加冥想日志
			LegionMeditationLog currentLog = new LegionMeditationLog();
			long now = GameServerAssist.getSystemTimeService().now();
			DateFormat format = new SimpleDateFormat("MM-dd hh:mm");
			String operateTime = format.format(new Date(now));
			String content = GameServerAssist.getSysLangService().read(
					LangConstants.LEGION_MEDITATION_LOG, operateTime,
					human.getName(), template.getName(), meditation,
					contribution, medal, legionCoin);
			currentLog.setContent(content);
			currentLog.setLegionId(legion.getId());
			currentLog.setOperateTime(now);
			globalLegionManager.addMeditationLog(legion, currentLog);

			// 设置已冥想
			human.setIsLegionMeditationed(true);
			// 发送消息
			GCLegionMeditation msg = new GCLegionMeditation();
			msg.setIsMeditationed(true);
			// 冥想日志
			LinkedList<LegionMeditationLog> returnLogList = new LinkedList<LegionMeditationLog>();
			for (LegionMeditationLog meditationLog : legion
					.getMeditationLogList()) {
				returnLogList.add(meditationLog);
			}
			// 由于插入冥想日志记录是回调方法，此时在列表中还没有刚加入的日志，要手动添加一下
			returnLogList.addFirst(currentLog);
			int size = returnLogList.size();
			if (size > SharedConstants.LEGION_MEDITATION_LOG_NUM) {
				returnLogList.removeLast();
			}
			size = returnLogList.size();
			String[] meditationLogs = new String[size];
			for (int i = 0; i < size; i++) {
				meditationLogs[i] = returnLogList.get(i).getContent();
			}
			msg.setMeditationLogs(meditationLogs);
			human.sendMessage(msg);
		}

	}
}
