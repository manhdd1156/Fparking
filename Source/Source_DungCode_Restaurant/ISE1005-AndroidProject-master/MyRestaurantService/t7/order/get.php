<?php
$id = intval($_GET['id']);
$user_id = intval($_GET['user_id']);
$table_id = intval($_GET['table_id']);
$status  = intval($_GET['status']);
$sql =  "SELECT * FROM `Orders` WHERE 1=1 ";
if($id>0){
    $sql .= "AND `id` = {$id} ";
}
if($table_id>0){
    $sql .= "AND `table_id` = {$table_id} ";
}
if($status>-1){
    $sql .= "AND `status` = {$status} ";
}
$sql .= " ORDER BY `id` DESC";
$rs = $mysqli->query($sql);
$orders = $rs->fetch_all(MYSQLI_ASSOC);
$outp['result'] = array();
foreach($orders as $order){
    $usql =  "SELECT u.*, r.`name` as `role_name` FROM `users` u INNER JOIN `roles` r ON u.`role_id` = r.`id` WHERE u.`id`={$order['user_id']}";
    $tsql =  "SELECT * FROM `tables` WHERE `id`={$order['table_id']}";
    $urs = $mysqli->query($usql);
    $trs = $mysqli->query($tsql);
    $order['user'] = array_pop($urs->fetch_all(MYSQLI_ASSOC));
    $order['table'] = array_pop($trs->fetch_all(MYSQLI_ASSOC));
    $outp['result'][] = $order;
}
$outp['size'] = $rs->num_rows;
?>