<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	String basePath=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";
%>
<html>
<head>
	<base href="<%=basePath%>">
	<meta charset="UTF-8">

	<link href="jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
	<link href="jquery/bootstrap-datetimepicker-master/css/bootstrap-datetimepicker.min.css" type="text/css" rel="stylesheet" />

	<script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
	<script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/js/bootstrap-datetimepicker.js"></script>
	<script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/locale/bootstrap-datetimepicker.zh-CN.js"></script>
	<%--引入pagination插件--%>
	<link type="text/css" rel="stylesheet" href="jquery/bs_pagination-master/css/jquery.bs_pagination.min.css" />
	<script type="text/javascript" src="jquery/bs_pagination-master/js/jquery.bs_pagination.min.js"></script>
	<script type="text/javascript" src="jquery/bs_pagination-master/localization/en.js"></script>

<script type="text/javascript">

	$(function(){
		queryTranByConditionForPage(1,10);
		//给查询按钮添加单击事件
		$("#queryTranBtn").click(function () {
			queryTranByConditionForPage(1,$("#pagination").bs_pagination("getOption","rowsPerPage"))
		});
		//全选和取消全选
		$("#checkedAll").click(function () {
			$("#tBody input[type='checkbox']").prop("checked",this.checked)
		});
		$("#tBody").on("click","input[type='checkbox']",function () {
			$("#checkedAll").prop("checked",$("#tBody input[type='checkbox']").size() == $("#tBody input[type='checkbox']:checked").size());
		});
		//跟创建按钮添加单击事件
		$("#saveCreateTranBtn").click(function () {
			window.location.href="workbench/transaction/save.do";
		});

	});
	function queryTranByConditionForPage(pageNo,pageSize){
		//收集参数
		let owner=$("#query-owner").val();
		let name =$("#query-name").val();
		let customerName=$("#query-customerName").val();
		/*let stage=$("#query-stage option:selected").text();
		let type=$("#query-type option:selected").text();
		let source=$("#query-source option:selected").text();*/
		let stage=$("#query-stage").val();
		let type=$("#query-type").val();
		let source=$("#query-source").val();
		let contactsName=$("#query-contactsName").val();
		//表单验证
		//发送请求
		$.ajax({
			url:"workbench/transaction/queryTranByConditionForPage.do",
			data:{
				owner:owner,
				name:name,
				customerName:customerName,
				stage:stage,
				type:type,
				source:source,
				contactsName:contactsName,
				pageNo:pageNo,
				pageSize:pageSize
			},
			type:'post',
			dataType:"json",
			success:function (data) {
				let str = "";
				$.each(data.tranList,function(index,obj){
					str+="<tr>";
					str+="<td><input value=\""+obj.id+"\" type=\"checkbox\" /></td>";
					str+="<td><a style=\"text-decoration: none; cursor: pointer;\" onclick=\"window.location.href='detail.html';\">"+obj.name+"</a></td>";
					str+="<td>"+obj.customerId+"</td>";
					str+="<td>"+obj.stage+"</td>";
					str+="<td>"+obj.type+"</td>";
					str+="<td>"+obj.owner+"</td>";
					str+="<td>"+obj.source+"</td>";
					str+="<td>"+obj.contactsId+"</td>";
					str+="</tr>";
				});
				$("#tBody").html(str);
				//每次页面刷新取消全选
				$("#checkedAll").prop("checked",false);
				//分页插件
				//计算总页数
				var totalPages=1;
				if(data.totalRows%pageSize ==0){
					totalPages=data.totalRows/pageSize;
				}else{
					totalPages=parseInt(data.totalRows/pageSize)+1;
				}
				//调用工具函数,完成分页查询
				$("#pagination").bs_pagination({
					totalPages:totalPages,
					currentPage: pageNo,
					rowsPerPage: pageSize,
					totalRows: data.totalRows,
					visiblePageLinks: 5,

					showGoToPage: true,
					showRowsPerPage: true,
					showRowsInfo: true,
					onChangePage: function(event,pageObj) {// returns page_num and rows_per_page after a link has clicked
						queryTranByConditionForPage(pageObj.currentPage,pageObj.rowsPerPage);
					}
				});
			}
		});

	}

