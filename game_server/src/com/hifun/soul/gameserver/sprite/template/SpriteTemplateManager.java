package com.hifun.soul.gameserver.sprite.template;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.hifun.soul.common.IInitializeRequired;
import com.hifun.soul.core.template.TemplateService;
import com.hifun.soul.gameserver.sprite.model.SpriteBuffInfo;
import com.hifun.soul.gameserver.sprite.model.SpritePubInfo;
import com.hifun.soul.gameserver.sprite.model.SpritePubPageInfo;
import com.hifun.soul.gameserver.sprite.model.SpriteSignInfo;
import com.hifun.soul.gameserver.sprite.model.SpriteStarMapInfo;

/**
 * 精灵模版管理器;
 * 
 * @author crazyjohn
 * 
 */
@Scope("singleton")
@Component
public class SpriteTemplateManager implements IInitializeRequired {
	/** 精灵id和精灵简单信息的映射 */
	private Map<Integer, SpritePubInfo> spritePubInfos = new HashMap<Integer, SpritePubInfo>();
	/** pageid和精灵模版列表信息的映射 */
	private Map<Integer, List<SpriteTemplate>> pageIdTospriteTemplates = new HashMap<Integer, List<SpriteTemplate>>();
	/** 品质和精灵简单列表信息的映射 */
	private Map<Integer, List<SpritePubInfo>> qualityToSpritePubInfos = new HashMap<Integer, List<SpritePubInfo>>();
	/** pageid和精灵简单信息列表的映射 */
	private Map<Integer, List<SpritePubInfo>> pageIdToSpritePubInfos = new HashMap<Integer, List<SpritePubInfo>>();
	/** pageId和page信息的映射 */
	private List<SpritePubPageInfo> pageInfos = new ArrayList<SpritePubPageInfo>();
	/** spriteId-to level */
	private Map<Integer, Map<Integer, SpriteLevelupTemplate>> spriteIdToLevelUpTemplate = new HashMap<Integer, Map<Integer, SpriteLevelupTemplate>>();
	@Autowired
	private TemplateService templateService;

	@Override
	public void init() {

		// 精灵升级信息
		for (SpriteLevelupTemplate eachLevel : this.templateService.getAll(
				SpriteLevelupTemplate.class).values()) {
			Map<Integer, SpriteLevelupTemplate> levelUpTemplateMap = spriteIdToLevelUpTemplate
					.get(eachLevel.getSpriteId());
			if (levelUpTemplateMap == null) {
				levelUpTemplateMap = new HashMap<Integer, SpriteLevelupTemplate>();
				spriteIdToLevelUpTemplate.put(eachLevel.getSpriteId(),
						levelUpTemplateMap);
			}
			levelUpTemplateMap.put(eachLevel.getLevel(), eachLevel);
		}
		// 酒馆精灵信息
		Map<Integer, SpriteTemplate> templates = templateService
				.getAll(SpriteTemplate.class);
		for (SpriteTemplate eachSpriteTemplate : templates.values()) {
			SpritePubInfo eachInfo = eachSpriteTemplate.toSimpleInfo();
			spritePubInfos.put(eachSpriteTemplate.getId(), eachInfo);
			// 簡單信息
			List<SpritePubInfo> spriteList = pageIdToSpritePubInfos
					.get(eachSpriteTemplate.getPubPageId());
			// 模版信息
			List<SpriteTemplate> spriteTemplateList = pageIdTospriteTemplates
					.get(eachSpriteTemplate.getPubPageId());
			// 品质先关
			List<SpritePubInfo> qualityList = qualityToSpritePubInfos
					.get(eachSpriteTemplate.getQuality());
			// 简单信息
			if (spriteList == null) {
				spriteList = new ArrayList<SpritePubInfo>();
				pageIdToSpritePubInfos.put(eachSpriteTemplate.getPubPageId(),
						spriteList);
			}
			// 模版信息
			if (spriteTemplateList == null) {
				spriteTemplateList = new ArrayList<SpriteTemplate>();
				pageIdTospriteTemplates.put(eachSpriteTemplate.getPubPageId(),
						spriteTemplateList);
			}
			// 品质
			if (qualityList == null) {
				qualityList = new ArrayList<SpritePubInfo>();
				qualityToSpritePubInfos.put(eachSpriteTemplate.getQuality(),
						qualityList);
			}
			spriteList.add(eachInfo);
			spriteTemplateList.add(eachSpriteTemplate);
			qualityList.add(eachInfo);
		}
		// 页签信息
		Map<Integer, SpriteCostTemplate> pageTemplates = templateService
				.getAll(SpriteCostTemplate.class);
		for (SpriteCostTemplate eachPage : pageTemplates.values()) {
			SpritePubPageInfo eachPageInfo = eachPage.toInfo();
			pageInfos.add(eachPageInfo);
		}

	}

