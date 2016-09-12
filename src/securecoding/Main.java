package securecoding;
import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import org.apache.commons.configuration.HierarchicalConfiguration.Node;
import org.apache.commons.io.FileUtils;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.Assignment;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.BodyDeclaration;
import org.eclipse.jdt.core.dom.ChildListPropertyDescriptor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.EnhancedForStatement;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.ExpressionStatement;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.IVariableBinding;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.QualifiedType;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.Statement;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;
import org.eclipse.jdt.core.dom.WhileStatement;

public class Main {
	// These fields used to get different counts for making final report
public static int FileCount=0;
public static int DirCount=0;
public static int dcl00j=0;
public static int dcl01j=0;
public static int dcl02j=0;
public static int msc01j=0;
public static int exp02j=0;
public static int ser05j=0;
public static int msc02j=0;
public static int msc00j=0;
public static int num07j=0;
public static int num09j=0;
public static int num10j=0;
public static int obj10j=0;
public static int obj09j=0;
public static int lck01j=0;
public static int lck02j=0;
public static int fio02j=0;
public static int err04j=0;
public static int err05j=0;
public static int err07j=0;
public static int err08j=0;
public static int err02j=0;
public static int met09j=0;
public static String htmlContent="null";
public static String Report="";
public static String RuleInfo=""; 
public static String htmlString=""; 
public static File newHtmlFile;
public static String htmlTable="";
public static File f;
public static int comFlag=0;
static FileOutputStream FileLoc=null;
StringBuilder sb = new StringBuilder();
private enum Options{
 a,b,c,e,d,f,s,h,cb,cs;
}
private enum rulesList{
	DCL00J,DCL01J,DCL02J,MSC00J,MSC01J,EXP02J,SER05J,MSC02J;
	}
	public static void main(String[] args) throws Exception  {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	 	boolean choice=true;
		String CurFileName;
		File currentDir;
		String PrintFname;
		File Existfile;
		File tempHtmlFile = new File("/usr/lib/rasttool/config/Template/index.html");
	     newHtmlFile = new File("/usr/lib/rasttool/output/index.html");
	  htmlString = FileUtils.readFileToString(tempHtmlFile);
		if (args.length == 0) {
			  System.err.println("Arguments expected...");
			  System.out.println("Please type 'java -jar rat.jar -h for help.");
			  return;
			}
		String arg1=args[0];  
		arg1=arg1.replace("-", "");
	/*	 if(!arg1.equals(Options.values().toString()))
		{
			System.err.print("Invalid Argument  ");	
			System.out.println(arg1);
			return;
		} */
		String flag="0";
		for (Object s : Options.values())
	    {
	            if (arg1.equals(s.toString()))
	        {
	            	flag="1";

	        }
	    }
		if(flag.equals("0"))
		{
			System.err.print("Invalid Argument  ");	
			System.out.println(arg1);
			return;
		}
		Options option=Options.valueOf(arg1);
        
        switch (option) {
        
            case b:   
            	        toZero();
            	      outputfile();
            	      if (args.length < 2) {
            			  System.err.println("Directory name expected as arguments ");
            			  System.out.println("Please type 'java -jar rat.jar h for help.");
            			  return;
            			}
            		   
    	               CurFileName= args[1];
    	                currentDir = new File(CurFileName);
    	                
						if(currentDir.exists() && currentDir.isDirectory()) { 
							display1();
							
	    	                displayDirectoryContents(currentDir);
	    	                display2();
						}
						else{
							System.err.println("Directory not found....");
						}
    	                
                     break;
            case s:  	 
            			toZero(); 
            			outputfile();
            			  if (args.length < 2) {
                			  System.err.println("File name expected as arguments ");
                			  System.out.println("Please type 'java -jar rat.jar h for help.");
                			  return;
                			}
                		   
						 PrintFname=args[1];
						 Existfile = new File(PrintFname);
						if(Existfile.isDirectory())
						{
							
							System.out.println(Existfile+"  is Directory. Please give a valid file name");
						}
						
						else if(Existfile.exists()) { 
						
							if(PrintFname.endsWith(".java"))
				              {
								display1();
								   ReadFile(PrintFname);
								display2();
				              }
                            else{
								
								System.out.println("Invalid Filename. Please give a valid filename(ending with .java):");
				              } 
							
							
						}
						else{
							System.out.println("File not found....");
						}
						
                     break;
                     
            case a:   if (args.length < 2) {
            		  System.err.println("File name expected as arguments ");
            		  System.out.println("Please type 'java -jar rat.jar h for help.");
            		  return;
  							}
  		   
					  String JavaFname=args[1];
					  File ExistFile = new File(JavaFname);
					  if(ExistFile.isDirectory())
					  {
				
						  System.out.println(ExistFile+"  is Directory. Please give a valid file name");
					  }
			
					  else if(ExistFile.exists()) { 
			
						  if(JavaFname.endsWith(".java"))
						  	{
							  if(args.length < 3)
							  	{
								  System.out.println("Rule name expected as third argument");
							  		System.out.println("Please type 'java -jar rat.jar h for help.");
				            		  return;
								  
							  	}
							     else
							     	{
							    	 
							    	 String RuleName=args[2];
							    	 QuickFix(RuleName,JavaFname);
								  		
							  }  
						  	}
						  else{
					
							  		System.out.println("Invalid second argument. Please give a valid filename(ending with .java):");
						  } 
				
				
					  }
					  else{
						  System.out.println("File not found....");
					  }
            		
                     break;
            case f:  System.out.println("Not implemented");
                     break;
            case h:  man();
                     break;
            case d:  outputfile();
            		 break;
            case cb:   
            		   toZero();
		    	      outputfile();
		    	      if (args.length < 2) {
		    			  System.err.println("Directory name expected as arguments ");
		    			  System.out.println("Please type 'java -jar rat.jar h for help.");
		    			  return;
		    			}
		    	      
		    		   
		               CurFileName= args[1];
		                currentDir = new File(CurFileName);
		                comFlag=1;
						if(currentDir.exists() && currentDir.isDirectory()) { 
							display1();
							
			                displayDirectoryContents(currentDir);
			                display2();
						}
						else{
							System.err.println("Directory not found....");
						}
		                
		             break;
            case cs:  	 
    			toZero(); 
    			outputfile();
    			  if (args.length < 2) {
        			  System.err.println("File name expected as arguments ");
        			  System.out.println("Please type 'java -jar rat.jar h for help.");
        			  return;
        			}
        		   
				PrintFname=args[1];
				Existfile = new File(PrintFname);
				if(Existfile.isDirectory())
				{
					
					System.out.println(Existfile+"  is Directory. Please give a valid file name");
				}
				
				else if(Existfile.exists()) { 
					comFlag=1;
					if(PrintFname.endsWith(".java"))
		              {
						display1();
						   ReadFile(PrintFname);
						display2();
		              }
                    else{
						
						System.out.println("Invalid Filename. Please give a valid filename(ending with .java):");
		              } 
					
					
				}
				else{
					System.out.println("File not found....");
				}
				
             break;
            case e:  choice=false;
                     System.out.println("Exit......");
            		 System.exit(0);
            		 break;
            default: System.out.println("Invalid option");
                     break;
        }
        htmlString=htmlString.replace("$001finalreport1001$" , Report);
        htmlString=htmlString.replace("<!--$totalblockattachhere-->", htmlTable);
        FileUtils.writeStringToFile(newHtmlFile, htmlString);
        File htmlFile = new File("/usr/lib/rasttool/output/index.html");
        Desktop.getDesktop().browse(htmlFile.toURI());
		} 	
		
 
	public static void toZero()
	{    comFlag=0;
		 FileCount=0;
		 DirCount=0;
		 
		 dcl00j=0;
		 dcl01j=0;
		 dcl02j=0;
		 msc01j=0;
		 exp02j=0;
		 
		 ser05j=0;
		 msc02j=0;
		 met09j=0;
		 msc00j=0;
		 num07j=0;
		 
		 num09j=0;
		 num10j=0;
		 obj10j=0;
		 obj09j=0;
		 lck01j=0;
		 
		 lck02j=0;
		 fio02j=0;
		 err04j=0;
		 err05j=0;
		 err07j=0;
		 
		 err08j=0;
		 err02j=0;
	}
	 private static void printLines(InputStream ins) throws Exception {
		    String line = null;
		    BufferedReader in = new BufferedReader(
		        new InputStreamReader(ins));
		    while ((line = in.readLine()) != null) {
		        System.out.println(line);
		    }
		  }

