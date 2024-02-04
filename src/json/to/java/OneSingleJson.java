package json.to.java;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.apache.commons.lang3.StringEscapeUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

public class OneSingleJson {

	public static void main(String[] args) throws ClassNotFoundException, SQLException, JsonGenerationException, JsonMappingException, IOException {
		// TODO Auto-generated method stub
Class.forName("com.mysql.cj.jdbc.Driver");
Connection conn=null;
JSONArray js = new JSONArray();

ArrayList<CustomerDetails> arr =new ArrayList<CustomerDetails>();

conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/Business", "root", "gayu1503");

//object of statement class will help us to execute queries
Statement st=conn.createStatement();
ResultSet rs=st.executeQuery("select * from CustomerInfo where location = 'Asia' and purchasedDate=curdate();");
while(rs.next())
{
	CustomerDetails c=new CustomerDetails();
	c.setCourseName(rs.getString(1));
	c.setPurchasedDate(rs.getString(2));
	c.setAmount(rs.getInt(3));
	c.setLocation(rs.getString(4));
	arr.add(c);
	
	

}
for(int i=0;i<arr.size();i++)
{
	ObjectMapper o=new ObjectMapper(); // using Jackson
	o.writeValue(new File("C:\\Atish\\SDET-Full Stack QA\\JsonJava\\infoCust"+i+".json"),arr.get(i));
	//covert json String from java object
	Gson g = new Gson();
	String jsonString = g.toJson(arr.get(i));
	js.add(jsonString);

}

// Json Simple
JSONObject jo = new JSONObject();
jo.put("data", js);
System.out.println(jo.toJSONString());
String unescape = StringEscapeUtils.unescapeJava(jo.toJSONString());
System.out.println(unescape);
String str = unescape.replace("\"{", "{");
String finalString = str.replace("}\"", "}");
System.out.println(finalString);
   // Create single json File
try(FileWriter file = new FileWriter("C:\\Atish\\SDET-Full Stack QA\\JsonJava\\SingleJson.json")){
	file.write(finalString);
}

conn.close();

	}

}
