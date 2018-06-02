<?php
if(isset($_POST['name']) && isset($_POST['price'])){
    $name = trim($mysqli->real_escape_string($_POST['name']));
    $description = trim($mysqli->real_escape_string($_POST['description']));
    $price = floatval($_POST['price']);
    $image = trim($mysqli->real_escape_string($_POST['image']));
    $sql = "INSERT INTO `products` (`name`, `description`, `price`,`image`) VALUES ('{$name}','{$description}',$price, '{$image}')";
    $rs = $mysqli->query($sql);
    $insert_id = $mysqli->insert_id;
    if($insert_id>0){
        $sql =  "SELECT * FROM `products` WHERE `id` = {$insert_id} ";
        $rs = $mysqli->query($sql);
        $outp['result'] = $rs->fetch_all(MYSQLI_ASSOC);
        $outp['size'] = $rs->num_rows;
        $outp['hasErr'] = false;
    }else{
        $outp['result'] = array('message'=>'System fail');
    }
}
?>