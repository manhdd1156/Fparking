<?php
$name = trim($mysqli->real_escape_string($_GET['name']));
$id = intval($_GET['id']);
$sql =  "SELECT * FROM `products` WHERE 1=1 ";
if($id>0){
    $sql .= "AND `id` = {$id} ";
}
if(!empty($name)){
    $sql .= "AND `name` LIKE '%{$name}%' ";
}
$rs = $mysqli->query($sql);
$outp['result'] = $rs->fetch_all(MYSQLI_ASSOC);
$outp['size'] = $rs->num_rows;
$outp['hasErr'] = false;
?>