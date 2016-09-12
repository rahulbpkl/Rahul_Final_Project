package securecoding;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.ArrayCreation;
import org.eclipse.jdt.core.dom.ArrayType;
import org.eclipse.jdt.core.dom.Assignment;
import org.eclipse.jdt.core.dom.BreakStatement;
import org.eclipse.jdt.core.dom.CatchClause;
import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.ContinueStatement;
import org.eclipse.jdt.core.dom.EnhancedForStatement;
import org.eclipse.jdt.core.dom.ExpressionStatement;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.ForStatement;
import org.eclipse.jdt.core.dom.ImportDeclaration;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.QualifiedName;
import org.eclipse.jdt.core.dom.ReturnStatement;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.SimpleType;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.SynchronizedStatement;
import org.eclipse.jdt.core.dom.ThrowStatement;
import org.eclipse.jdt.core.dom.TryStatement;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;
import org.eclipse.jdt.core.dom.WhileStatement;
import org.eclipse.jdt.internal.compiler.lookup.TypeBinding;

import securecoding.StringParse;

class MyVisitor extends ASTVisitor{
	CompilationUnit cu;
	String source;
	String PrintFname;
	String Classname;
	String met;
	String catchname="";
	String lcStr="";
	int LcFlg=0;
	String LCvdf="";
	int LineNo,dcl02lno;
	int cFlag=0,Mlno=0;
	int Lastlno=0;
	int Mil=0,Nil=0;
	 String str="";
	 int Mflag=0;
	int Clno=0;
	String SingVarDec=null;
	StringParse p=new StringParse();
	int pos=0;
	ArrayList<Integer> LineList = new ArrayList<Integer>();
	ArrayList<String> VarList = new ArrayList<String>();
	ArrayList<String> ArrList = new ArrayList<String>();
	ArrayList<String> SymList1 = new ArrayList<String>();
	ArrayList<String> SymList2 = new ArrayList<String>();
	FileOutputStream FileLoc = new FileOutputStream(Main.f,true);
	  int Flg=0;
    PrintStream LogFile = new PrintStream(FileLoc);
  //  File tempHtmlFile = new File("Template/index.html");
   
   
	public MyVisitor(CompilationUnit cu, String source, String printFname) throws IOException {
		super();
		this.cu = cu;
		this.source = source;
		this.PrintFname=printFname;
		
	}
 
