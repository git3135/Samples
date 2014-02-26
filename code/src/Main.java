import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.eclipse.jdt.internal.compiler.ast.CompilationUnitDeclaration;
import org.eclipse.jdt.internal.compiler.classfmt.ClassFileConstants;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.eclipse.jdt.internal.core.util.CodeSnippetParsingUtil;

import com.google.gwt.dev.javac.CompilationUnit;

public class Main {
 
	//use ASTParse to parse string
	public static void parse(String str) {
		
		com.google.gwt.dev.javac.JavaSourceParser parser;
		//com.google.gwt.dev.javac.JavaSourceParser.
		//SafeASTParser parser = SafeASTParser.newParser(AST.JLS3);
		//parser.setSource(str.toCharArray());
		//parser.setKind(ASTParser.K_COMPILATION_UNIT);
 
		final CompilationUnit cu = null; //(CompilationUnit) parser.createAST(null);

		// private static CompilationUnitDeclaration parseJava(String javaSource) {
			    CodeSnippetParsingUtil parsingUtil = new CodeSnippetParsingUtil();
			    CompilerOptions options = new CompilerOptions();
			    options.complianceLevel = ClassFileConstants.JDK1_5;
			    options.sourceLevel = ClassFileConstants.JDK1_5;
			    CompilationUnitDeclaration unit = parsingUtil.parseCompilationUnit(
			        "public class".toString().toCharArray(), options.getMap(), true);
			    if (unit.compilationResult().hasProblems()) {
			      //return null;
			    }
			    //return unit;
			  //}
			    //unit.
	}
 
	//read file content into a string
	public static String readFileToString(String filePath) throws IOException {
		StringBuilder fileData = new StringBuilder(1000);
		BufferedReader reader = new BufferedReader(new FileReader(filePath));
 
		char[] buf = new char[10];
		int numRead = 0;
		while ((numRead = reader.read(buf)) != -1) {
			System.out.println(numRead);
			String readData = String.valueOf(buf, 0, numRead);
			fileData.append(readData);
			buf = new char[1024];
		}
 
		reader.close();
 
		return  fileData.toString();	
	}
 
	//loop directory to get file list
	public static void ParseFilesInDir() throws IOException{
		File dirs = new File(".");
		String dirPath = dirs.getCanonicalPath() + File.separator+"src"+File.separator;
 
		File root = new File(dirPath);
		//System.out.println(rootDir.listFiles());
		File[] files = root.listFiles ( );
		String filePath = null;
 
		 for (File f : files ) {
			 filePath = f.getAbsolutePath();
			 if(f.isFile()){
				 parse(readFileToString(filePath));
			 }
		 }
	}
 
	public static void main(String[] args) throws IOException {
		ParseFilesInDir();
	}
}