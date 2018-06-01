<?php
$name = trim($mysqli->real_escape_string($_GET['name']));
$username = trim($mysqli->real_escape_string($_GET['username']));
$id = intval($_GET['id']);
$role = intval($_GET['role']);

$sql =  "SELECT u.*, r.`name` as `role_name` FROM `users` u INNER JOIN `roles` r ON u.`role_id` = r.`id` WHERE 1=1 ";
if($id>0){
    $sql .= "AND u.`id` = {$id} ";
}
if(!empty($name)){
    $sql .= "AND u.`name` LIKE '%{$name}%' ";
}
if(!empty($username)){
    $sql .= "AND u.`username` LIKE '%{$username}%' ";
}
if($role>0){
    $sql .= "AND u.`role_id` = {$role} ";
}
$sql .= "ORDER BY u.`id` DESC";
$rs = $mysqli->query($sql);
$outp['result'] = $rs->fetch_all(MYSQLI_ASSOC);
$outp['size'] = $rs->num_rows;
$outp['hasErr'] = false;
?>