<?php
if(isset($_POST['name']) && isset($_POST['username']) && isset($_POST['password'])){
    $name = trim($mysqli->real_escape_string($_POST['name']));
    $username = trim($mysqli->real_escape_string($_POST['username']));
    $password = md5($_POST['password']);
    $role_id = intval($_POST['role_id']);
    $status = intval($_POST['status']);

    $sql = "INSERT INTO `users` (`name`, `username`, `password`, `role_id`, `status`) VALUES ('{$name}','{$username}', '{$password}', {$role_id}, {$status})";
    $rs = $mysqli->query($sql);
    $insert_id = $mysqli->insert_id;
    if($insert_id>0){
        $sql =  "SELECT * FROM `users` WHERE `id` = {$insert_id} ";
        $rs = $mysqli->query($sql);
        $outp['result'] = $rs->fetch_all(MYSQLI_ASSOC);
        $outp['size'] = $rs->num_rows;
        $outp['hasErr'] = false;
    }else{
        $outp['result'] = array('message'=>'System fail');
    }
}
?>