</script>
</head>
<body>



	<div>
		<div style="position: relative; left: 10px; top: -10px;">
			<div class="page-header">
				<h3>交易列表</h3>
			</div>
		</div>
	</div>

	<div style="position: relative; top: -20px; left: 0px; width: 100%; height: 100%;">

		<div style="width: 100%; position: absolute;top: 5px; left: 10px;">

			<div class="btn-toolbar" role="toolbar" style="height: 80px;">
				<form class="form-inline" role="form" style="position: relative;top: 8%; left: 5px;">

				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">所有者</div>
				      <input class="form-control" id="query-owner" type="text">
				    </div>
				  </div>

				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">名称</div>
				      <input class="form-control" id="query-name" type="text">
				    </div>
				  </div>

				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">客户名称</div>
				      <input class="form-control" id="query-customerName" type="text">
				    </div>
				  </div>

				  <br>

				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">阶段</div>
					  <select id="query-stage" class="form-control">
					  	<option></option>
					  	<c:forEach items="${stageList}" var="s" >
							<option value="${s.id}">${s.value}</option>
						</c:forEach>
					  </select>
				    </div>
				  </div>

				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">类型</div>
					  <select id="query-type" class="form-control">
					  	<option></option>
					  	<c:forEach items="${transactionTypeList}" var="t">
							<option value="${t.id}">${t.value}</option>
						</c:forEach>
					  </select>
				    </div>
				  </div>

				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">来源</div>
				      <select class="form-control" id="query-source">
						  <option></option>
						 <c:forEach items="${sourceList}" var="s">
							 <option value="${s.id}">${s.value}</option>
						 </c:forEach>
						</select>
				    </div>
				  </div>

				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">联系人名称</div>
				      <input class="form-control" id="query-contactsName" type="text">
				    </div>
				  </div>

				  <button type="button" id="queryTranBtn" class="btn btn-default">查询</button>

				</form>
			</div>
			<div class="btn-toolbar" role="toolbar" style="background-color: #F7F7F7; height: 50px; position: relative;top: 10px;">
				<div class="btn-group" style="position: relative; top: 18%;">
				  <button type="button" class="btn btn-primary" id="saveCreateTranBtn"><span class="glyphicon glyphicon-plus"></span> 创建</button>
				  <button type="button" class="btn btn-default" onclick="window.location.href='edit.html';"><span class="glyphicon glyphicon-pencil"></span> 修改</button>
				  <button type="button" class="btn btn-danger"><span class="glyphicon glyphicon-minus"></span> 删除</button>
				</div>


			</div>
			<div style="position: relative;top: 10px;">
				<table class="table table-hover">
					<thead>
						<tr style="color: #B3B3B3;">
							<td><input id="checkedAll" type="checkbox" /></td>
							<td>名称</td>
							<td>客户名称</td>
							<td>阶段</td>
							<td>类型</td>
							<td>所有者</td>
							<td>来源</td>
							<td>联系人名称</td>
						</tr>
					</thead>
					<tbody id="tBody">
						<%--<tr>
							<td><input type="checkbox" /></td>
							<td><a style="text-decoration: none; cursor: pointer;" onclick="window.location.href='detail.html';">动力节点-交易01</a></td>
							<td>动力节点</td>
							<td>谈判/复审</td>
							<td>新业务</td>
							<td>zhangsan</td>
							<td>广告</td>
							<td>李四</td>
						</tr>
                        <tr class="active">
                            <td><input type="checkbox" /></td>
                            <td><a style="text-decoration: none; cursor: pointer;" onclick="window.location.href='detail.html';">动力节点-交易01</a></td>
                            <td>动力节点</td>
                            <td>谈判/复审</td>
                            <td>新业务</td>
                            <td>zhangsan</td>
                            <td>广告</td>
                            <td>李四</td>
                        </tr>--%>
					</tbody>
				</table>
				<div id="pagination"></div>
			</div>

			<%--<div style="height: 50px; position: relative;top: 20px;">
				<div>
					<button type="button" class="btn btn-default" style="cursor: default;">共<b>50</b>条记录</button>
				</div>
				<div class="btn-group" style="position: relative;top: -34px; left: 110px;">
					<button type="button" class="btn btn-default" style="cursor: default;">显示</button>
					<div class="btn-group">
						<button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
							10
							<span class="caret"></span>
						</button>
						<ul class="dropdown-menu" role="menu">
							<li><a href="#">20</a></li>
							<li><a href="#">30</a></li>
						</ul>
					</div>
					<button type="button" class="btn btn-default" style="cursor: default;">条/页</button>
				</div>
				<div style="position: relative;top: -88px; left: 285px;">
					<nav>
						<ul class="pagination">
							<li class="disabled"><a href="#">首页</a></li>
							<li class="disabled"><a href="#">上一页</a></li>
							<li class="active"><a href="#">1</a></li>
							<li><a href="#">2</a></li>
							<li><a href="#">3</a></li>
							<li><a href="#">4</a></li>
							<li><a href="#">5</a></li>
							<li><a href="#">下一页</a></li>
							<li class="disabled"><a href="#">末页</a></li>
						</ul>
					</nav>
				</div>
			</div>--%>

		</div>

	</div>
</body>
</html>