	public boolean visit(ClassInstanceCreation node) {
		 
//			  System.out.println(node.arguments());
				 
				  	if(node.getType().toString().equals("BigDecimal")){
				  		if(node.arguments().toString().contains(".")&&!node.arguments().toString().contains("\""))
				  		{
				  			System.out.println("File: " + PrintFname);
		 	 	            System.out.println("NUM10-J. Do not construct BigDecimal objects from floating-point literals Error at Line "+cu.getLineNumber(node.getStartPosition()));	
				  			Main.num10j++;	
				  			try {
    			   				
 							    
			  					
    			   				if (Main.htmlContent=="null"){
    			   					String block=	p.parseBlock(PrintFname);
    			   					Main.htmlContent = block;	
    			   					
    			   					String x=	p.parseTable("NUM10-J", "CERT", Integer.toString(cu.getLineNumber(node.getStartPosition())),  node.toString() );
    			   					Main.htmlContent = Main.htmlContent.replace("<!--$newtableblockattachhere-->",x);
    			   				 
    			   					
    			   				}
    			   				else
    			   				{
    			   					String x=	p.parseTable("NUM10-J", "CERT", Integer.toString(cu.getLineNumber(node.getStartPosition())),  node.toString() );
    			   					Main.htmlContent = Main.htmlContent.replace("<!--$newtableblockattachhere-->",x);
    			   				 
    			   					}
    			   			} catch (IOException e) {
    			   				// TODO Auto-generated catch block
    			   			
    			   				 e.printStackTrace();
    			   			} 
					 	}
				  		String s;
			  			s=node.arguments().toString().replace("[", "");
			  			s=s.replace("]", "");
			  			for (int i = 0; i < SymList1.size(); i++) 
				  		{		  			
				  		if(SymList1.contains(s)){
				  			 		  				 
				  				//System.out.println(i); 
				  				if(SymList2.get(i).toString().equals("float"))
				  				{
				  					System.out.println("File: " + PrintFname);
				 	 	            System.out.println("NUM10-J. Do not construct BigDecimal objects from floating-point literals Error at Line "+cu.getLineNumber(node.getStartPosition()));	
				  				    Main.num10j++;
				  				try {
	    			   				
		 							    
				  					
	    			   				if (Main.htmlContent=="null"){
	    			   					String block=	p.parseBlock(PrintFname);
	    			   					Main.htmlContent = block;	
	    			   					
	    			   					String x=	p.parseTable("NUM10-J", "CERT", Integer.toString(cu.getLineNumber(node.getStartPosition())),  node.toString() );
	    			   					Main.htmlContent = Main.htmlContent.replace("<!--$newtableblockattachhere-->",x);
	    			   				 
	    			   					
	    			   				}
	    			   				else
	    			   				{
	    			   					String x=	p.parseTable("NUM10-J", "CERT", Integer.toString(cu.getLineNumber(node.getStartPosition())),  node.toString() );
	    			   					Main.htmlContent = Main.htmlContent.replace("<!--$newtableblockattachhere-->",x);
	    			   				 
	    			   					}
	    			   			} catch (IOException e) {
	    			   				// TODO Auto-generated catch block
	    			   			
	    			   				 e.printStackTrace();
	    			   			} 

				  				}
				  			}
				  		}
				 	} 
					
				 
		
		if(node.getType().toString().equals("Random"))
		{	 System.out.println("File: " + PrintFname);
        System.out.println("MSC02-J. Generate strong random numbers. Error at line  '" +cu.getLineNumber(node.getStartPosition()));								    
	    Main.msc02j++;
			try{
				
				if (Main.htmlContent=="null"){
					String block=	p.parseBlock(PrintFname);
					Main.htmlContent = block;	
					String x=	p.parseTable("MSC02-J", "CERT", Integer.toString(cu.getLineNumber(node.getStartPosition())), node.toString());
					Main.htmlContent = Main.htmlContent.replace("<!--$newtableblockattachhere-->",x);
				 
					
				}
				else
				{
					String x=	p.parseTable("MSC02-J", "CERT", Integer.toString(cu.getLineNumber(node.getStartPosition())), node.toString());
					Main.htmlContent = Main.htmlContent.replace("<!--$newtableblockattachhere-->",x);
				 
					}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} 
		if(node.getType().toString().equals("ServerSocket")||node.getType().toString().equals("Socket"))
		{	
			System.out.println("File: " + PrintFname);
            System.out.println("MSC00-J. Use SSLSocket rather than Socket for secure data exchange. Error at line  '" +cu.getLineNumber(node.getStartPosition()));								    
		    	
		try {
			 			 
			if (Main.htmlContent=="null"){
				String block=	p.parseBlock(PrintFname);
				Main.htmlContent = block;	
				String x=	p.parseTable("MSC00-J", "CERT", Integer.toString(cu.getLineNumber(node.getStartPosition())), "Use SSLSocket rather than Socket");
				Main.htmlContent = Main.htmlContent.replace("<!--$newtableblockattachhere-->",x);
			 
				
			}
			else
			{
				String x=	p.parseTable("MSC00-J", "CERT", Integer.toString(cu.getLineNumber(node.getStartPosition())), "Use SSLSocket rather than Socket");
				Main.htmlContent = Main.htmlContent.replace("<!--$newtableblockattachhere-->",x);
			 
				}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}Main.msc00j++;
		}return true;
	}
	//***********************It is for getting class name********
	public boolean visit(TypeDeclaration node) {
		Mflag=0;
		cFlag=0;
		node.accept(new ASTVisitor() {
			public boolean visit(MethodDeclaration node1) {
			
		 
		  
		 
		//  System.out.println("kooi1");
		if(node1.getName().toString().equals("equals")){
			 
			str= node1.toString().replace(node1.getBody().toString(), "");
			if(str.equals("public boolean equals(Object o)")){
				Mflag=1;
				}}
			return true;
		 }});
		 
		if(Mflag==1){
		 node.accept(new ASTVisitor() {
			  public boolean visit(MethodDeclaration sn) { // Moving inside to get variable
				  String str1=sn.toString().replace(sn.getBody().toString(), "");
					if(str1.equals("public int hashCode()")){
					cFlag=1;
						}
			         return true;
			     }}); }
		
		 
	if(cFlag==0&& Mflag==1){
		System.out.println("Error met09"+str);
		System.out.println("File: " + PrintFname);
        System.out.println("MET09-J. Classes that define an equals() method must also define a hashCode() method. Error at line  '" +cu.getLineNumber(node.getStartPosition()));								    
	    Main.met09j++;	
	try {
		 			 
		if (Main.htmlContent=="null"){
			String block=	p.parseBlock(PrintFname);
			Main.htmlContent = block;	
			String x=	p.parseTable("MET09-J", "CERT", Integer.toString(cu.getLineNumber(node.getStartPosition())), " defines an equals() without a hashCode() method");
			Main.htmlContent = Main.htmlContent.replace("<!--$newtableblockattachhere-->",x);
		 
			
		}
		else
		{
			String x=	p.parseTable("MET09-J", "CERT", Integer.toString(cu.getLineNumber(node.getStartPosition())), " defines an equals() without a hashCode() method");
			Main.htmlContent = Main.htmlContent.replace("<!--$newtableblockattachhere-->",x);
		 
			}
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}}
 
	Clno=0;
	// It is useful if multiple classes are present in a single .java file			
		SimpleName name=node.getName(); // Gives class name of current visiting node
		this.Classname=name.toString(); // Saving to a global variable for many other function
		this.LineNo=cu.getLineNumber(name.getStartPosition());// Line number saving to a global variable. It is useful for giving error message
		 /*try {
			ruleDcl01(); // Calling the method for DCL01-J. Currently added as a method. Later it may include this block.
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} */
	
			
		node.accept(new ASTVisitor() {
            public boolean visit(TypeDeclaration nm) {
            	if(nm.superInterfaceTypes().toString().equals(node.superInterfaceTypes().toString()) &&!nm.superInterfaceTypes().toString().equals("[]")&&!node.superInterfaceTypes().toString().equals("[]")&&cu.getLineNumber(nm.getStartPosition())!=cu.getLineNumber(node.getStartPosition())&&!node.modifiers().toString().contains("static")&&!nm.modifiers().toString().contains("static"))
            	{	 System.out.println("File: " + PrintFname);
                       
                   
            		System.out.println("SER05-J. Do not serialize instances of inner classes. Error at line  '" +cu.getLineNumber(nm.getStartPosition())+"' Outer class name '"+node.getName()+ "' Inner class name '"+nm.getName()+ "' Instances name"+node.superInterfaceTypes().toString());								    
					 Main.ser05j++;
					try {
		   				
						    
						
		   				if (Main.htmlContent=="null"){
		   					String block=	p.parseBlock(PrintFname);
		   					Main.htmlContent = block;	
		   					
		   					String x=	p.parseTable("SER05-J", "CERT", Integer.toString(cu.getLineNumber(nm.getStartPosition())),  "' Outer class name '"+node.getName()+ "' Inner class name '"+nm.getName()+ "' Instances name"+node.superInterfaceTypes().toString());
		   					Main.htmlContent = Main.htmlContent.replace("<!--$newtableblockattachhere-->",x);
		   				 
		   					
		   				}
		   				else
		   				{
		   					String x=	p.parseTable("SER05-J", "CERT", Integer.toString(cu.getLineNumber(nm.getStartPosition())),  "' Outer class name '"+node.getName()+ "' Inner class name '"+nm.getName()+ "' Instances name"+node.superInterfaceTypes().toString());
		   					Main.htmlContent = Main.htmlContent.replace("<!--$newtableblockattachhere-->",x);
		   				 
		   					}
		   			} catch (IOException e) {
		   				// TODO Auto-generated catch block
		   			
		   				 e.printStackTrace();
		   			} 
            		
            	}
            	return true;
            }}); 
		
		return true;
		
	}
	
	//*************************Block Ends Here*************************************
	//***********************Prevent class initialization cycles(IntraClass)*********
	  public boolean visit(MethodDeclaration md) {
		 
	        if (md.getName().toString().equals(Classname)) {
	            md.accept(new ASTVisitor() {
	                public boolean visit(Assignment fd) { // Moving inside to get assignment operator
	               String s=fd.getRightHandSide().toString();
	                	
	                    //System.out.println("in method: " + fd.getRightHandSide());
	                    for(int i=0;i<VarList.size();i++)
	                    { if(s.contains(VarList.get(i)))
	                    {
	                    	 
	                    	 if(LineList.get(i)>0&&Clno>0){ // Check variable declaration and constructor initialization with "Static" keyword is present
	    					if(LineList.get(i)>Clno)//  Check variable declaration is after constructor initialization 
	    					{
	    						System.out.println("File: " + PrintFname);	   	                    
	    						System.out.println("DCL00-J. Prevent class initialization cycles (Intraclass Cycle) problem. 'static' constructor initialization at line " +Clno+", invoked at line  " +cu.getLineNumber(fd.getStartPosition())+ " and variable ' "+VarList.get(i)+" ' initialization at line "+LineList.get(i)+". Please do 'static' constructor initialization after all 'static' variable initialization");								    
	    						try {
	    			   				
	    				   			 String xYz=" 'Static' constructor initialization at line " +Clno+", invoked at line  " +cu.getLineNumber(fd.getStartPosition())+ " and variable ' "+VarList.get(i)+" ' initialization at line "+LineList.get(i)+". Please do 'static' constructor initialization after all 'static' variable initialization";								    
				
	    			   				if (Main.htmlContent=="null"){
	    			   					String block=	p.parseBlock(PrintFname);
	    			   					Main.htmlContent = block;	
	    			   					
	    			   					String x=	p.parseTable("DCL00-J", "CERT", Integer.toString(cu.getLineNumber(fd.getStartPosition())),  xYz );
	    			   					Main.htmlContent = Main.htmlContent.replace("<!--$newtableblockattachhere-->",x);
	    			   				 
	    			   					
	    			   				}
	    			   				else
	    			   				{
	    			   					String x=	p.parseTable("DCL00-J", "CERT", Integer.toString(cu.getLineNumber(fd.getStartPosition())),  xYz );
	    			   					Main.htmlContent = Main.htmlContent.replace("<!--$newtableblockattachhere-->",x);
	    			   				 
	    			   					}
	    			   			} catch (IOException e) {
	    			   				// TODO Auto-generated catch block
	    			   			
	    			   				 e.printStackTrace();
	    			   			} 
	    						Main.dcl00j++;
	    					}
	    				} 
	                    }}
	                    return true;
	                }
	            });
	          
	            
	           // System.out.println(VarList);
	           // System.out.println(LineList);
	        }
	        List ls=md.thrownExceptions();
			for(int i=0;i<ls.size();i++)
			{
	            if(ls.contains("Exception")||ls.contains("RuntimeException")||ls.contains("Throwable"))
	            {
	        
	   		 try {
	   				
	   			 
	   				if (Main.htmlContent=="null"){
	   					String block=	p.parseBlock(PrintFname);
	   					Main.htmlContent = block;	
	   					String x=	p.parseTable("ERR07-J", "CERT", Integer.toString(cu.getLineNumber(md.getStartPosition())),  md.thrownExceptions().toString() );
	   					Main.htmlContent = Main.htmlContent.replace("<!--$newtableblockattachhere-->",x);
	   				 
	   					
	   				}
	   				else
	   				{
	   					// System.out.println("ERR07-J" +"CERT"+ Integer.toString(cu.getLineNumber(sn.getStartPosition()))+  "Ruxception" );
	   					  
	   				 	  String x=	p.parseTable("ERR07-J", "CERT", Integer.toString(cu.getLineNumber(md.getStartPosition())), md.thrownExceptions().toString() );
	   				   Main.htmlContent = Main.htmlContent.replace("<!--$newtableblockattachhere-->",x);
	   				 
	   					}
	   			} catch (IOException e) {
	   				// TODO Auto-generated catch block
	   			
	   				 e.printStackTrace();
	   			}
	   			Main.err07j++;
	   	}}
	        return true;
	    }
	 
	  public boolean visit(VariableDeclarationStatement vds) { // Moving inside to get variable
		   
		  if(vds.getType().isArrayType()){
			  vds.accept(new ASTVisitor() {
				  public boolean visit(SimpleName sn) { 
					 
					   
				     ArrList.add(sn.toString());
					
			      
			  return true;
			  }});  
		 
		   
		 /* if(vds.modifiers().toString().equals("[]")){
			  vds.accept(new ASTVisitor() {
			  public boolean visit(SimpleName sn) { // Moving inside to get variable
				     ArrList.add(sn.getIdentifier());
			         return true;
			     }});}*/
		  }
	         return true;
	     }
  public boolean visit(MethodInvocation node) {
	  //System.out.println(ArrList);
		//  SimpleName sn=node.getName();
	  
	  if(node.toString().contains(".getClass().getName().equals"))
		{

			try {
				 
				 
				if (Main.htmlContent=="null"){
					String block=	p.parseBlock(PrintFname);
					Main.htmlContent = block;	
					String x=	p.parseTable("OBJ09-J", "CERT", Integer.toString(cu.getLineNumber(node.getStartPosition())), node.toString());
					Main.htmlContent = Main.htmlContent.replace("<!--$newtableblockattachhere-->",x);
				 
					
				}
				else
				{
					String x=	p.parseTable("OBJ09-J", "CERT", Integer.toString(cu.getLineNumber(node.getStartPosition())), node.toString());
					Main.htmlContent = Main.htmlContent.replace("<!--$newtableblockattachhere-->",x);
				 
					}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Main.obj09j++;
		}
		 Flg=0;

		  if(node.toString().contains(".equals")&&!node.toString().contains("Arrays.equals"))
		  {
			  node.accept(new ASTVisitor() {
				  public boolean visit(SimpleName sn) { // Moving inside to get variable
					 if(ArrList.contains(sn.getIdentifier().toString()))
							 {
						 Flg++;
							 }
				         return true;
				     }});}
			  
		  if(Flg>1){
				System.out.println("File:" + PrintFname);
	            
					System.out.println("EXP02-J. Do not use the Object.equals() method to compare two arrays. Error at line "+cu.getLineNumber(node.getStartPosition()));
					LogFile.println("EXP02-J. Do not use the Object.equals() method to compare two arrays. Error at line "+cu.getLineNumber(node.getStartPosition()));
					try {
		   				
						    
						
		   				if (Main.htmlContent=="null"){
		   					String block=	p.parseBlock(PrintFname);
		   					Main.htmlContent = block;	
		   					
		   					String x=	p.parseTable("EXP02-J", "CERT", Integer.toString(cu.getLineNumber(node.getStartPosition())),  node.toString() );
		   					Main.htmlContent = Main.htmlContent.replace("<!--$newtableblockattachhere-->",x);
		   				 
		   					
		   				}
		   				else
		   				{
		   					String x=	p.parseTable("DCL00-J", "CERT", Integer.toString(cu.getLineNumber(node.getStartPosition())),  node.toString() );
		   					Main.htmlContent = Main.htmlContent.replace("<!--$newtableblockattachhere-->",x);
		   				 
		   					}
		   			} catch (IOException e) {
		   				// TODO Auto-generated catch block
		   			
		   				 e.printStackTrace();
		   			} 

					
					Main.exp02j++;
			 }
		
		return true;
		  
	  } 
 	public boolean visit(FieldDeclaration node) {
		 Type t=node.getType();
 if(t.toString().equals(this.Classname)&&node.modifiers().toString().contains("static"))//check constructor initialization with "Static" keyword
			{
			 
			Clno=cu.getLineNumber(node.getStartPosition());
			//System.out.println(Classname+Clno);
			}
		 else if (node.modifiers().toString().contains("static"))//check variable initialization with "Static" keyword
				
		 {
			// System.out.println(Classname+Clno);
	            node.accept(new ASTVisitor() {	
		 public boolean visit(VariableDeclarationFragment fd) { // Moving inside to get variable
         	 LineList.add(cu.getLineNumber(fd.getStartPosition()));// Get variable initialization position
         	 VarList.add(fd.getName().toString());// Get variable name
          //   System.out.println("in frag: " + fd.getName());
             return true;
         }
     });
	}
 int flag_pub=0;
 int flag_sta=0;
 int flag_fin=0;
 for(int i=0; i<node.modifiers().size();i++){
	 
if(node.modifiers().get(i).toString().equals("public")){
	flag_pub=1;	
	
}
if(node.modifiers().get(i).toString().equals("static")){
	flag_sta=1;	
	
}
if(node.modifiers().get(i).toString().equals("final")){
	flag_fin=1;	
	
}}
if(flag_fin==0&&flag_pub==1&&flag_sta==1){
	System.out.println("     file:" + PrintFname);
      LogFile.println("     file:" + PrintFname);
      LogFile.println("OBJ10-J. Do not use public static nonfinal fieldsError at line "+cu.getLineNumber(node.getStartPosition()));
System.out.println("OBJ10-J. Do not use public static nonfinal fieldsError at line "+cu.getLineNumber(node.getStartPosition()));
Main.obj10j++;
}
 	 
	return true; //  
 	} 
 	 public boolean visit(VariableDeclarationFragment node) {
 		
		 if(node.getParent() instanceof VariableDeclarationStatement){
			 VariableDeclarationStatement declaration = ((VariableDeclarationStatement) node.getParent());
               
            	 
            	 SymList1.add(node.getName().toString());
            	 SymList2.add(declaration.getType().toString());
		 
		 }
		 return true;
		} 
 	 public boolean visit(EnhancedForStatement node) { // Moving inside to get variable
 		 
 		
 		  if(!node.getParameter().modifiers().toString().contains("final")){
 			 
 			 dcl02lno=cu.getLineNumber(node.getStartPosition());
 			 node.accept(new ASTVisitor() {
 		 public boolean visit(SingleVariableDeclaration SVD) { // Moving inside to get variable
 	     	  
 			SingVarDec=SVD.getName().toString();
 	         return false;
 	     }
 		 }); 
 		node.accept(new ASTVisitor() {
 	 		 public boolean visit(Assignment as) { // Moving inside to get variable
 	 	     	  
 	 			 if(as.getLeftHandSide().toString().equals(SingVarDec)){
 	 				    System.out.println("File: " + PrintFname);
 	 	            	System.out.println("DCL02-J. Do not modify the collection's elements during an enhanced for statement. Error at line "+dcl02lno);
 	 	            	try {
			   				
							    
 	 	  				
			   				if (Main.htmlContent=="null"){
			   					String block=	p.parseBlock(PrintFname);
			   					Main.htmlContent = block;	
			   					
			   					String x=	p.parseTable("DCL02-J", "CERT", Integer.toString(dcl02lno),  SingVarDec );
			   					Main.htmlContent = Main.htmlContent.replace("<!--$newtableblockattachhere-->",x);
			   				 
			   					
			   				}
			   				else
			   				{
			   					String x=	p.parseTable("DCL02-J", "CERT", Integer.toString(dcl02lno),  SingVarDec );
			   					Main.htmlContent = Main.htmlContent.replace("<!--$newtableblockattachhere-->",x);
			   				 
			   					}
			   			} catch (IOException e) {
			   				// TODO Auto-generated catch block
			   			
			   				 e.printStackTrace();
			   			} 
 	 	            	
 	 	            	Main.dcl02j++;
 	 			 }
 	 	         return false;
 	 	     }
 	 		 }); }
 		  return true;
     }
	
 
	public boolean visit(WhileStatement stmt) {
		
		String cntn= stmt.getExpression().toString();// Get condition True or False
		String sn=stmt.getBody().toString(); // Get body 
		sn=sn.replace("{",""); 
		sn=sn.replace("}","");
		sn=sn.replaceAll("\\s+","");
		//Removed all { } and whitespace may be a programmer will type "while(true){}" so body becomes {}
//********************************* Constructor invocation node position checking  ********************	
//******** To implement DCL001-J IntraClass allow constructor after all variable declaration**********
		//System.out.println(sn);
		if(cntn.equals("true")&&sn.equals(""))//Checking condition is true and body is empty
			 {
			   
				try {
					
					 
					if (Main.htmlContent=="null"){
						String block=	p.parseBlock(PrintFname);
						Main.htmlContent = block;	
						String x=	p.parseTable("MSC01-J", "CERT", Integer.toString(cu.getLineNumber(stmt.getStartPosition())), "Empty infinite loop");
						Main.htmlContent = Main.htmlContent.replace("<!--$newtableblockattachhere-->",x);
					 
						
					}
					else
					{
						String x=	p.parseTable("MSC01-J", "CERT", Integer.toString(cu.getLineNumber(stmt.getStartPosition())), "Empty infinite loop");
						Main.htmlContent = Main.htmlContent.replace("<!--$newtableblockattachhere-->",x);
					 
						}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				 
				Main.msc01j++;
			 
			 }
			 
			return true; // do not continue to avoid usage info
		}
	public void ruleDcl01() throws IOException{
		//*********************Read Class List*******************************************
				Path filePath = new File("config/javaclass").toPath();
				   Charset charset = Charset.defaultCharset();        
				   List<String> stringList = Files.readAllLines(filePath, charset);
				   String[] stringArray = stringList.toArray(new String[]{});
				   int retVal =0;
		//*******************************************************************************
		//***********************Do not reuse public identifiers from the Java Standard Library(Detection)*********
		//********************************* Comparing Class name with Java Standard Class names********************		
				String searchVal = this.Classname; // typeDec.getName() gives the class name
			    retVal=Arrays.binarySearch(stringArray,searchVal); // binary search using java standard method. If found it will return the index(index>=0)
				if(retVal>=0)//If index greater than or equals zero match found. ie class name is same as a java standard class name. So a warning message for programmer.
				{

  					System.out.println("File: " + PrintFname);
 	 	            System.out.println("DCL01-J. Do not reuse public identifiers from the Java Standard Library Error at Line "+LineNo);	
  				
				  try {
					
					 
					if (Main.htmlContent=="null"){
						String block=	p.parseBlock(PrintFname);
						Main.htmlContent = block;	
						String x=	p.parseTable("DCL01-J", "CERT", Integer.toString(LineNo), searchVal);
						Main.htmlContent = Main.htmlContent.replace("<!--$newtableblockattachhere-->",x);
					 
						
					}
					else
					{
						String x=	p.parseTable("DCL01-J", "CERT", Integer.toString(LineNo), searchVal);
						
						Main.htmlContent = Main.htmlContent.replace("<!--$newtableblockattachhere-->",x);
					 
						}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			    Main.dcl01j++;
				}
		//*********************************************************************************************************
		//*********************************************************************************************************
	
	}
	
	
	//***************************New*****************
	 public boolean visit(ForStatement node) {
		 	if(node.initializers().toString().endsWith("f]")||node.initializers().toString().contains("float") ){
		 		 System.out.println("File: " + PrintFname);
		 	     System.out.println("NUM09-J. Do not use floating-point variables as loop counters Error at line "+cu.getLineNumber(node.getStartPosition()));
		 		 try {
					
					 
					if (Main.htmlContent=="null"){
						String block=	p.parseBlock(PrintFname);
						Main.htmlContent = block;	
						String x=	p.parseTable("NUM09-J", "CERT", Integer.toString(cu.getLineNumber(node.getStartPosition())), "Do not use floating-point variables as loop counters ' "+ node.getExpression()+" ");
						Main.htmlContent = Main.htmlContent.replace("<!--$newtableblockattachhere-->",x);
					 
						
					}
					else
					{
						String x=	p.parseTable("NUM09-J", "CERT", Integer.toString(cu.getLineNumber(node.getStartPosition())), "Empty infinite loop");
						Main.htmlContent = Main.htmlContent.replace("<!--$newtableblockattachhere-->",x);
					 
						}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		 		Main.num09j++;
		 	}
			
			return true;
			
		} 
	 
	
public boolean visit(SimpleName node) {
	if(node.getIdentifier().toString().equals("NaN")){
		  if(node.getParent() instanceof QualifiedName){
				 QualifiedName ql = ((QualifiedName) node.getParent());
	               if(ql.toString().equals("Double.NaN"))
	               {
	            	   System.out.println("File: " + PrintFname);
	 		 	       System.out.println("NUM07-J. Do not attempt comparisons with NaN. Statement. Error at line "+cu.getLineNumber(node.getStartPosition()));
	 		 		Main.num07j++;
	 		 		try {
		
	    
	
	 		 			if (Main.htmlContent=="null"){
	 		 				String block=	p.parseBlock(PrintFname);
	 		 				Main.htmlContent = block;	
			
								String x=	p.parseTable("NUM07-J", "CERT", Integer.toString(cu.getLineNumber(node.getStartPosition())),  node.toString() );
								Main.htmlContent = Main.htmlContent.replace("<!--$newtableblockattachhere-->",x);
							 
								
							}
							else
							{
								String x=	p.parseTable("NUM07-J", "CERT", Integer.toString(cu.getLineNumber(node.getStartPosition())),  node.toString() );
								Main.htmlContent = Main.htmlContent.replace("<!--$newtableblockattachhere-->",x);
							 
								}
						} catch (IOException e) {
							// TODO Auto-generated catch block
						
							 e.printStackTrace();
						} 
	               }}
	}
	return true;
	
} 
 
public boolean visit(ExpressionStatement node) {
	
	if(node.toString().contains(".delete()"))
	{ 
		node.accept(new ASTVisitor() {
 	 		 public boolean visit(SimpleName as) { 
 	 			
 	 			for (int i = 0; i < SymList1.size(); i++) 
		  		{	
 	 				
		  		if(SymList1.contains(as.toString())){
		  			 		 
		  				 
		  				if(SymList2.get(i).toString().equals("File"))
		  				{	 
		  					System.out.println("File: " + PrintFname);
		  			        System.out.println("FIO02-J. Detect and handle file-related errors. Error at Line "+LineNo);	
		  				
 	 			 
		  					try {
		  						
		  						 
		  						if (Main.htmlContent=="null"){
		  							String block=	p.parseBlock(PrintFname);
		  							Main.htmlContent = block;	
		  							String x=	p.parseTable("FIO02-J", "CERT", Integer.toString(cu.getLineNumber(node.getStartPosition())),   node.toString() );
		  							Main.htmlContent = Main.htmlContent.replace("<!--$newtableblockattachhere-->",x);
		  						 
		  							
		  						}
		  						else
		  						{
		  							String x=	p.parseTable("FIO02-J", "CERT", Integer.toString(cu.getLineNumber(node.getStartPosition())), node.toString());
		  							Main.htmlContent = Main.htmlContent.replace("<!--$newtableblockattachhere-->",x);
		  						 
		  							}
		  					} catch (IOException e) {
		  						// TODO Auto-generated catch block
		  						e.printStackTrace();
		  					}
		  			 		Main.fio02j++;
		  				 
		  				}
 	 		  
 	     }}return false;
 		 }});}
	return true;
	
} 
public boolean visit(TryStatement node) {
	 Mil=0;
	 Nil=0;
	 if(node.getFinally()!=null)
	 {	
	node.getFinally().accept(new ASTVisitor() {
		  public boolean visit(BreakStatement sn) { 
			   System.out.println("File: " + PrintFname);
	            System.out.println("ERR04-J. Do not complete abruptly from a finally block. Error at Line "+LineNo);	
			
			  try {
					
					 
					if (Main.htmlContent=="null"){
						String block=	p.parseBlock(PrintFname);
						Main.htmlContent = block;	
						String x=	p.parseTable("ERR04-J", "CERT", Integer.toString(cu.getLineNumber(sn.getStartPosition())),   sn.toString() );
						Main.htmlContent = Main.htmlContent.replace("<!--$newtableblockattachhere-->",x);
					 
						
					}
					else
					{
						String x=	p.parseTable("ERR04-J", "CERT", Integer.toString(cu.getLineNumber(sn.getStartPosition())),   sn.toString() );
						Main.htmlContent = Main.htmlContent.replace("<!--$newtableblockattachhere-->",x);
					 
						}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		 		Main.err04j++;
			 
			 
	      
	  return true;
	  }}); 
	node.getFinally().accept(new ASTVisitor() {
		  public boolean visit(ContinueStatement sn) { 
			 
			  try {
					
					 
					if (Main.htmlContent=="null"){
						String block=	p.parseBlock(PrintFname);
						Main.htmlContent = block;	
						String x=	p.parseTable("ERR04-J", "CERT", Integer.toString(cu.getLineNumber(sn.getStartPosition())),   sn.toString() );
						Main.htmlContent = Main.htmlContent.replace("<!--$newtableblockattachhere-->",x);
					 
						
					}
					else
					{
						String x=	p.parseTable("ERR04-J", "CERT", Integer.toString(cu.getLineNumber(sn.getStartPosition())),   sn.toString() );
						Main.htmlContent = Main.htmlContent.replace("<!--$newtableblockattachhere-->",x);
					 
						}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		 		Main.err04j++;
			 
			 
	      
	  return true;
	  }}); 
	node.getFinally().accept(new ASTVisitor() {
		  public boolean visit(ReturnStatement sn) { 
			 
			  try {
					
					 
					if (Main.htmlContent=="null"){
						String block=	p.parseBlock(PrintFname);
						Main.htmlContent = block;	
						String x=	p.parseTable("ERR04-J", "CERT", Integer.toString(cu.getLineNumber(sn.getStartPosition())),   sn.toString() );
						Main.htmlContent = Main.htmlContent.replace("<!--$newtableblockattachhere-->",x);
					 
						
					}
					else
					{
						String x=	p.parseTable("ERR04-J", "CERT", Integer.toString(cu.getLineNumber(sn.getStartPosition())),   sn.toString() );
						Main.htmlContent = Main.htmlContent.replace("<!--$newtableblockattachhere-->",x);
					 
						}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		 		Main.err04j++;
			 
	      
	  return true;
	  }}); 
	node.getFinally().accept(new ASTVisitor() {
		  public boolean visit(ThrowStatement sn) { 
			 
			  try {
					
					 
					if (Main.htmlContent=="null"){
						String block=	p.parseBlock(PrintFname);
						Main.htmlContent = block;	
						String x=	p.parseTable("ERR04-J", "CERT", Integer.toString(cu.getLineNumber(sn.getStartPosition())),   sn.toString() );
						Main.htmlContent = Main.htmlContent.replace("<!--$newtableblockattachhere-->",x);
					 
						
					}
					else
					{
						String x=	p.parseTable("ERR04-J", "CERT", Integer.toString(cu.getLineNumber(sn.getStartPosition())),   sn.toString() );
						Main.htmlContent = Main.htmlContent.replace("<!--$newtableblockattachhere-->",x);
					 
						}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		 		Main.err04j++;
			 
	      
	  return true;
	  }}); 
	node.getFinally().accept(new ASTVisitor() {
		  public boolean visit(MethodInvocation mi) { 
			 
			  if(!mi.toString().contains("Log.")){
				  if(!mi.toString().contains("System.")){
					  if(!mi.toString().contains("Console.")){
			 Nil=1;
			// System.out.println(mi);
			 met=mi.toString();
			Mlno=cu.getLineNumber(mi.getStartPosition());}}}
	return true;
	}}); 
	node.getFinally().accept(new ASTVisitor() {
		  public boolean visit(TryStatement tr) { 
			  if(tr.getBody().toString().contains(met))	{
					 Mil=1;
				 } 
			  
			  
			  	
	    
	return true;
	}}); 
	if(Mil==0&&Nil==1){
		System.out.println("File: " + PrintFname);
        System.out.println("ERR05-J. Do not let checked exceptions escape from a finally block. Error at Line "+LineNo);	
	
		 try {
				
			 
				if (Main.htmlContent=="null"){
					String block=	p.parseBlock(PrintFname);
					Main.htmlContent = block;	
					String x=	p.parseTable("ERR05-J", "CERT", Integer.toString(Mlno),   met );
					Main.htmlContent = Main.htmlContent.replace("<!--$newtableblockattachhere-->",x);
				 
					
				}
				else
				{
				 	String x=	p.parseTable("ERR05-J", "CERT", Integer.toString(Mlno),   met );
				 	Main.htmlContent = Main.htmlContent.replace("<!--$newtableblockattachhere-->",x);
				 
					}
			} catch (IOException e) {
				// TODO Auto-generated catch block
			
				 e.printStackTrace();
			}
			Main.err05j++;
	 }
	 }
	return true;
	
}
public boolean visit(ThrowStatement node) {
	node.accept(new ASTVisitor() {
		  public boolean visit(ClassInstanceCreation cs) { 
	cs.accept(new ASTVisitor() {
		  public boolean visit(SimpleName sn) { 
				if(sn.toString().equals("RuntimeException")||sn.toString().equals("Exception")||sn.toString().equals("Throwable")) 
		 		{  
					System.out.println("File: " + PrintFname);
			        System.out.println("ERR07-J. Do not throw RuntimeException, Exception, or Throwable. Error at Line "+LineNo);	
				
					 try {
							
						 
							if (Main.htmlContent=="null"){
								String block=	p.parseBlock(PrintFname);
								Main.htmlContent = block;	
								String x=	p.parseTable("ERR07-J", "CERT", Integer.toString(cu.getLineNumber(sn.getStartPosition())),  sn.toString() );
								Main.htmlContent = Main.htmlContent.replace("<!--$newtableblockattachhere-->",x);
							 
								
							}
							else
							{
								// System.out.println("ERR07-J" +"CERT"+ Integer.toString(cu.getLineNumber(sn.getStartPosition()))+  "Ruxception" );
								  
							 	  String x=	p.parseTable("ERR07-J", "CERT", Integer.toString(cu.getLineNumber(sn.getStartPosition())),  sn.toString() );
							   Main.htmlContent = Main.htmlContent.replace("<!--$newtableblockattachhere-->",x);
							 
								}
						} catch (IOException e) {
							// TODO Auto-generated catch block
						
							 e.printStackTrace();
						}
						Main.err07j++;
		 		}
	return true;
	}}); 
	return true;
			}}); 
	 return true;
} 
public boolean visit(CatchClause node) {
	 
	if(node.getException().getType().toString().equals("NullPointerException")){
		System.out.println("File: " + PrintFname);
        System.out.println("ERR08-J. Do not catch NullPointerException or any of its ancestors. Error at Line "+LineNo);	
	
		 try {
				
			 
				if (Main.htmlContent=="null"){
					String block=	p.parseBlock(PrintFname);
					Main.htmlContent = block;	
					String x=	p.parseTable("ERR08-J", "CERT", Integer.toString(cu.getLineNumber(node.getStartPosition())),  node.getException().toString() );
					Main.htmlContent = Main.htmlContent.replace("<!--$newtableblockattachhere-->",x);
				 
					
				}
				else
				{
					// System.out.println("ERR07-J" +"CERT"+ Integer.toString(cu.getLineNumber(sn.getStartPosition()))+  "Ruxception" );
					  
				 	  String x=	p.parseTable("ERR08-J", "CERT", Integer.toString(cu.getLineNumber(node.getStartPosition())),  node.getException().toString() );
				   Main.htmlContent = Main.htmlContent.replace("<!--$newtableblockattachhere-->",x);
				 
					}
			} catch (IOException e) {
				// TODO Auto-generated catch block
			
				 e.printStackTrace();
			}
			Main.err08j++;
	}
	 catchname=node.getException().getName().toString();
	 	
		node.accept(new ASTVisitor() {
			  public boolean visit(MethodInvocation mi) { 
				  String[] matches = new String[] {"Console.printf()",  "System.out.print",  "System.err.print"};

				  if (mi.toString().equals(catchname+".printStackTrace()")||mi.toString().equals(" Throwable.printStackTrace()"))
				  {
					    System.out.println("File: " + PrintFname);
	 	 	            System.out.println("ERR02-J. Prevent exceptions while logging data. Error at Line "+LineNo);	
					  
					  try {
							
							 
							if (Main.htmlContent=="null"){
								String block=	p.parseBlock(PrintFname);
								Main.htmlContent = block;	
								String x=	p.parseTable("ERR02-J", "CERT", Integer.toString(cu.getLineNumber(mi.getStartPosition())),  mi.toString() );
								Main.htmlContent = Main.htmlContent.replace("<!--$newtableblockattachhere-->",x);
							 
								
							}
							else
							{
								// System.out.println("ERR07-J" +"CERT"+ Integer.toString(cu.getLineNumber(sn.getStartPosition()))+  "Ruxception" );
								  
								String x=	p.parseTable("ERR02-J", "CERT", Integer.toString(cu.getLineNumber(mi.getStartPosition())),  mi.toString() );
								 Main.htmlContent = Main.htmlContent.replace("<!--$newtableblockattachhere-->",x);
							 
								}
						} catch (IOException e) {
							// TODO Auto-generated catch block
						
							 e.printStackTrace();
						}
						Main.err02j++;
					  }
				    	
				  for (String s : matches)
				  {
				    if (mi.toString().contains(s))
				    {
				    	mi.accept(new ASTVisitor() {
							  public boolean visit(SimpleName sn) { 
								  if(sn.toString().equals(catchname))
								  {
									    System.out.println("File: " + PrintFname);
					 	 	            System.out.println("ERR02-J. Prevent exceptions while logging data. Error at Line "+cu.getLineNumber(mi.getStartPosition()));	
									  try {
											
											 
											if (Main.htmlContent=="null"){
												String block=	p.parseBlock(PrintFname);
												Main.htmlContent = block;	
												String x=	p.parseTable("ERR02-J", "CERT", Integer.toString(cu.getLineNumber(mi.getStartPosition())),  mi.toString() );
												Main.htmlContent = Main.htmlContent.replace("<!--$newtableblockattachhere-->",x);
											 
												
											}
											else
											{
												// System.out.println("ERR07-J" +"CERT"+ Integer.toString(cu.getLineNumber(sn.getStartPosition()))+  "Ruxception" );
												  
												String x=	p.parseTable("ERR02-J", "CERT", Integer.toString(cu.getLineNumber(mi.getStartPosition())),  mi.toString() );
												 Main.htmlContent = Main.htmlContent.replace("<!--$newtableblockattachhere-->",x);
											 
												}
										} catch (IOException e) {
											// TODO Auto-generated catch block
										
											 e.printStackTrace();
										}
										Main.err02j++;
									  }
								  return true;
								}});
							  }
				      
				    }
				  
				 
		return true;
		}}); 
	 return true;
} 

 
public boolean visit(SynchronizedStatement node) {
    lcStr=node.getExpression().toString();
	cu.accept(new ASTVisitor() {
		public boolean visit(VariableDeclarationFragment vdf) {
		 	if(vdf.getName().toString().equals(lcStr)){
		 		
		 		 LCvdf=vdf.toString();
		 	 
			vdf.accept(new ASTVisitor() {
				public boolean visit(ClassInstanceCreation CiC) {
				 
						if(CiC.getType().toString().equals("Integer")){
							LcFlg=1;
						}
							if(CiC.getType().toString().equals("Object")){
							
								LcFlg=1;
										}
							
								if(CiC.getType().toString().equals("String")&&!LCvdf.endsWith(".intern()")){
									LcFlg=1;
								}
								 
						
						return true; 
			 }}); }
			return true; 
	 }}); 
	
	if(node.getExpression().toString().contains("getClass()"))
	{LcFlg=1;
	}
		if(LcFlg==0){
			    System.out.println("File: " + PrintFname);
		        System.out.println("LCK01-J. Do not synchronize on objects that may be reused. Error at Line "+LineNo);
			try {
				
				 
				if (Main.htmlContent=="null"){
					String block=	p.parseBlock(PrintFname);
					Main.htmlContent = block;	
					String x=	p.parseTable("LCK01-J", "CERT", Integer.toString(cu.getLineNumber(node.getStartPosition())),   node.toString() );
					Main.htmlContent = Main.htmlContent.replace("<!--$newtableblockattachhere-->",x);
				 
					
				}
				else
				{
					String x=	p.parseTable("LCK01-J", "CERT", Integer.toString(cu.getLineNumber(node.getStartPosition())), node.toString());
					Main.htmlContent = Main.htmlContent.replace("<!--$newtableblockattachhere-->",x);
				 
					}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	 		Main.lck01j++;
		}
		if(node.getExpression().toString().contains("getClass()"))
		{
			 System.out.println("File: " + PrintFname);
		        System.out.println("LCK02-J. Do not synchronize on the class object returned by getClass(). Error at Line "+LineNo);
			
			try {
				
				 
				if (Main.htmlContent=="null"){
					String block=	p.parseBlock(PrintFname);
					Main.htmlContent = block;	
					String x=	p.parseTable("LCK02-J", "CERT", Integer.toString(cu.getLineNumber(node.getStartPosition())),   node.toString() );
					Main.htmlContent = Main.htmlContent.replace("<!--$newtableblockattachhere-->",x);
				 
					
				}
				else
				{
					String x=	p.parseTable("LCK02-J", "CERT", Integer.toString(cu.getLineNumber(node.getStartPosition())), node.toString());
					Main.htmlContent = Main.htmlContent.replace("<!--$newtableblockattachhere-->",x);
				 
					}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	 		Main.lck02j++;
		}

		return true;
		}
	}
 
	 


 
