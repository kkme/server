package com.hifun.soul.gameserver.sprite.msg.handler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hifun.soul.common.LogReasons.MoneyLogReason;
import com.hifun.soul.common.constants.LangConstants;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.core.template.TemplateService;
import com.hifun.soul.core.util.MathUtils;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.currency.CurrencyType;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.player.Player;
import com.hifun.soul.gameserver.sprite.FingerGuessType;
import com.hifun.soul.gameserver.sprite.model.SpritePubInfo;
import com.hifun.soul.gameserver.sprite.msg.CGFingerGuessing;
import com.hifun.soul.gameserver.sprite.msg.GCFingerGuessing;
import com.hifun.soul.gameserver.sprite.template.SpriteCostTemplate;
import com.hifun.soul.gameserver.sprite.template.SpriteTemplate;
import com.hifun.soul.gameserver.sprite.template.SpriteTemplateManager;

/**
 * 处理请求开始对酒;
 * 
 * @author crazyjohn
 * 
 */
@Component
public class CGFingerGuessingHandler implements
		IMessageHandlerWithType<CGFingerGuessing> {
	/** 开始索引 */
	private static final int COMMON_BEGIN = 0;
	/** 需要抽选的精灵个数 */
	private static final int SELECT_COUNT = 3;
	@Autowired
	private TemplateService templateService;
	@Autowired
	private SpriteTemplateManager templateManager;

	@Override
	public short getMessageType() {
		return MessageType.CG_FINGER_GUESSING;
	}

	@Override
	public void execute(CGFingerGuessing message) {
		// 开始对酒;1. 是否有足够的货币; 2. 扣除货币; 3. 根据概率抽取3个人
		Player player = message.getPlayer();
		if (player == null) {
			return;
		}
		Human human = player.getHuman();
		if (human == null) {
			return;
		}
		// 功能是否开启
		if (!GameServerAssist.getGameFuncService().gameFuncIsOpen(human,
				GameFuncType.SPRITE_PUB, true)) {
			return;
		}
		if (message.getPageId() <= 0) {
			return;
		}
		// 取出页签模版
		SpriteCostTemplate pageTemplate = templateService.get(
				message.getPageId(), SpriteCostTemplate.class);
		if (pageTemplate == null) {
			return;
		}
		// 对酒类型
		FingerGuessType guessType = FingerGuessType.typeOf(message
				.getGuessType());
		if (guessType == null) {
			return;
		}
		// 判断当前是否还有酒没对完
		if (!human.getHumanSpritePubManager().finishedLashFingerGuess()) {
			return;
		}
		CurrencyType currencyType = guessType.getCurrencyType(pageTemplate);
		int costCount = guessType.getCostNum(pageTemplate);
		// 判断是否有足够的货币
		if (!human.getWallet().isEnough(currencyType, costCount)) {
			human.sendWarningMessage(LangConstants.COMMON_NOT_ENOUGH,
					currencyType.getDesc());
			return;
		}
		// 扣除货币, 抽取人
		reduceCurrencyAndStartFingerGuess(human, pageTemplate, currencyType,
				costCount, guessType);
	}

	/**
	 * 扣除掉货币, 然后开始对酒;
	 * 
	 * @param human
	 * 
	 * @param pageTemplate
	 * @param currencyType
	 * @param costCount
	 * @param guessType
	 * 
	 */
	private void reduceCurrencyAndStartFingerGuess(Human human,
			SpriteCostTemplate pageTemplate, CurrencyType currencyType,
			int costCount, FingerGuessType guessType) {
		// 扣钱
		if (!human.getWallet().costMoney(currencyType, costCount,
				MoneyLogReason.FINGER_GUESS, guessType.toString())) {
			return;
		}
		// 设置对酒类型
		human.setSpritePubGuessType(guessType.getIndex());
		// 开始对酒;
		GCFingerGuessing guessMessage = new GCFingerGuessing();
		guessMessage.setCurrentIndex(COMMON_BEGIN);
		guessMessage.setIsLastGuess(false);
		// 抽取精灵列表;
		List<SpritePubInfo> selectedSprites = extractSprites(human, guessType,
				pageTemplate);
		guessMessage.setSelectedList(selectedSprites
				.toArray(new SpritePubInfo[0]));
		guessMessage.setSucceedCrystalCost(human.getHumanSpritePubManager()
				.getSucceedCrystalCost());
		guessMessage.setRemainSucceedNum(human.getHumanSpritePubManager()
				.getRemainSucceedNum());
		human.sendMessage(guessMessage);
	}

	/**
	 * 抽取精灵列表;
	 * 
	 * @param human
	 * 
	 * @param guessType
	 *            对酒类型;
	 * @param pageTemplate
	 *            页签模版;
	 * @return
	 */
	private List<SpritePubInfo> extractSprites(Human human,
			FingerGuessType guessType, SpriteCostTemplate pageTemplate) {
		Map<Integer, SpriteTemplate> idToSpriteTemplate = new HashMap<Integer, SpriteTemplate>();
		List<SpriteTemplate> templateList = templateManager
				.getSpriteTemplateByPageId(pageTemplate.getId());
		List<Integer> rateList = guessType.getRateList(templateList);
		while (idToSpriteTemplate.size() < SELECT_COUNT) {
			int selectedIndex = MathUtils.random(rateList
					.toArray(new Integer[0]));
			idToSpriteTemplate.put(templateList.get(selectedIndex).getId(),
					templateList.get(selectedIndex));
		}
		return human.getHumanSpritePubManager().buildFingerGuessSpriteList(
				idToSpriteTemplate);
	}

}
