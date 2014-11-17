package com.hifun.soul.robot;


public class RobotTest {

	public static void main(String[] args){
		Robot localUser = new Robot("bot", 1, "192.168.1.106", 8001);		
		localUser.start();
	}
}
