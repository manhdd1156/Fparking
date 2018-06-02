<?php
if(isset($_POST['name'])){
    $name = trim($mysqli->real_escape_string($_POST['name']));
    $description = trim($mysqli->real_escape_string($_POST['description']));
    $sql = "INSERT INTO `tables` (`name`, `description`) VALUES ('{$name}','{$description}')";
    $rs = $mysqli->query($sql);
    $insert_id = $mysqli->insert_id;
    if($insert_id>0){
        $sql =  "SELECT * FROM `tables` WHERE `id` = {$insert_id} ";
        $rs = $mysqli->query($sql);
        $outp['result'] = $rs->fetch_all(MYSQLI_ASSOC);
        $outp['size'] = $rs->num_rows;
        $outp['hasErr'] = false;
    }else{
        $outp['result'] = array('message'=>'System fail');
    }
}
?>