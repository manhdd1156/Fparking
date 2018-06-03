<?php
$table_id = intval($_GET['table_id']);
$id = intval($_GET['id']);
if($id>0 || $table_id>0){
    if($table_id>0){
        $sql =  "SELECT `id` FROM `orders` WHERE `table_id` = {$table_id} AND `status`=0 ORDER BY id DESC";
        $rs = $mysqli->query($sql);
        $od = $rs->fetch_all(MYSQLI_ASSOC);
        if(count($od)>0) $id = $od[0]['id'];
    }   
    $sql =  "SELECT * FROM `order_detail` WHERE `order_id` = {$id} ";
    $rs = $mysqli->query($sql);
    $details = $rs->fetch_all(MYSQLI_ASSOC);
    $outp['result'] = array();
    foreach($details as $detail){
        $psql =  "SELECT * FROM `products` WHERE `id`={$detail['product_id']}";
        $prs = $mysqli->query($psql);
        $detail['product'] = array_pop($prs->fetch_all(MYSQLI_ASSOC));
        $outp['result'][] = $detail;
    }
    $outp['size'] = $rs->num_rows;
    $outp['hasErr'] = false;
}
?>