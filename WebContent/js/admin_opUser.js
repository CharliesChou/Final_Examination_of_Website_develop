function updateUserPower(operate,username)
{
	  var url_updatePower='api/v1/idol/updatePower';
	  var data2;
	 // alert(operate+":"+username);
	  if(operate==1)//禁用
		  {
		  data2={
				 operate:'disable' 
				,username:username
		  	};
		  }
	  else  //解禁
		  {
		  data2={
					operate:'enable' 
					,username:username
			  	};
		  }
	  $.ajax({
		  	url:url_updatePower
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

function searchUser()
{
	var url_searchUser='api/v1/idol/searchUser?userinfo=';
	var dataForSearch;
	layer.prompt({formType: 2,value:'输入查询的用户！'},function(value, index){
  	    	dataForSearch=value;
  	    	url_searchUser+=dataForSearch;
  	    	layui.use('table', function()
  				{var table = layui.table;  
  				  table.render
  				  ({elem:'#essay_list'
  					,url:url_searchUser
  					,toolbar: '#toolbarDemo' //开启头部工具栏，并为其绑定左侧模板
  					,defaultToolbar: ['filter', 'exports', 'print', { //自定义头部工具栏右侧图标。如无需自定义，去除该参数即可
  					title: '提示'
  					,layEvent: 'LAYTABLE_TIPS'
  					,icon: 'layui-icon-tips'
  					}]
  					,title: '用户数据表'
  					,cols: [[
  				      {type: 'checkbox', fixed: 'left'}
  				      ,{field:'username', title:'用户名', MinWidth:10, fixed: 'left', unresize: true, sort: true}
  				      ,{field:'password', title:'密码',MinWidth:10}
  				      ,{field:'profile', title:'头像', width:80}
  				      ,{field:'email', title:'邮箱', MinWidth:100}
  				      ,{field:'birth', title:'生日', MinWidth:100}
  				      ,{field:'place', title:'地址',MinWidth:100}
  				      ,{field:'power', title:'级别', width:120}
  				      ,{fixed: 'right', title:'操作', toolbar: '#user_op', width:150}
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
  				        layer.alert('这是工具栏右侧自定义的一个图标按钮');
  				      break;
  				    };
  				  });
  				  table.on('tool(essay_list)', function(obj){
  					    var data = obj.data;
  					    if(obj.event === 'del'){
  					      layer.confirm('真的禁用行么', function(index){
  					    	  updateUserPower(1,data.username);
  					    	  obj.update({
  						          power:'已禁用'
  						        });
  					        layer.close(index);
  					      });
  					    } else if(obj.event === 'edit'){
  						      layer.confirm('恢复成普通会员？', function(index){
  						    	  updateUserPower(2,data.username);
  						    	  obj.update({
  							          power:'普通会员'
  							        });
  						        layer.close(index);
  						      });
  					    }
  					  });
  				});
  	        layer.close(index);
  	      });
}

function opUser()
{
	var url_getUserList='api/v1/idol/getUserList';
	$("#add_essay").attr("hidden",true);
	$("#post_essay").attr("hidden",true);
	layui.use('table', function(){
		  var table = layui.table;
		  table.render({
		    elem: '#essay_list'
		    ,url:url_getUserList
		    ,toolbar: '#toolbarDemo' //开启头部工具栏，并为其绑定左侧模板
		    ,defaultToolbar: ['filter', 'exports', 'print', { //自定义头部工具栏右侧图标。如无需自定义，去除该参数即可
		      title: '提示'
		      ,layEvent: 'LAYTABLE_TIPS'
		      ,icon: 'layui-icon-tips'
		    }]
		    ,title: '用户数据表'
		    ,cols: [[
		      {type: 'checkbox', fixed: 'left'}
		      ,{field:'username', title:'用户名', MinWidth:10, fixed: 'left', unresize: true, sort: true}
		      ,{field:'password', title:'密码',MinWidth:10}
		      ,{field:'profile', title:'头像', width:80}
		      ,{field:'email', title:'邮箱', MinWidth:100}
		      ,{field:'birth', title:'生日', MinWidth:100}
		      ,{field:'place', title:'地址',MinWidth:100}
		      ,{field:'power', title:'级别', width:120}
		      ,{fixed: 'right', title:'操作', toolbar: '#user_op', width:150}
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
		        layer.alert('这是工具栏右侧自定义的一个图标按钮');
		      break;
		    };
		  });
		  table.on('tool(essay_list)', function(obj){
			    var data = obj.data;
			    if(obj.event === 'del'){
			      layer.confirm('真的禁用行么', function(index){
			    	  updateUserPower(1,data.username);
			    	  obj.update({
				          power:'已禁用'
				        });
			        layer.close(index);
			      });
			    } else if(obj.event === 'edit'){
				      layer.confirm('恢复成普通会员？', function(index){
				    	  updateUserPower(2,data.username);
				    	  obj.update({
					          power:'普通会员'
					        });
				        layer.close(index);
				      });
			    }
			  });
		});
}
