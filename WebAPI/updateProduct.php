<?php
	$link = mysqli_connect('localhost', 'root', '123456','Ensemble');

	//check connection
	if (!$link) {
	    echo json_encode(array("Error"=>"true", "ErrorMessage"=>"Could not connect to mysql"));
	    exit;
	}

	//get the json string
	foreach($_POST as $key=>$value)
	{
	  $content = $key;
	}

	//decode json string to array
	$decode = json_decode($content,true);

	$productCode = $decode['ProductCode'];
	$productName = $decode["ProductName"];
	$productAmount = $decode["ProductAmount"];
	$productPrice = $decode["ProductPrice"];

	$fixProductPrice = str_replace('_', '.', $productPrice);

	$query = "UPDATE Product SET Name = '".$productName."', Amount = ".$productAmount.", Price = '".$fixProductPrice."' WHERE Code = ".$productCode;
	
	

	if ($link->query($query) === TRUE) {
		echo "Update Successfully";
	}else{
		echo "Fail to Update";
	}
?>
