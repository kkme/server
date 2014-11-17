package com.hifun.soul.gameserver.rank.model;

import com.hifun.soul.gamedb.entity.HonorRankEntity;
import com.hifun.soul.gamedb.entity.RankEntity;
import com.hifun.soul.gamedb.entity.TitleRankEntity;
import com.hifun.soul.gamedb.entity.VipRankEntity;

/**
 * 排行榜业务对象
 * 
 * @author magicstone
 * 
 */
public abstract class HumanRankData extends RankData {
	protected long humanGuid;
	protected String humanName;
	protected int occupation;
	/** 军衔 */
	protected int title;
	protected String titleName;
	protected int level;
	protected long legionId;
	protected String legionName;
	/** 黄钻等级 */
	protected int yellowVipLevel;
	/** 是否年费黄钻用户 */
	protected boolean isYearYellowVip;
	/** 是否豪华黄钻用户 */
	protected boolean isYellowHighVip;

	public HumanRankData() {

	}

	public HumanRankData(RankEntity entity) {
		this.humanGuid = (Long) entity.getId();
		this.humanName = entity.getHumanName();
		this.occupation = entity.getOccupation();
		this.title = entity.getHonor();
		this.level = entity.getLevel();
		this.legionId = entity.getLegionId();
		this.legionName = entity.getLegionName();
	}

	public HumanRankData(TitleRankEntity entity) {
		this.humanGuid = (Long) entity.getId();
		this.humanName = entity.getHumanName();
		this.occupation = entity.getOccupation();
		this.title = entity.getHonor();
		this.level = entity.getLevel();
		this.legionId = entity.getLegionId();
		this.legionName = entity.getLegionName();
	}

	public HumanRankData(HonorRankEntity entity) {
		this.humanGuid = (Long) entity.getId();
		this.humanName = entity.getHumanName();
		this.occupation = entity.getOccupation();
		this.title = entity.getHonor();
		this.level = entity.getLevel();
		this.legionId = entity.getLegionId();
		this.legionName = entity.getLegionName();
	}

	public HumanRankData(VipRankEntity entity) {
		this.humanGuid = (Long) entity.getId();
		this.humanName = entity.getHumanName();
		this.occupation = entity.getOccupation();
		this.title = entity.getHonor();
		this.level = entity.getLevel();
		this.legionId = entity.getLegionId();
		this.legionName = entity.getLegionName();
	}

	public long getHumanGuid() {
		return humanGuid;
	}

	public void setHumanGuid(long humanGuid) {
		this.humanGuid = humanGuid;
	}

	public String getHumanName() {
		return humanName;
	}

	public void setHumanName(String humanName) {
		this.humanName = humanName;
	}

	public int getOccupation() {
		return occupation;
	}

	public void setOccupation(int occupation) {
		this.occupation = occupation;
	}

	public int getTitle() {
		return title;
	}

	public void setTitle(int title) {
		this.title = title;
	}

	public String getTitleName() {
		return titleName;
	}

	public void setTitleName(String titleName) {
		this.titleName = titleName;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public long getLegionId() {
		return legionId;
	}

	public void setLegionId(long legionId) {
		this.legionId = legionId;
	}

	public String getLegionName() {
		return legionName;
	}

	public void setLegionName(String legionName) {
		this.legionName = legionName;
	}

	public int getYellowVipLevel() {
		return yellowVipLevel;
	}

	public void setYellowVipLevel(int yellowVipLevel) {
		this.yellowVipLevel = yellowVipLevel;
	}

	public boolean getIsYearYellowVip() {
		return isYearYellowVip;
	}

	public void setIsYearYellowVip(boolean isYearYellowVip) {
		this.isYearYellowVip = isYearYellowVip;
	}

	public boolean getIsYellowHighVip() {
		return isYellowHighVip;
	}

	public void setIsYellowHighVip(boolean isYellowHighVip) {
		this.isYellowHighVip = isYellowHighVip;
	}

}