	/**
	 * 根据精灵ID获取精灵简单信息
	 * 
	 * @param spriteId
	 * @return
	 */
	public SpritePubInfo getSpritePubInfoById(int spriteId) {
		return spritePubInfos.get(spriteId);

	}

	/**
	 * 根据页码获取精灵简单信息;
	 * 
	 * @param pageId
	 *            一
	 * @return
	 */
	public List<SpritePubInfo> getSpritePubInfoByPageId(int pageId) {
		return pageIdToSpritePubInfos.get(pageId);
	}

	/**
	 * 获取所有的酒馆页码信息;
	 * 
	 * @return
	 */
	public List<SpritePubPageInfo> getAllPubPages() {
		return pageInfos;
	}

	/**
	 * 最大的页码数;
	 * 
	 * @return
	 */
	public int getMaxPage() {
		return pageInfos.get(pageInfos.size() - 1).getPageId();
	}

	public List<SpriteTemplate> getSpriteTemplateByPageId(int id) {
		return this.pageIdTospriteTemplates.get(id);
	}

	/**
	 * 根据精灵品质获取精力列表;
	 * 
	 * @param quality
	 * @return
	 */
	public List<SpritePubInfo> getSpritePubInfoByQuality(int quality) {
		return qualityToSpritePubInfos.get(quality);
	}

	/**
	 * 获取所有招募配置信息;
	 * 
	 * @return
	 */
	public SpriteRecruitTemplate[] getRecruitConfigList() {
		return this.templateService.getAll(SpriteRecruitTemplate.class)
				.values().toArray(new SpriteRecruitTemplate[0]);
	}

	public SpritePubInfo getSpritePubInfoByIndex(int qualityType,
			int selectIndex) {
		return getSpritePubInfoByQuality(qualityType).get(selectIndex);
	}

	/**
	 * 获取精灵的buff列表;
	 * 
	 * @return
	 */
	public List<SpriteBuffInfo> getSpriteBuffList() {
		List<SpriteBuffInfo> result = new ArrayList<SpriteBuffInfo>();
		for (SpriteBuffTemplate eachTemplate : this.templateService.getAll(
				SpriteBuffTemplate.class).values()) {
			SpriteBuffInfo info = eachTemplate.toInfo();
			result.add(info);
		}
		return result;
	}

	/**
	 * 获取星图信息列表;
	 * 
	 * @return
	 */
	public List<SpriteStarMapInfo> getSpriteStarMapList() {
		List<SpriteStarMapInfo> result = new ArrayList<SpriteStarMapInfo>();
		for (SpriteStarMapTemplate eachTemplate : this.templateService.getAll(
				SpriteStarMapTemplate.class).values()) {
			SpriteStarMapInfo info = eachTemplate.toInfo();
			result.add(info);
		}
		// 排序
		Collections.sort(result, new Comparator<SpriteStarMapInfo>() {

			@Override
			public int compare(SpriteStarMapInfo o1, SpriteStarMapInfo o2) {
				if (o1.getStarMapId() < o2.getStarMapId()) {
					return -1;
				} else if (o1.getStarMapId() > o2.getStarMapId()) {
					return 1;
				}
				return 0;
			}

		});
		return result;
	}

	/**
	 * 根据精灵id和等级获取精灵升级模版;
	 * 
	 * @param spriteId
	 * @param level
	 * @return
	 */
	public SpriteLevelupTemplate getSpriteLevelUpTemplate(int spriteId,
			int level) {
		return spriteIdToLevelUpTemplate.get(spriteId).get(level);
	}

	/**
	 * 获取所有的星座信息;
	 * 
	 * @return
	 */
	public Map<Integer, List<SpriteSignInfo>> getStarMapSigns() {
		Map<Integer, List<SpriteSignInfo>> signMap = new HashMap<Integer, List<SpriteSignInfo>>();
		for (SpriteSignTemplate eachTemplate : this.templateService.getAll(
				SpriteSignTemplate.class).values()) {
			List<SpriteSignInfo> signList = signMap.get(eachTemplate
					.getStarMapId());
			if (signList == null) {
				signList = new ArrayList<SpriteSignInfo>();
				signMap.put(eachTemplate.getStarMapId(), signList);
			}
			signList.add(eachTemplate.toInfo());
		}
		for (List<SpriteSignInfo> eachList : signMap.values()) {
			// 排序
			Collections.sort(eachList, new Comparator<SpriteSignInfo>() {

				@Override
				public int compare(SpriteSignInfo o1, SpriteSignInfo o2) {
					if (o1.getSignId() < o2.getSignId()) {
						return -1;
					} else if (o1.getSignId() > o2.getSignId()) {
						return 1;
					}
					return 0;
				}

			});
		}
		return signMap;
	}

	/**
	 * 根据页码获取对酒页信息;
	 * @param pageId
	 * @return
	 */
	public SpritePubPageInfo getPageInfoByPageId(int pageId) {
		for (SpritePubPageInfo each: pageInfos)  {
			if (each.getPageId() == pageId) {
				return each;
			}
		}
		return null;
	}

}
