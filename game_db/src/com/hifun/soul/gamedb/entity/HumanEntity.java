package com.hifun.soul.gamedb.entity;

import java.io.IOException;
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.mina.common.ByteBuffer;

import com.google.protobuf.InvalidProtocolBufferException;
import com.hifun.soul.core.orm.BaseProtobufEntity;
import com.hifun.soul.gamedb.annotation.BlobSubHumanEntity;
import com.hifun.soul.proto.common.EliteStage.HumanEliteStageInfo;
import com.hifun.soul.proto.common.Mine.HumanMineInfo;
import com.hifun.soul.proto.common.Warrior.HumanWarriorInfo;
import com.hifun.soul.proto.common.YellowVip.HumanYellowVipRewardStateInfo;
import com.hifun.soul.proto.data.entity.Entity.Human;
import com.hifun.soul.proto.data.entity.Entity.Human.Builder;
import com.hifun.soul.proto.data.entity.Entity.HumanBaseProperties;
import com.hifun.soul.proto.data.entity.Entity.HumanCarriedSkill;
import com.hifun.soul.proto.data.entity.Entity.HumanCd;
import com.hifun.soul.proto.data.entity.Entity.HumanCostNotify;
import com.hifun.soul.proto.data.entity.Entity.HumanDailyQuestRewardBox;
import com.hifun.soul.proto.data.entity.Entity.HumanGift;
import com.hifun.soul.proto.data.entity.Entity.HumanGodsoul;
import com.hifun.soul.proto.data.entity.Entity.HumanGuide;
import com.hifun.soul.proto.data.entity.Entity.HumanHoroscope;
import com.hifun.soul.proto.data.entity.Entity.HumanItem;
import com.hifun.soul.proto.data.entity.Entity.HumanLegionMineBattleReward;
import com.hifun.soul.proto.data.entity.Entity.HumanLegionTask;
import com.hifun.soul.proto.data.entity.Entity.HumanLoginReward;
import com.hifun.soul.proto.data.entity.Entity.HumanMagicPaper;
import com.hifun.soul.proto.data.entity.Entity.HumanMainCityInfo;
import com.hifun.soul.proto.data.entity.Entity.HumanMarsLoser;
import com.hifun.soul.proto.data.entity.Entity.HumanMarsRoom;
import com.hifun.soul.proto.data.entity.Entity.HumanMeditation;
import com.hifun.soul.proto.data.entity.Entity.HumanNostrum;
import com.hifun.soul.proto.data.entity.Entity.HumanOtherProperties;
import com.hifun.soul.proto.data.entity.Entity.HumanPubSprite;
import com.hifun.soul.proto.data.entity.Entity.HumanQuestData;
import com.hifun.soul.proto.data.entity.Entity.HumanQuestFinishData;
import com.hifun.soul.proto.data.entity.Entity.HumanRefineMap;
import com.hifun.soul.proto.data.entity.Entity.HumanRefineStage;
import com.hifun.soul.proto.data.entity.Entity.HumanSign;
import com.hifun.soul.proto.data.entity.Entity.HumanSingleRechargeReward;
import com.hifun.soul.proto.data.entity.Entity.HumanSkillSlot;
import com.hifun.soul.proto.data.entity.Entity.HumanSpecialLoginReward;
import com.hifun.soul.proto.data.entity.Entity.HumanSpecialShopItem;
import com.hifun.soul.proto.data.entity.Entity.HumanSprite;
import com.hifun.soul.proto.data.entity.Entity.HumanSpriteBagCell;
import com.hifun.soul.proto.data.entity.Entity.HumanSpriteBuff;
import com.hifun.soul.proto.data.entity.Entity.HumanSpriteSlot;
import com.hifun.soul.proto.data.entity.Entity.HumanStageDrama;
import com.hifun.soul.proto.data.entity.Entity.HumanStageMapState;
import com.hifun.soul.proto.data.entity.Entity.HumanStageReward;
import com.hifun.soul.proto.data.entity.Entity.HumanStageStar;
import com.hifun.soul.proto.data.entity.Entity.HumanStageStarReward;
import com.hifun.soul.proto.data.entity.Entity.HumanStarMap;
import com.hifun.soul.proto.data.entity.Entity.HumanStargazer;
import com.hifun.soul.proto.data.entity.Entity.HumanTarget;
import com.hifun.soul.proto.data.entity.Entity.HumanTechnology;
import com.hifun.soul.proto.data.entity.Entity.HumanTotalRechargeReward;

/**
 * 角色实体
 * 
 * @author crazyjohn
 * 
 */
@Entity
@Table(name = "t_human")
public class HumanEntity extends BaseProtobufEntity<Human.Builder> {

	private static final int DEFAULT_BUFFER_SIZE = 64;
	/**
	 * 分表存储的问答数据;
	 */
	private HumanQuestionEntity questionEntity;
	/**
	 * 分表存储的匹配战个人数据;
	 */
	private HumanMatchBattleEntity matchBattleRoleEntity;

	protected HumanEntity(Builder builder) {
		super(builder);
	}

	public HumanEntity() {
		super(Human.newBuilder());
	}

	@Id
	@Override
	public Long getId() {
		return builder.getGuid();
	}

	@Override
	public void setId(Serializable id) {
		this.builder.setGuid((Long) id);
	}

	@Column
	public String getName() {
		return this.builder.getBaseProperties().getName();
	}

	public void setName(String name) {
		this.builder.getBasePropertiesBuilder().setName(name);
	}

	@Column
	public long getPassportId() {
		return this.builder.getBaseProperties().getPassportId();
	}

	public void setPassportId(long passportId) {
		this.builder.getBasePropertiesBuilder().setPassportId(passportId);
	}

	@Column
	@BlobSubHumanEntity(getSubEntityClass = HumanQuestDataEntity.class, getSubFieldDesc = "humanQuestData")
	public byte[] getHumanQuestData() throws IOException {
		ByteBuffer buffer = allocateByteBuffer();
		for (HumanQuestData.Builder eachQuest : this.builder
				.getHumanQuestBuilderList()) {
			HumanQuestDataEntity entity = new HumanQuestDataEntity(eachQuest);
			entity.write(buffer);
		}
		byte[] results = new byte[buffer.position()];
		buffer.flip();
		buffer.get(results);
		return results;
	}

	public void setHumanQuestData(byte[] questDatas) throws IOException {
		if (questDatas != null) {
			ByteBuffer questDetailBuf = ByteBuffer.wrap(questDatas);
			while (questDetailBuf.hasRemaining()) {
				HumanQuestDataEntity questDetail = new HumanQuestDataEntity();
				questDetail.read(questDetailBuf);
				this.builder.addHumanQuest(questDetail.getBuilder());
			}
		}
	}

