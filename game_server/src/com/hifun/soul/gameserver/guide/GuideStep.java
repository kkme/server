package com.hifun.soul.gameserver.guide;

import com.hifun.soul.gameserver.guide.msg.GuideStepInfo;
import com.hifun.soul.gameserver.guide.template.GuideTemplate;

/**
 * 引导步骤
 * 
 * @author magicstone
 *
 */
public class GuideStep {
	/** 引导步骤信息 */
	private GuideStepInfo _gsi;
	/** 引导配置模版 */
	private GuideTemplate _guideTmpl;
	/** 蒙板颜色整数值 */
	private int _maskColorInt;
	/** 高亮区域 1 边框颜色 */
	private int _highlite1BorderColorInt;
	/** 高亮区域 2 边框颜色 */
	private int _highlite2BorderColorInt;
	/** 高亮区域 3 边框颜色 */
	private int _highlite3BorderColorInt;
	/** 高亮区域 4 边框颜色 */
	private int _highlite4BorderColorInt;
	/** 高亮区域 5 边框颜色 */
	private int _highlite5BorderColorInt;
	/** 可操作区域边框颜色 */
	private int _operationalBorderColorInt;

	/**
	 * 类参数构造器
	 * 
	 * @param guideTmpl
	 * @throws IllegalArgumentException if guideTmpl == null
	 * 
	 */
	public GuideStep(GuideTemplate guideTmpl) {
		if (guideTmpl == null) {
			throw new IllegalArgumentException("guideTmpl is null");
		}

		this._guideTmpl = guideTmpl;

		this._maskColorInt = Integer.parseInt(
			guideTmpl.getMaskColor(), 16);
		
		this._highlite1BorderColorInt = Integer.parseInt(
			guideTmpl.getHighlite1BorderColor(), 16);
		this._highlite2BorderColorInt = Integer.parseInt(
			guideTmpl.getHighlite2BorderColor(), 16);
		this._highlite3BorderColorInt = Integer.parseInt(
			guideTmpl.getHighlite3BorderColor(), 16);
		this._highlite4BorderColorInt = Integer.parseInt(
			guideTmpl.getHighlite2BorderColor(), 16);
		this._highlite5BorderColorInt = Integer.parseInt(
			guideTmpl.getHighlite3BorderColor(), 16);
		
		this._operationalBorderColorInt = Integer.parseInt(
			guideTmpl.getOperationalBorderColor(), 16);
	}

	/**
	 * 获取模版 Id
	 * 
	 * @return
	 */
	public int getTemplateId() {
		return this._guideTmpl.getId();
	}

	/**
	 * 获取新手引导类型 Id
	 * 
	 * @return
	 */
	public int getGuideTypeId() {
		return this._guideTmpl.getGuideTypeId();
	}

	/**
	 * 设置蒙板颜色
	 * 
	 * @return
	 */
	public String getMaskColor() {
		return this._guideTmpl.getMaskColor();
	}

	/**
	 * 获取蒙板颜色整数值
	 * 
	 * @return
	 */
	public int getMaskColorInt() {
		return this._maskColorInt;
	}

	/**
	 * 获取蒙板透明度
	 * 
	 * @return
	 */
	public int getMaskAlpha() {
		return this._guideTmpl.getMaskAlpha();
	}

	/**
	 * 获取引导文字
	 * 
	 * @return
	 */
	public String getGuideText() {
		return this._guideTmpl.getGuideText();
	}

	/**
	 * 获取引导文字多语言 Id
	 * 
	 * @return
	 */
	public int getGuideTextLangId() {
		return this._guideTmpl.getGuideTextLangId();
	}

	/**
	 * 获取引导文字 X 坐标位置
	 * 
	 * @return
	 */
	public int getTextPosX() {
		return this._guideTmpl.getTextPosX();
	}

	/**
	 * 获取引导文字 Y 坐标位置
	 * 
	 * @return
	 */
	public int getTextPosY() {
		return this._guideTmpl.getTextPosY();
	}

	/**
	 * 获取引导文字显示方式
	 * 
	 * @return
	 */
	public int getTextMode() {
		return this._guideTmpl.getTextMode();
	}

	/**
	 * 获取高亮区域 1 X 坐标位置
	 * 
	 * @return
	 */
	public int getHighlite1PosX() {
		return this._guideTmpl.getHighlite1PosX();
	}

	/**
	 * 获取高亮区域 1 Y 坐标位置
	 * 
	 * @return
	 */
	public int getHighlite1PosY() {
		return this._guideTmpl.getHighlite1PosY();
	}

