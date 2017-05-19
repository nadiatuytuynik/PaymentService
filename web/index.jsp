<%@ page import="java.io.PrintWriter" %>
<%@ page import="com.service.util.DBManager" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.Statement" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="java.util.Objects" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
  <head>
    		<meta charset="utf-8">
    		<meta http-equiv="X-UA-Compatible" content="IE=edge">
    		<meta name="viewport" content="width=device-width, initial-scale=1">
    		
    		<title>Payment service</title>
			
			<link rel="stylesheet" href="css/bootstrap.css">
			<link rel="stylesheet" href="js/bootstrap.js">
			<link rel="stylesheet" href="js/bootstrap.min.js">
			<link rel="stylesheet" href="css/bootstrap.min.css">
			<link rel="stylesheet" href="css/styles.css">

	  <style>
		  body {
			  background-image: url(fonts/1.jpg);
              -moz-background-size: 100%; /* Firefox 3.6+ */
              -webkit-background-size: 100%; /* Safari 3.1+ и Chrome 4.0+ */
              -o-background-size: 100%; /* Opera 9.6+ */
              background-size: 100%; /* Современные браузеры */
		  }
	  </style>

	<nav class="navbar navbar-inverse navbar-static-top" role="navigation">
      	<div class ="container-fluid">
			<div class = "navbar-header maintitle">
			   	<button type = "button" class = "navbar-toggle">
					<span class = "sc-only">Toggle navigation</span>
					<span class = "icon-bar"></span>
					<span class = "icon-bar"></span>
					<span class = "icon-bar"></span>
			   	</button>
			   <a class = "navbar-brand active" href = "index.jsp">Payment service</a>
			
			</div>
			
			<div class = "collapse navbar-collapse">
				<ul class = "nav navbar-nav">
					<li class="secondtitle">
                        <a  id = "pointer1" href="javascript:onoff('MyForm1');" style = "pointer-events: none;">Mobile refill</a>
					</li>
					<li class="secondtitle">
                        <a id = "pointer2" href="javascript:onoff('MyForm2');" style = "pointer-events: none;">Remittance</a>
					</li>
					<li class="secondtitle">
                        <a  id = "pointer3" href="javascript:onoff('Basket');" style = "pointer-events: none;">Basket</a>
					</li>
				<ul>
			
			</div>
	  </div>
   </nav>
   

<script type="text/javascript">
	function onoff(t) {
		p=document.getElementById(t);
		if(p.style.display=="none"){
			p.style.display="block";
		}else{
			p.style.display="none";
		}
};

    function setPointer(t) {
        p=document.getElementById(t);
        if(p.style.pointerEvents = "none"){
            p.style.pointerEvents="auto";
        }else{
            p.style.pointerEvents="none";
        }
    };

</script>


      <%Object authorize = request.getAttribute("authorize");%>
      <%if(authorize == null){%>

	  <form class = "formhead" name = "authorization" action = "/AuthorizationServlet" method="post">
        

        <div class = "row">
			<div class = "col-xs-6 col-md-8"></div>
			<div class = "col-xs-6 col-md-4 ">
				<div class="form-group" id = "id1">
					<div id = "log" class = "input-group">
						<span class="input-group-addon"><span class="glyphicon glyphicon-user"></span></span>
						<input type="email" class="form-control"  class ="form1"  placeholder="Login" name = "Log" pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,3}$" required="" value="" maxlength = "20" style = "display: block"/>
						<span class="glyphicon form-control-feedback"></span>
					</div>
				</div>
			
				<div class="form-group">
					<div id = "pass" class = "input-group">
						<span class="input-group-addon"><span class="glyphicon glyphicon-lock"></span></span>
						<input type="password" class="form-control"  class ="form1"  placeholder="Password" name = "Pass" pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,}" required="" value="" maxlength = "20" style = "display: block"/>
						<span class="glyphicon form-control-feedback"></span>
					</div>
				</div>
		
			</div>
		</div>
		
		
		<div class = "row">
			<div class = "col-xs-5 col-md-8"></div>
			<div id = "button1" class = "col-xs-5 col-md-2">
				<button type="submit" class="btn btn-labeled btn-success" id = "authorize" style = "display: block">
                 	<span class="btn-label"><i class="glyphicon glyphicon-ok"></i></span>Log in
		    	</button>
			</div>

            <div id = "button2" class = "col-xs-5col-md-2">
				<a href="javascript:onoff('MyForm4');">
				<button type="button" class="btn btn-labeled btn-primary" style = "display: block">
                <span class="btn-label"><i class="glyphicon glyphicon-registration-mark"></i></span>Registration
		    	</button>
		    	</a>
			</div>

        </div>

      </form>

            <%}else{%>

      <script>
            setPointer('pointer1');
            setPointer('pointer2');
            setPointer('pointer3');
      </script>

      <form name = "LogOutServlet" action = "/LogOutServlet" method="post">
      <div class = "row">
          <div class = "col-xs-5 col-md-8"></div>
          <div class = "col-xs-2 col-md-2"></div>
          <div class = "col-xs-2 col-md-2">
              <button type="submit" class="btn btn-labeled btn-primary">
                  <span class="btn-label"><i class="glyphicon glyphicon-log-out"></i></span>Log out
              </button>

          </div>
      </div>

          </form>

            <%}%>

