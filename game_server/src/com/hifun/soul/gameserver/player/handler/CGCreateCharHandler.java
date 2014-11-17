package com.hifun.soul.gameserver.player.handler;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hifun.soul.common.auth.LoginChar;
import com.hifun.soul.common.constants.LangConstants;
import com.hifun.soul.common.constants.SharedConstants;
import com.hifun.soul.common.constants.SystemMessageType;
import com.hifun.soul.common.service.DirtFilterService;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.core.uuid.UUIDService;
import com.hifun.soul.core.uuid.UUIDType;
import com.hifun.soul.gamedb.agent.query.DataQueryConstants;
import com.hifun.soul.gamedb.callback.IDBCallback;
import com.hifun.soul.gamedb.callback.IMainThreadDBCallback;
import com.hifun.soul.gamedb.entity.HumanEntity;
import com.hifun.soul.gamedb.entity.RankEntity;
import com.hifun.soul.gamedb.service.IDataService;
import com.hifun.soul.gameserver.bag.BagType;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.compass.ICompassService;
import com.hifun.soul.gameserver.human.occupation.OccupationTemplateManager;
import com.hifun.soul.gameserver.human.occupation.template.CharacterOccupationTemplate;
import com.hifun.soul.gameserver.item.Item;
import com.hifun.soul.gameserver.item.assist.ItemFactory;
import com.hifun.soul.gameserver.player.Player;
import com.hifun.soul.gameserver.player.auth.CreateCharResultCode;
import com.hifun.soul.gameserver.player.msg.CGCreateChar;
import com.hifun.soul.gameserver.player.msg.GCCharList;
import com.hifun.soul.gameserver.player.msg.GCCreateCharResult;
import com.hifun.soul.gameserver.role.Occupation;
import com.hifun.soul.gameserver.role.properties.HumanIntProperty;
import com.hifun.soul.gameserver.role.properties.PropertyType;
import com.hifun.soul.gameserver.skill.SkillDevelopType;
import com.hifun.soul.gameserver.skill.SkillStateType;
import com.hifun.soul.gameserver.skill.template.SkillTemplateManager;
import com.hifun.soul.proto.common.Character.CharProperty;
import com.hifun.soul.proto.data.entity.Entity.HumanBaseProperties;
import com.hifun.soul.proto.data.entity.Entity.HumanCarriedSkill;
import com.hifun.soul.proto.data.entity.Entity.HumanItem;
import com.hifun.soul.proto.data.entity.Entity.HumanOtherProperties;

/**
 * 玩家请求创建角色;
 * 
 * @author crazyjohn
 * 
 */
