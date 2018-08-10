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
					<h1 class="page-header">Phản hồi người dùng</h1>
				</div>
				<!-- /.col-lg-12 -->
			</div>
			<div class="row">
				<div class="col-lg-12">
					<div class="panel panel-primary">
						<div class="panel-body">
							<table width="100%"
								class="table table-striped table-bordered table-hover"
								id="dataTables-example">
								<thead>
									<tr>
										<th>Ngày</th>
										<th>Tên</th>
										<th>Nội Dung</th>
										<th>Trạng thái</th>
										<th>Xóa</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${arrayListFeedback}" var="listFeedBack">
										<tr class="odd gradeX">
											<td>${listFeedBack.dateFeedBack }</td>
											<td>${listFeedBack.nameFeedBack}</td>
											<td><a href="/business/feedbackdetail/${listFeedBack.id}">${listFeedBack.content }</a></td>
											<td>${listFeedBack.status }</td>
											<td><a href="#" onclick="deleteFeedback2(${listFeedBack.id });return false">Xóa</a></td>
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