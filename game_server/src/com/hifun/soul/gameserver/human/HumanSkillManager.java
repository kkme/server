package com.hifun.soul.gameserver.human;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.hifun.soul.common.LogReasons.ItemLogReason;
import com.hifun.soul.common.LogReasons.SkillPointLogReason;
import com.hifun.soul.common.constants.LangConstants;
import com.hifun.soul.common.constants.SharedConstants;
import com.hifun.soul.gamedb.entity.HumanEntity;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.event.EventType;
import com.hifun.soul.gameserver.event.IEvent;
import com.hifun.soul.gameserver.event.IEventListener;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.human.template.HumanLevelUpTemplate;
import com.hifun.soul.gameserver.item.Item;
import com.hifun.soul.gameserver.role.properties.HumanIntProperty;
import com.hifun.soul.gameserver.role.properties.PropertyType;
import com.hifun.soul.gameserver.skill.ISkill;
import com.hifun.soul.gameserver.skill.SkillDevelopType;
import com.hifun.soul.gameserver.skill.SkillInfoSorter;
import com.hifun.soul.gameserver.skill.SkillInstance;
import com.hifun.soul.gameserver.skill.SkillStateType;
import com.hifun.soul.gameserver.skill.converter.CarriedSkillConverter;
import com.hifun.soul.gameserver.skill.converter.SkillSlotConverter;
import com.hifun.soul.gameserver.skill.msg.GCAllSkills;
import com.hifun.soul.gameserver.skill.msg.GCUpdateSkillEquipState;
import com.hifun.soul.gameserver.skill.slot.SkillSlot;
import com.hifun.soul.gameserver.skill.slot.SkillSlotState;
import com.hifun.soul.gameserver.skill.template.SkillInfo;
import com.hifun.soul.gameserver.skill.template.SkillScrollTemplate;
import com.hifun.soul.gameserver.skill.template.SkillSlotTemplate;
import com.hifun.soul.gameserver.skill.template.SkillTemplate;
import com.hifun.soul.gameserver.skill.template.SkillTemplateManager;
import com.hifun.soul.gameserver.vip.template.ResetSkillPointsTemplate;
import com.hifun.soul.proto.common.Skills.CarriedSkill;
import com.hifun.soul.proto.data.entity.Entity.HumanCarriedSkill;

/**
 * 角色技能管理器;<br>
 * FIXME: crazyjohn 没有添加持久化;
 * 
 * @author crazyjohn
 * 
 */
