package securecoding;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;

public class StringParse {
	public String parseTable(String rule, String standard,String line,String violation) throws IOException{
		//System.out.println( rule+standard+line+violation);
		String title = FileUtils.readFileToString(new File("config/table"), "UTF-8").toString();
        String des = FileUtils.readFileToString(new File("config/des.html"), "UTF-8").toString();
        String pre="<"+rule+">";
        String post="</"+rule+">";
        String content = StringUtils.substringBetween(des, pre, post);
    //	System.out.println(title);
 		title=title.replace("<!--$001rule$001-->", rule);
		title=title.replace("<!--$001standard$001-->", standard);
		title=title.replace("<!--$001linenumber$001-->", line);
		if(content!=null)
		{
		content=content.replace("<!--$001violation$001-->", violation);
		content=content.replace("<!--$001linenumber$001-->", line);
		}
		else{
			content="Not Available";
		}
		//title=title.replace("<!--$001rulename$001-->", rulename);
		//title=title.replace("<!--$001filelink$001-->", filelink);
		//title=title.replace("<!--$001violation$001-->", violation);
		title=title+content+"<!--$newtableblockattachhere-->";
		 
	
		return title;
	}
	public String parseBlock(String filename) throws IOException{
		 
		String body = FileUtils.readFileToString(new File("config/block.html"), "UTF-8").toString();
		 
		String path="<p>Location :<a href=\""+filename+"\">"+filename+"</a></p>\n";
		   filename=filename.substring(filename.lastIndexOf("/") + 1);  
 		// filename=StringUtils.capitalize(filename);
	 body= body.replace("<!--$001filename$001-->", filename);
	 body= body.replace("<!--$001href$001-->", "Loc"+Main.FileCount);
	 body= body.replace("<!--$001id$001-->", "Loc"+Main.FileCount);
	 body= body.replace("<!--$001path$001-->", path);
		//title=title.replace("<!--$001violation$001-->", violation);
	 body= body+" <!--$newfileblockattachhere-->";
		 
        
		return body;
	}
	public void copy() throws IOException{
	File newHtmlFile = new File("output/Template/index.html");
	String htmlString = FileUtils.readFileToString(newHtmlFile);
	String title = "<tr><td>Rahul</td><td>c</td><td>No</td></tr><!--table>";
	String body = "This is Body";
	htmlString = htmlString.replace("<!--table>", title);
 
	}
}
