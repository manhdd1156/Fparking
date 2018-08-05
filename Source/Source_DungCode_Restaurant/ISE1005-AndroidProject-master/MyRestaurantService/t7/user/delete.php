<?php
if(isset($_POST['id'])){
    $id = intval($_POST['id']);
    $sql =  "DELETE FROM `users` WHERE `id`={$id}";
    if($mysqli->query($sql)){
        $outp['hasErr'] = false;
        $outp['result'] = array('message'=>'Delete success');
    }else{
        $outp['hasErr'] = false;
        $outp['result'] = array('message'=>'Delete unsuccess');
    }
}else{
    $outp['result'] = array('message'=>'System fail');
}
?>