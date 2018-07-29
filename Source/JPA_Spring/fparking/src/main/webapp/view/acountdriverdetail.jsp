<%@ page pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="headerjavascript.jsp"%>
<meta charset="utf-8">
<title>Thông tin tài khoản</title>
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
		<div class="panel-body">
			<div class="row">
				<div class="col-lg-12 col-md-12">

					<div class="row panel panel-success"
						style="margin-top: 1%; margin-left: 15%; margin-right: 15%">
						<div class="panel-body">
							<div class="row">
								<div class="col-lg-12 col-md-12">
									<div class="row">
										<div class="col-lg-9 col-md-9">
											<ul class="nav nav-tabs">
												<li><a data-toggle="tab" href="#Infromation"
													class="text-success"><i class="fa fa-bookmark-o"></i>
														Thông tin tài khoản</a></li>
												<li><a data-toggle="tab" href="#FineHistory"
													class="text-success"><i class="fa fa-info"></i> Lịch sử
														phạt</a></li>
											</ul>
											<div class="tab-content">
												<div id="Infromation" class="tab-pane fade in active">
													<div class="table-responsive panel">
														<table class="table">
															<tbody>
																<tr>
																	<td class="text-success"><i class="fa fa-user"></i>
																		Họ Tên</td>
																	<td>${name}</td>
																</tr>
																<tr>
																	<td class="text-success"><i
																		class="glyphicon glyphicon-phone"></i> Số điện thoại</td>
																	<td>${phonenumber}</td>
																</tr>
																<tr>
																	<td class="text-success"><i class="fa fa-user"></i>
																		Trạng thái tài khoản</td>
																	<td>${status }</td>
																</tr>
																<tr>
																	<td class="text-success"><i class="fa fa-user"></i>
																		Xe đã đăng kí</td>
																	<td>${TotalCar}</td>
																</tr>
															</tbody>
														</table>
													</div>
												</div>

												<div id="FineHistory" class="tab-pane fade">
													<div class="table-responsive panel">
														<table class="table">

															<thead>
																<tr>
																	<th>Ngày phạt</th>
																	<th>Biển số xe</th>
																	<th>Điển đếm</th>
																	<th>Số tiền bị phạt</th>
																	<th>Trạng thái</th>
																</tr>
															</thead>

															<tbody>
																<c:forEach items="${ driverFine}" var="driverFine">
																	<tr>
																		<td>${driverFine.date }</td>
																		<td>${driverFine.licenseplate }</td>
																		<td>${driverFine.nameParking }</td>
																		<td>${driverFine.price }</td>
																		<td>${driverFine.status }</td>
																	</tr>
																</c:forEach>
															</tbody>

														</table>
													</div>
												</div>

											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
						<!-- /.table-responsive -->
					</div>
				</div>
			</div>
		</div>
		<!-- ===============Content End================= -->

	</div>
	<!-- ===============Body End================= -->

	<!-- ===============FooterJavaScrip Start================= -->
	<%@include file="footerjavascrip.jsp"%>
	<!-- ===============FooterJavaScrip End================= -->

</body>
</html>