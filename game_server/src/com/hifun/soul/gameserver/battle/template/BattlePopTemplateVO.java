package com.hifun.soul.gameserver.battle.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.core.template.TemplateObject;
import com.hifun.soul.core.template.ExcelCollectionMapping;
import java.util.List;

/**
 * 战斗泡泡
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public abstract class BattlePopTemplateVO extends TemplateObject {

	/**  pop内容 */
	@ExcelCollectionMapping(clazz = String.class, collectionNumber = "1;2;3;4;5")
	protected List<String> words;


	public List<String> getWords() {
		return this.words;
	}

	public void setWords(List<String> words) {
		this.words = words;
	}
	

	@Override
	public String toString() {
		return "BattlePopTemplateVO[words=" + words + ",]";

	}
}