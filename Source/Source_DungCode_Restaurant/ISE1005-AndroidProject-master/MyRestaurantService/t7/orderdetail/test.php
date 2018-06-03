<?php
include_once "pusher/Pusher.php";
$pusher = new Pusher\Pusher($pusherConfig['key'], $pusherConfig['secret'], $pusherConfig['app_id'], $pusherOptions);
$pusher->trigger('ISE1005', "test", "DungNA SE04066");
?>