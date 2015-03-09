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
<form name="foodSearch" action="SearchServlet" method="POST">
Food: <input type="text" name="query" />
<input type ="submit" /><input type="reset" value="clear" />
</form>
<h1>Results</h1>
<table>
    <tr>
        <th>Food Name</th>
        <th>Calories</th>
        <th>Portion Size</th>
    </tr>
    <c:forEach items="${foodList}" var="food">
        <tr>
            <td>${product.id}</td>
            <td><c:out value="${food.name}" /></td>
            <td><c:out value="${food.calories}" /></td>
            <td><c:out value="${food.portionSize}" /></td>
        </tr>
    </c:forEach>
    </table>
</body>
</html>
