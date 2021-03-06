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

package ch.njol.skript.command;

import org.bukkit.command.CommandSender;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * @author Peter Güttinger
 */
public class CommandEvent extends Event {
	
	private final CommandSender sender;
	private final String command;
	private final String[] args;
	
	public CommandEvent(final CommandSender sender, final String command, final String[] args) {
		this.sender = sender;
		this.command = command;
		this.args = args;
	}
	
	public CommandSender getSender() {
		return sender;
	}
	
	public String getCommand() {
		return command;
	}
	
	public String[] getArgs() {
		return args;
	}
	
	// Bukkit stuff
	private static final HandlerList handlers = new HandlerList();
	
	@Override
	public HandlerList getHandlers() {
		return handlers;
	}
	
	public static HandlerList getHandlerList() {
		return handlers;
	}
	
}