</head>

<body class="big-page" ondragstart="return false;">

  <div class = "formbody">
	
  <div class ="b">
        <div id = "Profile" class = "col-xs-6 col-md-4" style = "display: none;">
  <div class ="panel panel-default">
  <div class="panel-heading">
    <h3 class="panel-title">Profile</h3>
  </div>
  <div class = "panel-body">
  <div class = "row">
				
			<div class = "panel-body">

				<table class = "table table-bordered">

					<thead>
						<tr>
							<th>Fist name</th>
							<th>Second name</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td><%=  request.getAttribute("name") %></td>
							<td><%= request.getAttribute("secondname") %></td>
						</tr>
					</tbody>
				</table>

				<div id = "NewCard" style = "display:none;">
					<table  class = "table table-bordered">

						<thead>
							<tr>
								<th>№</th>
								<th>Card</th>
								<th>Amount</th>
								<th>Currency</th>
							</tr>
						</thead>

                        <tbody>
							<div id = "Row1" style = "display:none;">
                                <tr>
								    <td>1</td>
								    <td><%= request.getAttribute("number(0)")%></td>
								    <td><%= request.getAttribute("account(0)")%></td>
								    <td><%= request.getAttribute("currency(0)")%></td>
							    </tr>
                            </div>

                            <div id = "Row2" style = "display:none;">
                                <tr>
                                    <td>2</td>
                                    <td><%= request.getAttribute("number(1)")%></td>
                                    <td><%= request.getAttribute("account(1)")%></td>
                                    <td><%= request.getAttribute("currency(1)")%></td>
                                </tr>
                            </div>

                            <div id = "Row3" style = "display:none;">
                                <tr>
                                    <td>3</td>
                                    <td><%= request.getAttribute("number(2)")%></td>
                                    <td><%= request.getAttribute("account(2)")%></td>
                                    <td><%= request.getAttribute("currency(2)")%></td>
                                </tr>
                            </div>

                            <div id = "Row4" style = "display:none;">
                                <tr>
                                    <td>4</td>
                                    <td><%= request.getAttribute("number(3)")%></td>
                                    <td><%= request.getAttribute("account(3)")%></td>
                                    <td><%= request.getAttribute("currency(3)")%></td>
                            </tr>
                            </div>
						</tbody>

					</table>
				</div>


				<a href="javascript:onoff('AddCard');">
					<button type="button" class="btn btn-labeled btn-primary">
						<span class="btn-label"><i class="glyphicon glyphicon-plus"></i></span>
                        Add card
					</button>
				</a>
			</div>
			
	
			</div>
		<div class = "col-xs-6 col-md-4"></div>
		</div>
	</div>