		  private static void runProcess(String command) throws Exception {
		    Process pro = Runtime.getRuntime().exec(command);
		   printLines(pro.getInputStream());
		    printLines(pro.getErrorStream());
		    pro.waitFor();
		//    System.out.println(command + " exitValue() " + pro.exitValue());
		  }   
	public static void outputfile(){
        	 
        	 String home = System.getProperty("user.home");
        	  f = new File(home + "/errorlog.txt");
            
        	//  f = new File("errorlog.txt");
              
    		
            // tries to create new file in the system
            try {
				if(f.createNewFile()||f.exists() ){
					 FileLoc = new FileOutputStream(f,true);
					// PrintStream LogFile = new PrintStream(FileLoc);
				}
			} catch (IOException e) {
				System.out.println("File Creation Error ");
				e.printStackTrace();
			}
            
        }
        public static void QuickFix(String arg3,String PrintFname) throws IOException{
        	String flag="0";
        	for (Object r : rulesList.values())
    	    {
    	            if (arg3.toUpperCase().equals(r.toString()))
    	        {
    	            	flag="1";

    	        }
    	    }
    		if(flag.equals("0"))
    		{
    			System.err.print(" Invalid rule name  ");	
    			System.out.println(arg3);
    			System.out.println("Please type 'java -jar rat.jar h for help.");
  			  	return;
    		}
        	
    		rulesList option=rulesList.valueOf(arg3.toUpperCase());
			 
		 
	        switch (option) {
	        
	            case MSC01J:   
	            		MSC01Jfix(PrintFname);
	    	              
	                     break;
	            case DCL00J:  System.out.println("Not implemented");	
	                     break;
	            case DCL01J:  System.out.println("Not implemented");
	                     break;
	            case DCL02J:  System.out.println("Not implemented");
	                     break;
	            case EXP02J: EXP02Jfix(PrintFname);
	            		break;
	            case SER05J: SER05Jfix(PrintFname);
        					break;
	            case MSC02J: MSC02Jfix(PrintFname);
				             break;
	            default: System.out.println("Invalid option");
	                     break;
	        }
        }
        public static void man() throws IOException{
    	 
    		BufferedReader br = new BufferedReader(new FileReader("/usr/lib/rasttool/config/man"));
    			try {
    			    StringBuilder sb = new StringBuilder();
    			    String line = br.readLine();
    			    while (line != null) {
    			        sb.append(line);
    			        sb.append(System.lineSeparator());
    			        line = br.readLine();
    			    }
    			  String manStr = sb.toString();
    			  System.out.println(manStr);
    			   
    			} finally {
    			    br.close();
    			    
    			}
    			System.exit(0);

    			  
    	}
        public static void ReadFix(String PrintFname) throws IOException{
    		String str;
    		//System.out.println("here");
    		BufferedReader br = new BufferedReader(new FileReader(PrintFname));
    			try {
    			    StringBuilder sb = new StringBuilder();
    			    String line = br.readLine();
    			    while (line != null) {
    			        sb.append(line);
    			        sb.append(System.lineSeparator());
    			        line = br.readLine();
    			    }
    			   str = sb.toString();
    			   
    			} finally {
    			    br.close();
    			}
    			 
    			FixParse(str,PrintFname);
    			System.exit(0);

    	}
        
