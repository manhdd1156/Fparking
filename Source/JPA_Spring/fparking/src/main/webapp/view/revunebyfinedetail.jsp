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
					<h1 class="page-header">Chi tiết doanh thu tiền phạt</h1>
				</div>
				<!-- /.col-lg-12 -->
			</div>

			<div class="row">
				<div class="col-lg-12">
					<div class="panel panel-default">
						
							<table class="table table-bordered">
								<thead>
									<tr>
										<th>Thời gian vào</th>
										<th>Thời gian ra</th>
										<th>Địa chỉ</th>
										<th>Biển số xe</th>
										<th>Giá</th>
										<th>Tiền phạt</th>
										<th>Phần chăm triết khấu</th>
										<th>Tổng tiền thu</th>
									</tr>
								</thead>
								<tbody>
									<tr>
										
										
									</tr>
								</tbody>
							</table>

					
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