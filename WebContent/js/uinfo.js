function loadUserInfo()
{
	var url_loadingUinfo='api/v1/idol/loadUserInfo';
	var username;
	var profile;
	var power;
	var regdate;
	var place;
	var birth;
	var email;
	$.ajax({
	  	url:url_loadingUinfo
        , async:false
        , type: "GET"
        ,success: function(data){
               var ret=JSON.parse(data);
               username=ret.username;
               profile=ret.profile;
               email=ret.email;
               birth=ret.birth;
               regdate=ret.regdate;
               place=ret.place;
               power=ret.power;
               email=ret.email;
               profile=ret.profile;
               if(ret.islogin==1)
            	   {
            	   $("#user_name").html('欢迎您：'+username);
                   $("#user_profile").attr("src","./imgs/"+profile);
                   $("#user_email").html(email);
                   $("#user_birth").html(birth);
                   $("#user_regdate").html(regdate);
                   $("#user_place").html(place);
                   switch(power)
                   {
                   case 0: $("#user_type").html('该账户已禁用');break;
                   case 1: $("#user_type").html('普通会员');break;
                   case 2: $("#user_type").html('管理员');break;
                   }
            	   }
               else
            	   {
            	   	layer.msg("您未登陆！！");
            	   }
            }
    });
}