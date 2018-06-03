<?php
if(isset($_POST['username']) && isset($_POST['password'])){
    $username = trim($mysqli->real_escape_string($_POST['username']));
    $password = md5($_POST['password']);
    $sql =  "SELECT u.*, r.`name` as `role_name` FROM `users` u INNER JOIN `roles` r ON u.`role_id` = r.`id` WHERE  u.`username`='{$username}' AND u.`password`='{$password}'";
    $rs = $mysqli->query($sql);
    $outp['result'] = $rs->fetch_all(MYSQLI_ASSOC);
    $outp['size'] = $rs->num_rows;
    $outp['hasErr'] = false;
}
?>