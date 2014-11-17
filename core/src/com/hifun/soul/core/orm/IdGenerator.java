package com.hifun.soul.core.orm;

import java.io.Serializable;


/**
 * 
 * 
 */
public interface IdGenerator {
	public Serializable generateId(IEntity entity);
}