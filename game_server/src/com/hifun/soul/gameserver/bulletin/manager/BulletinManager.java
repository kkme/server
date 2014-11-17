package com.hifun.soul.gameserver.bulletin.manager;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.hifun.soul.common.IHeartBeatable;
import com.hifun.soul.core.orm.IDBService;
import com.hifun.soul.core.time.SystemTimeService;
import com.hifun.soul.core.util.TimeUtils;
import com.hifun.soul.gamedb.agent.query.DataQueryConstants;
import com.hifun.soul.gamedb.callback.IDBCallback;
import com.hifun.soul.gamedb.entity.BulletinEntity;
import com.hifun.soul.gamedb.service.DataService;
import com.hifun.soul.gameserver.bulletin.Bulletin;
import com.hifun.soul.gameserver.bulletin.BulletinType;
import com.hifun.soul.gameserver.bulletin.DelayBulletin;
import com.hifun.soul.gameserver.bulletin.RegularBulletin;
import com.hifun.soul.gameserver.bulletin.assist.CommonBulletin;
import com.hifun.soul.gameserver.bulletin.assist.CommonBulletinBuilder;
import com.hifun.soul.gameserver.bulletin.msg.GCBulletinList;
import com.hifun.soul.gameserver.bulletin.msg.GCBulletinStop;
import com.hifun.soul.gameserver.common.GameServerAssist;

/**
 * 公告管理器
 * 
 * @author magicstone
 * 
 */

@Scope("singleton")
@Component
public class BulletinManager implements IHeartBeatable {
	/** 系统默认公告级别 */
	private static final int DEFAULT_SYSTEM_BULLETIN_LEVEL = 1;
	/** 系统默认公告显示时长 */
	private static final int DEFAULT_SYSTEM_BULLETIN_SHOWTIME = 5;
	@Autowired
	private DataService dataService;
	@Autowired
	private SystemTimeService sysTimeService;
	/** 有效公告 */
	protected List<Bulletin> bulletins = new ArrayList<Bulletin>();
	/** 新增公告 */
	protected List<Bulletin> addedBulletins = new ArrayList<Bulletin>();
	/** 过期公告 */
	protected List<Bulletin> removedBulletins = new ArrayList<Bulletin>();

	private boolean isChanged;

	public void initData(IDBService dbService) {
		// 加载数据
		List<?> result = dbService.findByNamedQuery(DataQueryConstants.QUERY_VALID_BULLETIN);
		if (result == null || result.isEmpty()) {
			return;
		}
		for (int i = 0; i < result.size(); i++) {
			BulletinEntity entity = (BulletinEntity) result.get(i);
			BulletinType bulletinType = BulletinType.indexOf(entity.getBulletinType());
			switch (bulletinType) {
			case DELAY_BULLETIN:
				bulletins.add(new DelayBulletin(entity));
				break;
			case REGULAR_BULLETIN:
				bulletins.add(new RegularBulletin(entity));
				break;
			default:break;
			}
		}
	}

	/**
	 * 持久化
	 */
	protected void persistence() {
		if (removedBulletins.size() > 0) {
			for (Bulletin bulletin : removedBulletins) {
				BulletinEntity entity = bulletin.toEntity();
				dataService.update(entity);
			}
			removedBulletins.clear();
		}
		if (addedBulletins.size() > 0) {
			for (final Bulletin bulletin : addedBulletins) {
				BulletinEntity entity = bulletin.toEntity();
				entity.setCreateTime(new Date());
				dataService.insert(entity, new IDBCallback<Integer>() {
					@Override
					public void onSucceed(Integer result) {
						bulletin.setId(result);
					}

					@Override
					public void onFailed(String errorMsg) {

					}

				});
			}
			addedBulletins.clear();
		}

	}

	@Override
	public void heartBeat() {
		long now = sysTimeService.now();
		Bulletin[] bulletinArray = getSuitableBulletins(now);
		if (bulletinArray.length > 0) {
			CommonBulletin[] commonBulletins = new CommonBulletin[bulletinArray.length];
			for (int i = 0; i < bulletinArray.length; i++) {
				commonBulletins[i] = CommonBulletinBuilder.convertFrom(bulletinArray[i]);
				commonBulletins[i].setContent(commonBulletins[i].getContent());
			}
 			GCBulletinList bulletinMsg = new GCBulletinList();
			bulletinMsg.setBulletins(commonBulletins);
			GameServerAssist.getGameWorld().boradcast(bulletinMsg);
		}
		if (isChanged) {
			this.isChanged = false;
			persistence();
		}
	}