        public static void MSC01Jfix(String PrintFname) throws IOException{
        	 RuleInfo="MSC01J";
        	ReadFix(PrintFname);
    		
    			 
    	}
        public static void MSC02Jfix(String PrintFname) throws IOException{
       	 RuleInfo="MSC02J";
       	ReadFix(PrintFname);
   		
   			 
   	}
        public static void SER05Jfix(String PrintFname) throws IOException{
       	 RuleInfo="SER05J";
       	ReadFix(PrintFname);
   		
   			 
   	}
        public static void EXP02Jfix(String PrintFname) throws IOException{
        	 RuleInfo="EXP02J";
        	ReadFix(PrintFname);
   		
   			 
   	}
        
	 public static void display1(){
		// PrintStream LogFile = new PrintStream(FileLoc);
		 DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		 System.out.println("**************Scanning Started**************");
		 			 
			System.out.println("");
			System.out.println("");
			 
     }
	 public static void display2(){
		 String sdcl00j="https://www.securecoding.cert.org/confluence/display/java/DCL00-J.+Prevent+class+initialization+cycles";
		 String sdcl01j="https://www.securecoding.cert.org/confluence/display/java/DCL01-J.+Do+not+reuse+public+identifiers+from+the+Java+Standard+Library";
		 String sdcl02j="https://www.securecoding.cert.org/confluence/display/java/DCL02-J.+Do+not+modify+the+collection%27s+elements+during+an+enhanced+for+statement";
		 String smsc01j="https://www.securecoding.cert.org/confluence/display/java/MSC01-J.+Do+not+use+an+empty+infinite+loop";
		 String sexp02j="https://www.securecoding.cert.org/confluence/display/java/EXP02-J.+Do+not+use+the+Object.equals%28%29+method+to+compare+two+arrays";
		 String sser05j="https://www.securecoding.cert.org/confluence/display/java/SER05-J.+Do+not+serialize+instances+of+inner+classes";
		 String smsc02j="https://www.securecoding.cert.org/confluence/display/java/MSC02-J.+Generate+strong+random+numbers";
		 String smsc00j="https://www.securecoding.cert.org/confluence/display/java/MSC00-J.+Use+SSLSocket+rather+than+Socket+for+secure+data+exchange";
		 String snum07j="https://www.securecoding.cert.org/confluence/display/java/NUM07-J.+Do+not+attempt+comparisons+with+NaN";
		 String snum09j="https://www.securecoding.cert.org/confluence/display/java/NUM09-J.+Do+not+use+floating-point+variables+as+loop+counters";
		 String snum10j="https://www.securecoding.cert.org/confluence/display/java/NUM10-J.+Do+not+construct+BigDecimal+objects+from+floating-point+literals";
		 String sobj10j="https://www.securecoding.cert.org/confluence/display/java/OBJ10-J.+Do+not+use+public+static+nonfinal+fields";
		 String sobj09j="https://www.securecoding.cert.org/confluence/display/java/OBJ09-J.+Compare+classes+and+not+class+names";
		 String slck01j="https://www.securecoding.cert.org/confluence/display/java/LCK01-J.+Do+not+synchronize+on+objects+that+may+be+reused";
		 String slck02j="https://www.securecoding.cert.org/confluence/pages/viewpage.action?pageId=43647087";
		 String sfio02j="https://www.securecoding.cert.org/confluence/display/java/FIO02-J.+Detect+and+handle+file-related+errors";
		 String serr04j="https://www.securecoding.cert.org/confluence/display/java/ERR04-J.+Do+not+complete+abruptly+from+a+finally+block";
		 String serr05j="https://www.securecoding.cert.org/confluence/display/java/ERR05-J.+Do+not+let+checked+exceptions+escape+from+a+finally+block";
		 String serr07j="https://www.securecoding.cert.org/confluence/display/java/ERR07-J.+Do+not+throw+RuntimeException%2C+Exception%2C+or+Throwable";
		 String serr08j="https://www.securecoding.cert.org/confluence/display/java/ERR02-J.+Prevent+exceptions+while+logging+data";
		 String serr02j="https://www.securecoding.cert.org/confluence/display/java/ERR02-J.+Prevent+exceptions+while+logging+data";
		 String smet09j="https://www.securecoding.cert.org/confluence/display/java/MET09-J.+Classes+that+define+an+equals%28%29+method+must+also+define+a+hashCode%28%29+method";
		 PrintStream LogFile = new PrintStream(FileLoc);
		 DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		 String Repotable="<tr>\n"+
				 "<td><a href=\"$link\" target=\"_blank\">$rule</a></td>\n"+
                 "<td>$number</td>\n"+
           " </tr>\n";
		int finalCount=0;
		finalCount=dcl00j+dcl01j+dcl02j+met09j+msc01j+exp02j+ser05j+msc02j+msc00j+num07j+num09j+num10j+obj10j+obj09j+lck01j+lck02j+fio02j+err04j+err05j+err07j+err08j+err02j; 
	    System.out.println("");
		System.out.println("");
		System.out.println("**************Report**************");
		System.out.println("----------------------------------");
		System.out.println("Rule Name       Total Violations");
		System.out.println("----------------------------------");
		System.out.println("DCL00J   -       "+dcl00j);
		System.out.println("DCL01J   -       "+dcl01j);
		System.out.println("DCL02J   -       "+dcl02j);	
		System.out.println("ERR02J   -       "+err02j);
		System.out.println("ERR04J   -       "+err04j);
		System.out.println("ERR05J   -       "+err05j);
		System.out.println("ERR07J   -       "+err07j);
		System.out.println("ERR08J   -       "+err08j);
		System.out.println("EXP02J   -       "+exp02j);
		System.out.println("FIO02J   -       "+fio02j);
		System.out.println("LCK01J   -       "+lck01j);
		System.out.println("LCK02J   -       "+lck02j);
		System.out.println("MET09J   -       "+met09j);
		System.out.println("MSC00J   -       "+msc00j);
		System.out.println("MSC01J   -       "+msc01j);
		System.out.println("MSC02J   -       "+msc02j);
		System.out.println("NUM07J   -       "+num07j);
		System.out.println("NUM09J   -       "+num09j);
		System.out.println("NUM10J   -       "+num10j);
		System.out.println("OBJ09J   -       "+obj09j);
		System.out.println("OBJ10J   -       "+obj10j);
		System.out.println("SER05J   -       "+ser05j);
	
		System.out.println("----------------------------------");
		String dirt="directories";
		String fILEC="files";
		 
		String Fc="";
		if(DirCount==0)
		{
			DirCount=1;
			dirt="directory";
			}
		if(FileCount==1)
		{
			fILEC="file";
			}
		if(finalCount==0)
		{
			Fc=" Out of the above rules, No CERT violations are reported";
		}
		else if(finalCount==1){
			Fc=" Out of the above rules, 1 CERT violation is reported";
		}
		else
		{
			Fc=" Out of the above rules, "+finalCount +" CERT violations are reported";
		}
		Report="Total "+DirCount+" " +dirt+ " searched and "+FileCount+" " +fILEC+ " scanned."+Fc;
		System.out.println(Report);
		System.out.println("");
		System.out.println("");
		 
		htmlTable=Repotable.replace("$rule"," DCL00J").replace("$link", sdcl00j).replace("$number", Integer.toString(dcl00j));
 
		htmlTable=htmlTable+Repotable.replace("$rule"," DCL01-J").replace("$link", sdcl01j).replace("$number", Integer.toString(dcl01j));
		htmlTable=htmlTable+Repotable.replace("$rule"," DCL02-J").replace("$link", sdcl02j).replace("$number", Integer.toString(dcl02j));
		htmlTable=htmlTable+Repotable.replace("$rule"," EXP02-J").replace("$link", sexp02j).replace("$number", Integer.toString(exp02j));
		htmlTable=htmlTable+Repotable.replace("$rule"," ERR02-J").replace("$link", serr02j).replace("$number", Integer.toString(err02j));
		htmlTable=htmlTable+Repotable.replace("$rule"," ERR04-J").replace("$link", serr04j).replace("$number", Integer.toString(err04j));
		htmlTable=htmlTable+Repotable.replace("$rule"," ERR05-J").replace("$link", serr05j).replace("$number", Integer.toString(err05j));
		htmlTable=htmlTable+Repotable.replace("$rule"," ERR07-J").replace("$link", serr07j).replace("$number", Integer.toString(err07j));
		htmlTable=htmlTable+Repotable.replace("$rule"," ERR08-J").replace("$link", serr08j).replace("$number", Integer.toString(err08j));
		htmlTable=htmlTable+Repotable.replace("$rule"," FIO02-J").replace("$link", sfio02j).replace("$number", Integer.toString(fio02j)); 
		htmlTable=htmlTable+Repotable.replace("$rule"," LCK01-J").replace("$link", slck01j).replace("$number", Integer.toString(lck01j)); 
		htmlTable=htmlTable+Repotable.replace("$rule"," LCK02-J").replace("$link", slck02j).replace("$number", Integer.toString(lck02j)); 
		htmlTable=htmlTable+Repotable.replace("$rule"," MET09-J").replace("$link", smet09j).replace("$number", Integer.toString(met09j)); 
		htmlTable=htmlTable+Repotable.replace("$rule"," MSC00-J").replace("$link", smsc00j).replace("$number", Integer.toString(msc00j));
		htmlTable=htmlTable+Repotable.replace("$rule"," MSC01-J").replace("$link", smsc01j).replace("$number", Integer.toString(msc01j));
		htmlTable=htmlTable+Repotable.replace("$rule"," MSC02-J").replace("$link", smsc02j).replace("$number", Integer.toString(msc02j));
		htmlTable=htmlTable+Repotable.replace("$rule"," NUM07-J").replace("$link", snum07j).replace("$number", Integer.toString(num07j));
		htmlTable=htmlTable+Repotable.replace("$rule"," NUM09-J").replace("$link", snum09j).replace("$number", Integer.toString(num09j));
		htmlTable=htmlTable+Repotable.replace("$rule"," NUM10-J").replace("$link", snum10j).replace("$number", Integer.toString(num10j));
		htmlTable=htmlTable+Repotable.replace("$rule"," OBJ09-J").replace("$link", sobj09j).replace("$number", Integer.toString(obj09j));
		htmlTable=htmlTable+Repotable.replace("$rule"," OBJ10-J").replace("$link", sobj10j).replace("$number", Integer.toString(obj10j)); 
		htmlTable=htmlTable+Repotable.replace("$rule"," SER05-J").replace("$link", sser05j).replace("$number", Integer.toString(ser05j));
		
		
		
		Date date1 = new Date();
		System.out.println("*******Scanning Finished*******");
	 
		  }
// Read and parse all java file in a directory and sub directory
	public static void displayDirectoryContents(File dir) throws Exception {
		String str;
		String PrintFname;
		String Cmd;
		//FileOutputStream FileLoc = new FileOutputStream("output/errorlog.txt",true);// Save output to this file
		//   PrintStream LogFile = new PrintStream(FileLoc);
		 
		try {
			File[] files = dir.listFiles();
			for (File file : files) {
				
				if (file.isDirectory()) {
				 
					 if(file.toString().endsWith("test")||file.toString().endsWith("tests")||file.toString().endsWith("debug"))
					{
						break;
					
				} 
					else
					{  
						displayDirectoryContents(file);
					DirCount++;
					 }
					}
				 else {
					
					 if(file.getCanonicalPath().toString().endsWith(".java"))
	                  { 
						 PrintFname=file.getCanonicalPath().toString();
	                    // System.out.println("     file:" + file.getCanonicalPath());
	                     //LogFile.println("     file:" + file.getCanonicalPath());
	                     FileCount++;
	                     BufferedReader br = new BufferedReader(new FileReader(file.getCanonicalPath().toString()));
	     				try {
	     				    StringBuilder sb = new StringBuilder();
	     				    String line = br.readLine();
	     				    while (line != null) {
	     				        sb.append(line);
	     				        sb.append(System.lineSeparator());
	     				        line = br.readLine();
	     				    }
	     				   str = sb.toString();
	     				} finally {
	     				    br.close();
	     				}
	     					Cmd="javac "+PrintFname.toString();
	     					if(comFlag==1){
	     				 runProcess(Cmd);}
	                     parse(str,PrintFname);
	                  }
					
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void ReadFile(String PrintFname) throws Exception{
		String str;
		FileCount++;
		BufferedReader br = new BufferedReader(new FileReader(PrintFname));
			try {
			    StringBuilder sb = new StringBuilder();
			    String line = br.readLine();
			    while (line != null) {
			        sb.append(line);
			        sb.append(System.lineSeparator());
			        line = br.readLine();
			    }
			   str = sb.toString();
			   
			} finally {
			    br.close();
			}
			if(comFlag==1){
				String Cmd="javac "+PrintFname.toString();
				 runProcess(Cmd);}
			 parse(str,PrintFname);
			 
	}
	public static void parse(String str,	String PrintFname) throws IOException{
	ASTParser parser = ASTParser.newParser(AST.JLS3);
	    parser.setSource(str.toCharArray());
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
	    CompilationUnit cu = (CompilationUnit) parser.createAST(null);
//*******************************************************************************

	    // System.out.println(str.toCharArray());
//TypeFinderVisitor v = new TypeFinderVisitor();
cu.accept(new MyVisitor(cu,str,PrintFname));
 
if(!htmlContent.equals("null"))
		 
			
		
		htmlString=htmlString.replace("<!--$newfileblockattachhere-->", htmlContent);
		
		htmlContent="null";
	 
 
	}

 

public static void FixParse(String str,	String PrintFname) throws IOException{
	ASTParser parser = ASTParser.newParser(AST.JLS3);
	    parser.setSource(str.toCharArray());
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
	    CompilationUnit cu = (CompilationUnit) parser.createAST(null);
//*******************************************************************************
	    AST ast = cu.getAST();
	    
cu.accept(new MyQuickFix(cu,str,PrintFname,ast));	
	}
}
