package com.hifun.soul.gameserver.predict.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hifun.soul.common.IInitializeRequired;
import com.hifun.soul.common.constants.SharedConstants;
import com.hifun.soul.core.template.TemplateService;
import com.hifun.soul.gameserver.predict.template.PredictPageTemplate;
import com.hifun.soul.gameserver.predict.template.PredictTemplate;

/**
 * 预见模板管理器
 * 
 * @author yandajun
 * 
 */
@Component
public class PredictTemplateManager implements IInitializeRequired {
	@Autowired
	private TemplateService templateService;
	private Map<Integer, PredictTemplate> predictTemplates = new HashMap<Integer, PredictTemplate>();
	private Map<Integer, PredictPageTemplate> pageTemplates = new HashMap<Integer, PredictPageTemplate>();

	@Override
	public void init() {
		predictTemplates = templateService.getAll(PredictTemplate.class);
		pageTemplates = templateService.getAll(PredictPageTemplate.class);
	}

	/**
	 * 获取指定页码的预见模板
	 */
	public List<PredictTemplate> getPagePredictTemplateList(int pageIndex) {
		List<PredictTemplate> templateList = new ArrayList<PredictTemplate>();
		for (Integer predictId : predictTemplates.keySet()) {
			if (predictTemplates.get(predictId).getPageIndex() == pageIndex) {
				templateList.add(predictTemplates.get(predictId));
			}
		}
		return templateList;
	}

	/**
	 * 根据预见ID获取预见模板
	 */
	public PredictTemplate getPredictTemplate(int predictId) {
		return predictTemplates.get(predictId);
	}

	/**
	 * 根据页码获取页码模板
	 */
	public PredictPageTemplate getPageTemplate(int pageIndex) {
		return pageTemplates.get(pageIndex);
	}

	/**
	 * 根据预见ID获取当前页面模板
	 */
	public PredictPageTemplate getCurrentPageTemplate(int currentPredictId) {
		for (Integer predictId : predictTemplates.keySet()) {
			if (predictTemplates.get(predictId).getId() == currentPredictId) {
				return pageTemplates.get(predictTemplates.get(predictId)
						.getPageIndex());
			}
		}
		return pageTemplates.get(SharedConstants.CONFIG_TEMPLATE_DEFAULT_ID);
	}

	/**
	 * 获取页码列表
	 */
	public List<Integer> getPageIndexs() {
		List<Integer> pageIndexs = new ArrayList<Integer>();
		for (Integer index : pageTemplates.keySet()) {
			pageIndexs.add(index);
		}
		return pageIndexs;
	}

	/**
	 * 获得最后一页页码
	 */
	public int getLastPageIndex() {
		return pageTemplates.size();
	}

	/**
	 * 可以激活的预见数量
	 */
	public int canActivatePredictNum(int level) {
		int num = 0;
		for (Integer predictId : predictTemplates.keySet()) {
			if (level >= predictTemplates.get(predictId).getNeedLevel()) {
				num++;
			}
		}
		return num;
	}
}
