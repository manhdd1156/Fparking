<%@ page pageEncoding="utf-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html>
<html>
<head>
<%@include file="headerjavascript.jsp"%>
<meta charset="utf-8">
<title>Đăng Nhập</title>
</head>

<body>
	<div class="container">
		<div class="row">
			<div class="col-md-4 col-md-offset-4">
				<div class="login-panel panel panel-default">
					<div class="panel-heading">
						<h3 class="panel-title">Vui lòng đăng nhập</h3>
					</div>
					<div class="panel-body">
						<form role="form" method="POST" action="">
							<fieldset>
								<div class="form-group">
									<input class="form-control" placeholder="Tên đăng nhập"
										name="username" type="text" autofocus required>
								</div>
								<div class="form-group">
									<input class="form-control" placeholder="Mật Khẩu"
										name="password" type="password" value="" required>
								</div>
								<div class="checkbox">
									<label> <input name="remember" type="checkbox"
										value="Remember Me">Ghi nhớ mật khẩu
									</label>
								</div>
									<input type="submit"
									class="btn btn-lg btn-success btn-block" value="Đăng nhập">
							</fieldset>
							<h5 style="color: red">${messError}</h5>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>

	<!-- ========================Footer=================================> -->
	<%@include file="footerjavascrip.jsp"%>
	<!-- ========================Footer End=================================> -->
</body>
</html>