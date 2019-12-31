package com.charles.idol.test;
import java.util.Date;

import com.charles.idol.pojo.User;
import com.charles.idol.service.UserService;
import com.charles.idol.service.impl.UserServiceImpl;
public class TestFuc {
	public static void main(String[] args) {
		//User(String username, String password, String profile, String email, String birth, String place) {
//		User u=new User("charles","1001abcd1","1.png","111@qq.com","1999-8-9","cqy1c");
//		UserService ser=new UserServiceImpl();
//		boolean register = ser.register(u);
//		System.out.println(register);
		testtime();
	}
	public static void testtime()
	{
		System.out.println(new Date().getTime());
	}
}
