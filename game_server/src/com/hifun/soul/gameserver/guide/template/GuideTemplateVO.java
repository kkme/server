package com.hifun.soul.gameserver.guide.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelCellBinding;
import com.hifun.soul.core.template.TemplateObject;
import com.hifun.soul.core.annotation.NotTranslate;
import com.hifun.soul.core.util.StringUtils;

/**
 * 新手引导模版
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public abstract class GuideTemplateVO extends TemplateObject {

	/**  引导类型 */
	@ExcelCellBinding(offset = 1)
	protected int guideTypeId;

	/**  蒙板颜色 */
	@ExcelCellBinding(offset = 2)
	protected String maskColor;

	/**  蒙板透明度 */
	@ExcelCellBinding(offset = 3)
	protected int maskAlpha;

	/**  引导文字多语言 ID */
	@ExcelCellBinding(offset = 4)
	protected int guideTextLangId;

	/**  引导文字 */
	@NotTranslate
	@ExcelCellBinding(offset = 5)
	protected String guideText;

	/**  引导文字 X 坐标位置 */
	@ExcelCellBinding(offset = 6)
	protected int textPosX;

	/**  引导文字 Y 坐标位置 */
	@ExcelCellBinding(offset = 7)
	protected int textPosY;

	/**  文字方式, 0 = 全显方式, 1 = 逐字显示 */
	@ExcelCellBinding(offset = 8)
	protected int textMode;

	/**  高亮区域 X 坐标位置 */
	@ExcelCellBinding(offset = 9)
	protected int highlite1PosX;

	/**  高亮区域 Y 坐标位置 */
	@ExcelCellBinding(offset = 10)
	protected int highlite1PosY;

	/**  高亮区域宽度 */
	@ExcelCellBinding(offset = 11)
	protected int highlite1Width;

	/**  高亮区域高度 */
	@ExcelCellBinding(offset = 12)
	protected int highlite1Height;

	/**  高亮区域边框颜色 */
	@ExcelCellBinding(offset = 13)
	protected String highlite1BorderColor;

	/**  高亮区域边框厚度 */
	@ExcelCellBinding(offset = 14)
	protected int highlite1BorderThickness;

	/**  高亮区域 X 坐标位置 */
	@ExcelCellBinding(offset = 15)
	protected int highlite2PosX;

	/**  高亮区域 Y 坐标位置 */
	@ExcelCellBinding(offset = 16)
	protected int highlite2PosY;

	/**  高亮区域宽度 */
	@ExcelCellBinding(offset = 17)
	protected int highlite2Width;

	/**  高亮区域高度 */
	@ExcelCellBinding(offset = 18)
	protected int highlite2Height;

	/**  高亮区域边框颜色 */
	@ExcelCellBinding(offset = 19)
	protected String highlite2BorderColor;

	/**  高亮区域边框厚度 */
	@ExcelCellBinding(offset = 20)
	protected int highlite2BorderThickness;

	/**  高亮区域 X 坐标位置 */
	@ExcelCellBinding(offset = 21)
	protected int highlite3PosX;

	/**  高亮区域 Y 坐标位置 */
	@ExcelCellBinding(offset = 22)
	protected int highlite3PosY;

	/**  高亮区域宽度 */
	@ExcelCellBinding(offset = 23)
	protected int highlite3Width;

	/**  高亮区域高度 */
	@ExcelCellBinding(offset = 24)
	protected int highlite3Height;

	/**  高亮区域边框颜色 */
	@ExcelCellBinding(offset = 25)
	protected String highlite3BorderColor;

	/**  高亮区域边框厚度 */
	@ExcelCellBinding(offset = 26)
	protected int highlite3BorderThickness;

	/**  高亮区域 X 坐标位置 */
	@ExcelCellBinding(offset = 27)
	protected int highlite4PosX;

	/**  高亮区域 Y 坐标位置 */
	@ExcelCellBinding(offset = 28)
	protected int highlite4PosY;

	/**  高亮区域宽度 */
	@ExcelCellBinding(offset = 29)
	protected int highlite4Width;

	/**  高亮区域高度 */
	@ExcelCellBinding(offset = 30)
	protected int highlite4Height;

	/**  高亮区域边框颜色 */
	@ExcelCellBinding(offset = 31)
	protected String highlite4BorderColor;

	/**  高亮区域边框厚度 */
	@ExcelCellBinding(offset = 32)
	protected int highlite4BorderThickness;

	/**  高亮区域 X 坐标位置 */
	@ExcelCellBinding(offset = 33)
	protected int highlite5PosX;

	/**  高亮区域 Y 坐标位置 */
	@ExcelCellBinding(offset = 34)
	protected int highlite5PosY;

	/**  高亮区域宽度 */
	@ExcelCellBinding(offset = 35)
	protected int highlite5Width;

	/**  高亮区域高度 */
	@ExcelCellBinding(offset = 36)
	protected int highlite5Height;

	/**  高亮区域边框颜色 */
	@ExcelCellBinding(offset = 37)
	protected String highlite5BorderColor;

	/**  高亮区域边框厚度 */
	@ExcelCellBinding(offset = 38)
	protected int highlite5BorderThickness;

	/**  箭头 1 X 坐标位置 */
	@ExcelCellBinding(offset = 39)
	protected int arrow1PosX;

	/**  箭头 1 Y 坐标位置 */
	@ExcelCellBinding(offset = 40)
	protected int arrow1PosY;

	/**  箭头 2 X 坐标位置 */
	@ExcelCellBinding(offset = 41)
	protected int arrow2PosX;

	/**  箭头 2 Y 坐标位置 */
	@ExcelCellBinding(offset = 42)
	protected int arrow2PosY;

	/**  箭头 3 X 坐标位置 */
	@ExcelCellBinding(offset = 43)
	protected int arrow3PosX;

	/**  箭头 3 Y 坐标位置 */
	@ExcelCellBinding(offset = 44)
	protected int arrow3PosY;

	/**  可操作区域 X 坐标位置 */
	@ExcelCellBinding(offset = 45)
	protected int operationalPosX;

	/**  可操作区域 Y 坐标位置 */
	@ExcelCellBinding(offset = 46)
	protected int operationalPosY;

	/**  可操作区域宽度 */
	@ExcelCellBinding(offset = 47)
	protected int operationalWidth;

	/**  可操作区域高度 */
	@ExcelCellBinding(offset = 48)
	protected int operationalHeight;

	/**  可操作区域边框颜色 */
	@ExcelCellBinding(offset = 49)
	protected String operationalBorderColor;

	/**  可操作区域边框厚度 */
	@ExcelCellBinding(offset = 50)
	protected int operationalBorderThickness;

	/**  操作事件 */
	@ExcelCellBinding(offset = 51)
	protected String event;

	/**  操作参数 */
	@ExcelCellBinding(offset = 52)
	protected String controlName;

	/**  动画资源名称 */
	@ExcelCellBinding(offset = 53)
	protected String movie;

	/**  在延时指定时间后自动进入下一步引导 */
	@ExcelCellBinding(offset = 54)
	protected int autoCommit;

	/**  场景 */
	@ExcelCellBinding(offset = 55)
	protected int sceneType;

	/**  引导图标 */
	@ExcelCellBinding(offset = 56)
	protected int guideIcon;

	/**  引导图标x */
	@ExcelCellBinding(offset = 57)
	protected int guideIconX;

	/**  引导图标y */
	@ExcelCellBinding(offset = 58)
	protected int guideIconY;

	/**  是否需要设置宝石消除位置 */
	@ExcelCellBinding(offset = 59)
	protected boolean needSetGemPos;

	/**  是否需要设置技能id */
	@ExcelCellBinding(offset = 60)
	protected boolean needSetSkill;


	public int getGuideTypeId() {
		return this.guideTypeId;
	}

	public void setGuideTypeId(int guideTypeId) {
		if (guideTypeId == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[ 引导类型]guideTypeId不可以为0");
		}
		if (guideTypeId < 1) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[ 引导类型]guideTypeId的值不得小于1");
		}
		this.guideTypeId = guideTypeId;
	}
	
	public String getMaskColor() {
		return this.maskColor;
	}

	public void setMaskColor(String maskColor) {
		if (StringUtils.isEmpty(maskColor)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[ 蒙板颜色]maskColor不可以为空");
		}
		this.maskColor = maskColor;
	}
	
	public int getMaskAlpha() {
		return this.maskAlpha;
	}

	public void setMaskAlpha(int maskAlpha) {
		if (maskAlpha > 100 || maskAlpha < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					4, "[ 蒙板透明度]maskAlpha的值不合法，应为0至100之间");
		}
		this.maskAlpha = maskAlpha;
	}
	
	public int getGuideTextLangId() {
		return this.guideTextLangId;
	}

	public void setGuideTextLangId(int guideTextLangId) {
		if (guideTextLangId < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					5, "[ 引导文字多语言 ID]guideTextLangId的值不得小于0");
		}
		this.guideTextLangId = guideTextLangId;
	}
	
	public String getGuideText() {
		return this.guideText;
	}

	public void setGuideText(String guideText) {
		this.guideText = guideText;
	}
	
	public int getTextPosX() {
		return this.textPosX;
	}

	public void setTextPosX(int textPosX) {
		if (textPosX < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					7, "[ 引导文字 X 坐标位置]textPosX的值不得小于0");
		}
		this.textPosX = textPosX;
	}
	
	public int getTextPosY() {
		return this.textPosY;
	}

	public void setTextPosY(int textPosY) {
		if (textPosY < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					8, "[ 引导文字 Y 坐标位置]textPosY的值不得小于0");
		}
		this.textPosY = textPosY;
	}
	
	public int getTextMode() {
		return this.textMode;
	}

	public void setTextMode(int textMode) {
		if (textMode < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					9, "[ 文字方式, 0 = 全显方式, 1 = 逐字显示]textMode的值不得小于0");
		}
		this.textMode = textMode;
	}
	
	public int getHighlite1PosX() {
		return this.highlite1PosX;
	}

	public void setHighlite1PosX(int highlite1PosX) {
		if (highlite1PosX < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					10, "[ 高亮区域 X 坐标位置]highlite1PosX的值不得小于0");
		}
		this.highlite1PosX = highlite1PosX;
	}
	
	public int getHighlite1PosY() {
		return this.highlite1PosY;
	}

	public void setHighlite1PosY(int highlite1PosY) {
		if (highlite1PosY < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					11, "[ 高亮区域 Y 坐标位置]highlite1PosY的值不得小于0");
		}
		this.highlite1PosY = highlite1PosY;
	}
	
	public int getHighlite1Width() {
		return this.highlite1Width;
	}

	public void setHighlite1Width(int highlite1Width) {
		if (highlite1Width < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					12, "[ 高亮区域宽度]highlite1Width的值不得小于0");
		}
		this.highlite1Width = highlite1Width;
	}
	
	public int getHighlite1Height() {
		return this.highlite1Height;
	}

	public void setHighlite1Height(int highlite1Height) {
		if (highlite1Height < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					13, "[ 高亮区域高度]highlite1Height的值不得小于0");
		}
		this.highlite1Height = highlite1Height;
	}
	
	public String getHighlite1BorderColor() {
		return this.highlite1BorderColor;
	}

	public void setHighlite1BorderColor(String highlite1BorderColor) {
		this.highlite1BorderColor = highlite1BorderColor;
	}
	
	public int getHighlite1BorderThickness() {
		return this.highlite1BorderThickness;
	}

	public void setHighlite1BorderThickness(int highlite1BorderThickness) {
		if (highlite1BorderThickness < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					15, "[ 高亮区域边框厚度]highlite1BorderThickness的值不得小于0");
		}
		this.highlite1BorderThickness = highlite1BorderThickness;
	}
	
	public int getHighlite2PosX() {
		return this.highlite2PosX;
	}

	public void setHighlite2PosX(int highlite2PosX) {
		if (highlite2PosX < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					16, "[ 高亮区域 X 坐标位置]highlite2PosX的值不得小于0");
		}
		this.highlite2PosX = highlite2PosX;
	}
	
	public int getHighlite2PosY() {
		return this.highlite2PosY;
	}

	public void setHighlite2PosY(int highlite2PosY) {
		if (highlite2PosY < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					17, "[ 高亮区域 Y 坐标位置]highlite2PosY的值不得小于0");
		}
		this.highlite2PosY = highlite2PosY;
	}
	
	public int getHighlite2Width() {
		return this.highlite2Width;
	}

	public void setHighlite2Width(int highlite2Width) {
		if (highlite2Width < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					18, "[ 高亮区域宽度]highlite2Width的值不得小于0");
		}
		this.highlite2Width = highlite2Width;
	}
	
	public int getHighlite2Height() {
		return this.highlite2Height;
	}

	public void setHighlite2Height(int highlite2Height) {
		if (highlite2Height < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					19, "[ 高亮区域高度]highlite2Height的值不得小于0");
		}
		this.highlite2Height = highlite2Height;
	}
	
	public String getHighlite2BorderColor() {
		return this.highlite2BorderColor;
	}

	public void setHighlite2BorderColor(String highlite2BorderColor) {
		this.highlite2BorderColor = highlite2BorderColor;
	}
	
	public int getHighlite2BorderThickness() {
		return this.highlite2BorderThickness;
	}

	public void setHighlite2BorderThickness(int highlite2BorderThickness) {
		if (highlite2BorderThickness < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					21, "[ 高亮区域边框厚度]highlite2BorderThickness的值不得小于0");
		}
		this.highlite2BorderThickness = highlite2BorderThickness;
	}
	
	public int getHighlite3PosX() {
		return this.highlite3PosX;
	}

	public void setHighlite3PosX(int highlite3PosX) {
		if (highlite3PosX < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					22, "[ 高亮区域 X 坐标位置]highlite3PosX的值不得小于0");
		}
		this.highlite3PosX = highlite3PosX;
	}
	
	public int getHighlite3PosY() {
		return this.highlite3PosY;
	}

	public void setHighlite3PosY(int highlite3PosY) {
		if (highlite3PosY < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					23, "[ 高亮区域 Y 坐标位置]highlite3PosY的值不得小于0");
		}
		this.highlite3PosY = highlite3PosY;
	}
	
	public int getHighlite3Width() {
		return this.highlite3Width;
	}

	public void setHighlite3Width(int highlite3Width) {
		if (highlite3Width < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					24, "[ 高亮区域宽度]highlite3Width的值不得小于0");
		}
		this.highlite3Width = highlite3Width;
	}
	
	public int getHighlite3Height() {
		return this.highlite3Height;
	}

	public void setHighlite3Height(int highlite3Height) {
		if (highlite3Height < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					25, "[ 高亮区域高度]highlite3Height的值不得小于0");
		}
		this.highlite3Height = highlite3Height;
	}
	
	public String getHighlite3BorderColor() {
		return this.highlite3BorderColor;
	}

	public void setHighlite3BorderColor(String highlite3BorderColor) {
		this.highlite3BorderColor = highlite3BorderColor;
	}
	
	public int getHighlite3BorderThickness() {
		return this.highlite3BorderThickness;
	}

	public void setHighlite3BorderThickness(int highlite3BorderThickness) {
		if (highlite3BorderThickness < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					27, "[ 高亮区域边框厚度]highlite3BorderThickness的值不得小于0");
		}
		this.highlite3BorderThickness = highlite3BorderThickness;
	}
	
	public int getHighlite4PosX() {
		return this.highlite4PosX;
	}

	public void setHighlite4PosX(int highlite4PosX) {
		if (highlite4PosX < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					28, "[ 高亮区域 X 坐标位置]highlite4PosX的值不得小于0");
		}
		this.highlite4PosX = highlite4PosX;
	}
	
	public int getHighlite4PosY() {
		return this.highlite4PosY;
	}

	public void setHighlite4PosY(int highlite4PosY) {
		if (highlite4PosY < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					29, "[ 高亮区域 Y 坐标位置]highlite4PosY的值不得小于0");
		}
		this.highlite4PosY = highlite4PosY;
	}
	
	public int getHighlite4Width() {
		return this.highlite4Width;
	}

	public void setHighlite4Width(int highlite4Width) {
		if (highlite4Width < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					30, "[ 高亮区域宽度]highlite4Width的值不得小于0");
		}
		this.highlite4Width = highlite4Width;
	}
	
	public int getHighlite4Height() {
		return this.highlite4Height;
	}

	public void setHighlite4Height(int highlite4Height) {
		if (highlite4Height < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					31, "[ 高亮区域高度]highlite4Height的值不得小于0");
		}
		this.highlite4Height = highlite4Height;
	}
	
	public String getHighlite4BorderColor() {
		return this.highlite4BorderColor;
	}

	public void setHighlite4BorderColor(String highlite4BorderColor) {
		this.highlite4BorderColor = highlite4BorderColor;
	}
	
	public int getHighlite4BorderThickness() {
		return this.highlite4BorderThickness;
	}

	public void setHighlite4BorderThickness(int highlite4BorderThickness) {
		if (highlite4BorderThickness < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					33, "[ 高亮区域边框厚度]highlite4BorderThickness的值不得小于0");
		}
		this.highlite4BorderThickness = highlite4BorderThickness;
	}
	
	public int getHighlite5PosX() {
		return this.highlite5PosX;
	}

	public void setHighlite5PosX(int highlite5PosX) {
		if (highlite5PosX < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					34, "[ 高亮区域 X 坐标位置]highlite5PosX的值不得小于0");
		}
		this.highlite5PosX = highlite5PosX;
	}
	
	public int getHighlite5PosY() {
		return this.highlite5PosY;
	}

	public void setHighlite5PosY(int highlite5PosY) {
		if (highlite5PosY < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					35, "[ 高亮区域 Y 坐标位置]highlite5PosY的值不得小于0");
		}
		this.highlite5PosY = highlite5PosY;
	}
	
	public int getHighlite5Width() {
		return this.highlite5Width;
	}

	public void setHighlite5Width(int highlite5Width) {
		if (highlite5Width < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					36, "[ 高亮区域宽度]highlite5Width的值不得小于0");
		}
		this.highlite5Width = highlite5Width;
	}
	
	public int getHighlite5Height() {
		return this.highlite5Height;
	}

	public void setHighlite5Height(int highlite5Height) {
		if (highlite5Height < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					37, "[ 高亮区域高度]highlite5Height的值不得小于0");
		}
		this.highlite5Height = highlite5Height;
	}
	
	public String getHighlite5BorderColor() {
		return this.highlite5BorderColor;
	}

	public void setHighlite5BorderColor(String highlite5BorderColor) {
		this.highlite5BorderColor = highlite5BorderColor;
	}
	
	public int getHighlite5BorderThickness() {
		return this.highlite5BorderThickness;
	}

	public void setHighlite5BorderThickness(int highlite5BorderThickness) {
		if (highlite5BorderThickness < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					39, "[ 高亮区域边框厚度]highlite5BorderThickness的值不得小于0");
		}
		this.highlite5BorderThickness = highlite5BorderThickness;
	}
	
	public int getArrow1PosX() {
		return this.arrow1PosX;
	}

	public void setArrow1PosX(int arrow1PosX) {
		if (arrow1PosX < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					40, "[ 箭头 1 X 坐标位置]arrow1PosX的值不得小于0");
		}
		this.arrow1PosX = arrow1PosX;
	}
	
	public int getArrow1PosY() {
		return this.arrow1PosY;
	}

	public void setArrow1PosY(int arrow1PosY) {
		if (arrow1PosY < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					41, "[ 箭头 1 Y 坐标位置]arrow1PosY的值不得小于0");
		}
		this.arrow1PosY = arrow1PosY;
	}
	
	public int getArrow2PosX() {
		return this.arrow2PosX;
	}

	public void setArrow2PosX(int arrow2PosX) {
		if (arrow2PosX < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					42, "[ 箭头 2 X 坐标位置]arrow2PosX的值不得小于0");
		}
		this.arrow2PosX = arrow2PosX;
	}
	
	public int getArrow2PosY() {
		return this.arrow2PosY;
	}

	public void setArrow2PosY(int arrow2PosY) {
		if (arrow2PosY < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					43, "[ 箭头 2 Y 坐标位置]arrow2PosY的值不得小于0");
		}
		this.arrow2PosY = arrow2PosY;
	}
	
	public int getArrow3PosX() {
		return this.arrow3PosX;
	}

	public void setArrow3PosX(int arrow3PosX) {
		if (arrow3PosX < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					44, "[ 箭头 3 X 坐标位置]arrow3PosX的值不得小于0");
		}
		this.arrow3PosX = arrow3PosX;
	}
	
	public int getArrow3PosY() {
		return this.arrow3PosY;
	}

	public void setArrow3PosY(int arrow3PosY) {
		if (arrow3PosY < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					45, "[ 箭头 3 Y 坐标位置]arrow3PosY的值不得小于0");
		}
		this.arrow3PosY = arrow3PosY;
	}
	
	public int getOperationalPosX() {
		return this.operationalPosX;
	}

	public void setOperationalPosX(int operationalPosX) {
		if (operationalPosX < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					46, "[ 可操作区域 X 坐标位置]operationalPosX的值不得小于0");
		}
		this.operationalPosX = operationalPosX;
	}
	
	public int getOperationalPosY() {
		return this.operationalPosY;
	}

	public void setOperationalPosY(int operationalPosY) {
		if (operationalPosY < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					47, "[ 可操作区域 Y 坐标位置]operationalPosY的值不得小于0");
		}
		this.operationalPosY = operationalPosY;
	}
	
	public int getOperationalWidth() {
		return this.operationalWidth;
	}

	public void setOperationalWidth(int operationalWidth) {
		if (operationalWidth < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					48, "[ 可操作区域宽度]operationalWidth的值不得小于0");
		}
		this.operationalWidth = operationalWidth;
	}
	
	public int getOperationalHeight() {
		return this.operationalHeight;
	}

	public void setOperationalHeight(int operationalHeight) {
		if (operationalHeight < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					49, "[ 可操作区域高度]operationalHeight的值不得小于0");
		}
		this.operationalHeight = operationalHeight;
	}
	
	public String getOperationalBorderColor() {
		return this.operationalBorderColor;
	}

	public void setOperationalBorderColor(String operationalBorderColor) {
		this.operationalBorderColor = operationalBorderColor;
	}
	
	public int getOperationalBorderThickness() {
		return this.operationalBorderThickness;
	}

	public void setOperationalBorderThickness(int operationalBorderThickness) {
		if (operationalBorderThickness < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					51, "[ 可操作区域边框厚度]operationalBorderThickness的值不得小于0");
		}
		this.operationalBorderThickness = operationalBorderThickness;
	}
	
	public String getEvent() {
		return this.event;
	}

	public void setEvent(String event) {
		this.event = event;
	}
	
	public String getControlName() {
		return this.controlName;
	}

	public void setControlName(String controlName) {
		this.controlName = controlName;
	}
	
	public String getMovie() {
		return this.movie;
	}

	public void setMovie(String movie) {
		this.movie = movie;
	}
	
	public int getAutoCommit() {
		return this.autoCommit;
	}

	public void setAutoCommit(int autoCommit) {
		if (autoCommit < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					55, "[ 在延时指定时间后自动进入下一步引导]autoCommit的值不得小于0");
		}
		this.autoCommit = autoCommit;
	}
	
	public int getSceneType() {
		return this.sceneType;
	}

	public void setSceneType(int sceneType) {
		if (sceneType < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					56, "[ 场景]sceneType的值不得小于0");
		}
		this.sceneType = sceneType;
	}
	
	public int getGuideIcon() {
		return this.guideIcon;
	}

	public void setGuideIcon(int guideIcon) {
		this.guideIcon = guideIcon;
	}
	
	public int getGuideIconX() {
		return this.guideIconX;
	}

	public void setGuideIconX(int guideIconX) {
		this.guideIconX = guideIconX;
	}
	
	public int getGuideIconY() {
		return this.guideIconY;
	}

	public void setGuideIconY(int guideIconY) {
		this.guideIconY = guideIconY;
	}
	
	public boolean isNeedSetGemPos() {
		return this.needSetGemPos;
	}

	public void setNeedSetGemPos(boolean needSetGemPos) {
		this.needSetGemPos = needSetGemPos;
	}
	
	public boolean isNeedSetSkill() {
		return this.needSetSkill;
	}

	public void setNeedSetSkill(boolean needSetSkill) {
		this.needSetSkill = needSetSkill;
	}
	

	@Override
	public String toString() {
		return "GuideTemplateVO[guideTypeId=" + guideTypeId + ",maskColor=" + maskColor + ",maskAlpha=" + maskAlpha + ",guideTextLangId=" + guideTextLangId + ",guideText=" + guideText + ",textPosX=" + textPosX + ",textPosY=" + textPosY + ",textMode=" + textMode + ",highlite1PosX=" + highlite1PosX + ",highlite1PosY=" + highlite1PosY + ",highlite1Width=" + highlite1Width + ",highlite1Height=" + highlite1Height + ",highlite1BorderColor=" + highlite1BorderColor + ",highlite1BorderThickness=" + highlite1BorderThickness + ",highlite2PosX=" + highlite2PosX + ",highlite2PosY=" + highlite2PosY + ",highlite2Width=" + highlite2Width + ",highlite2Height=" + highlite2Height + ",highlite2BorderColor=" + highlite2BorderColor + ",highlite2BorderThickness=" + highlite2BorderThickness + ",highlite3PosX=" + highlite3PosX + ",highlite3PosY=" + highlite3PosY + ",highlite3Width=" + highlite3Width + ",highlite3Height=" + highlite3Height + ",highlite3BorderColor=" + highlite3BorderColor + ",highlite3BorderThickness=" + highlite3BorderThickness + ",highlite4PosX=" + highlite4PosX + ",highlite4PosY=" + highlite4PosY + ",highlite4Width=" + highlite4Width + ",highlite4Height=" + highlite4Height + ",highlite4BorderColor=" + highlite4BorderColor + ",highlite4BorderThickness=" + highlite4BorderThickness + ",highlite5PosX=" + highlite5PosX + ",highlite5PosY=" + highlite5PosY + ",highlite5Width=" + highlite5Width + ",highlite5Height=" + highlite5Height + ",highlite5BorderColor=" + highlite5BorderColor + ",highlite5BorderThickness=" + highlite5BorderThickness + ",arrow1PosX=" + arrow1PosX + ",arrow1PosY=" + arrow1PosY + ",arrow2PosX=" + arrow2PosX + ",arrow2PosY=" + arrow2PosY + ",arrow3PosX=" + arrow3PosX + ",arrow3PosY=" + arrow3PosY + ",operationalPosX=" + operationalPosX + ",operationalPosY=" + operationalPosY + ",operationalWidth=" + operationalWidth + ",operationalHeight=" + operationalHeight + ",operationalBorderColor=" + operationalBorderColor + ",operationalBorderThickness=" + operationalBorderThickness + ",event=" + event + ",controlName=" + controlName + ",movie=" + movie + ",autoCommit=" + autoCommit + ",sceneType=" + sceneType + ",guideIcon=" + guideIcon + ",guideIconX=" + guideIconX + ",guideIconY=" + guideIconY + ",needSetGemPos=" + needSetGemPos + ",needSetSkill=" + needSetSkill + ",]";

	}
}