<%@page contentType="text/html" pageEncoding="UTF-8"%>
<header>

	<div class="navbar-header">
		<button type="button" class="navbar-toggle" data-toggle="collapse"
			data-target=".navbar-collapse">
			<span class="sr-only">Toggle navigation</span> <span class="icon-bar"></span>
			<span class="icon-bar"></span> <span class="icon-bar"></span>
		</button>
		<a class="navbar-brand" href="/home">FParking</a>
	</div>
	<!-- /.navbar-header -->

	<ul class="nav navbar-top-links navbar-right">
		<!-- /.dropdown -->
		<!-- /.dropdown -->
		<li class="dropdown"><a class="dropdown-toggle"
			data-toggle="dropdown" href="#"> <i class="fa fa-user fa-fw"></i>
				<i class="fa fa-caret-down"></i>
		</a>
			<ul class="dropdown-menu dropdown-user">
				<li><a href="/account/admin/editaccount/1"><i class="fa fa-user fa-fw"></i> Đổi mật khẩu</a>
				</li>
				<li class="divider"></li>
				<li><a href="/login"><i class="fa fa-sign-out fa-fw"></i>
						Đăng xuất</a></li>
			</ul> <!-- /.dropdown-user --></li>
		<!-- /.dropdown -->
	</ul>
	<!-- /.navbar-top-links -->
	<style>
#map {
	width: 100%;
	height: 400px;
	background-color: grey;
}
</style>

</header>