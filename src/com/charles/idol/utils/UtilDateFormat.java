package com.charles.idol.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UtilDateFormat {
	private static DateFormat df=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	public static String getFormatDate()
	{
		return df.format(new Date());
	}

}
