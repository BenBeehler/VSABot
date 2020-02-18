package com.benbeehler.vsabot.resource;

import java.time.LocalDateTime;

import com.benbeehler.vsabot.schoology.SchoologyManager;
import com.benbeehler.vsabot.utilities.ProcessHandler;

public class BotScheduler {

	public static void beginScheduler() {
    	ProcessHandler.spawn(() -> {
    		while(true) {
	    		LocalDateTime now = LocalDateTime.now(); 
	    		String today = now.getDayOfWeek().name();
	    			    		
	    		if(today.equalsIgnoreCase("sunday")) {
	    			int hour = now.getHour();
	    			
		    		if(hour >= 5 && hour < 23) {
		    			if(!Reference.isDisabled()) {
							SchoologyManager.closeAll();
								
							Reference.getUpdatesChannel().sendMessage(Message.CLOSED_DISCUSSIONS).complete();
							
		    				Reference.setDisabled(true);
		    			}
		    		} else {
		    			if(Reference.isDisabled()) {
							SchoologyManager.openAll();
								
							Reference.getUpdatesChannel().sendMessage(Message.OPENED_DISCUSSIONS).complete();
							
		    				Reference.setDisabled(false);
		    			}
		    		}
	    		} else {
		    		if(!(today.equalsIgnoreCase("friday") 
		    				|| today.equalsIgnoreCase("saturday"))) {
			    		int hour = now.getHour();
			    		
			    		//now.atZone();
			    		if(hour >= 9 && hour < 16) {			    			
			    			if(!Reference.isDisabled()) {
								SchoologyManager.disableAll();
									
								Reference.getUpdatesChannel().sendMessage(Message.DISABLED_CREATION).complete();
								
			    				Reference.setDisabled(true);
			    			}
			    		} else {
			    			if(Reference.isDisabled()) {
								SchoologyManager.enableAll();
									
								Reference.getUpdatesChannel().sendMessage(Message.ENABLED_CREATION).complete();
								
			    				Reference.setDisabled(false);
			    			}
			    		}
		    		}
	    		}
	    		
	    		try {
	    			//Check every 3 minutes (60 x 3)
					Thread.sleep(ProcessHandler.seconds(60 * 3));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
    		}
    	});	
	}
	
}
