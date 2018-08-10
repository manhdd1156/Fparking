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
					<h1 class="page-header">Doanh Thu Chiết Khấu</h1>
				</div>
				<!-- /.col-lg-12 -->
			</div>
			<div class="col-lg-12">
				<div class="panel panel-primary">
					<div class="panel-heading">
						Tổng doanh thu chiết khấu: <b>${revenueCommission }</b>
					</div>
					<form action="" method="GET">
						<div>
							<br>
							<div class="col-lg-12">
								Từ <input type="date" name="dateFrom" value="${dateFrom }">
								đến <input type="date" name="dateTo" value="${dateTo }">
								<input type="submit" value="Tìm">
							</div>
						</div>
					</form>
					<br>
					<!-- /.panel-heading -->
					<div class="panel-body">
						<div class="row">
							<div class="col-lg-12">
								<div class="panel panel-default">
									<!-- /.panel-heading -->
									<div class="panel-body">
										<table width="100%"
											class="table table-striped table-bordered table-hover"
											id="dataTables-example2">
											<thead>
												<tr>
													<th>Ngày</th>
													<th>Địa chỉ</th>
													<th>Tỉnh/Thành Phố</th>
													<th>Tổng hóa đơn</th>
													<th>Tiền chiết khấu</th>
													<th></th>
												</tr>
											</thead>
											<tbody>
												<c:forEach items="${arrayListBooking}" var="booking">
													<tr class="odd gradeX">
														<td>${booking.timeout }</td>
														<td>${booking.address }</td>
														<td>${booking.city }</td>
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