@Component
public class CGCreateCharHandler implements
		IMessageHandlerWithType<CGCreateChar> {
	@Autowired
	private IDataService dataService;
	@Autowired
	private UUIDService uuidService;
	@Autowired
	private DirtFilterService dirtFilterService;
	@Autowired
	private OccupationTemplateManager templateService;
	@Autowired
	private SkillTemplateManager skillTemplateManager;
	/** 罗盘服务 */
	private ICompassService compassService;

	@Override
	public short getMessageType() {
		return MessageType.CG_CREATE_CHAR;
	}

	@Override
	public void execute(final CGCreateChar message) {
		final Player player = message.getSession().getPlayer();
		if (player == null) {
			return;
		}
		if (message.getName() == null || message.getName().trim().equals("")) {
			return;
		}
		// 角色是否已经满了
		if (player.getCharSize() >= SharedConstants.PLAYER_MAX_CHAR_SIZE) {
			sendCreateResult(message.getSession().getPlayer(),
					CreateCharResultCode.CHAR_FULL);
			return;
		}
		try {
			// 角色长度是否超长
			if (message.getName().getBytes("GBK").length > GameServerAssist
					.getGameConstants().getMaxRoleNameSize()) {
				player.sendSystemMessage(
						SystemMessageType.ERROR,
						GameServerAssist.getSysLangService().read(
								LangConstants.CHARACTER_NAME_TOO_LONG));
				return;
			}
			// 角色长度是否过短
			if (message.getName().getBytes("GBK").length < GameServerAssist
					.getGameConstants().getMinRoleNameSize()) {
				player.sendSystemMessage(
						SystemMessageType.ERROR,
						GameServerAssist.getSysLangService().read(
								LangConstants.CHARACTER_NAME_TOO_SHORT));
				return;
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return;
		}
		// 角色名中是否含有屏蔽字符
		if (dirtFilterService.containsName(message.getName())) {
			String langName = GameServerAssist.getSysLangService().read(
					LangConstants.CHARACTER_NAME);
			String content = GameServerAssist.getSysLangService().read(
					LangConstants.GAME_INPUT_ERROR2, langName);
			player.sendSystemMessage(SystemMessageType.ERROR, content);
			return;
		}
		// 是否有重名
		dataService.query(DataQueryConstants.QUERY_HUMAN_BY_NAME,
				new String[] { "name" }, new Object[] { message.getName() },
				new IMainThreadDBCallback<List<?>>() {

					@Override
					public void onSucceed(List<?> result) {
						// 如果结果不为空
						if (!result.isEmpty()) {
							sendCreateResult(message.getSession().getPlayer(),
									CreateCharResultCode.USERNAME_EXIST);
							return;
						}
						// 根据信息创建角色
						final HumanEntity entity = new HumanEntity();
						final long guid = uuidService.getNextUUID(UUIDType.HUMAN);
						entity.setId(guid);

						HumanBaseProperties.Builder baseProps = HumanBaseProperties
								.newBuilder();
						baseProps.setHumanGuid(entity.getId());
						baseProps.setName(message.getName());
						baseProps
								.setHomeLevel(SharedConstants.INIT_HUMAN_LEVEL);
						baseProps.setLevel(SharedConstants.INIT_HUMAN_LEVEL);
						baseProps.setPassportId(message.getSession()
								.getPlayer().getPassportId());
						baseProps.setOccupation(message.getOccupation());
						baseProps.setCoins(SharedConstants.INIT_HUMAN_COINS);
						baseProps.setEnergy(GameServerAssist.getGameConstants()
								.getMaxEnergy());

						// 角色其它属性
						HumanOtherProperties.Builder otherProps = HumanOtherProperties
								.newBuilder();
						otherProps.setHumanGuid(entity.getId());
						// 刚创建角色的时候, 角色的otherProps为空;
						// 添加属性
						otherProps.addProps(CharProperty
								.newBuilder()
								.setKey(PropertyType.genPropertyKey(
										HumanIntProperty.LEVEL,
										PropertyType.HUMAN_INT_PROPERTY))
								.setValue(SharedConstants.INIT_HUMAN_LEVEL));
						otherProps.addProps(CharProperty
								.newBuilder()
								.setKey(PropertyType.genPropertyKey(
										HumanIntProperty.BAG_SIZE,
										PropertyType.HUMAN_INT_PROPERTY))
								.setValue(SharedConstants.INIT_HUMAN_BAG_SIZE));
						// 设置精灵背包格子大小
						otherProps.addProps(CharProperty
								.newBuilder()
								.setKey(PropertyType.genPropertyKey(
										HumanIntProperty.SPRITE_BAG_CELL_SIZE,
										PropertyType.HUMAN_INT_PROPERTY))
								.setValue(SharedConstants.INIT_HUMAN_SPRITE_BAG_SIZE));
						// 设置初始技能系
						otherProps.addProps(CharProperty
								.newBuilder()
								.setKey(PropertyType.genPropertyKey(
										HumanIntProperty.SKILL_DEVELOP_TYPE,
										PropertyType.HUMAN_INT_PROPERTY))
								.setValue(SkillDevelopType.GEM.getIndex()));
						otherProps.addProps(CharProperty
								.newBuilder()
								.setKey(PropertyType.genPropertyKey(
										HumanIntProperty.HOME_LEVEL,
										PropertyType.HUMAN_INT_PROPERTY))
								.setValue(SharedConstants.INIT_HUMAN_LEVEL));
						// 设置职业
						otherProps.addProps(CharProperty
								.newBuilder()
								.setKey(PropertyType.genPropertyKey(
										HumanIntProperty.OCCUPATION,
										PropertyType.HUMAN_INT_PROPERTY))
								.setValue(message.getOccupation()));
						// 设置钱币
						otherProps.addProps(CharProperty
								.newBuilder()
								.setKey(PropertyType.genPropertyKey(
										HumanIntProperty.COIN,
										PropertyType.HUMAN_INT_PROPERTY))
								.setValue(SharedConstants.INIT_HUMAN_COINS));
						// 设置精力
						otherProps.addProps(CharProperty
								.newBuilder()
								.setKey(PropertyType.genPropertyKey(
										HumanIntProperty.ENERGY,
										PropertyType.HUMAN_INT_PROPERTY))
								.setValue(
										GameServerAssist.getGameConstants()
												.getMaxEnergy()));

						entity.getBuilder().setBaseProperties(baseProps);
						entity.getBuilder().setOtherProperties(otherProps);
						// 设置出生礼包
						Occupation occupation = Occupation.typeOf(message
								.getOccupation());
						if (occupation == null) {
							return;
						}
						CharacterOccupationTemplate occupationTemplate = templateService
								.getOccupationTemplateByOccupation(occupation);
						if (occupationTemplate == null) {
							return;
						}
						Item item = ItemFactory.creatNewItem(null,
								occupationTemplate.getGiftId());
						if (item != null) {
							item.setBagIndex(0);
							HumanItem.Builder itemBuilder = ItemFactory
									.converItemToHumanItem(entity.getId(),
											item, BagType.MAIN_BAG);
							entity.getBuilder().addItem(itemBuilder);
						}
						// 设置初始携带技能
						// 根据职业找到初始技能
						int defaultSkillId = skillTemplateManager.getDefaultSkillId(occupation.getIndex());
						if(defaultSkillId > 0){
							HumanCarriedSkill.Builder builder = HumanCarriedSkill.newBuilder();
							builder.setHumanGuid(entity.getId());
							builder.getSkillBuilder().setSkillId(defaultSkillId);
							builder.getSkillBuilder().setSlotIndex(0);
							builder.getSkillBuilder().setSkillState(SkillStateType.STUDYED.getIndex());
							entity.getBuilder().addSkill(builder);
						}
						dataService.insert(entity,
								new IMainThreadDBCallback<Long>() {

									@Override
									public void onSucceed(Long result) {
										// 注册罗盘汇报
										if (compassService == null) {
											compassService = GameServerAssist
													.getCompassService();
										}
										if (compassService != null) {
											compassService.register(
													(int) guid,
													player.getAccount());
										}
										// 如果插入成功, 则执行
										LoginChar loginChar = new LoginChar();
										loginChar.setHumanGuid(entity.getId());
										loginChar.setName(message.getName());
										loginChar
												.setHomeLevel(SharedConstants.INIT_HUMAN_BUILDING_LEVEL);
										loginChar
												.setLevel(SharedConstants.INIT_HUMAN_LEVEL);
										loginChar
												.setCoins(SharedConstants.INIT_HUMAN_COINS);
										loginChar.setEnergy(GameServerAssist
												.getGameConstants()
												.getMaxEnergy());
										loginChar.setOccupation(message
												.getOccupation());
										player.addLoginChar(loginChar);
										GCCharList charList = build(player);
										player.sendMessage(charList);
									}

									@Override
									public void onFailed(String errorMsg) {
										sendCreateResult(
												message.getSession()
														.getPlayer(),
												CreateCharResultCode.USERNAME_EXIST);
									}

								});
						GCCreateCharResult createResult = new GCCreateCharResult();
						createResult.setResultCode(CreateCharResultCode.SUCCESS
								.getResultCode());
						message.getSession().getPlayer()
								.sendMessage(createResult);
						// 往排行数副本中添加该角色对应的排行数据
						RankEntity rankEntity = new RankEntity();
						rankEntity.setId(guid);
						rankEntity.setHumanName(message.getName());
						rankEntity.setOccupation(message.getOccupation());
						rankEntity.setLevel(SharedConstants.INIT_HUMAN_LEVEL);
						rankEntity.setValid(true);
						dataService.insert(rankEntity, new IDBCallback<Long>() {
							@Override
							public void onSucceed(Long result) {

							}

							@Override
							public void onFailed(String errorMsg) {

							}
						});
					}

					@Override
					public void onFailed(String errorMsg) {
						// TODO Auto-generated method stub

					}

				});
	}

	private void sendCreateResult(Player player, CreateCharResultCode resultCode) {
		GCCreateCharResult createResultMsg = new GCCreateCharResult();
		createResultMsg.setResultCode(resultCode.getResultCode());
		player.sendMessage(createResultMsg);
	}

	public static GCCharList build(Player player) {
		GCCharList charListMsg = new GCCharList();
		List<LoginChar> charList = new ArrayList<LoginChar>(player.getChars());
		charListMsg.setCharList(charList.toArray(new LoginChar[0]));
		return charListMsg;
	}

}
