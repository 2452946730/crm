<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	String basePath=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";
%>
<!DOCTYPE html>
<html>
<base href="<%=basePath%>">
<head>
<meta charset="UTF-8">

<link href="jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
<script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>

<script type="text/javascript">

	//默认情况下取消和保存按钮是隐藏的
	var cancelAndSaveBtnDefault = true;

	$(function(){
		$("#remark").focus(function(){
			if(cancelAndSaveBtnDefault){
				//设置remarkDiv的高度为130px
				$("#remarkDiv").css("height","130px");
				//显示
				$("#cancelAndSaveBtn").show("2000");
				cancelAndSaveBtnDefault = false;
			}
		});

		$("#cancelBtn").click(function(){
			//显示
			$("#cancelAndSaveBtn").hide();
			//设置remarkDiv的高度为130px
			$("#remarkDiv").css("height","90px");
			cancelAndSaveBtnDefault = true;
		});

		/*$(".remarkDiv").mouseover(function(){
			$(this).children("div").children("div").show();
		});*/
		$("#clueRemark").on("mouseover",".remarkDiv",function () {
			$(this).children("div").children("div").show();
		})

		/*$(".remarkDiv").mouseout(function(){
			$(this).children("div").children("div").hide();
		});*/
		$("#clueRemark").on("mouseout",".remarkDiv",function () {
			$(this).children("div").children("div").hide();
		})

		/*$(".myHref").mouseover(function(){
			$(this).children("span").css("color","red");
		});*/
		$("#clueRemark").on("mouseover",".myHref",function () {
			$(this).children("span").css("color","red");
		})

		/*$(".myHref").mouseout(function(){
			$(this).children("span").css("color","#E6E6E6");
		});*/
		$("#clueRemark").on("mouseout",".myHref",function () {
			$(this).children("span").css("color","#E6E6E6");
		})
		//给保存按钮添加单击事件
		$("#saveCreateClueRemarkBtn").click(function () {
			//收集参数
			var noteContent=$("#remark").val();
			var clueId="${clue.id}";
			//表单验证
			if(noteContent == ""){
				alert("请输入备注")
				return;
			}
			//发送数据
			$.ajax({
				url:"workbench/clue/saveCreateClueRemark.do",
				data:{
					noteContent:noteContent,
					clueId:clueId
				},
				type:"post",
				dataType:"json",
				success:function (data) {
					if(data.code==1){
						//清空输入框,刷新备注列表
						$("#remark").val("");
						var str="";
						str+="<div id=\"div_"+data.date.id+"\" class=\"remarkDiv\" style=\"height: 60px;\">";
						str+="<img title=\"${clue.owner}\" src=\"image/user-thumbnail.png\" style=\"width: 30px; height:30px;\">";
						str+="<div style=\"position: relative; top: -40px; left: 40px;\" >";
						str+="<h5>"+noteContent+"</h5>";
						str+="<font color=\"gray\">线索</font> <font color=\"gray\">-</font> <b>${clue.fullname}${clue.appellation}-${clue.company}</b> <small style=\"color: gray;\"> "+data.date.createTime+" 由${clue.owner}创建</small>";
						str+="<div style=\"position: relative; left: 500px; top: -30px; height: 30px; width: 100px; display: none;\">";
						str+="<a class=\"myHref\" name=\"editA\" remarkId=\""+data.date.id+"\" href=\"javascript:void(0);\"><span class=\"glyphicon glyphicon-edit\" style=\"font-size: 20px; color: #E6E6E6;\"></span></a>";
						str+="&nbsp;&nbsp;&nbsp;&nbsp;";
						str+="<a class=\"myHref\" name=\"deleteA\" remarkId=\""+data.date.id+"\" href=\"javascript:void(0);\"><span class=\"glyphicon glyphicon-remove\" style=\"font-size: 20px; color: #E6E6E6;\"></span></a>";
						str+="</div>";
						str+="</div>";
						str+="</div>";

						$("#remarkDiv").before(str);
					}else{
						//打印信息
						alert(data.message);
					}
				}
			});
		});
		//删除备注
		$("#clueRemark").on("click","a[name='deleteA']",function () {
			var id=$(this).attr("remarkId");
			$.ajax({
				url:"workbench/clue/deleteClueRemarkById.do",
				data:{
					id:id
				},
				type:"post",
				dataType:"json",
				success:function (data) {
					if(data.code==1){
						//刷新备注列表
						$("#div_"+id).remove();
					}else{
						alert(data.message);
					}
				}
			});
		});
		//修改线索的备注
		$("#clueRemark").on("click","a[name='editA']",function () {
			//收集参数
			let id = $(this).attr("remarkId");
			$("#edit-id").val(id);
			$("#edit-noteContent").val($("#div_"+id+" h5").text());
			//弹出模态窗口
			$("#editRemarkModal").modal("show");
			//给更新按钮添加单击事件
			$("#updateRemarkBtn").click(function () {
				let id = $("#edit-id").val();
				let noteContent = $("#edit-noteContent").val();
				if(!noteContent){
					alert("备注不能为空!")
					return;
				}
				//发送请求
				$.ajax({
					url:"workbench/clue/updateClueRemarkById.do",
					data:{
						id:id,
						noteContent:noteContent
					},
					type:"post",
					dataType:"json",
					success:function (data) {
						if(data.code == 1){
							//关闭模态窗口
							$("#editRemarkModal").modal("hide");
							//刷新备注列表
							$("#div_"+id+" h5").text(data.date.noteContent);
							$("#div_"+id+" small").text(" "+data.date.editTime+" 由${sessionScope.sessionUser.name}修改")
						} else {
							//打印信息
							//模态窗口不关闭
							$("#editRemarkModal").modal("show");
						}
					}
				});
			});
		});
		//给关联市场活动按钮添加单击事件
		$("#relationalA").click(function () {
			//初始化
			//将文本框和表格清空
			$("#activityName").val("");
			$("#tBody").html("");
			//弹出模态窗口
			$("#bundModal").modal("show");
			$("#activityName").keyup(function (){
				//收集参数
				let clueId = "${clue.id}"
				let activityName = $("#activityName").val();
					//发送请求
				$.ajax({
					url:"workbench/clue/queryActivityByNameClueId.do",
					data:{
						clueId:clueId,
						activityName:activityName
					},
					type:"post",
					dataType:"json",
					success:function (data){
						let str = "";
						$.each(data,function (index, obj) {
							str+="<tr>";
							str+="<td><input value=\""+obj.id+"\"  type=\"checkbox\"/></td>";
							str+="<td>"+obj.name+"</td>";
							str+="<td>"+obj.startDate+"</td>";
							str+="<td>"+obj.endDate+"</td>";
							str+="	<td>"+obj.owner+"</td>";
							str+="</tr>";
						})
						$("#tBody").html(str);
					}
				});
			});
		});
		//给关联按钮添加单价事件
		$("#saveRelationBtn").click(function () {
			//初始化
			//收集参数
			let activityIds = $("#tBody input[type='checkbox']:checked");
			let clueId = "${clue.id}"
			//表单验证
			if(activityIds.size == 0){
				alert("请选择要关联的市场活动")
				return;
			}
			let ids="";
			$.each(activityIds,function () {
				ids+="activityId="+this.value+"&";
			})
			ids+="clueId="+clueId;
			//发送请求
			$.ajax({
				url:"workbench/clue/saveBound.do",
				data:ids,
				type:"post",
				dataType:"json",
				success:function (data){
					if(data.code == 1){
						//关闭模态窗口,
						$("#bundModal").modal("hide");
						//刷新已经关联过的市场活动列表
						let str = "";
						$.each(data.date,function (index, obj) {
							str+="<tr id=\"tr_"+obj.id+"\">";
							str+="<td>"+obj.name+"</td>";
							str+="<td>"+obj.startDate+"</td>";
							str+="<td>"+obj.endDate+"</td>";
							str+="<td>"+obj.owner+"</td>";
							str+="<td><a href=\"javascript:void(0);\" activityId=\""+obj.id+"\"  style=\"text-decoration: none;\"><span class=\"glyphicon glyphicon-remove\"></span>解除关联</a></td>";
							str+="</tr>";
						})
						$("#tBody2").append(str);
					} else {
						alert(data.message);
						$("#bundModal").modal("show");
					}
				}
			});
		});
		//添加关联市场活动模态窗口的全选和取消全选
		$("#checkedAll").click(function () {
			$("#tBody input[type='checkbox']").prop("checked",this.checked);
		});
		$("#tBody").on("click","input[type='checkbox']",function () {
			$("#checkedAll").prop("checked",$("#tBody input[type='checkbox']").size() == $("#tBody input[type='checkbox']:checked").size())
		});
		//给解除关联按钮添加单击事件
		$("#tBody2").on("click","a",function () {
			//收集参数
			let activityId = $(this).attr("activityId");
			let clueId = "${clue.id}";
			if(window.confirm("确认删除吗?")){
				//发送请求
				$.ajax({
					url:"workbench/clue/saveUnBound.do",
					data:{
						activityId:activityId,
						clueId:clueId
					},
					type:"post",
					dataType:"json",
					success:function (data) {
						if(data.code == 1){
							$("#tr_"+activityId).remove();
						} else {
							alert(data.message);
						}
					}
				});
			}
		});
		//给转换按钮添加单击事件
		$("#toConvert").click(function () {
			window.location.href="workbench/clue/toConvert.do?id=${clue.id}";
		});
	});

