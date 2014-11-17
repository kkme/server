package com.hifun.soul.core.i18n.impl;

import java.text.MessageFormat;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.hifun.soul.core.i18n.I18NDictionary;
import com.hifun.soul.core.i18n.SysLangService;

/**
 * 多语言管理器
 * 
 * 
 */
@Scope("singleton")
@Component
public class SysLangServiceImpl implements SysLangService {
	/** 多语言容器 */
	private I18NDictionary<Integer, String> sysLangs;
	
	private String sysLangConfigFile = null;

	public SysLangServiceImpl() {
	}

	@Override
	public String read(Integer key) {
		return sysLangs.read(key);
	}

	@Override
	public String read(Integer key, Object... params) {
		String _msg = sysLangs.read(key);
		if (_msg == null) {
			return "lang_" + key;
		}
		if (params != null) {
			return MessageFormat.format(_msg, params);
		} else {
			return _msg;
		}
	}

	@Override
	public void setSysLangConfigFile(String sysLangConfigFile) {
		this.sysLangConfigFile = sysLangConfigFile;
	}
	
	@Override
	public void init() {
		sysLangs = new ExcelIntDictionaryImpl(sysLangConfigFile);
	}
}
