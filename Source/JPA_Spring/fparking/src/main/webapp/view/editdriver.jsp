<%@ page pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="headerjavascript.jsp"%>
<meta charset="utf-8">
<title>Sủa tài khoản lái xe</title>
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
		<div class="row panel panel-success"
			style="margin-top: 1%; margin-left: 15%; margin-right: 15%">
			<form>
				<div class="form-group">
					<label for="exampleInputEmail1">Họ Tên</label> <input type="text"
						class="form-control" id="nameDriver" placeholder="${name }">
				</div>
				<div class="form-group">
					<label for="exampleInputPassword1">Số điện Thoại</label> <input
						type="text" class="form-control" id="exampleInputPassword1"
						placeholder="${phonenumber }">
				</div>
				<button type="submit" class="btn btn-lg btn-success btn-block">Lưu</button>
				<button type="reset" class="btn btn-lg btn-success btn-block">Đặt lại</button>
			</form>
		</div>
		<!-- ===============Body End================= -->

		<!-- ===============FooterJavaScrip Start================= -->
		<%@include file="footerjavascrip.jsp"%>
		<!-- ===============FooterJavaScrip End================= -->
</body>
</html>