</div>
    <%
          Object value1 = request.getAttribute("value");
          if(value1 != null){ %>
      <script>
          alert("Successfully!");
          onoff('Profile');
      </script>
          <%}

         Object val1 = request.getAttribute("val");
        if(val1 !=null){%>
            <script>
                alert("Successfully!");
            </script>
        <%
        Object row1 = request.getAttribute("number(0)");
                                 if(row1 !=null){%>
      <script>onoff('Profile');onoff('NewCard');onoff('Row1');</script>
          <%}
          }%>

          <%       Object row2 = request.getAttribute("number(1)");
                            if(row2 !=null){%>
      <script>onoff('Row2');</script>
          <%}%>

          <%       Object row3 = request.getAttribute("number(2)");
                            if(row3 !=null){%>
      <script>onoff('Row3');</script>
          <%}%>

          <%       Object row4 = request.getAttribute("number(3)");
                            if(row4 !=null){%>
      <script>onoff('Row4');</script>
          <%}
          %>


      <div class = "col-xs-6 col-md-8"></div>
	  <div id = "AddCard" class = "col-xs-6 col-md-4" style = "display: none;">

		  <form  name = "addCardServlet" action = "/AddCardServlet" method="post">
			  <div class ="panel panel-default">
				  <div class="panel-heading">
					  <h3 class="panel-title">Adding a bank card to your account</h3>
				  </div>
				  <div class = "panel-body">
					  <form class = "row">
						  <p style = "font-weight: 600;">Step 1. Card details</p>
						  <div class ="panel-group">
							  <div class = "panel-body">

								  <div class = "input-group">
									  <span class="input-group-addon"><span class="glyphicon glyphicon-credit-card"></span></span>
									  <input id = "text_7" type="text" class="form-control text"  placeholder="Card name" name = "Card_name"  title = "" required="" value="" maxlength = "30"/>
									  <span class="glyphicon form-control-feedback"></span>
								  </div>


								  <div class = "input-group">
									  <span class="input-group-addon"><span class="glyphicon glyphicon-credit-card"></span></span>
									  <input id = "text_8" type="text" class="form-control text"  placeholder="Card number" name = "Card_number"  title = "" required="" value="" maxlength = "30"/>
									  <span class="glyphicon form-control-feedback"></span>
								  </div>
								  <br>
								  <div class = "input-group">
									  <span class="input-group-addon"><span class="glyphicon glyphicon-credit-card"></span></span>
									  <input id = "text_9" type="text" class="form-control text"  placeholder="Validity" name = "Validity"  title = "" required="" value="" maxlength = "30"/>
									  <span class="glyphicon form-control-feedback"></span>
								  </div>

								  <div class = "input-group">
									  <span class="input-group-addon"><span class="glyphicon glyphicon-credit-card"></span></span>
									  <input id = "text_10" type="text" class="form-control text"  placeholder="CVV2 code" name = "CVV2_code"  title = "" required="" value="" maxlength = "30"/>
									  <span class="glyphicon form-control-feedback"></span>
								  </div>


							  </div>

							  <div class = "panel-body">
								  	<button type="submit" class="btn btn-labeled btn-success">
									  	<span class="btn-label"><i class="glyphicon glyphicon-plus-sign"></i></span>Add
								  	</button>
							  </div>

						  </div>
					  </form>
					  <div class = "col-xs-6 col-md-4"></div>
				  </div>
			  </div>

	  </div>

	  <div class = "col-xs-6 col-md-8"></div>
	  <div id = "Step2" class = "col-xs-6 col-md-4" style = "display: none;">

		  <form  name = "CheckingPasswordServlet" action = "/CheckingPasswordServlet" method="post">
			  <div class ="panel panel-default">
				  <div class="panel-heading">
					  <h3 class="panel-title">Adding a bank card to your account</h3>
				  </div>
				  <div class = "panel-body">
					  <form class = "row">
						  <p style = "font-weight: 600;">Step 2. Check sms-password</p>
						  <div class ="panel-group">
							  <div class = "panel-body">
								  <div class = "input-group">
									  <span class="input-group-addon"><span class="glyphicon glyphicon-phone"></span></span>
									  <input id = "text_11" type="password" class="form-control text"  placeholder="sms-password" name = "sms_password"  title = "" required="" value="" maxlength = "30"/>
									  <span class="glyphicon form-control-feedback"></span>
								  </div>
							  </div>
									  <button type="submit" class="btn btn-labeled btn-success">
										  <span class="btn-label"><i class="glyphicon glyphicon-ok-circle"></i></span>Add
									  </button>
						  </div>
					  </form>
					  <div class = "col-xs-6 col-md-4"></div>
				  </div>
			  </div>

	  </div>


          <%Object value2 = request.getAttribute("checking");
            if(value2 != null){
		  %>
                <script>
                    onoff('Profile');
                    onoff('Step2');
                </script>
		  <%
        Object row5 = request.getAttribute("number(0)");
                                 if(row5 !=null){%>
	  <script>onoff('NewCard');onoff('Row1');</script>
		  <%}%>

		  <%       Object row6 = request.getAttribute("number(1)");
                            if(row6 !=null){%>
	  <script>onoff('Row2');</script>
		  <%}%>

		  <%       Object row7 = request.getAttribute("number(2)");
                            if(row7 !=null){%>
	  <script>onoff('Row3');</script>
		  <%}%>

		  <%       Object row8 = request.getAttribute("number(3)");
                            if(row8 !=null){%>
	  <script>onoff('Row4');</script>
		  <%}
		  }
          %>



	  <%Object value3 = request.getAttribute("smsresult");
	    if(value3 != null){ %>
            <script>
                onoff('Profile');
                alert('New card successfully add to your profile.');
            </script>
		  <%
        Object row5 = request.getAttribute("number(0)");
                                 if(row5 !=null){%>
	  <script>onoff('NewCard');onoff('Row1');</script>
		  <%}%>

		  <%       Object row6 = request.getAttribute("number(1)");
                            if(row6 !=null){%>
	  <script>onoff('Row2');</script>
		  <%}%>

		  <%       Object row7 = request.getAttribute("number(2)");
                            if(row7 !=null){%>
	  <script>onoff('Row3');</script>
		  <%}%>

		  <%       Object row8 = request.getAttribute("number(3)");
                            if(row8 !=null){%>
	  <script>onoff('Row4');</script>
		  <%}%>
	   <%}%>

