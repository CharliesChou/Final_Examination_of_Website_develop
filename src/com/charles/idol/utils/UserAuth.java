package com.charles.idol.utils;
import javax.servlet.http.HttpServletRequest;

import com.charles.idol.pojo.User;
public class UserAuth {
	public static boolean authAmin(HttpServletRequest req)
	{
		User u=(User) req.getSession().getAttribute("user");
		if(u==null)
		{
			return false;
		}
		else if(u.getPower()!=2)
		{
			return false;
		}
		else
		{
			return true;
		}
	}
	public static boolean authUser(HttpServletRequest req)
	{
		User u=(User)req.getSession().getAttribute("user");
		if(u==null)
		{
			return false;
		}
		else if(u.getPower()!=2)
		{
			return false;
		}
		else
		{
			return true;
		}
	}
	public static String getUsername(HttpServletRequest req)
	{
		if(authUser(req))
		{
			return ((User)req.getSession().getAttribute("user")).getUsername();
		}
		else
		{
			return null;
		}
		 
	}
}
