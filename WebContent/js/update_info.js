function updateInfo()
{
	var url_loadingUinfo='api/v1/idol/loadUserInfo';
	var username;
	var profile;
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
               email=ret.email;
               profile=ret.profile;
               if(ret.islogin==1)
            	   {
            	   $("#username").val(username);
                   $("#user_profile").attr("src","./imgs/"+profile);
                   $("#email").val(email);
                   $("#birth").val(birth);
                   $("#addr").val(place);
            	   }
               else
            	   {
            	   	layer.msg("您未登陆！！");
            	   }
            }
    });
	loadPlugin_layui();
}
function loadPlugin_layui()
{
	var formData = new FormData();
	var url_userProfileUpdate='api/v1/idol/userProfileUpdate';
	layui.use('upload', function(){
		  var upload = layui.upload;
		  //执行实例
		  var uploadInst = upload.render({
		    elem: '#test1' //绑定元素
		    ,url: url_userProfileUpdate //上传接口
		    ,data:formData //可选项。额外的参数，如：{id: 123, abc: 'xxx'}
		    ,before: function(obj){
		        //预读本地文件示例，不支持ie8
		        obj.preview(function(index, file, result){
		          $('#user_profile').attr('src', result); //图片链接（base64）
		        });
		      }
		    ,done: function(res){
		    	alert(res.msg);
		    }
		    ,error: function(){
		      //请求异常回调
		    }
		  });
		});
}
function update()
{
	var url_updateUserInfoHandler='api/v1/idol/updateUserInfoHandler';
	var email;
	var birth;
	var place;
	var validatecode;
	email= $("#email").val();
	birth= $("#birth").val();
	place= $("#addr").val();
	validatecode=$("#validatecode").val();
	var data={
			email:email
			,birth:birth
			,place:place
			,validatecode:validatecode
			};
	  $.ajax({
	        url: url_updateUserInfoHandler
	        , async:false
	        , type: "POST"
	        , data:data
	        , datatype: "json"
	        ,success: function(data){
                     var ret=JSON.parse(data);
                     if(ret.code==1)
                    	 {
                    	 layer.msg("更新成功！");
                    	 }
                     else if(ret.code==0)
                    	 {
                    	 layer.msg("更新失败");
                    	 }
                  }
	    });
}