	/**
	 * 设置任务完成数据;
	 * 
	 * @param questFinishDatas
	 * @throws IOException
	 */
	public void setHumanQuestFinishData(byte[] questFinishDatas)
			throws IOException {
		if (questFinishDatas != null) {
			ByteBuffer questFinishBuf = ByteBuffer.wrap(questFinishDatas);
			while (questFinishBuf.hasRemaining()) {
				HumanQuestFinishDataEntity questFinish = new HumanQuestFinishDataEntity();
				questFinish.read(questFinishBuf);
				this.builder.addHumanFinishQuest(questFinish.getBuilder());
			}
		}
	}

	/**
	 * 获取任务完成数据;
	 * 
	 * @return
	 * @throws IOException
	 */
	@Column
	@BlobSubHumanEntity(getSubEntityClass = HumanQuestFinishDataEntity.class, getSubFieldDesc = "humanQuestFinishData")
	public byte[] getHumanQuestFinishData() throws IOException {
		ByteBuffer buffer = allocateByteBuffer();
		for (HumanQuestFinishData.Builder eachQuest : this.builder
				.getHumanFinishQuestBuilderList()) {
			HumanQuestFinishDataEntity entity = new HumanQuestFinishDataEntity(
					eachQuest);
			entity.write(buffer);
		}
		byte[] results = new byte[buffer.position()];
		buffer.flip();
		buffer.get(results);
		return results;
	}

	public void setBaseProperties(byte[] baseProperties) throws IOException {
		this.builder.setBaseProperties(HumanBaseProperties
				.parseFrom(baseProperties));
	}

	@Column
	@BlobSubHumanEntity(getSubEntityClass = HumanBasePropertiesEntity.class, getSubFieldDesc = "baseProperties")
	public byte[] getBaseProperties() {
		return this.builder.getBaseProperties().toByteArray();
	}

	public void setOtherProperties(byte[] otherProperties) throws IOException {
		this.builder.setOtherProperties(HumanOtherProperties
				.parseFrom(otherProperties));
	}

	@Column
	@BlobSubHumanEntity(getSubEntityClass = HumanOtherPropertiesEntity.class, getSubFieldDesc = "otherProperties")
	public byte[] getOtherProperties() {
		return this.builder.getOtherProperties().toByteArray();
	}

	@Column
	@BlobSubHumanEntity(getSubEntityClass = HumanItemEntity.class, getSubFieldDesc = "humanItems")
	public byte[] getHumanItems() throws IOException {
		ByteBuffer buffer = allocateByteBuffer();
		for (HumanItem.Builder item : this.builder.getItemBuilderList()) {
			HumanItemEntity itemEntity = new HumanItemEntity(item);
			itemEntity.write(buffer);
		}
		byte[] result = new byte[buffer.position()];
		buffer.flip();
		buffer.get(result);
		return result;
	}

	public void setHumanItems(byte[] items) throws IOException {
		if (items != null) {
			ByteBuffer buffer = ByteBuffer.wrap(items);
			while (buffer.hasRemaining()) {
				HumanItemEntity entity = new HumanItemEntity();
				entity.read(buffer);
				this.builder.addItem(entity.getBuilder());
			}
		}
	}

	@Column
	@BlobSubHumanEntity(getSubEntityClass = HumanHoroscopeEntity.class, getSubFieldDesc = "humanHoroscope")
	public byte[] getHumanHoroscope() throws IOException {
		ByteBuffer buffer = allocateByteBuffer();
		for (HumanHoroscope.Builder horoscope : this.builder
				.getHoroscopeBuilderList()) {
			HumanHoroscopeEntity horoscopeEntity = new HumanHoroscopeEntity(
					horoscope);
			horoscopeEntity.write(buffer);
		}
		byte[] result = new byte[buffer.position()];
		buffer.flip();
		buffer.get(result);
		return result;
	}

	public void setHumanHoroscope(byte[] horoscopes) throws IOException {
		if (horoscopes != null) {
			ByteBuffer buffer = ByteBuffer.wrap(horoscopes);
			while (buffer.hasRemaining()) {
				HumanHoroscopeEntity entity = new HumanHoroscopeEntity();
				entity.read(buffer);
				this.builder.addHoroscope(entity.getBuilder());
			}
		}
	}

	@Column
	@BlobSubHumanEntity(getSubEntityClass = HumanStargazerEntity.class, getSubFieldDesc = "humanStargazer")
	public byte[] getHumanStargazer() throws IOException {
		ByteBuffer buffer = allocateByteBuffer();
		for (HumanStargazer.Builder stargazer : this.builder
				.getStargazerBuilderList()) {
			HumanStargazerEntity stargazerEntity = new HumanStargazerEntity(
					stargazer);
			stargazerEntity.write(buffer);
		}
		byte[] result = new byte[buffer.position()];
		buffer.flip();
		buffer.get(result);
		return result;
	}

	public void setHumanStargazer(byte[] stargazers) throws IOException {
		if (stargazers != null) {
			ByteBuffer buffer = ByteBuffer.wrap(stargazers);
			while (buffer.hasRemaining()) {
				HumanStargazerEntity entity = new HumanStargazerEntity();
				entity.read(buffer);
				this.builder.addStargazer(entity.getBuilder());
			}
		}
	}

	@Column
	@BlobSubHumanEntity(getSubEntityClass = HumanTechnologyEntity.class, getSubFieldDesc = "humanTechnology")
	public byte[] getHumanTechnology() throws IOException {
		ByteBuffer buffer = allocateByteBuffer();
		for (HumanTechnology.Builder technology : this.builder
				.getTechnologyBuilderList()) {
			HumanTechnologyEntity technologyEntity = new HumanTechnologyEntity(
					technology);
			technologyEntity.write(buffer);
		}
		byte[] result = new byte[buffer.position()];
		buffer.flip();
		buffer.get(result);
		return result;
	}

	public void setHumanTechnology(byte[] technologys) throws IOException {
		if (technologys != null) {
			ByteBuffer buffer = ByteBuffer.wrap(technologys);
			while (buffer.hasRemaining()) {
				HumanTechnologyEntity entity = new HumanTechnologyEntity();
				entity.read(buffer);
				this.builder.addTechnology(entity.getBuilder());
			}
		}
	}

	@Column
	@BlobSubHumanEntity(getSubEntityClass = HumanGodsoulEntity.class, getSubFieldDesc = "humanGodsoul")
	public byte[] getHumanGodsoul() throws IOException {
		ByteBuffer buffer = allocateByteBuffer();
		for (HumanGodsoul.Builder technology : this.builder
				.getGodsoulBuilderList()) {
			HumanGodsoulEntity technologyEntity = new HumanGodsoulEntity(
					technology);
			technologyEntity.write(buffer);
		}
		byte[] result = new byte[buffer.position()];
		buffer.flip();
		buffer.get(result);
		return result;
	}

