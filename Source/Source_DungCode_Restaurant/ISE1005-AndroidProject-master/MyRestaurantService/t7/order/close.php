<?php
if(isset($_POST['order_id'])){
    $order_id = intval($_POST['order_id']);
    $order_status = intval($_POST['status']);
    $sql = "SELECT * FROM `orders` WHERE `id` = {$order_id}";
    $rs = $mysqli->query($sql);
    $order = array_pop($rs->fetch_all(MYSQLI_ASSOC));
    $sql = "UPDATE `tables` SET `status` = 0 WHERE `id` = {$order['table_id']}";
    $mysqli->query($sql);
    $sql = "UPDATE `orders` SET `status` = {$order_status} WHERE `id` = {$order['id']}";
    $mysqli->query($sql);
}
?>