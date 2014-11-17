package com.hifun.soul.gameserver.horoscope.handler;

import java.text.MessageFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hifun.soul.common.LogReasons.HoroscopeLogReason;
import com.hifun.soul.common.constants.LangConstants;
import com.hifun.soul.core.i18n.SysLangService;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.function.service.GameFuncService;
import com.hifun.soul.gameserver.horoscope.HoroscopeBagType;
import com.hifun.soul.gameserver.horoscope.manager.HumanHoroscopeManager;
import com.hifun.soul.gameserver.horoscope.msg.CGHoroscopeSwap;
import com.hifun.soul.gameserver.horoscope.msg.GCHoroscopeCompound;
import com.hifun.soul.gameserver.horoscope.msg.GCHoroscopeUpdateAndRemove;
import com.hifun.soul.gameserver.horoscope.msg.HoroscopeInfo;
import com.hifun.soul.gameserver.horoscope.service.HoroscopeTemplateManager;
import com.hifun.soul.gameserver.horoscope.template.HoroscopeTypeTemplate;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.player.Player;

@Component
public class CGHoroscopeSwapHandler implements
		IMessageHandlerWithType<CGHoroscopeSwap> {
	
	@Autowired
	private SysLangService sysLangService;
	@Autowired
	private HoroscopeTemplateManager templateManager;
	@Autowired
	private GameFuncService gameFuncService;

	@Override
	public short getMessageType() {
		return MessageType.CG_HOROSCOPE_SWAP;
	}

	@Override
	public void execute(CGHoroscopeSwap message) {
		Player player = message.getPlayer();
		if(player == null){
			return;
		}
		
		Human human = player.getHuman();
		if(human == null){
			return;
		}
		
		// 判断功能是否开放
		if(!gameFuncService.gameFuncIsOpen(human, GameFuncType.GAMBLE, true)){
			return;
		}
		
		HoroscopeBagType fromBagType = HoroscopeBagType.indexOf(message.getFromBagType());
		int fromBagIndex = message.getFromBagIndex();
		HoroscopeBagType toBagType = HoroscopeBagType.indexOf(message.getToBagType());
		int toBagIndex = message.getToBagIndex();
		// 不能直接从主背包到装备背包
		if (fromBagType == HoroscopeBagType.MAIN_BAG && toBagType == HoroscopeBagType.EQUIP_BAG) {
			return;
		}
		// 判断背包类型是否正确
		if(fromBagType == null
				|| fromBagIndex < 0
				|| toBagType == null
				|| toBagIndex < 0){
			return;
		}
		
		// 判断是否是相同位置
		if(fromBagType == toBagType
				&& fromBagIndex == toBagIndex){
			return;
		}
		
		HumanHoroscopeManager manager = human.getHumanHoroscopeManager();
		HoroscopeInfo fromHoroscopeInfo = manager.getHoroscopeInfo(fromBagType, fromBagIndex);
		HoroscopeInfo toHoroscopeInfo = manager.getHoroscopeInfo(toBagType, toBagIndex);
		
		// from星运不能为空
		if(fromHoroscopeInfo == null){
			return;
		}
		
		// 移到空位置
		if(toHoroscopeInfo == null){
			// 如果移动到位置是装备栏,则要判断装备栏是否已经开启
			if(toBagType == HoroscopeBagType.EQUIP_BAG){
				HoroscopeTypeTemplate template = templateManager.getHoroscopeTypeTemplate(toBagIndex);
				if(template == null){
					return;
				}
				
				if(human.getLevel() < template.getLevel()){
					human.sendErrorMessage(LangConstants.HOROSCOPE_SOUL_TYPE_CLOSED);
					return;
				}
				
				// 判断是否有相同效果的星运已经装备
				if(fromBagType != HoroscopeBagType.EQUIP_BAG
						&& manager.hasSameHoroscope(fromHoroscopeInfo.getKey())){
					human.sendErrorMessage(LangConstants.HOROSCOPE_SAME);
					return;
				}
			}
			else if(toBagType == HoroscopeBagType.STORAGE_BAG){
				if(toBagIndex >= manager.getHoroscopeStorageSize()){
					return;
				}
			}
			String param = MessageFormat.format(HoroscopeLogReason.MOVE.getReasonText(), fromBagType,fromBagIndex);
			HoroscopeInfo horoscopeInfo = manager.updateHoroscopeInfo(toBagType, toBagIndex, fromHoroscopeInfo, HoroscopeLogReason.MOVE, param);
			manager.removeHoroscopeInfo(fromBagType, fromBagIndex, null, "");
			
			GCHoroscopeUpdateAndRemove gcMsg = new GCHoroscopeUpdateAndRemove();
			gcMsg.setHoroscopeInfo(horoscopeInfo);
			gcMsg.setRemoveBagType(fromBagType.getIndex());
			gcMsg.setRemoveBagIndex(fromBagIndex);
			human.sendMessage(gcMsg);
		}
		// 星运合成
		else{
			// 发送星运合成确认框
			GCHoroscopeCompound gcHoroscopeCompound = new GCHoroscopeCompound();
			gcHoroscopeCompound.setFromBagType(fromBagType.getIndex());
			gcHoroscopeCompound.setFromBagIndex(fromBagIndex);
			gcHoroscopeCompound.setToBagType(toBagType.getIndex());
			gcHoroscopeCompound.setToBagIndex(toBagIndex);
			//2013/1/14 策划需求变更 为了变化最小先这么改
			HoroscopeInfo[] horoscopeInfos = manager.sortHoroscopeInfo(fromHoroscopeInfo, toHoroscopeInfo, false);
			if(horoscopeInfos == null
					|| horoscopeInfos.length != 2){
				return;
			}
//			if(toBagType == HoroscopeBagType.EQUIP_BAG
//					&& horoscopeInfos[0].getHoroscopeBag() != HoroscopeBagType.EQUIP_BAG.getIndex()
//					&& horoscopeInfos[0].getKey() != horoscopeInfos[1].getKey()
//					&& manager.hasSameHoroscope(horoscopeInfos[0].getKey())){
//				human.sendErrorMessage(LangConstants.HOROSCOPE_SAME);
//				return;
//			}
			if(horoscopeInfos[0].getNextHoroscopeId() <= 0){
				human.sendErrorMessage(LangConstants.HOROSCOPE_TOP, horoscopeInfos[0].getName());
				return;
			}
			String desc = sysLangService.read(LangConstants.HOROSCOPE_COMPOUND,
					horoscopeInfos[0].getName(),
					horoscopeInfos[1].getName(),
					horoscopeInfos[1].getExperience());
			gcHoroscopeCompound.setDesc(desc);
			human.sendMessage(gcHoroscopeCompound);
		}
		
	}

}
