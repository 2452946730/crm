<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<%
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";
%>
<html>
<head>
	<base href="<%=basePath%>">
<meta charset="UTF-8">

<link href="jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
<script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>


<link href="jquery/bootstrap-datetimepicker-master/css/bootstrap-datetimepicker.min.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/js/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/locale/bootstrap-datetimepicker.zh-CN.js"></script>

<script type="text/javascript">
	$(function(){
		$("#isCreateTransaction").click(function(){
			if(this.checked){
				$("#create-transaction2").show(200);
			}else{
				$("#create-transaction2").hide(200);
			}
		});
		//给市场活动源添加单价事件
		$("#convertA").click(function () {
			//初始化
			$("#queryActivityTxt").val("");
			$("#tBody").html("");
			//弹出模态窗口
			$("#searchActivityModal").modal("show");
		});
		//给搜索市场活动的模态窗口添加键盘弹起事件
		$("#queryActivityTxt").keyup(function () {
			//收集参数
			let activityName = $("#queryActivityTxt").val();
			let clueId = "${clue.id}";
			//发送请求
			$.ajax({
				url:"workbench/clue/queryActivityForConvertByNameAndClueId.do",
				data:{
					activityName:activityName,
					clueId:clueId
				},
				type:"post",
				dataType:"json",
				success:function (data) {
					let str = "";
					$.each(data,function (index,obj) {
						str+="<tr>";
						str+="<td><input activityName=\""+obj.name+"\" value=\""+obj.id+"\" type=\"radio\" name=\"activity\"/></td>";
						str+="<td>"+obj.name+"</td>";
						str+="<td>"+obj.startDate+"</td>";
						str+="<td>"+obj.endDate+"</td>";
						str+="<td>"+obj.owner+"</td>";
						str+="</tr>";
					})
					$("#tBody").html(str);
				}
			});
			//给搜索市场活动的模态窗口中的单选按钮添加点击事件
			$("tBody").on("click","input[type='radio']",function () {
				$("#activityName").val($(this).attr("activityName"));
				$("#activityHiddenId").val(this.value);
				$("#searchActivityModal").modal("hide");
			});
		});
		//预计成交日期添加日历
		myDate($("#expectedClosingDate"))
		//给转换按钮添加单击事件
		$("#convertClue").click(function () {
			//收集参数
			let id = "${clue.id}";
			let money = $("#amountOfMoney").val();
			let name = $("#tradeName").val();
			let expectedDate = $("#expectedClosingDate").val();
			let stage = $("#stage").val();
			let source = $("#activityName").val();
			let activityId = $("#activityHiddenId").val();
			let isCreate = $("#isCreateTransaction").prop("checked");
			//发送请求
			$.ajax({
				url:"workbench/convert/saveConvertClue.do",
				data:{
					id:id,
					money:money,
					name:name,
					expectedDate:expectedDate,
					stage:stage,
					source:source,
					activityId:activityId,
					isCreate:isCreate
				},
				type:"post",
				dataType:"json",
				success:function (data) {
					if(data.code == 1){
						window.location.href="workbench/clue/index.do";
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

	<!-- 搜索市场活动的模态窗口 -->
	<div class="modal fade" id="searchActivityModal" role="dialog" >
		<div class="modal-dialog" role="document" style="width: 90%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title">搜索市场活动</h4>
				</div>
				<div class="modal-body">
					<div class="btn-group" style="position: relative; top: 18%; left: 8px;">
						<form class="form-inline" role="form">
						  <div class="form-group has-feedback">
						    <input type="text" id="queryActivityTxt" class="form-control" style="width: 300px;" placeholder="请输入市场活动名称，支持模糊查询">
						    <span class="glyphicon glyphicon-search form-control-feedback"></span>
						  </div>
						</form>
					</div>
					<table id="activityTable" class="table table-hover" style="width: 900px; position: relative;top: 10px;">
						<thead>
							<tr style="color: #B3B3B3;">
								<td></td>
								<td>名称</td>
								<td>开始日期</td>
								<td>结束日期</td>
								<td>所有者</td>
								<td></td>
							</tr>
						</thead>
						<tbody id="tBody">
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

	<div id="title" class="page-header" style="position: relative; left: 20px;">
		<h4>转换线索 <small>${clue.fullname}${clue.appellation}-${clue.company}</small></h4>
	</div>
	<div id="create-customer" style="position: relative; left: 40px; height: 35px;">
		新建客户：${clue.company}
	</div>
	<div id="create-contact" style="position: relative; left: 40px; height: 35px;">
		新建联系人：${clue.fullname}${clue.appellation}
	</div>
	<div id="create-transaction1" style="position: relative; left: 40px; height: 35px; top: 25px;">
		<input type="checkbox" id="isCreateTransaction"/>
		为客户创建交易
	</div>
	<div id="create-transaction2" style="position: relative; left: 40px; top: 20px; width: 80%; background-color: #F7F7F7; display: none;" >

		<form>
		  <div class="form-group" style="width: 400px; position: relative; left: 20px;">
		    <label for="amountOfMoney">金额</label>
		    <input type="text" class="form-control" id="amountOfMoney">
		  </div>
		  <div class="form-group" style="width: 400px;position: relative; left: 20px;">
		    <label for="tradeName">交易名称</label>
		    <input type="text" class="form-control" id="tradeName" value="动力节点-">
		  </div>
		  <div class="form-group" style="width: 400px;position: relative; left: 20px;">
		    <label for="expectedClosingDate">预计成交日期</label>
		    <input type="text" class="form-control" id="expectedClosingDate">
		  </div>
		  <div class="form-group" style="width: 400px;position: relative; left: 20px;">
		    <label for="stage">阶段</label>
		    <select id="stage"  class="form-control">
		    	<option></option>
		    	<c:forEach items="${stageList}" var="s">
					<option value="${s.value}">${s.value}</option>
				</c:forEach>
		    </select>
		  </div>
		  <div class="form-group" style="width: 400px;position: relative; left: 20px;">
		    <label for="activityName">市场活动源&nbsp;&nbsp;<a href="javascript:void(0);" id="convertA" style="text-decoration: none;"><span class="glyphicon glyphicon-search"></span></a></label>
			  <input type="hidden" id="activityHiddenId">
		    <input type="text" class="form-control" id="activityName" placeholder="点击上面搜索" readonly>
		  </div>
		</form>

	</div>

	<div id="owner" style="position: relative; left: 40px; height: 35px; top: 50px;">
		记录的所有者：<br>
		<b>${clue.owner}</b>
	</div>
	<div id="operation" style="position: relative; left: 40px; height: 35px; top: 100px;">
		<input class="btn btn-primary" id="convertClue" type="submit" value="转换">
		&nbsp;&nbsp;&nbsp;&nbsp;
		<input class="btn btn-default" type="button" value="取消">
	</div>
</body>
</html>
