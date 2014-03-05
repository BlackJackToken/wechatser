<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>

<html>
<head>
    <title></title>
    <script type="text/javascript" src="<%=basePath%>/html/jquery.min.js"></script>

</head>
<body>
    <input id="basePath" type="hidden" value="<%=basePath%>" />
    <h1>ddd</h1>

</body>
</html>
<script type="text/javascript">
    $(function(){
        var data="<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+
                "<xml>"+
                "<ToUserName><![CDATA[toUser]]></ToUserName>"+
                "<FromUserName><![CDATA[fromUser]]></FromUserName>"+
                "<CreateTime>1348831860</CreateTime>"+
                "<MsgType><![CDATA[text]]></MsgType>"+
                "<Content><![CDATA[你好啊]]></Content>"+
                "<MsgId>1234567890123456</MsgId>"+
                "</xml>"
        $.ajax(
                {
                    url:"${pageContext.request.contextPath}/bar/internetbarweixin.do",
                    dataType:"xml",
                    processData: false,
                    type:"POST",
                    data:data,
                    success:function(data){
                        document.write(data+"");
                    }
                }
        );
    });
</script>