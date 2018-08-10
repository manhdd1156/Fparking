<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
    <!-- jQuery -->
    <script src="assets/vendor/jquery/jquery.min.js"></script>

    <!-- Bootstrap Core JavaScript -->
    <script src="assets/vendor/bootstrap/js/bootstrap.min.js"></script>

    <!-- Metis Menu Plugin JavaScript -->
    <script src="assets/vendor/metisMenu/metisMenu.min.js"></script>

    <!-- Morris Charts JavaScript -->
    <script src="assets/vendor/raphael/raphael.min.js"></script>
    <script src="assets/vendor/morrisjs/morris.min.js"></script>
    <script src="assets/data/morris-data.js"></script>

    <!-- Custom Theme JavaScript -->
    <script src="assets/dist/js/sb-admin-2.js"></script>
    
     <!-- DataTables JavaScript -->
   	<script src="assets/vendor/datatables/js/jquery.dataTables.min.js"></script>
	<script src="assets/vendor/datatables-plugins/dataTables.bootstrap.min.js"></script>
    <script src="assets/vendor/datatables-responsive/dataTables.responsive.js"></script>
    
    <!-- Page-Level Demo Scripts - Tables - Use for reference -->
    <!-- Google map -->
    
    <script>
    $(document).ready(function() {
        $('#dataTables-example').DataTable({
            responsive: true
        });
    });
    $(document).ready(function() {
        $('#dataTables-example2').DataTable({
            responsive: true
        });
    });
    $(document).ready(function() {
        $('#dataTables-example3').DataTable({
            responsive: true
        });
    });
    $(document).ready(function() {
        $('#dataTables-example4').DataTable({
            responsive: true
        });
    });
    function deleteFeedback1(id) {
        var txt;
        if (confirm("Bạn có muốn xóa?")) {
        	window.location.href = "/home/feedback/delete/"+id;
        }
        return false;
    }
    
    function deleteFeedback2(id) {
        var txt;
        if (confirm("Bạn có muốn xóa?")) {
        	window.location.href = "/business/feedback/delete/"+id;
        }
        return false;
    }
    
    $.fn.dataTable.ext.search.push(
    	    function( settings, data, dataIndex ) {
    	        var min = parseInt( $('#min').val());
    	        var max = parseInt( $('#max').val());
    	        var age = parseFloat( data[2] ) || 0; // use data for the age column
    	 
    	        if ( ( isNaN( min ) && isNaN( max ) ) ||
    	             ( isNaN( min ) && age <= max ) ||
    	             ( min <= age   && isNaN( max ) ) ||
    	             ( min <= age   && age <= max ) )
    	        {
    	            return true;
    	        }
    	        return false;
    	    }
    	);
    	 
    	$(document).ready(function() {
    	    var table = $('#dataTables-example4').DataTable();
    	     
    	    // Event listener to the two range filtering inputs to redraw on input
    	    $('#min, #max').keyup( function() {
    	        table.draw();
    	    } );
    	} );
    </script>
