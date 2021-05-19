package com.benbeehler.vsabot.resource;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDateTime;

import com.benbeehler.vsabot.VSABot;
import com.benbeehler.vsabot.schoology.SchoologyManager;
import com.benbeehler.vsabot.utilities.Parser;
import com.benbeehler.vsabot.utilities.ProcessHandler;

public class BotScheduler {

	public static void beginScheduler() {
    	ProcessHandler.spawn(() -> {    		
    		while(true) {
	    		check(8, 15);
    		}
    	});	
	}
	
	public static void check(int start, int end) {
		LocalDateTime now = LocalDateTime.now(); 
		String today = now.getDayOfWeek().name();
		
		try {
			int hour = Parser.getNISTHourET();

    		if(today.equalsIgnoreCase("sunday")) {
	    		if(hour >= 5 && hour < 23) {
	    			if(!Reference.isDisabled()) {
						SchoologyManager.closeAll();
							
						Reference.getUpdatesChannel().sendMessage(MessageLib.CLOSED_DISCUSSIONS).complete();
						
	    				Reference.setDisabled(true);
	    			}
	    		} else {
	    			if(Reference.isDisabled()) {
						SchoologyManager.openAll();
							
						Reference.getUpdatesChannel().sendMessage(MessageLib.OPENED_DISCUSSIONS).complete();
						
	    				Reference.setDisabled(false);
	    			}
	    		}
    		} else {
	    		if(!(today.equalsIgnoreCase("friday") 
	    				|| today.equalsIgnoreCase("saturday"))) {
		    		
		    		//now.atZone();
		    		if(hour >= start && hour < end) {			    			
		    			if(!Reference.isDisabled()) {
							SchoologyManager.disableAll();
								
							Reference.getUpdatesChannel().sendMessage(MessageLib.DISABLED_CREATION).complete();
							
		    				Reference.setDisabled(true);
		    			}
		    		} else {
		    			if(Reference.isDisabled()) {
							SchoologyManager.enableAll();
								
							Reference.getUpdatesChannel().sendMessage(MessageLib.ENABLED_CREATION).complete();
							
		    				Reference.setDisabled(false);
		    			}
		    		}
	    		}
    		}
    		
    		if(now.getHour() == 23) {
    			if(now.getMinute() == 50 || now.getMinute() == 51 || now.getMinute() == 52 || now.getMinute() == 33 || now.getMinute() == 54) {
    				BotStatistics.reset();
    			}
    		}
		
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
		
		try {
			//Check every 3 minutes (60 x 3)
			Thread.sleep(ProcessHandler.seconds(60 * 3));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
