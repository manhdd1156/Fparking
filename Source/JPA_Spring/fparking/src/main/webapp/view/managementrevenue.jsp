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
		<div id="page-wrapper">

			<div class="row">
				<div class="col-lg-12">
					<h1 class="page-header">Doanh Thu</h1>
				</div>
				<!-- /.col-lg-12 -->
			</div>

			<div class="col-lg-6">
				<div class="panel panel-default">
					<div class="panel-heading">Doanh thu tiền phạt</div>
					<form action="" method="GET">
						<div>
							<br>
							<p class="col-lg-12">
								Từ <input type="date"> đến <input type="date"> <input
									type="submit" value="Tìm">
							</p>
						</div>
					</form>
					<!-- /.panel-heading -->
					<div class="panel-body">
						<div class="row">
							<div class="col-lg-12">
								<div class="panel panel-default">
									<div class="panel-heading">Tổng doanh thu: ${revenueFine } VND</div>
									<div class="panel-body">
										<table width="100%"
											class="table table-striped table-bordered table-hover"
											id="dataTables-example">
											<thead>
												<tr>
													<th>Ngày</th>
													<th>Biển số xe</th>
													<th>Loại xe</th>
													<th>Bãi xe</th>
													<th>Đối tượng phạt</th>
													<th>Số tiền phạt</th>
												</tr>
											</thead>
											<tbody>
												<c:forEach items="${arrayListFine}" var="listFine">
													<tr class="odd gradeX">
														<td>${listFine.dateFine }</td>
														<td>${listFine.licenseplate }</td>
														<td>${listFine.vehicletype }</td>
														<td>${ listFine.address}</td>
														<td>${listFine.objectFine }</td>
														<td>${listFine.priceFine }</td>
													</tr>
												</c:forEach>
											</tbody>
										</table>
										<!-- /.table-responsive -->

									</div>
									<!-- /.panel-body -->
								</div>
								<!-- /.panel -->
							</div>
							<!-- /.col-lg-12 -->
						</div>
					</div>
					<!-- /.panel-body -->
				</div>
				<!-- /.panel -->
			</div>

			<div class="col-lg-6">
				<div class="panel panel-default">
					<div class="panel-heading">Doanh thu triết khấu</div>
					<form action="" method="GET">
						<div>
							<br>
							<p class="col-lg-12">
								Từ <input type="date"> đến <input type="date"> <input
									type="submit" value="Tìm">
							</p>
						</div>
					</form>

					<!-- /.panel-heading -->
					<div class="panel-body">
						<div class="row">
							<div class="col-lg-12">
								<div class="panel panel-default">
									<div class="panel-heading">Tổng doanh thu: ${totalRevenue }
										VND</div>
									<!-- /.panel-heading -->
									<div class="panel-body">
										<table width="100%"
											class="table table-striped table-bordered table-hover"
											id="dataTables-example2">
											<thead>
												<tr>
													<th>Ngày</th>
													<th>Địa chỉ</th>
													<th>Tổng hóa đơn</th>
													<th>Tiền triết khấu</th>
													<th></th>
												</tr>
											</thead>
											<tbody>
												<c:forEach items="${arrayListBooking}" var="booking">
													<tr class="odd gradeX">
														<td>${booking.timeout }</td>
														<td>${booking.address }</td>
														<td>${ booking.amount}</td>
														<td>${booking.totalCommission }</td>
														<td class="center"><a
															href="/business/revenue/detail/${booking.id }">Xem</a></td>
													</tr>
												</c:forEach>
											</tbody>
										</table>
										<!-- /.table-responsive -->

									</div>
									<!-- /.panel-body -->
								</div>
								<!-- /.panel -->
							</div>
							<!-- /.col-lg-12 -->
						</div>
					</div>
					<!-- /.panel-body -->
				</div>
				<!-- /.panel -->
			</div>

		</div>
		<!-- ===============Body End================= -->

		<!-- ===============FooterJavaScrip Start================= -->
		<%@include file="footerjavascrip.jsp"%>
		<!-- ===============FooterJavaScrip End================= -->
</body>
</html>