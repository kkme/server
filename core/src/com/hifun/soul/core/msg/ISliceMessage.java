package com.hifun.soul.core.msg;

import java.util.List;

public interface ISliceMessage<T extends BaseMessage> {

	public abstract List<T> getSliceMessages();

}