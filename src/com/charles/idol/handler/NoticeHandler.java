package com.charles.idol.handler;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPObject;
import com.charles.idol.pojo.Notice;
import com.charles.idol.pojo.User;
import com.charles.idol.service.NoticeService;
import com.charles.idol.utils.EnCoder;
import com.charles.idol.utils.JSONConvertor;
import com.charles.idol.utils.UserAuth;
import com.charles.idol.utils.UtilDateFormat;
@RequestMapping("api/v1/idol/notice")
@Controller
public class NoticeHandler {
	@Autowired
	@Qualifier("noticeService")
	private NoticeService noticeService;
	public void setNoticeService(NoticeService noticeService) {
		this.noticeService = noticeService;
	}
	@ResponseBody
	@RequestMapping(value="postNotice",method = RequestMethod.POST)
	public String  postNotice(HttpServletRequest request,HttpServletResponse response)
	{
		JSONObject obj = new JSONObject();
		if(UserAuth.authAmin(request))
		{
			String content=request.getParameter("content");
			String sender=((User)request.getSession().getAttribute("user")).getUsername();
			String sendtime=UtilDateFormat.getFormatDate();
			String noticeid=EnCoder.md5Encoder((""+new Date().getTime()).getBytes());
			//public Notice(String sender, String content, String sendtime, String noticeid)
			Notice notice=new Notice(sender,content,sendtime,noticeid);
			boolean postNotice = noticeService.postNotice(notice);
			if(postNotice)
			{
				obj.put("status","1");
				obj.put("msg","发布成功");
			}
			else
			{
				obj.put("status","-1");
				obj.put("msg","发布失败，系统异常！");
			}
		}
		else
		{
			obj.put("status","0");
			obj.put("msg","您未登陆，权限错误！");
		}
		return obj.toString();
	}
	@ResponseBody
	@RequestMapping(value="delNoticeById",method = RequestMethod.POST)
	public String delNoticeById(HttpServletRequest request,HttpServletResponse response)
	{
		JSONObject obj = new JSONObject();
		if(UserAuth.authAmin(request))
		{
			HashMap<String,String> map=new HashMap<String,String>();
			String noticeid=request.getParameter("noticeid");
			map.put("noticeid", noticeid);
			boolean delNoticeById = noticeService.delNoticeById(map);
			if(delNoticeById)
			{
				obj.put("status","1");
				obj.put("msg","删除成功");
			}
			else
			{
				obj.put("status","-1");
				obj.put("msg","删除失败，系统异常！");
			}
		}
		else
		{
			obj.put("status","0");
			obj.put("msg","您未登陆，权限错误！");
		}
		return obj.toString();
	}
	@ResponseBody
	@RequestMapping(value="getAllNotice",method = RequestMethod.GET)
	public String getAllNotice(HttpServletRequest request,HttpServletResponse response){
		JSONObject obj = new JSONObject();
		JSONArray objs=new JSONArray();
		HashMap<String,Integer> page=new HashMap<String,Integer>();
		int no=Integer.parseInt(request.getParameter("page"));
		int size=Integer.parseInt(request.getParameter("limit"));
		page.put("pageNo", (no-1)*size);
		page.put("pageSize", size);
		if(UserAuth.authAmin(request))
		{
			List<Notice> allNotice = noticeService.getAllNotice(page);
			JSONArray list2json_notice = JSONConvertor.list2json_notice(allNotice);
			obj.put("code", 0);
			obj.put("data",list2json_notice);
			obj.put("count", "9999");
		}
		else
		{
			obj.put("code","1");
			obj.put("msg","您未登录！");
		}
		return obj.toString();
	}
	@ResponseBody
	@RequestMapping(value="updateNoticeById",method = RequestMethod.POST)
	public String updateNoticeById(HttpServletRequest request,HttpServletResponse response)
	{
		JSONObject obj = new JSONObject();
		HashMap<String,String> para=new HashMap<String,String>();
		if(UserAuth.authAmin(request))
		{
			String content=request.getParameter("content");
			String noticeid=request.getParameter("noticeid");
			para.put("content",content);
			para.put("noticeid",noticeid);
			boolean updateNoticeById = noticeService.updateNoticeById(para);
			if(updateNoticeById)
			{
				obj.put("status","1");
				obj.put("msg","修改成功");
			}
			else
			{
				obj.put("status","-1");
				obj.put("msg","修改失败，系统异常！");
			}
		}
		else
		{
			obj.put("status","0");
			obj.put("msg","您未登陆，权限错误！");
		}
		return obj.toString();
	}
	//主页的公告
	@ResponseBody
	@RequestMapping(value="getAllNotice_index",method = RequestMethod.GET)
	public String getAllNotice_index(HttpServletRequest request,HttpServletResponse response){
		JSONObject obj = new JSONObject();
		JSONArray objs=new JSONArray();
		HashMap<String,Integer> page=new HashMap<String,Integer>();
		//int no=Integer.parseInt(request.getParameter("page"));
		//int size=Integer.parseInt(request.getParameter("limit"));
		page.put("pageNo",0);
		page.put("pageSize", 5);
			List<Notice> allNotice = noticeService.getAllNotice(page);
			JSONArray list2json_notice = JSONConvertor.list2json_notice(allNotice);
			//obj.put("code", 0);
			obj.put("notice",list2json_notice);
			//obj.put("count", "9999");
			return obj.toString();
	}
}
