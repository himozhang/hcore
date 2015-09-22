<%@ page contentType="text/html;charset=UTF-8"%>
<%@page import="com.ricore.framework.constants.StateConstant"%>
<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE html>
<html lang="en">
<head>
<%@ include file="/common/header.jsp"%>
</head>

<body CLASS="navbar-fixed">

	<%@ include file="/common/navbar.jsp"%>
	
	<div id="main-container" class="main-container">
			<div class="main-container-inner">
				 <%@ include file="/common/sidebar.jsp"%>
				<div class="main-content">
					<!-- 页面内容导航 -->
					<div id="breadcrumbs" class="breadcrumbs">
						<ul class="breadcrumb">
							<li>
								<i class="icon-home home-icon"></i>
								<a href="${"$"}${"{"}homeUrl }">首页</a>
							</li>
							<li class="active"><a href="${"$"}${"{"}ctx}/${className}/">角色管理</a></li>
							<li class="active">角色编辑</li>
						</ul><!-- .breadcrumb -->
					</div>
					<!-- 页面内容 -->
					<div class="page-content">
						<div style="padding-bottom: 35px;" class="page-header">
								<div class="pull-left">
									<h1>
										角色编辑
										<small>
											<i class="icon-double-angle-right"></i>
											<c:if test="${"$"}${"{"}not empty entity.id}">
												${"$"}${"{"}entity.name }
											</c:if>
											<c:if test="${"$"}${"{"}empty entity.id}">
												您正在新增角色信息
											</c:if>
										</small>
									</h1>
								</div>
								<div class=" pull-right">
									<button class="btn btn-xs btn-danger" id="btn_back">
										<i class="icon-reply"></i>返回
									</button>
								</div>
						</div>
						<div class="row">
							<div class="col-xs-12">
								<!-- PAGE CONTENT BEGINS -->
								<form class="form-horizontal" id="infoForm"  action="${"$"}${"{"}ctx }/${className}/save" method="post">
									<!-- submitType 如果使用ajax提交表单，则设置value为 ajaxSubmit 用于将后台输出转换为JSON输出-->
									<input type="hidden"  name="submitType" value="ajaxSubmit" /> 
									<c:if test="${"$"}${"{"}not empty entity.id}">
										<input type="hidden" id="id" name="id" value="${"$"}${"{"}entity.id }" />
									</c:if>																					
									<div class="space-4"></div>			
									<div class="form-group">
										<label class="col-sm-3 control-label no-padding-right" for="form-field-1"> 角色名称 </label>
										<div class="col-sm-9">
											<div class="clearfix">
													<input type="text" class="col-xs-10 col-sm-5 easyui-validatebox"  data-options="required:true"
													placeholder="请输入角色名称" id="name" name="name"  value="${"$"}${"{"}entity.name }"  />
											</div>
											
										</div>
									</div>
									
									<div class="space-4"></div>
									
									<div class="form-group">
										<label class="col-sm-3 control-label no-padding-right " for="form-field-1"> 角色编码 </label>
										<div class="col-xs-12 col-sm-9">
											<div class="clearfix">
													<input type="text" id="code" name="code" value="${"$"}${"{"}entity.code}" 
														class="col-xs-10 col-sm-5 easyui-validatebox" data-options="required:true"
														validType="ajaxRequired['${"$"}${"{"}ctx }/${className}/checkCode?id=${"$"}${"{"}entity.id }','code','角色编码已存在','无效','2']"/>
											</div>
										</div>	
									</div>
									
									
									<div class="space-4"></div>
									<div class="form-group">
										<label class="col-sm-3 control-label no-padding-right" for="form-field-1"> 描述 </label>
										<div class="col-sm-9">
											<div class="clearfix">
												<textarea id="detail_txtare"  placeholder="请输入描述" class="col-xs-10 col-sm-5 easyui-validatebox limited" maxlength="200" validType="maxLength['200']"><c:if test="${"$"}${"{"}not empty entity.detail}">${"$"}${"{"}entity.detail }</c:if></textarea>
												<input type="hidden" name="detail" id="detail" value="${"$"}${"{"}entity.detail }"/>
											</div>
											
										</div>
									</div>
									
									<div class="space-4"></div>
										<div class="form-group">
											<label class="col-sm-3 control-label no-padding-right" for="form-field-1"> 角色状态 </label>
											<div class="col-sm-9">
												<div class="clearfix">
													<label class="radio inline"> 
														<input type="radio" value="<%=StateConstant.STATE_YES %>"  id="state1" name="state"><%=StateConstant.getStateName(StateConstant.STATE_YES) %>
													</label> 
													<label class="radio inline"> 
														<input type="radio" value="<%=StateConstant.STATE_NO %>" id="state0" name="state"><%=StateConstant.getStateName(StateConstant.STATE_NO) %>
													</label>
												</div>
											</div>
										</div>
													
									<div class="space-4"></div>
									<div class="form-group">
										<label class="col-sm-3 control-label no-padding-right" for="form-field-1"> 排序号 </label>
										<div class="col-sm-9">
											<div class="input-group">
												<span class="input-group-addon">
													<i class="icon-sort-numeric-asc"></i>
												</span>
												<input type="text" id="sort" name="sort" value="${"$"}${"{"}entity.sort}" 
													   data-options="validType:'integer'" class="easyui-validatebox"/>	
											</div>
										</div>
									</div>
									
									<div class="clearfix form-actions center">
										<button class="btn btn-info " type="button" id="btn_${className}_save">
											<i class="icon-check bigger-110"></i>
											保存
										</button>
							
										&nbsp; &nbsp; &nbsp;
										<button class="btn btn-danger" type="button" id="btn_${className}_reset">
											<i class="icon-undo bigger-110"></i>
											重置
										</button>
									</div>						
								</form>
								<!-- PAGE CONTENT ENDS -->
							</div><!-- /.col -->
						</div><!-- /.row -->	
					</div><!-- /.page-content -->
				</div>
				
		</div>
		
	 <%@ include file="/common/include-js.jsp"%>
	 <!-- 中文转全拼插件 -->
 	<script src="${"$"}${"{"}ctx}/static/js/SpellPinYin.js"></script>
	<script>
		$(document).ready(function() {
			//设置单选按钮选中
			radioBoxCheck("state","${"$"}${"{"}entity.state}","<%= StateConstant.STATE_YES%>");
			  $("#name").change(function(){
					var code_ = ConvertPinyin($(this).val());
					if(isNull($("#code").val())){
						$("#code").val(code_);
					}
				});
				$("#btn_${className}_save").click(function(e){
				   	$('#infoForm').form('submit', {
			   			onSubmit:function(){
			   				//判断表单验证是否通过
			   				var validate_= $('#infoForm').form('validate');
			   				if(validate_){
			   					$("#detail").val($("#detail_txtare").val());
			   					//给main-container容器添加loadding效果
	    						loadding($("#main-container"));
	    						return true;
			   				}
			   				return false;
			   			},
						success : function(data) {
							//移除loadding效果
		    				removeLoadding();
							var response_json = eval("(" + data + ")");
							if (response_json.stat == "ok") {//表单提交成功触发
								$.jGrowl(response_json.msg, {
									theme : 'growl-success',
									header : '成功!'
								});//右上提示操作信息
								//如果是新增，则需要动态创建一个hidden input用来存放ID
								if($("#id").length<=0){
									var inpt_id = $("<input type='hidden' name='id' id='id' value='"+response_json.data.roleId+"' />")
									$("#infoForm").append(inpt_id);
								}
							} else {
								$.jGrowl(response_json.msg, {
									theme : 'growl-error',
									header : '失败!'
								});
							}
						}
					});
	 		    });	
	 		     //重置表单
	            $("#btn_${className}_reset").click(function(){
	            	$('#infoForm').form('clear');
	            });
	            
	            
	        	//点击返回按钮时，触发
	            $("#btn_back").click(function(){
	            	window.location.href = "${"$"}${"{"}ctx}/${className}/";
	            });
			
		});
	</script>
</body>
</html>