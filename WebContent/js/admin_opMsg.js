function getAllMsg()
{
var url_getAllMsg='api/v1/idol/msgBoard/getAllMsg';
var url_delMsgById='api/v1/idol/msgBoard/delMsgById';
$("#add_essay").attr("hidden",true);
$("#post_essay").attr("hidden",true);
layui.use('table', function(){
	  var table = layui.table;
	  table.render({
	    elem: '#essay_list'
	    ,url:url_getAllMsg
	    ,toolbar: '#toolbarDemo' //开启头部工具栏，并为其绑定左侧模板
	    ,defaultToolbar: ['filter', 'exports', 'print', { //自定义头部工具栏右侧图标。如无需自定义，去除该参数即可
	      title: '提示'
	      ,layEvent: 'LAYTABLE_TIPS'
	      ,icon: 'layui-icon-tips'
	    }]
	    ,title: '公告数据表'
	    ,cols:[[
	    	 {type: 'checkbox', fixed: 'left'},
	        {field:'msgid', MinWidth:80, title: '信息ID', sort: true}
	        ,{field:'essayid', MinWidth:80, title: '文章ID'}
	        ,{field:'sender', MinWidth:80, title: '发送者', sort: true}
	        ,{field:'sendtime', MinWidth:80, title: '发送时间'}
	        ,{field:'content', MinWidth:80, title: '内容'}
	        ,{fixed: 'right', title:'操作', toolbar: '#msg_op', width:150}
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
			  	url:url_delMsgById
		        , async:false
		        , type: "POST"
		        , data:"msgId="+data.msgid
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
	    } 
	  });
	});
}
