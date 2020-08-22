<?php

    use Slim\Views\PhpRenderer;

    require __DIR__.'/vendor/autoload.php';

    require __DIR__.'/include/DBHelper.php';




    // Start PHP sessioncheckUser
    session_start(); //by default requires session storage

    // Create app
    $app = new \Slim\App(
        [
            'settings' => [
            'displayErrorDetails' => true,
                        ],
        ]
    );

    // Get container
    $container = $app->getContainer();
    // INSTANTIATE PHP RENDERER FOR PHP-VIEWS
    $container['renderer'] = new PhpRenderer(__DIR__.'/resources/views');
    
    // Register component on container
    $container['view'] = function ($container) {
    $view = new \Slim\Views\Twig(__DIR__.'/resources/views', [
        'cache' => false
    ]);

    // Instantiate and add Slim specific extension
    $router = $container->get('router');
    $uri = \Slim\Http\Uri::createFromEnvironment(new \Slim\Http\Environment($_SERVER));
    $view->addExtension(new \Slim\Views\TwigExtension($router, $uri));

    //making flash global so that we can access anywhere
    $view->getEnvironment()->addGlobal('flash',$container->flash);


    return $view;
    };


    // Register provider
    $container['flash'] = function () {
        return new \Slim\Flash\Messages();
    };

    function checkNull($val)
        {
            if(!empty($val))
            {
                return $val;
            }
            else
            {
                return 'NULL';
            }
        }

    // route for api -user register
    $app->post('/userRegister',function($request,$reponse,$args){
    
        $name = $request->getParam('name');
        $email = $request->getParam('email');
        $number = $request->getParam('number');
        $password = $request->getParam('password');
        $aadharNo = $request->getParam('aadharNo');
        $adminStatus = $request->getParam('adminStatus');
        $userStatus = $request->getParam('userStatus');
        
        $aadharNo = !empty($aadharNo) ? "$aadharNo" : "NULL";
        
        $dbHelper = new DbHelper();
        
        
        if(sizeof($dbHelper->UserEmailExists($email))>0){
            $response["error"] = true;
            $response["message"] = "Sorry, this email already existed";
            echo json_encode($response);
        }
        else if(sizeof($dbHelper->UserMobileExists($number))>0){
            $response["error"] = true;
            $response["message"] = "Sorry, this number already existed";
            echo json_encode($response);
        }
        else{
            $insertUser = $dbHelper->registerUser($name,$email,$number,md5($password),$adminStatus,$userStatus,$aadharNo);
            if($insertUser){
                $response["error"] = false;
                $response["message"] = "You are successfully registered";
                echo json_encode($response);
            }else{
                $response["error"] = true;
                $response["message"] = "Oops! An error occurred while registereing";
                echo json_encode($response);
            }
        
        
        }
    
    })->setName('userRegister');

    //route for api user login check
    $app->post('/userLogin',function($request,$reponse,$args){
    
        $phNo = $request->getParam('phNo');
        $password = $request->getParam('password');
        
        $userArray=array();
        
        
        $dbHelper = new DbHelper();
        
        $user=$dbHelper->checkUser($phNo,md5($password)); 
        
            if(!sizeof($dbHelper->UserMobileExists($phNo))>0){
                $response["error"] = true;
                $response["message"] = "User with this mobile number not exists need regiter first";
                $response["userData"] = null;
                echo json_encode($response);
            }
            else{
                if(sizeof($user)>0)
                {
                	$dbHelper->updateUserTimeStamp($user[0]->u_id);
                    
                    $response["error"] = false;
                    $response["message"] = "User login successfully ";
                    
                    $userArray['u_id'] = $user[0]->u_id;
                    $userArray['acive_status'] = $user[0]->acive_status;
                    
                    $response["userData"] = $userArray;
                    echo json_encode($response);
                }
                else{
                    $response["error"] = true;
                    $response["message"] = "User mobile number or password not match";
                    $response["userData"] = null;
                    echo json_encode($response);
                }
        
            }
        
    
    
    })->setName('userLogin');
    
    
    
    //route for api super admin login check
    $app->post('/superAdminLogin',function($request,$reponse,$args){
    
        $phNo = $request->getParam('phNo');
        $password = $request->getParam('password');
        $userArray=array();
        $dbHelper = new DbHelper();
        
        $user=$dbHelper->checkSuperAdmin($phNo,md5($password)); 
        
            if(!sizeof($dbHelper->SuperAdminMobileExists($phNo))>0){
                $response["error"] = true;
                $response["message"] = "User with this mobile number not exists need regiter first";
                $response["userData"] = null;
                echo json_encode($response);
            }
            else{
                if(sizeof($user)>0)
                {
                	$dbHelper->updateSuperAdminTimeStamp($user[0]->s_id);
                    
                    $response["error"] = false;
                    $response["message"] = "User login successfully ";
                    
                    $userArray['u_id'] = $user[0]->s_id;
                    $userArray['acive_status'] = $user[0]->acive_status;
                    
                    $response["userData"] = $userArray;
                    echo json_encode($response);
                }
                else{
                    $response["error"] = true;
                    $response["message"] = "User mobile number or password not match";
                    $response["userData"] = null;
                    echo json_encode($response);
                }
        
            }
    })->setName('superAdminLogin');



    //route for api user password  update
    $app->post('/userForgotPassword',function($request,$reponse,$args){
        $email = $request->getParam('email');
        $password = $request->getParam('password');
        $number = $request->getParam('number');
        
        $dbHelper = new DbHelper();
        if(!sizeof($dbHelper->UserEmailExists($email))>0){
            $response["error"] = true;
            $response["message"] = "User with this email not exists need regiter first";
            echo json_encode($response);
        }
        else if(!sizeof($dbHelper->UserMobileExists($number))>0){
            $response["error"] = true;
            $response["message"] = "User with this mobile number not exists need regiter first";
            echo json_encode($response);
        }
        else{
            $ispasswordupdated = $dbHelper->userForgotPassword($email,$number,md5($password));
            if($ispasswordupdated != NULL)
            {
                $response["error"] = false;
                $response["message"] = "Password updated successfully";
                echo json_encode($response);
            }
            else{
                $response["error"] = true;
                $response["message"] = "Password not updated successfully";
                echo json_encode($response);
            }
        
        }
    
    })->setName('userForgotPassword');


    //route for api user details by  user id
    $app->get('/userDetailsByUserId',function($request,$reponse,$args){
        $userId = $request->getParam('userId');
        
        $dbHelper = new DbHelper();
        $result =   $dbHelper->getUserDetailsByUserId($userId);
        if(sizeof($result)>0)
        {
            $response["error"] = false;
            $response["message"] = "Data Found";
            $response["user"] = $result;
            echo json_encode($response);
        }else{
            $response["error"] = true;
            $response["message"] = "Data Not  Found";
            $response["user"] = NULL;
            echo json_encode($response);
        }
    
    
    })->setName('userDetailsByUserId');

    // route for api -user profile update
    $app->post('/userUpdateProfile',function($request,$reponse,$args){
    
    
        $email = $request->getParam('email');
        $user_id = $request->getParam('user_id');
        $dob = $request->getParam('dob');
        $gender = $request->getParam('gender');
        $user_dr_id = $request->getParam('user_dr_id');
        $user_diabetes = $request->getParam('user_diabetes');
        $user_obese = $request->getParam('user_obese');
        $user_anemia = $request->getParam('user_anemia');
        $user_heart_disease = $request->getParam('user_heart_disease');
        $user_kidney_disease = $request->getParam('user_kidney_disease');
        $user_cancer = $request->getParam('user_cancer');
        $user_other_disease = $request->getParam('user_other_disease');
        
        $user_dr_id = !empty($user_dr_id) ? "$user_dr_id" : "NULL";
        
        $dbHelper = new DbHelper();
        
        if($user_dr_id == "NULL")
        {
            $isupdateUserProfile = $dbHelper->updateUserProfile($email,$user_id,$dob,$gender,$user_dr_id,$user_diabetes,$user_obese,$user_anemia,$user_heart_disease,$user_kidney_disease,$user_cancer,$user_other_disease);
            
            
            if($isupdateUserProfile){
                $response["error"] = false;
                $response["message"] = "Profile  successfully updated";
                echo json_encode($response);
            }else{
                $response["error"] = true;
                $response["message"] = "Oops! An error occurred while profile updateding";
                echo json_encode($response);
            }
        }else{
        
            if(sizeof($dbHelper->UserCheckDrIdValid($user_dr_id))>0){
            
                $isupdateUserProfile = $dbHelper->updateUserProfile($email,$user_id,$dob,$gender,$user_dr_id,$user_diabetes,$user_obese,$user_anemia,$user_heart_disease,$user_kidney_disease,$user_cancer,$user_other_disease);
                if($isupdateUserProfile){
                $response["error"] = false;
                $response["message"] = "Profile  successfully updated";
                echo json_encode($response);
                }else{
                $response["error"] = true;
                $response["message"] = "Oops! An error occurred while profile updateding";
                echo json_encode($response);
                }
            
            }else{
                $response["error"] = true;
                $response["message"] = "Sorry, this Dr id is not existed";
                echo json_encode($response);
            }
    }
    
    
    })->setName('userUpdateProfile');



    // route for api insert test details
    $app->post('/syncTestDetails',function($request,$reponse,$args){
    
       $data = $request->getParam('testDetails');
       $dbHelper = new DbHelper();
        
       //Util arrays to create response JSON
		$a=array();
		$b=array();
           //Loop through an Array and insert data read from JSON into MySQL DB
            for($i=0; $i<count($data) ; $i++)
                {
                    $val=$data[$i];
                   
                    if(!sizeof( $dbHelper-> checkHbTestResultByTestId($val['test_id'],$val['u_id']))>0)
        				{
  							$insertTest = $dbHelper-> insertHbTestDetails($val['test_id'],$val['u_id'],$val['client_name'],
  								$val['client_age'],$val['client_gender'],$val['client_pregnant_status'],$val['client_hb_value'],$val['district'],$val['block'],$val['phc_uhc_sc'],$val['test_time_stamp']);
  							if($insertTest)
  								{
      								$b["test_id"] = $val['test_id'];
      								$b["status"] = 'yes';
      								array_push($a,$b);
  								}
  							else{
       								$b["test_id"] = $val['test_id'];
      								$b["status"] = 'no';
      								array_push($a,$b);
  								}
        				}else
        					{
            					$b["test_id"] = $val['test_id'];
            					$b["status"] = 'yes';
            					array_push($a,$b);
        					}
                }
                
            if(count($a)>0)
                {
                    $response["error"] = false;
                    $response["message"] = "Data Found";
                    $response["testIds"] = $a;
                    echo json_encode($response);
                }else
                {
                    $response["error"] = true;
                    $response["message"] = "Data Not  Found";
                    $response["testIds"] = NULL;
                    echo json_encode($response);
                }
    })->setName('syncTestDetails');



    

   //Api for download hb test result 
