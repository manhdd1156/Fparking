<?php

     include 'config.inc.php';
	 
	 // Check whether phonenumber or password is set from android	
     if(isset($_POST['phonenumber']) && isset($_POST['password']))
     {
		  // Innitialize Variable
		  $result='';
	   	  $phonenumber = $_POST['phonenumber'];
          $password = $_POST['password'];
		  
		  // Query database for row exist or not
          $sql = 'SELECT * FROM ownerinfor WHERE  phoneNumber = :phonenumber AND password = :password';
          $stmt = $conn->prepare($sql);
          $stmt->bindParam(':phonenumber', $phonenumber, PDO::PARAM_STR);
          $stmt->bindParam(':password', $password, PDO::PARAM_STR);
          $stmt->execute();
          if($stmt->rowCount())
          {
			 $result="true";	
          }  
          elseif(!$stmt->rowCount())
          {
			  	$result="false";
          }
		  
		  // send result back to android
   		  echo $result;
  	}
	
?>