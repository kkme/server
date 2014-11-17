package com.hifun.soul.common.service;

import java.util.Collection;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hifun.soul.common.IInitializeRequired;
import com.hifun.soul.common.IReloadable;
import com.hifun.soul.common.constants.LangConstants;
import com.hifun.soul.common.model.template.DirtyWordsTemplate;
import com.hifun.soul.common.model.template.DirtyWordsTemplateVO;
import com.hifun.soul.common.model.template.NameDirtyWordsTemplate;
import com.hifun.soul.common.model.template.NameDirtyWordsTemplateVO;
import com.hifun.soul.core.i18n.SysLangService;
import com.hifun.soul.core.template.TemplateObject;
import com.hifun.soul.core.template.TemplateService;
import com.hifun.soul.core.util.IKeyWordsFilter;
import com.hifun.soul.core.util.KeyWordsACFilter;
import com.hifun.soul.core.util.MySqlUtil;
import com.hifun.soul.core.util.StringUtils;

/**
 * 关键字过滤器：  暂时未分离出loader,先测试
 * 
 * 采用AC算法过滤关键词 800个关键词，占用内存1M左右 过滤速度：4MB/s
 * 
 */
@Component
public class DirtFilterService implements IReloadable,IInitializeRequired {

	public static final char SUBSTITUTE_CHAR = '*';
	private static final char[] IGNORE_CHARS = { '　', ' ', '*', '-' };
	private static final char[] ILLEGAL_CHARS = {' ','　','<','>','_'};

	private volatile IKeyWordsFilter filter;
	private volatile IKeyWordsFilter namefilter;

	@Autowired
	private TemplateService templateService;
	/** 多语言管理 */
	@Autowired
	private SysLangService sysLangService;

	public DirtFilterService() {

	}
	
	@Override
	public void init() {
		this.filter = new KeyWordsACFilter(IGNORE_CHARS, SUBSTITUTE_CHAR);
		this.namefilter = new KeyWordsACFilter(IGNORE_CHARS, SUBSTITUTE_CHAR);
		filter.initialize(initDirtWords(DirtyWordsTemplate.class));
		namefilter.initialize(initNameDirtWords(NameDirtyWordsTemplate.class));
	}
	
	/**
	 * 初始化dirty words
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private <T extends TemplateObject> String[] initDirtWords(Class<T> clazz) {
		Collection<DirtyWordsTemplateVO> words = (Collection<DirtyWordsTemplateVO>)templateService.getAll(clazz)
				.values();
		int len = words.size();
		String[] dirty = new String[len];
		int i = 0;
		for (DirtyWordsTemplateVO tmpl : words) {
			dirty[i] = tmpl.getDirtyWords();
			i++;
		}
		return dirty;
	}
	
	/**
	 * 初始化dirty words
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private <T extends TemplateObject> String[] initNameDirtWords(Class<T> clazz) {
		Collection<NameDirtyWordsTemplateVO> words = (Collection<NameDirtyWordsTemplateVO>)templateService.getAll(clazz)
				.values();
		int len = words.size();
		String[] dirty = new String[len];
		int i = 0;
		for (NameDirtyWordsTemplateVO tmpl : words) {
			dirty[i] = tmpl.getDirtyWords();
			i++;
		}
		return dirty;
	}

	public String filt(String inputMsg) {
		return filter.filt(inputMsg);
	}

	public boolean contains(String msg) {
		return filter.contain(msg);
	}

	public String filtName(String name) {
		return namefilter.filt(name);
	}

	public boolean containsName(String msg) {
		return namefilter.contain(msg);
	}

	@Override
	public boolean afterReload(IResult result) {
		// TODO 重新加载
		return false;
	}

	@Override
	public IResult reload(IParameter parameter) {
		// TODO 重新加载
		return null;
	}

	/**
	 * 从文件中加载禁词的配置
	 * 
	 * @param file
	 * @return
	 */
	@SuppressWarnings("unused")
	private Map<String, String[]> loadFromXls(String file) {
		// TODO 重新加载
		return null;
	}

