/*
 *   This file is part of Skript.
 *
 *  Skript is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  Skript is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with Skript.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * 
 * Copyright 2011, 2012 Peter Güttinger
 * 
 */

package ch.njol.skript.log;

import java.util.Collection;
import java.util.Deque;
import java.util.LinkedList;
import java.util.logging.Level;

import org.bukkit.Bukkit;

import ch.njol.skript.Skript;
import ch.njol.skript.config.Node;

/**
 * @author Peter Güttinger
 */
public abstract class SkriptLogger {
	
	private static Node node = null;
	
	private static Verbosity verbosity = Verbosity.NORMAL;
	
	static boolean debug;
	
	private final static Deque<SubLog> subLogs = new LinkedList<SubLog>();
	
	private static int numErrors = 0;
	
	/**
	 * Starts a sub log. All subsequent log messages will be added to this log and not printed.<br>
	 * This should be used like this:
	 * 
	 * <pre>
	 * SubLog log = SkriptLogger.startSubLog();
	 * doSomethingThatLogsMessages();
	 * log.stop();
	 * // do something with the logged messages or ignore them
	 * </pre>
	 * 
	 * @return a newly created sublog
	 */
	public final static SubLog startSubLog() {
		final SubLog subLog = new SubLog();
		subLogs.addLast(subLog);
		return subLog;
	}
	
	public final static void stopSubLog(final SubLog log) {
		if (!subLogs.contains(log))
			return;
		if (subLogs.removeLast() != log) {
			int i = 1;
			while (subLogs.removeLast() != log)
				i++;
			Bukkit.getLogger().severe("[Skript] " + i + " sub log(s) was/were not stopped properly!");
		}
	}
	
	public static void setVerbosity(final Verbosity v) {
		verbosity = v;
		if (v.compareTo(Verbosity.DEBUG) >= 0)
			debug = true;
	}
	
	public static void setNode(final Node node) {
		SkriptLogger.node = node == null || node.getParent() == null ? null : node;
	}
	
	public static Node getNode() {
		return node;
	}
	
	/**
	 * Logging should be done like this:
	 * 
	 * <pre>
	 * if (Skript.logNormal())
	 * 	Skript.info(&quot;this information is displayed on verbosity normal or higher&quot;);
	 * </pre>
	 * 
	 * @param level
	 * @param message
	 * 
	 * @see Skript#info(String)
	 * @see Skript#warning(String)
	 * @see Skript#error(String)
	 * @see Skript#logNormal()
	 * @see Skript#logHigh()
	 * @see Skript#logVeryHigh()
	 * @see Skript#debug()
	 */
	public static void log(final Level level, final String message) {
		log(new LogEntry(level, message, node));
	}
	
	public static void log(final LogEntry entry) {
		if (!subLogs.isEmpty()) {
			subLogs.getLast().log.add(entry);
		} else {
			Bukkit.getLogger().log(entry.getLevel(), "[Skript] " + entry.getMessage());
			if (entry.getLevel() == Level.SEVERE)
				numErrors++;
		}
	}
	
	public static void logAll(final Collection<LogEntry> entries) {
		if (!subLogs.isEmpty()) {
			subLogs.getLast().log.addAll(entries);
		} else {
			for (final LogEntry entry : entries) {
				Bukkit.getLogger().log(entry.getLevel(), "[Skript] " + entry.getMessage());
				if (entry.getLevel() == Level.SEVERE)
					numErrors++;
			}
		}
	}
	
	/**
	 * checks whether messages should be logged for the given verbosity.
	 * 
	 * @param minVerb minimal verbosity
	 * @return
	 */
	public static boolean log(final Verbosity minVerb) {
		return minVerb.compareTo(verbosity) <= 0;
	}
	
	public static boolean debug() {
		return debug;
	}
	
	public static int getNumErrors() {
		return numErrors;
	}
	
}
