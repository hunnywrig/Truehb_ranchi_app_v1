<?php

class DBHelper{

    private $conn;
    
    function __construct() {
        require_once 'db.php';
        // opening db connection
        $db = new db();
        $this->conn = $db->connect();
    }



    // register user

    public function registerUser($name,$email,$number,$password,$adminStatus,$userStatus,$aadharNo){


        $sql = "INSERT INTO user_data_table (name,email,number,password,admin_status,user_status,aadhar_no) VALUES (?,?,?,?,?,?,?)";
           try{

            $stmt = $this->conn->prepare($sql);
            $result = $stmt->execute(
                [$name,$email,$number,$password,$adminStatus,$userStatus,$aadharNo]
            );        

            return $result;

        }catch(PDOException $e){
            echo '{"error":"text": '.$e->getMessage().'}';
            error_log("erro", 1,$e->getMessage());
        }


    }


    //check user email  allReady Exists

    public function UserEmailExists($email){

        $sql = "SELECT * FROM user_data_table WHERE email = :email";

        try{

            $stmt = $this->conn->prepare($sql);

            $stmt->execute([
                'email'=>$email
            ]);

            $user = $stmt->fetchAll(PDO::FETCH_OBJ);

            return $user;

        }catch(PDOException $e){
            echo '{"error":"text": '.$e->getMessage().'}';
            error_log("erro", 1,$e->getMessage());
        }

    }
    
    //check user mobile number allReady Exists
    public function UserMobileExists($ph_no){

        $sql = "SELECT * FROM user_table WHERE ph_no = :ph_no";

        try{

            $stmt = $this->conn->prepare($sql);

            $stmt->execute([
                'ph_no'=>$ph_no
            ]);

            $user = $stmt->fetchAll(PDO::FETCH_OBJ);

            return $user;

        }catch(PDOException $e){
            echo '{"error":"text": '.$e->getMessage().'}';
            error_log("erro", 1,$e->getMessage());
        }

    }
    
    //check super admin mobile number allReady Exists
    public function SuperAdminMobileExists($ph_no){

        $sql = "SELECT * FROM super_admin_table WHERE ph_no = :ph_no";

        try{

            $stmt = $this->conn->prepare($sql);

            $stmt->execute([
                'ph_no'=>$ph_no
            ]);

            $user = $stmt->fetchAll(PDO::FETCH_OBJ);

            return $user;

        }catch(PDOException $e){
            echo '{"error":"text": '.$e->getMessage().'}';
            error_log("erro", 1,$e->getMessage());
        }

    }

   

    // check user login
    public function checkUser($ph_no,$password){

        $sql = "SELECT * FROM user_table WHERE ph_no = :ph_no and password = :password";

        try{

            $stmt = $this->conn->prepare($sql);

            $stmt->execute([
                'ph_no'=>$ph_no,
                'password'=>$password
            ]);

            $user = $stmt->fetchAll(PDO::FETCH_OBJ);

            return $user;

        }catch(PDOException $e){
            echo '{"error":"text": '.$e->getMessage().'}';
            error_log("erro", 1,$e->getMessage());
        }

    }
    
    // check super admin login
    public function checkSuperAdmin($ph_no,$password){

        $sql = "SELECT * FROM super_admin_table WHERE ph_no = :ph_no and password = :password";

        try{

            $stmt = $this->conn->prepare($sql);

            $stmt->execute([
                'ph_no'=>$ph_no,
                'password'=>$password
            ]);

            $user = $stmt->fetchAll(PDO::FETCH_OBJ);

            return $user;

        }catch(PDOException $e){
            echo '{"error":"text": '.$e->getMessage().'}';
            error_log("erro", 1,$e->getMessage());
        }

    }


      //function for update time stamp fro the user
    public function updateUserTimeStamp($u_id)
    {
        $sql = "UPDATE user_table SET last_login = now() WHERE u_id = ?";
        
        try{
        
            $stmt = $this->conn->prepare($sql);
             $stmt->execute(
                [$u_id]
            );        
            $result = $stmt->rowCount();
            return $result;

        }catch(PDOException $e){
            echo '{"error":"text": '.$e->getMessage().'}';
            error_log("erro", 1,$e->getMessage());
        }
    }
    
    
    //function for update time stamp for the super admin
    public function updateSuperAdminTimeStamp($u_id)
    {
        $sql = "UPDATE super_admin_table SET last_login = now() WHERE s_id = ?";
        
        try{
        
            $stmt = $this->conn->prepare($sql);
             $stmt->execute(
                [$u_id]
            );        
            $result = $stmt->rowCount();
            return $result;

        }catch(PDOException $e){
            echo '{"error":"text": '.$e->getMessage().'}';
            error_log("erro", 1,$e->getMessage());
        }
    }

    // user forgot password

