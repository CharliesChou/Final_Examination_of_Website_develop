function reg()
{	
	var url_reg='api/v1/idol/registerHandler';
	var name;
	var passwd;
	var passwd_again;
	var email;
	var birth;
	var place;
	var validatecode;
	name=$("#username").val();
	passwd=$("#password").val();
	email= $("#email").val();
	birth= $("#birth").val();
	place= $("#addr").val();
	validatecode=$("#validatecode").val();
	var data={
			username:name
			,password:passwd
			,email:email
			,birth:birth
			,place:place
			,validatecode:validatecode
			};
	  $.ajax({
	        url: url_reg
	        , async:false
	        , type: "POST"
	        , data:data
	        , datatype: "json"
	        ,success: function(data){
                     var ret=JSON.parse(data);
                     if(ret.status==1)
                    	 {
                    	 alert("注册成功");
                    	 window.location.href = "http://localhost:8080/IDOL";
                    	 }
                     else if(ret.status==0)
                    	 {
                    	 layer.msg("注册失败");
                    	//window.location.href = "http://localhost:8080/IDOL/index.html";
                    	 }
                  }
	    });
	/*  alert(data);
	$.post(url_reg,// 服务端地址
			data,
			function(result) {
				alert(result+"666");
			});
	 alert(JSON.stringify(data.field));
	var xmlhttp=new XMLHttpRequest();
	xmlhttp.onreadystatechange=function(){
		 if (xmlhttp.readyState==4 && xmlhttp.status==200)
		    {
			 var result=JSON.parse(xmlhttp.responseText);
			 layer.msg(result.status);
		    }
	}
	xmlhttp.open("POST",url_reg,true);
	xmlhttp.send(data);*/
}