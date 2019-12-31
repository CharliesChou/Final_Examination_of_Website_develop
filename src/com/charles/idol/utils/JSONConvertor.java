package com.charles.idol.utils;

import java.util.HashMap;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.charles.idol.pojo.Essay;
import com.charles.idol.pojo.MsgBoard;
import com.charles.idol.pojo.Notice;
import com.charles.idol.pojo.User;
import com.sun.javafx.collections.MappingChange.Map;

public class JSONConvertor {
	public static HashMap json2map(String data)
	{
		HashMap parse = JSON.parseObject(data, HashMap.class);
		return parse;
	}
	//转换文章列表，包括全部原始内容
	public static JSONArray list2json_essay(List<Essay> list)
	{
		/*
		 *	<option value="0">科技榜</option>
	        <option value="1">爱国榜</option>
	        <option value="2">学习榜</option>
	        <option value="3">敬业榜</option>
	        <option value="4">光荣榜</option>
	        <option value="5">富强榜</option>
		 * */
		JSONArray data=new JSONArray();
		for(Essay essay:list)
		{
			JSONObject obj=new JSONObject();
			obj.put("content",essay.getContent());
			obj.put("editor",essay.getEditor());
			obj.put("essayid",essay.getEssayid());
			obj.put("from",essay.getFrom());
			obj.put("img",essay.getImg());
			obj.put("time",essay.getTime());
			obj.put("title",essay.getTitle());
			switch(essay.getType())
			{
			case 0:obj.put("type","科技榜");break;
			case 1:obj.put("type","爱国榜");break;
			case 2:obj.put("type","学习榜");break;
			case 3:obj.put("type","敬业榜");break;
			case 4:obj.put("type","光荣榜");break;
			case 5:obj.put("type","富强榜");break;
			}
			data.add(obj);
		}
		return data;
	}
	//删减/更改版，用作主页显示
	public static JSONArray list2json_essay_decrease(List<Essay> list)
	{
		/*
		 *	<option value="0">科技榜</option>
	        <option value="1">爱国榜</option>
	        <option value="2">学习榜</option>
	        <option value="3">敬业榜</option>
	        <option value="4">光荣榜</option>
	        <option value="5">富强榜</option>
		 * */
		JSONArray data=new JSONArray();
		for(Essay essay:list)
		{
			JSONObject obj=new JSONObject();
			obj.put("content",essay.getContent());
			obj.put("editor",essay.getEditor());
			obj.put("essayid","idol_info.html?essayid="+essay.getEssayid());
			obj.put("from",essay.getFrom());
			obj.put("img",essay.getImg());
			obj.put("time",essay.getTime());
			obj.put("title",essay.getTitle());
			switch(essay.getType())
			{
			case 0:obj.put("type","科技榜");break;
			case 1:obj.put("type","爱国榜");break;
			case 2:obj.put("type","学习榜");break;
			case 3:obj.put("type","敬业榜");break;
			case 4:obj.put("type","光荣榜");break;
			case 5:obj.put("type","富强榜");break;
			}
			data.add(obj);
		}
		return data;
	}
	public static JSONArray list2json_user(List<User> list)
	{
		JSONArray data=new JSONArray();
		for(User user:list)
		{
			JSONObject obj=new JSONObject();
			obj.put("username",user.getUsername());
			obj.put("password",user.getPassword());
			obj.put("email",user.getEmail());
			obj.put("profile",user.getProfile());
			obj.put("place",user.getPlace());
			obj.put("birth",user.getBirth());
			switch(user.getPower())
			{
			case 0:obj.put("power","已禁用");break;
			case 1:obj.put("power","普通会员");break;
			case 2:obj.put("power","管理员");break;
			}
			data.add(obj);
		}
		return data;
	}
	public static JSONArray list2json_notice(List<Notice> list)
	{
		JSONArray data=new JSONArray();
		for(Notice notice:list)
		{
			JSONObject obj=new JSONObject();
			obj.put("sender",notice.getSender());
			obj.put("sendtime",notice.getSendtime());
			obj.put("content",notice.getContent());
			obj.put("noticeid",notice.getNoticeid());
			data.add(obj);
		}
		return data;
	}
	public static JSONArray list2json_msgBoard(List<MsgBoard> list)
	{
		JSONArray data=new JSONArray();
		for(MsgBoard msgBoard:list)
		{
			JSONObject obj=new JSONObject();
			obj.put("msgid",msgBoard.getMsgid());
			obj.put("essayid",msgBoard.getEssayid());
			obj.put("sender",msgBoard.getSender());
			obj.put("sendtime",msgBoard.getSendtime());
			obj.put("content",msgBoard.getContent());
			data.add(obj);
		}
		return data;
	}
}
