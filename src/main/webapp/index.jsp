<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title>列表</title>
<style>
ul {
	list-style: none;
}

ul li {
	float: left;
}
</style>

</head>
<body>
	<a href="add.jsp">添加学生</a>
	<table>
		<thead>
			<tr>
				<td>id</td>
				<td>birthday</td>
				<td>avgscore</td>
				<td>description</td>
				<td>name</td>
			</tr>
		</thead>
		<c:forEach items="${list}" var="student">
			<tr>
				<td>${student.id}</td>
				<td>${student.birthday}</td>
				<td>${student.avgscore}</td>
				<td>${student.description}</td>
				<td>${student.name}</td>
				<td><a href="DeleteServlet?id=${student.id}">删除</a></td>
				<td><a href="EditServlet?id=${student.id}">修改</a></td>
			</tr>
		</c:forEach>
	</table>
	<div class="pager">
		<ul>
			<c:if test="${page.hasPrePage}">
				<li><a href="StudentListServlet?currentPage=1">首页</a></li>
				<li><a href="StudentListServlet?currentPage=${page.prePage}">上一页</a></li>
			</c:if>
			<c:forEach begin="1" end="${page.total}" var="each">
				<c:choose>
					<c:when test="${each == page.currentPage}">
						<li class="active"><a style="color: black;">${each}</a></li>
					</c:when>
					<c:when
						test="${each >= (page.currentPage- 2) && each <= (page.currentPage + 2)}">
						<li><a href="StudentListServlet?currentPage=${each}">${each}</a></li>
					</c:when>
				</c:choose>
			</c:forEach>
			<c:if test="${page.hasNextPage}">
				<li><a href="StudentListServlet?currentPage=${page.nextPage}">下一页</a></li>
				<li><a href="StudentListServlet?currentPage=${page.total}">尾页</a></li>
			</c:if>
		</ul>
	</div>
</body>
</html>