	/**
	 * 获取高亮区域 1 宽度
	 * 
	 * @return
	 */
	public int getHighlite1Width() {
		return this._guideTmpl.getHighlite1Width();
	}

	/**
	 * 获取高亮区域 1 高度
	 * 
	 * @return
	 */
	public int getHighlite1Height() {
		return this._guideTmpl.getHighlite1Height();
	}

	/**
	 * 获取高亮区域 1 边框颜色
	 * 
	 * @return
	 */
	public int getHighlite1BorderColorInt() {
		return this._highlite1BorderColorInt;
	}

	/**
	 * 获取高亮区域 1 边框厚度
	 * 
	 * @return
	 */
	public int getHighlite1BorderThickness() {
		return this._guideTmpl.getHighlite1BorderThickness();
	}

	/**
	 * 获取高亮区域 2 X 坐标位置
	 * 
	 * @return
	 */
	public int getHighlite2PosX() {
		return this._guideTmpl.getHighlite2PosX();
	}

	/**
	 * 获取高亮区域 2 Y 坐标位置
	 * 
	 * @return
	 */
	public int getHighlite2PosY() {
		return this._guideTmpl.getHighlite2PosY();
	}

	/**
	 * 获取高亮区域 2 宽度
	 * 
	 * @return
	 */
	public int getHighlite2Width() {
		return this._guideTmpl.getHighlite2Width();
	}

	/**
	 * 获取高亮区域 2 高度
	 * 
	 * @return
	 */
	public int getHighlite2Height() {
		return this._guideTmpl.getHighlite2Height();
	}

	/**
	 * 获取高亮区域 2 边框颜色
	 * 
	 * @return
	 */
	public int getHighlite2BorderColorInt() {
		return this._highlite2BorderColorInt;
	}

	/**
	 * 获取高亮区域 2 边框厚度
	 * 
	 * @return
	 */
	public int getHighlite2BorderThickness() {
		return this._guideTmpl.getHighlite2BorderThickness();
	}

	/**
	 * 获取高亮区域 3 X 坐标位置
	 * 
	 * @return
	 */
	public int getHighlite3PosX() {
		return this._guideTmpl.getHighlite3PosX();
	}

	/**
	 * 获取高亮区域 3 Y 坐标位置
	 * 
	 * @return
	 */
	public int getHighlite3PosY() {
		return this._guideTmpl.getHighlite3PosY();
	}

	/**
	 * 获取高亮区域 3 宽度
	 * 
	 * @return
	 */
	public int getHighlite3Width() {
		return this._guideTmpl.getHighlite3Width();
	}

	/**
	 * 获取高亮区域 3 高度
	 * 
	 * @return
	 */
	public int getHighlite3Height() {
		return this._guideTmpl.getHighlite3Height();
	}

	/**
	 * 获取高亮区域 3 边框颜色
	 * 
	 * @return
	 */
	public int getHighlite3BorderColorInt() {
		return this._highlite3BorderColorInt;
	}

	/**
	 * 获取高亮区域 3 边框厚度
	 * 
	 * @return
	 */
	public int getHighlite3BorderThickness() {
		return this._guideTmpl.getHighlite3BorderThickness();
	}

	/**
	 * 获取高亮区域 4 X 坐标位置
	 * 
	 * @return
	 */
	public int getHighlite4PosX() {
		return this._guideTmpl.getHighlite4PosX();
	}

	/**
	 * 获取高亮区域 4 Y 坐标位置
	 * 
	 * @return
	 */
	public int getHighlite4PosY() {
		return this._guideTmpl.getHighlite4PosY();
	}

	/**
	 * 获取高亮区域 4 宽度
	 * 
	 * @return
	 */
	public int getHighlite4Width() {
		return this._guideTmpl.getHighlite4Width();
	}

	/**
	 * 获取高亮区域 4 高度
	 * 
	 * @return
	 */
	public int getHighlite4Height() {
		return this._guideTmpl.getHighlite4Height();
	}

	/**
	 * 获取高亮区域 4 边框颜色
	 * 
	 * @return
	 */
	public int getHighlite4BorderColorInt() {
		return this._highlite4BorderColorInt;
	}

	/**
	 * 获取高亮区域 4 边框厚度
	 * 
	 * @return
	 */
	public int getHighlite4BorderThickness() {
		return this._guideTmpl.getHighlite4BorderThickness();
	}

