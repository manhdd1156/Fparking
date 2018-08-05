<?php
if(isset($_POST['user_id']) && isset($_POST['table_id']) && isset($_POST['order_detail']) && count($_POST['order_detail'])>0){
    $user_id = intval($_POST['user_id']);
    $table_id = intval($_POST['table_id']);
    $status = intval($_POST['status']);
    $sql = "INSERT INTO `orders` (`create_time`, `user_id`, `table_id`, `status`) VALUES (now(), '{$user_id}','{$table_id}','{$status}')";
    $mysqli->query($sql);
    $order_id = $mysqli->insert_id;
    if($order_id>0){
        $sql = "UPDATE `tables` SET `status` = 1 WHERE `id` = {$table_id}";
        $mysqli->query($sql);
        foreach($_POST['order_detail'] as $order_detail){
            $order_detail['product_id'] = intval($order_detail['product_id']);
            $order_detail['price'] = floatval($order_detail['price']);
            $order_detail['quantity'] = intval($order_detail['quantity']);
            $order_detail['note'] = trim($mysqli->real_escape_string($order_detail['note']));
            $sql = "INSERT INTO `order_detail` (`order_id`, `product_id`, `price`, `quantity`, `note`) VALUES ({$order_id},{$order_detail['product_id']},{$order_detail['price']},{$order_detail['quantity']},'{$order_detail['note']}')";
            $mysqli->query($sql);
            if(!$mysqli->connect_errno){
                $outp['hasErr'] = false;
                $outp['result'] = array('message'=>'Create success');
            }else{
                $outp['hasErr'] = true;
                $outp['result'] = array('message'=>'Create unsuccess');
            }
        }
        $sql =  "SELECT name FROM `tables` WHERE `id` = {$table_id}";
        $rs = $mysqli->query($sql);
        $data = array_pop($rs->fetch_all(MYSQLI_ASSOC));
        $message = "New order from ".$data['name'];
        $pusher->trigger($PUSHER_CHANNEL, $PUSHER_EVENT_FOR_COOKER, array("message"=>$message));
    }else{
        $outp['result'] = array('message'=>'System fail');
    }

}
?>