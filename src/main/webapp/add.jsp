<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>添加</title>
<script type="text/javascript">
	function check() {
		alert("aha");
		if (document.getElementById("name").value != "") {
			if (document.getElementById("description").value != "") {
				if (!isNaN(document.getElementById("avgscore").value)) {
					if (IsDate(document.getElementById("birthday").value)) {
						return true;  
					}else{
						alert("请保证birthday中输入的日期格式为yyyy-mm-dd或正确的日期!");
						return false;
					}
				}else{
					alert("平均分只能是数字");
					return false;
				}
			}else{
				alert("备注不能为空");
				return false;
			}
		}else{
			alert("姓名不能为空");
			return false;
		}
	}
	function IsDate(mystring) {  
		var reg = /^(\d{4})-(\d{2})-(\d{2})$/;  
		var str = mystring;  
		var arr = reg.exec(str);  
		if (str=="") return true;  
		if (!reg.test(str)&&RegExp.$2<=12&&RegExp.$3<=31){  
		  
		return false;  
		}  
		return true;  
		} 
</script>
</head>

<body>
	<form action="AddServlet" method="post" onsubmit="return check();">
	 <input type="hidden" id="id" value="${student.id}" name="id">
	<table>
	<tr><td>birthday(yyyy-MM-dd格式):</td><td><input type="text" id="birthday" name="birthday" value="${student.birthday}"></td></tr>
	<tr><td>avgscore:</td><td><input type="text" id="avgscore" name="avgscore" value="${student.avgscore}"></td></tr>
	<tr><td>description:</td><td><input type="text" id="description" name="description" value="${student.description}"></td></tr>
	<tr><td>name:</td><td><input type="text" id="name" name="name" value="${student.name}"></td></tr></table>
   
    

    <input type="submit" value="提交">
</form>
</body>
</html>