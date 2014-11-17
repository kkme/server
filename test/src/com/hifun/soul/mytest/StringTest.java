package com.hifun.soul.mytest;

public class StringTest {
public static void main(String[] args) {
	System.out.println("哈哈".length());
	System.out.println("abc".length());
	
	String s = "31";
	System.out.println(s.substring(s.length()-1));
	
	String ss = "[1041-1042]";
	String newSs = ss.replace("[","");
	newSs = newSs.replace("]", "");
	System.out.println(newSs);
}
}
