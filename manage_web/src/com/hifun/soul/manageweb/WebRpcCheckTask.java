package com.hifun.soul.manageweb;

import java.util.Timer;
import java.util.TimerTask;

import com.google.protobuf.ServiceException;
import com.hifun.soul.manageweb.service.ServiceManager;
import com.hifun.soul.proto.services.Services.CheckConnectionRequest;

public class WebRpcCheckTask extends TimerTask{
	private static final long CHECK_RPC_PERIOD=1000*60*3; //3分钟检查一次
	Timer timer = new Timer("WebRpcCheckSchedule");
	ServiceManager serviceManager;
	
	public void start(ServiceManager serviceManager){
		this.serviceManager = serviceManager;
		timer.schedule(this, CHECK_RPC_PERIOD, CHECK_RPC_PERIOD);
	}

	@Override
	public void run() {
		if(serviceManager != null){
			try {
				serviceManager.getRuntimeServie().checkConnection(null, CheckConnectionRequest.newBuilder().build());
			} catch (ServiceException e) {
				e.printStackTrace();
			}
		}
	}

}
