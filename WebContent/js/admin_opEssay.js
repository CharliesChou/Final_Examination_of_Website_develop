function checkEssay()
{
var url_essayDel='api/v1/idol/delEssayById';
var url_essayList='api/v1/idol/getEssayList';
$("#add_essay").attr("hidden",true);
$("#post_essay").attr("hidden",true);
layui.use('table', function(){
	  var table = layui.table;
	  table.render({
	    elem: '#essay_list'
	    ,url:url_essayList
	    ,toolbar: '#toolbarDemo' //开启头部工具栏，并为其绑定左侧模板
	    ,defaultToolbar: ['filter', 'exports', 'print', { //自定义头部工具栏右侧图标。如无需自定义，去除该参数即可
	      title: '提示'
	      ,layEvent: 'LAYTABLE_TIPS'
	      ,icon: 'layui-icon-tips'
	    }]
	    ,title: '文章数据表'
	    ,cols:[[
	    	 {type: 'checkbox', fixed: 'left'},
	        {field:'essayid', width:80, title: '文章id', sort: true}
	        ,{field:'title', width:80, title: '标题'}
	        ,{field:'type', width:80, title: '类别', sort: true}
	        ,{field:'from', width:80, title: '来源'}
	        ,{field:'time', title: '时间', minWidth: 50, sort: true} //minWidth：局部定义当前单元格的最小宽度，layui 2.2.1 新增
	        ,{field:'img', title: '图片'}
	        ,{field:'content', title: '内容', width: '30%'}
	        ,{field:'editor', title: '编辑'}
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
	  
	  var url_updateEssayContentById='api/v1/idol/updateEssayContentById';
	  //监听行工具事件
	  table.on('tool(essay_list)', function(obj){
	    var data = obj.data;
	    //console.log(obj)
	    if(obj.event === 'del'){
	      layer.confirm('真的删除行么', function(index){
	    	layer.msg(data.essayid);
	    	//删除操作，后端
	  	  $.ajax({
			  	url:url_essayDel
		        , async:false
		        , type: "POST"
		        , data:"essayid="+data.essayid
		        ,success: function(data)
		        {
	               ret=JSON.parse(data);
	               if(ret.status==1)//删除成功
	            	   {
			       	    obj.del();//删除操作，前端
			       		layer.msg("删除成功");
			       	    layer.close(index);
	            	   }
	               else     		//删除失败
	            	   {
	            	   	layer.msg("删除失败");
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
	        var essayId=data.essayid;
	        var contenT=data.content;
	        var data1={
	        		essayid:essayId
	        		,content:value
	        };
	   	  $.ajax({
			  	url:url_updateEssayContentById
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
function addEssay()
{
	$("#add_essay").attr("hidden",false);
	$("#post_essay").attr("hidden",false);
	layui.use('form', function(){
	  var form = layui.form;
	});
}
function postEssay()
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