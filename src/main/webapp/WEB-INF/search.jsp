<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
        "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<title>Search Page</title>
</head>

<body>

<h1>Search</h1>

<p>Please type in a food</p>
<p>We'll show you a list of close matches!</p>
<form name="foodSearch" action="search" method="POST">
Food: <input type="text" name="query" />
<input type ="submit" /><input type="reset" value="Clear" />
</form>
<h1>Results</h1>
<p>  <span class="error">${error}</span> </p>
<table>
    <tr>
        <th>Checkbox</th>
        <th>Food Name</th>
        <th>Calories</th>
        <th>Quantity Consumed</th>
    </tr>
    <form name="foodSelect" action="select" method="POST">
    <c:forEach items="${portionList}" var="portion">
        <tr>
            <td><input type = "checkbox" name="portionID" value=<c:out value="${portion.getFood().getName()}" /> </td>
            <td><c:out value="${portion.getFood().getName()}" /></td>
            <td><c:out value="${portion.getFood().getCalories()}" /></td>
            <td><input type = "text" name="portionSize" size="2"/></td>
        </tr>
    </c:forEach>
    <input type = "submit" />
    </form>
    </table>
</body>
</html>