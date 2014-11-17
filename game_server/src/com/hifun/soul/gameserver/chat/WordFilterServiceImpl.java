package com.hifun.soul.gameserver.chat;

import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hifun.soul.common.service.DirtFilterService;

/**
 * 过滤聊天中的不良信息
 * 
 * 
 */
@Component
public class WordFilterServiceImpl implements IWordFilterService {

	private static final Pattern html_pattern = Pattern.compile("<[^>]+>",
			Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
	@Autowired
	private DirtFilterService dirtFilterService;

	@Override
	public boolean containKeywords(String chatContent) {
		return dirtFilterService.contains(chatContent);
	}

	@Override
	public String filter(String chatContent) {
		return dirtFilterService.filt(chatContent);
	}

	@Override
	public String filterHtmlTag(String chatContent) {
		return html_pattern.matcher(chatContent).replaceAll("");
	}

}
