package securecoding;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.RuleBasedCollator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
 
import javax.swing.text.Document;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.Assignment;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.EnhancedForStatement;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.ImportDeclaration;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.Statement;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;
import org.eclipse.jdt.core.dom.WhileStatement;
import org.eclipse.jdt.core.dom.rewrite.ASTRewrite;
import org.eclipse.jdt.core.dom.rewrite.ListRewrite;

class MyQuickFix extends ASTVisitor{
	CompilationUnit cu;
	String source;
	String PrintFname;
	String Classname;
	int LineNo,dcl02lno;
	int Clno=0;
	int Lastlno=0;
	String SingVarDec=null;
	
	int pos=0;
	AST ast;
	ASTRewrite rewriter;
	ArrayList<Integer> LineList = new ArrayList<Integer>();
	ArrayList<String> VarList = new ArrayList<String>();
	ArrayList<String> ArrList = new ArrayList<String>();
	 
	  int Flg=0;
	Scanner scanner = new Scanner(System.in);
    
	public MyQuickFix(CompilationUnit cu, String source, String printFname,AST ast) throws IOException {
		super();
		//System.out.println("class here "+cu);
		this.cu = cu;
		this.source = source;
		this.PrintFname=printFname;
		this.ast=ast;
	//	this.rewriter = ASTRewrite.create(ast);
	}
	public boolean visit(ImportDeclaration node) {
		if(Main.RuleInfo.equals("MSC02J")||Main.RuleInfo.equals("All")){
        	
        		if(node.getName().toString().equals("java.util.Random"))
        			{	
        			System.out.println("     file:" + PrintFname);
        			System.out.println("MSC02-J. Generate strong random numbers. Error at line  '" +cu.getLineNumber(node.getStartPosition()));								    
        			System.out.println(node);
	     			System.out.println("**********************************************************************************************************");
	     			System.out.println(" Package name '"+node.getName()+ "' This class produces an identical sequence of numbers for each given seed value. Consequently, the sequence of numbers is predictable.");
	     			System.out.println("The solution is use the java.security.SecureRandom class to produce high-quality random numbers:");
	     			System.out.println("Do you want to continue (Y/N) ?");
	     			String option=scanner.nextLine();
	     			if(option.equals("Y")||option.equals("y"))
	   			   {
	     				 
        			}
        			}
	}
		return true;
	}
	public boolean visit(TypeDeclaration node) {
		Clno=0;
		// It is useful if multiple classes are present in a single .java file			
			SimpleName name=node.getName(); // Gives class name of current visiting node
			this.Classname=name.toString(); // Saving to a global variable for many other function
			this.LineNo=cu.getLineNumber(name.getStartPosition());// Line number saving to a global variable. It is useful for giving error message
			 // TODO Auto-generated catch block
			 
		
				
			node.accept(new ASTVisitor() {
	            public boolean visit(TypeDeclaration nm) {
	            	if(Main.RuleInfo.equals("SER05J")||Main.RuleInfo.equals("All")){
	            	if(nm.superInterfaceTypes().toString().equals(node.superInterfaceTypes().toString()) &&!nm.superInterfaceTypes().toString().equals("[]")&&!node.superInterfaceTypes().toString().equals("[]")&&cu.getLineNumber(nm.getStartPosition())!=cu.getLineNumber(node.getStartPosition())&&!node.modifiers().toString().contains("static")&&!nm.modifiers().toString().contains("static"))
	            	{   
	            		
	                	 System.out.println("SER05-J. Do not serialize instances of inner classes. Error at line  '" +cu.getLineNumber(nm.getStartPosition())+"' Outer class name '"+node.getName()+ "' Inner class name '"+nm.getName()+ "' Instances name"+node.superInterfaceTypes().toString());								    
	            		System.out.println("**********************************************************************************************************");
	            		 System.out.println(nm);
	     			    System.out.println("**********************************************************************************************************");
	     			    System.out.println("Serializing an inner class declared in a non-static context that contains implicit non-transient references to enclosing class instances results in serialization of its associated outer class instance.");
	     			    System.out.println("The new code will add ' Thread.yield();' to the empty while");
	     			    System.out.println("Do you want to continue (Y/N) ?");
	     			   String option=scanner.nextLine();
	     			  if(option.equals("Y")||option.equals("y"))
	   			   {
	     				   ASTRewrite rewrite = ASTRewrite.create(cu.getAST()); // unit instance of CompilationUnit
	     				// ...			
	     				   
	     				 
	   			   }
	            		}
	            	}
	            	return true;
	            }}); 
			
			return true;
			
		}
	public boolean visit(WhileStatement stmt) {
		if(Main.RuleInfo.equals("MSC01J")||Main.RuleInfo.equals("All")){
		Statement by = stmt.getBody();
		String cntn= stmt.getExpression().toString();// Get condition True or False
		String sn=stmt.getBody().toString(); // Get body 
		sn=sn.replace("{",""); 
		sn=sn.replace("}","");
		sn=sn.replaceAll("\\s+","");
		if(cntn.equals("true")&&sn.equals(""))//Checking condition is true and body is empty
			 {
			    System.out.println("MSC01-J. Do not use an empty infinite loop. Error at line "+cu.getLineNumber(stmt.getStartPosition()));
			    System.out.println("**********************************************************************************************************");
			    System.out.println(stmt);
			    System.out.println("**********************************************************************************************************");
			    System.out.println("An infinite loop with an empty body consumes CPU cycles but does nothing. According to CERT standard programs must not include infinite loops with empty bodies.");
			    System.out.println("The new code will add ' Thread.yield();' to the empty while");
			    System.out.println("Do you want to continue (Y/N) ?");
			   String option=scanner.nextLine();
			   if(option.equals("Y")||option.equals("y"))
			   {
			    PrintWriter out;
				/*try {
					stmt.setBody(statement);
				 
					ListRewrite listRewrite = rewriter.getListRewrite(block, Block.STATEMENTS_PROPERTY);
					Statement placeHolder = (Statement) rewriter.createStringPlaceholder("//mycomment", ASTNode.EMPTY_STATEMENT);
					listRewrite.insertFirst(placeHolder, null);
				 
					TextEdit edits = rewriter.rewriteAST();
				 
					// apply the text edits to the compilation unit
					Document document = new Document(cu.getSource());
				 
					edits.apply(document);
				 
					// this is the code for adding statements
					cu.getBuffer().setContents(document.get());
				 
					System.out.println("done");
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}*/
			    
				
			 
			 }
			 }}
			return true; // do not continue to avoid usage info
		}
	public boolean visit(VariableDeclarationStatement vds) { // Moving inside to get variable
		  if(vds.modifiers().toString().equals("[]")){
			  vds.accept(new ASTVisitor() {
			  public boolean visit(SimpleName sn) { // Moving inside to get variable
				     ArrList.add(sn.getIdentifier());
			         return true;
			     }});}
		  
	         return true;
	     }
public boolean visit(MethodInvocation node) {
	//System.out.println(Main.RuleInfo);
	if(Main.RuleInfo.equals("EXP02J")||Main.RuleInfo.equals("All")){
        //  System.out.println(Main.RuleInfo);
		  SimpleName sn=node.getName();
		 Flg=0;
		// System.out.println("here "+node);
		  if(node.toString().contains(".equals")&&!node.toString().contains("Arrays"))
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
			  System.out.println("EXP02-J. Do not use the Object.equals() method to compare two arrays. Error at "+cu.getLineNumber(node.getStartPosition()));
			    System.out.println("**********************************************************************************************************");
			    System.out.println(node.toString());
			    System.out.println("**********************************************************************************************************");
			//    System.out.println("An infinite loop with an empty body consumes CPU cycles but does nothing. According to cert standard programs must not include infinite loops with empty bodies.");
			    System.out.println("According to CERT standard there are two solutions");
			    System.out.println("1. The solution is compares the content of two arrays using the two-argument Arrays.equals() method");
			    System.out.println("2. The solution is compares the array references using the reference equality operators ==:");
			    System.out.println("Do you want to continue (Y/N) ?");
			   String option=scanner.nextLine();
			   if(option.equals("Y")||option.equals("y"))
			   {
				   System.out.println("Not implemented");
				   
			 }}
 	}
		return true;
		  
	  } 
	 
		//*********************************************************************************************************
		//*********************************************************************************************************
	
	}
	 
	 

