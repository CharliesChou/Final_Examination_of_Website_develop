function loadAdminInfo()
{
	var url_loadingUinfo='api/v1/idol/loadUserInfo';
	var username;
	var pfofile;
	$.ajax({
	  	url:url_loadingUinfo
        , async:false
        , type: "GET"
        ,success: function(data){
               var ret=JSON.parse(data);
               username=ret.username;
               profile=ret.profile;
               if(ret.islogin==1)
            	   {
                   $("#admin_name").html(username);
                   $("#admin_profile").attr("src","./imgs/"+profile);
            	    if(ret.power!=2)
            	    	{
            	    	window.location.href = "http://localhost:8080/IDOL";
            	    	}
            	   }
               else
            	   {
            	   window.location.href = "http://localhost:8080/IDOL";
            	   }
            }
    });
	//checkEssay();
	addEssay();
}
function addAdmin()
{
	var url_addAdmin='api/v1/idol/addAdmin';
	layer.prompt({
		  formType: 2,
		  value: '请输入已注册的会员名！',
		  title: '注册管理员',
		  area: ['190px', '90px'] //自定义文本域宽高
		}, function(value, index, elem){
			var data1={
					username:value
			};
			$.ajax({
				url: url_addAdmin,
				async:false, 
				type: 'POST',
				data: data1, 
				datatype: "json",
				success: function(data){
		            var ret=JSON.parse(data);
		            layer.msg(ret.msg);
		         }
			});
		  layer.close(index);
		}); 
}
function updatePassoword()
{
	var url_updatePassoword='api/v1/idol/updatePassoword';
	layer.prompt({
		  formType: 2,
		  value: '密码必须大于8位，并且牢记！',
		  title: '更换密码',
		  area: ['190px', '45px'] //自定义文本域宽高
		}, function(value, index, elem){
			var data1={
					password:value
			};
			// alert('您的新密码是【'+value+'】！请牢记！');
			$.ajax({
				url: url_updatePassoword,
				async:false, 
				type: 'POST',
				data: data1, 
				datatype: "json",
				success: function(data){
		            var ret=JSON.parse(data);
		            alert('您的新密码是【'+value+'】！请牢记！');
		            layer.msg(ret.msg);
		         }
			});
		  layer.close(index);
		});
}