function loadUserInfo()
{
	loadImgPerfomance();
	loadPlugin();
	var type=get_url_para_TYPE();
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
               $("#user_name").html(username);
               $("#user_profile").attr("src","./imgs/"+profile);
               if(ret.islogin==1)
            	   {
            	   	$("#login-nav").hide();
            	    $("#user-option-child").html(
              			  '<dd><a href="update_info.html;">修改信息</a></dd>'
              			 + '<dd><a href="javascript:updatePassoword();">安全管理</a></dd>'
              			 + '<dd><a href="javascript:logout();">退了</a></dd>'
              	  		);
            	    if(ret.power!=2)
            	    	{
            	    	$("#admin_access").hide();
            	    	}
            	   }
               else
            	   {
            	   $("#login-nav").show();
            	   $("#user-option-child").html('<dd><a href="login.html">请先登陆</a></dd>');
            	   $("#personal-manage").hide();
            	   $("#admin_access").hide();
            	   }
               load_essayAndNotice_vue(""+type);//9代表全部，其余数字代表分类
            }
    });
}
function load_essayAndNotice_vue(type)
{
	 new Vue({
   	  el: '#app',
   	  data : {
         	page:{page:1},
         	user:{username:"",profile:""},
         	essays: "", 
         	notices: "",
         },
         mounted () {
   			 _that=this;
        		axios.get("api/v1/idol/getEssay?page="+(_that.page.page)+"&type="+type,this.user).then(function(response){
        			_that.essays=response.data.essay;
        			//alert(_that.essays[0]);
        			});
          		axios.get("api/v1/idol/notice/getAllNotice_index",this.user).then(function(response){
          			_that.notices=response.data.notice;
          			});
   	  }
   	});
}
//主页图片轮播
function loadImgPerfomance()
{
	layui.use('carousel', function(){
		  var carousel = layui.carousel;
		  //建造实例
		  carousel.render({ 
		    elem: '#test1'
		    ,width: '100%' //设置容器宽度
		  });
		});
}
//导航的hover效果、二级菜单等功能，需要依赖element模块
function loadPlugin()
{
	layui.use('element', function(){
		  var element = layui.element; 
		  //监听导航点击
		  element.on('nav(demo)', function(elem){
		    //console.log(elem)
		    layer.msg(elem.text());
		  });
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
function different_types(type)
{
	switch(type)
	{
	case "0":window.location.href='index.html?type=0';break;
	case "1":window.location.href='index.html?type=1';break;
	case "2":window.location.href='index.html?type=2';break;
	case "3":window.location.href='index.html?type=3';break;
	case "4":window.location.href='index.html?type=4';break;
	case "5":window.location.href='index.html?type=5';break;
	}
}
function get_url_para_TYPE()
{
	   var variable='type';
       var query = window.location.search.substring(1);
       var vars = query.split("&");
       for (var i=0;i<vars.length;i++)
       {
               var pair = vars[i].split("=");
               if(pair[0] == variable){
            	   return pair[1];
            	   }
       }
       return(false);
}
