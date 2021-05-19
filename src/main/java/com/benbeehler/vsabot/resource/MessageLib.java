package com.benbeehler.vsabot.resource;

public class MessageLib {

	public static final String NEW_DISCUSSION_WELCOME = "Hello,<br>"
			+ "I am <b>VSABot</b> and I assist the Student Moderators by automating much of the busy work associated with running discussions. "
			+ "Please ensure that your behavior follows a Christ-like conduct by reviewing the official guidelines at "
			+ "<a href='https://docs.google.com/document/d/1iNqC2Mln0CQgI4w6yXDGp4c9_ptnzK_c2T-XRT_tiCc/edit?usp=sharing'>this link</a>.<br><br>"
			+ "Thank you,<br>"
			+ "<i>VSABot 2.x</i><br>"
			+ "<br>"
			+ "<i>I'm just a bot. My goal is to improve discussions. :)</i>";
	
	public static final String NEW_DISCUSSION_WELCOME_MEME = "My name is Dante and I will find you. I am coming for you, 10-12.";
	
	//public static final String NEW_DISCUSSION_WELCOME = "The remaining Jedi will be hunted down and defeated! Any collaborators will suffer the same fate. These have been trying times, but we have passed the test. The attempt on my life has left me scarred and deformed, but I assure you my resolve has never been stronger. The war is over. The Separatists have been defeated, and the Jedi rebellion has been foiled. We stand on the threshold of a new beginning. In order to ensure our security and continuing stability, the Republic will be reorganized into the first Galactic Empire, for a safe and secure society, which I assure you will last for ten thousand years.";*/
	
	public static final String NEW_DEBATE_WELCOME = "<b>Hello Everyone</b>.<br>"
			+ "<b>Debates</b> are <i>designed</i> to foster a rational and logical discussion between opposing viewpoints.<br>"
			+ "It is not acceptable to berate your peers and/or lose your sense of rationality over a simple conversation.<br>"
			+ "Respect is critical. Please take all of these recommendations to heart before posting a comment.<br>"
			+ "<br>"
			+ "Thank You,<br>"
			+ "<i>Mod Team</i><br>"
			+ "<br>"
			+ "<i>I'm just a bot. My goal is to improve discussions. :)</i>";
	
	public static final String EUPHEMISM_RESPONSE = "Your comment contains a euphemism and violates Student Commons guidelines. Please, edit your comment.";
	
	public static final String MODERATE_COMMENT_RESPONSE = "Your comment may contain content that violates Student Commons guidelines. Please, edit your comment.";
	
	public static final String HARSH_COMMENT_RESPONSE = "Your comment has an extremely likely chance of violating Student Commons guidelines. Please, edit your comment.";
	
	public static String HELP_MESSAGE = "Hello! I am VSABot and I help the Student Moderators.<br>"
			+ "<br>"
			+ "<b>Commands</b><br>"
			+ "@help -> This command issues a help message<br>"
			+ "@ping -> This command pings the bot<br>"
			+ "@query [query] -> This queries wolfram alpha<br>"
			+ "@bible [Book Chapter:Verse] -> This provides a Bible verse<br>"
			+ "@verse -> This command sends a motivational Bible verse<br>"
			+ "<br>"
			+ "You can review the Student Commons guidelines at <a href='https://docs.google.com/document/d/1iNqC2Mln0CQgI4w6yXDGp4c9_ptnzK_c2T-XRT_tiCc/edit?usp=sharing'>this link.</a>";
	
	public static String DISABLED_CREATION = "```"
			+ "Update\n"
			+ "According to schedule, VSABot has DISABLED the creation of new discussions. Discussion creation will be re-enabled automatically at the end of Quiet Hours.\n"
			+ "```";
	
	public static String ENABLED_CREATION = "```"
			+ "Update\n"
			+ "According to schedule, VSABot has ENABLED the creation of new discussions.\n"
			+ "```";
	
	public static String CLOSED_DISCUSSIONS = "```"
			+ "Update\n"
			+ "According to routine, VSABot has CLOSED discussions. They will be re-enabled when authorized.\n"
			+ "```";
	
	public static String OPENED_DISCUSSIONS = "```"
			+ "Update\n"
			+ "According to routine, VSABot has OPENED discussions.\n"
			+ "```";
}
