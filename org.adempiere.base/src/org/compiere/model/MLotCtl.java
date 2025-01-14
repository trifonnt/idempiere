/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.util.Util;

/**
 *	Lot Control Model
 *	
 *  @author Jorg Janke
 *  @version $Id: MLotCtl.java,v 1.3 2006/07/30 00:51:02 jjanke Exp $
 */
public class MLotCtl extends X_M_LotCtl
{
	/**
	 * generated serial id
	 */
	private static final long serialVersionUID = -1020114756336617138L;

    /**
     * UUID based Constructor
     * @param ctx  Context
     * @param M_LotCtl_UU  UUID key
     * @param trxName Transaction
     */
    public MLotCtl(Properties ctx, String M_LotCtl_UU, String trxName) {
        super(ctx, M_LotCtl_UU, trxName);
		if (Util.isEmpty(M_LotCtl_UU))
			setInitialDefaults();
    }

	/**
	 * 	Standard Constructor
	 *	@param ctx context
	 *	@param M_LotCtl_ID id
	 *	@param trxName transaction
	 */
	public MLotCtl (Properties ctx, int M_LotCtl_ID, String trxName)
	{
		super (ctx, M_LotCtl_ID, trxName);
		if (M_LotCtl_ID == 0)
			setInitialDefaults();
	}	//	MLotCtl

	/**
	 * Set the initial defaults for a new record
	 */
	private void setInitialDefaults() {
		setStartNo (1);
		setCurrentNext (1);
		setIncrementNo (1);
	}

	/**
	 * 	Load Constructor
	 *	@param ctx context
	 *	@param rs result set
	 *	@param trxName transaction
	 */
	public MLotCtl (Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	//	MLotCtl

	/**
	 * 	Create and save new Lot.
	 * 	Increments Current Next and Save.
	 *	@param M_Product_ID product
	 *	@return new Lot
	 */
	public MLot createLot (int M_Product_ID)
	{
		StringBuilder name = new StringBuilder();
		if (getPrefix() != null)
			name.append(getPrefix());
		int no = getCurrentNext();
		name.append(no);
		if (getSuffix() != null)
			name.append(getSuffix());
		//
		no += getIncrementNo();
		setCurrentNext(no);
		saveEx();
		//
		MLot retValue = new MLot (this, M_Product_ID, name.toString());
		retValue.saveEx();
		return retValue;
	}	//	createLot

}	//	MLotCtl