	/**
	 * 获取当前时间应该公布的公告
	 * 
	 * @param time
	 * @return
	 */
	private Bulletin[] getSuitableBulletins(long now) {
		List<Bulletin> suitableBulletins = new ArrayList<Bulletin>();
		// 查询到点的公告
		for (int i = bulletins.size() - 1; i >= 0; i--) {
			Bulletin bulletin = bulletins.get(i);
			switch (bulletin.getBulletinType()) {
			case DELAY_BULLETIN:
				DelayBulletin delay = (DelayBulletin) bulletin;
				if (delay.getPublishTime() < now) {
					suitableBulletins.add(bulletin);
					bulletinOverdue(bulletin);
				}
				break;
			case REGULAR_BULLETIN:
				RegularBulletin regular = (RegularBulletin) bulletin;
				if (isSuitableRegularBulletin(regular, now)) {
					suitableBulletins.add(regular);
				}
				break;
			default:
				break;
			}
		}
		return suitableBulletins.toArray(new Bulletin[0]);
	}

	/**
	 * 判断当前公告是否该发布了
	 * 
	 * @param regular
	 * @param now
	 * @return
	 */
	private boolean isSuitableRegularBulletin(RegularBulletin regular, long now) {
		boolean result = false;
		if (regular.getStartDate().getTime() < now && regular.getEndDate().getTime() >= now) {
			long todayBegin = TimeUtils.getBeginOfDay(now);
			long startTime = regular.getStartTime().getTime();
			startTime = todayBegin + startTime - TimeUtils.getBeginOfDay(startTime);
			long endTime = regular.getEndTime().getTime();
			endTime = todayBegin + endTime - TimeUtils.getBeginOfDay(endTime);
			if ((now >= startTime) && (now <= endTime) && now >= regular.getNextPublishTime()) {
				long nextPubTime = now + regular.getPublishInterval() * TimeUtils.SECOND;
				if (nextPubTime <= endTime) {
					regular.setNextPublishTime(nextPubTime);
				} else {
					regular.setNextPublishTime(startTime + TimeUtils.DAY);
				}
				result = true;
			}
			if (regular.getNextPublishTime() > regular.getEndDate().getTime()) {
				bulletinOverdue(regular);
			}
		}
		return result;
	}

	/**
	 * 公告过期
	 * 
	 * @param bulletin
	 */
	private void bulletinOverdue(Bulletin bulletin) {
		bulletin.setValid(false);
		this.removedBulletins.add(bulletin);
		this.bulletins.remove(bulletin);
		isChanged = true;
	}

	/**
	 * 添加公告
	 * 
	 * @param bulletin
	 */
	public void addBulletin(Bulletin bulletin) {
		this.addedBulletins.add(bulletin);
		this.bulletins.add(bulletin);
		this.isChanged = true;
	}

	/**
	 * 更新公告
	 * 
	 * @param bulletin
	 */
	public void updateBulletin(Bulletin newBulletin) {
		for (int i = bulletins.size() - 1; i >= 0; i--) {
			Bulletin bulletin = bulletins.get(i);
			if (bulletin.getId() == newBulletin.getId()) {
				bulletin.setValid(false);
				removedBulletins.add(bulletin);
				addedBulletins.add(newBulletin);
				bulletins.remove(i);
				bulletins.add(newBulletin);
				this.isChanged = true;
				break;
			}
		}
	}

	/**
	 * 移除公告
	 * 
	 * @param bulletinId
	 */
	public void removeBulletin(int bulletinId) {
		for (int i = bulletins.size() - 1; i >= 0; i--) {
			Bulletin bulletin = bulletins.get(i);
			if (bulletin.getId() == bulletinId) {
				bulletin.setValid(false);
				removedBulletins.add(bulletin);
				bulletins.remove(i);
				this.isChanged = true;
				sendBulletinStopMessage(bulletinId);
				break;
			}
		}
	}

	/**
	 * 即时发送公告
	 * 
	 * @param content
	 *            内容
	 * @param bulletinLevel
	 *            显示级别
	 * @param showTime
	 *            显示时长（s）
	 */
	public void sendBulletinMessage(String content, int bulletinLevel, int showTime) {
		GCBulletinList bulletinMsg = new GCBulletinList();
		CommonBulletin[] bulletins = new CommonBulletin[1];
		bulletins[0] = CommonBulletinBuilder.buildCommonBulletin(content, bulletinLevel, showTime);
		bulletinMsg.setBulletins(bulletins);
		GameServerAssist.getGameWorld().boradcast(bulletinMsg);
	}

	/**
	 * 即时发送公告 (使用系统默认公告显示级别和显示时长)
	 * 
	 * @param content
	 *            内容
	 */
	public void sendSystemBulletin(String content) {
		sendBulletinMessage(content, BulletinManager.DEFAULT_SYSTEM_BULLETIN_LEVEL,
				BulletinManager.DEFAULT_SYSTEM_BULLETIN_SHOWTIME);
	}

	/**
	 * 通知客户端停播指定id的公告
	 * 
	 * @param bulletinId
	 */
	private void sendBulletinStopMessage(int bulletinId) {
		GCBulletinStop gcMsg = new GCBulletinStop();
		gcMsg.setId(bulletinId);
		GameServerAssist.getGameWorld().boradcast(gcMsg);
	}

}
