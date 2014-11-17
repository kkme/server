package com.hifun.soul.gameserver.sprite;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hifun.soul.core.annotation.AutoCreateClientEnumType;
import com.hifun.soul.core.annotation.ClientEnumComment;
import com.hifun.soul.core.enums.IndexedEnum;
import com.hifun.soul.gameserver.currency.CurrencyType;
import com.hifun.soul.gameserver.sprite.template.SpriteCostTemplate;
import com.hifun.soul.gameserver.sprite.template.SpriteTemplate;

/**
 * 對酒類型;
 * 
 * @author crazyjohn
 * 
 */
@AutoCreateClientEnumType
public enum FingerGuessType implements IndexedEnum {
	/** 普通对酒 */
	@ClientEnumComment(comment = "普通对酒 ")
	COMMON(1) {
		@Override
		public int getCostNum(SpriteCostTemplate pageTemplate) {
			return pageTemplate.getCommonCostNum();
		}

		@Override
		public CurrencyType getCurrencyType(SpriteCostTemplate pageTemplate) {
			return CurrencyType.indexOf(pageTemplate.getCommonCostType());
		}

		@Override
		public List<Integer> getRateList(List<SpriteTemplate> templateList) {
			List<Integer> result = new ArrayList<Integer>();
			for (SpriteTemplate each : templateList) {
				result.add(each.getCommonFingerGuessRatio());
			}
			return result;
		}
	},
	/** 普通对酒 */
	@ClientEnumComment(comment = "普通对酒 ")
	SUPER(2) {
		@Override
		public int getCostNum(SpriteCostTemplate pageTemplate) {
			return pageTemplate.getSuperCostNum();
		}

		@Override
		public CurrencyType getCurrencyType(SpriteCostTemplate pageTemplate) {
			return CurrencyType.indexOf(pageTemplate.getSuperCostType());
		}

		@Override
		public List<Integer> getRateList(List<SpriteTemplate> templateList) {
			List<Integer> result = new ArrayList<Integer>();
			for (SpriteTemplate each : templateList) {
				result.add(each.getSuperFingerGuessRatio());
			}
			return result;
		}
	};
	private int type;
	private static Map<Integer, FingerGuessType> types = new HashMap<Integer, FingerGuessType>();;

	static {
		for (FingerGuessType each : FingerGuessType.values()) {
			types.put(each.getIndex(), each);
		}
	}

	FingerGuessType(int type) {
		this.type = type;
	}

	@Override
	public int getIndex() {
		return type;
	}

	public static FingerGuessType typeOf(int guessType) {
		return types.get(guessType);
	}

	public abstract int getCostNum(SpriteCostTemplate pageTemplate);

	public abstract CurrencyType getCurrencyType(SpriteCostTemplate pageTemplate);

	public abstract List<Integer> getRateList(List<SpriteTemplate> templateList);
}
