package com.hifun.soul.gameserver.prison.converter;

import com.hifun.soul.core.converter.IConverter;
import com.hifun.soul.gamedb.entity.PrisonerEntity;
import com.hifun.soul.gameserver.prison.Prisoner;

public class PrisonerToEntityConverter implements
		IConverter<Prisoner, PrisonerEntity> {

	@Override
	public PrisonerEntity convert(Prisoner prisoner) {
		PrisonerEntity entity = new PrisonerEntity();
		entity.setId((Long) prisoner.getHumanId());
		entity.setIdentityType(prisoner.getIdentityType());
		entity.setHumanName(prisoner.getHumanName());
		entity.setHumanLevel(prisoner.getHumanLevel());
		entity.setMasterId(prisoner.getMasterId());
		entity.setArrestedNum(prisoner.getArrestedNum());
		entity.setBuyArrestNum(prisoner.getBuyArrestNum());
		entity.setRescuedNum(prisoner.getRescuedNum());
		entity.setInteractedNum(prisoner.getInteractedNum());
		entity.setExtractedExperience(prisoner.getExtractedExperience());
		entity.setForHelpedNum(prisoner.getForHelpedNum());
		entity.setRevoltedNum(prisoner.getRevoltedNum());
		entity.setEnemyIds(prisoner.getEnemyIds());
		entity.setArrestIds(prisoner.getLooserIds());
		entity.setBeSlaveTime(prisoner.getBeSlaveTime());
		entity.setLastBeInteractedTime(prisoner.getLastBeInteractedTime());
		entity.setLastBeExtractedTime(prisoner.getLastBeExtractedTime());
		entity.setLastInteractTime(prisoner.getLastInteractTime());
		entity.setBeSlaveSelfLevel(prisoner.getBeSlaveSelfLevel());
		entity.setBeSlaveMasterLevel(prisoner.getBeSlaveMasterLevel());
		entity.setOffLineExperience(prisoner.getOffLineExperience());
		return entity;
	}

	@Override
	public Prisoner reverseConvert(PrisonerEntity prisonerEntity) {
		Prisoner prisoner = new Prisoner();
		prisoner.setHumanId((Long) prisonerEntity.getId());
		prisoner.setIdentityType(prisonerEntity.getIdentityType());
		prisoner.setHumanName(prisonerEntity.getHumanName());
		prisoner.setHumanLevel(prisonerEntity.getHumanLevel());
		prisoner.setMasterId(prisonerEntity.getMasterId());
		prisoner.setArrestedNum(prisonerEntity.getArrestedNum());
		prisoner.setBuyArrestNum(prisonerEntity.getBuyArrestNum());
		prisoner.setRescuedNum(prisonerEntity.getRescuedNum());
		prisoner.setInteractedNum(prisonerEntity.getInteractedNum());
		prisoner.setExtractedExperience(prisonerEntity.getExtractedExperience());
		prisoner.setForHelpedNum(prisonerEntity.getForHelpedNum());
		prisoner.setRevoltedNum(prisonerEntity.getRevoltedNum());
		prisoner.setEnemyIds(prisonerEntity.getEnemyIds());
		prisoner.setLooserIds(prisonerEntity.getArrestIds());
		prisoner.setBeSlaveTime(prisonerEntity.getBeSlaveTime());
		prisoner.setLastBeInteractedTime(prisonerEntity
				.getLastBeInteractedTime());
		prisoner.setLastBeExtractedTime(prisonerEntity.getLastBeExtractedTime());
		prisoner.setLastInteractTime(prisonerEntity.getLastInteractTime());
		prisoner.setBeSlaveSelfLevel(prisonerEntity.getBeSlaveSelfLevel());
		prisoner.setBeSlaveMasterLevel(prisonerEntity.getBeSlaveMasterLevel());
		prisoner.setOffLineExperience(prisonerEntity.getOffLineExperience());
		return prisoner;
	}

}
