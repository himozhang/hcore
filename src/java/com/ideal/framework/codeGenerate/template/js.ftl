${"/**"}
${"*"} ${"@ClassName"}:${moduleName}.${className?lower_case}.js
${"*"} ${"@CreateTime"}:${CreateTime}
${"*"} ${"@author"}:${author}
${"*"} ${"@mail"}:${mail}
${"*"} ${"@Description"}:${Description}-${className}.jsp对应的JS - 由代码生成器生成
${"*/"}

//新增操作 方法名为菜单中生成的菜单按钮操作点方法名
function add(){
	var node = $('#treeContainer').tree('getSelected');//获得Tree选中的节点
	if(node&&!isNull(node.id)){//判断节点是否存在
		location.href=ctx+"/${className}/input/pid/"+node.id;//如果选中节点存在，则传递父级ID为当前选中的节点ID
	}else{
		location.href=ctx+"/${className}/input";//如果节点为空，则表示当前需要新增节点的父级ID为空
	}
}
//修改操作 方法名为菜单中生成的菜单按钮操作点方法名
function update(){
 	  var id = $("#grid-table").jqGrid('getGridParam','selarrrow');
	  if(id.length==0){
	  	$.jGrowl("请选择要修改的数据！", { theme: 'growl-error', header: '失败!' });
	  }else if (id.length>1){
	  	$.jGrowl("不能同时选择多条数据修改！", { theme: 'growl-error', header: '失败!' });
	  }else{
	  	location.href=ctx+"/${className}/input/"+id;
	  }	
}

/**
*删除操作 方法名为菜单中生成的菜单按钮操作点方法名
*${"@param"} obj 为绑定删除动作的BTN对象
*/
function del(obj){
	//js/ideal.app/ideal.app.delete.js
	 $(obj).ideal_delete({
	 	url:ctx+'/${className}/delete',
	 	deleteIds:$("#grid-table").jqGrid('getGridParam','selarrrow'),
	 	delSuccessCallBack:function(ids){
		 	 datagridReload();//重新刷新DataGrid
		 	 if(!isNull(ids)){//如果ids长度大于0，则需要删除Tree中的相应节点
			 	$.each(ids,function(i, item){
		 			var node = $('#treeContainer').tree('find',item);//根据ID在Tree中查找节点
		 			if(node){
		 				$('#treeContainer').tree('remove',node.target); //删除节点
		 			}
	   			 });
			 }
	 	}
	 });
}

//根据关键字重载datagrid
function datagridReload(){
	var queryParams = {};//新建参数对象
	queryParams.searchKey = $("#searchKey").val();//设置查询关键字
	var node = $('#treeContainer').tree('getSelected');//获得Tree选中节点
	if (node){//判断节点是否存在
		queryParams.pid=node.id;//设置上级节点ID为当前选中的节点ID
    }
	
    $("#grid-table").jqGrid('setGridParam',{ 
        postData:queryParams //发送数据 
    }).trigger("reloadGrid"); //重新载入 
}	

//排序成功后回调函数 order方法声明处见js/ideal.app/ideal.app.grid.operation.js
function orderSuccessCallBack(id){
	datagridReload();//重新刷新DataGrid
}

  
//重新加载树
function treeReload(){
     var node = $('#treeContainer').tree('getSelected');//获得Tree选中的节点
     if (node) {
     	 if(isNull(node.id)){
     	 	$('#treeContainer').tree('reload');//如果ID为空，则刷新全部树
     	 }else{
     	 	$('#treeContainer').tree('reload', node.target);//否则根据node节点刷新相关部分
     	 }
     }else {
        $('#treeContainer').tree('reload');//如果节点为空，则刷新全部树
     }
 }
 
//tree初始化
function initTree(){
	$('#treeContainer').ideal_tree({//ideal_tree方法声明处见js/ideal.app/ideal.app.tree.js
		url:ctx+'/${className}/tree',
		onClick:function(node){//tree节点选中事件,刷新datagrid数据
			datagridReload();//根据栏目和关键字重载datagrid
		},
		onLoadSuccess:function(node,data){//tree加载成功后,默认展开第一级节点
			//var node = $('#treeContainer').tree('getRoot');//获得tree Root节点
			//$('#treeContainer').tree('expand',node.target);//展开root节点
		}
	});
}

$(document).ready(function(){
	//实例化树对象		 
	initTree();
	
	//树查询事件
	$("#btn_treeSearch").on("click",function(event){
		$.post(ctx+'/${className}/tree',{'treeSearchKey':$("#treeSearchKey").val()},function(data){
			$('#treeContainer').tree('loadData',data);//根据返回的数据，重新load树
		},'json');
	});

	//grid的查询事件
	$("#btn_search").on("click",function(event){
		var queryParams = {};
		queryParams.searchKey = $("#searchKey").val();
		var node = $('#treeContainer').tree('getSelected');//获得 组织tree 选中节点
		if (node){
			queryParams.pid=node.id;
		}
		$("#grid-table").jqGrid('setGridParam',{ 
			postData:queryParams, //发送数据 
		}).trigger("reloadGrid"); //重新载入 
	});
	 
});
