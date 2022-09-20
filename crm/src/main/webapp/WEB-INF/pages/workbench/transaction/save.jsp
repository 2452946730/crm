<%@page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";
%>
<html>
<head>
	<base href="<%=basePath%>" >
<meta charset="UTF-8">

<link href="jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
<link href="jquery/bootstrap-datetimepicker-master/css/bootstrap-datetimepicker.min.css" type="text/css" rel="stylesheet" />

<script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
<script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
<script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/js/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/locale/bootstrap-datetimepicker.zh-CN.js"></script>
<%--引入typeahead--%>
<script type="text/javascript" src="jquery/bs_typeahead/bootstrap3-typeahead.min.js"></script>
	<script type="text/javascript">
		$(function () {
			//给阶段下拉列表添加change事件
			$("#create-stage").change(function () {
				let stage = $("#create-stage option:selected").text();
				$.ajax({
					url:"workbench/transaction/possibility.do",
					data:{
						stage:stage
					},
					type:"post",
					dataType:"json",
					success:function (data){
						$("#create-possibility").val(data+"%");
					}
				});
			});
			//给查询客户名称添加键盘弹起事件
			$("#create-customerName").typeahead({
				source:function (jquery, process) {
					//发送请求
					$.ajax({
						url: "workbench/transaction/queryCustomerNameByName.do",
						data: {name: jquery},
						type: "post",
						dataType: "json",
						success: function (data) {
							process(data);
						}
					});
				}
			});
			//给市场活动源添加单击事件
			$("#activityA").click(function () {
				$("#findMarketActivity").modal("show");
				//初始化
				$("#queryActivityTxt").val("");
				$("#activityTBody").html("");
			});
			//给查找市场活动添加键盘弹起事件
			$("#queryActivityTxt").keyup(function () {
				//收集参数
				let activityName = $("#queryActivityTxt").val();
				$.ajax({
					url:"workbench/transaction/queryAllActivity.do",
					data:{activityName:activityName},
					type:"post",
					dataType:"json",
					success:function (data) {
						let str = "";
						$.each(data,function (index, obj) {
							str+="<tr>";
							str+="<td><input activityName=\""+obj.name+"\" value=\""+obj.id+"\" type=\"radio\" name=\"activity\"/></td>";
							str+="<td>"+obj.name+"</td>";
							str+="<td>"+obj.startDate+"</td>";
							str+="<td>"+obj.endDate+"</td>";
							str+="<td>"+obj.owner+"</td>";
							str+="</tr>";
						})
						$("#activityTBody").html(str);
						//给查找市场活动添加单击事件
						$("#activityTBody").on("click","input[type='radio']",function () {
							$("#create-activitySource").val($(this).attr("activityName"));
							$("#create-activitySourceId").val(this.value);
							$("#findMarketActivity").modal("hide");
						})
					}
				});
			});
			//给联系人名称添加单击事件
			$("#contactsA").click(function () {
				$("#findContacts").modal("show");
				//初始化
				$("#queryContactsTxt").val("");
				$("#contactsTBody").html("");
			});
			//搜索联系人名称添加键盘弹起事件
			$("#queryContactsTxt").keyup(function () {
				//收集参数
				let contactsName = $("#queryContactsTxt").val();
				$.ajax({
					url:"workbench/transaction/queryAllContacts.do",
					data:{contactsName:contactsName},
					type:"post",
					dataType:"json",
					success:function (data) {
						let str = "";
						$.each(data,function (index,obj){
							str+="<tr>";
							str+="<td><input contactsName=\""+obj.fullname+"\" value=\""+obj.id+"\" type=\"radio\" name=\"activity\"/></td>";
							str+="<td>"+obj.fullname+"</td>";
							str+="<td>"+obj.email+"</td>";
							str+="<td>"+obj.mphone+"</td>";
							str+="</tr>";
						});
						$("#contactsTBody").html(str);
						$("#contactsTBody").on("click","input",function () {
							$("#create-contactsName").val($(this).attr("contactsName"));
							$("#create-contactsNameId").val(this.value);
							$("#findContacts").modal("hide");
						})
					}
				});
			});
			//给预计成交日期设置日期函数
			myDate($("#create-expectedDate"));
			//给下次联系时间设置日期函数
			myDate($("#create-nextContactTime"));
			//给保存按钮添加单击事件
			$("#saveCreateTranBtn").click(function () {
				//收集参数
				let owner = $("#create-owner").val();
				let money = $("#create-money").val();
				let name = $("#create-name").val();
				let expectedDate = $("#create-expectedDate").val();
				let customerId = $("#create-customerName").val();
				let stage = $("#create-stage option:selected").text();
				let type = $("#create-type option:selected").text();
				let source = $("#create-source option:selected").text();
				let activityId = $("#create-activitySourceId").val();
				let contactsId = $("#create-contactsNameId").val();
				let description = $("#create-description").val();
				let contactSummary = $("#create-contactSummary").val();
				let nextContactTime = $("#create-nextContactTime").val();
				//表单验证
				//发送请求
				$.ajax({
					url:"workbench/transaction/saveCreateTran.do",
					data:{
						owner:owner,
						money:money,
						name:name,
						expectedDate:expectedDate,
						customerId:customerId,
						stage:stage,
						type:type,
						source:source,
						activityId:activityId,
						contactsId:contactsId,
						description:description,
						contactSummary:contactSummary,
						nextContactTime:nextContactTime,
					},
					type:"post",
					dataType:"json",
					success:function (data) {
						if (data.code == 1) {
							window.location.href = "workbench/transaction/index.do";
						} else {
							alert(data.message);
						}
					}
				});

			});
		});
		//设置日历插件
		function myDate(id) {
			id.datetimepicker({
				language:"zh-CN",
				format:"yyyy-mm-dd",
				autoclose:true,
				minView:"month",
				initialDate:new Date(),
				todayBtn:true,
				clearBtn:true
			});
		}
	</script>