$app->post('/userDownloadHbTestDetails',function($request,$reponse,$args){
	$u_id = $request->getParam('u_id');
    $page_no = $request->getParam('page_no');
    
    $items_per_page =  50; 
    $offset = ($page_no - 1) * $items_per_page;
    $dbHelper = new DbHelper();

   	
   	$test_result=   $dbHelper->downloadHbTestDetailsByUserId($offset,$items_per_page,$u_id);
    if(sizeof($test_result)>0)
    {
    	$response["error"] = false;
        $response["message"] = "Data Found";
        $response['testDetails'] = $test_result;
        echo json_encode($response);
    }
    else
    {
    	$response["error"] = true;
        $response["message"] = "Test Ressult Not Found";
        $response['testDetails'] = NULL;
        echo json_encode($response);
    }
     
    
})->setName('userDownloadHbTestDetails');


   //Api for display hb test result 
$app->post('/displayHbTestDetails',function($request,$reponse,$args){
	$fromDate = $request->getParam('fromDate');
    $toDate = $request->getParam('toDate');
    $fromAge = $request->getParam('fromAge');
    $toAge = $request->getParam('toAge');
    $gender = $request->getParam('gender');
    $anemicStatus = $request->getParam('anemicStatus');
    $block = $request->getParam('block');
    
    
    
    $dbHelper = new DbHelper();

   	
   	$test_result=   $dbHelper->displayHbTestDetailsByFilter($fromDate,$toDate,$fromAge,$toAge,$gender,$anemicStatus,$block);
    if(sizeof($test_result)>0)
    {
    	$response["error"] = false;
        $response["message"] = "Data Found";
        $response['testDetails'] = $test_result;
        echo json_encode($response);
    }
    else
    {
    	$response["error"] = true;
        $response["message"] = "Test Ressult Not Found";
        $response['testDetails'] = NULL;
        echo json_encode($response);
    }
     
    
})->setName('displayHbTestDetails');


    $app->get('/hello', function ($request,$response){

        return $this->renderer->render($response, "abc.php");

    })->setName('hello');

$app->run();

?>