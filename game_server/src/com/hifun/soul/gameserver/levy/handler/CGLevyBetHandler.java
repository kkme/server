package com.hifun.soul.gameserver.levy.handler;

import java.util.Timer;
import java.util.TimerTask;

import org.apache.commons.lang.math.RandomUtils;
import org.springframework.stereotype.Component;

import com.hifun.soul.common.LogReasons.MoneyLogReason;
import com.hifun.soul.common.constants.LangConstants;
import com.hifun.soul.common.constants.SharedConstants;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.core.util.TimeUtils;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.currency.CurrencyType;
import com.hifun.soul.gameserver.event.MainCityBetEvent;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.levy.enums.LevyBetType;
import com.hifun.soul.gameserver.levy.msg.CGLevyBet;
import com.hifun.soul.gameserver.levy.msg.GCLevyBet;
import com.hifun.soul.gameserver.vip.template.VipPrivilegeTemplate;

@Component
public class CGLevyBetHandler implements IMessageHandlerWithType<CGLevyBet> {
	private int[] randomPoints;

	@Override
	public short getMessageType() {
		return MessageType.CG_LEVY_BET;
	}

	@Override
	public void execute(CGLevyBet message) {
		Human human = message.getPlayer().getHuman();
		// 判断功能是否开启
		if (!GameServerAssist.getGameFuncService().gameFuncIsOpen(human,
				GameFuncType.MAIN_CITY_BET, true)) {
			return;
		}
		// 判断是否还有押注次数
		if (human.getLevyBetRemainNum() <= 0) {
			human.sendErrorMessage(LangConstants.HAVE_NO_BET_NUM);
			return;
		}
		LevyBetType betType = LevyBetType.indexOf(message.getBetType());
		switch (betType) {
		/** 必胜 */
		case WIN:
			if (!certainWinOperate(human)) {
				return;
			}
			break;
		/** 押大 */
		case BIG:
			// 修改筛子点数
			changePoints(human, betType);
			bigOperate(human);
			break;
		/** 押小 */
		case SMALL:
			// 修改筛子点数
			changePoints(human, betType);
			smallOperate(human);
			break;
		}
	}

	/**
	 * 必胜处理
	 */
	private boolean certainWinOperate(Human human) {
		int usedWinNum = human.getLevyCertainWinUsedNum();
		VipPrivilegeTemplate vipTemplate = GameServerAssist
				.getVipPrivilegeTemplateManager().getVipTemplate(
						human.getVipLevel());
		if (vipTemplate == null) {
			return false;
		}
		if (usedWinNum >= vipTemplate.getMaxLevyCertainWinTimes()) {
			human.sendErrorMessage(LangConstants.HAVE_NO_WIN_NUM);
			return false;
		}
		int costCount = GameServerAssist.getLevyTemplateManager()
				.getCertainWinCost(usedWinNum + 1);
		if (human.getWallet().costMoney(CurrencyType.CRYSTAL, costCount,
				MoneyLogReason.LEVY_BET_WIN, "")) {
			human.setLevyCertainWinUsedNum(human.getLevyCertainWinUsedNum() + 1);
			// 修改筛子点数
			changePoints(human, LevyBetType.WIN);
			// 发送消息
			sendBetResultInfo(human, LevyBetType.WIN, 1);
			return true;
		}
		return false;
	}

	/**
	 * 押大处理
	 */
	private void bigOperate(Human human) {
		// 失败
		if (sumOfPoints() < SharedConstants.LEVY_BET_BIG_POINT) {
			sendBetResultInfo(human, LevyBetType.BIG, 0);
		} else {// 成功
			sendBetResultInfo(human, LevyBetType.BIG, 1);
		}
	}

	/**
	 * 押小处理
	 */
	private void smallOperate(Human human) {
		// 失败
		if (sumOfPoints() >= SharedConstants.LEVY_BET_BIG_POINT) {
			sendBetResultInfo(human, LevyBetType.SMALL, 0);
		} else {// 成功
			sendBetResultInfo(human, LevyBetType.SMALL, 1);
		}
	}

