<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String basePath=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";
%>
<html>
<head>
    <base href="<%=basePath%>"/>
    <%--引入jquery--%>
    <script type="text/javascript" src="../../main/webapp/jquery/jquery-1.11.1-min.js"></script>
    <%--引入bootstrap框架--%>
    <link type="text/css" rel="stylesheet" href="../../main/webapp/jquery/bootstrap_3.3.0/css/bootstrap.min.css"/>
    <script type="text/javascript" src="../../main/webapp/jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
    <%--引入pagination插件--%>
    <link type="text/css" rel="stylesheet" href="../../main/webapp/jquery/bs_pagination-master/css/jquery.bs_pagination.min.css" />
    <script type="text/javascript" src="../../main/webapp/jquery/bs_pagination-master/js/jquery.bs_pagination.min.js"></script>
    <script type="text/javascript" src="../../main/webapp/jquery/bs_pagination-master/localization/en.js"></script>
    <title>分页插件测试</title>
    <script type="text/javascript">
        $(function () {
            $("#mydiv").bs_pagination({
                totalPages: 100, //总页数,必填
                currentPage: 2, //当前页号
                rowsPerPage: 20, //每页显示总条数
                totalRows: 0, //总条数

                visiblePageLinks: 10,//最多可以显示的卡片数

                showGoToPage: false,
                showRowsPerPage: false,
                showRowsInfo: true,
                onChangePage: function() { // returns page_num and rows_per_page after a link has clicked
                },
            });
        });
    </script>
</head>
<body>
<div id="mydiv"></div>
</body>
</html>
