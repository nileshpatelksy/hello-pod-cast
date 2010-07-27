<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.sql.*" %>
<%@ page import="javax.naming.Context" %>
<%@ page import="javax.naming.InitialContext" %>
<%@ page import="javax.sql.*" %>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<%
Context initCtx = new InitialContext();
Context envCtx = (Context) initCtx.lookup("java:comp/env");
DataSource ds = (DataSource)
  envCtx.lookup("jdbc/myoracle");

Connection conn = ds.getConnection();
Connection connection = null;
connection = conn;
Statement stmt = connection.createStatement();
// stmt.execute("CREATE TYPE object2 AS OBJECT(col_string2 VARCHAR(30), col_integer2 NUMBER)");
// stmt.execute("CREATE TYPE object1 AS OBJECT(col_string1 VARCHAR(30), col_integer2 object2)");
// stmt.execute("CREATE TABLE object1_table(col_integer NUMBER, col_object1 object1)");
// stmt.execute("INSERT INTO object1_table VALUES(1, object1('str1', object2('obj2str1', 123)))");

ResultSet rs = stmt.executeQuery("SELECT * FROM users");
while (rs.next()) {
	out.println(rs.getString("user_id")+":"+rs.getString("pswd"));
	out.println("-------------");
}

rs.close();
stmt.close();
connection.close();
%>

</body>
</html>