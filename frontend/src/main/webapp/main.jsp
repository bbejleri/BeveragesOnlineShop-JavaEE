<%@ page import="java.util.*" %>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Beverage Store Demo</title>

<!-- Bootstrap -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap.min.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap-theme.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/js/bootstrap.min.js"></script>
<style type="text/css">
.container {
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
<body style="width:100%;height:100%;background-color:#8e1b29;color:white; font-family:Monospace">
	<div class="container">
		<h1>Welcome to our Christmas Beverage Store</h1>
		<hr>
		<h4>Here you can place your order:</h4>
		<form action="${pageContext.request.contextPath}/BeveragesServlet" id="placeorder" method="post">
		<table border=0 style="width:100%">
		<tr>
		<th>Beverage</th>
		<th>Quantity</th>
		<th>Manufacturer</th>
		<th>Iniciative</th>
		<th>Price</th>
		</tr>
		<tr>
		<td><label class="thislabel">Festbier:</label> <input type="checkbox" id="festbier" name="festbier" value="festbier"></td>
		<td style="color:black"><input type="number" id="quantity1" name="quantity1"
        min="0" max="100" placeholder="bttl."></td>
        <td>Bamberg Bier</td>
        <td>Trail Package</td>
        <td>3.5 Euro/bttl.</td>
		</tr>
		<tr>
		<td><label class="thislabel">Rot Glühwein mit Alk:</label> <input type="checkbox" id="rgluhweinalk" name="rgluhweinalk" value="rgluhweinalk"></td>
		<td style="color:black"><input type="number" id="quantity2" name="quantity2"
        min="0" max="100" placeholder="bttl."></td>
        <td>Bamberg Wein</td>
        <td>Promotional Gift</td>
        <td>4.5 Euro/bttl.</td>
		</tr>
		<tr>
		<td><label class="thislabel">Rot Glühwein ohne Alk:</label> <input type="checkbox" id="rgluhwein" name="rgluhwein" value="rgluhwein"></td>
		<td style="color:black"><input type="number" id="quantity3" name="quantity3"
        min="0" max="100" placeholder="bttl."></td>
        <td>Bamberg Wein</td>
        <td>Trail Package</td>
        <td>4 Euro/bttl.</td>
		</tr>
		</table>
		<input type='button' onclick="totalPrice()" id="totalbttn" value='Total Price' style="background-color:#f4bf42;border-radius:8px;width:150px"/><label id="totalprice"></label>
		<input type="submit" value="Place Order" style="background-color:green;border-radius:8px;width:150px" />

		</form>
		<hr>
				
	</div>
	
<script type="text/javascript">
    
    function totalPrice(){

    var product1 = 0.0
    var product2 = 0.0
    var product3 = 0.0
    var totalprice = 0.0
    
    if(document.getElementById("festbier").checked){
    	var q1 = document.getElementById("quantity1").value
    	product1 = (q1*3.5)
    	
    }
    
    
    if(document.getElementById("rgluhweinalk").checked){
    	var q2 = document.getElementById("quantity2").value
    	product2 = (q2*4.5)
    	
    }
    
    
    if(document.getElementById("rgluhwein").checked){
    	var q3 = document.getElementById("quantity3").value
    	product5 = (q3*4)
    	
    }
    
    
    totalprice = (product1 + product2 + product3)
    document.getElementById("totalprice").innerHTML = " " + totalprice + " Euro";
    
   
    }    

    </script>
</body>
</html>
