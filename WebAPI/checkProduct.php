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

	$productCode = $decode['scanCode'];

	$query = "SELECT * FROM Product WHERE Code = ".intval($productCode);

	$result = $link->query($query);

	//successfully got the result and result is bigger then 0
	if ($result->num_rows > 0) {
		$row = $result->fetch_assoc();
		echo("Product Name:".$row['Name'].",Product Amount:".$row['Amount'].",Product Price:".$row["Price"]);

	}else{
		echo "No Prodcut in Database";
	}
?>
