<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	String basePath=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";
%>
<!doctype html>
<html>
<head>
	<base href="<%=basePath%>" >
<meta charset="UTF-8">
<%--引入bootstrap插件--%>
<link href="jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
<%--引入日期插件--%>
<link href="jquery/bootstrap-datetimepicker-master/css/bootstrap-datetimepicker.min.css" type="text/css" rel="stylesheet" />
<%--引入分页插件--%>
<link href="jquery/bs_pagination-master/css/jquery.bs_pagination.min.css" type="text/css" rel="stylesheet"/>

<%--//引入jquery--%>
<script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
<%--//引入bootstrap框架--%>
<script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
<%--//引入日期插件--%>
<script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/js/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/locale/bootstrap-datetimepicker.zh-CN.js"></script>
<%--//引入分页插件--%>
<script type="text/javascript" src="jquery/bs_pagination-master/js/jquery.bs_pagination.min.js"></script>
<script type="text/javascript" src="jquery/bs_pagination-master/localization/en.js"></script>
<script type="text/javascript">

	$(function(){
		//初始化
		//创建线索的初始化
		queryClueByConditionForPage(1,10);
		myDate($("#create-nextContactTime"));
		//更新的初始化
		myDate($("#edit-nextContactTime"));
		//给创建按钮添加单击事件
		$("#createClueBtn").click(function (){
			//初始化
			$("#saveCreateClueForm")[0].reset();
			//弹出创建线索的模态窗口
			$("#createClueModal").modal("show");
		});
		//给保存按钮添加单击事件
		$("#saveCreateClueBtn").click(function () {
			var regEmail=/^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;
			var regPhone=/\d{3}-\d{8}|\d{4}-\d{7}|\d{7,8}/;
			var regMphone=/^(13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|18[0|1|2|3|5|6|7|8|9])\d{8}$/;
			//获取参数
			var fullname=$.trim($("#create-fullname").val());
			var appellation=$("#create-appellation").val();
			var owner=$("#create-owner").val();
			var company=$.trim($("#create-company").val());
			var job=$.trim($("#create-job").val());
			var email=$.trim($("#create-email").val());
			var phone=$.trim($("#create-phone").val());
			var website=$.trim($("#create-website").val());
			var mphone=$.trim($("#create-mphone").val());
			var state=$("#create-state").val();
			var source=$("#create-source").val();
			var description=$.trim($("#create-description").val());
			var contactSummary=$.trim($("#create-contactSummary").val());
			var nextContactTime=$("#create-nextContactTime").val();
			var address=$.trim($("#create-address").val());
			//表单验证
			if(company == ""){
				alert("公司名称不能为空!");
				return;
			}
			if(appellation == ""){
				alert("称呼不能为空!");
				return;
			}
			if(fullname == ""){
				alert("姓名不能为空!");
				return;
			}
			if(job == ""){
				alert("职位不能为空!");
				return;
			}
			if(!regEmail.test(email)){
				alert("邮箱不合法");
				return;
			}
			if(!regMphone.test(mphone)){
				alert("手机号不合法");
				return;
			}
			if(website == ""){
				alert("公司网站不能为空!");
				return;
			}
			if(!regPhone.test(phone)){
				alert("电话不合法");
				return;
			}
			if(description == ""){
				alert("线所描述不能为空!");
				return;
			}
			if(contactSummary == ""){
				alert("联系纪要不能为空!");
				return;
			}
			if(address == ""){
				alert("详细地址不能为空!");
				return;
			}
			//发送请求
			$.ajax({
				url:"workbench/clue/saveCreateClue.do",
				data:{
					fullname:fullname,
					appellation:appellation,
					owner:owner,
					company:company,
					job:job,
					email:email,
					phone:phone,
					website:website,
					mphone:mphone,
					state:state,
					source:source,
					description:description,
					contactSummary:contactSummary,
					nextContactTime:nextContactTime,
					address:address
				},
				type:"post",
				dataType:"json",
				//处理响应信息
				success:function(data){
					if(data.code==1){
						//关闭模态窗口，刷新线索列表，显示第一页数据，保持每页显示条数不变
						queryClueByConditionForPage(1,$("#pagination").bs_pagination("getOption","rowsPerPage"));
						$("#createClueModal").modal("hide");
					}else{
						alert(data.message);
						//模态窗口不关闭，列表也不刷新
						$("#createClueModal").modal("show");
					}
				}
			});
		});
		//全选和取消全选
		$("#checked-all").click(function (){
			$("#queryClueByConditionForPageB input[type='checkbox']").prop("checked",this.checked);
		});
		$("#queryClueByConditionForPageB").on("click","input[type='checkbox']", function () {
			$("#checked-all").prop("checked",$("#queryClueByConditionForPageB input[type='checkbox']:checked").size()==$("#queryClueByConditionForPageB input[type='checkbox']").size())
		});
		//删除线索
		$("#delete-clue").click(function () {
			var idList=$("#queryClueByConditionForPageB input[type='checkbox']:checked");
			if(idList.size()==0){
				alert("请选择要删除的线索!")
				return;
			}
			if(window.confirm("确认删除吗?亲!")){
				var ids="";
				$.each(idList,function (index,obj){
					ids+="id="+obj.value+"&";
				});
				ids=ids.substr(0,ids.length-1);
				//发送请求
				$.ajax({
					url:"workbench/clue/deleteClueByIds.do",
					data:ids,
					type:"post",
					dataType:"json",
					success:function (data) {
						if(data.code==1){
							//刷新列表
							queryClueByConditionForPage($("#pagination").bs_pagination("getOption","currentPage"),$("#pagination").bs_pagination("getOption","rowsPerPage"));
						}else{
							alert(data.message);
						}
					}
				});
			}
		});
		//给修改按钮绑定单击事件
		$("#editClueBtn").click(function () {
			//获得选择的线索的id
			var ids=$("#queryClueByConditionForPageB input[type='checkbox']:checked");
			if(ids.size()==0){
				alert("请选择要修改的线索!")
				return;
			}else if(ids.size() >1){
				alert("一次只能修改一条线索!")
				return;
			}
			var id=ids.val();
			$.ajax({
				url:"workbench/clue/queryEditClueById.do",
				data:{
					id:id
				},
				type:"post",
				dataType:"json",
				success:function (data) {
					$("#edit-id").val(data.id);
					$("#edit-fullname").val(data.fullname);
					$("#edit-appellation").val(data.appellation);
					$("#edit-company").val(data.company);
					$("#edit-job").val(data.job);
					$("#edit-email").val(data.email);
					$("#edit-phone").val(data.phone);
					$("#edit-website").val(data.website);
					$("#edit-mphone").val(data.mphone);
					$("#edit-state").val(data.state);
					$("#edit-source").val(data.source);
					$("#edit-description").val(data.description);
					$("#edit-contactSummary").val(data.contactSummary);
					$("#edit-nextContactTime").val(data.nextContactTime);
					$("#edit-address").val(data.address);
					//弹出模态窗口
					$("#editClueModal").modal("show");
				}
			});
		});
		//给更新按钮添加单击事件
		$("#saveEditClueBtn").click(function () {
			var regEmail=/^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;
			var regPhone=/\d{3}-\d{8}|\d{4}-\d{7}|\d{7,8}/;
			var regMphone=/^(13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|18[0|1|2|3|5|6|7|8|9])\d{8}$/;
			//收集参数
			var id=$("#edit-id").val();
			var fullname =$.trim($("#edit-fullname").val());
			var appellation =$.trim($("#edit-appellation").val());
			var  owner=$("#edit-owner").val();
			var company =$.trim($("#edit-company").val());
			var job=$.trim($("#edit-job").val());
			var  email=$.trim($("#edit-email").val());
			var phone =$.trim($("#edit-phone").val());
			var  website=$.trim($("#edit-website").val());
			var  mphone=$.trim($("#edit-mphone").val());
			var  state=$("#edit-state").val();
			var  source=$("#edit-source").val();
			var  description=$.trim($("#edit-description").val());
			var  contactSummary=$.trim($("#edit-contactSummary").val());
			var  nextContactTime=$("#edit-nextContactTime").val();
			var  address=$.trim($("#edit-address").val());
			//表单验证
			if(company == ""){
				alert("公司名称不能为空!");
				return;
			}
			if(appellation == ""){
				alert("称呼不能为空!");
				return;
			}
			if(fullname == ""){
				alert("姓名不能为空!");
				return;
			}
			if(job == ""){
				alert("职位不能为空!");
				return;
			}
			if(!regEmail.test(email)){
				alert("邮箱不合法");
				return;
			}
			if(!regMphone.test(mphone)){
				alert("手机号不合法");
				return;
			}
			if(website == ""){
				alert("公司网站不能为空!");
				return;
			}
			if(!regPhone.test(phone)){
				alert("电话不合法");
				return;
			}
			if(description == ""){
				alert("线所描述不能为空!");
				return;
			}
			if(contactSummary == ""){
				alert("联系纪要不能为空!");
				return;
			}
			if(address == ""){
				alert("详细地址不能为空!");
				return;
			}
			//发送请求
			$.ajax({
				url:"workbench/clue/saveEditClue.do",
				data:{
					id:id,
					fullname:fullname,
					appellation:appellation,
					owner:owner,
					company:company,
					job:job,
					email:email,
					phone:phone,
					website:website,
					mphone:mphone,
					state:state,
					source:source,
					description:description,
					contactSummary:contactSummary,
					nextContactTime:nextContactTime,
					address:address
				},
				type:"post",
				dataType:"json",
				success:function (data) {
					if(data.code==1){
						//刷新列表
						queryClueByConditionForPage($("#pagination").bs_pagination("getOption","currentPage"),$("#pagination").bs_pagination("getOption","rowsPerPage"));
						//关闭模态窗口
						$("#editClueModal").modal("hide");
					}else{
						//提示信息
						alert(data.message);
						//不关闭模态窗口
						$("#editClueModal").modal("show");
					}
				}
			});
		});
	});
	//设置日历插件
	function myDate(id){
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
	//查询线索并分页
	function queryClueByConditionForPage(pageNum,pageSize){
		//收集参数
		var fullname=$("#query-fullname").val();
		var company=$("#query-company").val();
		var mphone=$("#query-mphone").val();
		var phone=$("#query-phone").val();
		var source=$("#query-source").val();
		var owner=$("#query-owner").val();
		var state=$("#query-state").val();
		//发送数据
		$.ajax({
			url:"workbench/clue/queryClueByConditionForPage.do",
			data:{
				fullname:fullname,
				company:company,
				mphone:mphone,
				phone:phone,
				source:source,
				owner:owner,
				state:state,
				pageNum:pageNum,
				pageSize:pageSize
			},
			type:"post",
			dataType:"json",
			//响应数据
			success:function (data) {
				var str="";
				$.each(data.clueList,function (index, clue) {
					str+="<tr>";
					str+="<td><input type=\"checkbox\" value=\""+clue.id+"\" /></td>";
					str+="<td><a style=\"text-decoration: none; cursor: pointer;\" onclick=\"window.location.href='workbench/clue/queryClueById.do?id="+clue.id+"';\">"+clue.fullname+clue.appellation+"</a></td>";
					str+="<td>"+clue.company+"</td>";
					str+="<td>"+clue.phone+"</td>";
					str+="<td>"+clue.mphone+"</td>";
					str+="<td>"+clue.source+"</td>";
					str+="<td>"+clue.owner+"</td>";
					str+="<td>"+clue.state+"</td>";
					str+="</tr>";
				});
				$("#queryClueByConditionForPageB").html(str);
				//计算总页数
				var totalPage=1;
				if(data.totalRows%pageSize==0){
					totalPage=data.totalRows/pageSize;
				}else{
					totalPage=parseInt(data.totalRows/pageSize)+1;
				}
				//分页插件
				$("#pagination").bs_pagination({
					totalPages:totalPage,
					currentPage: pageNum,
					rowsPerPage: pageSize,
					totalRows: data.totalRows,
					visiblePageLinks: 5,

					showGoToPage: true,
					showRowsPerPage: true,
					showRowsInfo: true,
					onChangePage: function(event,pageObj) {// returns page_num and rows_per_page after a link has clicked
						queryClueByConditionForPage(pageObj.currentPage,pageObj.rowsPerPage);
					}
				});
				$("#checked-all").prop("checked",false);
			}
		});
	}

</script>
</head>
<body>

	<!-- 创建线索的模态窗口 -->
	<div class="modal fade" id="createClueModal" role="dialog">
		<div class="modal-dialog" role="document" style="width: 90%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">创建线索</h4>
				</div>
				<div class="modal-body">
					<form id="saveCreateClueForm" class="form-horizontal" role="form">

						<div class="form-group">
							<label for="create-owner" class="col-sm-2 control-label">所有者<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="create-owner">
								  <c:forEach items="${userList}" var="u">
									  <option value="${u.id}">${u.name}</option>
								  </c:forEach>
								</select>
							</div>
							<label for="create-company" class="col-sm-2 control-label">公司<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="create-company">
							</div>
						</div>

						<div class="form-group">
							<label for="create-appellation" class="col-sm-2 control-label">称呼</label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="create-appellation">
								  <option></option>
									<c:forEach items="${appellationList}" var="app">
										<option id="${app.id}">${app.value}</option>
									</c:forEach>
								</select>
							</div>
							<label for="create-fullname" class="col-sm-2 control-label">姓名<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="create-fullname">
							</div>
						</div>

						<div class="form-group">
							<label for="create-job" class="col-sm-2 control-label">职位</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="create-job">
							</div>
							<label for="create-email" class="col-sm-2 control-label">邮箱</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="create-email">
							</div>
						</div>

						<div class="form-group">
							<label for="create-phone" class="col-sm-2 control-label">公司座机</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="create-phone">
							</div>
							<label for="create-website" class="col-sm-2 control-label">公司网站</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="create-website">
							</div>
						</div>

						<div class="form-group">
							<label for="create-mphone" class="col-sm-2 control-label">手机</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="create-mphone">
							</div>
							<label for="create-state" class="col-sm-2 control-label">线索状态</label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="create-state">
								  <option></option>
								  <c:forEach items="${clueStateList}" var="cs">
									  <option id="${cs.id}">${cs.value}</option>
								  </c:forEach>
								</select>
							</div>
						</div>

						<div class="form-group">
							<label for="create-source" class="col-sm-2 control-label">线索来源</label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="create-source">
								  <option></option>
								  <c:forEach items="${sourceList}" var="sl">
									  <option id="${sl.id}">${sl.value}</option>
								  </c:forEach>
								</select>
							</div>
						</div>


						<div class="form-group">
							<label for="create-description" class="col-sm-2 control-label">线索描述</label>
							<div class="col-sm-10" style="width: 81%;">
								<textarea class="form-control" rows="3" id="create-description"></textarea>
							</div>
						</div>

						<div style="height: 1px; width: 103%; background-color: #D5D5D5; left: -13px; position: relative;"></div>

						<div style="position: relative;top: 15px;">
							<div class="form-group">
								<label for="create-contactSummary" class="col-sm-2 control-label">联系纪要</label>
								<div class="col-sm-10" style="width: 81%;">
									<textarea class="form-control" rows="3" id="create-contactSummary"></textarea>
								</div>
							</div>
							<div class="form-group">
								<label for="create-nextContactTime" class="col-sm-2 control-label">下次联系时间</label>
								<div class="col-sm-10" style="width: 300px;">
									<input type="text" class="form-control" id="create-nextContactTime" readonly>
								</div>
							</div>
						</div>

						<div style="height: 1px; width: 103%; background-color: #D5D5D5; left: -13px; position: relative; top : 10px;"></div>

						<div style="position: relative;top: 20px;">
							<div class="form-group">
                                <label for="create-address" class="col-sm-2 control-label">详细地址</label>
                                <div class="col-sm-10" style="width: 81%;">
                                    <textarea class="form-control" rows="1" id="create-address"></textarea>
                                </div>
							</div>
						</div>
					</form>

				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary" id="saveCreateClueBtn">保存</button>
				</div>
			</div>
		</div>
	</div>

	<!-- 修改线索的模态窗口 -->
	<div class="modal fade" id="editClueModal" role="dialog">
		<div class="modal-dialog" role="document" style="width: 90%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title">修改线索</h4>
				</div>
				<div class="modal-body">
					<form class="form-horizontal" role="form">
						<input type="hidden" id="edit-id"/>

						<div class="form-group">
							<label for="edit-owner" class="col-sm-2 control-label">所有者<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="edit-owner">
									<c:forEach items="${userList}" var="u">
										<option value="${u.id}">${u.name}</option>
									</c:forEach>
								</select>
							</div>
							<label for="edit-company" class="col-sm-2 control-label">公司<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="edit-company" value="动力节点">
							</div>
						</div>

						<div class="form-group">
							<label for="edit-appellation" class="col-sm-2 control-label">称呼</label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="edit-appellation">
								  <option></option>
									<c:forEach items="${appellationList}" var="app">
										<option id="${app.id}">${app.value}</option>
									</c:forEach>
								</select>
							</div>
							<label for="edit-fullname" class="col-sm-2 control-label">姓名<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="edit-fullname" value="李四">
							</div>
						</div>

						<div class="form-group">
							<label for="edit-job" class="col-sm-2 control-label">职位</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="edit-job" value="CTO">
							</div>
							<label for="edit-email" class="col-sm-2 control-label">邮箱</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="edit-email" value="lisi@bjpowernode.com">
							</div>
						</div>

						<div class="form-group">
							<label for="edit-phone" class="col-sm-2 control-label">公司座机</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="edit-phone" value="010-84846003">
							</div>
							<label for="edit-website" class="col-sm-2 control-label">公司网站</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="edit-website" value="http://www.bjpowernode.com">
							</div>
						</div>

						<div class="form-group">
							<label for="edit-mphone" class="col-sm-2 control-label">手机</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="edit-mphone" value="12345678901">
							</div>
							<label for="edit-state" class="col-sm-2 control-label">线索状态</label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="edit-state">
								  <option></option>
									<c:forEach items="${clueStateList}" var="cs">
										<option id="${cs.id}">${cs.value}</option>
									</c:forEach>
								</select>
							</div>
						</div>

						<div class="form-group">
							<label for="edit-source" class="col-sm-2 control-label">线索来源</label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="edit-source">
								  <option></option>
									<c:forEach items="${sourceList}" var="sl">
										<option id="${sl.id}">${sl.value}</option>
									</c:forEach>
								</select>
							</div>
						</div>

						<div class="form-group">
							<label for="edit-description" class="col-sm-2 control-label">描述</label>
							<div class="col-sm-10" style="width: 81%;">
								<textarea class="form-control" rows="3" id="edit-description">这是一条线索的描述信息</textarea>
							</div>
						</div>

						<div style="height: 1px; width: 103%; background-color: #D5D5D5; left: -13px; position: relative;"></div>

						<div style="position: relative;top: 15px;">
							<div class="form-group">
								<label for="edit-contactSummary" class="col-sm-2 control-label">联系纪要</label>
								<div class="col-sm-10" style="width: 81%;">
									<textarea class="form-control" rows="3" id="edit-contactSummary">这个线索即将被转换</textarea>
								</div>
							</div>
							<div class="form-group">
								<label for="edit-nextContactTime" class="col-sm-2 control-label">下次联系时间</label>
								<div class="col-sm-10" style="width: 300px;">
									<input type="text" class="form-control" id="edit-nextContactTime" value="2017-05-01">
								</div>
							</div>
						</div>

						<div style="height: 1px; width: 103%; background-color: #D5D5D5; left: -13px; position: relative; top : 10px;"></div>

                        <div style="position: relative;top: 20px;">
                            <div class="form-group">
                                <label for="edit-address" class="col-sm-2 control-label">详细地址</label>
                                <div class="col-sm-10" style="width: 81%;">
                                    <textarea class="form-control" rows="1" id="edit-address">北京大兴区大族企业湾</textarea>
                                </div>
                            </div>
                        </div>
					</form>

				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary" id="saveEditClueBtn">更新</button>
				</div>
			</div>
		</div>
	</div>




	<div>
		<div style="position: relative; left: 10px; top: -10px;">
			<div class="page-header">
				<h3>线索列表</h3>
			</div>
		</div>
	</div>

	<div style="position: relative; top: -20px; left: 0px; width: 100%; height: 100%;">

		<div style="width: 100%; position: absolute;top: 5px; left: 10px;">

			<div class="btn-toolbar" role="toolbar" style="height: 80px;">
				<form class="form-inline" role="form" style="position: relative;top: 8%; left: 5px;">

				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">名称</div>
				      <input id="query-fullname" class="form-control" type="text">
				    </div>
				  </div>

				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">公司</div>
				      <input id="query-company" class="form-control" type="text">
				    </div>
				  </div>

				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">公司座机</div>
				      <input id="query-phone" class="form-control" type="text">
				    </div>
				  </div>

				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">线索来源</div>
					  <select id="query-source" class="form-control">
					  	  <option></option>
						  <c:forEach items="${sourceList}" var="sl">
							  <option id="${sl.id}">${sl.value}</option>
						  </c:forEach>
					  </select>
				    </div>
				  </div>

				  <br>

				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">所有者</div>
				      <input id="query-owner" class="form-control" type="text">
				    </div>
				  </div>



				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">手机</div>
				      <input id="query-mphone" class="form-control" type="text">
				    </div>
				  </div>

				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">线索状态</div>
					  <select id="query-state" class="form-control">
					  	<option></option>
						  <c:forEach items="${clueStateList}" var="cs">
							  <option id="${cs.id}">${cs.value}</option>
						  </c:forEach>
					  </select>
				    </div>
				  </div>

				  <button type="submit" class="btn btn-default">查询</button>

				</form>
			</div>
			<div class="btn-toolbar" role="toolbar" style="background-color: #F7F7F7; height: 50px; position: relative;top: 40px;">
				<div class="btn-group" style="position: relative; top: 18%;">
				  <button type="button" class="btn btn-primary" id="createClueBtn"><span class="glyphicon glyphicon-plus"></span> 创建</button>
				  <button type="button" class="btn btn-default" data-toggle="modal" id="editClueBtn" ><span class="glyphicon glyphicon-pencil"></span> 修改</button>
				  <button type="button" id="delete-clue" class="btn btn-danger"><span class="glyphicon glyphicon-minus"></span> 删除</button>
				</div>


			</div>
			<div style="position: relative;top: 50px;">
				<table class="table table-hover">
					<thead>
						<tr style="color: #B3B3B3;">
							<td><input id="checked-all" type="checkbox" /></td>
							<td>名称</td>
							<td>公司</td>
							<td>公司座机</td>
							<td>手机</td>
							<td>线索来源</td>
							<td>所有者</td>
							<td>线索状态</td>
						</tr>
					</thead>
					<tbody id="queryClueByConditionForPageB">
						<%--<tr>
							<td><input type="checkbox" /></td>
							<td><a style="text-decoration: none; cursor: pointer;" onclick="window.location.href='detail.jsp';">李四先生</a></td>
							<td>动力节点</td>
							<td>010-84846003</td>
							<td>12345678901</td>
							<td>广告</td>
							<td>zhangsan</td>
							<td>已联系</td>
						</tr>
                        <tr class="active">
                            <td><input type="checkbox" /></td>
                            <td><a style="text-decoration: none; cursor: pointer;" onclick="window.location.href='detail.jsp';">李四先生</a></td>
                            <td>动力节点</td>
                            <td>010-84846003</td>
                            <td>12345678901</td>
                            <td>广告</td>
                            <td>zhangsan</td>
                            <td>已联系</td>
                        </tr>--%>
					</tbody>
				</table>
				<div id="pagination"></div>
			</div>

			<%--<div style="height: 50px; position: relative;top: 60px;">
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
