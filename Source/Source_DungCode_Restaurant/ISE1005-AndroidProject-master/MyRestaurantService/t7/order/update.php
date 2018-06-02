<?php
if(isset($_POST['id'])  && isset($_POST['order_detail']) && count($_POST['order_detail'])>0){
    $id = intval($_POST['id']);
    $ids = array();
    if($id>0){
        foreach($_POST['order_detail'] as $order_detail){
            $or_id = intval($order_detail['id']);
            $order_detail['product_id'] = intval($order_detail['product_id']);
            $order_detail['price'] = floatval($order_detail['price']);
            $order_detail['quantity'] = intval($order_detail['quantity']);
            $order_detail['note'] = trim($mysqli->real_escape_string($order_detail['note']));

            if($or_id>0){
                $sql = "UPDATE `order_detail` SET `quantity` = {$order_detail['quantity']} WHERE `id`={$or_id} AND `status`=0";
                $mysqli->query($sql);                
                $ids[] = $or_id;
            }else{
                $sql = "INSERT INTO `order_detail` (`order_id`, `product_id`, `price`, `quantity`, `note`) VALUES ({$id},{$order_detail['product_id']},{$order_detail['price']},{$order_detail['quantity']},'{$order_detail['note']}')";
                $mysqli->query($sql);                
                $ids[] = $mysqli->insert_id;
            }
        }
        if(!$mysqli->connect_errno){
            $mysqli->query("DELETE FROM `order_detail` WHERE `order_id` = {$id}  AND `status`=0 AND `id` NOT IN (".implode(',', $ids).")");
            $outp['hasErr'] = false;
            $outp['result'] = array('message'=>'Update success');
        }else{
            $outp['hasErr'] = true;
            $outp['result'] = array('message'=>'Update unsuccess');
        }
        $sql =  "SELECT t.name FROM `tables` t INNER JOIN `orders` o ON o.table_id=t.id WHERE o.id=".$id;
        $rs = $mysqli->query($sql);
        $data = array_pop($rs->fetch_all(MYSQLI_ASSOC));
        $message = "Update order from ".$data['name'];
        $pusher->trigger($PUSHER_CHANNEL, $PUSHER_EVENT_FOR_COOKER, array("message"=>$message));
    }else{
        $outp['result'] = array('message'=>'System fail');
    }

}
?>