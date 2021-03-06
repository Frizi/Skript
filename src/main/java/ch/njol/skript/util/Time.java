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

package ch.njol.skript.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ch.njol.skript.Skript;

/**
 * @author Peter Güttinger
 */
public class Time {
	
	private final int time;
	
	public Time(final int time) {
		this.time = time % 24000;
	}
	
	public int getTicks() {
		return time;
	}
	
	@Override
	public String toString() {
		return toString(time);
	}
	
	public static String toString(final int ticks) {
		final int t = (ticks + 6000) % 24000;
		final int hours = (int) Math.floor(t / 1000);
		final int minutes = (int) (Math.floor((t % 1000) / 16.666666667)); // floor to prevent ':60'
		return "" + hours + ":" + (minutes < 10 ? "0" : "") + minutes;
	}
	
	/**
	 * 
	 * @param s The trim()med string to parse
	 * @return The parsed time of null if the input was invalid
	 */
	public final static Time parse(final String s) {
//		if (s.matches("\\d+")) {
//			return new Time(Integer.parseInt(s));
//		} else 
		if (s.matches("\\d?\\d:\\d\\d")) {
			final int hours = Skript.parseInt(s.split(":")[0]);
			if (hours >= 24) {
				Skript.error("a day only has 24 hours");
				return null;
			}
			final int minutes = Skript.parseInt(s.split(":")[1]);
			if (minutes >= 60) {
				Skript.error("an hour only has 60 minutes");
				return null;
			}
			return new Time((int) Math.round(hours * 1000 - 6000 + minutes * 16.6666667));
		} else {
			final Matcher m = Pattern.compile("^(?i)(\\d?\\d)(:(\\d\\d))? ?(am|pm)$").matcher(s);
			if (m.matches()) {
				int hours = Skript.parseInt(m.group(1));
				if (hours > 12) {
					Skript.error("using 12-hour format does not allow more than 12 hours");
					return null;
				}
				int minutes = 0;
				if (m.group(3) != null)
					minutes = Skript.parseInt(m.group(3));
				if (minutes >= 60) {
					Skript.error("an hour only has 60 minutes");
					return null;
				}
				if (m.group(4).equalsIgnoreCase("pm"))
					hours += 12;
				return new Time((int) Math.round(hours * 1000 - 6000 + minutes * 16.6666667));
			}
		}
		return null;
	}
	
	@Override
	public int hashCode() {
		return time;
	}
	
	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Time))
			return false;
		final Time other = (Time) obj;
		return time == other.time;
	}
	
}
