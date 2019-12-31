package com.charles.idol.handler;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.charles.idol.pojo.MsgBoard;
import com.charles.idol.service.MsgBoardService;
import com.charles.idol.utils.EnCoder;
import com.charles.idol.utils.JSONConvertor;
import com.charles.idol.utils.UserAuth;
import com.charles.idol.utils.UtilDateFormat;
@RequestMapping("api/v1/idol/msgBoard")
@Controller
public class MsgBoardHandler {
	@Autowired
	@Qualifier("msgBoardService")
	private MsgBoardService msgBoardService;
	public void setMsgBoardService(MsgBoardService msgBoardService) {
		this.msgBoardService = msgBoardService;
	}
	@ResponseBody
	@RequestMapping(value="pushMsgBoard",method=RequestMethod.POST)
	public String pushMsgBoard(HttpServletRequest req,HttpServletResponse res)
	{
		JSONObject obj=new JSONObject();
		if(UserAuth.authUser(req))
		{
			MsgBoard msg=new MsgBoard();
			msg.setContent(req.getParameter("content"));
			msg.setEssayid(req.getParameter("essayid"));
			msg.setSendtime(UtilDateFormat.getFormatDate());
			msg.setSender(UserAuth.getUsername(req));
			msg.setMsgid(EnCoder.md5Encoder((""+new Date().getTime()).getBytes()));
			boolean pushMsgBoard = msgBoardService.pushMsgBoard(msg);
			if(pushMsgBoard)
			{
				obj.put("status", "1");
				obj.put("msg", "发布成功！");
				return obj.toString();
			}
			else
			{
				obj.put("status", "0");
				obj.put("msg", "系统异常，失败！");
				return obj.toString();
			}
		}
		else
		{
			obj.put("status", "-1");
			obj.put("msg", "您未登陆！");
			return obj.toString();
		}
	}
	@ResponseBody
	@RequestMapping(value = "delMsgById",method =RequestMethod.POST)
	public String delMsgById(HttpServletRequest req,HttpServletResponse res)
	{
		JSONObject obj=new JSONObject();
		if(UserAuth.authAmin(req))
		{
			boolean delMsgById = msgBoardService.delMsgById(req.getParameter("msgId"));
			if(delMsgById)
			{
				obj.put("status", "1");
				obj.put("msg", "操作成功！");
				return obj.toString();
			}
			else
			{
				obj.put("status", "0");
				obj.put("msg", "系统异常，失败！");
				return obj.toString();
			}
		}
		else
		{
			obj.put("status", "-1");
			obj.put("msg", "您未登陆！");
			return obj.toString();
		}
	}
	@ResponseBody
	@RequestMapping(value = "getAllMsg",method =RequestMethod.GET)
	public String getAllMsg(HttpServletRequest req,HttpServletResponse res)
	{
		JSONObject obj=new JSONObject();
		if(UserAuth.authAmin(req))
		{
			HashMap<String,Integer> page=new HashMap<String,Integer>();
			int no=Integer.parseInt(req.getParameter("page"));
			int size=Integer.parseInt(req.getParameter("limit"));
			page.put("pageNo", (no-1)*size);
			page.put("pageSize", size);
			List<MsgBoard> allMsg = msgBoardService.getAllMsg(page);
			JSONArray list2json_msgBoard = JSONConvertor.list2json_msgBoard(allMsg);
			obj.put("code", 0);
			obj.put("count", "9999");
			obj.put("data", list2json_msgBoard);
			return obj.toString();
		}
		else
		{
			obj.put("code", 1);
			obj.put("count", "0");
			return obj.toString();
		}
	}
	@ResponseBody
	@RequestMapping(value = "getAllMsgByEssayId",method =RequestMethod.GET)
	public String getAllMsgByEssayId(HttpServletRequest req,HttpServletResponse res)
	{
		JSONObject obj=new JSONObject();
		System.out.println("有点东西");
			HashMap<String,Object> id=new HashMap<String,Object>();
			id.put("pageNo",0);
			id.put("pageSize",10);
			id.put("essayid", req.getParameter("essayid"));
			List<MsgBoard> allMsg = msgBoardService.getAllMsgByEssayId(id);
			JSONArray list2json_msgBoard = JSONConvertor.list2json_msgBoard(allMsg);
			obj.put("code", 0);
			obj.put("count", "9999");
			obj.put("data", list2json_msgBoard);
			System.out.println(obj);
			return obj.toString();
	}
	@ResponseBody
	@RequestMapping(value = "getTenMsg",method =RequestMethod.GET)
	public String getTenMsg(HttpServletRequest req,HttpServletResponse res)
	{
		JSONObject obj=new JSONObject();
		return obj.toString();
	}
}
