function login(){
	var url_login='api/v1/idol/userLogin';
	var name=$("#username").val();
	var pwd=$("#password").val();
	var validatecode=$("#validatecode").val();
	var ret;
	var data={
			username:name
			,password:pwd
			,validatecode:validatecode
	};
	  $.ajax({
		  	url:url_login
	        , async:false
	        , type: "POST"
	        , data:data
	        , datatype: "json"
	        ,success: function(data)
	        {
                   ret=JSON.parse(data);
            }
	    });
	  if(ret.status==1)
			{
      	   layer.msg("用户名密码有误！");
      	  // window.location.href = "http://localhost:8080/IDOL/index.html";
			}
		else if(ret.status==2)
			{
				layer.msg("验证码错误！");
			//	window.location.href = "http://localhost:8080/IDOL/index.html";
			}
		else if(ret.status==3)
			{
				layer.msg("登录失败，账号异常！");
			}
		else if(ret.status==4)
			{
			//alert("登陆成功！");
			window.location.href = "http://localhost:8080/IDOL";
			}
		else
			{
			layer.msg("未知错误");
			}
}