	/**
	 * 获取高亮区域 5 X 坐标位置
	 * 
	 * @return
	 */
	public int getHighlite5PosX() {
		return this._guideTmpl.getHighlite5PosX();
	}

	/**
	 * 获取高亮区域 5 Y 坐标位置
	 * 
	 * @return
	 */
	public int getHighlite5PosY() {
		return this._guideTmpl.getHighlite5PosY();
	}

	/**
	 * 获取高亮区域 5 宽度
	 * 
	 * @return
	 */
	public int getHighlite5Width() {
		return this._guideTmpl.getHighlite5Width();
	}

	/**
	 * 获取高亮区域 5 高度
	 * 
	 * @return
	 */
	public int getHighlite5Height() {
		return this._guideTmpl.getHighlite5Height();
	}

	/**
	 * 获取高亮区域 5 边框颜色
	 * 
	 * @return
	 */
	public int getHighlite5BorderColorInt() {
		return this._highlite5BorderColorInt;
	}

	/**
	 * 获取高亮区域 5 边框厚度
	 * 
	 * @return
	 */
	public int getHighlite5BorderThickness() {
		return this._guideTmpl.getHighlite5BorderThickness();
	}

	/**
	 * 箭头 1 X 位置
	 * 
	 * @return
	 */
	public int getArrow1PosX() {
		return this._guideTmpl.getArrow1PosX();
	}

	/**
	 * 箭头 1 Y 位置
	 * 
	 * @return
	 */
	public int getArrow1PosY() {
		return this._guideTmpl.getArrow1PosY();
	}

	/**
	 * 箭头 2 X 位置
	 * 
	 * @return
	 */
	public int getArrow2PosX() {
		return this._guideTmpl.getArrow2PosX();
	}

	/**
	 * 箭头 2 Y 位置
	 * 
	 * @return
	 */
	public int getArrow2PosY() {
		return this._guideTmpl.getArrow2PosY();
	}

	/**
	 * 箭头 3 X 位置
	 * 
	 * @return
	 */
	public int getArrow3PosX() {
		return this._guideTmpl.getArrow3PosX();
	}

	/**
	 * 箭头 3 Y 位置
	 * 
	 * @return
	 */
	public int getArrow3PosY() {
		return this._guideTmpl.getArrow3PosY();
	}

	/**
	 * 获取可操作区域 X 坐标位置
	 * 
	 * @return
	 */
	public int getOperationalPosX() {
		return this._guideTmpl.getOperationalPosX();
	}

	/**
	 * 获取可操作区域 Y 坐标位置
	 * 
	 * @return
	 */
	public int getOperationalPosY() {
		return this._guideTmpl.getOperationalPosY();
	}

	/**
	 * 获取可操作区域宽度
	 * 
	 * @return
	 */
	public int getOperationalWidth() {
		return this._guideTmpl.getOperationalWidth();
	}

	/**
	 * 获取可操作区域高度
	 * 
	 * @return
	 */
	public int getOperationalHeight() {
		return this._guideTmpl.getOperationalHeight();
	}

	/**
	 * 获取可操作区域边框颜色整数值
	 * 
	 * @return
	 */
	public long getOperationalBorderColorInt() {
		return this._operationalBorderColorInt;
	}

	/**
	 * 获取可操作区域边框厚度 
	 * 
	 * @return
	 */
	public int getOperationalBorderThickness() {
		return this._guideTmpl.getOperationalBorderThickness();
	}

	/**
	 * 获取操作参数
	 * 
	 * @return
	 */
	public String getEvent() {
		return this._guideTmpl.getEvent();
	}

	/**
	 * 获取控件名称
	 * 
	 * @return
	 */
	public String getControlName() {
		return this._guideTmpl.getControlName();
	}

	/**
	 * 获取动画资源名称
	 * 
	 * @return
	 */
	public String getMovie() {
		return this._guideTmpl.getMovie();
	}

	/**
	 * 获取在指定时间后自动提交
	 * 
	 * @return
	 */
	public int getAutoCommit() {
		return this._guideTmpl.getAutoCommit();
	}
	
	/**
	 * 引导场景
	 * 
	 * @return
	 */
	public int getSceneType() {
		return this._guideTmpl.getSceneType();
	}

