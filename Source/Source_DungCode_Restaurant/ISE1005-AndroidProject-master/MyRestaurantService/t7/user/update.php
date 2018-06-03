<?php
if(isset($_POST['name']) && isset($_POST['username']) && isset($_POST['password'])){
    $name = trim($mysqli->real_escape_string($_POST['name']));
    $username = trim($mysqli->real_escape_string($_POST['username']));
    $password = md5($_POST['password']);
    $role_id = intval($_POST['role_id']);
    $status = intval($_POST['status']);
    $id = intval($_POST['id']);

    $sql = "UPDATE `users` SET `name`='{$name}',`username`='{$username}',`password`='{$password}',`role_id`='{$role_id}',`status`='{$status}' WHERE `id`={$id}";
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