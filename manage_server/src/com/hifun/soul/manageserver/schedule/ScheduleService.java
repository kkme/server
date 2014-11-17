package com.hifun.soul.manageserver.schedule;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.IMessage;
import com.hifun.soul.core.msg.SceneScheduleMessage;
import com.hifun.soul.core.server.IMessageProcessor;
import com.hifun.soul.core.server.TimerManager;

@Scope("singleton")
@Component
public class ScheduleService {
	/** 消息队列 */
	private IMessageProcessor messageProcessor;
	/** 场景调度管理器 */
	private TimerManager sceneTimerManager = new TimerManager("ManageServerScheduler");
	
	public void start(){
		sceneTimerManager.start(new IMessageProcessor(){
			@Override
			public void start() {
				
			}

			@Override
			public void stop() {
				
			}

			@Override
			public void put(IMessage msg) {
				execScheduleMessage(msg);
			}

			@Override
			public boolean isFull() {				
				return false;
			}
			
		});
	}
	
	public void stop(){
		sceneTimerManager.stop();
	}
	
	public IMessageProcessor getMessageProcessor() {
		return messageProcessor;
	}

	public void setMessageProcessor(IMessageProcessor messageProcessor) {
		this.messageProcessor = messageProcessor;
	}

	private void execScheduleMessage(IMessage message){
		if(message instanceof SceneScheduleMessage){
			SceneScheduleMessage scheduleMsg = (SceneScheduleMessage)message;
			if(scheduleMsg.isCanceled()){
				return;
			}
			messageProcessor.put(scheduleMsg);
		}
	}
	
	public void scheduleOnce(SceneScheduleMessage message,long delay){		
			sceneTimerManager.scheduleOnece(message, delay);		
	}
	
	public void scheduleAtFixedRate(SceneScheduleMessage task,long delay, long period){
		sceneTimerManager.scheduleAtFixedRate(task, delay, period);
	}
}
