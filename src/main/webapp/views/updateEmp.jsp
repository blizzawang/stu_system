<%@ page contentType="text/html;charset=UTF-8" language="java" isErrorPage="true" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>update Emp</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link rel="stylesheet" type="text/css"
			href="${pageContext.request.contextPath}/views/css/style.css" />

        <link rel="stylesheet" href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css">
        <script src="https://cdn.bootcss.com/jquery/2.1.1/jquery.min.js"></script>
        <script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
	</head>

	<body>
		<div id="wrap">
			<div id="top_content">
					<div id="header">
						<div id="rightheader">
							<p>
								2009/11/20
								<br />
							</p>
						</div>
						<div id="topheader">
							<h1 id="title">
								<a href="${pageContext.request.contextPath}/views/stus">回到主页</a>
							</h1>
						</div>
						<div id="navigation">
						</div>
					</div>
				<div id="content">
					<p id="whereami">
					</p>
					<h1>
						更新学生信息:
					</h1>
					<form action="${pageContext.request.contextPath}/views/stu/${student.id}" method="post">
						<table cellpadding="0" cellspacing="0" border="0"
							class="form_table">
							<tr>
								<td valign="middle" align="right">
									id:
								</td>
								<td valign="middle" align="left">
									<%-- 取出需要更新的学生ID --%>
									${student.id}
								</td>
							</tr>
							<tr>
								<td valign="middle" align="right">
									姓名:
								</td>
								<td valign="middle" align="left">
									<%-- 表单回显 --%>
									<input type="text" class="inputgri" name="name" value="${student.name}"/>
								</td>
								<td>
									<span class="label label-danger">${update_name_flag}</span>
								</td>
							</tr>
							<tr>
								<td valign="middle" align="right">
									照片:
								</td>
								<td valign="middle" align="left">
									<input type="file" name="photo" />
								</td>
							</tr>
							<tr>
								<td valign="middle" align="right">
									性别:
								</td>
								<td valign="middle" align="left">
									<input type="text" class="inputgri" name="sex" value="${student.sex}"/>
								</td>
								<td>
									<span class="label label-danger">${update_sex_flag}</span>
								</td>
							</tr>
							<tr>
								<td valign="middle" align="right">
									年龄:
								</td>
								<td valign="middle" align="left">
									<input type="text" class="inputgri" name="age" value="${student.age}"/>
								</td>
								<td>
									<span class="label label-danger">${update_age_flag}${update_age_able_flag}</span>
								</td>
							</tr>
						</table>
						<p>
							<input type="hidden" name="_method" value="PUT">
							<input type="submit" class="button" value="确认更新" />
							<span class="label label-danger">${update_flag}</span>
						</p>
					</form>
				</div>
			</div>
			<div id="footer">
				<div id="footer_bg">
					ABC@126.com
				</div>
			</div>
		</div>
	</body>
</html>