    public function userForgotPassword($email,$number,$password){
        $sql = "UPDATE user_data_table SET password = ? WHERE email = ? and number = ? ";

        try{

            $stmt = $this->conn->prepare($sql);
             $stmt->execute(
                [$password,$email,$number]
            );        
             $result = $stmt->rowCount();
            return $result;

        }catch(PDOException $e){
            echo '{"error":"text": '.$e->getMessage().'}';
            error_log("erro", 1,$e->getMessage());
        }
    }



//function for get user data by user id
     public function getUserDetailsByUserId($userId)
    {
        try{
        $sql = "SELECT * FROM user_data_table WHERE user_id = :user_id";
        


            $stmt = $this->conn->prepare($sql);

            $stmt->execute(['user_id'=>$userId]);

            $test = $stmt->fetchAll(PDO::FETCH_OBJ);

            return $test;

        }catch(PDOException $e){
            echo '{"error":"text": '.$e->getMessage().'}';
             error_log("erro", 1,$e->getMessage());
        }
    }    
    
    // Api user update profile
    
    public function updateUserProfile($email,$user_id,$dob,$gender,$user_dr_id,$user_diabetes,$user_obese,$user_anemia,$user_heart_disease,$user_kidney_disease,$user_cancer,$user_other_disease)
    {
        $sql = "UPDATE truehb_alpha_user SET user_email = ? , user_dob = ? , user_gender = ? , user_dr_id = ? ,  user_diabetes = ? , user_obese = ? , user_anemia = ? , user_heart_disease = ? , user_kidney_disease = ? , user_cancer = ? , user_other_disease = ?  WHERE user_id = ?";
        try{

            $stmt = $this->conn->prepare($sql);
             $stmt->execute(
                [$email,$dob,$gender,$user_dr_id,$user_diabetes,$user_obese,$user_anemia,$user_heart_disease,$user_kidney_disease,$user_cancer,$user_other_disease,$user_id]
            );        
            $result = $stmt->rowCount();
            return $result;

        }catch(PDOException $e){
            //echo '{"error":"text": '.$e->getMessage().'}';
            error_log("updateUserProfile___".$e->getMessage(),0);
            error_log("erro", 1,$e->getMessage());
        }
    }
   
    
   //function for the save Hb test details
    public function insertHbTestDetails($test_id,$u_id,$client_name,$client_age,$client_gender,$client_pregnant_status,$client_hb_value,$district,$block,$phc_uhc_sc,$test_time_stamp)
    {
         try{
      
         $sql = "INSERT INTO test_details_table (test_id,u_id,client_name,client_age,client_gender,client_pregnant_status,client_hb_value,district,block,phc_uhc_sc,test_time_stamp) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
          

            $stmt = $this->conn->prepare($sql);
            $result = $stmt->execute(
                [$test_id,$u_id,$client_name,$client_age,$client_gender,$client_pregnant_status,$client_hb_value,$district,$block,$phc_uhc_sc,$test_time_stamp]
            );        

            return $result;
    
        }catch(PDOException $e){
            echo '{"error":"text": '.$e->getMessage().'}';
             error_log("erro", 1,$e->getMessage());
        }

    }
    
    //function for check hb test result is allready exist
    public function  checkHbTestResultByTestId($test_id,$u_id)
    {
        
        try{
        $sql = "SELECT * FROM test_details_table WHERE test_id = :test_id AND  u_id = :u_id";
        


            $stmt = $this->conn->prepare($sql);

            $stmt->execute([
                'test_id'=>$test_id,
                'u_id'=>$u_id
            ]);

            $test = $stmt->fetchAll(PDO::FETCH_OBJ);

            return $test;

        }catch(PDOException $e){
            echo '{"error":"text": '.$e->getMessage().'}';
             error_log("erro", 1,$e->getMessage());
        }
    }

    // function for get test details by user id
    public function downloadHbTestDetailsByUserId($offset,$items_per_page,$u_id)
    {
        try{
        $sql = "SELECT * FROM test_details_table WHERE u_id = :u_id LIMIT  $offset , $items_per_page";
      //  echo $sql;
        


            $stmt = $this->conn->prepare($sql);

            $stmt->execute(['u_id'=>$u_id]);
           
          //  $stmt->execute([]);

            $test = $stmt->fetchAll(PDO::FETCH_OBJ);

            return $test;

        }catch(PDOException $e){
            echo '{"error":"text": '.$e->getMessage().'}';
             error_log("erro", 1,$e->getMessage());
        }
    }
    
