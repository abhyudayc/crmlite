<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <!-- Latest compiled and minified CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">

    <!-- jQuery library -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>

    <!-- Latest compiled JavaScript -->
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

    <!-- external link -->
    <link rel="stylesheet" type="text/css" href="css/style.css">
    <title>CRMLite</title>
</head>

<body class="bgcolor">
    <h1 style="text-align: center;color: white;">KEEP CLOSING</h1>
    <div class="loginform">
    <div class="card">
            <form method="post" action="user-login">
                <!-- <img src="images/CODING-SUPER-STAR.png" alt="logo" width="50%" height="50%"> -->
               
                <div class="form-group">
                    <input name="user-name" type="text" class="form-control" placeholder="Username" autofocus>
                </div>
                <div class="form-group">
                    <input name="user-password" type="password" class="form-control" placeholder="Password">
                </div>
                <div class="form-group">
                    <small id="emailHelp" class="form-text text-muted"><%=(request.getAttribute("MSG_FRM_SERVLET")==null?"":request.getAttribute("MSG_FRM_SERVLET")) %></small>
                </div>
                <div class="form-group">
                    <a href="#" style="text-decoration: none;"><input type="submit" class="btn btn-danger" value="Submit"></a>
                </div>
                
            </form>
    </div>
</div>
    <script>
    	sessionStorage.clear();
    </script>
</body>

</html>