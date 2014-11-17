package com.hifun.soul.gameserver.monster;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.hifun.soul.common.constants.SharedConstants;
import com.hifun.soul.core.template.TemplateService;
import com.hifun.soul.gameserver.monster.template.MonsterTemplate;

/**
 * 怪物工厂;
 * 
 * @author crazyjohn
 * 
 */
@Scope("singleton")
@Component
public class MonsterFactory implements IMonsterFactory {
	private Logger logger = LoggerFactory.getLogger(MonsterFactory.class);
	@Autowired
	private TemplateService templateService;
	private Map<Integer,MonsterTemplate> monsterTemplates;
	private Map<Integer,List<MonsterTemplate>> sameLevelNpcTemplates = new HashMap<Integer, List<MonsterTemplate>>();
	private int maxNpcLevel = 0;
	public void init(){
		monsterTemplates = templateService.getAll(MonsterTemplate.class);
		
		for(MonsterTemplate template : monsterTemplates.values()){
			if(template.getType()!=MonsterType.BRAVE_NPC.getIndex()){
				continue;
			}
			if(template.getLevel()>maxNpcLevel){
				maxNpcLevel = template.getLevel();
			}
			if(sameLevelNpcTemplates.containsKey(template.getLevel())){
				sameLevelNpcTemplates.get(template.getLevel()).add(template);
			}else{
				List<MonsterTemplate> npcList = new ArrayList<MonsterTemplate>();
				npcList.add(template);
				sameLevelNpcTemplates.put(template.getLevel(), npcList);
			}
		}
	}

	/**
	 * 根据怪物ID创建怪物;
	 * 
	 * @param monsterId
	 * @return
	 */
	public Monster createMonster(int monsterId) {
		MonsterTemplate template = monsterTemplates.get(monsterId);
		if (template == null) {
			logger.warn("Invalid monsterId: " + monsterId);
			return null;
		}
		Monster monster = new Monster(template);
		return monster;
	}
	
	/**
	 * 获取怪物模板
	 * @param monsterId
	 * @return
	 */
	public MonsterTemplate getMonsterTemplate(int monsterId){
		return monsterTemplates.get(monsterId);
	}
	
	/**
	 * 获取勇士之路对应的npc
	 * @param humanLevel
	 * @return
	 */
	public List<Monster> getWarriorNpc(int humanLevel){
		List<MonsterTemplate> sameLevelNpcs = null;
		if(!sameLevelNpcTemplates.containsKey(humanLevel)){
			sameLevelNpcs = sameLevelNpcTemplates.get(maxNpcLevel);
		}else{
			sameLevelNpcs = sameLevelNpcTemplates.get(humanLevel);
		}		
		List<Monster> npcList = new ArrayList<Monster>();
		for(MonsterTemplate template : sameLevelNpcs){
			Monster npc = new Monster(template);
			npcList.add(npc);
		}
		return npcList;
	}
	
	/**
	 * 获取战神之巅对应的npc
	 */
	public List<Monster> getMarsNpcs(int startLevel, int endLevel) {
		List<MonsterTemplate> suitableLevelNpcs = new ArrayList<MonsterTemplate>();
		if (endLevel < SharedConstants.INIT_HUMAN_BUILDING_LEVEL) {
			endLevel = SharedConstants.INIT_HUMAN_BUILDING_LEVEL;
		}
		if (startLevel > SharedConstants.MAX_HUMAN_LEVEL) {
			startLevel = SharedConstants.MAX_HUMAN_LEVEL;
		}
		for (Integer level : sameLevelNpcTemplates.keySet()) {
			if (level >= startLevel && level <= endLevel) {
				suitableLevelNpcs.addAll(sameLevelNpcTemplates.get(level));
			}
		}
		List<Monster> npcList = new ArrayList<Monster>();
		for (MonsterTemplate template : suitableLevelNpcs) {
			Monster npc = new Monster(template);
			npcList.add(npc);
		}
		return npcList;
	}
}
