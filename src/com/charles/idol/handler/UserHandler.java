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
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONPObject;
import com.charles.idol.pojo.User;
import com.charles.idol.service.NoticeService;
import com.charles.idol.service.UserService;
import com.charles.idol.utils.EnCoder;
import com.charles.idol.utils.JSONConvertor;
import com.charles.idol.utils.UserAuth;
import com.charles.idol.utils.UtilDateFormat;
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
public class UserHandler {
	@Autowired
	@Qualifier("userService")
	private UserService userService;
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	@ResponseBody
	@RequestMapping(value="registerHandler",method = RequestMethod.POST)
	public String registerHandler(HttpServletRequest request,HttpServletResponse response) throws IOException
	{
		//数据处理方式，相对于复杂，不使用
		/*InputStreamReader reader=new InputStreamReader(request.getInputStream(),"UTF-8");
		char [] buff=new char[1024];
		int length=0;
		String data="";
		while((length=reader.read(buff))!=-1){
		     String x=new String(buff,0,length);
		     data+=x;
		}
		System.out.println("register...");
		System.out.println(data);
		return "ok";*/
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		
		User user=new User();
		user.setUsername(request.getParameter("username"));
		user.setPassword(EnCoder.md5Encoder(request.getParameter("password").getBytes()));
		user.setPlace(request.getParameter("place"));
		user.setEmail(request.getParameter("email"));
		user.setBirth(request.getParameter("birth"));
		user.setPower(1);
		user.setProfile("default.jpg");
		user.setRegdate(UtilDateFormat.getFormatDate());
		System.out.println(user);
		
		String checkcodeServer=((String) request.getSession().getAttribute("CKECKCODE")).toLowerCase();
		String checkcodeClient=(String)request.getParameter("validatecode").toLowerCase();
		
		boolean flag=false;
		if(checkcodeServer.equals(checkcodeClient))
		{
			flag=userService.register(user);
		}
		else
		{
			flag=false;
		}
		JSONObject obj = new JSONObject();
		if(flag)
		{
			obj.put("status", "1");
		}
		else
		{
			obj.put("status", "0");
		}
		return obj.toString();
	}
	@ResponseBody
	@RequestMapping(value="userLogin",method=RequestMethod.POST)
	public String userLogin(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException
	{
		String name=request.getParameter("username");
		String password=request.getParameter("password");
		String validatecode=request.getParameter("validatecode");
		JSONObject obj = new JSONObject();
		User user=new User();
		user.setUsername(name);
		user.setPassword(EnCoder.md5Encoder(password.getBytes()));
		System.out.println(user);
		String checkcodeServer=((String) request.getSession().getAttribute("CKECKCODE")).toLowerCase();
		
		if(checkcodeServer.equals(validatecode.toLowerCase()))
		{
			user=userService.userLogin(user);
			if(user==null)
			{
				obj.put("status", "1");
			}
			else if(user.getPower()==0)
			{
				obj.put("status", "3");
			}
			else
			{
				obj.put("status", "4");
				request.getSession().setAttribute("user", user);
			}
		}
		else
		{
			obj.put("status", "2");
		}
		return obj.toString();
	}
	@ResponseBody
	@RequestMapping(value="loadUserInfo",method=RequestMethod.GET)
	public String loadUserInfo(HttpServletRequest request,HttpServletResponse response)
	{
		JSONObject obj = new JSONObject();
		User user=(User) request.getSession().getAttribute("user");
		if(user!=null)
		{
			obj.put("username", user.getUsername());
			obj.put("profile", user.getProfile());
			obj.put("islogin", "1");
			obj.put("email",user.getEmail());
			obj.put("birth", user.getBirth());
			obj.put("place",user.getPlace());
			obj.put("regdate",user.getRegdate());
			obj.put("power", user.getPower());
		}
		else
		{
			obj.put("username","您未登陆");
			obj.put("profile", "default.jpg");
			obj.put("islogin", "0");
			obj.put("power", 0);
		}
		return obj.toString();
	}
	@ResponseBody
	@RequestMapping(value="userLogout",method = RequestMethod.POST)
	public String userLogout(HttpServletRequest request,HttpServletResponse response)
	{
		JSONObject obj = new JSONObject();
		User user=(User)request.getSession().getAttribute("user");
		System.out.println("logout....");
		if(user!=null)
		{
			obj.put("islogout", "1");
			request.getSession().removeAttribute("user");
		}
		else
		{
			obj.put("islogout", "0");
		}
		return obj.toString();
	}
	@ResponseBody
	@RequestMapping(value="getUserList",method = RequestMethod.GET)
	public String getUserList(HttpServletRequest request,HttpServletResponse response)
	{
		JSONObject obj = new JSONObject();
		JSONArray objs=new JSONArray();
		HashMap<String,Integer> page=new HashMap<String,Integer>();
		int no=Integer.parseInt(request.getParameter("page"));
		int size=Integer.parseInt(request.getParameter("limit"));
		page.put("pageNo", (no-1)*size);
		page.put("pageSize", size);
		if(UserAuth.authAmin(request))
		{
			List<User> userList = userService.getUserList(page);
			JSONArray list2json_user = JSONConvertor.list2json_user(userList);
			obj.put("code", 0);
			obj.put("data", list2json_user);
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
	@RequestMapping(value="updatePower",method = RequestMethod.POST)
	public String updatePower(HttpServletRequest request,HttpServletResponse response)
	{
		JSONObject obj = new JSONObject();
		HashMap<String,Object> power=new HashMap<String,Object>();
		String functionalState=request.getParameter("operate");
		String username=request.getParameter("username");
		boolean flag=false;
		if(UserAuth.authAmin(request))
		{
			if("disable".equals(functionalState))
			{
				power.put("power", 0);
				power.put("username",username);
				flag=userService.updatePower(power);
			}
			else if("enable".equals(functionalState))
			{
				power.put("power", 1);
				power.put("username",username);
				flag=userService.updatePower(power);
			}
			else//有人瞎搞，直接封号
			{
				power.put("power", 0);
				power.put("username",username);
				flag=userService.updatePower(power);
			}
			if(flag)
			{
				obj.put("status", "1");
				obj.put("msg", "操作成功！");
				return obj.toString();
			}
			else
			{
				obj.put("status", "0");
				obj.put("msg", "异常错误！");
				return obj.toString();
			}

		}
		else
		{
			obj.put("status","0");
			obj.put("msg","您未登录！");
			return obj.toString();
		}
		
	}
	@ResponseBody
	@RequestMapping(value="addAdmin",method = RequestMethod.POST)
	public String addAdmin(HttpServletRequest request,HttpServletResponse response)
	{
		JSONObject obj = new JSONObject();
		HashMap<String,Object> power=new HashMap<String,Object>();
		//String functionalState=request.getParameter("operate");
		String username=request.getParameter("username");
		boolean flag=false;
		if(UserAuth.authAmin(request))
		{
			power.put("power", 2);
			power.put("username",username);
			flag=userService.updatePower(power);
			if(flag)
			{
				obj.put("status", "1");
				obj.put("msg", "操作成功！");
				return obj.toString();
			}
			else
			{
				obj.put("status", "0");
				obj.put("msg", "异常错误！");
				return obj.toString();
			}

		}
		else
		{
			obj.put("status","0");
			obj.put("msg","您未登录！");
			return obj.toString();
		}
	}
	@ResponseBody
	@RequestMapping(value="updatePassoword",method = RequestMethod.POST)
	public String updatePassoword(HttpServletRequest request,HttpServletResponse response)
	{
		JSONObject obj = new JSONObject();
		HashMap<String,String> pass=new HashMap<String,String>();
		//String functionalState=request.getParameter("operate");
		String password=request.getParameter("password");
		if(password.length()<8||password==null)
		{
			obj.put("status", "0");
			obj.put("msg", "密码格式错误！");
			return obj.toString();
		}
		boolean flag=false;
		if(UserAuth.authUser(request))
		{
			String username=UserAuth.getUsername(request);
			pass.put("username", username);
			pass.put("password",EnCoder.md5Encoder(password.getBytes()));
			System.out.println(pass);
			flag=userService.updatePassoword(pass);
			if(flag)
			{
				obj.put("status", "1");
				obj.put("msg", "操作成功！");
				return obj.toString();
			}
			else
			{
				obj.put("status", "0");
				obj.put("msg", "异常错误！");
				return obj.toString();
			}

		}
		else
		{
			obj.put("status","0");
			obj.put("msg","您未登录！");
			return obj.toString();
		}
	}
	@ResponseBody
	@RequestMapping(value="searchUser",method = RequestMethod.GET)
	public String searchUser(HttpServletRequest request,HttpServletResponse response)
	{
		JSONObject obj = new JSONObject();
		JSONArray objs=new JSONArray();
		HashMap<String,Object> info=new HashMap<String,Object>();
		int no=Integer.parseInt(request.getParameter("page"));
		int size=Integer.parseInt(request.getParameter("limit"));
		String searchInfo=request.getParameter("userinfo");
		info.put("pageNo", (no-1)*size);
		info.put("pageSize", size);
		info.put("email","%"+searchInfo+"%");
		info.put("username","%"+searchInfo+"%");
		info.put("place","%"+searchInfo+"%");
		if(UserAuth.authAmin(request))
		{
			List<User> userList = userService.searchUser(info);
			JSONArray list2json_user = JSONConvertor.list2json_user(userList);
			obj.put("code", 0);
			obj.put("data", list2json_user);
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
	@RequestMapping(value="userProfileUpdate",method = RequestMethod.POST)
	public String userProfileUpdate(@RequestParam("file")MultipartFile file,HttpServletRequest request,HttpServletResponse response) throws Exception
	{
		 JSONObject res = new JSONObject();
         JSONObject resUrl = new JSONObject();
		if(UserAuth.authUser(request))
		{
		 String username=UserAuth.getUsername(request);
         //上传文件路径 
         String path =request.getSession().getServletContext().getRealPath("imgs")+"\\";
         System.out.println("文件名称"+file.getOriginalFilename()); 
         //上传文件名         
         String name = file.getOriginalFilename();//上传文件的真实名称
         String suffixName = name.substring(name.lastIndexOf("."));//获取后缀名
         String hash = Integer.toHexString(new Random().nextInt());//自定义随机数（字母+数字）作为文件名
         String fileName = hash + suffixName;  
         HashMap<String,String> profile=new HashMap<String,String>();
//         userService
         profile.put("username", username);
         profile.put("profile", fileName);
         boolean userProfileUpdate = userService.userProfileUpdate(profile);
         if(userProfileUpdate)
         {
         File filepath = new File(path, fileName); 
        // System.out.println("随机数文件名称"+filepath.getName()); 
         //判断路径是否存在，没有就创建一个 
         if (!filepath.getParentFile().exists()) { 
             filepath.getParentFile().mkdirs(); 
             } 
         //将上传文件保存到一个目标文档中 
         File tempFile = new File(path + fileName);
         file.transferTo(tempFile);
         
         resUrl.put("src", tempFile.getPath());
         res.put("code", 0);
         res.put("msg", "上传成功！");
         res.put("data", resUrl);
         //str = "{\"code\": 0,\"msg\": \"上传成功\",\"data\": {\"src\":\""+path+fileName + "\"}}";
        // System.out.println("res里面的值：");
         System.out.println(res.toString());    
         return res.toString();
         }
         else
         {
			 res.put("code", 0);
	         res.put("msg", "上传失败！");
	         res.put("data", null);
	         return res.toString();
         }
		}
		else
		{
			 res.put("code", 0);
	         res.put("msg", "上传失败！");
	         res.put("data", null);
	         return res.toString();
		}
	}
	@ResponseBody
	@RequestMapping(value="updateUserInfoHandler",method = RequestMethod.POST)
	public String updateUserInfoHandler(HttpServletRequest request,HttpServletResponse response)
	{
		System.out.println("来了老弟");
		JSONObject obj = new JSONObject();
		HashMap<String,String> uinfo=new HashMap<String,String>();
		String checkcodeServer=((String) request.getSession().getAttribute("CKECKCODE")).toLowerCase();
		String checkcodeClient=(String)request.getParameter("validatecode").toLowerCase();
		if(!checkcodeServer.equals(checkcodeClient))
		{
			obj.put("code","-1");
			obj.put("msg","验证码错误！");
			return obj.toString();
		}
		else if(UserAuth.authUser(request))
		{
			String username=UserAuth.getUsername(request);
			uinfo.put("email", request.getParameter("email"));
			uinfo.put("birth", request.getParameter("birth"));
			uinfo.put("place", request.getParameter("place"));
			uinfo.put("username", username);
			boolean updateUserInfoHandler = userService.updateUserInfoHandler(uinfo);
			if(updateUserInfoHandler)
			{
				obj.put("code","1");
				obj.put("msg","修改成功！");
				return obj.toString();
			}
			else
			{
				obj.put("code","-2");
				obj.put("msg","系统异常，修改失败！");
				return obj.toString();
			}
		}
		else
		{
			obj.put("code","0");
			obj.put("msg","您未登录！");
		}
	
		return obj.toString();
	}
	//
	@ResponseBody
	@RequestMapping(value="test1",method = RequestMethod.GET)
	public void test1(HttpServletRequest request,HttpServletResponse response) throws IOException
	{
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.getWriter().write("yes55");
	}
}
