<%@ page import="java.io.PrintWriter" %>
<%@ page import="java.sql.Types" %>
<%@ page import="java.sql.CallableStatement" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="com.service.util.DBManager" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="java.util.ArrayList" %>
<%--
  Created by IntelliJ IDEA.
  User: Nadia
  Date: 21.05.2017
  Time: 18:56
  To change this template use File | Settings | File Templates.
--%>
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
</head>
<body>
       <script>
           alert("Hello, Admin!");
       </script>
       <script type="text/javascript">
           function onoff(t) {
               p=document.getElementById(t);
               if(p.style.display=="none"){
                   p.style.display="block";
               }else{
                   p.style.display="none";
               }
           };
       </script>

       <%PrintWriter out1 = response.getWriter();
       out1.print(" <nav class=\"navbar navbar-inverse navbar-static-top\" role=\"navigation\">\n" +
               "           <div class =\"container-fluid\">\n" +
               "               <div class = \"navbar-header maintitle\">\n" +
               "                   <button type = \"button\" class = \"navbar-toggle\">\n" +
               "                       <span class = \"sc-only\">Toggle navigation</span>\n" +
               "                       <span class = \"icon-bar\"></span>\n" +
               "                       <span class = \"icon-bar\"></span>\n" +
               "                       <span class = \"icon-bar\"></span>\n" +
               "                   </button>\n" +
               "                   <a class = \"navbar-brand active\" href = \"index.jsp\">Payment service</a>\n" +
               "\n" +
               "               </div>\n" +
               "\n" +
               "               <div class = \"collapse navbar-collapse\">\n" +
               "                   <ul class = \"nav navbar-nav\">\n" +
              " <li class=\"secondtitle\">" +
          " <a  id = \"pointer1\" href=\"javascript:onoff(\'BlockCustomer\');\">Block</a>"+
          " </li>" +
               " <li class=\"secondtitle\">" +
               " <a  id = \"pointer1\" href=\"javascript:onoff(\'UnBlockCustomer\');\">Unblock</a>"+
               " </li>" +
               " <li class=\"secondtitle\">" +
               " <a  id = \"pointer1\" href=\"javascript:onoff(\'GenerateKeys\');\">Generate keys</a>"+
               " </li>" +
               "                       </ul>\n" +
               "\n" +
               "               </div>\n" +
               "           </div>\n" +
               "       </nav>" +
               "<div>"+
               "<form name = \"LogOutServlet\" action = \"/LogOutServlet\" method=\"post\">\n" +
               "      <div class = \"row\">\n" +
               "          <div class = \"col-xs-5 col-md-8\"></div>\n" +
               "          <div class = \"col-xs-2 col-md-2\"></div>\n" +
               "          <div class = \"col-xs-2 col-md-2\">\n" +
               "              <button type=\"submit\" class=\"btn btn-labeled btn-primary\">\n" +
               "                  <span class=\"btn-label\"><i class=\"glyphicon glyphicon-log-out\"></i></span>Log out\n" +
               "              </button>\n" +
               "\n" +
               "          </div>\n" +
               "      </div>\n" +
               "\n" +
               "          </form>"+
               "</div>"+
               "<div class = \"row\">" +
                       "<div  class = \"col-xs-6 col-md-8\">" + "<div class =\"panel panel-default\">" +
          " <div class=\"panel-heading\">"+
          " <h3 class=\"panel-title\">Users</h3>"+
         " </div>"+
          " <div class = \"panel-body\">"+
               "<table class=\"table table-bordered\" style=\"margin-top: 5%;\">\n" +
       "<tr>" +
               "<th>Id</th>" +
         "<th>Name</th>" +
         "<th>Second name</th>" +
         "<th>Login</th>" +
         "<th>Password</th>" +
               "<th>Status</th>" +
         "</tr>");%>

       <%DBManager dbManager = DBManager.getInstance();
           try {
               Connection connection = dbManager.getConnection();


               CallableStatement amountOfCustomers = connection.prepareCall("{call AmountOfCustomers(?,?)");

               amountOfCustomers.registerOutParameter(1, Types.INTEGER);
               amountOfCustomers.registerOutParameter(2, Types.INTEGER);
               amountOfCustomers.executeQuery();

               int maxcustid = (int) amountOfCustomers.getObject(1);
               int mincustid = (int) amountOfCustomers.getObject(2);
               amountOfCustomers.close();

               if(maxcustid !=0) {
                   for (int i = mincustid; i <= maxcustid; i++) {

                       CallableStatement getAllCustomers = connection.prepareCall("{call GetAllCustomers(?,?,?,?,?,?)");
                       getAllCustomers.setInt(1, i);
                       getAllCustomers.registerOutParameter(2, Types.VARCHAR);
                       getAllCustomers.registerOutParameter(3, Types.VARCHAR);
                       getAllCustomers.registerOutParameter(4, Types.VARCHAR);
                       getAllCustomers.registerOutParameter(5, Types.VARCHAR);
                       getAllCustomers.registerOutParameter(6, Types.VARCHAR);
                       getAllCustomers.executeQuery();

                       String custname = (String) getAllCustomers.getObject(2);
                       String custsecondname = (String) getAllCustomers.getObject(3);
                       String custlogin = (String) getAllCustomers.getObject(4);
                       String custpass = (String) getAllCustomers.getObject(5);
                       String custstatus = (String) getAllCustomers.getObject(6);
                       getAllCustomers.close();


                       if((custname.equals("none") && (i >= maxcustid))){
                           break;
                       }

                       if(custname.equals("none")){
                           i++;
                       }else{

                       out1.print(
                               "<tr>"+
                                       "<td>"+ i +"</td>"+
                                       "<td>"+ custname +"</td>"+
                                       "<td>"+ custsecondname +"</td>"+
                                       "<td>"+ custlogin +"</td>"+
                                       "<td>"+ custpass + "</td>" +
                                       "<td>"+ custstatus + "</td>" +
                                       "</tr>");

                       }
                   }

               }
               out1.print("</table></div></div>");
           } catch (SQLException e) {
               e.printStackTrace();
           }%>

       <div id = "BlockCustomer" class = "col-xs-6 col-md-4" style = "display: none;">

           <form  name = "BlockServlet" action = "/BlockServlet" method="post">
               <div class ="panel panel-default">
                   <div class="panel-heading">
                       <h3 class="panel-title">Block customer</h3>
                   </div>
                   <div class = "panel-body">
                       <form class = "row">
                           <div class ="panel-group">
                               <div class = "panel-body">
                                   <div class = "input-group">
                                       <span class="input-group-addon"><span class="glyphicon glyphicon-credit-card"></span></span>
                                       <input id = "text_10" type="text" class="form-control text"  placeholder="Customer login" name = "Customer_login"  title = "" required="" value="" maxlength = "30"/>
                                       <span class="glyphicon form-control-feedback"></span>
                                   </div>
                               </div>

                               <div class = "panel-body">
                                   <div class = "row">
                                        <button type="submit" class="btn btn-labeled btn-success">
                                            <span class="btn-label"><i class="glyphicon glyphicon-plus-sign"></i></span>Block
                                        </button>
                                   </div>
                               </div>

                           </div>
                       </form>
                   </div>
               </div>

       </div>


       <div id = "UnBlockCustomer" class = "col-xs-6 col-md-4" style = "display: none;">

           <form  name = "UnblockServlet" action = "/UnblockServlet" method="post">
               <div class ="panel panel-default">
                   <div class="panel-heading">
                       <h3 class="panel-title">Unblock customer</h3>
                   </div>
                   <div class = "panel-body">
                       <form class = "row">
                           <div class ="panel-group">
                               <div class = "panel-body">
                                   <div class = "input-group">
                                       <span class="input-group-addon"><span class="glyphicon glyphicon-credit-card"></span></span>
                                       <input id = "text_11" type="text" class="form-control text"  placeholder="Customer login" name = "Customer_login"  title = "" required="" value="" maxlength = "30"/>
                                       <span class="glyphicon form-control-feedback"></span>
                                   </div>
                               </div>

                               <div class = "panel-body">
                                   <div class = "row">
                                       <button type="submit" class="btn btn-labeled btn-success">
                                           <span class="btn-label"><i class="glyphicon glyphicon-plus-sign"></i></span>Unblock
                                       </button>
                                   </div>
                               </div>

                           </div>
                       </form>
                   </div>
               </div>

       </div>

       <div id = "GenerateKeys" class = "col-xs-6 col-md-4" style = "display: none;">

           <form  name = "GeneratedKeysServlet" action = "/GeneratedKeysServlet" method="post">
               <div class ="panel panel-default">
                   <div class="panel-heading">
                       <h3 class="panel-title">Generated keys</h3>
                   </div>
                   <div class = "panel-body">
                       <form class = "row">
                           <div class ="panel-group">
                               <div class = "panel-body">
                               </div>
                               <div class = "panel-body">
                                   <div class = "row">
                                       <button type="submit" class="btn btn-labeled btn-success">
                                           <span class="btn-label"><i class="glyphicon glyphicon-plus-sign"></i></span>Generate
                                       </button>
                                   </div>
                               </div>

                           </div>
                       </form>
                   </div>
               </div>

       </div>

       <%Object block = request.getAttribute("block");
           if(block !=null){%>
       <script>
           alert("Customer successfully blocked.");
       </script>
       <% }

       %>

       <%Object unblock = request.getAttribute("unblock");
           if(unblock !=null){%>
       <script>
           alert("Customer successfully unblocked.");
       </script>
       <% }

       %>

       <%Object generatedKey = request.getAttribute("generatedKey");
           if(generatedKey !=null){%>
       <script>
           alert("Keys successfully generated.");
       </script>
       <% }

       %>

    </div>

</body>
</html>
