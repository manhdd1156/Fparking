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
		<div id="page-wrapper">
			<div class="row">
				<div class="col-lg-12">
					<h1 class="page-header">Thông tin chủ bãi xe</h1>
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
										<li><a data-toggle="tab" href="#FineHistory"
											class="text-success"><i class="fa fa-info"></i> Bãi xe
												của bạn</a></li>
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
															<td class="text-success"><i class="fa fa-list-alt"></i>
																Địa chỉ</td>
															<td>${address }</td>
														</tr>
													</tbody>
												</table>
											</div>
										</div>

										<div id="FineHistory" class="tab-pane fade">
											<div class="row">
												<div class="col-lg-12">
													<div class="panel panel-default">
														<div class="panel-heading">Tổng số bãi:
															${totalPriceFine }</div>
														<div class="panel-body">
															<table width="100%"
																class="table table-striped table-bordered table-hover"
																id="dataTables-example">
																<thead>
																	<tr>
																		<th>Địa chỉ</th>
																		<th>Số chỗ trống</th>
																		<th>Tổng số chỗ</th>
																		<th>Thời gian mở/đóng cửa</th>
																		<th>Số dư tài khoản</th>
																	</tr>
																</thead>
																<tbody>
																	<tr>
																		<td></td>
																		<td></td>
																		<td></td>
																		<td></td>
																		<td></td>
																	</tr>
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