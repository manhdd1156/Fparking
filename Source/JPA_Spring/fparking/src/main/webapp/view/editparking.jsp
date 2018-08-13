<%@ page pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="headerjavascript.jsp"%>
<meta charset="utf-8">
<title>Quản lý bãi đỗ</title>
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
					<h1 class="page-header">Sửa thông tin bãi xe</h1>
				</div>
				<!-- /.col-lg-12 -->
			</div>

			<div class="row">
				<div class="col-lg-12">
					<div class="panel panel-default">
						<div class="panel-body">
							<div class="row">
								<div class="col-lg-12">
									<form role="form" method="POST" action="">
										<div class="form-group">
											<label>Địa chỉ:</label> 
											<input type="text" class="form-control" name="address" value="${address }" required>
										</div>
										<div class="form-group">
											<label>Kinh độ:</label> 
											<input type="number" class="form-control" name="longitude" value="${longitude }" step="0.000000000001" required>
										</div>
										<div class="form-group">
											<label>Vĩ độ:</label> 
											<input type="number" class="form-control" name="latitude" value="${latitude }" step="0.000000000001" required>
										</div>
										<div class="form-group">
											<label>Tổng số chỗ:</label> 
											<input type="number" class="form-control" name="totalSpace" value="${totalSpace }" min="0" step="1" required>
										</div>
										<div class="form-group">
											<label>Thời gian mở/đóng:</label> 
											<input type="text" class="form-control" name="timeoc" value="${timeoc }" required>
										</div>
										<div class="form-group">
											<label>Số dư tài khoản:</label> 
											<input type="number" class="form-control" name="deposits" value="${deposits }" min="110000" max="2000000000" step="1" required>
										</div>
										<p style="color: green;">${messSuss }</p>
										<p style="color: red;">${messError }</p>
										<button type="submit" class="btn btn-success">Lưu</button>
										<button type="reset" class="btn btn-success">Đặt lại</button>
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