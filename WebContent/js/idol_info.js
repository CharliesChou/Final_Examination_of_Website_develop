function getQueryVariable()
{
	//加载用户信息，index里复制过来的
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
              			   '<dd><a href="javascript:;">修改信息</a></dd>'
              			 + '<dd><a href="javascript:;">安全管理</a></dd>'
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
            }
    });
	loadPlugin_banner();
	loadPlugin_imgPerfomance();
	load_vue();
	//取得essayid,菜鸟网学的
	   var variable='essayid';
       var query = window.location.search.substring(1);
       var vars = query.split("&");
       for (var i=0;i<vars.length;i++)
       {
               var pair = vars[i].split("=");
             //  根据id获取文章，加载弹幕，
               if(pair[0] == variable){
            	   load_msgBoard(pair[1]);
            	   loadPlugin_msgBoard(pair[1]);
            	   load_essay(pair[1]);
            	   return(true);
            	   }
       }
       return(false);
}
//根据essayid获取文章信息
function load_essay(source)
{
	var url_getEssayContentById='api/v1/idol/getEssayContentById?essayid='+source;
	  $.ajax({
		  	url:url_getEssayContentById
	        , async:false
	        , type: "GET"
	        ,success: function(data)
	        {
                 ret=JSON.parse(data);
                 $("#title_essay").html(ret.title);
                 $("#time_essay").html('发布时间：'+ret.time);
                 $("#source_essay").html('文章类型： [     '+ret.from+'    ] ');
                 $("#content_essay").html(ret.content);
                 $("#editor_essay").html('编辑作者：'+ret.editor);
                 $("#essay_img").attr("src","./imgs/"+ret.img);
                 $("#essay_img").attr("alt",ret.title);
                 $("#user_location").html("【"+ret.type+"】");
          }
	    });
}
function loadPlugin_banner()//导航的hover效果
{
	layui.use('element', function(){
		  var element = layui.element; //导航的hover效果、二级菜单等功能，需要依赖element模块
		  //监听导航点击
		  element.on('nav(demo)', function(elem){
		    //console.log(elem)
		    layer.msg(elem.text());
		  });
		});
}
function loadPlugin_imgPerfomance(){ //轮播图
	layui.use('carousel', function(){
		  var carousel = layui.carousel;
		  //建造实例
		  carousel.render({ 
		    elem: '#test1'
		    ,width: '100%' //设置容器宽度
		  });
		});
}
function loadPlugin_msgBoard(essayid)//弹幕板
{
	 $(function () {
	        $(".barrager").barrager()
	    });
	    (function () {
	        var Barrager = function (ele,options) {
	            var defaults = {
	                color:["#ff9999","#35d2f4","#9ee353","#9d77ff","#4785d9","#ff9333","#5bdea8","#51befc"],
	                wrap:ele
	            };
	            this.settings = $.extend({},defaults,options||{});
	            this._init();
	            this.bindEven();
	        };
	        Barrager.prototype = {
	            _init:function () {
	                var item = $(this.settings.wrap).find("div");
	                for(var i = 0;i<item.length;i++){
	                    item.eq(i).css({
	                        top:this.getReandomTop()+"px",
	                        color:this.getReandomColor(),
	                        fontSize:this.getReandomSize()+"px"
	                    });
	                    item.eq(i).css({
	                        right:-item.eq(i).width()
	                    })
	                }
	                this.randomTime(0);
	            },
	            bindEven:function () {
	                var _this = this;
	                $(".addBarrager .submit").on('click',function () {
	                    _this._click(_this);
	                });
	            },
	            getReandomColor:function () {
	                var max = this.settings.color.length;
	                var randomNum = Math.floor(Math.random()*max);
	                return this.settings.color[randomNum];
	            },
	            getReandomTop:function () {
	                var top = (Math.random()*450).toFixed(1);
	                return top;
	            },
	            getReandomSize:function () {
	                var size = (12+Math.random()*28);
	                return size;
	            },
	            getReandomTime:function () {
	                var time = Math.floor((8+Math.random()*10));
	                return time*1000;
	            },
	            randomTime:function (n) {
	                var obj = $(this.settings.wrap).find("div");
	                var _this = this;
	                var len = obj.length;
	                if(n>=len){
	                    n=0;
	                }
	                setTimeout(function () {
	                    n++;
	                    _this.randomTime(n)
	                },1000);
	                var item = obj.eq(n),_w = item.outerWidth(!0);
	                item.animate({
	                    left:-_w
	                },_this.getReandomTime(),"linear",function () {
	                    item.css({right:-_w,left:""});
	                    _this.randomTime(n)
	                });
	            },
	            _click:function (obj) {
	                var _this = obj;
	                var _val = $(".barVal");
	                if(_val.val() == ""){
	                    alert("发布您的看法！");
	                    return false;
	                }
	                $(_this.settings.wrap).append("<div><span class='new'>"+_val.val()+"</span></div>");
	                pushMsg(_val.val(),essayid);
	                _this._init();
	                _val.val("");
	                
	            }
	        };
	        $.fn.barrager = function (opt) {
	            var bger = new Barrager(this,opt);
	        }
	    })(jQuery);
}
function load_vue()
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
       		this.message=this.user.username+" "+this.user.userpassword;   		
       		axios.get("api/v1/idol/getEssay?page="+(_that.page.page),this.user).then(function(response){
       			_that.essays=response.data.essay;
       			});
         		axios.get("api/v1/idol/notice/getAllNotice_index",this.user).then(function(response){
         			_that.notices=response.data.notice;
         			});
  	  }
  	});
}
function pushMsg(msg,essayid_)
{
	var url_pushMsg='api/v1/idol/msgBoard/pushMsgBoard';
	var data2={
			essayid:essayid_,
			content:msg
	};
	  $.ajax({
		  	url:url_pushMsg
	        , async:false
	        , type: "POST"
	        , data:data2
	        , datatype: "json"
	        ,success: function(data)
	        {
           ret=JSON.parse(data);
           if(ret.status==1)
        	   {
		       	layer.msg(ret.msg);
        	   }
           else     		
        	   {
        	   	layer.msg(ret.msg);
        	   }
        }
	    });
	
}
function load_msgBoard(essayid_)
{
	/*
	 * <div><span id="msg_push1">我的女神</span></div>
		    <div><span>温婉而雅</span></div>
		    <div><span>温柔美丽</span></div>
		    <div><span>温柔美丽</span></div>
		    <div><span>温柔美丽</span></div>
		    <div><span>我最爱的叶老师</span></div>
		    <div><span>气质型</span></div>
		    <div><span>风趣幽默</span></div>
		    <div><span>风趣幽默</span></div>
	 * */
	var url_getAllMsgByEssayId='api/v1/idol/msgBoard/getAllMsgByEssayId?essayid='+essayid_;
	$.ajax({
	  	url:url_getAllMsgByEssayId
        , async:false
        , type: "GET"
        ,success: function(data){
               var ret=JSON.parse(data);
               var dom_msg='';
               var pre='<div><span>';
               var suf='</span></div>';
               for(var i=0;i<ret.data.length;i++)
            	   {
            	   dom_msg+=(pre+ret.data[i].content+suf);
            	   }
               $(".barrager").html(dom_msg);
            }
    });
}