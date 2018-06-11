package uniandes.tsdl.mutapk.helper;


import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;

import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.TokenSource;
import org.antlr.runtime.tree.CommonTree;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;

import uniandes.tsdl.antlr.smaliParser;
import uniandes.tsdl.jflex.smaliFlexLexer;
import uniandes.tsdl.mutapk.detectors.code.visitors.ClassInstanceVisitor;
import uniandes.tsdl.mutapk.detectors.code.visitors.MethodCallVO;
import uniandes.tsdl.mutapk.detectors.code.visitors.MethodCallVisitor;
import uniandes.tsdl.mutapk.detectors.code.visitors.MethodDeclarationVO;
import uniandes.tsdl.mutapk.detectors.code.visitors.MethodDeclarationVisitor;
import uniandes.tsdl.smali.LexerErrorInterface;

public class ASTHelper {

	public static CommonTree getAST(String sourcePath) {

		FileInputStream fis = null;
		File smaliFile = new File(sourcePath);
		CommonTree t = null;
		try {
			fis = new FileInputStream(smaliFile);
			InputStreamReader reader = new InputStreamReader(fis, "UTF-8");

			LexerErrorInterface lexer = new smaliFlexLexer(reader);
			((smaliFlexLexer)lexer).setSourceFile(smaliFile);
			// System.out.println(((smaliFlexLexer)lexer).nextToken().getText());
			CommonTokenStream tokens = new CommonTokenStream((TokenSource)lexer);
			tokens.getTokens();
			smaliParser parser = new smaliParser(tokens);
			// parser.setVerboseErrors(options.verboseErrors);
			// parser.setAllowOdex(options.allowOdexOpcodes);
			// parser.setApiLevel(options.apiLevel);

			smaliParser.smali_file_return result = parser.smali_file();
			t = result.getTree();
			return t;
		} catch (Exception e){
			e.printStackTrace();
		}
		return t;
	}

	//	public static HashSet<MethodCallVO> getMethodCallsFromCU(CompilationUnit cu, HashSet<String> targetCalls){
	//		MethodCallVisitor mcVisitor = new MethodCallVisitor();
	//		mcVisitor.setTargetCalls(targetCalls);
	//		cu.accept(mcVisitor);
	//		return  mcVisitor.getCalls();
	//	}
	//
	//	public static HashSet<MethodDeclarationVO> getMethodDeclarationsFromCU(CompilationUnit cu, HashSet<String> targetDeclarations){
	//		MethodDeclarationVisitor mdVisitor = new MethodDeclarationVisitor(targetDeclarations);
	//		cu.accept(mdVisitor);
	//		return mdVisitor.getDeclarations();
	//	}
	//
		
	public static int[] isValidLocation(CommonTree t){
		if(t.getType()==159 
				&& t.getFirstChildWithType(smaliParser.I_REGISTER_LIST).getChildCount()==3 
				&& t.getFirstChildWithType(smaliParser.CLASS_DESCRIPTOR).toString().equals("Landroid/content/Intent;") 
				&& t.getFirstChildWithType(smaliParser.SIMPLE_NAME).toString().equals("<init>")){
			return new int[]{2,6};
		} 
//		else if (t.getType()==159 
//				&& t.getFirstChildWithType(smaliParser.I_REGISTER_LIST).getChildCount()==3 
//				&& t.getFirstChildWithType(smaliParser.CLASS_DESCRIPTOR).toString().equals("Landroid/content/Intent;") 
//				&& t.getFirstChildWithType(smaliParser.SIMPLE_NAME).toString().equals("putExtra")){
//			return new int[]{4,7};
//		} else if(false){//HttpClient.execute
//			return new int[]{13,20};
//		} else if(false){//14 HttpConnectionParams.setConnectionTimeout
//			return new int[]{14};
//		} else if(false){//BluetoothAdapter.isEnabled
//			return new int[]{15};
//		} else if(false){//BluetoothAdapter.getDefaultAdapter
//			return new int[]{16};
//		} else if(false){//URI.<init>
//			return new int[]{17};
//		} else if(false){//Location.<init>
//			return new int[]{18};	
//		} else if(false){//Date.<init>
//			return new int[]{19};	
//		} else if(false){//Cursor.close
//			return new int[]{23};	
//		} else if(false){//SQLiteDatabase.rawQuery
//			return new int[]{24,25};	
//		} else if(false){//Activity.findViewById
//			return new int[]{26,27,31};	
//		} else if(false){//View.OnClickListener
//			return new int[]{30,36};	
//		} else if(false){//File.<init>
//			return new int[]{32};	
//		} else if(false){//FileChannel.close,InputStream.close,BufferedInputStream.close,ByteArrayInputStream.close,DataInputStream.close,FilterInputStream.close,ObjectInputStream.close,PipedInputStream.close,SequenceInputStream.close,StringBufferInputStream.close
//			return new int[]{33};	
//		} else if(false){//Bitmap.createScaledBitmap
//			return new int[]{35};	
//		} else if(false){//OutputStream.close,ByteArrayOutputStream.close,FileOutputStream.close,FilterOutputStream.close,ObjectOutputStream.close,PipedOutputStream.close,BufferedOutputStream.close,PrintStream.close,DataOutputStream.close
//			return new int[]{37};	
//		}
		return new int[]{-1};
	}




}