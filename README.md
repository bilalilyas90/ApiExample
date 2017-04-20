# ApiExample
volley library usage

config.php

	$servername = "localhost";
	$username = "root";
	$password = "";
	$dbname = "students";

	// Create connection
	$conn = mysqli_connect($servername, $username, $password, $dbname);
	// Check connection
	if (!$conn) {
		die("Connection failed: " . mysqli_connect_error());
	}


PHP code for API's is given below

	require_once("config.php");
	
	$response = array();
	if(isset($_REQUEST['action'])){
		if($_REQUEST['action'] == "register"){
			if(isset($_REQUEST['name']) && isset($_REQUEST['email']) && isset($_REQUEST['password'])){
				$name = $_REQUEST['name'];
				$email = $_REQUEST['email'];
				$password = $_REQUEST['password'];
				
				$query = "INSERT INTO `students`(`name`,`email`,`password`) 
									VALUES('$name','$email','$password')";
				$result = mysqli_query($conn,$query);
				if($result){
					$response['error'] = false;
					$response['message'] = "Successfully insert";
				}else{
					$response['error'] = true;
					$response['error_msg'] = "something went wrong";
				}
			}else{
				$response['error'] = true;
				$response['error_msg'] = "required parameters(email,name,password) missing";
			}
		}else if($_REQUEST['action'] == "login"){
			if(isset($_REQUEST['email']) && isset($_REQUEST['password'])){
				$email = $_REQUEST['email'];
				$password = $_REQUEST['password'];
				
				$query = "SELECT `id`,`name`,`email` FROM `students` WHERE `email` = '$email' AND `password` = '$password'";
				$result = mysqli_query($conn,$query);
				
				if(mysqli_num_rows($result) > 0){
					$response['error'] = false;
					$response['student_rec'] = mysqli_fetch_assoc($result);
					//$response['message'] = "Success";
					
				}else{
					$response['error'] = true;
					$response['error_msg'] = "email and password incorrect";
				}
				
			}else{
				$response['error'] = true;
				$response['error_msg'] = "required parameters(email,password) missing";
			}
		}else{
			$response['error'] = true;
			$response['error_msg'] = "action paramter value invalid";
		}
	}else{
		$response['error'] = true;
		$response['error_msg'] = "action parameter missing";
	}
	echo json_encode($response);
