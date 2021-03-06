<%@ page pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="headerjavascript.jsp"%>
<meta charset="utf-8">
<title>Quản lý lái xe</title>
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
					<h1 class="page-header">Thông tin lái xe</h1>
				</div>
				<!-- /.col-lg-12 -->
			</div>

			<div class="row">
				<div class="col-lg-12">
					<div class="panel panel-green">
						<div class="panel-body">
							<div class="row">
								<div class="col-lg-12">

									<ul class="nav nav-tabs">
										<li><a data-toggle="tab" href="#Infromation"
											id="tabInfromation" class="text-success"><i
												class="fa fa-bookmark-o"></i> Thông tin tài khoản</a></li>
										<li><a data-toggle="tab" href="#FineHistory"
											id="tabFineHistory" class="text-success"><i
												class="fa fa-info"></i> Lịch sử phạt</a></li>
									</ul>

									<div class="tab-content">
										<div id="Infromation" class="tab-pane fade in">
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
															<td class="text-success"><i class="fa fa-car"></i>
																Xe đã đăng kí</td>
															<td>${TotalCar}</td>
														</tr>
													</tbody>
												</table>
											</div>
										</div>

										<div id="FineHistory" class="tab-pane fade">
											<div class="row">
												<div class="col-lg-12">
													<div class="panel panel-default">
														<div class="panel-heading">Tổng số tiền phạt:
															${totalPriceFine }</div>
														<form action="" method="GET">
															<div>
																<br>
																<p class="col-lg-12">
																	Từ <input type="date" name="dateFrom"
																		value="${dateFrom }"> đến <input type="date"
																		name="dateTo" value="${dateTo }"> <input
																		type="hidden" name="type" value="1"> <input
																		type="submit" value="Tìm">
																</p>
															</div>
														</form>

														<div class="panel-body">
															<div class="panel-body">
																<table width="100%"
																	class="table table-striped table-bordered table-hover"
																	id="dataTables-example">
																	<thead>
																		<tr>
																			<th>Ngày phạt</th>
																			<th>Biển số xe</th>
																			<th>Điển đến</th>
																			<th>Số tiền bị phạt</th>
																			<th>Trạng thái</th>
																		</tr>
																	</thead>
																	<tbody>
																		<c:forEach items="${driverFine}"
																			var="driverFineHistory">
																			<tr>
																				<td>${driverFineHistory.dateFine }</td>
																				<td>${driverFineHistory.licenseplate }</td>
																				<td>${driverFineHistory.address }</td>
																				<td>${driverFineHistory.priceFine }</td>
																				<td>${driverFineHistory.status }</td>
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
		<script type="text/javascript">
			$(document).ready(function() {
				var type = ${type}
				;
				if (type == 1) {
					$("#tabFineHistory").tab('show')
				} else {
					$("#tabInfromation").tab('show')
				}
			});
		</script>
		<!-- ===============FooterJavaScrip End================= -->
</body>
</html>