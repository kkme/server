package com.hifun.soul.gameserver.sprite.msg.handler;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hifun.soul.common.constants.LangConstants;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.core.template.TemplateService;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.player.Player;
import com.hifun.soul.gameserver.sprite.SpriteQualityType;
import com.hifun.soul.gameserver.sprite.model.SpritePubInfo;
import com.hifun.soul.gameserver.sprite.msg.CGRecruitSprite;
import com.hifun.soul.gameserver.sprite.msg.GCRecruitSprite;
import com.hifun.soul.gameserver.sprite.template.SpriteRecruitTemplate;
import com.hifun.soul.gameserver.sprite.template.SpriteTemplateManager;

/**
 * 处理招募精灵逻辑;
 * 
 * @author crazyjohn
 * 
 */
@Component
public class CGRecruitSpriteHandler implements
		IMessageHandlerWithType<CGRecruitSprite> {
	@Autowired
	private TemplateService templateService;
	@Autowired
	private SpriteTemplateManager templateManager;

	@Override
	public short getMessageType() {
		return MessageType.CG_RECRUIT_SPRITE;
	}

	@Override
	public void execute(CGRecruitSprite message) {
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
				GameFuncType.SPRITE, true)) {
			return;
		}
		// 招募类型
		int recruitType = ((message.getRecruitType() <= 0) ? SpriteQualityType.SPRITE_GREEN
				.getIndex() : message.getRecruitType());
		SpriteRecruitTemplate recruitTemplate = templateService.get(
				recruitType, SpriteRecruitTemplate.class);
		if (recruitTemplate == null) {
			return;
		}
		// 1. 等级是否满足
		if (human.getLevel() < recruitTemplate.getOpenLevel()) {
			return;
		}
		// 2. 指定的精魂是否足够;
		SpriteQualityType qualityType = SpriteQualityType.typeOf(recruitType);
		if (!qualityType.isSoulEnough(human, recruitTemplate)) {
			// 给提示然后返回
			human.sendErrorMessage(LangConstants.SPRITE_SOUL_NOT_ENOUTH);
			return;
		}
		// TODO : crazyjohn 日志记录下资源的去向
		// 精灵背包是否有位置可以放
		if (!human.getHumanSpriteManager().haveSpriteCellSpace()) {
			// 给提示然后返回
			human.sendErrorMessage(LangConstants.SPRITE_BAG_NO_SPACE);
			return;
		}
		qualityType.costSoul(human, recruitTemplate);
		// 3. 抽出精灵;
		List<SpritePubInfo> spriteList = templateManager
				.getSpritePubInfoByQuality(recruitType);
		// 把抽取到的精灵放到精灵背包里;
		SpritePubInfo sprite = getSuitableSprite(spriteList, message.getSpriteId());
		human.getHumanSpriteManager().putToBag(sprite, false);
		// 4. 给响应
		GCRecruitSprite recruitSpriteMessage = new GCRecruitSprite();
		recruitSpriteMessage.setRecruitType(recruitType);
		recruitSpriteMessage.setSpriteId(sprite.getSpriteId());
		human.sendMessage(recruitSpriteMessage);
	}

	/**
	 * 获取合适id的精灵;
	 * @param spriteList
	 * @param spriteId
	 * @return
	 */
	private SpritePubInfo getSuitableSprite(List<SpritePubInfo> spriteList,
			int spriteId) {
		for (SpritePubInfo eachSprite : spriteList) {
			if (eachSprite.getSpriteId() == spriteId) {
				return eachSprite;
			}
		}
		return null;
	}

}
