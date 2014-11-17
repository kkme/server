package com.hifun.soul.gameserver.godsoul;

import com.hifun.soul.core.converter.IConverter;
import com.hifun.soul.gamedb.entity.HumanMagicPaperEntity;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.proto.common.MagicPapers.MagicPaper;

public class MagicPaperToEntityConverter implements
		IConverter<MagicPaperLevel, HumanMagicPaperEntity> {
	private Human _human;

	public MagicPaperToEntityConverter(Human human) {
		this._human = human;
	}

	@Override
	public HumanMagicPaperEntity convert(MagicPaperLevel src) {
		HumanMagicPaperEntity entity = new HumanMagicPaperEntity();
		entity.getBuilder().setHumanGuid(_human.getHumanGuid());
		entity.getBuilder().setMagicPaper(
				MagicPaper.newBuilder().setPaperId(src.getPaperId())
						.setLevel(src.getLevel()));
		return entity;
	}

	@Override
	public MagicPaperLevel reverseConvert(HumanMagicPaperEntity src) {
		throw new UnsupportedOperationException();
	}

}