	public void setHumanGodsoul(byte[] technologys) throws IOException {
		if (technologys != null) {
			ByteBuffer buffer = ByteBuffer.wrap(technologys);
			while (buffer.hasRemaining()) {
				HumanGodsoulEntity entity = new HumanGodsoulEntity();
				entity.read(buffer);
				this.builder.addGodsoul(entity.getBuilder());
			}
		}
	}

	public void setHumanDailyRewardBox(byte[] dailyRewardBox)
			throws IOException {
		if (dailyRewardBox != null) {
			ByteBuffer buffer = ByteBuffer.wrap(dailyRewardBox);
			while (buffer.hasRemaining()) {
				HumanDailyQuestRewardBoxEntity entity = new HumanDailyQuestRewardBoxEntity();
				entity.read(buffer);
				this.builder.addDailyRewardBox(entity.getBuilder());
			}
		}
	}

	@Column
	@BlobSubHumanEntity(getSubEntityClass = HumanDailyQuestRewardBoxEntity.class, getSubFieldDesc = "humanDailyRewardBox")
	public byte[] getHumanDailyRewardBox() throws IOException {
		ByteBuffer buffer = allocateByteBuffer();
		for (HumanDailyQuestRewardBox.Builder box : this.builder
				.getDailyRewardBoxBuilderList()) {
			HumanDailyQuestRewardBoxEntity boxEntity = new HumanDailyQuestRewardBoxEntity(
					box);
			boxEntity.write(buffer);
		}
		byte[] result = new byte[buffer.position()];
		buffer.flip();
		buffer.get(result);
		return result;
	}

	public void setHumanGuide(byte[] guides) throws IOException {
		if (guides != null) {
			ByteBuffer buffer = ByteBuffer.wrap(guides);
			while (buffer.hasRemaining()) {
				HumanGuideEntity entity = new HumanGuideEntity();
				entity.read(buffer);
				this.builder.addGuide(entity.getBuilder());
			}
		}
	}

	@Column
	@BlobSubHumanEntity(getSubEntityClass = HumanGuideEntity.class, getSubFieldDesc = "humanGuide")
	public byte[] getHumanGuide() throws IOException {
		ByteBuffer buffer = allocateByteBuffer();
		for (HumanGuide.Builder guide : this.builder.getGuideBuilderList()) {
			HumanGuideEntity guideEntity = new HumanGuideEntity(guide);
			guideEntity.write(buffer);
		}
		byte[] result = new byte[buffer.position()];
		buffer.flip();
		buffer.get(result);
		return result;
	}

	public void setHumanStageReward(byte[] stageRewards) throws IOException {
		if (stageRewards != null) {
			ByteBuffer buffer = ByteBuffer.wrap(stageRewards);
			while (buffer.hasRemaining()) {
				HumanStageRewardEntity entity = new HumanStageRewardEntity();
				entity.read(buffer);
				this.builder.addStageReward(entity.getBuilder());
			}
		}
	}

	@Column
	@BlobSubHumanEntity(getSubEntityClass = HumanStageRewardEntity.class, getSubFieldDesc = "humanStageReward")
	public byte[] getHumanStageReward() throws IOException {
		ByteBuffer buffer = allocateByteBuffer();
		for (HumanStageReward.Builder stageReward : this.builder
				.getStageRewardBuilderList()) {
			HumanStageRewardEntity stageRewardEntity = new HumanStageRewardEntity(
					stageReward);
			stageRewardEntity.write(buffer);
		}
		byte[] result = new byte[buffer.position()];
		buffer.flip();
		buffer.get(result);
		return result;
	}

	public void setHumanStageMapState(byte[] stageMapStates) throws IOException {
		if (stageMapStates != null) {
			ByteBuffer buffer = ByteBuffer.wrap(stageMapStates);
			while (buffer.hasRemaining()) {
				HumanStageMapStateEntity entity = new HumanStageMapStateEntity();
				entity.read(buffer);
				this.builder.addStageMapState(entity.getBuilder());
			}
		}
	}

	@Column
	@BlobSubHumanEntity(getSubEntityClass = HumanStageMapStateEntity.class, getSubFieldDesc = "humanStageMapState")
	public byte[] getHumanStageMapState() throws IOException {
		ByteBuffer buffer = allocateByteBuffer();
		for (HumanStageMapState.Builder stageMapState : this.builder
				.getStageMapStateBuilderList()) {
			HumanStageMapStateEntity stageMapStateEntity = new HumanStageMapStateEntity(
					stageMapState);
			stageMapStateEntity.write(buffer);
		}
		byte[] result = new byte[buffer.position()];
		buffer.flip();
		buffer.get(result);
		return result;
	}

	public void setHumanStageDrama(byte[] stageDramas) throws IOException {
		if (stageDramas != null) {
			ByteBuffer buffer = ByteBuffer.wrap(stageDramas);
			while (buffer.hasRemaining()) {
				HumanStageDramaEntity entity = new HumanStageDramaEntity();
				entity.read(buffer);
				this.builder.addStageDrama(entity.getBuilder());
			}
		}
	}

	@Column
	@BlobSubHumanEntity(getSubEntityClass = HumanStageDramaEntity.class, getSubFieldDesc = "humanStageDrama")
	public byte[] getHumanStageDrama() throws IOException {
		ByteBuffer buffer = allocateByteBuffer();
		for (HumanStageDrama.Builder stageDrama : this.builder
				.getStageDramaBuilderList()) {
			HumanStageDramaEntity stageDramaEntity = new HumanStageDramaEntity(
					stageDrama);
			stageDramaEntity.write(buffer);
		}
		byte[] result = new byte[buffer.position()];
		buffer.flip();
		buffer.get(result);
		return result;
	}

	public void setHumanCd(byte[] cds) throws IOException {
		if (cds != null) {
			ByteBuffer buffer = ByteBuffer.wrap(cds);
			while (buffer.hasRemaining()) {
				HumanCdEntity entity = new HumanCdEntity();
				entity.read(buffer);
				this.builder.addCd(entity.getBuilder());
			}
		}
	}

	@Column
	@BlobSubHumanEntity(getSubEntityClass = HumanCdEntity.class, getSubFieldDesc = "humanCd")
	public byte[] getHumanCd() throws IOException {
		ByteBuffer buffer = allocateByteBuffer();
		for (HumanCd.Builder cd : this.builder.getCdBuilderList()) {
			HumanCdEntity cdEntity = new HumanCdEntity(cd);
			cdEntity.write(buffer);
		}
		byte[] result = new byte[buffer.position()];
		buffer.flip();
		buffer.get(result);
		return result;
	}

