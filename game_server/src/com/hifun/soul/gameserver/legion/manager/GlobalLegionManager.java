package com.hifun.soul.gameserver.legion.manager;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.hifun.soul.common.IInitializeRequired;
import com.hifun.soul.common.constants.LangConstants;
import com.hifun.soul.common.constants.SharedConstants;
import com.hifun.soul.core.i18n.SysLangService;
import com.hifun.soul.core.orm.IDBService;
import com.hifun.soul.core.orm.IEntity;
import com.hifun.soul.core.time.TimeService;
import com.hifun.soul.core.util.TimeUtils;
import com.hifun.soul.gamedb.agent.query.DataQueryConstants;
import com.hifun.soul.gamedb.cache.CacheEntry;
import com.hifun.soul.gamedb.cache.CacheManager;
import com.hifun.soul.gamedb.cache.ICachableComponent;
import com.hifun.soul.gamedb.callback.IDBCallback;
import com.hifun.soul.gamedb.entity.LegionApplyEntity;
import com.hifun.soul.gamedb.entity.LegionBuildingEntity;
import com.hifun.soul.gamedb.entity.LegionEntity;
import com.hifun.soul.gamedb.entity.LegionHonorEntity;
import com.hifun.soul.gamedb.entity.LegionLogEntity;
import com.hifun.soul.gamedb.entity.LegionMeditationLogEntity;
import com.hifun.soul.gamedb.entity.LegionMemberEntity;
import com.hifun.soul.gamedb.entity.LegionShopEntity;
import com.hifun.soul.gamedb.entity.LegionTechnologyEntity;
import com.hifun.soul.gamedb.service.IDataService;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.legion.Legion;
import com.hifun.soul.gameserver.legion.LegionApply;
import com.hifun.soul.gameserver.legion.LegionApplyStatus;
import com.hifun.soul.gameserver.legion.LegionBuilding;
import com.hifun.soul.gameserver.legion.LegionHonor;
import com.hifun.soul.gameserver.legion.LegionLog;
import com.hifun.soul.gameserver.legion.LegionMeditationLog;
import com.hifun.soul.gameserver.legion.LegionMember;
import com.hifun.soul.gameserver.legion.LegionResultType;
import com.hifun.soul.gameserver.legion.LegionShop;
import com.hifun.soul.gameserver.legion.LegionTechnology;
import com.hifun.soul.gameserver.legion.LegionTechnologyAmend;
import com.hifun.soul.gameserver.legion.converter.LegionApplyToEntityConverter;
import com.hifun.soul.gameserver.legion.converter.LegionBuildingToEntityConverter;
import com.hifun.soul.gameserver.legion.converter.LegionHonorToEntityConverter;
import com.hifun.soul.gameserver.legion.converter.LegionLogToEntityConverter;
import com.hifun.soul.gameserver.legion.converter.LegionMeditationLogToEntityConverter;
import com.hifun.soul.gameserver.legion.converter.LegionMemberToEntityConverter;
import com.hifun.soul.gameserver.legion.converter.LegionShopToEntityConverter;
import com.hifun.soul.gameserver.legion.converter.LegionTechnologyToEntityConverter;
import com.hifun.soul.gameserver.legion.converter.LegionToEntityConverter;
import com.hifun.soul.gameserver.legion.enums.JoinApplyLegionStatus;
import com.hifun.soul.gameserver.legion.enums.LegionBuildingType;
import com.hifun.soul.gameserver.legion.enums.LegionTechnologyType;
import com.hifun.soul.gameserver.legion.info.LegionBuildingInfo;
import com.hifun.soul.gameserver.legion.info.LegionTechnologyInfo;
import com.hifun.soul.gameserver.legion.info.LegionTitleInfo;
import com.hifun.soul.gameserver.legion.msg.GCAddLegionNotice;
import com.hifun.soul.gameserver.legion.msg.GCAgreeJoinLegion;
import com.hifun.soul.gameserver.legion.msg.GCApplyJoinLegion;
import com.hifun.soul.gameserver.legion.msg.GCApplyLegionCommander;
import com.hifun.soul.gameserver.legion.msg.GCCreateLegion;
import com.hifun.soul.gameserver.legion.msg.GCDissolveLegion;
import com.hifun.soul.gameserver.legion.msg.GCQuitLegion;
import com.hifun.soul.gameserver.legion.msg.GCRejectJoinLegion;
import com.hifun.soul.gameserver.legion.msg.GCRemoveLegionMember;
import com.hifun.soul.gameserver.legion.msg.GCSearchLegion;
import com.hifun.soul.gameserver.legion.msg.GCShowLegionLogTab;
import com.hifun.soul.gameserver.legion.msg.GCTransferLegion;
import com.hifun.soul.gameserver.legion.msg.GCUpdateHumanLegionInfo;
import com.hifun.soul.gameserver.legion.msg.LegionApplyListInfo;
import com.hifun.soul.gameserver.legion.msg.LegionListInfo;
import com.hifun.soul.gameserver.legion.msg.LegionMemberListInfo;
import com.hifun.soul.gameserver.legion.template.LegionConstantsTemplate;
import com.hifun.soul.gameserver.legion.template.LegionFuncTemplate;
import com.hifun.soul.gameserver.legion.template.LegionHonorTemplate;
import com.hifun.soul.gameserver.legion.template.LegionLevelTemplate;
import com.hifun.soul.gameserver.legion.template.LegionPositionTemplate;
import com.hifun.soul.gameserver.legion.template.LegionRightTemplate;
import com.hifun.soul.gameserver.legion.template.LegionTechnologyTemplate;
import com.hifun.soul.gameserver.legion.template.LegionTechnologyTypeTemplate;
import com.hifun.soul.gameserver.rank.RankType;
import com.hifun.soul.gameserver.role.properties.amend.AmendMethod;
import com.hifun.soul.gameserver.scene.SceneHumanManager;

/**
 * 全局军团管理器
 * 
 * @author yandajun
 * 
 */