</head>
<body>

	<!-- 查找市场活动 -->
	<div class="modal fade" id="findMarketActivity" role="dialog">
		<div class="modal-dialog" role="document" style="width: 80%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title">查找市场活动</h4>
				</div>
				<div class="modal-body">
					<div class="btn-group" style="position: relative; top: 18%; left: 8px;">
						<form class="form-inline" role="form">
						  <div class="form-group has-feedback">
						    <input type="text" class="form-control" id="queryActivityTxt" style="width: 300px;" placeholder="请输入市场活动名称，支持模糊查询">
						    <span class="glyphicon glyphicon-search form-control-feedback"></span>
						  </div>
						</form>
					</div>
					<table id="activityTable3" class="table table-hover" style="width: 900px; position: relative;top: 10px;">
						<thead>
							<tr style="color: #B3B3B3;">
								<td></td>
								<td>名称</td>
								<td>开始日期</td>
								<td>结束日期</td>
								<td>所有者</td>
							</tr>
						</thead>
						<tbody id="activityTBody">
							<%--<tr>
								<td><input type="radio" name="activity"/></td>
								<td>发传单</td>
								<td>2020-10-10</td>
								<td>2020-10-20</td>
								<td>zhangsan</td>
							</tr>
							<tr>
								<td><input type="radio" name="activity"/></td>
								<td>发传单</td>
								<td>2020-10-10</td>
								<td>2020-10-20</td>
								<td>zhangsan</td>
							</tr>--%>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>

	<!-- 查找联系人 -->
	<div class="modal fade" id="findContacts" role="dialog">
		<div class="modal-dialog" role="document" style="width: 80%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title">查找联系人</h4>
				</div>
				<div class="modal-body">
					<div class="btn-group" style="position: relative; top: 18%; left: 8px;">
						<form class="form-inline" role="form">
						  <div class="form-group has-feedback">
						    <input type="text" id="queryContactsTxt" class="form-control" style="width: 300px;" placeholder="请输入联系人名称，支持模糊查询">
						    <span class="glyphicon glyphicon-search form-control-feedback"></span>
						  </div>
						</form>
					</div>
					<table id="activityTable" class="table table-hover" style="width: 900px; position: relative;top: 10px;">
						<thead>
							<tr style="color: #B3B3B3;">
								<td></td>
								<td>名称</td>
								<td>邮箱</td>
								<td>手机</td>
							</tr>
						</thead>
						<tbody id="contactsTBody">
							<%--<tr>
								<td><input type="radio" name="activity"/></td>
								<td>李四</td>
								<td>lisi@bjpowernode.com</td>
								<td>12345678901</td>
							</tr>
							<tr>
								<td><input type="radio" name="activity"/></td>
								<td>李四</td>
								<td>lisi@bjpowernode.com</td>
								<td>12345678901</td>
							</tr>--%>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>


	<div style="position:  relative; left: 30px;">
		<h3>创建交易</h3>
	  	<div style="position: relative; top: -40px; left: 70%;">
			<button type="button" id="saveCreateTranBtn" class="btn btn-primary">保存</button>
			<button type="button" class="btn btn-default">取消</button>
		</div>
		<hr style="position: relative; top: -40px;">
	</div>
	<form class="form-horizontal" role="form" style="position: relative; top: -30px;">
		<div class="form-group">
			<label for="create-owner" class="col-sm-2 control-label">所有者<span style="font-size: 15px; color: red;">*</span></label>
			<div class="col-sm-10" style="width: 300px;">
				<select class="form-control" id="create-owner">
				  <c:forEach items="${userList}" var="u">
					  <option value="${u.id}">${u.name}</option>
				  </c:forEach>
				</select>
			</div>
			<label for="create-money" class="col-sm-2 control-label">金额</label>
			<div class="col-sm-10" style="width: 300px;">
				<input type="text" class="form-control" id="create-money">
			</div>
		</div>

		<div class="form-group">
			<label for="create-name" class="col-sm-2 control-label">名称<span style="font-size: 15px; color: red;">*</span></label>
			<div class="col-sm-10" style="width: 300px;">
				<input type="text" class="form-control" id="create-name">
			</div>
			<label for="create-expectedDate" class="col-sm-2 control-label">预计成交日期<span style="font-size: 15px; color: red;">*</span></label>
			<div class="col-sm-10" style="width: 300px;">
				<input type="text" class="form-control" id="create-expectedDate" readonly>
			</div>
		</div>

		<div class="form-group">
			<label for="create-customerName" class="col-sm-2 control-label">客户名称<span style="font-size: 15px; color: red;">*</span></label>
			<div class="col-sm-10" style="width: 300px;">
				<input type="text" class="form-control" id="create-customerName" placeholder="支持自动补全，输入客户不存在则新建">
			</div>
			<label for="create-stage" class="col-sm-2 control-label">阶段<span style="font-size: 15px; color: red;">*</span></label>
			<div class="col-sm-10" style="width: 300px;">
			  <select class="form-control" id="create-stage">
			  	<option></option>
			  	<c:forEach items="${stageList}" var="s">
					<option value="${s.id}">${s.value}</option>
				</c:forEach>
			  </select>
			</div>
		</div>

		<div class="form-group">
			<label for="create-type" class="col-sm-2 control-label">类型</label>
			<div class="col-sm-10" style="width: 300px;">
				<select class="form-control" id="create-type">
				  <option></option>
				 <c:forEach items="${transactionTypeList}" var="t">
					 <option value="${t.id}">${t.value}</option>
				 </c:forEach>
				</select>
			</div>
			<label for="create-possibility" class="col-sm-2 control-label">可能性</label>
			<div class="col-sm-10" style="width: 300px;">
				<input type="text" class="form-control" id="create-possibility" readonly>
			</div>
		</div>

		<div class="form-group">
			<label for="create-source" class="col-sm-2 control-label">来源</label>
			<div class="col-sm-10" style="width: 300px;">
				<select class="form-control" id="create-source">
				  <option></option>
				 <c:forEach items="${sourceList}" var="s">
					 <option value="${s.id}">${s.value}</option>
				 </c:forEach>
				</select>
			</div>
			<label for="create-activitySource" class="col-sm-2 control-label">市场活动源&nbsp;&nbsp;<a href="javascript:void(0);" id="activityA"><span class="glyphicon glyphicon-search"></span></a></label>
			<div class="col-sm-10" style="width: 300px;">
				<input type="hidden" id="create-activitySourceId"/>
				<input type="text" class="form-control" id="create-activitySource" readonly>
			</div>
		</div>

		<div class="form-group">
			<label for="create-contactsName" class="col-sm-2 control-label">联系人名称&nbsp;&nbsp;<a href="javascript:void(0);" id="contactsA" ><span class="glyphicon glyphicon-search"></span></a></label>
			<div class="col-sm-10" style="width: 300px;">
				<input type="hidden" id="create-contactsNameId" />
				<input type="text" class="form-control" id="create-contactsName">
			</div>
		</div>

		<div class="form-group">
			<label for="create-description" class="col-sm-2 control-label">描述</label>
			<div class="col-sm-10" style="width: 70%;">
				<textarea class="form-control" rows="3" id="create-description"></textarea>
			</div>
		</div>

		<div class="form-group">
			<label for="create-contactSummary" class="col-sm-2 control-label">联系纪要</label>
			<div class="col-sm-10" style="width: 70%;">
				<textarea class="form-control" rows="3" id="create-contactSummary"></textarea>
			</div>
		</div>

		<div class="form-group">
			<label for="create-nextContactTime" class="col-sm-2 control-label">下次联系时间</label>
			<div class="col-sm-10" style="width: 300px;">
				<input type="text" class="form-control" id="create-nextContactTime" readonly>
			</div>
		</div>

	</form>
</body>
</html>
