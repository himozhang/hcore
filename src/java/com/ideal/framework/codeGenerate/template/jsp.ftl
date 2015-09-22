<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
 
<!DOCTYPE html>
<html lang="en">
	<head>
		<%@ include file="/common/header.jsp"%>
	</head>
	
	<body class="navbar-fixed">
		<%@ include file="/common/navbar.jsp"%>
		
		<div class="main-container" id="main-container">
			<div class="main-container-inner">
				<!-- 侧边菜单栏 -->
				<%@ include file="/common/sidebar.jsp"%>
				
				<!-- 页面内容 -->
				<div class="main-content">
					
					 <div class="col-sm-12  no-padding-left no-padding-right ">
							<div class="col-xs-3 col-sm-3 no-padding-left no-padding-right" >
								<div  class="col-xs-11  secondbar "  style="border-left:none;">
									<div class="search-shortcuts-large center" id="">
										<span class="input-icon input-icon-right">
											<input type="text" id="treeSearchKey" class="input-sm" placeholder="请输入关键字">
											<i class="icon-search " style="cursor: pointer;" id="btn_treeSearch"></i>
										</span>
									</div>
									<ul id="treeContainer" ></ul>
								</div>
							</div><!-- /.col -->
							<div class="col-xs-9 col-sm-9 no-padding-left " >
								<div id="page-content">
									<div>
										<h3 class="header smaller lighter blue">
											<ricore:currentLocation />
										</h3>
										<div id="nav-search" class="nav-search">
											<span class="input-icon input-icon-right">
												<input type="text" placeholder="请输入关键字" class="nav-search-input" id="searchKey" >
												<i class="icon-search " style="cursor: pointer;" id="btn_search"></i>
											</span>
										</div>
										</ricore:haveMenuButton>
									</div>
									<table id="grid-table"></table>
								</div>
							</div><!-- /.col -->
					</div><!-- /.row -->	
				</div><!-- /.main-content -->
			</div>
		</div>
		
		<!-- 引入公共js -->
		<%@ include file="/common/include-js.jsp"%>
		<script type="text/javascript" src="${"$"}${"{"}ctx}/static/js/app/${moduleName}/${moduleName}.${className}.js"></script>
		
    	
		<script type="text/javascript">
			 	 
				$(document).ready(function(){
								
					//调用js/grid/ideal.app.grid.js
					$("#grid-table").DataGrid({
						url:ctx+"/${className}/load",
						colNames:['菜单名称', 'URL','上级菜单', '状态','排序'],
						colModel:[
							{name:'name',index:'name',width:100,formatter:function(cellvalue, options, rowObject){
								var state = "easyui-tooltip ";
								var title = "菜单启用中";
								if("1"!=rowObject.state){
									state="tooltip-error red";
									title="菜单已停用";
								}
								var html ="<a href='"+ctx+"/${className}/input/"+rowObject.id+"' class='"+state+"' data-options='position:\"top\"' title='"+title+"'  >"+cellvalue+"</a>";
								if(!isNull(rowObject.icon)){
									html= '<span class="badge badge-info"><i class="'+rowObject.icon+'"></i></span>&nbsp;'+html;
								}
								
								return html;
							}},
							{name:'url',index:'url', width:170},
							{name:'pname',index:'pname', width:80,sortable:false},	
							{name:'state',index:'state', width:30,algin:'center',formatter:function(cellvalue, options, rowObject){
								var state = "<span class='label label-important'>停用</span>";
									if("1"==cellvalue){
										state = "<span class='label label-success '>启用</span>";
									}
		  						return state;
							}},
							{name:'sort',index:'sort', width:80,formatter:function(cellvalue, options, rowObject){
								//ideal.app.order.js 排序按钮调用方法
								return setOrderBtn(rowObject,"${"$"}${"{"}ctx}/${className}/exchangeOrder","orderSuccessCallBack");
							}}
						],
						postData :{
							searchKey:"${"$"}${"{"}searchKey}" // 过滤条件
						}
					});	
				});
		</script>	
	</body>
	
</html>