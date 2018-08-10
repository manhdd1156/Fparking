<%@ page pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="headerjavascript.jsp"%>
<meta charset="utf-8">
<title>Trang chủ</title>
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

		<!-- ===============Content Start================= -->
		<div id="page-wrapper">
			<div class="row">
				<div class="col-lg-12">
					<h1 class="page-header">Trang chủ</h1>
				</div>
				<!-- /.col-lg-12 -->
			</div>
			<!-- /.row -->

			<!-- ==Thông tin hệ thông Start== -->
			<div class="row">
				<div class="col-lg-3 col-md-6">
					<div class="panel panel-primary">
						<div class="panel-heading">
							<div class="row">
								<div class="col-xs-3">
									<i class="fa fa-car fa-5x"></i>
								</div>
								<div class="col-xs-9 text-right">
									<div class="huge">${totalAccountDriver }</div>
									<div>Tài khoản lái xe</div>
								</div>
							</div>
						</div>
						<a href="/account/driver">
							<div class="panel-footer">
								<span class="pull-left">Xem chi tiết</span> <span
									class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
								<div class="clearfix"></div>
							</div>
						</a>
					</div>
				</div>
				<div class="col-lg-3 col-md-6">
					<div class="panel panel-green">
						<div class="panel-heading">
							<div class="row">
								<div class="col-xs-3">
									<i class="fa fa-ruble fa-5x"></i>
								</div>
								<div class="col-xs-9 text-right">
									<div class="huge">${totalAccountParking }</div>
									<div>Tổng số bãi đỗ</div>
								</div>
							</div>
						</div>
						<a href="/account/parking">
							<div class="panel-footer">
								<span class="pull-left">Xem chi tiết</span> <span
									class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
								<div class="clearfix"></div>
							</div>
						</a>
					</div>
				</div>
				<div class="col-lg-3 col-md-6">
					<div class="panel panel-yellow">
						<div class="panel-heading">
							<div class="row">
								<div class="col-xs-3">
									<i class="glyphicon glyphicon-stats fa-5x"></i>
								</div>
								<div class="col-xs-9 text-right">
									<div class="huge">${totalTrasaction }</div>
									<div>Giao dịch hôm nay</div>
								</div>
							</div>
						</div>
						<a href="/business/revenue">
							<div class="panel-footer">
								<span class="pull-left">Xem chi tiết</span> <span
									class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
								<div class="clearfix"></div>
							</div>
						</a>
					</div>
				</div>
				<div class="col-lg-3 col-md-6">
					<div class="panel panel-red">
						<div class="panel-heading">
							<div class="row">
								<div class="col-xs-3">
									<i class="glyphicon glyphicon-usd fa-5x"></i>
								</div>
								<div class="col-xs-9 text-right">
									<div class="huge">${totalReveune }</div>
									<div>Doanh thu hôm nay</div>
								</div>
							</div>
						</div>
						<a href="/business/revenue">
							<div class="panel-footer">
								<span class="pull-left">Xem chi tiết</span> <span
									class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
								<div class="clearfix"></div>
							</div>
						</a>
					</div>
				</div>
			</div>
			<!-- ==Thông tin hệ thông End== -->

			<!--====Feedback from actor Start===-->
			<div class="col-lg-12">
				<div class="panel panel-warning">
					<div class="panel-heading">Phản hồi từ người dùng</div>
					<!-- /.panel-heading -->
					<div class="panel-body">
						<div class="row">
							<div class="col-lg-12">
								<div class="panel panel-warning">
									<div class="panel-body">
										<table width="100%"
											class="table table-striped table-bordered table-hover"
											id="dataTables-example">
											<thead>
												<tr>
													<th>Ngày</th>
													<th>Tên</th>
													<th>Nội Dung</th>
													<th>Xóa</th>
												</tr>
											</thead>
											<tbody>
												<c:forEach items="${arrayListFeedback}" var="listFeedBack">
													<tr class="odd gradeX">
														<td>${listFeedBack.dateFeedBack }</td>
														<td>${listFeedBack.nameFeedBack}</td>
														<td><a href="/home/feedbackdetail/${listFeedBack.id}">${listFeedBack.content }</a></td>
														<td><a href="#"
															onclick="deleteFeedback1(${listFeedBack.id });return false">Xóa</a></td>
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
			<!--====Feedback from actor End===-->
			<!--====Parking Start===-->
			<div class="col-lg-12">
				<div class="panel panel-danger">
					<div class="panel-heading">Bãi xe sắp hết tiền cọc</div>
					<!-- /.panel-heading -->
					<div class="panel-body">
						<div class="row">
							<div class="col-lg-12">
								<div class="panel panel-danger">
									<!-- /.panel-heading -->
									<div class="panel-body">
										<table width="100%"
											class="table table-striped table-bordered table-hover"
											id="dataTables-example2">
											<thead>
												<tr>
													<th>Địa chỉ</th>
													<th>Chủ bãi</th>
													<th>Số điện thoại</th>
													<th>Số dư tài khoản</th>
												</tr>
											</thead>
											<tbody>
												<c:forEach items="${arrayListParking}" var="listParking">
													<tr class="odd gradeX">
														<td><a
															href="/account/parking/detail/${listParking.id }">${listParking.addressParking }</a></td>
														<td>${listParking.nameOwner }</td>
														<td>${listParking.phoneOwner }</td>
														<td>${listParking.deposits }</td>
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
			<!--====Parking End===-->

			<!--====Parking Pending Start===-->
			<div class="col-lg-12">
				<div class="panel panel-yellow">
					<div class="panel-heading">Bãi xe chờ duyệt</div>
					<!-- /.panel-heading -->
					<div class="panel-body">
						<div class="row">
							<div class="col-lg-12">
								<div class="panel panel-yellow">
									<!-- /.panel-heading -->
									<div class="panel-body">
										<table width="100%"
											class="table table-striped table-bordered table-hover"
											id="dataTables-example3">
											<thead>
												<tr>
													<th>Địa chỉ</th>
													<th>Chủ bãi</th>
													<th>Số điện thoại</th>
													<th>Số dư tài khoản</th>
													<th></th>
												</tr>
											</thead>
											<tbody>
												<c:forEach items="${arrayListParkingPending}" var="listParkingPending">
													<tr class="odd gradeX">
														<td><a
															href="/account/parking/detail/${listParkingPending.id }">${listParkingPending.addressParking }</a></td>
														<td>${listParkingPending.nameOwner }</td>
														<td>${listParkingPending.phoneOwner }</td>
														<td>${listParkingPending.deposits }</td>
														<td><a href="/home/addmoney/${listParkingPending.id }">Chấp nhận</a></td>
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
			<!--====Parking Pending End===-->

		</div>
		<!-- /#page-wrapper -->
		<!-- ===============Content End================= -->

	</div>
	<!-- ===============Body End================= -->

	<!-- ===============FooterJavaScrip Start================= -->
	<%@include file="footerjavascrip.jsp"%>
	<!-- ===============FooterJavaScrip End================= -->

</body>
</html>