package com.benbeehler.vsabot.resource;

import com.wolfram.alpha.WAEngine;
import com.wolfram.alpha.WAException;
import com.wolfram.alpha.WAPlainText;
import com.wolfram.alpha.WAPod;
import com.wolfram.alpha.WAQuery;
import com.wolfram.alpha.WAQueryResult;
import com.wolfram.alpha.WASubpod;

public class WolframAPI {

    public static String query(String q) {
        WAEngine engine = new WAEngine();
        
        engine.setAppID(Reference.getWolframToken());
        engine.addFormat("plaintext");

        WAQuery query = engine.createQuery();
        
        query.setInput(q);
        
        try {
            WAQueryResult queryResult = engine.performQuery(query);
            
            if (queryResult.isError()) {
            } else if (!queryResult.isSuccess()) {
                System.out.println("Query was not understood; no results available.");
            } else {
            	StringBuilder result = new StringBuilder();
            	
                for (WAPod pod : queryResult.getPods()) {
                    if (!pod.isError()) {
                        for (WASubpod subpod : pod.getSubpods()) {
                            for (Object element : subpod.getContents()) {
                                if (element instanceof WAPlainText) {     
                                	result.append("<br><b>" + pod.getTitle() + "</b><br>");
                                    result.append(((WAPlainText) element).getText() + "<br>");
                                }
                            }
                        }
                    }
                }
                
                return result.toString();
            }
        } catch (WAException e) {
            e.printStackTrace();
        }
        
        return "I do not know the answer.";
    }
	
}