	private ByteBuffer allocateByteBuffer(int size) {
		return ByteBuffer.allocate(size).setAutoExpand(true);
	}

	private ByteBuffer allocateByteBuffer() {
		return allocateByteBuffer(DEFAULT_BUFFER_SIZE);
	}

	@Column
	@BlobSubHumanEntity(getSubEntityClass = HumanCarriedSkillEntity.class, getSubFieldDesc = "humanCarriedSkills")
	public byte[] getHumanCarriedSkills() throws IOException {
		ByteBuffer buffer = allocateByteBuffer();
		for (HumanCarriedSkill.Builder skill : this.builder
				.getSkillBuilderList()) {
			HumanCarriedSkillEntity skillEntity = new HumanCarriedSkillEntity(
					skill);
			skillEntity.write(buffer);
		}
		byte[] result = new byte[buffer.position()];
		buffer.flip();
		buffer.get(result);
		return result;
	}

	public void setHumanCarriedSkills(byte[] skillDatas) throws IOException {
		if (skillDatas != null) {
			ByteBuffer buffer = ByteBuffer.wrap(skillDatas);
			while (buffer.hasRemaining()) {
				HumanCarriedSkillEntity entity = new HumanCarriedSkillEntity();
				entity.read(buffer);
				this.builder.addSkill(entity.getBuilder());
			}
		}
	}

	@Column
	@BlobSubHumanEntity(getSubEntityClass = HumanSkillSlotEntity.class, getSubFieldDesc = "humanSkillSlots")
	public byte[] getHumanSkillSlots() throws IOException {
		ByteBuffer buffer = allocateByteBuffer();
		for (HumanSkillSlot.Builder slot : this.builder.getSlotBuilderList()) {
			HumanSkillSlotEntity slotEntity = new HumanSkillSlotEntity(slot);
			slotEntity.write(buffer);
		}
		byte[] result = new byte[buffer.position()];
		buffer.flip();
		buffer.get(result);
		return result;
	}

	public void setHumanSkillSlots(byte[] slotDatas) throws IOException {
		if (slotDatas != null) {
			ByteBuffer buffer = ByteBuffer.wrap(slotDatas);
			while (buffer.hasRemaining()) {
				HumanSkillSlotEntity entity = new HumanSkillSlotEntity();
				entity.read(buffer);
				this.builder.addSlot(entity.getBuilder());
			}
		}
	}

	@Column
	@BlobSubHumanEntity(getSubEntityClass = HumanLoginRewardEntity.class, getSubFieldDesc = "loginRewards")
	public byte[] getLoginRewards() throws IOException {
		ByteBuffer buffer = allocateByteBuffer();
		for (HumanLoginReward.Builder loginReward : this.builder
				.getLoginRewardBuilderList()) {
			HumanLoginRewardEntity entity = new HumanLoginRewardEntity(
					loginReward);
			entity.write(buffer);
		}
		byte[] result = new byte[buffer.position()];
		buffer.flip();
		buffer.get(result);
		return result;
	}

	public void setLoginRewards(byte[] loginRewards) throws IOException {
		if (loginRewards != null) {
			ByteBuffer buffer = ByteBuffer.wrap(loginRewards);
			while (buffer.hasRemaining()) {
				HumanLoginRewardEntity entity = new HumanLoginRewardEntity();
				entity.read(buffer);
				this.builder.addLoginReward(entity.getBuilder());
			}
		}
	}

	@Column
	@BlobSubHumanEntity(getSubEntityClass = HumanCostNotifyEntity.class, getSubFieldDesc = "costNotifys")
	public byte[] getCostNotifys() throws IOException {
		ByteBuffer buffer = allocateByteBuffer();
		for (HumanCostNotify.Builder costNotify : this.builder
				.getCostNotifyBuilderList()) {
			HumanCostNotifyEntity entity = new HumanCostNotifyEntity(costNotify);
			entity.write(buffer);
		}
		byte[] result = new byte[buffer.position()];
		buffer.flip();
		buffer.get(result);
		return result;
	}

	public void setCostNotifys(byte[] costNotifys) throws IOException {
		if (costNotifys != null) {
			ByteBuffer buffer = ByteBuffer.wrap(costNotifys);
			while (buffer.hasRemaining()) {
				HumanCostNotifyEntity entity = new HumanCostNotifyEntity();
				entity.read(buffer);
				this.builder.addCostNotify(entity.getBuilder());
			}
		}
	}

	@Column
	@BlobSubHumanEntity(getSubEntityClass = HumanSpecialShopItemEntity.class, getSubFieldDesc = "specialShopItems")
	public byte[] getSpecialShopItems() throws IOException {
		ByteBuffer buffer = allocateByteBuffer();
		for (HumanSpecialShopItem.Builder specialShopItem : this.builder
				.getSpecialShopItemBuilderList()) {
			HumanSpecialShopItemEntity entity = new HumanSpecialShopItemEntity(
					specialShopItem);
			entity.write(buffer);
		}
		byte[] result = new byte[buffer.position()];
		buffer.flip();
		buffer.get(result);
		return result;
	}

	public void setSpecialShopItems(byte[] specialShopItems) throws IOException {
		if (specialShopItems != null) {
			ByteBuffer buffer = ByteBuffer.wrap(specialShopItems);
			while (buffer.hasRemaining()) {
				HumanSpecialShopItemEntity entity = new HumanSpecialShopItemEntity();
				entity.read(buffer);
				this.builder.addSpecialShopItem(entity.getBuilder());
			}
		}
	}

	/**
	 * 获取冥想数据
	 * 
	 * @return
	 */
	@Column
	@BlobSubHumanEntity(getSubEntityClass = HumanMeditationEntity.class, getSubFieldDesc = "meditation")
	public byte[] getMeditation() {
		if (this.builder.getMeditation() == null) {
			return new byte[0];
		}
		return this.builder.getMeditation().toByteArray();
	}

	/**
	 * 设置冥想数据
	 * 
	 * @param meditation
	 * @throws InvalidProtocolBufferException
	 */
	public void setMeditation(byte[] meditation)
			throws InvalidProtocolBufferException {
		if (meditation == null || meditation.length == 0) {
			return;
		}
		this.builder.setMeditation(HumanMeditation.parseFrom(meditation));
	}

	/**
	 * 获取精英副本信息
	 * 
	 * @return
	 */
	@Column
	@BlobSubHumanEntity(getSubEntityClass = HumanEliteStageEntity.class, getSubFieldDesc = "eliteStageInfo")
	public byte[] getEliteStageInfo() {
		if (this.builder.getEliteStageInfo() == null) {
			return new byte[0];
		}
		return this.builder.getEliteStageInfo().toByteArray();
	}

