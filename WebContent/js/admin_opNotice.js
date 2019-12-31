function checkNotice()
{
var url_delNotice='api/v1/idol/notice/delNoticeById';
var url_getAllNotice='api/v1/idol/notice/getAllNotice';
var url_updateNoticeById='api/v1/idol/notice/updateNoticeById';
$("#add_essay").attr("hidden",true);
$("#post_essay").attr("hidden",true);
layui.use('table', function(){
	  var table = layui.table;
	  table.render({
	    elem: '#essay_list'
	    ,url:url_getAllNotice
	    ,toolbar: '#toolbarDemo' //开启头部工具栏，并为其绑定左侧模板
	    ,defaultToolbar: ['filter', 'exports', 'print', { //自定义头部工具栏右侧图标。如无需自定义，去除该参数即可
	      title: '提示'
	      ,layEvent: 'LAYTABLE_TIPS'
	      ,icon: 'layui-icon-tips'
	    }]
	    ,title: '公告数据表'
	    ,cols:[[
	    	 {type: 'checkbox', fixed: 'left'},
	        {field:'sender', MinWidth:80, title: '发布者', sort: true}
	        ,{field:'sendtime', MinWidth:80, title: '发布时间'}
	        ,{field:'noticeid', MinWidth:80, title: '标识ID', sort: true}
	        ,{field:'content', MinWidth:80, title: '内容'}
	        ,{fixed: 'right', title:'操作', toolbar: '#essay_op', width:150}
	      ]]
	    ,page: true
	  });
	  
	  //头工具栏事件
	  table.on('toolbar(essay_list)', function(obj){
	    var checkStatus = table.checkStatus(obj.config.id);
	    switch(obj.event){
	      case 'getCheckData':
	        var data = checkStatus.data;
	        layer.alert(JSON.stringify(data));
	      break;
	      case 'getCheckLength':
	        var data = checkStatus.data;
	        layer.msg('选中了：'+ data.length + ' 个');
	      break;
	      case 'isAll':
	        layer.msg(checkStatus.isAll ? '全选': '未全选');
	      break;
	      //自定义头工具栏右侧图标 - 提示
	      case 'LAYTABLE_TIPS':
	        layer.alert('啥也没得。。。');
	      break;
	    };
	  });
	  
	  //监听行工具事件
	  table.on('tool(essay_list)', function(obj){
	    var data = obj.data;
	    //console.log(obj)
	    if(obj.event === 'del'){
	      layer.confirm('真的删除行么', function(index){
	    	layer.msg(data.essayid);
	    	//删除操作，后端
	  	  $.ajax({
			  	url:url_delNotice
		        , async:false
		        , type: "POST"
		        , data:"noticeid="+data.noticeid
		        ,success: function(data)
		        {
	               ret=JSON.parse(data);
	               if(ret.status==1)//删除成功
	            	   {
			       	    obj.del();//删除操作，前端
			       		layer.msg(ret.msg);
			       	    layer.close(index);
	            	   }
	               else     		//删除失败
	            	   {
	            	   	layer.msg(ret.msg);
	            	   	layer.close(index);
	            	   }
	            }
		    });
	      });
	    } else if(obj.event === 'edit'){
	      layer.prompt({
	        formType: 2
	        ,value: data.content
	      }, function(value, index){
	        obj.update({
	          content: value
	        });
	        //后端修改请求
	        var noticeid=data.noticeid;
	      //  var content=content;
	        var data1={
	        		noticeid:noticeid
	        		,content:value
	        };
	   	  $.ajax({
			  	url:url_updateNoticeById
		        , async:false
		        , type: "POST"
		        , data:data1
		        , datatype: "json"
		        ,success: function(data2)
		        {
	               ret=JSON.parse(data2);
	               if(ret.status==1)//修改改成功
	            	   {
	            	   layer.msg(ret.msg);
	            	   }
	               else     		//修改失败
	            	   {
	            	   layer.msg(ret.msg);
	            	   }
	            }
		    });
	        //关闭窗口
	        layer.close(index);
	      });
	    }
	  });
	});
}
function addNotice()
{
	var url_postNotice='api/v1/idol/notice/postNotice';
	layer.prompt({
		  formType: 2,
		  value: '不超过30字！！！',
		  title: '发布公告',
		  area: ['800px', '350px'] //自定义文本域宽高
		}, function(value, index, elem){
			var data1={
					content:value
			};
			$.ajax({
				url: url_postNotice,
				async:false, 
				type: 'POST',
				data: data1, 
				datatype: "json",
				success: function(data){
		            var ret=JSON.parse(data);
		            layer.msg(ret.msg);
		         }
			});
		//  alert(value); //得到value
		  layer.close(index);
		}); 
}
function postNotice()
{
	var url_postEssay='api/v1/idol/addEssay';
	var essay_title=$("#essay_title").val();
	var essay_from=$("#essay_from").val();
	var essay_type=$("#essay_type").val();
	var essay_content=$("#essay_content").val();
	var file = $('#essay_file')[0].files[0];
	var formData = new FormData();
	formData.append("essay_title",essay_title);
	formData.append("essay_from",essay_from);
	formData.append("essay_type",essay_type);
	formData.append("essay_content",essay_content);
	formData.append("essay_file",$('#essay_file')[0].files[0]);
	var data={
			essay_title:essay_title,
			essay_from:essay_from,
			essay_type:essay_type,
			essay_content:essay_content
	};
	$.ajax({
		url: url_postEssay,
		type: 'POST',
		cache: false, // cache设置为false，上传文件不需要缓存
		data: formData, // 将form表单实例化为FormData类型
		processData: false, // 设置为false。因为data值是FormData对象，不需要对数据做处理.
		contentType: false, // contentType设置为false。因为是由<form>表单构造的FormData对象，且已经声明了属性enctype="multipart/form-data"，所以这里设置为false。
		success: function(data){
            var ret=JSON.parse(data);
            layer.msg(ret.msg);
         }
	});
}