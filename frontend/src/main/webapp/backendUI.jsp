<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="de.uniba.dsg.dsam.model.Beverage" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Backend User Interface</title>
<!-- Bootstrap -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap.min.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap-theme.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/js/bootstrap.min.js"></script>
<style type="text/css">
.backend {
  display: block;
  position: relative;
  padding-left: 35px;
  margin-bottom: 12px;
  cursor: pointer;
  font-size: 22px;
  -webkit-user-select: none;
  -moz-user-select: none;
  -ms-user-select: none;
  user-select: none;
}
</style>
</head>
<body style="width:100%;height:100%;background-color:#f49542;color:black; font-family:Monospace">
<div class="backend">
<h1>Backend Interface</h1>
<hr>
<form action="${pageContext.request.contextPath}/SalesmanServlet" method="POST">
<table border=0 style="width:100%;">
<tr>
<td><h3><b>View all Beverages</b></h3><input type="submit" name="submitbutton" value="View Beverages" style="background-color:green;border-radius:8px;width:200px"></td>
<%
List<Beverage>all_beverages = (List<Beverage>)request.getAttribute("beverages");
for(int i = 0; i < all_beverages.size(); i++) 
out.println("<b> " + all_beverages.get(i) + "</b>");
%>
</tr>
<tr>
<td><h3><b>Create Beverage</b></h3>
<input type="text" name="bevname">
<input type="text" name="incname">
<input type="text" name="manu">
<input type="text" name="price">
<input type="text" name="quantity">
<input type="submit" name="submitbutton" value="Create Beverages" style="background-color:green;border-radius:8px;width:200px">
</td>
</tr>
<tr>
<td><h3><b>Assign Incentive Beverage</b></h3>
Actual name:<input type="text" name="actualincname">
New name:<input type="text" name="newincname">
<input type="submit" name="submitbutton" value="Assign Incentive" style="background-color:green;border-radius:8px;width:200px">
</td>
</tr>
<tr>
<td><h3><b>Add New Promotional Gift</b></h3>Name:<input type="text" name="newincentivenamepromo">
<input type="submit" name="submitbutton" value="Add Promo Gift" style="background-color:green;border-radius:8px;width:200px"></td>
</tr>
<tr>
<td><h3><b>Add New Trial Package</b></h3>Name:<input type="text" name="newincentivenametrial">
<input type="submit" name="submitbutton" value="Add Trial Package" style="background-color:green;border-radius:8px;width:200px"></td>
</tr>
<tr>
<td><h3><b>Edit Incentive</b></h3>Actual name:<input type="text" name="oldincname">
New name:<input type="text" name="neweditname">
<input type="submit" name="submitbutton" value="Edit Incentive" style="background-color:green;border-radius:8px;width:200px">
</td>
</tr>
<tr><td><h3><b>Delete Incentive</b></h3>
Name of incentive to delete:<input type="text" name="inctodelete">
<input type="submit" name="submitbutton" value="Delete Incentive" style="background-color:green;border-radius:8px;width:200px"></td></tr>
<tr><td><h3><b>View report of the overall summarized revenue of all orders</b></h3>
<input type="submit" name="submitbutton" value="View Report 1" style="background-color:green;border-radius:8px;width:200px">
<%
Double revenue1 = (Double)request.getAttribute("simpleRevenue");
out.println("<b> " + revenue1 + "</b>");
%>
</td></tr>
<tr><td><h3><b>Revenue of all sold beverages broken down to different incentive types</b></h3>
<input type="submit" name="submitbutton" value="View Report 2" style="background-color:green;border-radius:8px;width:200px">
<%
String revenue2 = (String)request.getAttribute("detailedRevenue");
out.println("<b> " + revenue2 + "</b>");
%>
</td></tr>
</table>
</form>
</div>
</body>
</html>