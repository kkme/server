package com.hifun.soul.gameserver.activity.question.template;

import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelRowBinding;

/**
 * 问答积分兑换模板
 * 
 * @author magicstone
 *
 */
@ExcelRowBinding
public class QuestionScoreExchangeTemplate extends QuestionScoreExchangeTemplateVO {

	@Override
	public void check() throws TemplateConfigException {
		
	}

}
