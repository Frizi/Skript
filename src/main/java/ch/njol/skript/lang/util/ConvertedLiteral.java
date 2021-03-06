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

package ch.njol.skript.lang.util;

import org.bukkit.event.Event;

import ch.njol.skript.Skript;
import ch.njol.skript.SkriptAPIException;
import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Literal;
import ch.njol.skript.util.Utils;
import ch.njol.util.Checker;

/**
 * @author Peter Güttinger
 * @see SimpleLiteral
 */
public class ConvertedLiteral<F, T> extends ConvertedExpression<F, T> implements Literal<T> {
	
	private final T[] data;
	
	public ConvertedLiteral(final Literal<F> source, final T[] data, final Class<T> to) {
		super(source, to, null);
		this.data = data;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <R> Literal<? extends R> getConvertedExpression(final Class<R> to) {
		if (to.isAssignableFrom(this.to))
			return (Literal<? extends R>) this;
		return ((Literal<F>) source).getConvertedExpression(to);
	}
	
	@Override
	public String toString(final Event e, final boolean debug) {
		return Skript.toString(data, getAnd());
	}
	
	@Override
	public T[] getArray() {
		return data;
	}
	
	@Override
	public T[] getAll() {
		return data;
	}
	
	@Override
	public T[] getArray(final Event e) {
		return getArray();
	}
	
	@Override
	public T getSingle() {
		if (getAnd() && data.length > 1)
			throw new SkriptAPIException("Call to getSingle on a non-single expression");
		return Utils.getRandom(data);
	}
	
	@Override
	public T getSingle(final Event e) {
		return getSingle();
	}
	
	@Override
	public boolean check(final Event e, final Checker<? super T> c) {
		return SimpleExpression.check(data, c, false, getAnd());
	}
	
	@Override
	public boolean check(final Event e, final Checker<? super T> c, final Condition cond) {
		return SimpleExpression.check(data, c, cond.isNegated(), getAnd());
	}
	
}
