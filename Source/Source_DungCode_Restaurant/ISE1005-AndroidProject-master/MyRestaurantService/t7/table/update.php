<?php
if(isset($_POST['id']) && isset($_POST['name'])){
    $name = trim($mysqli->real_escape_string($_POST['name']));
    $description = trim($mysqli->real_escape_string($_POST['description']));
    $id = intval($_POST['id']);
    $sql = "UPDATE `tables` SET `name`='{$name}',`description`='{$description}' WHERE `id`={$id}";
    if($mysqli->query($sql)){
        $sql =  "SELECT * FROM `tables` WHERE `id` = {$id} ";
        $rs = $mysqli->query($sql);
        $outp['result'] = $rs->fetch_all(MYSQLI_ASSOC);
        $outp['size'] = $rs->num_rows;
        $outp['hasErr'] = false;
    }else{
        $outp['result'] = array('message'=>'System fail');
    }
}
?>