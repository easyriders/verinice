/*
 * This file is part of the SerNet Customer Database Application (SNKDB).
 * Copyright Alexander Prack, 2004.
 * 
 *  SNKDB is free software; you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation; either version 2 of the License, or
 *   (at your option) any later version.
 *
 *   SNKDB is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with SNKDB; if not, write to the Free Software
 *   Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */
package sernet.snutils;

import java.sql.Date;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Class to parse different user text inputs like dates, curencies or
 * simple number formats.
 * 
 * @author prack
 * @version $Id: FormInputParser.java,v 1.1 2005/12/27 16:41:18 aprack Exp $
 */
public abstract class FormInputParser {
	private static final boolean GROUPING = true;
	private static NumberFormat numFmt;
	private static NumberFormat priceFmt; 
	
	static {
		numFmt = NumberFormat.getNumberInstance(Locale.GERMANY);
		numFmt.setGroupingUsed(GROUPING);
		priceFmt = NumberFormat.getCurrencyInstance(Locale.GERMANY);
		priceFmt.setGroupingUsed(GROUPING);
	}
	
	public static float stringToCurrency(String fieldName, String s) throws DBException {
		try {
			priceFmt.setGroupingUsed(GROUPING);
			return priceFmt.parse(s).floatValue();
		}
		catch (ParseException e) {
			numFmt.setGroupingUsed(GROUPING);
			try {
				return numFmt.parse(s).floatValue();
			}
			catch (ParseException e1) {
				throw new DBException("Falsches Format f�r " + fieldName + ".");
			}
		}
	}
	
	public static String currencyToString(float value) {
		priceFmt.setGroupingUsed(GROUPING);
		return priceFmt.format(value);
	}
	
	public static float stringToFloat(String fieldName, String s) throws DBException {
		try {
			numFmt.setGroupingUsed(GROUPING);
			return numFmt.parse(s).floatValue();
		}
		catch (ParseException e) {
			throw new DBException("Falsches Format f�r " + fieldName + ".");
		}
	}
	
	public static short stringToShort(String fieldName, String s) throws DBException {
		try {
			numFmt.setGroupingUsed(GROUPING);
			return numFmt.parse(s).shortValue();
		}
		catch (ParseException e) {
			throw new DBException("Falsches Format f�r " + fieldName + ".");
		}
	}
	
	public static String dateToString(Date date) throws AssertException {
		try {
			if (date == null)
				return "";
			SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, dd.MM.yyyy");
			return dateFormat.format(date);
		}
		catch (IllegalArgumentException e) {
			throw new AssertException("Falsches / fehlendes Datum: " + date.toString());
		}
	}
	
	public static Date stringToDate(String string) throws AssertException {
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, dd.MM.yyyy");
			dateFormat.setLenient(true);
			return new Date(dateFormat.parse(string).getTime());
		}
		catch (IllegalArgumentException e) {
			throw new AssertException("Falsches / fehlendes Datum: " + string);
		}
		catch (ParseException e) {
			SimpleDateFormat dateFormat2 = new SimpleDateFormat("dd.MM.yyyy");
			dateFormat2.setLenient(true);
			try {
				return new Date(dateFormat2.parse(string).getTime());
			}
			catch (ParseException e1) {
				SimpleDateFormat dateFormat3 = new SimpleDateFormat("yyyy-MM-dd");
				dateFormat3.setLenient(true);
				try {
					return new Date(dateFormat3.parse(string).getTime());
				}
				catch (ParseException e2) {
					throw new AssertException("Falsches / fehlendes Datum: " + string);
				}
			}
		}
	}

	/**
	 * @param f
	 * @return
	 */
	public static String floatToString(float f) {
		return numFmt.format(f);
	}

}
