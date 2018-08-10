<%@ page pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="headerjavascript.jsp"%>
<meta charset="utf-8">
<title>Quản lý nghiệp vụ</title>
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
					<h1 class="page-header">Thêm loại xe</h1>
				</div>
				<!-- /.col-lg-12 -->
			</div>

			<div class="row">
				<div class="col-lg-12">
					<div class="panel panel-primary">
						<div class="panel-body">
							<div class="row">
								<div class="col-lg-12">
									<form role="form" method="POST" action="">
										<div class="form-group">
											<label>Loại xe:</label> 
											<input type="number" class="form-control" name="vehicletype" min="1" step="1" max="45" required>
										</div>
										<div class="form-group">
											<label>Mức tiền phạt:</label> 
											<input type="number" class="form-control" name="priceFine" min="0.1" step="0.01" required>
										</div>
										<button type="submit" class="btn btn-success">Thêm</button>
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