/**
 * projectName: IDOL
 * fileName: UserHandler.java
 * pakageName: com.charles.idol.handler
 * date: 2019年12月10日
 * copyright(c): 2019 2020 charles
 */
package com.charles.idol.handler;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.alibaba.fastjson.JSONPObject;
import com.charles.idol.pojo.Essay;
import com.charles.idol.pojo.User;
import com.charles.idol.service.EssayService;
import com.charles.idol.service.UserService;
import com.charles.idol.utils.EnCoder;
import com.charles.idol.utils.JSONConvertor;
import com.charles.idol.utils.UserAuth;
import com.charles.idol.utils.UtilDateFormat;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * @author charles
 * @title UserHandler.java
 * @pakage com.charles.idol.handler
 * @description TODO
 * @author charles
 * @version V1.0
 * @date 2019年12月10日 下午8:52:02
 */
@RequestMapping("api/v1/idol")
@Controller
public class EssayHandler {
	@Autowired
	@Qualifier("essayService")
	private EssayService essayService;
	public void setUserService(EssayService essayService) {
		this.essayService = essayService;
	}
	@ResponseBody
	@RequestMapping(value="getEssayById",method =RequestMethod.GET)
	public String getEssayById(HttpServletRequest req,HttpServletResponse res)
	{
		String essayId=req.getParameter("essayId");
		Essay essayById = essayService.getEssayById(essayId);
		JSONObject parseObject = JSON.parseObject(JSON.toJSONString(essayById));
		JSONObject obj=new JSONObject();
		if(essayById!=null)
		{
			obj.put("status", "1");
			obj.put("essay", parseObject.toString());
		}
		else
		{
			obj.put("status", "0");
		}
		return obj.toString();
	}
	@ResponseBody
	@RequestMapping(value="addEssay",method =RequestMethod.POST)
	public String addEssay(@RequestParam("essay_file") MultipartFile file,HttpServletRequest request,HttpServletResponse response) throws IOException
	{
		JSONObject obj=new JSONObject();
		if(UserAuth.authAmin(request))
		{
			//生成唯一文件名
			String fileName = file.getOriginalFilename() ;
			String suffix = fileName.substring(  fileName.indexOf("."));
			String prefix = EnCoder.md5Encoder((""+new Date().getTime()).getBytes());
			fileName=prefix+suffix;
			String path = request.getSession().getServletContext().getRealPath("imgs")+"\\";
			InputStream input = file.getInputStream() ;
			OutputStream out = new FileOutputStream(path+fileName) ;
			byte[] bs = new byte[1024];
			int len = -1;
			while(( len = input.read(bs)) !=-1 ) {
				out.write(bs, 0, len);
			}
			out.close();
			input.close();
			Essay e=new Essay();
			e.setContent(request.getParameter("essay_content"));
			e.setTitle(request.getParameter("essay_title"));
			e.setType(Integer.parseInt(request.getParameter("essay_type")));
			e.setFrom(request.getParameter("essay_from"));
			e.setEssayid(EnCoder.md5Encoder((""+new Date().getTime()).getBytes()));
			e.setTime(UtilDateFormat.getFormatDate());
			e.setEditor(((User)request.getSession().getAttribute("user")).getUsername());
			e.setImg(fileName);
			boolean flag=essayService.addEssay(e);
			if(flag)
			{
				obj.put("status", "1");
				obj.put("msg", "增加成功！");
				return obj.toString();
			}
			else
			{
				obj.put("status", "-1");
				obj.put("msg", "操作错误！");
				return obj.toString();
			}
		}
		else
		{
			//权限不够或者未登录
			obj.put("status", "0");
			obj.put("msg", "您的权限不足！");
			return obj.toString();
		}
	}
	@ResponseBody
	@RequestMapping(value="getEssayList",method =RequestMethod.GET)
	public String getEssayList(HttpServletRequest request,HttpServletResponse response)
	{
		HashMap<String,Integer> page=new HashMap<String,Integer>();
		int no=Integer.parseInt(request.getParameter("page"));
		int size=Integer.parseInt(request.getParameter("limit"));
		page.put("pageNo", (no-1)*size);
		page.put("pageSize", size);
		JSONObject obj=new JSONObject();
		JSONArray objs=new JSONArray();
		if(UserAuth.authAmin(request))
		{
			List<Essay> essayList = essayService.getEssayList(page);
			JSONArray list2json_essay = JSONConvertor.list2json_essay(essayList);
			obj.put("code", 0);
			obj.put("count", "9999");
			obj.put("data", list2json_essay);
			return obj.toString();
		}
		else
		{
			obj.put("code", 1);
			obj.put("message", "您的权限不足！");
			return obj.toString();
		}
	}
	@ResponseBody
	@RequestMapping(value="delEssayById",method =RequestMethod.POST)
	public String delEssayById(HttpServletRequest request,HttpServletResponse response)
	{
		JSONObject obj=new JSONObject();
		if(UserAuth.authAmin(request))
		{
		String essayId=request.getParameter("essayid");
		boolean flag=essayService.delEssayById(essayId);
			if(flag)
			{
				obj.put("status", "1");
				obj.put("msg", "删除成功！");
				return obj.toString();
			}
			else
			{
				obj.put("status", "-1");
				obj.put("msg", "删除失败，系统错误！");
				return obj.toString();
			}
		}
		else
		{
			obj.put("status", "0");
			obj.put("msg", "删除失败！");
			return obj.toString();
		}
	}
	
