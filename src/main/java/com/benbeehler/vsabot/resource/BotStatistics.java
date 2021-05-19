package com.benbeehler.vsabot.resource;

public class BotStatistics {

	private static int todayComments = 0;
	private static int todayDiscussions = 0;
	private static int todayReports = 0;
	private static int todayDeleted = 0;
	private static int todayEuphemisms = 0;
	private static int todaySwears = 0;
	private static int todayModComments = 0;
	
	public static void reset() {
		todayComments = 0;
		todayDiscussions = 0;
		todayReports = 0;
		todayDeleted = 0;
		todayEuphemisms = 0;
		todaySwears = 0;
		todayModComments = 0;
	}

	public static int getTodayComments() {
		return todayComments;
	}

	public static void addTodayComments() {
		BotStatistics.todayComments++;
	}

	public static int getTodayDiscussions() {
		return todayDiscussions;
	}

	public static void addTodayDiscussions() {
		BotStatistics.todayDiscussions++;
	}

	public static int getTodayReports() {
		return todayReports;
	}

	public static void addTodayReports() {
		BotStatistics.todayReports++;
	}

	public static int getTodayDeleted() {
		return todayDeleted;
	}

	public static void addTodayDeleted() {
		BotStatistics.todayDeleted++;
	}

	public static int getTodayEuphemisms() {
		return todayEuphemisms;
	}

	public static void addTodayEuphemisms() {
		BotStatistics.todayEuphemisms++;
	}

	public static int getTodaySwears() {
		return todaySwears;
	}

	public static void addTodaySwears() {
		BotStatistics.todaySwears++;
	}

	public static int getTodayModComments() {
		return todayModComments;
	}

	public static void addTodayModComments() {
		BotStatistics.todayModComments++;
	}	
}
