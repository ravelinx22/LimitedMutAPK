package uniandes.tsdl.mutapk.operators.activity;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.antlr.runtime.tree.CommonTree;

import uniandes.tsdl.mutapk.helper.FileHelper;
import uniandes.tsdl.mutapk.helper.Helper;
import uniandes.tsdl.mutapk.helper.StringGenerator;
import uniandes.tsdl.mutapk.model.location.ASTMutationLocation;
import uniandes.tsdl.mutapk.model.location.MutationLocation;
import uniandes.tsdl.mutapk.operators.MutationOperator;

public class InvalidKeyIntentPutExtra implements MutationOperator{

	public boolean performMutation(MutationLocation location, BufferedWriter writer, int mutantIndex) throws IOException {
		
		ASTMutationLocation mLocation = (ASTMutationLocation) location;
        CommonTree parent = (CommonTree) mLocation.getTree().getParent();
//        System.out.println(parent.toStringTree());
        List<CommonTree> hijos = (List<CommonTree>)parent.getChildren();
        String extraKeyVar = hijos.get(1).getChild(1).toString();
        CommonTree method = (CommonTree) parent.getParent();
        int putExtraPos = parent.getChildIndex();
        int extraKeyValPos = 0;
        boolean finished = false;
        for (int i = putExtraPos; i >-1 && !finished; i--) {
        	CommonTree temp = (CommonTree) method.getChild(i);
        	if(temp.getType()==140 && temp.getChild(1).toString().equals(extraKeyVar)) {
        		extraKeyValPos = temp.getLine();
        		finished = true;
        	}
		}
        String newKey = StringGenerator.generateRandomString();
        
        List<String> newLines = new ArrayList<String>();
        List<String> lines = FileHelper.readLines(location.getFilePath());
        
 		//Add lines before the MutationLocation
 		for(int i=0; i < extraKeyValPos-1; i++){
 			newLines.add(lines.get(i));
 		}
 		
 		//Apply mutation
 		
 		newLines.add("    const-string "+extraKeyVar+", \""+newKey+"\"");
 		newLines.add("");
 		
 		for(int i=extraKeyValPos; i < lines.size() ; i++){
 			newLines.add(lines.get(i));
 		}
 		
// //		
 //		int[] mutatedlines = new int[]{intentInstanceLine, contextInstanceLine, classComponentLine, mLocation.getLine()};
 //		String mutatedLines = "{ ";
 //		for (int i = 0; i < mutatedlines.length; i++) {
 //			mutatedLines += mutatedlines[i]+", ";
 //		}
 //		mutatedLines = mutatedLines.substring(0,mutatedLines.length()-2)+" }";
 		FileHelper.writeLines(location.getFilePath(), newLines);
 		Helper.mutationSuccess(mutantIndex);
 		Helper.writeBasicLogInfo(mutantIndex, location.getFilePath(), location.getType().getName(), new int[] {extraKeyValPos}, writer);
 		writer.write("	For mutant "+mutantIndex+" extra key value at line "+extraKeyValPos+" has been set to "+newKey);
 		writer.newLine();
 		writer.flush();
		return true;
	}

}