</script>

</head>
<body>
<!-- 修改线索备注的模态窗口 -->
<div class="modal fade" id="editRemarkModal" role="dialog">
	<%-- 备注的id --%>
	<input type="hidden" id="remarkId">
	<div class="modal-dialog" role="document" style="width: 40%;">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">
					<span aria-hidden="true">×</span>
				</button>
				<h4 class="modal-title" id="myModalLabel">修改备注</h4>
			</div>
			<div class="modal-body">
				<input type="hidden" id="edit-id">
				<form class="form-horizontal" role="form">
					<div class="form-group">
						<label for="edit-noteContent" class="col-sm-2 control-label">内容</label>
						<div class="col-sm-10" style="width: 81%;">
							<textarea class="form-control" rows="3" id="edit-noteContent"></textarea>
						</div>
					</div>
				</form>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
				<button type="button" class="btn btn-primary" id="updateRemarkBtn">更新</button>
			</div>
		</div>
	</div>
</div>

	<!-- 关联市场活动的模态窗口 -->
	<div class="modal fade" id="bundModal" role="dialog">
		<div class="modal-dialog" role="document" style="width: 80%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title">关联市场活动</h4>
				</div>
				<div class="modal-body">
					<div class="btn-group" style="position: relative; top: 18%; left: 8px;">
						<form class="form-inline" role="form">
						  <div class="form-group has-feedback">
						    <input type="text" id="activityName" class="form-control" style="width: 300px;" placeholder="请输入市场活动名称，支持模糊查询">
						    <span class="glyphicon glyphicon-search form-control-feedback"></span>
						  </div>
						</form>
					</div>
					<table id="activityTable" class="table table-hover" style="width: 900px; position: relative;top: 10px;">
						<thead>
							<tr style="color: #B3B3B3;">
								<td><input id="checkedAll" type="checkbox"/></td>
								<td>名称</td>
								<td>开始日期</td>
								<td>结束日期</td>
								<td>所有者</td>
								<td></td>
							</tr>
						</thead>
						<tbody id="tBody">
							<%--<tr>
								<td><input type="checkbox"/></td>
								<td>发传单</td>
								<td>2020-10-10</td>
								<td>2020-10-20</td>
								<td>zhangsan</td>
							</tr>
							<tr>
								<td><input type="checkbox"/></td>
								<td>发传单</td>
								<td>2020-10-10</td>
								<td>2020-10-20</td>
								<td>zhangsan</td>
							</tr>--%>
						</tbody>
					</table>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
					<button type="button" class="btn btn-primary" id="saveRelationBtn">关联</button>
				</div>
			</div>
		</div>
	</div>


	<!-- 返回按钮 -->
	<div style="position: relative; top: 35px; left: 10px;">
		<a href="javascript:void(0);" onclick="window.history.back();"><span class="glyphicon glyphicon-arrow-left" style="font-size: 20px; color: #DDDDDD"></span></a>
	</div>

	<!-- 大标题 -->
	<div style="position: relative; left: 40px; top: -30px;">
		<div class="page-header">
			<h3>李四先生 <small>动力节点</small></h3>
		</div>
		<div style="position: relative; height: 50px; width: 500px;  top: -72px; left: 700px;">
			<button type="button" class="btn btn-default" id="toConvert"><span class="glyphicon glyphicon-retweet"></span> 转换</button>

		</div>
	</div>

	<br/>
	<br/>
	<br/>

	<!-- 详细信息 -->
	<div style="position: relative; top: -70px;">
		<div style="position: relative; left: 40px; height: 30px;">
			<div style="width: 300px; color: gray;">名称</div>
			<div style="width: 300px;position: relative; left: 200px; top: -20px;"><b>${clue.fullname}${clue.appellation}</b></div>
			<div style="width: 300px;position: relative; left: 450px; top: -40px; color: gray;">所有者</div>
			<div style="width: 300px;position: relative; left: 650px; top: -60px;"><b>${clue.owner}</b></div>
			<div style="height: 1px; width: 400px; background: #D5D5D5; position: relative; top: -60px;"></div>
			<div style="height: 1px; width: 400px; background: #D5D5D5; position: relative; top: -60px; left: 450px;"></div>
		</div>
		<div style="position: relative; left: 40px; height: 30px; top: 10px;">
			<div style="width: 300px; color: gray;">公司</div>
			<div style="width: 300px;position: relative; left: 200px; top: -20px;"><b>${clue.company}</b></div>
			<div style="width: 300px;position: relative; left: 450px; top: -40px; color: gray;">职位</div>
			<div style="width: 300px;position: relative; left: 650px; top: -60px;"><b>${clue.job}</b></div>
			<div style="height: 1px; width: 400px; background: #D5D5D5; position: relative; top: -60px;"></div>
			<div style="height: 1px; width: 400px; background: #D5D5D5; position: relative; top: -60px; left: 450px;"></div>
		</div>
		<div style="position: relative; left: 40px; height: 30px; top: 20px;">
			<div style="width: 300px; color: gray;">邮箱</div>
			<div style="width: 300px;position: relative; left: 200px; top: -20px;"><b>${clue.email}</b></div>
			<div style="width: 300px;position: relative; left: 450px; top: -40px; color: gray;">公司座机</div>
			<div style="width: 300px;position: relative; left: 650px; top: -60px;"><b>${clue.phone}</b></div>
			<div style="height: 1px; width: 400px; background: #D5D5D5; position: relative; top: -60px;"></div>
			<div style="height: 1px; width: 400px; background: #D5D5D5; position: relative; top: -60px; left: 450px;"></div>
		</div>
		<div style="position: relative; left: 40px; height: 30px; top: 30px;">
			<div style="width: 300px; color: gray;">公司网站</div>
			<div style="width: 300px;position: relative; left: 200px; top: -20px;"><b>${clue.website}</b></div>
			<div style="width: 300px;position: relative; left: 450px; top: -40px; color: gray;">手机</div>
			<div style="width: 300px;position: relative; left: 650px; top: -60px;"><b>${clue.mphone}</b></div>
			<div style="height: 1px; width: 400px; background: #D5D5D5; position: relative; top: -60px;"></div>
			<div style="height: 1px; width: 400px; background: #D5D5D5; position: relative; top: -60px; left: 450px;"></div>
		</div>
		<div style="position: relative; left: 40px; height: 30px; top: 40px;">
			<div style="width: 300px; color: gray;">线索状态</div>
			<div style="width: 300px;position: relative; left: 200px; top: -20px;"><b>${clue.state}</b></div>
			<div style="width: 300px;position: relative; left: 450px; top: -40px; color: gray;">线索来源</div>
			<div style="width: 300px;position: relative; left: 650px; top: -60px;"><b>${clue.source}</b></div>
			<div style="height: 1px; width: 400px; background: #D5D5D5; position: relative; top: -60px;"></div>
			<div style="height: 1px; width: 400px; background: #D5D5D5; position: relative; top: -60px; left: 450px;"></div>
		</div>
		<div style="position: relative; left: 40px; height: 30px; top: 50px;">
			<div style="width: 300px; color: gray;">创建者</div>
			<div style="width: 500px;position: relative; left: 200px; top: -20px;"><b>${clue.createBy}&nbsp;&nbsp;</b><small style="font-size: 10px; color: gray;">${clue.createTime}</small></div>
			<div style="height: 1px; width: 550px; background: #D5D5D5; position: relative; top: -20px;"></div>
		</div>
		<div style="position: relative; left: 40px; height: 30px; top: 60px;">
			<div style="width: 300px; color: gray;">修改者</div>
			<div style="width: 500px;position: relative; left: 200px; top: -20px;"><b>${clue.editBy}&nbsp;&nbsp;</b><small style="font-size: 10px; color: gray;">${clue.editTime}</small></div>
			<div style="height: 1px; width: 550px; background: #D5D5D5; position: relative; top: -20px;"></div>
		</div>
		<div style="position: relative; left: 40px; height: 30px; top: 70px;">
			<div style="width: 300px; color: gray;">描述</div>
			<div style="width: 630px;position: relative; left: 200px; top: -20px;">
				<b>
					${clue.description}
				</b>
			</div>
			<div style="height: 1px; width: 850px; background: #D5D5D5; position: relative; top: -20px;"></div>
		</div>
		<div style="position: relative; left: 40px; height: 30px; top: 80px;">
			<div style="width: 300px; color: gray;">联系纪要</div>
			<div style="width: 630px;position: relative; left: 200px; top: -20px;">
				<b>
					${clue.contactSummary}
				</b>
			</div>
			<div style="height: 1px; width: 850px; background: #D5D5D5; position: relative; top: -20px;"></div>
		</div>
		<div style="position: relative; left: 40px; height: 30px; top: 90px;">
			<div style="width: 300px; color: gray;">下次联系时间</div>
			<div style="width: 300px;position: relative; left: 200px; top: -20px;"><b>${clue.nextContactTime}</b></div>
			<div style="height: 1px; width: 400px; background: #D5D5D5; position: relative; top: -20px; "></div>
		</div>
        <div style="position: relative; left: 40px; height: 30px; top: 100px;">
            <div style="width: 300px; color: gray;">详细地址</div>
            <div style="width: 630px;position: relative; left: 200px; top: -20px;">
                <b>
                    ${clue.address}
                </b>
            </div>
            <div style="height: 1px; width: 850px; background: #D5D5D5; position: relative; top: -20px;"></div>
        </div>
	</div>

	<!-- 备注 -->
	<div id="clueRemark" style="position: relative; top: 40px; left: 40px;">
		<div class="page-header">
			<h4>备注</h4>
		</div>
		<c:forEach items="${remarkList}" var="re" >
			<div id="div_${re.id}" class="remarkDiv" style="height: 60px;">
				<img title="${clue.owner}" src="image/user-thumbnail.png" style="width: 30px; height:30px;">
				<div style="position: relative; top: -40px; left: 40px;" >
					<h5>${re.noteContent}</h5>
					<font color="gray">线索</font> <font color="gray">-</font> <b>${clue.fullname}${clue.appellation}-${clue.company}</b> <small style="color: gray;"> ${re.editFlag==0?re.createBy:re.editBy} 由${re.editFlag==0?re.createTime:re.editTime}${re.editFlag==0?"创建":"修改"}</small>
					<div style="position: relative; left: 500px; top: -30px; height: 30px; width: 100px; display: none;">
						<a class="myHref" name="editA" remarkId="${re.id}" href="javascript:void(0);"><span class="glyphicon glyphicon-edit" style="font-size: 20px; color: #E6E6E6;"></span></a>
						&nbsp;&nbsp;&nbsp;&nbsp;
						<a class="myHref" name="deleteA" remarkId="${re.id}" href="javascript:void(0);"><span class="glyphicon glyphicon-remove" style="font-size: 20px; color: #E6E6E6;"></span></a>
					</div>
				</div>
			</div>
		</c:forEach>

		<!-- 备注1 -->
		<%--<div class="remarkDiv" style="height: 60px;">
			<img title="zhangsan" src="image/user-thumbnail.png" style="width: 30px; height:30px;">
			<div style="position: relative; top: -40px; left: 40px;" >
				<h5>哎呦！</h5>
				<font color="gray">线索</font> <font color="gray">-</font> <b>李四先生-动力节点</b> <small style="color: gray;"> 2017-01-22 10:10:10 由zhangsan</small>
				<div style="position: relative; left: 500px; top: -30px; height: 30px; width: 100px; display: none;">
					<a class="myHref" href="javascript:void(0);"><span class="glyphicon glyphicon-edit" style="font-size: 20px; color: #E6E6E6;"></span></a>
					&nbsp;&nbsp;&nbsp;&nbsp;
					<a class="myHref" href="javascript:void(0);"><span class="glyphicon glyphicon-remove" style="font-size: 20px; color: #E6E6E6;"></span></a>
				</div>
			</div>
		</div>

		<!-- 备注2 -->
		<div class="remarkDiv" style="height: 60px;">
			<img title="zhangsan" src="image/user-thumbnail.png" style="width: 30px; height:30px;">
			<div style="position: relative; top: -40px; left: 40px;" >
				<h5>呵呵！</h5>
				<font color="gray">线索</font> <font color="gray">-</font> <b>李四先生-动力节点</b> <small style="color: gray;"> 2017-01-22 10:20:10 由zhangsan</small>
				<div style="position: relative; left: 500px; top: -30px; height: 30px; width: 100px; display: none;">
					<a class="myHref" href="javascript:void(0);"><span class="glyphicon glyphicon-edit" style="font-size: 20px; color: #E6E6E6;"></span></a>
					&nbsp;&nbsp;&nbsp;&nbsp;
					<a class="myHref" href="javascript:void(0);"><span class="glyphicon glyphicon-remove" style="font-size: 20px; color: #E6E6E6;"></span></a>
				</div>
			</div>
		</div>--%>

		<div id="remarkDiv" style="background-color: #E6E6E6; width: 870px; height: 90px;">
			<form role="form" style="position: relative;top: 10px; left: 10px;">
				<textarea id="remark" class="form-control" style="width: 850px; resize : none;" rows="2"  placeholder="添加备注..."></textarea>
				<p id="cancelAndSaveBtn" style="position: relative;left: 737px; top: 10px; display: none;">
					<button id="cancelBtn" type="button" class="btn btn-default">取消</button>
					<button type="button" class="btn btn-primary" id="saveCreateClueRemarkBtn">保存</button>
				</p>
			</form>
		</div>
	</div>

	<!-- 市场活动 -->
	<div>
		<div style="position: relative; top: 60px; left: 40px;">
			<div class="page-header">
				<h4>市场活动</h4>
			</div>
			<div style="position: relative;top: 0px;">
				<table class="table table-hover" style="width: 900px;">
					<thead>
						<tr style="color: #B3B3B3;">
							<td>名称</td>
							<td>开始日期</td>
							<td>结束日期</td>
							<td>所有者</td>
							<td></td>
						</tr>
					</thead>
					<tbody id="tBody2">
					<c:forEach items="${activityList}" var="act">
						<tr id="tr_${act.id}">
							<td>${act.name}</td>
							<td>${act.startDate}</td>
							<td>${act.endDate}</td>
							<td>${act.owner}</td>
							<td><a href="javascript:void(0);" activityId="${act.id}"  style="text-decoration: none;"><span class="glyphicon glyphicon-remove"></span>解除关联</a></td>
						</tr>
					</c:forEach>
						<%--<tr>
							<td>发传单</td>
							<td>2020-10-10</td>
							<td>2020-10-20</td>
							<td>zhangsan</td>
							<td><a href="javascript:void(0);"  style="text-decoration: none;"><span class="glyphicon glyphicon-remove"></span>解除关联</a></td>
						</tr>
						<tr>
							<td>发传单</td>
							<td>2020-10-10</td>
							<td>2020-10-20</td>
							<td>zhangsan</td>
							<td><a href="javascript:void(0);"  style="text-decoration: none;"><span class="glyphicon glyphicon-remove"></span>解除关联</a></td>
						</tr>--%>
					</tbody>
				</table>
			</div>

			<div>
				<a href="javascript:void(0);" id="relationalA" style="text-decoration: none;"><span class="glyphicon glyphicon-plus"></span>关联市场活动</a>
			</div>
		</div>
	</div>


	<div style="height: 200px;"></div>
</body>
</html>
