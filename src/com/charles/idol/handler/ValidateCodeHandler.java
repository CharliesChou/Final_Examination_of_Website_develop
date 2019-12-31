package com.charles.idol.handler;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.charles.idol.service.UserService;
@RequestMapping("api/v1/idol")
@Controller
public class ValidateCodeHandler {
	@Autowired
	@Qualifier("userService")
	private UserService userService;
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	@ResponseBody
	@RequestMapping(value="validataCodeVerify",method=RequestMethod.POST)
	public String validateCodeVerify(HttpServletRequest request,HttpServletResponse response) throws IOException
	{
		String resultTip = "imgs/wrong.jpg";
        //获取用户输入验证码
       String checkcodeClient = request.getParameter("checkcode").toLowerCase();
       //真实的验证码值
       String checkcodeServer = ((String) request.getSession().getAttribute("CKECKCODE")).toLowerCase();
       if(checkcodeServer.equals(checkcodeClient)){
           resultTip = "imgs/right.jpg";
       }
       response.setContentType("text/html;charset=UTF-8");
       return resultTip;
	}

}
