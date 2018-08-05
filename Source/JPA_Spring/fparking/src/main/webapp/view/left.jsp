<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!-- ===============Menu Start================= -->
<div class="navbar-default sidebar" role="navigation">

	<div class="sidebar-nav navbar-collapse">
		<ul class="nav" id="side-menu">
			<li><a href="/home"><i class="fa fa-home"></i> Trang chủ</a></li>
			<!-- ===============Tai khoản lai xe Start================= -->
			<li><a href="#"><i class="fa fa-car"></i> Quản lý tài khoản
					lái xe<span class="fa arrow"></span></a>
				<ul class="nav nav-second-level">
					<li><a href="/account/driver">Tài Khoản</a></li>
					<li><a href="/account/driver/block">Tài Khoản bị khóa</a></li>
				</ul> <!-- /.nav-second-level --></li>
			<!-- ===============Tai khoan lai xe End================= -->

			<!-- ===============Tai khoản bai xe Start================= -->
			<li><a href="#"><i class="fa fa-ruble"></i> Quản lý tài
					khoản bãi xe<span class="fa arrow"></span></a>
				<ul class="nav nav-second-level">
					<li><a href="/account/parking">Tài Khoản</a></li>
					<li><a href="/account/parking/block">Tài Khoản bị khóa</a></li>
				</ul> <!-- /.nav-second-level --></li>
			<!-- ===============Tai khoan bai xe End================= -->
			<!-- ===============Tai khoan owner Start================= -->
			<li><a href="/account/owner"><i class="fa fa-users"></i>
					Quản lý tài khoản chủ bãi xe</a></li>
			<!-- ===============Tai khoan owner End================= -->
			<!-- ===============Nghiep vu Start================= -->
			<li><a href="#"><i class="glyphicon glyphicon-usd"></i> Quản lý
					nghiệp vụ<span class="fa arrow"></span></a>
				<ul class="nav nav-second-level">
					<li><a href="/business/revenue">Doanh thu</a></li>
					<li><a href="/business/managementfine">Quản lý tiền phạt</a></li>
					<li><a href="/business/commission">Phần trăm tiết khấu</a></li>
					<li><a href="/business/vehicletype">Quản lý loại xe</a></li>
				</ul> <!-- /.nav-second-level --></li>
			<!-- ===============Nghiep vu Start================= -->

		</ul>
	</div>

	<!-- /.sidebar-collapse -->
</div>
<!-- /.navbar-static-side -->
<!-- ===============Menu End================= -->