    // function for get display hb test result 
    public function displayHbTestDetailsByFilter($fromDate,$toDate,$fromAge,$toAge,$gender,$anemicStatus,$block)
    {
        try{
        
        $sql = "SELECT * FROM test_details_table WHERE DATE(test_time_stamp) BETWEEN '$fromDate' AND '$toDate' AND client_age BETWEEN $fromAge AND $toAge";
        
        if($gender!=0)
        {
            $sql = "SELECT * FROM test_details_table WHERE DATE(test_time_stamp) BETWEEN '$fromDate' AND '$toDate' AND client_age BETWEEN $fromAge AND $toAge AND client_gender = $gender";

            if($block != 'NA')
            {
            	$sql = "SELECT * FROM test_details_table WHERE DATE(test_time_stamp) BETWEEN '$fromDate' AND '$toDate' AND client_age BETWEEN $fromAge AND $toAge AND client_gender = $gender AND block = '$block'";
            }

            if($anemicStatus != 0)
            {
            	if($anemicStatus == 1)
            	{
            		$sql = "SELECT * FROM test_details_table WHERE DATE(test_time_stamp) BETWEEN '$fromDate' AND '$toDate' AND client_age BETWEEN $fromAge AND $toAge AND client_gender = $gender AND client_hb_value < 8 ";

            	}else if($anemicStatus == 2)
            	{
            		$sql = "SELECT * FROM test_details_table WHERE DATE(test_time_stamp) BETWEEN '$fromDate' AND '$toDate' AND client_age BETWEEN $fromAge AND $toAge AND client_gender = $gender AND client_hb_value > 8 ";
            	}
            }
        }

        if($block != 'NA')
        {
            $sql = "SELECT * FROM test_details_table WHERE DATE(test_time_stamp) BETWEEN '$fromDate' AND '$toDate' AND client_age BETWEEN $fromAge AND $toAge AND block = '$block'";

            if($gender!=0)
            {
            	$sql = "SELECT * FROM test_details_table WHERE DATE(test_time_stamp) BETWEEN '$fromDate' AND '$toDate' AND client_age BETWEEN $fromAge AND $toAge AND client_gender = $gender AND block = '$block'";
            }

            if($anemicStatus != 0)
            {
            	if($anemicStatus == 1)
            	{
            		$sql = "SELECT * FROM test_details_table WHERE DATE(test_time_stamp) BETWEEN '$fromDate' AND '$toDate' AND client_age BETWEEN $fromAge AND $toAge AND block = '$block' AND client_hb_value < 8 ";

            	}else if($anemicStatus == 2)
            	{
            		$sql = "SELECT * FROM test_details_table WHERE DATE(test_time_stamp) BETWEEN '$fromDate' AND '$toDate' AND client_age BETWEEN $fromAge AND $toAge AND block = '$block' AND client_hb_value > 8 ";
            	}
            }
        }

        if($anemicStatus != 0)
        {

        	if($anemicStatus == 1)
            	{
            		$sql = "SELECT * FROM test_details_table WHERE DATE(test_time_stamp) BETWEEN '$fromDate' AND '$toDate' AND client_age BETWEEN $fromAge AND $toAge AND  client_hb_value < 8 ";

            	}else if($anemicStatus == 2)
            	{
            		$sql = "SELECT * FROM test_details_table WHERE DATE(test_time_stamp) BETWEEN '$fromDate' AND '$toDate' AND client_age BETWEEN $fromAge AND $toAge AND  client_hb_value > 8 ";
            	}

        	if($block != 'NA')
        	{


            	if($anemicStatus == 1)
            		{
            			$sql = "SELECT * FROM test_details_table WHERE DATE(test_time_stamp) BETWEEN '$fromDate' AND '$toDate' AND client_age BETWEEN $fromAge AND $toAge AND block = '$block' AND client_hb_value < 8 ";

            		}else if($anemicStatus == 2)
            		{
            			$sql = "SELECT * FROM test_details_table WHERE DATE(test_time_stamp) BETWEEN '$fromDate' AND '$toDate' AND client_age BETWEEN $fromAge AND $toAge AND block = '$block' AND client_hb_value > 8 ";
            		}
           }

           if($gender != 0)
        	{


            	if($anemicStatus == 1)
            		{
            			$sql = "SELECT * FROM test_details_table WHERE DATE(test_time_stamp) BETWEEN '$fromDate' AND '$toDate' AND client_age BETWEEN $fromAge AND $toAge AND client_gender = $gender AND client_hb_value < 8 ";

            		}else if($anemicStatus == 2)
            		{
            			$sql = "SELECT * FROM test_details_table WHERE DATE(test_time_stamp) BETWEEN '$fromDate' AND '$toDate' AND client_age BETWEEN $fromAge AND $toAge AND client_gender = $gender AND client_hb_value > 8 ";
            		}
           }
        }
        
        
       // echo $sql;

            $stmt = $this->conn->prepare($sql);

            $stmt->execute([]);
           
          //  $stmt->execute([]);

            $test = $stmt->fetchAll(PDO::FETCH_OBJ);

            return $test;

        }catch(PDOException $e){
            echo '{"error":"text": '.$e->getMessage().'}';
             error_log("erro", 1,$e->getMessage());
        }
    }


}

?>