	/**
	 * 设置精英副本信息
	 * 
	 * @param meditation
	 * @throws InvalidProtocolBufferException
	 */
	public void setEliteStageInfo(byte[] eliteStageInfo)
			throws InvalidProtocolBufferException {
		if (eliteStageInfo == null || eliteStageInfo.length == 0) {
			return;
		}
		this.builder.setEliteStageInfo(HumanEliteStageInfo
				.parseFrom(eliteStageInfo));
	}

	/**
	 * 获取矿场信息
	 * 
	 * @return
	 */
	@Column
	@BlobSubHumanEntity(getSubEntityClass = HumanMineEntity.class, getSubFieldDesc = "mine")
	public byte[] getMine() {
		if (this.builder.getMine() == null) {
			return new byte[0];
		}
		return this.builder.getMine().toByteArray();
	}

	/**
	 * 设置矿场信息
	 * 
	 * @param meditation
	 * @throws InvalidProtocolBufferException
	 */
	public void setMine(byte[] mine) throws InvalidProtocolBufferException {
		if (mine == null || mine.length == 0) {
			return;
		}
		this.builder.setMine(HumanMineInfo.parseFrom(mine));
	}

	@Column
	@BlobSubHumanEntity(getSubEntityClass = HumanMainCityEntity.class, getSubFieldDesc = "mainCityInfo")
	public byte[] getMainCityInfo() {
		if (this.builder.getMainCityInfo() == null) {
			return new byte[0];
		}
		return this.builder.getMainCityInfo().toByteArray();
	}

	public void setMainCityInfo(byte[] mainCityInfo)
			throws InvalidProtocolBufferException {
		if (mainCityInfo == null || mainCityInfo.length == 0) {
			return;
		}
		this.builder.setMainCityInfo(HumanMainCityInfo.parseFrom(mainCityInfo));
	}

	@Column
	@BlobSubHumanEntity(getSubEntityClass = HumanStageStarEntity.class, getSubFieldDesc = "stageStars")
	public byte[] getStageStars() throws IOException {
		ByteBuffer buffer = allocateByteBuffer();
		for (HumanStageStar.Builder stageStar : this.builder
				.getStageStarBuilderList()) {
			HumanStageStarEntity entity = new HumanStageStarEntity(stageStar);
			entity.write(buffer);
		}
		byte[] result = new byte[buffer.position()];
		buffer.flip();
		buffer.get(result);
		return result;
	}

	public void setStageStars(byte[] stageStars) throws IOException {
		if (stageStars != null) {
			ByteBuffer buffer = ByteBuffer.wrap(stageStars);
			while (buffer.hasRemaining()) {
				HumanStageStarEntity entity = new HumanStageStarEntity();
				entity.read(buffer);
				this.builder.addStageStar(entity.getBuilder());
			}
		}
	}

	@Column
	@BlobSubHumanEntity(getSubEntityClass = HumanStageStarRewardEntity.class, getSubFieldDesc = "stageStarRewards")
	public byte[] getStageStarRewards() throws IOException {
		ByteBuffer buffer = allocateByteBuffer();
		for (HumanStageStarReward.Builder stageStarReward : this.builder
				.getStageStarRewardBuilderList()) {
			HumanStageStarRewardEntity entity = new HumanStageStarRewardEntity(
					stageStarReward);
			entity.write(buffer);
		}
		byte[] result = new byte[buffer.position()];
		buffer.flip();
		buffer.get(result);
		return result;
	}

	public void setStageStarRewards(byte[] stageStarRewards) throws IOException {
		if (stageStarRewards != null) {
			ByteBuffer buffer = ByteBuffer.wrap(stageStarRewards);
			while (buffer.hasRemaining()) {
				HumanStageStarRewardEntity entity = new HumanStageStarRewardEntity();
				entity.read(buffer);
				this.builder.addStageStarReward(entity.getBuilder());
			}
		}
	}

	@Transient
	public HumanQuestionEntity getQuestionEntity() {
		return questionEntity;
	}

	public void setQuestionEntity(HumanQuestionEntity questionEntity) {
		this.questionEntity = questionEntity;
	}

	@Column
	@BlobSubHumanEntity(getSubEntityClass = HumanGiftEntity.class, getSubFieldDesc = "humanGift")
	public byte[] getHumanGift() throws IOException {
		ByteBuffer buffer = allocateByteBuffer();
		for (HumanGift.Builder gift : this.builder.getGiftBuilderList()) {
			HumanGiftEntity giftEntity = new HumanGiftEntity(gift);
			giftEntity.write(buffer);
		}
		byte[] result = new byte[buffer.position()];
		buffer.flip();
		buffer.get(result);
		return result;
	}

	public void setHumanGift(byte[] gifts) throws IOException {
		if (gifts != null) {
			ByteBuffer buffer = ByteBuffer.wrap(gifts);
			while (buffer.hasRemaining()) {
				HumanGiftEntity entity = new HumanGiftEntity();
				entity.read(buffer);
				this.builder.addGift(entity.getBuilder());
			}
		}
	}

	public void setRefineMaps(byte[] refineMaps) throws IOException {
		if (refineMaps != null) {
			ByteBuffer buffer = ByteBuffer.wrap(refineMaps);
			while (buffer.hasRemaining()) {
				HumanRefineMapEntity entity = new HumanRefineMapEntity();
				entity.read(buffer);
				this.builder.addRefineMap(entity.getBuilder());
			}
		}
	}

	@Column
	@BlobSubHumanEntity(getSubEntityClass = HumanRefineMapEntity.class, getSubFieldDesc = "refineMaps")
	public byte[] getRefineMaps() throws IOException {
		ByteBuffer buffer = allocateByteBuffer();
		for (HumanRefineMap.Builder refineMap : this.builder
				.getRefineMapBuilderList()) {
			HumanRefineMapEntity entity = new HumanRefineMapEntity(refineMap);
			entity.write(buffer);
		}
		byte[] result = new byte[buffer.position()];
		buffer.flip();
		buffer.get(result);
		return result;
	}

	public void setRefineStages(byte[] refineStages) throws IOException {
		if (refineStages != null) {
			ByteBuffer buffer = ByteBuffer.wrap(refineStages);
			while (buffer.hasRemaining()) {
				HumanRefineStageEntity entity = new HumanRefineStageEntity();
				entity.read(buffer);
				this.builder.addRefineStage(entity.getBuilder());
			}
		}
	}

	@Column
	@BlobSubHumanEntity(getSubEntityClass = HumanRefineStageEntity.class, getSubFieldDesc = "refineStages")
	public byte[] getRefineStages() throws IOException {
		ByteBuffer buffer = allocateByteBuffer();
		for (HumanRefineStage.Builder refineStage : this.builder
				.getRefineStageBuilderList()) {
			HumanRefineStageEntity entity = new HumanRefineStageEntity(
					refineStage);
			entity.write(buffer);
		}
		byte[] result = new byte[buffer.position()];
		buffer.flip();
		buffer.get(result);
		return result;
	}