<div class = "col-xs-6 col-md-8"></div>
        <div id = "MyForm1" class = "col-xs-6 col-md-4" style = "display: none;">

			<form  name = "MobileRefillServlet" action = "/MobileRefillServlet" method="post">
			<div class ="panel panel-default">
  <div class="panel-heading">
    <h3 class="panel-title">Mobile refill</h3>
  </div>
  <div class = "panel-body">
  <form class = "row">
		
		   <div class ="panel-group">
				<div class = "panel-body">
					<div class="input-group">
						<span class="input-group-addon"><span class="glyphicon glyphicon-credit-card"></span></span>
						<select class="form-control" name = "Card_sender">
							<option value="0"><%= request.getAttribute("number(0)")%></option>
							<option value="1"><%= request.getAttribute("number(1)")%></option>
                            <option value="2"><%= request.getAttribute("number(2)")%></option>
                            <option value="3"><%= request.getAttribute("number(3)")%></option>
						</select>
					</div>
				

					<div class = "input-group">
							<span class="input-group-addon"><span class="glyphicon glyphicon-phone"></span></span>
							<input id = "text_1" type="number" class="form-control text"  placeholder="Phone number" name = "Phone_number"  title = "" required="" value="" maxlength = "30"/>
							<span class="glyphicon form-control-feedback"></span>
					</div>
					


					<div class = "input-group">
							<span class="input-group-addon"><span class="glyphicon glyphicon-usd"></span></span>
							<input id = "text_2" type="text" class="form-control text"  placeholder="account" name = "account"  title = "" required="" value="" maxlength = "30"/>
							<span class="glyphicon form-control-feedback"></span>
							<span class="input-group-addon"><span class="glyphicon glyphicon-hand-right"></span></span>
							<select class="form-control" name = "currency">
							<option value="dollar">dollar</option>
							<option value="euro">euro</option>
						</select>
					</div>
					</div>

					<div class = "panel-body">
					<a href="#">
						<button type="submit" class="btn btn-labeled btn-success">
							<span class="btn-label"><i class="glyphicon glyphicon-share"></i></span>SEND
						</button>
					</a>
					</div>
		
	
			</div>
	  </form>
		<div class = "col-xs-6 col-md-4"></div>
		</div>
	</div>
    
