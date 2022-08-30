<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String basePath=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";
%>
<html>
<head>
    <base href="<%=basePath%>" >
    <meta charset="UTF-8">
    <script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
    <title>正则表达式验证</title>
    <script type="text/javascript">
        $(function (){
            var regEmail=/^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;
            var regPhone=/^(13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|18[0|1|2|3|5|6|7|8|9])\d{8}$/;
            var regMphone=/\d{3}-\d{8}|\d{4}-\d{7}/;
            $("#btn").click(function () {
                var email=$("#email").val();
                var phone=$("#phone").val();
                var mphone=$("#mphone").val();
                if(!regEmail.test(email)){
                    alert("邮箱不合法")
                }
                if(!regPhone.test(phone)){
                    alert("手机号不合法")
                }
                if(!regMphone.test(mphone)){
                    alert("电话不合法")
                }
            });
        });
    </script>
</head>
<body>
<form action="" >
    <input type="text" id="email">
    <input type="text" id="phone">
    <input type="text" id="mphone">
    <input type="button" id="btn" value="提交">
</form>
</body>
</html>
