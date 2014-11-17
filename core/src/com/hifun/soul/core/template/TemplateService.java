package com.hifun.soul.core.template;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jdom.Element;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import com.hifun.soul.common.ITemplateLevelRange;
import com.hifun.soul.common.constants.Loggers;
import com.hifun.soul.common.exception.ConfigException;
import com.hifun.soul.core.encrypt.XorDecryptedInputStream;
import com.hifun.soul.core.util.JdomUtils;
import com.hifun.soul.core.util.StringUtils;

/**
 * 模板数据管理器，在服务器启动时加载所有excel模板数据
 * 
 * 
 */
@Scope("singleton")
@Component
public class TemplateService implements ITemplateService {

	/** 所有通过模板文件转换而成的模板对象的实例 */
	private Map<Class<?>, Map<Integer, TemplateObject>> templateObjects;

	private List<TemplateConfig> templateConfigs;
	private TemplateFileParser objectsParser;
	private String resourceFolder;
	private boolean isDebug;
	
	public TemplateService() {
	}

	public TemplateService(String resourceFolder) {
		this(resourceFolder, true);
	}

	public TemplateService(String resourceFolder, boolean isDebug) {
		this.resourceFolder = resourceFolder;
		this.isDebug = isDebug;
	}

	@Override
	public void init(URL cfgPath) {
		this.loadConfig(cfgPath);
		templateObjects = new HashMap<Class<?>, Map<Integer, TemplateObject>>();
		objectsParser = new TemplateFileParser();
		InputStream is = null;
		String fileName = null;
		for (TemplateConfig cfg : templateConfigs) {
			Loggers.TEMPLATE_LOGGER.info(cfg.toString());
			try {
				fileName = cfg.getFileName();
				if (fileName == null) {// 没有配置文件名可能是parser内部自己处理
					this.getTemplateParser(cfg).parseXlsFile(cfg.getClasses(),
							templateObjects, null);
					continue;
				}
				Loggers.TEMPLATE_LOGGER.info("loading template " + fileName);
				String xlsPath = "";
				if(resourceFolder==null || resourceFolder.length()==0 || "/".equals(resourceFolder)){
					ClassPathResource resource = new ClassPathResource(fileName); 
					xlsPath= resource.getURL().getPath().replace("20%", ""); 
				}
				else{
					xlsPath = resourceFolder + File.separator
						+ cfg.getFileName();
				}
				if (isDebug) {
					is = new FileInputStream(xlsPath);
				} else {
					is = new XorDecryptedInputStream(xlsPath);
				}
				this.getTemplateParser(cfg).parseXlsFile(cfg.getClasses(),
						templateObjects, is);
				is.close();
			} catch (Exception e) {
				throw new ConfigException(
						"Errors occurs while parsing xls file:" + fileName, e);
			} finally {
				if (is != null) {
					try {
						is.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		patchUpAndCheck();
	}

	/**
	 * 加载配置文件
	 * 
	 * @param cfgPath
	 */
	@SuppressWarnings("unchecked")
	private void loadConfig(URL cfgPath) {
		Element root = JdomUtils.getRootElemet(cfgPath);
		templateConfigs = new ArrayList<TemplateConfig>();
		// templateFiles = new TreeMap<String, Class<?>[]>();
		List<Element> fileElements = root.getChildren();
		for (Element fileElement : fileElements) {
			String fileName = fileElement.getAttributeValue("name");
			String parserClassName = fileElement.getAttributeValue("parser");
			List<Element> sheetElements = fileElement.getChildren();
			Class<?>[] fileSheetClasses = new Class<?>[sheetElements.size()];
			for (int i = 0; i < sheetElements.size(); i++) {
				Element sheet = sheetElements.get(i);
				String className = sheet.getAttributeValue("class");
				if (StringUtils.isEmpty(className)) {
					fileSheetClasses[i] = null;
					continue;
				}
				try {
					Class<?> clazz = Class.forName(className);
					fileSheetClasses[i] = clazz;
				} catch (ClassNotFoundException e) {
					Loggers.TEMPLATE_LOGGER.error("", e);
					throw new ConfigException(e);
				}
			}
			TemplateConfig templateConfig = new TemplateConfig(fileName,
					fileSheetClasses);
			if (parserClassName != null
					&& (parserClassName = parserClassName.trim()).length() > 0) {
				templateConfig.setParserClassName(parserClassName);
			}
			templateConfigs.add(templateConfig);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends TemplateObject> T get(int id, Class<T> clazz) {
		if (Loggers.TEMPLATE_LOGGER.isDebugEnabled()) {
			Loggers.TEMPLATE_LOGGER.debug("clazz:" + clazz);
		}
		Map<Integer, TemplateObject> map = templateObjects.get(clazz);
		return (T) map.get(id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends TemplateObject> Map<Integer, T> getAll(Class<T> clazz) {
		return (Map<Integer, T>) templateObjects.get(clazz);
	}

	/**
	 * 返回所有类别的template列表
	 * 
	 * @return
	 */
	public Map<Class<?>, Map<Integer, TemplateObject>> getAllClassTemplateMaps() {
		return Collections.unmodifiableMap(templateObjects);
	}

	@Override
	public <T extends TemplateObject> void add(T t) {
		Map<Integer, TemplateObject> objs = templateObjects.get(t.getClass());
		if (objs == null || objs.containsKey(t.getId())) {
			return;
		}
		objs.put(t.getId(), t);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends TemplateObject> Map<Integer, T> removeAll(Class<T> clazz) {
		return (Map<Integer, T>) templateObjects.remove(clazz);
	}

	@Override
	public <T extends TemplateObject> boolean isTemplateExist(int id,
			Class<T> clazz) {
		Map<Integer, TemplateObject> map = templateObjects.get(clazz);
		if (null != map) {
			return null == map.get(id) ? false : true;
		}
		return false;
	}

	/**
	 * 
	 * @param fileName
	 * @param index
	 * @return
	 */
	public boolean reload(String fileName, int index) {
		if (templateConfigs != null) {
			for (TemplateConfig cfg : templateConfigs) {
				if (fileName.equals(cfg.getFileName())) {
					Loggers.TEMPLATE_LOGGER.info(cfg.toString());
					String xlsPath = resourceFolder + File.separator + fileName;
					TemplateFileParser tmplFileParser = this.getTemplateParser(cfg);
					if(cfg.getClasses().length == 0)
					{
						try {
							InputStream is = null;
							if (isDebug) {
								is = new FileInputStream(xlsPath);
							} else {
								is = new XorDecryptedInputStream(xlsPath);
							}
							tmplFileParser.parseXlsFile(null,templateObjects, is);
						}catch (Exception e) {
							throw new ConfigException(
									"Errors occurs while parsing xls file:" + fileName, e);
						} 
					}
					else
					{
						tmplFileParser.parseXlsFile(xlsPath, index,
								cfg.getClasses()[index], templateObjects);
					}
					

					patchUpAndCheck();
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 进行合法性校验，并构建模板间对象依赖关系
	 */
	private void patchUpAndCheck() {
		boolean hasError = false;
		Collection<Map<Integer, TemplateObject>> tplObjMaps = templateObjects
				.values();
		for (Map<Integer, TemplateObject> tplObjMap : tplObjMaps) {
			Collection<TemplateObject> templates = tplObjMap.values();
			for (TemplateObject templateObject : templates) {
				try {
					templateObject.patchUp();
				} catch (Exception e) {
					Loggers.GAME_LOGGER.error("[excel数据整合]", e);
					hasError = true;
				}
			}
		}
		for (Map<Integer, TemplateObject> tplObjMap : tplObjMaps) {
			Collection<TemplateObject> templates = tplObjMap.values();
			for (TemplateObject templateObject : templates) {
				try {
					templateObject.check();
				} catch (Exception e) {
					Loggers.GAME_LOGGER.error("[excel启动检查]", e);
					hasError = true;
				}
			}
		}
		// sheet范围的全局检查
		sheetCheck();
		if (hasError) {
			System.exit(1);
		}
	}
	
	private void sheetCheck() {
		LevelRangeValidater validater = new LevelRangeValidater();
		for (Map.Entry<Class<?>, Map<Integer, TemplateObject>> entry : templateObjects
				.entrySet()) {
			Class<?> entryClass = entry.getKey();
			if (!isTemplateLevelRange(entryClass)) {
				continue;
			}
			List<ITemplateLevelRange> levelRangeList = new ArrayList<ITemplateLevelRange>();
			for (TemplateObject object : entry.getValue().values()) {
				levelRangeList.add((ITemplateLevelRange) object);
			}
			validater.checkLevelRanges(levelRangeList);
		}
	}
	
	private boolean isTemplateLevelRange(Class<?> clazz) {
		for (Class<?> interf : clazz.getInterfaces()) {
			if (interf == ITemplateLevelRange.class) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 根据配置取得解析器
	 * 
	 * @param cfg
	 * @return
	 */
	private TemplateFileParser getTemplateParser(TemplateConfig cfg) {
		if (cfg.getParserClassName() != null
				&& cfg.getParserClassName().length() > 0) {
			// 使用指定的解析器
			try {
				Class<?> clazz = Class.forName(cfg.getParserClassName());
				Constructor<?> constructor = clazz.getConstructor();
				return (TemplateFileParser) constructor.newInstance();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		} else {
			// 默认的解析器
			return objectsParser;
		}
	}

	/**
	 * 获取模版配置
	 * 
	 * @return
	 */
	public List<TemplateConfig> getTemplateCfgs() {
		return this.templateConfigs;
	}

	public String getResourceFolder() {
		return resourceFolder;
	}

	public void setResourceFolder(String resourceFolder) {
		this.resourceFolder = resourceFolder;
	}

	public boolean isDebug() {
		return isDebug;
	}

	public void setDebug(boolean isDebug) {
		this.isDebug = isDebug;
	}
	
}
