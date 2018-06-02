<?php
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
?>