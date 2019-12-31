function logout()
{
	var url_logout='api/v1/idol/userLogout';
	 $.ajax({
	        url: url_logout
	        , async:false
	        , type: "POST"
	        ,success: function(data){
                  var ret=JSON.parse(data);
                  if(ret.islogout==1)
                 	 {
                	  layer.msg("注销成功！");
                	  //window.location.href = "http://localhost:8080/IDOL";
                	  loadUserInfo();
                 	 }
                  else if(ret.islogout==0)
                 	 {
                 	 layer.msg("注销失败！");
                 	//window.location.href = "http://localhost:8080/IDOL/index.html";
                 	 }
               }
	    });
}