<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>登录</title>
</head>
<body>
    <form action="/login" method="post">
        用户名：<input type="text" name="username"><br>
        密码：<input   type="password" name="password">
        <input type="submit" value="提交">
    </form>
</body>
</html>