<%@ page pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="headerjavascript.jsp"%>
<meta charset="utf-8">
<title>Quản lý phản hồi người dùng</title>
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
		<!-- ===============Body Start================= -->
		<div id="page-wrapper">
			<div class="row">
				<div class="col-lg-12">
					<h1 class="page-header">Phản hồi từ: ${inforFeedBack }</h1>
				</div>
				<!-- /.col-lg-12 -->
			</div>

			<div class="row">
				<div class="col-lg-12">
					<div class="panel panel-default">
						<div class="panel-body">
							<div class="row">
								<div class="col-lg-12">
									<form action="" method="POST">
										<div>
											<label>Ngày: ${dateFeedBack }</label>
										</div>
										<div class="form-group">
											<label>Nội dung:</label>
											<textarea style="background-color: white;"
												class="form-control" rows="25" readonly>${content }</textarea>
										</div>

										<div class="form-group">
											<label>Cách thức xử lý:</label>
											<textarea style="background-color: white;"
												class="form-control" rows="10" name="resolve">${resolve }</textarea>
										</div>
										<dialog open>
										<p style="color: green;">${messSucc }</p>
										</dialog>
										<div>
											<a href="#" onclick="deleteFeedback2(${id });return false"
												type="button" class="btn btn-danger">Xóa</a> <input
												type="submit" class="btn btn-success" value="Xong">
										</div>
									</form>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<!-- ===============Body End================= -->
	</div>
	<!-- ===============FooterJavaScrip Start================= -->
	<%@include file="footerjavascrip.jsp"%>
	<!-- ===============FooterJavaScrip End================= -->
</body>
</html>