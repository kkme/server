package com.hifun.soul.gameserver.title.template;

import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelRowBinding;

@ExcelRowBinding
public class HumanTitleTemplate extends HumanTitleTemplateVO implements
		Comparable<HumanTitleTemplate> {

	@Override
	public void check() throws TemplateConfigException {

	}

	@Override
	public int compareTo(HumanTitleTemplate o) {
		if (o.getId() < id) {
			return 1;
		} else if (o.getId() > id) {
			return -1;
		} else {
			return 0;
		}
	}

}
