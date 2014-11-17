package com.hifun.soul.gameserver.item.template;

import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.core.util.StringUtils;

/**
 * 礼包模板
 * 
 * @author magicstone
 * 
 */
@ExcelRowBinding
public class SpreeTemplate extends SpreeTemplateVO {
	/** 星运ID和数量数组 */
	private int[] horoscopeIdArray = new int[0];
	private int[] horoscopeNumArray = new int[0];
	/** 精灵ID和精灵数组 */
	private int[] spriteIdArray = new int[0];
	private int[] spriteNumArray = new int[0];

	@Override
	public void check() throws TemplateConfigException {
		for (int i = items.size() - 1; i >= 0; i--) {
			if (items.get(i).getItemid() <= 0) {
				items.remove(i);
			}
		}
		if (horoscopeIds != null && StringUtils.trim(horoscopeIds).length() > 0) {
			String[] horosIds = StringUtils.trim(horoscopeIds).split(",");
			String[] horosNums = StringUtils.trim(horoscopeNums).split(",");
			if (horosIds.length != horosNums.length) {
				throw new TemplateConfigException(this.getSheetName(),
						this.getId(), "礼包中的星运id数组长度和星运数量数组长度不一致");
			}
			horoscopeIdArray = new int[horosIds.length];
			horoscopeNumArray = new int[horosIds.length];
			for (int i = 0; i < horosIds.length; i++) {
				horoscopeIdArray[i] = Integer.parseInt(horosIds[i]);
				horoscopeNumArray[i] = Integer.parseInt(horosNums[i]);
			}
		}
		if (spriteIds != null && StringUtils.trim(spriteIds).length() > 0) {
			String[] sIds = StringUtils.trim(spriteIds).split(",");
			String[] sNums = StringUtils.trim(spriteNums).split(",");
			if (sIds.length != sNums.length) {
				throw new TemplateConfigException(this.getSheetName(),
						this.getId(), "礼包中的精灵id数组长度和精灵数量数组长度不一致");
			}
			spriteIdArray = new int[sIds.length];
			spriteNumArray = new int[sIds.length];
			for (int i = 0; i < sIds.length; i++) {
				spriteIdArray[i] = Integer.parseInt(sIds[i]);
				spriteNumArray[i] = Integer.parseInt(sNums[i]);
			}
		}
	}

	public int[] getHoroscopeIdArray() {
		return horoscopeIdArray;
	}

	public int[] getHoroscopeNumArray() {
		return horoscopeNumArray;
	}

	public int[] getSpriteIdArray() {
		return spriteIdArray;
	}

	public int[] getSpriteNumArray() {
		return spriteNumArray;
	}

}