	@Column
	@BlobSubHumanEntity(getSubEntityClass = HumanSpecialLoginRewardEntity.class, getSubFieldDesc = "specialLoginRewards")
	public byte[] getSpecialLoginRewards() throws IOException {
		ByteBuffer buffer = allocateByteBuffer();
		for (HumanSpecialLoginReward.Builder specialLoginReward : this.builder
				.getSpecialLoginRewardBuilderList()) {
			HumanSpecialLoginRewardEntity entity = new HumanSpecialLoginRewardEntity(
					specialLoginReward);
			entity.write(buffer);
		}
		byte[] result = new byte[buffer.position()];
		buffer.flip();
		buffer.get(result);
		return result;
	}

	public void setSpecialLoginRewards(byte[] specialLoginRewards)
			throws IOException {
		if (specialLoginRewards != null) {
			ByteBuffer buffer = ByteBuffer.wrap(specialLoginRewards);
			while (buffer.hasRemaining()) {
				HumanSpecialLoginRewardEntity entity = new HumanSpecialLoginRewardEntity();
				entity.read(buffer);
				this.builder.addSpecialLoginReward(entity.getBuilder());
			}
		}
	}

	@Column
	@BlobSubHumanEntity(getSubEntityClass = HumanWarriorEntity.class, getSubFieldDesc = "warriorInfo")
	public byte[] getWarriorInfo() {
		if (builder.getWarriorInfo() == null) {
			return new byte[0];
		}
		return builder.getWarriorInfo().toByteArray();
	}

	public void setWarriorInfo(byte[] warriorEntity)
			throws InvalidProtocolBufferException {
		if (warriorEntity == null || warriorEntity.length == 0) {
			return;
		}
		this.builder.setWarriorInfo(HumanWarriorInfo.parseFrom(warriorEntity));
	}

	@Column
	@BlobSubHumanEntity(getSubEntityClass = HumanYellowVipRewardStateEntity.class, getSubFieldDesc = "yellowVipRewardState")
	public byte[] getYellowVipRewardState() {
		if (builder.getYellowVipRewardState() == null) {
			return new byte[0];
		}
		return builder.getYellowVipRewardState().toByteArray();
	}

	public void setYellowVipRewardState(byte[] yellowVipRewardStateEntity)
			throws InvalidProtocolBufferException {
		if (yellowVipRewardStateEntity == null
				|| yellowVipRewardStateEntity.length == 0) {
			return;
		}
		this.builder.setYellowVipRewardState(HumanYellowVipRewardStateInfo
				.parseFrom(yellowVipRewardStateEntity));
	}

	@Transient
	public HumanMatchBattleEntity getMatchBattleEntity() {
		return matchBattleRoleEntity;
	}

	public void setMatchBattleEntity(
			HumanMatchBattleEntity matchBattleRoleEntity) {
		this.matchBattleRoleEntity = matchBattleRoleEntity;
	}

	@Column
	@BlobSubHumanEntity(getSubEntityClass = HumanPubSpriteEntity.class, getSubFieldDesc = "humanPubSprite")
	public byte[] getHumanPubSprite() throws IOException {
		ByteBuffer buffer = allocateByteBuffer();
		for (HumanPubSprite.Builder eachPubSprite : this.builder
				.getPubSpiteBuilderList()) {
			HumanPubSpriteEntity spriteEntity = new HumanPubSpriteEntity(
					eachPubSprite);
			spriteEntity.write(buffer);
		}
		byte[] result = new byte[buffer.position()];
		buffer.flip();
		buffer.get(result);
		return result;
	}

	public void setHumanPubSprite(byte[] pubSprites) throws IOException {
		if (pubSprites != null) {
			ByteBuffer buffer = ByteBuffer.wrap(pubSprites);
			while (buffer.hasRemaining()) {
				HumanPubSpriteEntity entity = new HumanPubSpriteEntity();
				entity.read(buffer);
				this.builder.addPubSpite(entity.getBuilder());
			}
		}
	}

	@Column
	@BlobSubHumanEntity(getSubEntityClass = HumanSpriteEntity.class, getSubFieldDesc = "humanSprite")
	public byte[] getHumanSprite() throws IOException {
		ByteBuffer buffer = allocateByteBuffer();
		for (HumanSprite.Builder eachSprite : this.builder
				.getSpriteBuilderList()) {
			HumanSpriteEntity spriteEntity = new HumanSpriteEntity(eachSprite);
			spriteEntity.write(buffer);
		}
		byte[] result = new byte[buffer.position()];
		buffer.flip();
		buffer.get(result);
		return result;
	}

	public void setHumanSprite(byte[] sprites) throws IOException {
		if (sprites != null) {
			ByteBuffer buffer = ByteBuffer.wrap(sprites);
			while (buffer.hasRemaining()) {
				HumanSpriteEntity entity = new HumanSpriteEntity();
				entity.read(buffer);
				this.builder.addSprite(entity.getBuilder());
			}
		}
	}

	@Column
	@BlobSubHumanEntity(getSubEntityClass = HumanSpriteBuffEntity.class, getSubFieldDesc = "humanSpriteBuff")
	public byte[] getHumanSpriteBuff() throws IOException {
		ByteBuffer buffer = allocateByteBuffer();
		for (HumanSpriteBuff.Builder eachBuff : this.builder
				.getBuffBuilderList()) {
			HumanSpriteBuffEntity spriteEntity = new HumanSpriteBuffEntity(
					eachBuff);
			spriteEntity.write(buffer);
		}
		byte[] result = new byte[buffer.position()];
		buffer.flip();
		buffer.get(result);
		return result;
	}

	public void setHumanSpriteBuff(byte[] buffs) throws IOException {
		if (buffs != null) {
			ByteBuffer buffer = ByteBuffer.wrap(buffs);
			while (buffer.hasRemaining()) {
				HumanSpriteBuffEntity entity = new HumanSpriteBuffEntity();
				entity.read(buffer);
				this.builder.addBuff(entity.getBuilder());
			}
		}
	}

	@Column
	@BlobSubHumanEntity(getSubEntityClass = HumanSpriteBagCellEntity.class, getSubFieldDesc = "humanSpriteBagCell")
	public byte[] getHumanSpriteBagCell() throws IOException {
		ByteBuffer buffer = allocateByteBuffer();
		for (HumanSpriteBagCell.Builder eachCell : this.builder
				.getCellBuilderList()) {
			HumanSpriteBagCellEntity spriteEntity = new HumanSpriteBagCellEntity(
					eachCell);
			spriteEntity.write(buffer);
		}
		byte[] result = new byte[buffer.position()];
		buffer.flip();
		buffer.get(result);
		return result;
	}

