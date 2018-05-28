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
//if (isset($_GET["parkingLotID"])) {
//    $parkinglotid = $_GET['parkingLotID'];

try {
    $stmt = $conn->prepare("SELECT *FROM parkinglot "); 
    $stmt->execute();
	while($row=$stmt->fetch(PDO::FETCH_ASSOC)){
  
      $response['allparkinglot'][] = $row;
 
	}
	echo json_encode($response);

}
catch(PDOException $e) {
    echo "Error: " . $e->getMessage();
}
 
//} else {
    // required field is missing
 //   $response["success"] = 0;
 //   $response["message"] = "Required field(s) is missing";
 
    // echoing JSON response
 //   echo json_encode($response);
//}
?>