	@ResponseBody
	@RequestMapping(value="updateEssayContentById",method =RequestMethod.POST)
	public String updateEssayContentById(HttpServletRequest request,HttpServletResponse response)
	{
		JSONObject obj=new JSONObject();
		HashMap<String,String> essay=new HashMap<String,String>();
		if(UserAuth.authAmin(request))
		{
		String essayid=request.getParameter("essayid");
		String content=request.getParameter("content");
		essay.put("essayid", essayid);
		essay.put("content", content);
		boolean flag=essayService.updateEssayContentById(essay);
			if(flag)
			{
				obj.put("status", "1");
				obj.put("msg", "修改成功！");
				return obj.toString();
			}
			else
			{
				obj.put("status", "-1");
				obj.put("msg", "修改失败，系统错误！");
				return obj.toString();
			}
		}
		else
		{
			obj.put("status", "0");
			obj.put("msg", "修改失败！");
			return obj.toString();
		}
	}
	//主页的数据
	@ResponseBody
	@RequestMapping(value="getEssay",method =RequestMethod.GET)
	public String getEssay(HttpServletRequest request,HttpServletResponse response) throws IOException
	{
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		int no=Integer.parseInt(request.getParameter("page"));
		int type=9;
		try {
			type=Integer.parseInt(request.getParameter("type"));
		}catch(Exception e)
		{
			//do nothing
		}
		if(type==9)
		{
			HashMap<String,Integer> page=new HashMap<String,Integer>();
			page.put("pageNo", (no-1)*9);
			page.put("pageSize",9);
			JSONObject obj=new JSONObject();
			JSONArray objs=new JSONArray();
				List<Essay> essayList = essayService.getEssayList(page);
				JSONArray list2json_essay = JSONConvertor.list2json_essay_decrease(essayList);
				obj.put("code", 0);
				obj.put("essay", list2json_essay);
				response.getWriter().write(obj.toString());
				return null;
		}
		else
		{
			HashMap<String,Object> page=new HashMap<String,Object>();
			page.put("pageNo", (no-1)*9);
			page.put("pageSize",9);
			page.put("type",type);
			JSONObject obj=new JSONObject();
			JSONArray objs=new JSONArray();
				List<Essay> essayList = essayService.getEssayByType(page);
				JSONArray list2json_essay = JSONConvertor.list2json_essay_decrease(essayList);
				obj.put("code", 0);
				obj.put("essay", list2json_essay);
				response.getWriter().write(obj.toString());
				return null;
		}
	}
	//文章的数据
	@ResponseBody
	@RequestMapping(value="getEssayContentById",method =RequestMethod.GET)
	public String getEssayContent(@RequestParam("essayid") String essayid) throws IOException
	{
		if(!"".equals(essayid)&&essayid!=null)
		{
			JSONObject obj=new JSONObject();
			Essay essayContentById = essayService.getEssayContentById(essayid);
			JSONObject parseObject = JSON.parseObject(JSON.toJSONString(essayContentById));
			/*
			<option value="0">科技榜</option>
	        <option value="1">爱国榜</option>
	        <option value="2">学习榜</option>
	        <option value="3">敬业榜</option>
	        <option value="4">光荣榜</option>
	        <option value="5">富强榜</option>
			 * */
			switch(essayContentById.getType())
			{
			case 0:parseObject.put("type", "科技榜");break;
			case 1:parseObject.put("type", "爱国榜");break;
			case 2:parseObject.put("type", "学习榜");break;
			case 3:parseObject.put("type", "敬业榜");break;
			case 4:parseObject.put("type", "光荣榜");break;
			case 5:parseObject.put("type", "富强榜");break;
			}
			return parseObject.toString();
		}
		else
		{
			return "{\"code\":0}";
		}
	}
}