	public GuideStepInfo getGuideStepInfo() {
		if (this._gsi == null) {
			this._gsi = new GuideStepInfo();
			this._gsi.setGuideTypeId((short)this.getGuideTypeId());
			this._gsi.setMaskColorInt(this.getMaskColorInt());
			this._gsi.setMaskAlpha((short)this.getMaskAlpha());
			this._gsi.setGuideText(this.getGuideText());
			this._gsi.setTextPosX((short)this.getTextPosX());
			this._gsi.setTextPosY((short)this.getTextPosY());
			this._gsi.setTextMode((short)this.getTextMode());
			this._gsi.setHighlite1PosX((short)this.getHighlite1PosX());
			this._gsi.setHighlite1PosY((short)this.getHighlite1PosY());
			this._gsi.setHighlite1Width((short)this.getHighlite1Width());
			this._gsi.setHighlite1Height((short)this.getHighlite1Height());
			this._gsi.setHighlite1BorderColor(this.getHighlite1BorderColorInt());
			this._gsi.setHighlite1BorderThickness((short)this.getHighlite1BorderThickness());
			this._gsi.setHighlite2PosX((short)this.getHighlite2PosX());
			this._gsi.setHighlite2PosY((short)this.getHighlite2PosY());
			this._gsi.setHighlite2Width((short)this.getHighlite2Width());
			this._gsi.setHighlite2Height((short)this.getHighlite2Height());
			this._gsi.setHighlite2BorderColor(this.getHighlite2BorderColorInt());
			this._gsi.setHighlite2BorderThickness((short)this.getHighlite2BorderThickness());
			this._gsi.setHighlite3PosX((short)this.getHighlite3PosX());
			this._gsi.setHighlite3PosY((short)this.getHighlite3PosY());
			this._gsi.setHighlite3Width((short)this.getHighlite3Width());
			this._gsi.setHighlite3Height((short)this.getHighlite3Height());
			this._gsi.setHighlite3BorderColor(this.getHighlite3BorderColorInt());
			this._gsi.setHighlite3BorderThickness((short)this.getHighlite3BorderThickness());
			this._gsi.setHighlite4PosX((short)this.getHighlite4PosX());
			this._gsi.setHighlite4PosY((short)this.getHighlite4PosY());
			this._gsi.setHighlite4Width((short)this.getHighlite4Width());
			this._gsi.setHighlite4Height((short)this.getHighlite4Height());
			this._gsi.setHighlite4BorderColor(this.getHighlite4BorderColorInt());
			this._gsi.setHighlite4BorderThickness((short)this.getHighlite4BorderThickness());
			this._gsi.setHighlite5PosX((short)this.getHighlite5PosX());
			this._gsi.setHighlite5PosY((short)this.getHighlite5PosY());
			this._gsi.setHighlite5Width((short)this.getHighlite5Width());
			this._gsi.setHighlite5Height((short)this.getHighlite5Height());
			this._gsi.setHighlite5BorderColor(this.getHighlite5BorderColorInt());
			this._gsi.setHighlite5BorderThickness((short)this.getHighlite5BorderThickness());
			this._gsi.setArrow1PosX((short)this.getArrow1PosX());
			this._gsi.setArrow1PosY((short)this.getArrow1PosY());
			this._gsi.setArrow2PosX((short)this.getArrow2PosX());
			this._gsi.setArrow2PosY((short)this.getArrow2PosY());
			this._gsi.setArrow3PosX((short)this.getArrow3PosX());
			this._gsi.setArrow3PosY((short)this.getArrow3PosY());
			this._gsi.setOperationalPosX((short)this.getOperationalPosX());
			this._gsi.setOperationalPosY((short)this.getOperationalPosY());
			this._gsi.setOperationalWidth((short)this.getOperationalWidth());
			this._gsi.setOperationalHeight((short)this.getOperationalHeight());
			this._gsi.setOperationalBorderColor((int)this.getOperationalBorderColorInt());
			this._gsi.setOperationalBorderThickness((short)this.getOperationalBorderThickness());
			this._gsi.setEvent(this.getEvent());
			this._gsi.setControlName(this.getControlName());
			this._gsi.setMovie(this.getMovie());
			this._gsi.setAutoCommit((int)this.getAutoCommit());
			this._gsi.setSceneType(getSceneType());
			// 引导图标
			this._gsi.setGuideIcon(this._guideTmpl.getGuideIcon());
			this._gsi.setGuideIconX(this._guideTmpl.getGuideIconX());
			this._gsi.setGuideIconY(this._guideTmpl.getGuideIconY());
			this._gsi.setNeedSetGemPos(this._guideTmpl.isNeedSetGemPos());
			this._gsi.setNeedSetSkill(this._guideTmpl.isNeedSetSkill());
		}

		return this._gsi;
	}
}