</div>


<div class = "col-xs-6 col-md-8"></div>
        <div id = "MyForm2" class = "col-xs-6 col-md-4" style = "display: none;">

            <form  name = "RemittanceServlet" action = "/RemittanceServlet" method="post">
			<div class ="panel panel-default">
  <div class="panel-heading">
    <h3 class="panel-title">Remittance</h3>
  </div>
  <div class = "panel-body">
  <form class = "row">
		
		   <div class ="panel-group">
				<div class = "panel-body">
					<div class="input-group">
						<span class="input-group-addon"><span class="glyphicon glyphicon-credit-card"></span></span>
						<select class="form-control" name = "cards">
							<option value="0"><%= request.getAttribute("number(0)")%></option>
							<option value="1"><%= request.getAttribute("number(1)")%></option>
                            <option value="2"><%= request.getAttribute("number(2)")%></option>
                            <option value="3"><%= request.getAttribute("number(3)")%></option>
						</select>
					</div>
				
			
				
					<div class = "input-group">
							<span class="input-group-addon"><span class="glyphicon glyphicon-credit-card"></span></span>
							<input id = "text_3" type="text" class="form-control text"  placeholder="Card 2" name = "Card_2"  title = "" required="" value="" maxlength = "30"/>
							<span class="glyphicon form-control-feedback"></span>
					</div>
					


					<div class = "input-group">
							<span class="input-group-addon"><span class="glyphicon glyphicon-usd"></span></span>
							<input id = "text_4" type="number" class="form-control text"  placeholder="amount" name = "amount"  title = "" required="" value="" maxlength = "30"/>
							<span class="glyphicon form-control-feedback"></span>
							<span class="input-group-addon"><span class="glyphicon glyphicon-hand-right"></span></span>
							<select class="form-control" name = "currency">
							<option value="dollar">dollar</option>
							<option value="euro">euro</option>
						</select>
					</div>
					</div>

					<div class = "panel-body">
					<a href="#">
						<button type="submit" class="btn btn-labeled btn-success">
							<span class="btn-label"><i class="glyphicon glyphicon-share"></i></span>SEND
						</button>
					</a>
					</div>
		
	
			</div>
	  </form>
		<div class = "col-xs-6 col-md-4"></div>
		</div>
	</div>
    
</div>



<div class = "col-xs-6 col-md-8"></div>
        <div id = "Basket" class = "col-xs-6 col-md-8" style = "display: none;">

            <form  name = "BasketServlet" action = "/BasketServlet" method="post">
			<div class ="panel panel-default">
  <div class="panel-heading">
    <h3 class="panel-title">Basket</h3>
  </div>
  <div class = "panel-body">
  <form class = "row">

		   <div class ="panel-group">
				<div class = "panel-body">


     <table class="table table-bordered" >
                    <thead>
                     <tr>
                     <th>№</th>
                     <th>Card sender</th>
                     <th>Card recipient</th>
                     <th>Amount</th>
                    <th>Currency</th>
                     <th>Remove</th>
                     </tr>
                    </thead>
						<tbody>
							<tr>
								<td>1</td>
								<td><%= request.getAttribute("cardsender")%></td>
								<td><%= request.getAttribute("cardrecipient")%></td>
								<td><%= request.getAttribute("amount")%></td>
								<td><%= request.getAttribute("currency")%></td>
								<td>
									<label><input type="checkbox" value=""></label>
								</td>
							</tr>
						</tbody>

					</table>

                </div>

					<div class = "panel-body">
					<a href = "#">
						<button type="submit" class="btn btn-labeled btn-success">
							<span class="btn-label"><i class="glyphicon glyphicon-ok"></i></span>CONFIRM
						</button>
					</a>
					</div>
		
	
			</div>
	  </form>
		<div class = "col-xs-6 col-md-4"></div>
		</div>
	</div>
    
