package com.benbeehler.vsabot.commands.instances;

import com.benbeehler.vsabot.commands.Command;
import com.benbeehler.vsabot.commands.CommandInformation;
import com.benbeehler.vsabot.commands.CommandType;
import com.benbeehler.vsabot.utilities.PerspectiveAPI;
import com.benbeehler.vsabot.utilities.WordArchive;

import java.io.IOException;
import java.util.List;

public class RateCommand extends Command {

	public RateCommand(String parametersString, List<String> params, CommandInformation info) {
		super(parametersString, params, info);
		
		this.prefix = "rate";
		
		this.runnable = () -> {
			if(info.getType().equals(CommandType.DISCORD)) {
				if(params.size() >= 2) {
					String rateType = params.get(0);
					String compared = parametersString.replaceFirst(prefix, "").trim();
					
					if(rateType.equals("euphemism")) {
						boolean b = WordArchive.isNaughty(compared);
						
						info.getEvent().getChannel().sendMessage("Euphemism: " + b).complete();
					} else if(rateType.equals("numerical")) {
						try {
							double rating = PerspectiveAPI.rate(compared);
							
							info.getEvent().getChannel().sendMessage("Numerical percentage: " + rating + "%").complete();
						} catch (IOException e) {
							e.printStackTrace();
						}						
					} else if(rateType.equals("swear")) {
						boolean b = WordArchive.isSwear(compared);
						
						info.getEvent().getChannel().sendMessage("Swear: " + b).complete();
					} 
				}
			}
		};
	}
	
}
