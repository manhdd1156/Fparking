<?php
 
/*
 * Following code will get single product details
 * A product is identified by product id (pid)
 */
 
// array for JSON response
$response = array();
 
// include db connect class
require_once __DIR__ . '/db_connect.php';

 
// check for post data
if (isset($_GET["parkingID"]) && isset($_GET["status"])) {
    $parkingID = $_GET['parkingID'];
    $status = $_GET['status'];

try {
    $stmt = $conn->prepare("SELECT * FROM `bookinginfor`,`carofdriver`,`driver`,`typecar` WHERE bookinginfor.carID = carofdriver.carID AND driver.driverID = carofdriver.driverID AND typecar.typeCarID = carofdriver.typeCarID AND bookinginfor.parkingID = :parkingID AND bookinginfor.status = :status"); 
    $stmt->bindValue(':parkingID',$parkingID);
    $stmt->bindValue(':status',$status);
	$stmt->execute();
	while($row=$stmt->fetch(PDO::FETCH_ASSOC)){
		
      $response['cars'][] = $row;
 
	}
	echo json_encode($response);

}
catch(PDOException $e) {
    echo "Error: " . $e->getMessage();
}
 
} else {
    // required field is missing
    $response["success"] = 0;
    $response["message"] = "Required field(s) is missing";
 
     //echoing JSON response
    echo json_encode($response);
}
?>