package com.huateng.mina;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MinaIntegratedSpring {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new ClassPathXmlApplicationContext("applicationContext.xml");  
	}

}
