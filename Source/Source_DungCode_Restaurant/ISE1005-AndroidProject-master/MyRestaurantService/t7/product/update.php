<?php
if(isset($_POST['id']) && isset($_POST['name']) && isset($_POST['price'])){
    $name = trim($mysqli->real_escape_string($_POST['name']));
    $description = trim($mysqli->real_escape_string($_POST['description']));
    $price = floatval($_POST['price']);
    $id = intval($_POST['id']);
    $image = trim($mysqli->real_escape_string($_POST['image']));
    $sql = "UPDATE `products` SET `name`='{$name}',`description`='{$description}',`price`={$price}, `image`='{$image}' WHERE `id`={$id}";
    if($mysqli->query($sql)){
        $sql =  "SELECT * FROM `products` WHERE `id` = {$id} ";
        $rs = $mysqli->query($sql);
        $outp['result'] = $rs->fetch_all(MYSQLI_ASSOC);
        $outp['size'] = $rs->num_rows;
        $outp['hasErr'] = false;
    }else{
        $outp['result'] = array('message'=>'System fail');
    }
}
?>