</div>

	  <%Object basket = request.getAttribute("Basket");
	  if(basket !=null){
	  %>
	  	<script>
			onoff('Basket');
		</script>

	  <%}
	  Object update = request.getAttribute("update");
	  if(update !=null){%>
	  <script>
            alert('Operation successfully confirmed.');
			onoff('Profile');
		</script>
          <%
        Object row5 = request.getAttribute("number(0)");
                                 if(row5 !=null){%>
      <script>onoff('NewCard');onoff('Row1');</script>
          <%}%>

          <%       Object row6 = request.getAttribute("number(1)");
                            if(row6 !=null){%>
      <script>onoff('Row2');</script>
          <%}%>

          <%       Object row7 = request.getAttribute("number(2)");
                            if(row7 !=null){%>
      <script>onoff('Row3');</script>
          <%}%>

          <%       Object row8 = request.getAttribute("number(3)");
                            if(row8 !=null){%>
      <script>onoff('Row4');</script>
          <%}%>
      <%}%>


	  <%Object mobilerefill = request.getAttribute("mobilerefill");
	  if(mobilerefill != null){%>
	  	<script>
			onoff('Basket');
		</script>
	 <%}%>


 <div class = "col-xs-6 col-md-8"></div>
        <div id = "MyForm4" class = "col-xs-6 col-md-4" style = "display: none;">
			<form name = "registration" action = "/RegistrationServlet" method="post">
  				<div class ="panel panel-default">
  
  					<div class="panel-heading">
    					<h3 class="panel-title">Registration</h3>
  					</div>

  					<div class = "panel-body">
  
  						<div class = "row">
		
		   					<div class ="panel-group">
								<div class = "panel-body">
									<div class="form-group">
										<div class = "input-group">
											<span class="input-group-addon"><span class="glyphicon glyphicon-user"></span></span>
											<input type="text" class="form-control text"  placeholder="Fist name" name = "Name" pattern="^[A-Z]{1}[a-z]{1,10}(-[A-Z]{1}[a-z]{1,10})?" title = "Формат Ooo або Ooo-Ooo. Варіант англійською." required="" value="" maxlength = "30"/>
											<span class="glyphicon form-control-feedback"></span>
										</div>
									</div>
					

									<div class="form-group">
										<div class="input-group">
											<span class="input-group-addon"><span class="glyphicon glyphicon-user"></span></span>
											<input type="text" class="form-control" placeholder="Second name" name = "Secondname" pattern="^[A-Z]{1}[a-z]{1,10}(-[A-Z]{1}[a-z]{1,10})?" title = "Формат Ooo або Ooo-Ooo. Варіант англійською." required="" value="" maxlength = "30"/>
											<span class="glyphicon form-control-feedback"></span>
										</div>
									</div>
								</div>
			
								
								<div class = "panel-body">
									<div class="form-group">
										<div class="input-group">
											<span class="input-group-addon"><span class="glyphicon glyphicon-user"></span></span>
											<input type="email" class="form-control" placeholder="Login" name = "Login" pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,3}$" required="" value="" maxlength = "30"/>
											<span class="glyphicon form-control-feedback"></span>
										</div>
									</div>
					
									<div class="form-group">
										<div class="input-group">
											<span class="input-group-addon"><span class="glyphicon glyphicon-lock"></span></span>
											<input type="password" class="form-control" placeholder="Password" name="Password" pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,}" required="" value="" maxlength = "30" title = "Пароль має мітити букви у верхньому та нижньому регістрі, цифри, а також довжину не менше 8-ми символів."/>
											<span class="glyphicon form-control-feedback"></span>
										</div>
									</div>
			
									<div class="form-group b2">
				    					<a href="javascript:onoff('MyForm4');">
											<button type="button" class="btn btn-labeled btn-success">
												<span class="btn-label"><i class="glyphicon glyphicon-remove"></i></span>Close
											</button>
										</a>
										<a href="#">
											<button type="submit" class="btn btn-labeled btn-primary" id = "save">
												<span class="btn-label"><i class="glyphicon glyphicon-registration-mark"></i></span>Create the profile
											</button>
										</a>
									</div>
								</div>
							</div>
			</form>
			<div class = "col-xs-6 col-md-4"></div>
		</div>


    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
 
  </body>
</html>