	/**
	 * 公用的输入文字检查工具，主要用于玩家姓名，宠物姓名，玩家签名等可能带中文字符的输入检查 包括以下检查：
	 * 
	 * 
	 * 1. 非法字符检查 2. 非法屏蔽字检查 3. 允许的中文字符长度检查 4. 允许的英文字符长度检查 5. 为空检查
	 * 
	 * @param input
	 *            需要检查的输入信息
	 * @param inputType
	 *            输入项信息 ： 角色名/宠物名/角色签名
	 * @param minChLen
	 *            全中文字符时最小长度
	 * @param maxChLen
	 *            全中文字符时最大长度
	 * @param minEngLen
	 *            全英文字符时最小长度
	 * @param maxEngLen
	 *            全英文字符时最大长度
	 * @param checkNull
	 *            是否进行为空，或者长度为0的判断
	 * @return 错误信息
	 */
	public String checkInput(WordCheckType type, String input, int inputType,
			int minLen, int maxLen, boolean checkNull) {

		// 获取多语言管理类
		// 参数
		Object[] _params = new Object[] { sysLangService.read(inputType) };
		Object[] _minparams = new Object[] { sysLangService.read(inputType),
				minLen };
		Object[] _maxparams = new Object[] { sysLangService.read(inputType),
				maxLen };

		// 检查是否为NULL
		if (input == null) {
			return sysLangService.read(LangConstants.GAME_INPUT_NULL, _params);
		}

		// 获取长度
		int _length = input.length();

		int len = 0;
		for (int i = 0; i < _length; i++) {
			char c = input.charAt(i);
			if (c > '\u4E00' && c < '\u9FA5') {
				len = len + 2;
			} else {
				len++;
			}
		}

		// 如果不能为空
		if (checkNull) {
			if (len < 1) {
				return sysLangService.read(LangConstants.GAME_INPUT_TOO_SHORT,
						_minparams);
			}
		}

		// 检查长度
		if (len < minLen) {
			// 如果输入的字符串小于最小长度
			return sysLangService.read(LangConstants.GAME_INPUT_TOO_SHORT,
					_minparams);
		}

		// 如果输入的字符串长度大于最大允许长度
		if (len > maxLen) {
			return sysLangService.read(LangConstants.GAME_INPUT_TOO_LONG,
					_maxparams);
		}

		if (type == WordCheckType.NAME) {
			// 检查字符是否有异常字符
			if (StringUtils.hasExcludeChar(input) || !MySqlUtil.isValidMySqlUTF8Fast(input)) {
				return sysLangService.read(LangConstants.GAME_INPUT_ERROR1,
						_params);
			}
			// 检查字符是否有非法关键字
			if (this.containsName(input)) {
				return sysLangService.read(LangConstants.GAME_INPUT_ERROR2,
						_params);
			}
			// 检查非法字符
			for(int i=0;i<ILLEGAL_CHARS.length;i++){
				if(input.indexOf(ILLEGAL_CHARS[i]) >= 0){
					return sysLangService.read(LangConstants.GAME_INPUT_ERROR3,
							_params);
				}
			}
		}
		else if(type == WordCheckType.CHAT_ANNOUNCE_DESC)
		{
			// 检查字符是否有异常字符
			if (StringUtils.hasExcludeChar(input) || !MySqlUtil.isValidMySqlUTF8Fast(input)) {
				return sysLangService.read(LangConstants.GAME_INPUT_ERROR1,
						_params);
			}
			// 检查字符是否有非法关键字
			if (this.contains(input)) {
				System.out.println("FUCK");
				return sysLangService.read(LangConstants.GAME_INPUT_ERROR2,
						_params);
			}
		}
		return null;
	}

	/**
	 * 文字检查类型
	 * 
	 * 
	 */
	public enum WordCheckType {
		/** 名字类 */
		NAME,
		/** 聊天信息，大段信息，公告类 */
		CHAT_ANNOUNCE_DESC
	}

}
