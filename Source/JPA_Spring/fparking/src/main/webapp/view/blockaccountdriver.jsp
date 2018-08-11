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
		<div id="page-wrapper">
			<div class="row">
				<div class="col-lg-12">
					<h1 class="page-header">Tài khoản lái xe bị khóa</h1>
				</div>
				<!-- /.col-lg-12 -->
			</div>
			<div class="row">
				<div class="col-lg-12">
					<div class="panel panel-red">
						<div class="panel-heading">Tổng số tài khoản:
							${totalAccount}</div>
						<!-- /.panel-heading -->
						<div class="panel-body">
							<table width="100%"
								class="table table-striped table-bordered table-hover"
								id="dataTables-example">
								<thead>
									<tr>
										<th>Họ Tên</th>
										<th>Số điện thoại</th>
										<th></th>
										<th></th>
										<th></th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items= "${ listDriver}" var="driver">
									<tr class="odd gradeX">
										<td>${driver.name }</td>
										<td>${driver.phone }</td>
										<td class="center"><a href="/account/driver/detail/${driver.id }">Xem</a></td>
										<td class="center"><a href="/account/driver/unblockaccount/${driver.id }">Mở Khóa</a></td>
										<td class="center"><a href="/account/driver/editaccount/${driver.id }">Sửa</a></td>
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
	<!-- ===============Body End================= -->

	<!-- ===============FooterJavaScrip Start================= -->
	<%@include file="footerjavascrip.jsp"%>
	<!-- ===============FooterJavaScrip End================= -->
</body>
</html>