<%@ page pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="headerjavascript.jsp"%>
<meta charset="utf-8">
<title>Quản lý bãi xe</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<script
	src="http://css3-mediaqueries-js.googlecode.com/svn/trunk/css3-mediaqueries.js"></script>
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
					<h1 class="page-header">Thông tin bãi xe</h1>
				</div>
				<!-- /.col-lg-12 -->
			</div>

			<div class="row">
				<div class="col-lg-12">
					<div class="panel panel-default">
						<div class="panel-body">
							<div class="row">
								<div class="col-lg-12">

									<ul class="nav nav-tabs">
										<li><a data-toggle="tab" href="#Infromation"
											class="text-success"><i class="fa fa-bookmark-o"></i>
												Thông tin tài khoản</a></li>
										<li><a data-toggle="tab" href="#PriceType"
											class="text-success"><i class="glyphicon glyphicon-usd"></i>Giá
												gửi xe</a></li>
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
																Chủ bãi</td>
															<td>${nameOwner}</td>
														</tr>
														<tr>
															<td class="text-success"><i
																class="glyphicon glyphicon-phone"></i> Số điện thoại</td>
															<td>${phoneOwner}</td>
														</tr>
														<tr>
															<td class="text-success"><i
																class="glyphicon glyphicon-list"></i> Địa chỉ bãi xe</td>
															<td>${addressParking }</td>
														</tr>
														<tr>
															<td class="text-success"><i
																class="glyphicon glyphicon-time"></i> Thời gian mở/đóng
																cửa</td>
															<td>${timeoc}</td>
														</tr>
														<tr>
															<td class="text-success"><i
																class="glyphicon glyphicon-ruble"></i> Tổng số chỗ</td>
															<td>${totalSpace}</td>
														</tr>
														<tr>
															<td class="text-success"><i
																class="glyphicon glyphicon-usd"></i> Số dư tài khoản</td>
															<td>${deposits}</td>
														</tr>
													</tbody>
												</table>
											</div>
										</div>

										<div id="PriceType" class="tab-pane fade">
											<div class="row">
												<div class="col-lg-12">
													<div class="panel panel-default">
														<div class="panel-body">
															<table width="100%"
																class="table table-striped table-bordered table-hover"
																id="dataTables-example">
																<thead>
																	<tr>
																		<th>Loại xe</th>
																		<th>Giá</th>
																	</tr>
																</thead>
																<tbody>
																	<c:forEach items="${ arrayListVehicletype}"
																		var="parkingPrice">
																		<tr>
																			<td>${parkingPrice.typeCar }</td>
																			<td>${parkingPrice.priceType }</td>
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

										<div id="FineHistory" class="tab-pane fade">
											<div class="row">
												<div class="col-lg-12">
													<div class="panel panel-default">
														<div class="panel-body">
															<table width="100%"
																class="table table-striped table-bordered table-hover"
																id="dataTables-example2">
																<thead>
																	<tr>
																		<th>Ngày phạt</th>
																		<th>Biển số xe</th>
																		<th>Loại xe</th>
																		<th>Số tiền bị phạt</th>
																	</tr>
																</thead>
																<tbody>
																	<c:forEach items="${ arrayListFine}" var="parkingFine">
																		<tr>
																			<td>${parkingFine.dateFine }</td>
																			<td>${parkingFine.licenseplate }</td>
																			<td>${parkingFine.typeCar }</td>
																			<td>${parkingFine.priceFine }</td>
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

									</div>

								</div>
							</div>
						</div>
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