@Scope("singleton")
@Component
public class GlobalLegionManager implements IInitializeRequired,
		ICachableComponent {
	private LegionTemplateManager legionTemplateManager;
	@Autowired
	private IDataService dataService;
	@Autowired
	private TimeService timeService;
	@Autowired
	private SysLangService sysLangService;
	/** 军团ID - 军团映射 */
	private Map<Long, Legion> legionMap = new HashMap<Long, Legion>();
	/** 角色ID - 军团ID 映射 */
	private Map<Long, Long> humanLegionMap = new HashMap<Long, Long>();
	/** 军团任务主题 */
	private Map<Integer, String> taskThemeMap = new HashMap<Integer, String>();
	@Autowired
	private CacheManager cacheManager;
	private CacheEntry<Long, IEntity> cache = new CacheEntry<Long, IEntity>();

	/** 转化器 */
	LegionToEntityConverter legionConverter = new LegionToEntityConverter();
	LegionMemberToEntityConverter legionMemberConverter = new LegionMemberToEntityConverter();
	LegionApplyToEntityConverter legionApplyConverter = new LegionApplyToEntityConverter();
	LegionLogToEntityConverter legionLogConverter = new LegionLogToEntityConverter();
	LegionBuildingToEntityConverter buildingConverter = new LegionBuildingToEntityConverter();
	LegionMeditationLogToEntityConverter meditationLogConverter = new LegionMeditationLogToEntityConverter();
	LegionTechnologyToEntityConverter technologyConverter = new LegionTechnologyToEntityConverter();
	LegionShopToEntityConverter shopConverter = new LegionShopToEntityConverter();
	LegionHonorToEntityConverter honorConverter = new LegionHonorToEntityConverter();

	@Override
	public void init() {
		this.cacheManager.registerOtherCachableComponent(this);
		this.legionTemplateManager = GameServerAssist
				.getLegionTemplateManager();
		taskThemeMap = legionTemplateManager.getTaskThemes();
	}

	public void start(IDBService dbService) {
		loadAllLegionInfo(dbService);
	}

	/**
	 * 重置每日数据
	 */
	public void resetLegionDailyData() {
		// 清空当日冥想日志
		clearDayMeditationLog();
		// 商品当日购买次数清零
		resetShopItemData();
		// 军团成员每日数据重置
		resetLegionMemberDailyData();
	}

	/**
	 * 加载军团相关信息
	 */
	public void loadAllLegionInfo(IDBService dbService) {
		// 从数据库中查出军团信息
		loadLegionInfo(dbService);
		// 从数据库中查出军团成员列表信息
		loadLegionMemberInfo(dbService);
		// 从数据库中查出军团申请列表信息
		loadLegionApplyInfo(dbService);
		// 从数据库中查出军团日志列表信息
		loadLegionLogInfo(dbService);
		// 从数据库中查出军团建筑等级信息
		loadLegionBuildingInfo(dbService);
		// 从数据库中查出冥想日志列表信息
		loadLegionMeditationLogInfo(dbService);
		// 从数据库中查出军团科技信息
		loadLegionTechnologyInfo(dbService);
		// 从数据库中查出军团商品购买信息
		loadLegionShopInfo(dbService);
		// 从数据库中查出军团头衔信息
		loadLegionHonorInfo(dbService);
	}

	/**
	 * 加载军团信息
	 * 
	 */
	private void loadLegionInfo(IDBService dbService) {
		List<?> regionListResult = dbService
				.findByNamedQuery(DataQueryConstants.QUERY_ALL_LEGION);
		if (!regionListResult.isEmpty()) {
			@SuppressWarnings("unchecked")
			List<LegionEntity> legionEntityList = (List<LegionEntity>) regionListResult;
			for (LegionEntity legionEntity : legionEntityList) {
				Legion legion = legionConverter.reverseConvert(legionEntity);
				legionMap.put(legion.getId(), legion);
			}
		}
	}

	/**
	 * 加载军团成员信息
	 * 
	 */
	private void loadLegionMemberInfo(IDBService dbService) {
		List<?> memberListResult = dbService
				.findByNamedQuery(DataQueryConstants.QUERY_ALL_LEGION_MEMBER);
		if (!memberListResult.isEmpty()) {
			@SuppressWarnings("unchecked")
			List<LegionMemberEntity> legionMemberEntityList = (List<LegionMemberEntity>) memberListResult;
			for (LegionMemberEntity legionMemberEntity : legionMemberEntityList) {
				LegionMember legionMember = legionMemberConverter
						.reverseConvert(legionMemberEntity);
				Legion legion = legionMap.get(legionMemberEntity.getLegionId());
				if (legion != null) {
					legion.getMemberListMap().put(
							(Long) legionMemberEntity.getId(), legionMember);
					humanLegionMap.put(legionMember.getHumanGuid(),
							legion.getId());
				}
			}
		}
	}

	/**
	 * 加载军团申请信息
	 * 
	 */
	private void loadLegionApplyInfo(IDBService dbService) {
		List<?> applyListResult = dbService
				.findByNamedQuery(DataQueryConstants.QUERY_ALL_LEGION_APPLY);
		if (!applyListResult.isEmpty()) {
			@SuppressWarnings("unchecked")
			List<LegionApplyEntity> legionApplyEntityList = (List<LegionApplyEntity>) applyListResult;
			for (LegionApplyEntity legionApplyEntity : legionApplyEntityList) {
				LegionApply legionApply = legionApplyConverter
						.reverseConvert(legionApplyEntity);
				Legion legion = legionMap.get(legionApplyEntity
						.getApplyLegionId());
				if (legion != null) {
					legion.getApplyListMap().put(
							(Long) legionApplyEntity.getApplyHumanGuid(),
							legionApply);
				}
			}
		}
	}

	/**
	 * 加载军团日志信息
	 * 
	 */
	private void loadLegionLogInfo(IDBService dbService) {
		List<?> logListResult = dbService
				.findByNamedQuery(DataQueryConstants.QUERY_ALL_LEGION_LOG);
		if (!logListResult.isEmpty()) {
			@SuppressWarnings("unchecked")
			List<LegionLogEntity> legionLogEntityList = (List<LegionLogEntity>) logListResult;
			for (LegionLogEntity legionLogEntity : legionLogEntityList) {
				LegionLog legionLog = legionLogConverter
						.reverseConvert(legionLogEntity);
				Legion legion = legionMap.get(legionLog.getLegionId());
				if (legion != null) {
					LinkedList<LegionLog> legionLogList = legion
							.getLegionLogList();
					legionLogList.add(legionLog);
				}

			}
		}
	}

	/**
	 * 加载军团冥想日志信息
	 * 
	 */
	private void loadLegionMeditationLogInfo(IDBService dbService) {
		List<?> logListResult = dbService
				.findByNamedQuery(DataQueryConstants.QUERY_ALL_LEGION_MEDITATION_LOG);
		if (!logListResult.isEmpty()) {
			@SuppressWarnings("unchecked")
			List<LegionMeditationLogEntity> meditationLogEntityList = (List<LegionMeditationLogEntity>) logListResult;
			for (LegionMeditationLogEntity meditationLogEntity : meditationLogEntityList) {
				LegionMeditationLog meditationLog = meditationLogConverter
						.reverseConvert(meditationLogEntity);
				Legion legion = legionMap.get(meditationLog.getLegionId());
				if (legion != null) {
					LinkedList<LegionMeditationLog> meditationLogList = legion
							.getMeditationLogList();
					if (meditationLogList.size() >= SharedConstants.LEGION_MEDITATION_LOG_NUM) {
						break;
					}
					meditationLogList.add(meditationLog);
				}

			}
		}
	}

	/**
	 * 加载军团建筑等级信息
	 * 
	 */
	private void loadLegionBuildingInfo(IDBService dbService) {
		List<?> result = dbService
				.findByNamedQuery(DataQueryConstants.QUERY_ALL_LEGION_BUILDING);
		if (!result.isEmpty()) {
			@SuppressWarnings("unchecked")
			List<LegionBuildingEntity> buildingEntityList = (List<LegionBuildingEntity>) result;
			for (LegionBuildingEntity buildingEntity : buildingEntityList) {
				LegionBuilding building = buildingConverter
						.reverseConvert(buildingEntity);
				Legion legion = legionMap.get(building.getLegionId());
				if (legion != null) {
					legion.getBuildingListMap().put(building.getBuildingType(),
							building);
				}
			}
		}
	}

	/**
	 * 加载军团科技信息
	 * 
	 */
	private void loadLegionTechnologyInfo(IDBService dbService) {
		List<?> result = dbService
				.findByNamedQuery(DataQueryConstants.QUERY_ALL_LEGION_TECHNOLOGY);
		if (!result.isEmpty()) {
			@SuppressWarnings("unchecked")
			List<LegionTechnologyEntity> technologyEntityList = (List<LegionTechnologyEntity>) result;
			for (LegionTechnologyEntity technologyEntity : technologyEntityList) {
				LegionTechnology technology = technologyConverter
						.reverseConvert(technologyEntity);
				Legion legion = legionMap.get(technology.getLegionId());
				if (legion != null) {
					legion.getTechnologyListMap().put(
							technology.getTechnologyType(), technology);
				}
			}
		}
	}

	/**
	 * 加载军团商品购买信息
	 * 
	 */
	private void loadLegionShopInfo(IDBService dbService) {
		List<?> result = dbService
				.findByNamedQuery(DataQueryConstants.QUERY_ALL_LEGION_SHOP);
		if (!result.isEmpty()) {
			@SuppressWarnings("unchecked")
			List<LegionShopEntity> shopEntityList = (List<LegionShopEntity>) result;
			for (LegionShopEntity shopEntity : shopEntityList) {
				LegionShop shop = shopConverter.reverseConvert(shopEntity);
				Legion legion = legionMap.get(shop.getLegionId());
				if (legion != null) {
					legion.getShopListMap().put(shop.getItemId(), shop);
				}
			}
		}
	}

	/**
	 * 加载军团头衔信息
	 * 
	 */
	private void loadLegionHonorInfo(IDBService dbService) {
		List<?> result = dbService
				.findByNamedQuery(DataQueryConstants.QUERY_ALL_LEGION_HONOR);
		if (!result.isEmpty()) {
			@SuppressWarnings("unchecked")
			List<LegionHonorEntity> honorEntityList = (List<LegionHonorEntity>) result;
			for (LegionHonorEntity honorEntity : honorEntityList) {
				LegionHonor honor = honorConverter.reverseConvert(honorEntity);
				Legion legion = legionMap.get(honor.getLegionId());
				if (legion != null) {
					legion.getHonorListMap().put(honor.getTitleId(), honor);
				}
			}
		}
	}

	/**
	 * 获取军团列表
	 */
	public List<Legion> getLegionList() {
		List<Legion> legionList = new ArrayList<Legion>();
		for (long l : legionMap.keySet()) {
			legionList.add(legionMap.get(l));
		}
		return legionList;
	}

	/**
	 * 根据ID获取军团
	 */
	public Legion getLegionById(long legionId) {
		return legionMap.get(legionId);
	}

	/**
	 * 获取角色所在军团
	 */
	public Legion getLegion(long humanGuid) {
		Long legionId = humanLegionMap.get(humanGuid);
		return legionMap.get(legionId);
	}

	/**
	 * 获取军团的成员列表
	 */
	public List<LegionMember> getLegionMemberList(Legion legion) {
		List<LegionMember> legionMemberList = new ArrayList<LegionMember>();
		for (long humanGuid : legion.getMemberListMap().keySet()) {
			legionMemberList.add(legion.getMemberListMap().get(humanGuid));
		}
		Collections.sort(legionMemberList);
		return legionMemberList;
	}

	/**
	 * 获取角色所在军团的申请列表
	 */
	public List<LegionApply> getLegionApplyList(long legionId) {
		Legion legion = legionMap.get(legionId);
		Map<Long, LegionApply> legionApplyMap = legion.getApplyListMap();
		List<LegionApply> legionApplyList = new ArrayList<LegionApply>();
		for (long humanGuid : legionApplyMap.keySet()) {
			legionApplyList.add(legionApplyMap.get(humanGuid));
		}
		return legionApplyList;
	}

	@Override
	public List<IEntity> getUpdateEntities() {
		return cache.getAllUpdateData();
	}

	@Override
	public List<IEntity> getDeleteEntities() {
		return cache.getAllDeleteData();
	}

	/**
	 * 创建军团
	 */
	public void createLegion(final Human human, final String legionName) {
		// 设置军团实体
		final LegionEntity legionEntity = new LegionEntity();
		legionEntity.setCommanderId(human.getHumanGuid());
		legionEntity.setCommanderName(human.getName());
		legionEntity.setExperience(0);
		legionEntity.setLegionName(legionName);
		legionEntity.setMemberNum(1);
		// 从军团等级模板中获取信息
		LegionLevelTemplate legionLevelTemplate = this.legionTemplateManager
				.getLegionLevelTemplate(SharedConstants.CONFIG_TEMPLATE_DEFAULT_ID);
		final int legionLevel = legionLevelTemplate.getId();
		legionEntity.setLegionLevel(legionLevel);
		final int memberLimit = legionLevelTemplate.getMemberLimit();
		legionEntity.setMemberLimit(memberLimit);
		// 插入军团实体
		dataService.insert(legionEntity, new IDBCallback<Serializable>() {

			@Override
			public void onSucceed(Serializable result) {
				final Long legionId = (Long) result;
				legionMap.put(legionId,
						legionConverter.reverseConvert(legionEntity));
				humanLegionMap.put(human.getHumanGuid(), legionId);
				// 刷新军团排行榜
				refreshLegionRank();
				// 设置军团成员实体
				final LegionMemberEntity memberEntity = new LegionMemberEntity();
				memberEntity.setId(human.getHumanGuid());
				memberEntity.setLegionId(legionId);
				memberEntity.setMemberName(human.getName());
				memberEntity.setLevel(human.getLevel());
				// 从军团职位模板中获取信息
				LegionPositionTemplate legionPositionTemplate = legionTemplateManager
						.getLegionPositionTemplate(legionTemplateManager
								.getHighestLegionPosition());
				memberEntity.setPosition(legionPositionTemplate.getId());
				memberEntity.setPositionName(legionPositionTemplate
						.getPositionName());
				memberEntity.setOffLineTime(timeService.now());
				// 插入军团成员实体
				dataService.insert(memberEntity,
						new IDBCallback<Serializable>() {

							@Override
							public void onSucceed(Serializable result) {
								legionMap
										.get(legionId)
										.getMemberListMap()
										.put(human.getHumanGuid(),
												legionMemberConverter
														.reverseConvert(memberEntity));
								// 添加创建军团日志
								LegionLog legionLog = new LegionLog();
								legionLog.setLegionId(legionId);
								long now = timeService.now();
								DateFormat format = new SimpleDateFormat(
										"MM-dd hh:mm");
								String operateTime = format
										.format(new Date(now));
								legionLog.setContent(sysLangService.read(
										LangConstants.LEGION_LOG_CREATE,
										operateTime, human.getName()));
								legionLog.setOperateTime(now);
								addLegionLog(legionLog);
								// 发送创建军团消息
								GCCreateLegion gcCreateMsg = new GCCreateLegion();
								gcCreateMsg.setLegionName(legionName);
								gcCreateMsg.setCommanderName(human.getName());
								gcCreateMsg.setLegionLevel(legionLevel);
								gcCreateMsg.setMemberLimit(memberLimit);
								gcCreateMsg.setMemberNum(1);
								gcCreateMsg.setExperience(0);
								human.sendMessage(gcCreateMsg);

								// 更新角色所在军团信息
								updateHumanLegionInfo(human);
							}

							@Override
							public void onFailed(String errorMsg) {
							}

						});

			}

			@Override
			public void onFailed(String errorMsg) {
			}
		});
	}

	/**
	 * 更新角色所在军团信息
	 */
	public void updateHumanLegionInfo(Human human) {
		if (human == null) {
			return;
		}
		Legion legion = getLegion(human.getHumanGuid());
		GCUpdateHumanLegionInfo gcUpdateMsg = new GCUpdateHumanLegionInfo();
		if (legion != null) {
			gcUpdateMsg.setJoinedLegion(true);
			gcUpdateMsg.setLegionId(legion.getId());
			gcUpdateMsg.setLegionName(legion.getLegionName());
		} else {
			gcUpdateMsg.setJoinedLegion(false);
			gcUpdateMsg.setLegionId(0);
			gcUpdateMsg.setLegionName("");
		}
		human.sendMessage(gcUpdateMsg);
	}

	/**
	 * 申请加入军团
	 */
	public void applyJoinLegion(final Human human, final long joinLegionId) {
		// 保存军团申请实体
		final LegionApplyEntity legionApplyEntity = new LegionApplyEntity();
		legionApplyEntity.setApplyHumanGuid(human.getHumanGuid());
		legionApplyEntity.setApplyLegionId(joinLegionId);
		legionApplyEntity.setApplyHumanLevel(human.getLevel());
		legionApplyEntity.setApplyHumanName(human.getName());
		legionApplyEntity.setApplyTime(timeService.now());
		legionApplyEntity.setApplyStatus(LegionApplyStatus.APPLIED.getIndex());
		this.dataService.insert(legionApplyEntity,
				new IDBCallback<Serializable>() {

					@Override
					public void onSucceed(Serializable result) {
						Long applyId = (Long) result;
						legionApplyEntity.setId(applyId);
						legionMap
								.get(joinLegionId)
								.getApplyListMap()
								.put(human.getHumanGuid(),
										legionApplyConverter
												.reverseConvert(legionApplyEntity));
						human.sendSuccessMessage(LangConstants.APPLY_LEGION_SUCCESS);
						// 发送响应消息
						GCApplyJoinLegion gcMsg = new GCApplyJoinLegion();
						gcMsg.setJoinLegionId(joinLegionId);
						gcMsg.setApplyButtonStatus(JoinApplyLegionStatus.APPLIED
								.getIndex());
						human.sendMessage(gcMsg);
					}

					@Override
					public void onFailed(String errorMsg) {
					}
				});

	}

	/**
	 * 同意加入军团
	 */
	public void agreeJoinLegion(final Human human, final long joinHumanGuid) {

		final Legion legion = getLegion(human.getHumanGuid());
		// 保存军团成员实体
		final LegionMemberEntity memberEntity = new LegionMemberEntity();
		memberEntity.setId(joinHumanGuid);
		memberEntity.setLegionId(legion.getId());
		memberEntity.setMemberName(legion.getApplyListMap().get(joinHumanGuid)
				.getApplyHumanName());
		// 从职位模板中获取信息
		LegionPositionTemplate legionPositionTemplate = legionTemplateManager
				.getLegionPositionTemplate(legionTemplateManager
						.getLowestLegionPosition());
		memberEntity.setPosition(legionPositionTemplate.getId());
		memberEntity.setPositionName(legionPositionTemplate.getPositionName());
		memberEntity.setLevel(legion.getApplyListMap().get(joinHumanGuid)
				.getApplyHumanLevel());
		if (!isOnline(joinHumanGuid)) {
			memberEntity.setOffLineTime(timeService.now());
		}
		dataService.insert(memberEntity, new IDBCallback<Serializable>() {
			@Override
			public void onSucceed(Serializable result) {
				int memberNum = legion.getMemberNum();
				legion.setMemberNum(++memberNum);
				// 更新实体
				cache.addUpdate(legion.getId(), legionConverter.convert(legion));
				LegionApply legionApply = legion.getApplyListMap().get(
						joinHumanGuid);
				cache.addDelete(legionApply.getId(),
						legionApplyConverter.convert(legionApply));
				legion.getMemberListMap().put(joinHumanGuid,
						legionMemberConverter.reverseConvert(memberEntity));
				legion.getApplyListMap().remove(joinHumanGuid);
				humanLegionMap.put(joinHumanGuid, legion.getId());

				// 发送响应加入军团的消息
				GCAgreeJoinLegion gcAgreeMsg = new GCAgreeJoinLegion();
				human.sendMessage(gcAgreeMsg);
				// 向新加入的成员发送加入成功提示
				Human joinHuman = GameServerAssist.getGameWorld()
						.getSceneHumanManager().getHumanByGuid(joinHumanGuid);
				if (joinHuman != null) {
					joinHuman.sendSuccessMessage(
							LangConstants.JOIN_LEGION_SUCCESS,
							legion.getLegionName());
				}

				// 更新角色所在军团信息
				updateHumanLegionInfo(GameServerAssist.getGameWorld()
						.getSceneHumanManager().getHumanByGuid(joinHumanGuid));

				// 添加加入军团日志
				LegionLog legionLog = new LegionLog();
				legionLog.setLegionId(legion.getId());
				long now = timeService.now();
				DateFormat format = new SimpleDateFormat("MM-dd hh:mm");
				String operateTime = format.format(new Date(now));
				legionLog.setContent(sysLangService.read(
						LangConstants.LEGION_LOG_ADD, operateTime,
						memberEntity.getMemberName()));
				legionLog.setOperateTime(now);
				addLegionLog(legionLog);
				// 刷新军团排行榜
				refreshLegionRank();
			}

			@Override
			public void onFailed(String errorMsg) {
			}

		});

	}

	/**
	 * 拒绝加入军团
	 */
	public void rejectJoinLegion(Human human, long joinHumanGuid) {
		// 删除申请
		Legion legion = this.getLegion(human.getHumanGuid());
		LegionApply legionApply = legion.getApplyListMap().get(joinHumanGuid);
		cache.addDelete(legionApply.getId(),
				legionApplyConverter.convert(legionApply));
		legion.getApplyListMap().remove(joinHumanGuid);
		// 发送消息
		GCRejectJoinLegion gcMsg = new GCRejectJoinLegion();
		human.sendMessage(gcMsg);
	}

	/**
	 * 退出军团
	 */
	public void quitLegion(Human human) {
		Legion legion = this.getLegion(human.getHumanGuid());
		LegionMember legionMember = legion.getMemberListMap().get(
				human.getHumanGuid());
		legion.setMemberNum(legion.getMemberNum() - 1);
		legion.getMemberListMap().remove(human.getHumanGuid());
		humanLegionMap.remove(human.getHumanGuid());
		cache.addDelete(legionMember.getHumanGuid(),
				legionMemberConverter.convert(legionMember));
		// 军团职位变动
		this.changeLegionPositions(legion);
		cache.addUpdate(legion.getId(), legionConverter.convert(legion));
		// 发送退出消息
		GCQuitLegion gcMsg = new GCQuitLegion();
		gcMsg.setResult(LegionResultType.SUCCESS.getIndex());
		human.sendMessage(gcMsg);

		// 更新角色所在军团信息
		updateHumanLegionInfo(human);

		// 添加退出军团日志
		LegionLog legionLog = new LegionLog();
		legionLog.setLegionId(legion.getId());
		long now = timeService.now();
		DateFormat format = new SimpleDateFormat("MM-dd hh:mm");
		String operateTime = format.format(new Date(now));
		legionLog.setContent(sysLangService.read(LangConstants.LEGION_LOG_QUIT,
				operateTime, human.getName()));
		legionLog.setOperateTime(now);
		this.addLegionLog(legionLog);
		// 刷新军团排行榜
		refreshLegionRank();
	}

	/**
	 * 解散军团
	 */
	public void dissolveLegion(Human human) {
		// 解散军团时，团里只剩下一个人即团长
		Legion legion = this.getLegion(human.getHumanGuid());
		// 删除军团日志
		for (LegionLog legionLog : legion.getLegionLogList()) {
			cache.addDelete(legionLog.getId(),
					legionLogConverter.convert(legionLog));
		}
		// 删除军团申请
		for (long applyHumanId : legion.getApplyListMap().keySet()) {
			LegionApply legionApply = legion.getApplyListMap()
					.get(applyHumanId);
			cache.addDelete(legionApply.getId(),
					legionApplyConverter.convert(legionApply));
		}
		// 删除军团成员
		LegionMember legionMember = legion.getMemberListMap().get(
				human.getHumanGuid());
		// 由于军团成员主键是humanGuid，缓存删除有时间延迟的原因导致，解散军团后立即创建军团，会报t_legion_member主键冲突；修改为不用缓存删除，而是直接删除
		dataService.delete(legionMemberConverter.convert(legionMember));
		// 删除军团
		cache.addDelete(legion.getId(), legionConverter.convert(legion));
		legionMap.remove(legion.getId());
		// 删除角色与军团关系
		humanLegionMap.remove(human.getHumanGuid());
		// 发送解散消息
		human.sendImportantMessage(LangConstants.LEGION_DISSOLVED);
		GCDissolveLegion gcDissolveMsg = new GCDissolveLegion();
		human.sendMessage(gcDissolveMsg);

		// 更新角色所在军团信息
		updateHumanLegionInfo(human);
		// 刷新军团排行榜
		refreshLegionRank();
	}

	/**
	 * 删除军团成员
	 */
	public void removeLegionMember(Human human, long removeHumanGuid) {
		Legion legion = this.getLegion(human.getHumanGuid());
		legion.setMemberNum(legion.getMemberNum() - 1);
		LegionMember removeLegionMember = legion.getMemberListMap().get(
				removeHumanGuid);
		cache.addDelete(removeHumanGuid,
				legionMemberConverter.convert(removeLegionMember));
		humanLegionMap.remove(removeHumanGuid);
		// 军团职位变动
		this.changeLegionPositions(legion);
		cache.addUpdate(legion.getId(), legionConverter.convert(legion));
		legion.getMemberListMap().remove(removeHumanGuid);
		// 刷新军团排行榜
		refreshLegionRank();
		// 发送删除消息
		GCRemoveLegionMember gcRemoveMsg = new GCRemoveLegionMember();
		human.sendMessage(gcRemoveMsg);

		// 更新角色所在军团信息
		updateHumanLegionInfo(GameServerAssist.getGameWorld()
				.getSceneHumanManager().getHumanByGuid(removeHumanGuid));

		// 添加删除成员日志
		LegionLog legionLog = new LegionLog();
		legionLog.setLegionId(legion.getId());
		long now = timeService.now();
		DateFormat format = new SimpleDateFormat("MM-dd hh:mm");
		String operateTime = format.format(new Date(now));
		legionLog.setContent(sysLangService.read(
				LangConstants.LEGION_LOG_REMOVE_MEMBER, operateTime,
				human.getName(), removeLegionMember.getMemberName()));
		legionLog.setOperateTime(now);
		addLegionLog(legionLog);
	}

	/**
	 * 军团职位变动
	 */
	private void changeLegionPositions(Legion legion) {
		// 除了团长，所有成员的职位重新调整
		List<LegionMember> legionMemberList = new ArrayList<LegionMember>();
		for (long humanGuid : legion.getMemberListMap().keySet()) {
			LegionMember legionMember = legion.getMemberListMap()
					.get(humanGuid);
			if (legionMember.getPosition() != legionTemplateManager
					.getHighestLegionPosition()) {
				legionMemberList.add(legionMember);
			}
		}
		Collections.sort(legionMemberList, new Comparator<LegionMember>() {
			// 成员按贡献值从大到小排序
			@Override
			public int compare(LegionMember o1, LegionMember o2) {
				if (o1.getTotalContribution() > o2.getTotalContribution()) {
					return -1;
				} else if (o1.getTotalContribution() < o2
						.getTotalContribution()) {
					return 1;
				}
				return 0;
			}
		});
		// 职位从高到低，按贡献标准、人数限制设置给每个成员
		int memberIndex = 0;
		for (int position = legionTemplateManager.getHighestLegionPosition() - 1; position >= 1; position--) {
			LegionPositionTemplate positionTemplate = legionTemplateManager
					.getLegionPositionTemplate(position);
			for (int num = 0; num < positionTemplate.getNumLimit(); num++) {
				if (memberIndex >= legionMemberList.size()) {
					return;
				}
				LegionMember legionMember = legionMemberList.get(memberIndex);
				if (legionMember.getTotalContribution() >= positionTemplate
						.getNeedContribution()) {
					int oldPosition = legionMember.getPosition();
					legionMember.setPosition(position);
					legionMember.setPositionName(positionTemplate
							.getPositionName());
					// 添加职位变动日志
					if (position != oldPosition) {
						LegionLog legionLog = new LegionLog();
						legionLog.setLegionId(legion.getId());
						long now = timeService.now();
						DateFormat format = new SimpleDateFormat("MM-dd hh:mm");
						String operateTime = format.format(new Date(now));
						legionLog.setContent(sysLangService.read(
								LangConstants.LEGION_LOG_POSITION_CHANGE,
								operateTime, legionMember.getMemberName(),
								legionMember.getPositionName()));
						legionLog.setOperateTime(now);
						addLegionLog(legionLog);
					}
					memberIndex++;
				}
			}

		}
		// 更新缓存
		for (LegionMember legionMember : legionMemberList) {
			cache.addUpdate(legionMember.getHumanGuid(),
					legionMemberConverter.convert(legionMember));
		}
	}

	/**
	 * 申请晋升团长
	 */
	public void applyLegionCommander(Human human) {
		Legion legion = this.getLegion(human.getHumanGuid());
		LegionMember legionMember = legion.getMemberListMap().get(
				human.getHumanGuid());
		LegionMember commander = legion.getMemberListMap().get(
				legion.getCommanderId());
		long lastLogoutTime = commander.getOffLineTime();
		// 团长3天不在线
		if (TimeUtils.getSoFarWentHours(lastLogoutTime, timeService.now()) < 72) {
			human.sendErrorMessage(LangConstants.APPLY_LEGION_COMMANDER_OFFLINE_THREE_DAYS);
			return;
		}
		// 贡献值超过团长10%
		if (legionMember.getTotalContribution() <= commander
				.getTotalContribution() * 1.1) {
			human.sendErrorMessage(LangConstants.APPLY_LEGION_COMMANDER_EXP_NOT_ENOUGH);
			return;
		}
		int commanderPosition = this.legionTemplateManager
				.getHighestLegionPosition();
		// 团员晋升团长
		legionMember.setPosition(commanderPosition);
		legionMember
				.setPositionName(this.legionTemplateManager
						.getLegionPositionTemplate(commanderPosition)
						.getPositionName());
		// 军团信息调整
		legion.setCommanderId(human.getHumanGuid());
		legion.setCommanderName(human.getName());
		// 团长职位变动
		commander.setPosition(legionTemplateManager.getLowestLegionPosition());
		this.changeLegionPositions(legion);
		// 更新实体
		cache.addUpdate(legion.getId(), legionConverter.convert(legion));
		cache.addUpdate(legionMember.getHumanGuid(),
				legionMemberConverter.convert(legionMember));
		cache.addUpdate(commander.getHumanGuid(),
				legionMemberConverter.convert(commander));
		// 发送消息
		GCApplyLegionCommander gcMsg = new GCApplyLegionCommander();
		human.sendMessage(gcMsg);

	}

	/**
	 * 转让军团
	 */
	public void transferLegion(Human human, long transferHumanGuid) {
		Legion legion = this.getLegion(human.getHumanGuid());
		// 1 接受者晋升团长
		LegionMember transferLegionMember = legion.getMemberListMap().get(
				transferHumanGuid);
		transferLegionMember.setPosition(legionTemplateManager
				.getHighestLegionPosition());
		transferLegionMember.setPositionName(legionTemplateManager
				.getLegionPositionTemplate(
						legionTemplateManager.getHighestLegionPosition())
				.getPositionName());
		// 2 团长根据贡献值获得相应职位
		LegionMember commander = legion.getMemberListMap().get(
				human.getHumanGuid());
		commander.setPosition(legionTemplateManager.getLowestLegionPosition());
		commander.setPositionName(legionTemplateManager
				.getLegionPositionTemplate(
						legionTemplateManager.getLowestLegionPosition())
				.getPositionName());
		legion.setCommanderId(transferHumanGuid);
		legion.setCommanderName(transferLegionMember.getMemberName());
		human.sendSuccessMessage(LangConstants.LEGION_TRANSFER_SUCCESS);
		// 军团职位变动
		this.changeLegionPositions(legion);
		// 3 更新Map、实体
		cache.addUpdate(legion.getId(), legionConverter.convert(legion));
		cache.addUpdate(transferLegionMember.getHumanGuid(),
				legionMemberConverter.convert(transferLegionMember));
		cache.addUpdate(commander.getHumanGuid(),
				legionMemberConverter.convert(commander));
		// 4 发送消息
		GCTransferLegion gcMsg = new GCTransferLegion();
		human.sendMessage(gcMsg);
		// 5 添加转让军团日志
		final LegionLog legionLog = new LegionLog();
		legionLog.setLegionId(legion.getId());
		long now = timeService.now();
		DateFormat format = new SimpleDateFormat("MM-dd hh:mm");
		String operateTime = format.format(new Date(now));
		legionLog.setContent(sysLangService.read(
				LangConstants.LEGION_LOG_TRANSFER, operateTime,
				human.getName(), transferLegionMember.getMemberName()));
		legionLog.setOperateTime(now);
		addLegionLog(legionLog);
	}

	/**
	 * 搜索军团
	 */
	public void searchLegion(Human human, String searchName) {
		Set<Long> legionSet = legionMap.keySet();
		List<Legion> legionList = new ArrayList<Legion>();
		for (long legionId : legionSet) {
			Legion legion = legionMap.get(legionId);
			if (legion.getLegionName().contains(searchName)) {
				legionList.add(legion);
			}
		}
		// 发消息
		GCSearchLegion gcMsg = new GCSearchLegion();
		LegionListInfo[] legions = this.legionToListInfo(human, legionList);
		gcMsg.setLegions(legions);
		human.sendMessage(gcMsg);
	}

	/**
	 * 判断军团名称是否存在
	 */
	public boolean isExistLegionName(String legionName) {
		for (long legionId : legionMap.keySet()) {
			Legion legion = legionMap.get(legionId);
			if (legion.getLegionName().equals(legionName)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 获取军团成员
	 */
	public LegionMember getLegionMember(long humanGuid) {
		Legion legion = this.getLegion(humanGuid);
		if (legion == null) {
			return null;
		}
		return legion.getMemberListMap().get(humanGuid);
	}

	/**
	 * 更新军团信息
	 */
	public void updateLegion(Legion legion) {
		cache.addUpdate(legion.getId(), legionConverter.convert(legion));
	}

	/**
	 * 更新军团成员信息
	 */
	public void updateLegionMember(LegionMember legionMember) {
		cache.addUpdate(legionMember.getHumanGuid(),
				legionMemberConverter.convert(legionMember));
	}

	/**
	 * 军团model转化为消息对象
	 */
	public LegionListInfo letionToInfo(Legion legion) {
		LegionListInfo legionListInfo = new LegionListInfo();
		legionListInfo.setLegionId(legion.getId());
		legionListInfo.setLegionName(legion.getLegionName());
		legionListInfo.setLegionLevel(legion.getLegionLevel());
		legionListInfo.setCommanderName(legion.getCommanderName());
		legionListInfo.setMemberLimit(legion.getMemberLimit());
		legionListInfo.setMemberNum(legion.getMemberNum());
		return legionListInfo;
	}

	/**
	 * 军团成员model转化为消息对象
	 */
	public LegionMemberListInfo legionMemberToInfo(LegionMember legionMember) {
		LegionMemberListInfo legionMemberListInfo = new LegionMemberListInfo();
		legionMemberListInfo.setMemberId(legionMember.getHumanGuid());
		legionMemberListInfo.setMemberName(legionMember.getMemberName());
		legionMemberListInfo.setLevel(legionMember.getLevel());
		legionMemberListInfo.setArenaRank(GameServerAssist.getArenaService()
				.getArenaRank(legionMember.getHumanGuid()));
		legionMemberListInfo.setPositionName(legionMember.getPositionName());
		legionMemberListInfo.setTodayContribution(legionMember
				.getTodayContribution());
		legionMemberListInfo.setTotalContribution(legionMember
				.getTotalContribution());
		return legionMemberListInfo;
	}

	/**
	 * 军团申请model转化为消息对象
	 */
	public LegionApplyListInfo legionApplyToInfo(LegionApply legionApply) {
		LegionApplyListInfo legionApplyListInfo = new LegionApplyListInfo();
		legionApplyListInfo.setApplyHumanId(legionApply.getApplyHumanGuid());
		legionApplyListInfo.setApplyHumanName(legionApply.getApplyHumanName());
		legionApplyListInfo
				.setApplyHumanLevel(legionApply.getApplyHumanLevel());
		legionApplyListInfo.setApplyArenaRank(legionApply.getApplyArenaRank());
		return legionApplyListInfo;
	}

	/**
	 * 当前角色看到的军团列表(消息发送，客服端显示)
	 */
	public LegionListInfo[] legionToListInfo(Human human,
			List<Legion> legionList) {
		int size = legionList.size();
		LegionListInfo[] legions = new LegionListInfo[size];
		for (int i = 0; i < size; i++) {
			LegionListInfo legionListInfo = this
					.letionToInfo(legionList.get(i));
			// 根据当前的角色，控制申请按钮的显隐
			int applyButtonStatus = 0;
			if (getLegion(human.getHumanGuid()) == null) { // 没有加入军团
				if (legionList.get(i).getApplyListMap()
						.get(human.getHumanGuid()) == null) {// 没有申请过该军团
					applyButtonStatus = JoinApplyLegionStatus.CAN_APPLY
							.getIndex();
				} else {
					applyButtonStatus = JoinApplyLegionStatus.APPLIED
							.getIndex();
				}
			} else {// 已加入军团
				applyButtonStatus = JoinApplyLegionStatus.CAN_NOT_APPLY
						.getIndex();
			}
			legionListInfo.setApplyButtonStatus(applyButtonStatus);
			legions[i] = legionListInfo;
		}
		return legions;
	}

	/**
	 * 获取角色所在军团日志
	 */
	public void sendLegionLogList(Human human) {
		Legion legion = this.getLegion(human.getHumanGuid());
		if (legion == null) {
			return;
		}
		List<LegionLog> legionLogList = legion.getLegionLogList();
		GCShowLegionLogTab gcMsg = new GCShowLegionLogTab();
		LegionLog[] legionLogs = new LegionLog[legionLogList.size()];
		gcMsg.setLegionLogs(legionLogList.toArray(legionLogs));
		human.sendMessage(gcMsg);
	}

	/**
	 * 添加军团日志
	 */
	private void addLegionLog(final LegionLog legionLog) {
		final Legion legion = legionMap.get(legionLog.getLegionId());
		LegionLogEntity legionLogEntity = legionLogConverter.convert(legionLog);
		dataService.insert(legionLogEntity, new IDBCallback<Serializable>() {

			@Override
			public void onSucceed(Serializable result) {
				long logId = (Long) result;
				LinkedList<LegionLog> legionLogList = legion.getLegionLogList();
				legionLog.setId(logId);
				if (legionLogList.size() >= SharedConstants.LEGION_LOG_NUM) {
					// 从库中删除日志
					dataService.delete(legionLogConverter.convert(legionLogList
							.getLast()));
					legionLogList.removeLast();
				}
				legionLogList.addFirst(legionLog);
			}

			@Override
			public void onFailed(String errorMsg) {
			}
		});
	}

	/**
	 * 判断角色是否在线
	 */
	public boolean isOnline(long humanGuid) {
		SceneHumanManager humanManager = GameServerAssist.getGameWorld()
				.getSceneHumanManager();
		Human online = humanManager.getHumanByGuid(humanGuid);
		if (online != null) {
			return true;
		}
		return false;
	}

	/**
	 * 角色增加军团经验(因为荣誉增加或战神之巅，暂时废弃)
	 */
	public void addExperienceByHonor(Human human, int value) {
		if (value <= 0) {
			return;
		}
		Legion legion = this.getLegion(human.getHumanGuid());
		if (legion == null) {
			return;
		}
		// 军团已升至最高等级
		if (legion.getLegionLevel() >= SharedConstants.LEGION_MAX_LEVEL) {
			return;
		}
		LegionConstantsTemplate legionConstantsTemplate = legionTemplateManager
				.getConstantsTemplate();
		// 荣誉值兑换军团贡献参数
		int param = legionConstantsTemplate.getHonorToContributionParam();
		int addExperience = value * param;
		if (legion != null) {
			LegionMember legionMember = legion.getMemberListMap().get(
					human.getHumanGuid());
			legionMember.setTodayContribution(legionMember
					.getTodayContribution() + addExperience);
			legionMember.setTotalContribution(legionMember
					.getTotalContribution() + addExperience);
			// 军团升级
			int experience = legion.getExperience() + addExperience;
			while (experience >= legion.getLevelMaxExperience()
					&& legion.getLegionLevel() < SharedConstants.LEGION_MAX_LEVEL) {
				experience -= legion.getLevelMaxExperience();
				this.legionLevelUp(legion);
			}
			legion.setExperience(experience);
			// 刷新军团职位
			this.changeLegionPositions(legion);
			cache.addUpdate(legion.getId(), legionConverter.convert(legion));
			cache.addUpdate(legionMember.getHumanGuid(),
					legionMemberConverter.convert(legionMember));
			// 添加增加军团贡献日志
			LegionLog legionLog = new LegionLog();
			legionLog.setLegionId(legion.getId());
			long now = timeService.now();
			DateFormat format = new SimpleDateFormat("MM-dd hh:mm");
			String operateTime = format.format(new Date(now));
			legionLog.setContent(sysLangService.read(
					LangConstants.LEGION_LOG_HUMAN_CONTRIBUTION_ADD,
					operateTime, human.getName(), addExperience));
			legionLog.setOperateTime(now);
			addLegionLog(legionLog);
		}
	}

	/**
	 * 增加军团经验
	 */
	public void addExperience(Human human, Legion legion, int addExperience,
			boolean notify) {
		if (legion == null || addExperience <= 0) {
			return;
		}
		// 军团已升至最高等级
		if (legion.getLegionLevel() >= SharedConstants.LEGION_MAX_LEVEL) {
			return;
		}
		// 悬浮提示
		if (notify && human != null) {
			String desc = sysLangService.read(LangConstants.LEGION_EXPERICENCE);
			String operate = addExperience >= 0 ? "+" : "-";
			human.sendImportantMessage(LangConstants.COMMON_OBTAIN,
					addExperience, desc, operate);
		}
		// 军团升级
		int experience = legion.getExperience() + addExperience;
		while (experience >= legion.getLevelMaxExperience()
				&& legion.getLegionLevel() < SharedConstants.LEGION_MAX_LEVEL) {
			experience -= legion.getLevelMaxExperience();
			this.legionLevelUp(legion);
		}
		legion.setExperience(experience);
		cache.addUpdate(legion.getId(), legionConverter.convert(legion));
		// 添加增加军团经验日志
		LegionLog legionLog = new LegionLog();
		legionLog.setLegionId(legion.getId());
		long now = timeService.now();
		DateFormat format = new SimpleDateFormat("MM-dd hh:mm");
		String operateTime = format.format(new Date(now));
		legionLog.setContent(sysLangService.read(
				LangConstants.LEGION_LOG_EXPERIENCE_ADD, operateTime,
				addExperience));
		legionLog.setOperateTime(now);
		addLegionLog(legionLog);
	}

	/**
	 * 增加军团资金
	 */
	public void addLegionCoin(Human human, int addCoin, boolean notify) {
		if (addCoin <= 0) {
			return;
		}
		Legion legion = this.getLegion(human.getHumanGuid());
		if (legion == null) {
			return;
		}
		// 增加军团资金
		legion.addCoin(human, addCoin, notify);
		// 添加日志
		LegionLog legionLog = new LegionLog();
		legionLog.setLegionId(legion.getId());
		long now = timeService.now();
		DateFormat format = new SimpleDateFormat("MM-dd hh:mm");
		String operateTime = format.format(new Date(now));
		legionLog.setContent(sysLangService.read(
				LangConstants.LEGION_LOG_COIN_ADD, operateTime, addCoin));
		legionLog.setOperateTime(now);
		addLegionLog(legionLog);
	}

	/**
	 * 角色增加个人贡献值(跟军团经验区分开)
	 */
	public void addSelfContribution(Human human, int addContribution,
			boolean notify) {
		if (addContribution <= 0) {
			return;
		}
		Legion legion = this.getLegion(human.getHumanGuid());
		if (legion == null) {
			return;
		}
		LegionMember legionMember = legion.getMemberListMap().get(
				human.getHumanGuid());
		if (legionMember == null) {
			return;
		}
		// 增加个人贡献
		legionMember.addSelfContribution(human, addContribution, notify);

		// 刷新军团职位
		this.changeLegionPositions(legion);
		cache.addUpdate(legionMember.getHumanGuid(),
				legionMemberConverter.convert(legionMember));
		// 添加增加军团个人贡献日志
		LegionLog legionLog = new LegionLog();
		legionLog.setLegionId(legion.getId());
		long now = timeService.now();
		DateFormat format = new SimpleDateFormat("MM-dd hh:mm");
		String operateTime = format.format(new Date(now));
		legionLog.setContent(sysLangService.read(
				LangConstants.LEGION_LOG_HUMAN_CONTRIBUTION_ADD, operateTime,
				human.getName(), addContribution));
		legionLog.setOperateTime(now);
		addLegionLog(legionLog);
	}

	/**
	 * 增加个人勋章
	 */
	public void addSelfMedal(Human human, int addMedal, boolean notify) {
		if (addMedal <= 0) {
			return;
		}
		Legion legion = this.getLegion(human.getHumanGuid());
		if (legion == null) {
			return;
		}
		LegionMember legionMember = legion.getMemberListMap().get(
				human.getHumanGuid());
		if (legionMember == null) {
			return;
		}
		// 增加勋章
		legionMember.addMedal(human, addMedal, notify);
		// 添加增加勋章日志
		LegionLog legionLog = new LegionLog();
		legionLog.setLegionId(legion.getId());
		long now = timeService.now();
		DateFormat format = new SimpleDateFormat("MM-dd hh:mm");
		String operateTime = format.format(new Date(now));
		legionLog.setContent(sysLangService.read(
				LangConstants.LEGION_LOG_MEDAL_ADD, operateTime,
				human.getName(), addMedal));
		legionLog.setOperateTime(now);
		addLegionLog(legionLog);
	}

	/**
	 * 军团升级
	 */
	public void legionLevelUp(Legion legion) {
		if (legion == null) {
			return;
		}
		if (legion.getLegionLevel() >= SharedConstants.LEGION_MAX_LEVEL) {
			legion.setExperience(0);
			return;
		}
		int oldLevel = legion.getLegionLevel();
		legion.setLegionLevel(oldLevel + 1);
		LegionLevelTemplate legionLevelTemplate = this.legionTemplateManager
				.getLegionLevelTemplate(legion.getLegionLevel());
		legion.setMemberLimit(legionLevelTemplate.getMemberLimit());
		// 刷新军团排行榜
		refreshLegionRank();
	}

	/**
	 * 定时任务：申请超过响应时间删除
	 */
	public void refreshLegionApplyList() {
		List<Legion> legionList = this.getLegionList();
		int limitHour = this.legionTemplateManager.getConstantsTemplate()
				.getRespondApplyHour();
		for (Legion legion : legionList) {
			Map<Long, LegionApply> applyListMap = legion.getApplyListMap();
			Map<Long, LegionApply> deleApplyListMap = new HashMap<Long, LegionApply>();
			for (long humanGuid : applyListMap.keySet()) {
				LegionApply legionApply = applyListMap.get(humanGuid);
				if (TimeUtils.getSoFarWentHours(legionApply.getApplyTime(),
						this.timeService.now()) >= limitHour) {
					deleApplyListMap.put(humanGuid, legionApply);
				}
			}
			// 执行删除（不在上面直接删除时为了避免java.util.ConcurrentModificationException）
			for (long humanGuid : deleApplyListMap.keySet()) {
				applyListMap.remove(humanGuid);
				cache.addDelete(deleApplyListMap.get(humanGuid).getId(),
						legionApplyConverter.convert(deleApplyListMap
								.get(humanGuid)));
			}
		}
	}

	/**
	 * 清空当天冥想日志
	 */
	public void clearDayMeditationLog() {
		List<Legion> legionList = getLegionList();
		for (Legion legion : legionList) {
			LinkedList<LegionMeditationLog> meditationLogList = legion
					.getMeditationLogList();
			Map<Long, LegionMeditationLog> deleMeditationLogMap = new HashMap<Long, LegionMeditationLog>();
			for (LegionMeditationLog legionMeditationLog : meditationLogList) {
				deleMeditationLogMap.put(legionMeditationLog.getId(),
						legionMeditationLog);
			}
			// 执行删除（不在上面直接删除时为了避免java.util.ConcurrentModificationException）
			for (long id : deleMeditationLogMap.keySet()) {
				LegionMeditationLog meditationLog = deleMeditationLogMap
						.get(id);
				meditationLogList.remove(meditationLog);
				cache.addDelete(meditationLog.getId(),
						meditationLogConverter.convert(meditationLog));

			}
		}
	}

	/**
	 * 当日商品购买数量清零
	 */
	private void resetShopItemData() {
		List<Legion> legionList = getLegionList();
		for (Legion legion : legionList) {
			for (int itemId : legion.getShopListMap().keySet()) {
				legion.getShopListMap().get(itemId).setBuyNum(0);
				updateShopItem(legion.getShopListMap().get(itemId));
			}
		}
	}

	/**
	 * 军团成员每日数据重置
	 */
	private void resetLegionMemberDailyData() {
		List<Legion> legionList = getLegionList();
		for (Legion legion : legionList) {
			for (long humanGuid : legion.getMemberListMap().keySet()) {
				LegionMember legionMember = legion.getMemberListMap().get(
						humanGuid);
				if (legionMember == null) {
					continue;
				}
				// 今日贡献清零
				legionMember.setTodayContribution(0);
				// 昨日积分排名
				legionMember.setYesterdayScoreRank(getTaskScoreRank(humanGuid));
				// 今日积分清零
				legionMember.setTodayTaskScore(0);
				// 添加缓存
				updateLegionMember(legionMember);
			}
		}
	}

	/**
	 * 获取任务积分排名
	 */
	public int getTaskScoreRank(long humanGuid) {
		Legion legion = getLegion(humanGuid);
		List<LegionMember> memberList = getLegionMemberList(legion);
		Collections.sort(memberList, new Comparator<LegionMember>() {
			@Override
			public int compare(LegionMember o1, LegionMember o2) {
				return o2.getTodayTaskScore() - o1.getTodayTaskScore();
			}
		});
		// 任务积分排行
		int rank = 0;
		for (LegionMember member : memberList) {
			rank++;
			if (member.getHumanGuid() == humanGuid) {
				if (member.getTodayTaskScore() == 0) {
					return -1;
				}
				return rank;
			}
		}
		return rank;
	}

	/**
	 * 是否加入军团
	 */
	public boolean isJoinedLegion(Human human) {
		if (humanLegionMap.get(human.getHumanGuid()) != null) {
			return true;
		}
		return false;
	}

	/**
	 * 构建成员列表信息
	 * 
	 */
	public LegionMemberListInfo[] generateMemberListInfo(Human human) {
		Legion legion = getLegion(human.getHumanGuid());
		List<LegionMember> legionMemberList = this.getLegionMemberList(legion);
		int size = legionMemberList.size();
		LegionMemberListInfo[] legionMembers = new LegionMemberListInfo[size];
		for (int i = 0; i < size; i++) {
			LegionMember legionMember = legionMemberList.get(i);
			LegionMemberListInfo legionMemberListInfo = this
					.legionMemberToInfo(legionMember);
			// 离线时间间隔
			if (isOnline(legionMember.getHumanGuid())) {
				legionMemberListInfo.setOffLineTimeInterval(0);
			} else {
				long now = GameServerAssist.getSystemTimeService().now();
				legionMemberListInfo
						.setOffLineTimeInterval((int) (now - legionMember
								.getOffLineTime()));
			}
			int position = legion.getMemberListMap().get(human.getHumanGuid())
					.getPosition();
			LegionRightTemplate legionRightTemplate = GameServerAssist
					.getLegionTemplateManager()
					.getLegionRightTemplate(position);
			// 转让权限
			legionMemberListInfo.setTransferButtonVisible(legionRightTemplate
					.getTransferLegion() == 1);
			// 删除权限
			if (legionRightTemplate.getRemoveMember() == 1
					&& position < legionMember.getPosition()) {
				legionMemberListInfo.setRemoveButtonVisible(true);
			}
			legionMembers[i] = legionMemberListInfo;
		}
		return legionMembers;
	}

	/**
	 * 添加公告
	 */
	public void addLegionNotice(Human human, String notice) {
		Legion legion = this.getLegion(human.getHumanGuid());
		legion.setNotice(notice);
		cache.addUpdate(legion.getId(), legionConverter.convert(legion));
		GCAddLegionNotice msg = new GCAddLegionNotice();
		msg.setLegionNotice(notice);
		human.sendMessage(msg);
	}

	/**
	 * 军团排行榜同时刷新
	 */
	private void refreshLegionRank() {
		refreshLegionLevelRank();
		refreshLegionMemberRank();
	}

	/**
	 * 军团等级变化刷新军团等级排行榜
	 */
	private void refreshLegionLevelRank() {
		GameServerAssist.getRankManager().refreshRankData(
				RankType.LEGION_LEVEL_RANK);
	}

	/**
	 * 军团人数变化刷新军团人数排行榜
	 */
	private void refreshLegionMemberRank() {
		GameServerAssist.getRankManager().refreshRankData(
				RankType.LEGION_MEMBER_RANK);
	}

	/**
	 * 获取军团建筑
	 */
	public LegionBuilding getBuilding(Legion legion,
			final LegionBuildingType buildingType) {
		LegionBuilding legionBuilding = legion.getBuildingListMap().get(
				buildingType.getIndex());
		// 如果为空，插入数据库
		if (legionBuilding == null) {
			legionBuilding = new LegionBuilding();
			legionBuilding.setBuildingType(buildingType.getIndex());
			legionBuilding.setLegionId(legion.getId());
			legionBuilding.setBuildingLevel(1);
			addBuilding(legionBuilding);
		}
		return legionBuilding;
	}

	/**
	 * 更新军团建筑
	 */
	public void updateBuilding(LegionBuilding building) {
		cache.addUpdate(building.getId(), buildingConverter.convert(building));
	}

	/**
	 * 插入军团建筑
	 */
	public void addBuilding(final LegionBuilding legionBuilding) {
		final LegionBuildingEntity buildingEntity = buildingConverter
				.convert(legionBuilding);
		dataService.insert(buildingEntity, new IDBCallback<Serializable>() {

			@Override
			public void onSucceed(Serializable result) {
				legionMap
						.get(legionBuilding.getLegionId())
						.getBuildingListMap()
						.put(legionBuilding.getBuildingType(),
								buildingConverter
										.reverseConvert(buildingEntity));
			}

			@Override
			public void onFailed(String errorMsg) {

			}
		});
	}

	/**
	 * 生成建筑信息
	 */
	public LegionBuildingInfo generateBuildingInfo(Legion legion,
			LegionBuildingType buildingType) {
		LegionTemplateManager templateManager = GameServerAssist
				.getLegionTemplateManager();
		LegionBuilding legionBuilding = getBuilding(legion, buildingType);
		LegionBuildingInfo buildingInfo = new LegionBuildingInfo();
		buildingInfo.setBuildingLevel(legionBuilding.getBuildingLevel());
		buildingInfo.setCurrentNum(templateManager.getBuildingOpenNum(
				buildingType, legionBuilding.getBuildingLevel()));
		buildingInfo.setNextNum(templateManager.getBuildingOpenNum(
				buildingType, legionBuilding.getBuildingLevel() + 1));
		buildingInfo.setNeedLegionCoin(templateManager.getUpgradeBuildingCost(
				buildingType, legionBuilding.getBuildingLevel()));
		buildingInfo.setCurrentLegionCoin(legion.getLegionCoin());
		return buildingInfo;
	}

	/**
	 * 添加军团冥想日志
	 */
	public void addMeditationLog(final Legion legion,
			final LegionMeditationLog meditationLog) {
		final LegionMeditationLogEntity legionLogEntity = meditationLogConverter
				.convert(meditationLog);
		dataService.insert(legionLogEntity, new IDBCallback<Serializable>() {

			@Override
			public void onSucceed(Serializable result) {
				long logId = (Long) result;
				LinkedList<LegionMeditationLog> legionLogList = legion
						.getMeditationLogList();
				meditationLog.setId(logId);
				if (legionLogList.size() >= SharedConstants.LEGION_MEDITATION_LOG_NUM) {
					// 从库中删除日志
					dataService.delete(meditationLogConverter
							.convert(legionLogList.getLast()));
					legionLogList.removeLast();
				}
				legionLogList.addFirst(meditationLogConverter
						.reverseConvert(legionLogEntity));
			}

			@Override
			public void onFailed(String errorMsg) {
			}
		});
	}

	/**
	 * 获取商店商品信息
	 */
	public LegionShop getShopItem(Legion legion, int itemId) {
		LegionShop shopItem = legion.getShopListMap().get(itemId);
		if (shopItem == null) {
			shopItem = new LegionShop();
			shopItem.setLegionId(legion.getId());
			shopItem.setItemId(itemId);
			shopItem.setBuyNum(0);
			addShopItem(shopItem);
		}
		return shopItem;
	}

	/**
	 * 更新商店商品
	 */
	public void updateShopItem(LegionShop shop) {
		cache.addUpdate(shop.getId(), shopConverter.convert(shop));
	}

	/**
	 * 插入商店商品信息
	 */
	public void addShopItem(final LegionShop legionShop) {
		dataService.insert(shopConverter.convert(legionShop),
				new IDBCallback<Serializable>() {

					@Override
					public void onSucceed(Serializable result) {
						Long shopId = (Long) result;
						Legion legion = getLegionById(legionShop.getLegionId());
						legionShop.setId(shopId);
						legion.getShopListMap().put(legionShop.getItemId(),
								legionShop);
					}

					@Override
					public void onFailed(String errorMsg) {

					}
				});
	}

	/**
	 * 获取军团科技
	 */
	public LegionTechnology getTechnology(Legion legion, int technologyType) {
		LegionTechnology technology = legion.getTechnologyListMap().get(
				technologyType);
		if (technology == null) {
			technology = new LegionTechnology();
			technology.setLegionId(legion.getId());
			technology.setTechnologyType(technologyType);
			technology.setTechnologyLevel(1);
			technology.setCurrentCoin(0);
			addTechnology(technology);
		}
		return technology;
	}

	/**
	 * 生成科技信息
	 */
	public LegionTechnologyInfo generateTechnologyInfo(Legion legion,
			LegionTechnology technology) {
		LegionTechnologyTypeTemplate typeTemplate = legionTemplateManager
				.getTechnologyTypeTemplate(technology.getTechnologyType());
		LegionTechnologyInfo techInfo = new LegionTechnologyInfo();
		techInfo.setTechnologyType(typeTemplate.getId());
		techInfo.setTechnologyName(typeTemplate.getTechName());
		techInfo.setTechnologyLevel(technology.getTechnologyLevel());
		techInfo.setIconId(typeTemplate.getIconId());
		techInfo.setAmendDesc(typeTemplate.getTechDesc());
		techInfo.setUpCurrentCoin(technology.getCurrentCoin());
		LegionTechnologyTemplate techTemplate = legionTemplateManager
				.getTechnologyTemplate(typeTemplate.getId(),
						technology.getTechnologyLevel());
		if (techTemplate != null) {
			techInfo.setUpNeedCoin(techTemplate.getUpNeedCoin());
		}
		techInfo.setAmendEffect(techTemplate.getAmendEffect());
		techInfo.setAmendMethod(techTemplate.getAmendMethod());
		return techInfo;
	}

	/**
	 * 插入军团科技信息
	 */
	public void addTechnology(LegionTechnology technology) {
		final LegionTechnologyEntity entity = technologyConverter
				.convert(technology);
		dataService.insert(entity, new IDBCallback<Serializable>() {
			@Override
			public void onSucceed(Serializable result) {
				Legion legion = getLegionById(entity.getLegionId());
				legion.getTechnologyListMap().put(entity.getTechnologyType(),
						technologyConverter.reverseConvert(entity));
			}

			@Override
			public void onFailed(String errorMsg) {

			}
		});
	}

	/**
	 * 更新科技
	 */
	public void updateTechnology(LegionTechnology technology) {
		cache.addUpdate(technology.getId(),
				technologyConverter.convert(technology));
	}

	/**
	 * 获取军团荣誉
	 */
	public LegionHonor getHonor(final Legion legion, final int titleId) {
		LegionHonor honor = legion.getHonorListMap().get(titleId);
		if (honor == null) {
			honor = new LegionHonor();
			honor.setLegionId(legion.getId());
			honor.setTitleId(titleId);
			honor.setExchangeNum(0);
			addHonor(legion, honor);
		}
		return honor;
	}

	/**
	 * 根据模板生成头衔信息
	 */
	public LegionTitleInfo generateTitleInfo(int titleId, Legion legion) {
		LegionHonorTemplate titleTemplate = legionTemplateManager
				.getHonorTemplate(titleId);
		LegionTitleInfo titleInfo = new LegionTitleInfo();
		titleInfo.setTitleId(titleTemplate.getId());
		titleInfo.setTitelName(titleTemplate.getTitleName());
		titleInfo.setIconId(titleTemplate.getIconId());
		titleInfo.setColorId(titleTemplate.getColorId());
		titleInfo.setNeedPositionName(titleTemplate.getNeedPositionName());
		titleInfo.setMaxNum(titleTemplate.getMaxNum());
		LegionHonor legionHonor = GameServerAssist.getGlobalLegionManager()
				.getHonor(legion, titleTemplate.getId());
		titleInfo.setRemainNum(titleTemplate.getMaxNum()
				- legionHonor.getExchangeNum());
		titleInfo.setNeedMedal(titleTemplate.getCostMedal());
		titleInfo.setIsAllProperty(titleTemplate.isAllProperty());
		titleInfo.setPropertyId(titleTemplate.getPropertyId());
		titleInfo.setPropertyRate(titleTemplate.getAmendEffect());
		titleInfo.setValidDays(titleTemplate.getValidDays());
		titleInfo.setNeedBuildingLevel(titleTemplate.getNeedBuildingLevle());
		return titleInfo;
	}

	/**
	 * 插入军团荣誉信息
	 */
	public void addHonor(final Legion legion, final LegionHonor honor) {
		final LegionHonorEntity entity = honorConverter.convert(honor);
		dataService.insert(entity, new IDBCallback<Serializable>() {

			@Override
			public void onSucceed(Serializable result) {
				legion.getHonorListMap().put(honor.getTitleId(),
						honorConverter.reverseConvert(entity));
			}

			@Override
			public void onFailed(String errorMsg) {

			}
		});
	}

	/**
	 * 更新荣誉信息
	 */
	public void updateHonor(LegionHonor honor) {
		cache.addUpdate(honor.getId(), honorConverter.convert(honor));
	}

	/**
	 * 获取军团任务主题
	 */
	public Map<Integer, String> getTaskThemeMap() {
		return taskThemeMap;
	}

	/**
	 * 判断是否有任务积分奖励
	 */
	public boolean hasTaskRankReward(Human human) {
		if (human.isGodLegionTaskRankReward()) {
			return false;
		}
		LegionMember member = getLegionMember(human.getHumanGuid());
		int rank = member.getYesterdayScoreRank();
		if (rank >= 1 && rank <= SharedConstants.LEGION_TASK_RANK_NUM) {
			return true;
		}
		return false;
	}

	/**
	 * 获取军团科技收益加成
	 */
	public LegionTechnologyAmend getTechnologyAmend(long humanGuid,
			LegionTechnologyType legionTechnologyType) {
		Legion legion = getLegion(humanGuid);
		if (legion == null) {
			return null;
		}
		// 判断建筑等级是否开启
		LegionTechnologyTypeTemplate typeTemplate = legionTemplateManager
				.getTechnologyTypeTemplate(legionTechnologyType.getIndex());
		if (typeTemplate == null) {
			return null;
		}
		LegionBuilding building = getBuilding(legion,
				LegionBuildingType.TECHNOLOGY);
		if (building.getBuildingLevel() < typeTemplate.getNeedBuildingLevel()) {
			return null;
		}
		LegionTechnology technology = getTechnology(legion,
				legionTechnologyType.getIndex());
		LegionTechnologyTemplate techTemplate = legionTemplateManager
				.getTechnologyTemplate(legionTechnologyType.getIndex(),
						technology.getTechnologyLevel());
		if (techTemplate == null) {
			return null;
		}
		LegionTechnologyAmend amend = new LegionTechnologyAmend();
		amend.setAmendMethod(AmendMethod.valueOf(techTemplate.getAmendMethod()));
		amend.setAmendValue(techTemplate.getAmendEffect());
		return amend;
	}

	/**
	 * 军团子功能是否开启
	 */
	public boolean legionFuncIsOpen(Human human,
			LegionBuildingType buildingType, boolean notify) {
		Legion legion = getLegion(human.getHumanGuid());
		if (legion == null) {
			return false;
		}
		int legionLevel = legion.getLegionLevel();
		LegionFuncTemplate funcTempalte = legionTemplateManager
				.getFuncTemplate(buildingType);
		if (legionLevel < funcTempalte.getOpenLegionLevel()) {
			if (notify) {
				human.sendWarningMessage(LangConstants.LEGION_LEVEL_NOT_OPEN);
			}
			return false;
		}
		return true;
	}
}