	public void setHumanSpriteBagCell(byte[] cells) throws IOException {
		if (cells != null) {
			ByteBuffer buffer = ByteBuffer.wrap(cells);
			while (buffer.hasRemaining()) {
				HumanSpriteBagCellEntity entity = new HumanSpriteBagCellEntity();
				entity.read(buffer);
				this.builder.addCell(entity.getBuilder());
			}
		}
	}

	@Column
	@BlobSubHumanEntity(getSubEntityClass = HumanSpriteSlotEntity.class, getSubFieldDesc = "humanSpriteSlot")
	public byte[] getHumanSpriteSlot() throws IOException {
		ByteBuffer buffer = allocateByteBuffer();
		for (HumanSpriteSlot.Builder eachSlot : this.builder
				.getEquipSlotBuilderList()) {
			HumanSpriteSlotEntity spriteEntity = new HumanSpriteSlotEntity(
					eachSlot);
			spriteEntity.write(buffer);
		}
		byte[] result = new byte[buffer.position()];
		buffer.flip();
		buffer.get(result);
		return result;
	}

	public void setHumanSpriteSlot(byte[] slots) throws IOException {
		if (slots != null) {
			ByteBuffer buffer = ByteBuffer.wrap(slots);
			while (buffer.hasRemaining()) {
				HumanSpriteSlotEntity entity = new HumanSpriteSlotEntity();
				entity.read(buffer);
				this.builder.addEquipSlot(entity.getBuilder());
			}
		}
	}

	@Column
	@BlobSubHumanEntity(getSubEntityClass = HumanStarMapEntity.class, getSubFieldDesc = "humanStarMap")
	public byte[] getHumanStarMap() throws IOException {
		ByteBuffer buffer = allocateByteBuffer();
		for (HumanStarMap.Builder eachStarMap : this.builder
				.getStarMapBuilderList()) {
			HumanStarMapEntity starMapEntity = new HumanStarMapEntity(
					eachStarMap);
			starMapEntity.write(buffer);
		}
		byte[] result = new byte[buffer.position()];
		buffer.flip();
		buffer.get(result);
		return result;
	}

	public void setHumanStarMap(byte[] starMaps) throws IOException {
		if (starMaps != null) {
			ByteBuffer buffer = ByteBuffer.wrap(starMaps);
			while (buffer.hasRemaining()) {
				HumanStarMapEntity entity = new HumanStarMapEntity();
				entity.read(buffer);
				this.builder.addStarMap(entity.getBuilder());
			}
		}
	}

	@Column
	@BlobSubHumanEntity(getSubEntityClass = HumanSignEntity.class, getSubFieldDesc = "humanSign")
	public byte[] getHumanSign() throws IOException {
		ByteBuffer buffer = allocateByteBuffer();
		for (HumanSign.Builder sign : this.builder.getSignBuilderList()) {
			HumanSignEntity signEntity = new HumanSignEntity(sign);
			signEntity.write(buffer);
		}
		byte[] result = new byte[buffer.position()];
		buffer.flip();
		buffer.get(result);
		return result;
	}

	public void setHumanSign(byte[] signs) throws IOException {
		if (signs != null) {
			ByteBuffer buffer = ByteBuffer.wrap(signs);
			while (buffer.hasRemaining()) {
				HumanSignEntity entity = new HumanSignEntity();
				entity.read(buffer);
				this.builder.addSign(entity.getBuilder());
			}
		}
	}

	@Column
	@BlobSubHumanEntity(getSubEntityClass = HumanSingleRechargeRewardEntity.class, getSubFieldDesc = "humanSingleRechargeReward")
	public byte[] getHumanSingleRechargeReward() throws IOException {
		ByteBuffer buffer = allocateByteBuffer();
		for (HumanSingleRechargeReward.Builder singleRechargeReward : this.builder.getSingleRechargeRewardBuilderList()) {
			HumanSingleRechargeRewardEntity rechargeRewardEntity = new HumanSingleRechargeRewardEntity(singleRechargeReward);
			rechargeRewardEntity.write(buffer);
		}
		byte[] result = new byte[buffer.position()];
		buffer.flip();
		buffer.get(result);
		return result;
	}

	public void setHumanSingleRechargeReward(byte[] rechargeRewards) throws IOException {
		if (rechargeRewards != null) {
			ByteBuffer buffer = ByteBuffer.wrap(rechargeRewards);
			while (buffer.hasRemaining()) {
				HumanSingleRechargeRewardEntity entity = new HumanSingleRechargeRewardEntity();
				entity.read(buffer);
				this.builder.addSingleRechargeReward(entity.getBuilder());
			}
		}
	}
	
	@Column
	@BlobSubHumanEntity(getSubEntityClass = HumanTotalRechargeRewardEntity.class, getSubFieldDesc = "humanTotalRechargeReward")
	public byte[] getHumanTotalRechargeReward() throws IOException {
		ByteBuffer buffer = allocateByteBuffer();
		for (HumanTotalRechargeReward.Builder totalRechargeReward : this.builder.getTotalRechargeRewardBuilderList()) {
			HumanTotalRechargeRewardEntity totalRechargeRewardEntity = new HumanTotalRechargeRewardEntity(totalRechargeReward);
			totalRechargeRewardEntity.write(buffer);
		}
		byte[] result = new byte[buffer.position()];
		buffer.flip();
		buffer.get(result);
		return result;
	}

	public void setHumanTotalRechargeReward(byte[] totalRechargeRewards) throws IOException {
		if (totalRechargeRewards != null) {
			ByteBuffer buffer = ByteBuffer.wrap(totalRechargeRewards);
			while (buffer.hasRemaining()) {
				HumanTotalRechargeRewardEntity entity = new HumanTotalRechargeRewardEntity();
				entity.read(buffer);
				this.builder.addTotalRechargeReward(entity.getBuilder());
			}
		}
	}
	
	@Column
	@BlobSubHumanEntity(getSubEntityClass = HumanMarsRoomEntity.class, getSubFieldDesc = "humanMarsRoom")
	public byte[] getHumanMarsRoom() throws IOException {
		ByteBuffer buffer = allocateByteBuffer();
		for (HumanMarsRoom.Builder marsRoom : this.builder.getMarsRoomBuilderList()) {
			HumanMarsRoomEntity marsRoomEntity = new HumanMarsRoomEntity(marsRoom);
			marsRoomEntity.write(buffer);
		}
		byte[] result = new byte[buffer.position()];
		buffer.flip();
		buffer.get(result);
		return result;
	}

