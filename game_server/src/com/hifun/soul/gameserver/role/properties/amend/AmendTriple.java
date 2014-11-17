package com.hifun.soul.gameserver.role.properties.amend;

import net.sf.json.JSONArray;

/**
 * 属性修正的实例
 * 
 */
public class AmendTriple {
	
	private final Amend amend;
	
	private final AmendMethod method;
	
	/** 基础值 如果是加法--该值没用  如果是乘法--该值表示被乘数  */
	private final float baseAmendValue;
	
	/** 实际值 如果是加法--该值为加数  如果是乘法--该值表示乘数 */
	private final float variationValue;
	
	/** 强化每级增加的数值 */
	private int enhancePerLevel;

	public AmendTriple(Amend amend, AmendMethod method, float baseAmendValue, float variationValue, int enhancePerLevel) {
		if (amend == null || method == null) {
			throw new IllegalArgumentException("The amend and method must not be null.");
		}
		this.amend = amend;
		this.method = method;
		this.baseAmendValue = baseAmendValue;
		this.variationValue = variationValue;
		this.enhancePerLevel = enhancePerLevel;
	}

	public Amend getAmend() {
		return amend;
	}

	public AmendMethod getMethod() {
		return method;
	}

	public float getBaseAmendValue() {
		return baseAmendValue;
	}
	
	public float getVariationValue() {
		return variationValue;
	}

	public int getEnhancePerLevel() {
		return enhancePerLevel;
	}

	public float getAmendValue() {
		if (this.method == AmendMethod.ADD) {
			return this.variationValue;
		} else if (this.method == AmendMethod.MULIPLY) {
			return baseAmendValue * this.variationValue;
		} else if (this.method == AmendMethod.ADD_PER) {
			return baseAmendValue * this.variationValue / 100.0f;
		} else {
			throw new UnsupportedOperationException("Unknown method type:"
					+ this.method);
		}
	}
	
	/**
	 * 生成存库用的json string
	 * 
	 * @return
	 */
	public JSONArray toJsonArray() {
		JSONArray array = new JSONArray();
		array.add(amend.getKey());
		array.add(method.index);
		array.add(baseAmendValue);
		array.add(variationValue);
		return array;
	}

	@Override
	public String toString() {
		return "AmendTuple [amend=" + amend + ", method=" + method
				+ ", baseAmendValue=" + baseAmendValue + ", variationValue="
				+ variationValue + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((amend == null) ? 0 : amend.hashCode());
		result = prime * result + Float.floatToIntBits(baseAmendValue);
		result = prime * result + ((method == null) ? 0 : method.hashCode());
		result = prime * result + Float.floatToIntBits(variationValue);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AmendTriple other = (AmendTriple) obj;
		if (amend == null) {
			if (other.amend != null)
				return false;
		} else if (!amend.equals(other.amend))
			return false;
		if (Float.floatToIntBits(baseAmendValue) != Float
				.floatToIntBits(other.baseAmendValue))
			return false;
		if (method != other.method)
			return false;
		if (Float.floatToIntBits(variationValue) != Float
				.floatToIntBits(other.variationValue))
			return false;
		return true;
	}
	
}
