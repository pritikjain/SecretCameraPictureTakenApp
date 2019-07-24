<?php
  
    $file_path = "uploads/";
    
    //print_r($_FILES);
    if ( !isset( $_FILES["file"] )) {
        echo "fail";
        return;
    }
     
    $file_path = $file_path . basename( $_FILES['file']['name']);
    if(move_uploaded_file($_FILES['file']['tmp_name'], $file_path)) {
        echo "success";
    } else{
        echo "fail";
    }
 ?>
