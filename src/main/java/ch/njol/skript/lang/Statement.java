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

package ch.njol.skript.lang;

import ch.njol.skript.Skript;

/**
 * Supertype of conditions and effects
 * 
 * @author Peter Güttinger
 * @see Condition
 * @see Effect
 */
public abstract class Statement extends TriggerItem implements SyntaxElement {
	
	public static Statement parse(final String s, final String defaultError) {
		return (Statement) SkriptParser.parse(s, Skript.getStatements().iterator(), false, false, defaultError);
	}
	
}
