<%@ page pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="headerjavascript.jsp"%>
<meta charset="utf-8">
<title>Admin</title>
</head>
<body>
	<!-- ===============Body Start================= -->
	<div id="wrapper">
		<!-- ===============Hearder+Left Start================= -->
		<!-- Navigation -->
		<nav class="navbar navbar-default navbar-static-top" role="navigation"
			style="margin-bottom: 0">

			<!-- ===============Hearder Start================= -->
			<%@include file="header.jsp"%>
			<!-- ===============Hearder End================= -->

			<!-- ===============Left Start================= -->
			<%@include file="left.jsp"%>
			<!-- ===============Left End================= -->

		</nav>
		<!-- ===============Hearder+Left End================= -->
		<!-- ===============Body Start================= -->
		<div id="page-wrapper">
			<div class="row">
				<div class="col-lg-12">
					<h1 class="page-header">Thay đổi mật khẩu</h1>
				</div>
				<!-- /.col-lg-12 -->
			</div>

			<div class="row">
				<div class="col-lg-12">
					<div class="panel panel-default">
						<div class="panel-body">
							<div class="row">
								<div class="col-lg-12">
									<form role="form" method="POST" action="" id="changepass">
										<div class="form-group">
											<label>Tài khoản:</label> 
											<input style="background-color: white;" type="text" class="form-control"value="${username }" disabled>
										</div>
										<div class="form-group">
											<label>Mật khẩu cũ:</label> 
											<input type="password" class="form-control" name="oldPassword" required>
										</div>
										<div class="form-group">
											<label>Mật khẩu mới:</label> 
											<input type="password" class="form-control" name="newPassword" id="newPassword" maxlength="24" minlength="6" required>
										</div>
										<div class="form-group">
											<label>Xác nhận mật khẩu:</label> 
											<input type="password" class="form-control" name="re_Password" id="re_Password" maxlength="24" minlength="6" required>
										</div>
										<p style="color: green;">${messSuss }</p>
										<p style="color: red;">${messError }</p>
										<button type="submit" class="btn btn-success">Lưu</button>
									</form>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<!-- ===============Body End================= -->
	</div>
	<!-- ===============FooterJavaScrip Start================= -->
	<%@include file="footerjavascrip.jsp"%>
	<!-- ===============FooterJavaScrip End================= -->
</body>
</html>