<%@ page import="java.io.PrintWriter" %>
<%@ page import="com.service.util.DBManager" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.Statement" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="java.util.Objects" %>
<%@ page import="java.util.ArrayList" %>
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
					<li class="secondtitle">
						<a  id = "pointer4" href="javascript:onoff('History');" style = "pointer-events: none;">History</a>
					</li>
				</ul>
			
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




      <%Object authorize = request.getAttribute("authorize");
		  Object block = request.getAttribute("userblock");
	  if(block !=null){
      System.out.println("Blocked!");
      %>
		  <script>
			  alert("Sorry, but your account was blocked!");
		  </script>
	 <% }

	  %>

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
			setPointer('pointer4');
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
          onoff('Profile');
      </script>

          <% Object admin = request.getAttribute("admin");

      if(admin != null){%>
      <script>
          alert("Hello, Admin!");
      </script>
          <%}%>
          <%}

         Object val1 = request.getAttribute("val");
        if(val1 !=null){%>
            <script>
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
                    alert("Step 2");
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
							<input id = "text_1" type="text" class="form-control text"  placeholder="Phone number" name = "Phone_number" pattern="\([0-9]{3}\)\s[0-9]{3}-[0-9]{2}-[0-9]{2}" required="" value="" maxlength = "30" title = "Please write (XXX) XXX-XX-XX"/>
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
                     </tr>
                    </thead>
						<tbody>
                        <%if(request.getAttribute("cardsender") != null){
                        %>
							<tr>
								<td>1</td>
								<td><%= request.getAttribute("cardsender")%></td>
								<td><%= request.getAttribute("cardrecipient")%></td>
								<td><%= request.getAttribute("amount")%></td>
								<td><%= request.getAttribute("currency")%></td>
							</tr>
                        <%}%>
						</tbody>

					</table>
                    <p style = "text-align: center;"> No data found.</p>
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

      <div class = "col-xs-6 col-md-8"></div>
      <div id = "History" class = "col-xs-6 col-md-8" style = "display: none;">

          <form  name = "ClearHistoryServlet" action = "/ClearHistoryServlet" method="post">
              <div class ="panel panel-default">
                  <div class="panel-heading">
                      <h3 class="panel-title">History</h3>
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
										  <th>Data</th>
                                      </tr>
                                      </thead>
                                      <tbody>
                                      <%
                                          ArrayList<Object> CardsSenderBasketList = new ArrayList<>();ArrayList<Object> RecipientsBasketList = new ArrayList<>();
                                          ArrayList<Object> AmountsBasketList = new ArrayList<>();ArrayList<Object> CurrenciesBasketList = new ArrayList<>();
										  ArrayList<Object> DataBasketList = new ArrayList<>();

                                          Object bsender0 = request.getAttribute("bsender(0)");Object bsender1 = request.getAttribute("bsender(1)");Object bsender2 = request.getAttribute("bsender(2)");Object bsender3 = request.getAttribute("bsender(3)");
                                          Object bsender4 = request.getAttribute("bsender(4)");Object bsender5 = request.getAttribute("bsender(5)");Object bsender6 = request.getAttribute("bsender(6)");Object bsender7 = request.getAttribute("bsender(7)");
                                          Object bsender8 = request.getAttribute("bsender(8)");Object bsender9 = request.getAttribute("bsender(9)");

                                          CardsSenderBasketList.add(bsender0);CardsSenderBasketList.add(bsender1);CardsSenderBasketList.add(bsender2);CardsSenderBasketList.add(bsender3);
                                          CardsSenderBasketList.add(bsender4);CardsSenderBasketList.add(bsender5);CardsSenderBasketList.add(bsender6);CardsSenderBasketList.add(bsender7);
                                          CardsSenderBasketList.add(bsender8);CardsSenderBasketList.add(bsender9);


                                          Object brecipient0 = request.getAttribute("brecipient(0)");Object brecipient1 = request.getAttribute("brecipient(1)");Object brecipient2 = request.getAttribute("brecipient(2)");Object brecipient3 = request.getAttribute("brecipient(3)");
                                          Object brecipient4 = request.getAttribute("brecipient(4)");Object brecipient5 = request.getAttribute("brecipient(5)");Object brecipient6 = request.getAttribute("brecipient(6)");Object brecipient7 = request.getAttribute("brecipient(7)");
                                          Object brecipient8 = request.getAttribute("brecipient(8)");Object brecipient9 = request.getAttribute("brecipient(9)");

                                          RecipientsBasketList.add(brecipient0);RecipientsBasketList.add(brecipient1);RecipientsBasketList.add(brecipient2);RecipientsBasketList.add(brecipient3);
                                          RecipientsBasketList.add(brecipient4);RecipientsBasketList.add(brecipient5);RecipientsBasketList.add(brecipient6);RecipientsBasketList.add(brecipient7);
                                          RecipientsBasketList.add(brecipient8);RecipientsBasketList.add(brecipient9);


                                          Object bamount0 = request.getAttribute("bamount(0)");Object bamount1 = request.getAttribute("bamount(1)");Object bamount2 = request.getAttribute("bamount(2)");Object bamount3 = request.getAttribute("bamount(3)");
                                          Object bamount4 = request.getAttribute("bamount(4)");Object bamount5 = request.getAttribute("bamount(5)");Object bamount6 = request.getAttribute("bamount(6)");Object bamount7 = request.getAttribute("bamount(7)");
                                          Object bamount8 = request.getAttribute("bamount(8)");Object bamount9 = request.getAttribute("bamount(9)");

                                          AmountsBasketList.add(bamount0);AmountsBasketList.add(bamount1);AmountsBasketList.add(bamount2);AmountsBasketList.add(bamount3);
                                          AmountsBasketList.add(bamount4);AmountsBasketList.add(bamount5);AmountsBasketList.add(bamount6);AmountsBasketList.add(bamount7);
                                          AmountsBasketList.add(bamount8);AmountsBasketList.add(bamount9);

                                          Object bcurrency0 = request.getAttribute("bcurrency(0)");Object bcurrency1 = request.getAttribute("bcurrency(1)");Object bcurrency2 = request.getAttribute("bcurrency(2)");Object bcurrency3 = request.getAttribute("bcurrency(3)");
                                          Object bcurrency4 = request.getAttribute("bcurrency(4)");Object bcurrency5 = request.getAttribute("bcurrency(5)");Object bcurrency6 = request.getAttribute("bcurrency(6)");Object bcurrency7 = request.getAttribute("bcurrency(7)");
                                          Object bcurrency8 = request.getAttribute("bcurrency(8)");Object bcurrency9 = request.getAttribute("bcurrency(9)");

                                          CurrenciesBasketList.add(bcurrency0);CurrenciesBasketList.add(bcurrency1);CurrenciesBasketList.add(bcurrency2);CurrenciesBasketList.add(bcurrency3);
                                          CurrenciesBasketList.add(bcurrency4);CurrenciesBasketList.add(bcurrency5);CurrenciesBasketList.add(bcurrency6);CurrenciesBasketList.add(bcurrency7);
                                          CurrenciesBasketList.add(bcurrency8);CurrenciesBasketList.add(bcurrency9);

										  Object data0 = request.getAttribute("data(0)");Object data1 = request.getAttribute("data(1)");Object data2 = request.getAttribute("data(2)");Object data3 = request.getAttribute("data(3)");
										  Object data4 = request.getAttribute("data(4)");Object data5 = request.getAttribute("data(5)");Object data6 = request.getAttribute("data(6)");Object data7 = request.getAttribute("data(7)");
										  Object data8 = request.getAttribute("data(8)");Object data9 = request.getAttribute("data(9)");


										  DataBasketList.add(data0);DataBasketList.add(data1);DataBasketList.add(data2);DataBasketList.add(data3);
										  DataBasketList.add(data4);DataBasketList.add(data5);DataBasketList.add(data6);DataBasketList.add(data7);
										  DataBasketList.add(data8);DataBasketList.add(data9);

                                          int k = 0;
                                          for(int i = 0; i<CurrenciesBasketList.size(); i++){
                                              if(CurrenciesBasketList.get(i) != null){
                                                  k++;
                                              }
                                              if(CurrenciesBasketList.get(i) == null){
                                                  i++;%>
                                      <%}else{
                                      %>
                                      <tr>
                                          <td><%=k%></td>
                                          <td><%= CardsSenderBasketList.get(i)%></td>
                                          <td><%= RecipientsBasketList.get(i)%></td>
                                          <td><%= AmountsBasketList.get(i)%></td>
                                          <td><%= CurrenciesBasketList.get(i)%></td>
										  <td><%= DataBasketList.get(i)%></td>
                                      </tr>


                                      <%}}%>

                                      <%
                                          if(request.getAttribute("cardsender") != null){
                                      %>
                                      <tr>
                                          <td>1</td>
                                          <td><%= request.getAttribute("cardsender")%></td>
                                          <td><%= request.getAttribute("cardrecipient")%></td>
                                          <td><%= request.getAttribute("amount")%></td>
                                          <td><%= request.getAttribute("currency")%></td>
                                          <td>
                                              <label><input type="checkbox" value="0"></label>
                                          </td>
                                          <td>
                                              <label><input type="checkbox" value="1"></label>
                                          </td>
                                      </tr>
                                      <%}%>
                                      </tbody>

                                  </table>
                                  <p style = "text-align: center;"> No data found.</p>
                              </div>

                              <div class = "panel-body">
                                  <a href = "#">
                                      <button type="submit" class="btn btn-labeled btn-primary">
                                          <span class="btn-label"><i class="glyphicon glyphicon-remove"></i></span>Clear all
                                      </button>
                                  </a>
                              </div>


                          </div>
                      </form>
                      <div class = "col-xs-6 col-md-4"></div>
                  </div>
              </div>

      </div>

	  <%Object not_enough_money2 = request.getAttribute("not_enough_money2");

	  	if(not_enough_money2 !=null){%>
	  	<script>
			alert("Not enough money on the account!");
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

	  	<%}else{

	  Object basket = request.getAttribute("Basket");
	  if(basket !=null){
	  %>
	  	<script>
			onoff('Basket');
		</script>

	  <%}
	  }
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


		  <%Object not_enough_money = request.getAttribute("not_enough_money");
		  if(not_enough_money !=null){%>

		  <script>
			  alert("Not enough money on the account!");
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

		  <%}else{
		  Object mobilerefill = request.getAttribute("mobilerefill");
	  if(mobilerefill != null){
	  %>
	  	<script>
			onoff('Basket');
		</script>
	 <%}
	 }%>


	  <%Object cleared = request.getAttribute("cleared");
	  	if(cleared !=null){%>
		  <script>
              onoff('History');
			  alert("History was cleared!");
		  </script>
	  	<%}
	  %>


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

                                    <div class="form-group">
                                        <div class="input-group">
                                            <span class="input-group-addon"><span class="glyphicon glyphicon-lock"></span></span>
                                            <input type="text" class="form-control" placeholder="Phone number" name="Phone_number" pattern="\([0-9]{3}\)\s[0-9]{3}-[0-9]{2}-[0-9]{2}" required="" value="" maxlength = "30" title = "Please write (XXX) XXX-XX-XX"/>
                                            <span class="glyphicon form-control-feedback"></span>
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <span class="input-group-addon"><span class="glyphicon glyphicon-hand-right"></span>
                                            <select class="form-control" name = "Status">
                                                <option value="user">user</option>
                                                <option value="admin">admin</option>
                                            </select>
                                        </span>
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