	/**
	 * 随机骰子数
	 */
	private void changePoints(Human human, LevyBetType betType) {
		randomPoints = new int[SharedConstants.LEVY_BET_DICE_NUM];
		if (betType == LevyBetType.WIN) {
			randomPoints = new int[] { SharedConstants.LEVY_BET_DEFAULT_POINT,
					SharedConstants.LEVY_BET_DEFAULT_POINT,
					SharedConstants.LEVY_BET_DEFAULT_POINT };
		} else {
			for (int i = 0; i < randomPoints.length; i++) {
				int randPoint = RandomUtils.nextInt(6) + 1;
				randomPoints[i] = randPoint;
			}
		}
		human.getLevyManager().setBetPoints(randomPoints);
	}

	/**
	 * 骰子点数和
	 */
	private int sumOfPoints() {
		int sum = 0;
		for (int i : randomPoints) {
			sum += i;
		}
		return sum;
	}

	/**
	 * 响应押注消息
	 */
	private void sendBetResultInfo(Human human, LevyBetType betType, int result) {
		int addLevyExtraRate = GameServerAssist.getGameConstants()
				.getWinLevyExtraRate();
		if (result == 1) {
			int levyExtraRate = human.getLevyManager().getLevyExtraRate();
			if (levyExtraRate + addLevyExtraRate > GameServerAssist
					.getGameConstants().getMaxLeveExtraRate()) {
				levyExtraRate = GameServerAssist.getGameConstants()
						.getMaxLeveExtraRate();
			} else {
				levyExtraRate += addLevyExtraRate;
			}
			human.getLevyManager().setLevyExtraRate(levyExtraRate);
		}
		human.setLevyBetRemainNum(human.getLevyBetRemainNum() - 1);
		VipPrivilegeTemplate vipTemplate = GameServerAssist
				.getVipPrivilegeTemplateManager().getVipTemplate(
						human.getVipLevel());
		GCLevyBet msg = new GCLevyBet();
		msg.setBetPoints(randomPoints);
		msg.setRemainCertainWinNum(vipTemplate.getMaxLevyCertainWinTimes()
				- human.getLevyCertainWinUsedNum());
		msg.setCertainWinCost(GameServerAssist.getLevyTemplateManager()
				.getCertainWinCost(human.getLevyCertainWinUsedNum() + 1));
		msg.setLevyExtraRate(human.getLevyManager().getLevyExtraRate());
		msg.setRemainBetNum(human.getLevyBetRemainNum());
		msg.setResult(result);
		human.sendMessage(msg);
		sendBetNotifyMessage(human, betType, addLevyExtraRate, result);
		// 发送事件
		human.getEventBus().fireEvent(new MainCityBetEvent());
	}

	/**
	 * 延迟发送押注提示信息
	 */
	private void sendBetNotifyMessage(final Human human,
			final LevyBetType betType, final int levyExtraRate, final int result) {
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				int trainCoin = GameServerAssist.getLevyTemplateManager()
						.getTrainCoin(human.getLevel());
				switch (betType) {
				case WIN:
					human.sendImportantMessage(LangConstants.LEVY_CERTAIN_WIN,
							trainCoin, levyExtraRate / 100);
					break;
				case BIG:
				case SMALL:
					String betDesc = "";
					if (sumOfPoints() >= SharedConstants.LEVY_BET_BIG_POINT) {
						betDesc = GameServerAssist.getSysLangService().read(
								LangConstants.LEVY_BET_BIG);
					} else {
						betDesc = GameServerAssist.getSysLangService().read(
								LangConstants.LEVY_BET_SMALL);
					}
					if (result == 1) {
						human.sendImportantMessage(
								LangConstants.LEVY_BET_SUCCESS, sumOfPoints(),
								betDesc, trainCoin, levyExtraRate / 100);
					} else {
						human.sendImportantMessage(
								LangConstants.LEVY_BET_FAILD, sumOfPoints(),
								betDesc);
					}
					break;
				}
			}
		}, 4 * TimeUtils.SECOND);

	}
}
