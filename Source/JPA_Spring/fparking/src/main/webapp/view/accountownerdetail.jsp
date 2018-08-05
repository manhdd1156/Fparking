<%@ page pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="headerjavascript.jsp"%>
<meta charset="utf-8">
<title>Quản lý chủ bãi</title>
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
												quản lý</a></li>
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
															${toatalParking }</div>
														<div class="panel-body">
															<div>
																<b>Tổng tiền tài khoản bãi: ${totalDeposit } </b>
															</div>
															<br>
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
																		<th></th>
																		<th></th>
																	</tr>
																</thead>
																<tbody>
																	<c:forEach items="${ arrayListParking}"
																		var="arrayListParking">
																		<tr>
																			<td>${arrayListParking.address }</td>
																			<td>${arrayListParking.currentspace }</td>
																			<td>${arrayListParking.totalspace }</td>
																			<td>${arrayListParking.timeoc }</td>
																			<td>${arrayListParking.desposits }</td>
																			<td class="center"><a
																				href="/account/patking/detail/${arrayListParking.id }">Xem</a></td>
																			<td class="center"><a
																				href="/account/patking/detail/${arrayListParking.id }">Sửa</a></td>
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