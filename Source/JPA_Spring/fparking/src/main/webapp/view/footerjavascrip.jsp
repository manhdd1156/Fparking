<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!-- jQuery -->
<script src="assets/vendor/jquery/jquery.min.js"></script>

<!-- Bootstrap Core JavaScript -->
<script src="assets/vendor/bootstrap/js/bootstrap.min.js"></script>

<!-- Metis Menu Plugin JavaScript -->
<script src="assets/vendor/metisMenu/metisMenu.min.js"></script>

<!-- Morris Charts JavaScript >
<script src="assets/vendor/raphael/raphael.min.js"></script>
<script src="assets/vendor/morrisjs/morris.min.js"></script>
<script src="assets/data/morris-data.js"></script>

<!-- Custom Theme JavaScript -->
<script src="assets/dist/js/sb-admin-2.js"></script>

<!-- DataTables JavaScript -->
<script src="assets/vendor/datatables/js/jquery.dataTables.min.js"></script>
<script
	src="assets/vendor/datatables-plugins/dataTables.bootstrap.min.js"></script>
<script
	src="assets/vendor/datatables-responsive/dataTables.responsive.js"></script>

<!-- Page-Level Demo Scripts - Tables - Use for reference -->
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/crypto-js/3.1.2/rollups/md5.js" ></script>
	<!--<script src="assets/dist/js/md5.js"></script>  -->

<script>
	$(document).ready(function() {
		$('#dataTables-example').DataTable({
			responsive : true
		});
		$('#dataTables-example2').DataTable({
			responsive : true
		});
		$('#dataTables-example3').DataTable({
			responsive : true
		});
		$('#dataTables-example4').DataTable({
			responsive : true
		});
		var table = $('#dataTables-example4').DataTable();

		// Event listener to the two range filtering inputs to redraw on input
		$('#min, #max').keyup(function() {
			table.draw();
		});
		
		$('#loginForm').submit(function(event) {
			var pass = $('#loginForm [name=password]').val();
			pass = CryptoJS.MD5(pass).toString();
			$('#loginForm [name=password]').val(pass);
		});
		
		$('#changepass').submit(function(event) {
			$('#changepass [name=oldPassword]').val(CryptoJS.MD5($('#changepass [name=oldPassword]').val()).toString());
			$('#changepass [name=newPassword]').val(CryptoJS.MD5($('#changepass [name=newPassword]').val()).toString());
			$('#changepass [name=re_Password]').val(CryptoJS.MD5($('#changepass [name=re_Password]').val()).toString());
		});
	});
	function deleteFeedback1(id) {
		var txt;
		if (confirm("Bạn có muốn xóa?")) {
			window.location.href = "/home/feedback/delete/" + id;
		}
		return false;
	}

	function deleteFeedback2(id) {
		var txt;
		if (confirm("Bạn có muốn xóa?")) {
			window.location.href = "/business/feedback/delete/" + id;
		}
		return false;
	}

	function validateForm() {
		var newpass = document.getElementById('newPassword').value;
		var repass = document.getElementById('re_Password').value;
		if (newpass != repass) {

		} else {
			alert('Mật khẩu và mật khảu nhập lại không khớp!');
		}
		return false;
	}
	
</script>
