function reloadCheckImg() {
	    	$("#validateCode").attr(
	    			"src",
	    			"CheckCodeImg.jsp?"
	    					+ (new Date().getTime()));
	    }
$(document).ready(function(){
	//含用户名、密码、确认密码、email、生日、用户所在地
	var url_reg="api/v1/idol/registerHandler";
	var url_validatecode="api/v1/idol/validataCodeVerify";
	var name;
	var passwd;
	var passwd_again;
	var email;
	var birth;
	var place; 	
	var validatecode;
	
	var bt_flag=false;
	$("#username").blur(function(){
				name=$("#username").val();
				if(name.length>8||name.length<3)
					{
					layer.msg('用户名需在3-8位之间！'); 
					bt_flag=false;
					}
				/*else
					{
					bt_flag=true;
					}*/
			});
  $("#password").blur(function(){
	  passwd= $("#password").val();
	   if(passwd.length<8)
		   {
		   layer.msg('密码长度过短');
		   bt_flag=false;
		   }
	   else if(passwd=='12345678')
		   {
		   layer.msg('密码强度低！');
		   bt_flag=false;
		   }
	  /* else
			{
			bt_flag=true;
			}*/
	  });
  $("#password1").blur(function(){
	  passwd_again=$("#password1").val();
	  if(passwd_again!=passwd)
		  {
		  layer.msg("两次密码不一致！");
		  bt_flag=false;
		  }
	  	/*else
			{
			bt_flag=true;
			}*/
	  });
  $("#email").blur(function(){
	  email= $("#email").val();
	  var email = this.value;
		var reg = /^([a-zA-Z]|[0-9])(\w|\-)+@[a-zA-Z0-9]+\.([a-zA-Z]{2,4})$/;
		if(reg.test(email)){
			layer.msg("邮箱格式正确");
		}else{
			layer.msg("邮箱格式不正确");
		}
	  });
  $("#birth").blur(function(){
	  birth= $("#birth").val();
	  });
  $("#addr").blur(function(){
	  place= $("#addr").val();
	   if(place.length<4||place.length>30)
		   {
		   layer.msg("地址不符合规范！");
		   bt_flag=false;
		   }
	  /* else
			{
			bt_flag=true;
			}*/
	  });
  $("#validatecode").blur(function(){
	  validatecode=$("#validatecode").val();
		$.post(url_validatecode,
				"checkcode=" + validatecode,
				function(result) {
					var resultHtml = $("<img src='" + result
					+ "' height='15' width='15px'   />");
					$("#tip1").html(resultHtml);
					if("imgs/right.jpg"!=result)
						{
						bt_flag=false;
						$("#register_btn").attr("class","layui-btn layui-btn-disabled");
						}
					else
						{
						bt_flag=true;
						$("#register_btn").attr("class","layui-btn");
						}
				});
	  });
  		/*if(bt_flag==true)
  			{
  			$("#register_btn").attr("class","layui-btn");
  			}*/
	});
