<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
        "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<title>Login Page</title>
</head>

<body>
<h1>Log In</h1>
<form name="login" action="login" method="POST">
    Email: <input type="text" name="email" />
    Password: <input type = "password" name="password" />
    <input type ="submit" value="Login" /><input type="reset" value="clear" />
</form>
<p>  <span class="error">${error}</span> </p>

</body>
</html>