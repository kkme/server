package com.hifun.soul.gameserver.human.handler;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.human.msg.CGDistributePropertyPoint;
import com.hifun.soul.gameserver.player.Player;
import com.hifun.soul.gameserver.role.properties.manager.HumanPropertyManager;

@Component
public class CGDistributePropertyPointHandler implements
		IMessageHandlerWithType<CGDistributePropertyPoint> {

	@Override
	public short getMessageType() {
		return MessageType.CG_DISTRIBUTE_PROPERTY_POINT;
	}

	@Override
	public void execute(CGDistributePropertyPoint message) {
		Player player = message.getPlayer();
		if (player == null) {
			return;
		}
		Human human = player.getHuman();
		if (human == null) {
			return;
		}
		// 客户端传递的值
		int power = message.getPower();
		int agile = message.getAgile();
		int stamina = message.getStamina();
		int intelligence = message.getIntelligence();
		int spirit = message.getSpirit();

		// 原来的值
		int oldPower = human.getPower();
		int oldAgile = human.getAgile();
		int oldStamina = human.getStamina();
		int oldIntelligence = human.getIntelligence();
		int oldSpirit = human.getSpirit();
		// 不能比原来值小;
		if (power < oldPower) {
			return;
		}
		if (agile < oldAgile) {
			return;
		}
		if (stamina < oldStamina) {
			return;
		}
		if (intelligence < oldIntelligence) {
			return;
		}
		if (spirit < oldSpirit) {
			return;
		}

		if (power < 0 || agile < 0 || stamina < 0 || intelligence < 0
				|| spirit < 0) {
			return;
		}

		int propertyPoint = power + agile + stamina + intelligence + spirit;
		// 原来的未分配的属性点值
		int systemPoint = human.getUnDistributePropertyPoint();
		int totalPoint = systemPoint + human.getAgile() + human.getPower()
				+ human.getIntelligence() + human.getSpirit()
				+ human.getStamina();
		// 判断剩余属性点是否足够
		if (totalPoint < propertyPoint) {
			return;
		}

		HumanPropertyManager humanPropertyManager = human
				.getHumanPropertiesManager();

		// 分别设置各个属性
		if (power - oldPower > 0) {
			human.setSystemPower(human.getSystemPower() + power - oldPower);
			human.setPower(power);
			systemPoint -= (power - oldPower);
		}

		if (agile - oldAgile > 0) {
			human.setSystemAgile(human.getSystemAgile() + agile - oldAgile);
			human.setAgile(agile);
			systemPoint -= (agile - oldAgile);
		}

		if (stamina - oldStamina > 0) {
			human.setSystemStamina(human.getSystemtStamina() + stamina
					- oldStamina);
			human.setStamina(stamina);
			systemPoint -= (stamina - oldStamina);
		}

		if (intelligence - oldIntelligence > 0) {
			human.setSystemIntelligence(human.getSystemIntelligence()
					+ intelligence - oldIntelligence);
			human.setIntelligence(intelligence);
			systemPoint -= (intelligence - oldIntelligence);
		}

		if (spirit - oldSpirit > 0) {
			human.setSystemSpirit(human.getSystemSpirit() + spirit - oldSpirit);
			human.setSpirit(spirit);
			systemPoint -= (spirit - oldSpirit);
		}
		// 设置未分配的属性点;
		human.setUnDistributePropertyPoint(systemPoint);

		humanPropertyManager.recalculateInitProperties(human);
	}

}
