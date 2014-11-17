package com.hifun.soul.gameserver.godsoul.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.core.template.BeanFieldNumber;

/**
 * 装备位等级
 * 
 * @author yandajun
 * 
 */
@ExcelRowBinding
public class EquipBitLevel {
	/** 需要的金币 */
	@BeanFieldNumber(number = 1)
	private int needCoin;

	public int getNeedCoin() {
		return needCoin;
	}

	public void setNeedCoin(int needCoin) {
		this.needCoin = needCoin;
	}

}