public class HumanSkillManager extends AbstractNotifyManager implements
		IHumanPersistenceManager, IEventListener {
	private Human owner;
	/** 普通攻击技能 */
	private ISkill normalAttackSkill;
	/** combo攻击技能 */
	private ISkill comboAttackSkill;
	/** 角色的所有技能 */
	private Map<Integer, ISkill> skills;
	/** 角色身上装备的所有技能 , 使用linked容器是为了保持遍历时候的有序性 */
	private Map<Integer, ISkill> carriedSkills = new LinkedHashMap<Integer, ISkill>(
			SharedConstants.BATTLE_MAX_CARRIED_SKILLS);
	/** 技能装备位 */
	private List<SkillSlot> slots = new ArrayList<SkillSlot>();
	/** 转化器 */
	private CarriedSkillConverter carriedSkillConverter;
	/** 转化器 */
	private SkillSlotConverter skillSlotConverter;
	/** 排序器 */
	private SkillInfoSorter sorter = new SkillInfoSorter();
	private ResetSkillPointsTemplate template;
	private SkillTemplateManager skillTemplateManager;

	public HumanSkillManager(Human owner) {
		super(owner);
		this.owner = owner;
		carriedSkillConverter = new CarriedSkillConverter(owner);
		skillSlotConverter = new SkillSlotConverter(owner);
		this.owner.registerPersistenceManager(this);
		// 事件监听
		owner.getEventBus().addListener(EventType.LEVEL_UP_EVENT, this);
		owner.getEventBus().addListener(EventType.TITLE_UP_EVENT, this);
		template = GameServerAssist.getTemplateService().get(
				SharedConstants.CONFIG_TEMPLATE_DEFAULT_ID,
				ResetSkillPointsTemplate.class);
		skillTemplateManager = GameServerAssist.getSkillTemplateManager();
		this.owner.addNotifyManager(this);
	}

	public int getSkillPoints() {
		return owner.getPropertyManager()
				.getIntPropertySet(PropertyType.HUMAN_INT_PROPERTY)
				.getPropertyValue(HumanIntProperty.SKILL_POINTS);
	}

	public void setSkillPoints(int skillPoints) {
		owner.getPropertyManager()
				.getIntPropertySet(PropertyType.HUMAN_INT_PROPERTY)
				.setPropertyValue(HumanIntProperty.SKILL_POINTS, skillPoints);
	}

	/**
	 * 加载玩家技能;
	 */
	private void loadHumanSkills(HumanEntity humanEntity) {
		// 初始化装备栏状态;
		initSkillSlots(humanEntity);

		normalAttackSkill = new SkillInstance(
				skillTemplateManager.getHumanNormalAttackSkill(owner), owner);
		comboAttackSkill = new SkillInstance(
				skillTemplateManager.getHumanComboAttackSkill(owner), owner);
		// 其它技能(该职业的SkillType为OTHER的所有技能，不关系学习和装备状态)
		skills = skillTemplateManager.getHumanSuitableSkills(owner);
		// 如果DB中没有携带的技能;
		if (humanEntity.getBuilder().getSkillBuilderList().size() <= 0) {
			return;
		} else {
			for (HumanCarriedSkill.Builder eachSkill : humanEntity.getBuilder()
					.getSkillBuilderList()) {
				ISkill skillInstance = this.skills.get(eachSkill.getSkill()
						.getSkillId());
				if (skillInstance == null) {
					continue;
				}
				// 设置栏位
				skillInstance.setSlotIndex(eachSkill.getSkill().getSlotIndex());
				// 设置状态
				skillInstance.setSkillState(eachSkill.getSkill()
						.getSkillState());
				// 判断对应的技能装备的位置是否合法
				if (eachSkill.getSkill().getSlotIndex() < 0) {
					continue;
				}
				SkillSlot slot = this.slots.get(eachSkill.getSkill()
						.getSlotIndex());
				if (slot == null) {
					continue;
				}
				if (!slot.isOpen()) {
					continue;
				}
				if (!slot.isEquipedSkill()) {
					slot.setEquipedSkill(true);
				}
				slot.equipSkill(skillInstance);

				this.carriedSkills.put(skillInstance.getSkillId(),
						skillInstance);
			}
		}

	}

	/**
	 * 初始化装备位;
	 * 
	 * @param humanEntity
	 */
	private void initSkillSlots(HumanEntity humanEntity) {
		for (int i = 0; i < SharedConstants.MAX_SKILL_SLOT_COUNT; i++) {
			SkillSlotTemplate slotTemplate = skillTemplateManager
					.getSlotTemplate(i);
			SkillSlot aSlot = new SkillSlot();
			aSlot.setTemplate(slotTemplate);
			aSlot.setSlotIndex(slotTemplate.getId());
			aSlot.setOpenTitle(GameServerAssist.getTitleTemplateManager()
					.getSkillSlotOpenTitle(i));
			aSlot.setOpenTitleName(GameServerAssist.getTitleTemplateManager()
					.getHumanTitleTemplate(aSlot.getOpenTitle()).getTitleName());
			// 设置状态
			if (i == 0) {
				// 第一个栏位默认开启
				aSlot.setState(SkillSlotState.OPEN.getIndex());
			} else {
				// 军衔等级决定技能槽开启
				if (owner.getCurrentTitle() >= GameServerAssist
						.getTitleTemplateManager().getSkillSlotOpenTitle(
								aSlot.getSlotIndex())) {
					aSlot.setState(SkillSlotState.OPEN.getIndex());
				} else {
					aSlot.setState(SkillSlotState.UN_OPEN.getIndex());
				}
			}
			// GameServerAssist.getDataService().update(
			// skillSlotConverter.convert(aSlot));
			this.slots.add(aSlot);
		}
	}

	public ISkill getNormalAttackSkill() {
		return normalAttackSkill;
	}

	public ISkill getComboAttackSkill() {
		return comboAttackSkill;
	}

	public Map<Integer, ISkill> getSkills() {
		return Collections.unmodifiableMap(skills);
	}

	/**
	 * 判断是否有可以学习的技能
	 * 
	 * @return
	 */
	public boolean hasCanStudySkill() {
		for (ISkill skill : getSkills().values()) {
			if (this.canStudy(skill.getSkillId(), null, false)) {
				return true;
			}
		}

		return false;
	}

	@Override
	public Human getOwner() {
		return owner;
	}

	@Override
	public void onLoad(HumanEntity humanEntity) {
		this.loadHumanSkills(humanEntity);
	}

	@Override
	public void onPersistence(HumanEntity humanEntity) {
		// 持久化携带的技能;
		for (ISkill skill : this.skills.values()) {
			HumanCarriedSkill.Builder skillBuilder = HumanCarriedSkill
					.newBuilder();
			skillBuilder.setHumanGuid(humanEntity.getId());
			skillBuilder.setSkill(CarriedSkill.newBuilder()
					.setSkillId(skill.getSkillId())
					.setSlotIndex(skill.getSlotIndex())
					.setSkillState(skill.getSkillState()));
			humanEntity.getBuilder().addSkill(skillBuilder);
		}
		/**
		 * 技能栏位开启由军衔等级决定，不需要存储了
		 * 
		 * // 持久化技能栏位; for (SkillSlot slot : this.slots) {
		 * HumanSkillSlot.Builder slotBuilder = HumanSkillSlot.newBuilder();
		 * slotBuilder.setHumanGuid(this.owner.getHumanGuid());
		 * slotBuilder.getSlotBuilder().setSlotIndex(slot.getSlotIndex())
		 * .setState(slot.getState());
		 * humanEntity.getBuilder().addSlot(slotBuilder); }
		 */
	}

	/**
	 * 获取所有携带的技能;
	 * 
	 * @return 不会返回空;
	 */
	public Collection<ISkill> getCarriedSkills() {
		List<ISkill> result = new ArrayList<ISkill>();
		result.addAll(this.carriedSkills.values());
		// editby: crazyjohn 2013-04-17去掉排序, 为了效率, 排序如有需求交给client去做.
		// Collections.sort(result, new Comparator<ISkill>(){
		// @Override
		// public int compare(ISkill o1, ISkill o2) {
		// if (o1.getSlotIndex() <= o2.getSlotIndex()) {
		// return -1;
		// }
		// return 1;
		// }
		//
		// });
		return Collections.unmodifiableCollection(result);
	}

	/**
	 * 获取所有可以使用的技能;
	 * 
	 * @return
	 */
	public Collection<ISkill> getAllSkills() {
		return Collections.unmodifiableCollection(this.skills.values());
	}

	/**
	 * 是有有已经开启并且没有装备上技能的空白栏位;
	 * 
	 * @return
	 */
	private SkillSlot getSuitSlot() {
		SkillSlot result = null;
		for (SkillSlot slot : this.slots) {
			if (slot.isOpen() && !slot.isEquipedSkill()) {
				return slot;
			}
		}
		return result;
	}

	/**
	 * 卸下一个技能;
	 * 
	 * @param skill
	 * @return
	 */
	public boolean unEquipSkill(ISkill skill) {
		if (skill.getSlotIndex() == SharedConstants.INVALID_SKILL_SLOT_INDEX) {
			return false;
		}
		SkillSlot slot = this.slots.get(skill.getSlotIndex());
		if (slot == null) {
			return false;
		}
		if (slot.getSkill() != skill) {
			return false;
		}
		slot.unEquipSkill(skill);
		this.carriedSkills.remove(skill.getSkillId());
		skill.setSlotIndex(SharedConstants.INVALID_SKILL_SLOT_INDEX);
		// 从db删除
		GameServerAssist.getDataService().update(
				carriedSkillConverter.convert(skill));
		sendNotify();
		return true;
	}

	/**
	 * 装备一个技能;
	 * 
	 * @param skill
	 */
	public boolean equipSkill(ISkill skill) {
		if (this.carriedSkills.size() >= SharedConstants.BATTLE_MAX_CARRIED_SKILLS) {
			return false;
		}
		SkillSlot slot = getSuitSlot();
		if (slot == null) {
			return false;
		}
		slot.equipSkill(skill);
		this.carriedSkills.put(skill.getSkillId(), skill);
		// 插入db
		GameServerAssist.getDataService().update(
				carriedSkillConverter.convert(skill));
		sendNotify();
		return true;
	}

	public List<SkillSlot> getSkillSlots() {
		return Collections.unmodifiableList(this.slots);
	}

	public SkillSlot getCanOpenSkillSlot() {
		for (SkillSlot slot : this.slots) {
			if (slot.getState() == SkillSlotState.CAN_OPEN.getIndex()) {
				return slot;
			}
		}
		return null;
	}

	/**
	 * 开启指定的技能栏位;
	 * 
	 * @param slotIndex
	 * @return
	 */
	public boolean openSlot(int slotIndex) {
		if (this.getCanOpenSkillSlot() == null) {
			return false;
		}
		if (this.getCanOpenSkillSlot().getSlotIndex() != slotIndex) {
			return false;
		}
		SkillSlot slot = this.getCanOpenSkillSlot();
		slot.setState(SkillSlotState.OPEN.getIndex());
		// GameServerAssist.getDataService().update(skillSlotConverter.convert(slot));
		// 是否有下一个合适的需要更新的栏位
		afterOpenSlot(slotIndex);
		return true;
	}

	/**
	 * 开启技能栏结束以后的收尾动作, 更新下一个可开启的技能栏;
	 * 
	 * @param slotIndex
	 */
	private void afterOpenSlot(int slotIndex) {
		if (slotIndex >= SharedConstants.MAX_SKILL_SLOT_COUNT - 1) {
			return;
		}
		SkillSlot slot = this.slots.get(slotIndex + 1);
		if (slot == null) {
			return;
		}
		if (slot.getState() == SkillSlotState.UN_OPEN.getIndex()) {
			slot.setState(SkillSlotState.CAN_OPEN.getIndex());
			GameServerAssist.getDataService().update(
					skillSlotConverter.convert(slot));
			return;
		}
	}

	@Override
	public void onEvent(IEvent event) {
		if (event.getType() == EventType.LEVEL_UP_EVENT) {
			// 升级给技能点
			HumanLevelUpTemplate template = GameServerAssist
					.getTemplateService().get(owner.getLevel(),
							HumanLevelUpTemplate.class);
			if (template != null) {
				owner.addSkillPoint(template.getSkillPoint(), true,
						SkillPointLogReason.LEVEL_UP_ADD_SKILL_POINT, "");
			}
		}
		// 军衔升级，开启技能槽
		if (event.getType() == EventType.TITLE_UP_EVENT) {
			for (SkillSlot slot : slots) {
				if (owner.getCurrentTitle() >= GameServerAssist
						.getTitleTemplateManager().getSkillSlotOpenTitle(
								slot.getSlotIndex())) {
					slot.setState(SkillSlotState.OPEN.getIndex());
				} else {
					slot.setState(SkillSlotState.UN_OPEN.getIndex());
				}
			}
			sendNotify();
		}
	}

	/**
	 * 更新技能状态
	 * 
	 */
	private void updateSkillsState(SkillInfo skillInfo) {
		SkillScrollTemplate skillScrollTemplate = skillTemplateManager
				.getSkillScrollTemplateBySkillId(skillInfo.getSkillId());
		if (skillScrollTemplate != null
				&& skillInfo.getSkillState() != SkillStateType.STUDYED
						.getIndex()) {
			// 默认技能
			if (skillScrollTemplate.isDefaultOpen()) {
				updateSkillState(skillInfo, SkillStateType.STUDYED.getIndex());
			}
			// 不需要卷轴的情况
			else if (skillScrollTemplate.getNeedSkillScrollId() <= 0
					|| skillInfo.getSkillState() == SkillStateType.RESETED
							.getIndex()) {
				if (canStudy(skillInfo.getSkillId(), null, false)) {
					updateSkillState(skillInfo,
							SkillStateType.CAN_STUDY.getIndex());
				} else if (skillInfo.getSkillState() != SkillStateType.RESETED
						.getIndex()) {
					updateSkillState(skillInfo,
							SkillStateType.NOT_STUDY.getIndex());
				}
			}
			// 需要卷轴的情况
			else {
				List<Item> items = owner.getBagManager().getItemsFromMainBag(
						skillScrollTemplate.getNeedSkillScrollId(), 1);
				if (items != null
						&& items.size() > 0
						&& items.get(0) != null
						&& canStudy(skillInfo.getSkillId(), items.get(0), false)) {
					updateSkillState(skillInfo,
							SkillStateType.CAN_STUDY.getIndex());
				} else {
					updateSkillState(skillInfo,
							SkillStateType.NOT_STUDY.getIndex());
				}
			}
		}
	}

	private void updateSkillState(SkillInfo skill, int skillState) {
		if (skill.getSkillState() == skillState) {
			return;
		}

		skill.setSkillState(skillState);
	}

	public void sendAllSkillInfos() {
		// 下发所有技能信息
		GCAllSkills allSkillsMsg = new GCAllSkills();
		List<SkillInfo> defaultSkills = new ArrayList<SkillInfo>();
		List<SkillInfo> gemSkills = new ArrayList<SkillInfo>();
		List<SkillInfo> energySkills = new ArrayList<SkillInfo>();
		List<SkillInfo> assistSkills = new ArrayList<SkillInfo>();

		for (ISkill skill : getAllSkills()) {
			if (skills.size() <= SharedConstants.SKILL_PANEL_MAX_SKILLS) {
				SkillScrollTemplate skillScrollTemplate = skillTemplateManager
						.getSkillScrollTemplateBySkillId(skill.getSkillId());
				SkillInfo skillInfo = skill.toSkillInfo();
				if (carriedSkills.get(skillInfo.getSkillId()) != null) {
					skillInfo.setIsCarried(true);
				}
				updateSkillsState(skillInfo);
				if (skillScrollTemplate != null) {
					// 设置技能的几个学习条件的状态
					if (skillInfo.getSkillState() == SkillStateType.STUDYED
							.getIndex() || preSkillIsOpen(skillScrollTemplate)) {
						skillInfo.setPreSkillIsOpen(true);
					}
					if (skillInfo.getSkillState() == SkillStateType.STUDYED
							.getIndex() || hasSkillScroll(skillScrollTemplate)) {
						skillInfo.setHasSkillScroll(true);
					}
					if (skillInfo.getSkillState() == SkillStateType.STUDYED
							.getIndex()
							|| skillPointsIsEnough(skillScrollTemplate)) {
						skillInfo.setSkillPointsIsEnough(true);
					}
					SkillDevelopType skillDevelopType = SkillDevelopType
							.typeOf(skillScrollTemplate.getSkillDevelopType());
					if (skillDevelopType != null) {
						switch (SkillDevelopType.typeOf(skillScrollTemplate
								.getSkillDevelopType())) {
						case DEFAULT:
							defaultSkills.add(skillInfo);
							break;
						case GEM:
							gemSkills.add(skillInfo);
							break;
						case ASSIST:
							assistSkills.add(skillInfo);
							break;
						case ENERGY:
							energySkills.add(skillInfo);
							break;
						}
					}
				}
				continue;
			}
			break;

		}
		// 排序
		Collections.sort(defaultSkills, sorter);
		Collections.sort(gemSkills, sorter);
		Collections.sort(energySkills, sorter);
		Collections.sort(assistSkills, sorter);

		allSkillsMsg.setDefaultSkills(defaultSkills.toArray(new SkillInfo[0]));
		allSkillsMsg.setGemSkills(gemSkills.toArray(new SkillInfo[0]));
		allSkillsMsg.setEnergySkills(energySkills.toArray(new SkillInfo[0]));
		allSkillsMsg.setAssistSkills(assistSkills.toArray(new SkillInfo[0]));
		allSkillsMsg.setSkillPoints(getSkillPoints());
		if (template != null) {
			allSkillsMsg.setCrystal(template.getCrystal());
		}
		allSkillsMsg.setSkillDevelopInfos(skillTemplateManager
				.getSkillDevelopInfos(owner.getOccupation().getIndex()));
		owner.sendMessage(allSkillsMsg);
	}

	/**
	 * 学习技能
	 * 
	 * @param skillId
	 *            要学习技能的id
	 * @param skillScroll
	 *            学习技能需要消耗的技能卷轴
	 * 
	 */
	public boolean studySkill(int skillId, Item skillScroll) {
		if (!canStudy(skillId, skillScroll, true)) {
			return false;
		}
		// 消耗技能点
		SkillScrollTemplate skillScrollTemplate = skillTemplateManager
				.getSkillScrollTemplateBySkillId(skillId);
		owner.addSkillPoint(-skillScrollTemplate.getNeedSkillPoint(), true,
				SkillPointLogReason.STUDY_SKILL, "");
		// 设置当前技能的状态，如果需要的技能点为0则默认将状态设置为已经激活
		skills.get(skillId).setSkillState(SkillStateType.STUDYED.getIndex());
		GameServerAssist.getDataService().update(
				carriedSkillConverter.convert(skills.get(skillId)));
		// 移除卷轴
		if (skillScroll != null) {
			owner.getBagManager()
					.removeItem(skillScroll.getBagType(),
							skillScroll.getBagIndex(), 1,
							ItemLogReason.SKILL_STUDY, "");
		}
		// 发送学习成功的消息
		owner.sendSuccessMessage(LangConstants.SKILL_STUDY_SUCESS,
				skills.get(skillId).getSkillName());
		// 更新技能信息
		sendAllSkillInfos();
		return true;
	}

	/**
	 * 是否可以学习该技能
	 * 
	 * @param skillId
	 * @param skillScroll
	 * @param isNeedNotify
	 * @return
	 * 
	 *         TODO: cfh 注： 开启技能有两个入口，一个是卷轴使用，还有就是直接在技能面板学习。
	 *         为了照顾使用卷轴的情况所以参数要带上skillScroll,还有更好的方式，待优化
	 */
	private boolean canStudy(int skillId, Item skillScroll, boolean isNeedNotify) {
		// 判断玩家是否已经学会该技能
		if (skills.get(skillId) != null
				&& skills.get(skillId).getSkillState() == SkillStateType.STUDYED
						.getIndex()) {
			if (isNeedNotify) {
				owner.sendErrorMessage(LangConstants.SKILL_STUDYED);
			}
			return false;
		}
		// 判断技能id是否存在
		SkillScrollTemplate skillScrollTemplate = skillTemplateManager
				.getSkillScrollTemplateBySkillId(skillId);
		if (skillScrollTemplate == null) {
			if (isNeedNotify) {
				owner.sendErrorMessage(LangConstants.SKILL_CAN_NOT_STUDY_NO_TEMPLATE);
			}
			return false;
		}
		SkillTemplate skillTemplate = skillTemplateManager
				.getSkillTemplate(skillId);
		if (skillTemplate == null) {
			if (isNeedNotify) {
				owner.sendErrorMessage(LangConstants.SKILL_CAN_NOT_STUDY_NO_TEMPLATE);
			}
			return false;
		}
		// 判断是否有合适的技能卷轴
		if (skillScrollTemplate.getNeedSkillScrollId() > 0
				&& skills.get(skillId) != null
				&& skills.get(skillId).getSkillState() != SkillStateType.RESETED
						.getIndex()) {
			if (skillScroll == null
					|| skillScroll.getItemId() != skillScrollTemplate
							.getNeedSkillScrollId()) {
				if (isNeedNotify) {
					owner.sendWarningMessage(
							LangConstants.SKILL_CAN_NOT_STUDY_ONE,
							skillScroll.getName());
				}
				return false;
			}
		}

		// 策划需求把等级限制去掉 edited by cfh 2012/12/28
		// // 判断玩家等级是否到达开启卷轴的等级
		// if(owner.getLevel() < skillTemplate.getOpenLevel()){
		// if(isNeedNotify){
		// owner.sendErrorMessage(LangConstants.SKILL_CAN_NOT_STUDY);
		// }
		// return false;
		// }

		// 判断技能点是否足够
		if (!skillPointsIsEnough(skillScrollTemplate)) {
			if (isNeedNotify) {
				owner.sendWarningMessage(LangConstants.SKILL_CAN_NOT_STUDY_TWO);
			}
			return false;
		}

		// 判断前置技能是否已经激活
		if (!preSkillIsOpen(skillScrollTemplate)) {
			if (isNeedNotify) {
				owner.sendWarningMessage(LangConstants.SKILL_CAN_NOT_STUDY_THREE);
			}
			return false;
		}

		return true;
	}

	/**
	 * 判断前置技能是否已经开启
	 * 
	 * @param skillScrollTemplate
	 * @return
	 */
	private boolean preSkillIsOpen(SkillScrollTemplate skillScrollTemplate) {
		// 判断前置技能是否已经激活
		int preSkillId = skillScrollTemplate.getPreSkillId();
		if (preSkillId > 0) {
			ISkill skill = skills.get(preSkillId);
			if (skill == null
					|| skill.getSkillState() != SkillStateType.STUDYED
							.getIndex()) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 判断是否有技能卷轴
	 * 
	 * @param skillScrollTemplate
	 * @return
	 */
	private boolean hasSkillScroll(SkillScrollTemplate skillScrollTemplate) {
		if (skillScrollTemplate.getNeedSkillScrollId() > 0
				&& skills.get(skillScrollTemplate.getId()) != null
				&& skills.get(skillScrollTemplate.getId()).getSkillState() != SkillStateType.RESETED
						.getIndex()) {
			if (owner.getBagManager().getItemCount(
					skillScrollTemplate.getNeedSkillScrollId()) <= 0) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 判断技能点是否足够
	 * 
	 * @param skillScrollTemplate
	 * @return
	 */
	private boolean skillPointsIsEnough(SkillScrollTemplate skillScrollTemplate) {
		if (getSkillPoints() < skillScrollTemplate.getNeedSkillPoint()) {
			return false;
		}
		return true;
	}

	/**
	 * 重置所有技能获得技能点
	 * 
	 * @return
	 */
	public void resetSkills() {
		// 先卸下所有技能
		for (ISkill skill : owner.getCarriedSkills()) {
			unEquipSkill(skill);
			// 更新客户端面板状态
			GCUpdateSkillEquipState skillMsg = new GCUpdateSkillEquipState();
			skillMsg.setSkillId(skill.getSkillId());
			skillMsg.setCarried(false);
			skillMsg.setSlotIndex(skill.getSlotIndex());
			owner.sendMessage(skillMsg);
		}
		// 重置技能点
		int addSkillPoints = 0;
		for (ISkill skill : skills.values()) {
			if (skill.getSkillState() == SkillStateType.STUDYED.getIndex()) {
				SkillScrollTemplate skillScrollTemplate = skillTemplateManager
						.getSkillScrollTemplateBySkillId(skill.getSkillId());
				if (skillScrollTemplate != null) {
					addSkillPoints += skillScrollTemplate.getNeedSkillPoint();
				}
				// 默认技能
				if (skillScrollTemplate.isDefaultOpen()) {
					skill.setSkillState(SkillStateType.STUDYED.getIndex());
				} else {
					skill.setSkillState(SkillStateType.RESETED.getIndex());
				}
				GameServerAssist.getDataService().update(
						carriedSkillConverter.convert(skill));
			}
		}
		if (addSkillPoints > 0) {
			owner.addSkillPoint(addSkillPoints, true,
					SkillPointLogReason.RESET_SKILL_POINT, "");
		}
		// 更新技能面板信息
		sendAllSkillInfos();
	}

	@Override
	public GameFuncType getFuncType() {
		return GameFuncType.SKILL;
	}

	@Override
	public boolean isNotify() {
		// 有可学习的技能或有空的装备位
		if (hasCanStudySkill() || getSuitSlot() != null) {
			return true;
		}
		return false;
	}

}