	public void setHumanMarsRoom(byte[] marsRooms) throws IOException {
		if (marsRooms != null) {
			ByteBuffer buffer = ByteBuffer.wrap(marsRooms);
			while (buffer.hasRemaining()) {
				HumanMarsRoomEntity entity = new HumanMarsRoomEntity();
				entity.read(buffer);
				this.builder.addMarsRoom(entity.getBuilder());
			}
		}
	}
	
	@Column
	@BlobSubHumanEntity(getSubEntityClass = HumanMarsLoserEntity.class, getSubFieldDesc = "humanMarsRoom")
	public byte[] getHumanMarsLoser() throws IOException {
		ByteBuffer buffer = allocateByteBuffer();
		for (HumanMarsLoser.Builder marsLoser : this.builder.getMarsLoserBuilderList()) {
			HumanMarsLoserEntity marsLoserEntity = new HumanMarsLoserEntity(marsLoser);
			marsLoserEntity.write(buffer);
		}
		byte[] result = new byte[buffer.position()];
		buffer.flip();
		buffer.get(result);
		return result;
	}

	public void setHumanMarsLoser(byte[] marsLosers) throws IOException {
		if (marsLosers != null) {
			ByteBuffer buffer = ByteBuffer.wrap(marsLosers);
			while (buffer.hasRemaining()) {
				HumanMarsLoserEntity entity = new HumanMarsLoserEntity();
				entity.read(buffer);
				this.builder.addMarsLoser(entity.getBuilder());
			}
		}
	}
	
	@Column
	@BlobSubHumanEntity(getSubEntityClass = HumanTargetEntity.class, getSubFieldDesc = "humanTarget")
	public byte[] getHumanTarget() throws IOException {
		ByteBuffer buffer = allocateByteBuffer();
		for (HumanTarget.Builder target : this.builder.getTargetBuilderList()) {
			HumanTargetEntity targetEntity = new HumanTargetEntity(target);
			targetEntity.write(buffer);
		}
		byte[] result = new byte[buffer.position()];
		buffer.flip();
		buffer.get(result);
		return result;
	}

	public void setHumanTarget(byte[] targets) throws IOException {
		if (targets != null) {
			ByteBuffer buffer = ByteBuffer.wrap(targets);
			while (buffer.hasRemaining()) {
				HumanTargetEntity entity = new HumanTargetEntity();
				entity.read(buffer);
				this.builder.addTarget(entity.getBuilder());
			}
		}
	}
	
	@Column
	@BlobSubHumanEntity(getSubEntityClass = HumanNostrumEntity.class, getSubFieldDesc = "humanNostrum")
	public byte[] getHumanNostrum() throws IOException {
		ByteBuffer buffer = allocateByteBuffer();
		for (HumanNostrum.Builder nostrum : this.builder.getNostrumBuilderList()) {
			HumanNostrumEntity nostrumEntity = new HumanNostrumEntity(nostrum);
			nostrumEntity.write(buffer);
		}
		byte[] result = new byte[buffer.position()];
		buffer.flip();
		buffer.get(result);
		return result;
	}

	public void setHumanNostrum(byte[] nostrums) throws IOException {
		if (nostrums != null) {
			ByteBuffer buffer = ByteBuffer.wrap(nostrums);
			while (buffer.hasRemaining()) {
				HumanNostrumEntity entity = new HumanNostrumEntity();
				entity.read(buffer);
				this.builder.addNostrum(entity.getBuilder());
			}
		}
	}
	

	@Column
	@BlobSubHumanEntity(getSubEntityClass = HumanLegionTaskEntity.class, getSubFieldDesc = "humanLegionTask")
	public byte[] getHumanLegionTask() throws IOException {
		ByteBuffer buffer = allocateByteBuffer();
		for (HumanLegionTask.Builder legionTask : this.builder.getLegionTaskBuilderList()) {
			HumanLegionTaskEntity legionTaskEntity = new HumanLegionTaskEntity(legionTask);
			legionTaskEntity.write(buffer);
		}
		byte[] result = new byte[buffer.position()];
		buffer.flip();
		buffer.get(result);
		return result;
	}

	public void setHumanLegionTask(byte[] legionTasks) throws IOException {
		if (legionTasks != null) {
			ByteBuffer buffer = ByteBuffer.wrap(legionTasks);
			while (buffer.hasRemaining()) {
				HumanLegionTaskEntity entity = new HumanLegionTaskEntity();
				entity.read(buffer);
				this.builder.addLegionTask(entity.getBuilder());
			}
		}
	}
	
	@Column
	@BlobSubHumanEntity(getSubEntityClass = HumanMagicPaperEntity.class, getSubFieldDesc = "humanMagicPaper")
	public byte[] getHumanMagicPaper() throws IOException {
		ByteBuffer buffer = allocateByteBuffer();
		for (HumanMagicPaper.Builder magicPaper : this.builder.getMagicPaperBuilderList()) {
			HumanMagicPaperEntity magicPaperEntity = new HumanMagicPaperEntity(magicPaper);
			magicPaperEntity.write(buffer);
		}
		byte[] result = new byte[buffer.position()];
		buffer.flip();
		buffer.get(result);
		return result;
	}

	public void setHumanMagicPaper(byte[] magicPapers) throws IOException {
		if (magicPapers != null) {
			ByteBuffer buffer = ByteBuffer.wrap(magicPapers);
			while (buffer.hasRemaining()) {
				HumanMagicPaperEntity entity = new HumanMagicPaperEntity();
				entity.read(buffer);
				this.builder.addMagicPaper(entity.getBuilder());
			}
		}
	}
	
	@Column
	@BlobSubHumanEntity(getSubEntityClass = HumanLegionMineBattleRewardEntity.class, getSubFieldDesc = "humanLegionMineBattleReward")
	public byte[] getHumanLegionMineBattleReward() throws IOException {
		ByteBuffer buffer = allocateByteBuffer();
		for (HumanLegionMineBattleReward.Builder legionMineBattleReward : this.builder.getBattleRewardBuilderList()) {
			HumanLegionMineBattleRewardEntity legionMineBattleRewardEntity = new HumanLegionMineBattleRewardEntity(legionMineBattleReward);
			legionMineBattleRewardEntity.write(buffer);
		}
		byte[] result = new byte[buffer.position()];
		buffer.flip();
		buffer.get(result);
		return result;
	}

	public void setHumanLegionMineBattleReward(byte[] legionMineBattleRewards) throws IOException {
		if (legionMineBattleRewards != null) {
			ByteBuffer buffer = ByteBuffer.wrap(legionMineBattleRewards);
			while (buffer.hasRemaining()) {
				HumanLegionMineBattleRewardEntity entity = new HumanLegionMineBattleRewardEntity();
				entity.read(buffer);
				this.builder.addBattleReward(entity.getBuilder());
			}
		}
	}
}
