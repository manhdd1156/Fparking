<?php
$id = intval($_POST['id']);
$status = intval($_POST['status']);
if($id>0 && $status){
	$sql =  "UPDATE `order_detail` SET `status`={$status} WHERE `id` = {$id}";
    $mysqli->query($sql);
    if($status == 2){
        $sql =  "SELECT od.quantity, p.name pname, t.name tname FROM order_detail od INNER JOIN products p ON od.product_id = p.id INNER JOIN orders o ON od.order_id=o.id INNER JOIN tables t ON o.table_id=t.id WHERE od.id = ".$id;
        $rs = $mysqli->query($sql);
        $data = array_pop($rs->fetch_all(MYSQLI_ASSOC));
        $message = "Done ".$data['quantity']." - ".$data['pname']." for ".$data['tname'];
        $pusher->trigger($PUSHER_CHANNEL, $PUSHER_EVENT_FOR_STAFF, array("message"=>$message));
    }
    $sql =  "SELECT od.*, o.table_id FROM order_detail od inner join orders o on od.order_id = o.id WHERE od.status < 2 ORDER BY od.id ASC";
    $rs = $mysqli->query($sql);
    $details = $rs->fetch_all(MYSQLI_ASSOC);
    $outp['result'] = array();
    foreach($details as $detail){
        $psql =  "SELECT * FROM `products` WHERE `id`={$detail['product_id']}";
        $tsql =  "SELECT * FROM `tables` WHERE `id`={$detail['table_id']}";
        $prs = $mysqli->query($psql);
        $trs = $mysqli->query($tsql);
        $detail['product'] = array_pop($prs->fetch_all(MYSQLI_ASSOC));
        $detail['table'] = array_pop($trs->fetch_all(MYSQLI_ASSOC));
        $outp['result'][] = $detail;
    }
    $outp['size'] = $rs->num_rows;
    $outp['hasErr'] = false;
}
?>