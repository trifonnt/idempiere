/******************************************************************************
 * Copyright (C) 2009 Low Heng Sin                                            *
 * Copyright (C) 2009 Idalica Corporation                                     *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 *****************************************************************************/
package org.adempiere.model;

import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.model.X_M_PromotionLine;

public class MPromotionLine extends X_M_PromotionLine {

	private static final long serialVersionUID = -8284722914757724765L;

    /**
    * UUID based Constructor
    * @param ctx  Context
    * @param M_PromotionLine_UU  UUID key
    * @param trxName Transaction
    */
    public MPromotionLine(Properties ctx, String M_PromotionLine_UU, String trxName) {
        super(ctx, M_PromotionLine_UU, trxName);
    }

    /**
     * @param ctx
     * @param M_PromotionLine_ID
     * @param trxName
     */
	public MPromotionLine(Properties ctx, int M_PromotionLine_ID, String trxName) {
		super(ctx, M_PromotionLine_ID, trxName);
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MPromotionLine(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	/**
	 * @param ctx
	 * @param M_PromotionLine_ID
	 * @param trxName
	 * @param virtualColumns
	 */
	public MPromotionLine(Properties ctx, int M_PromotionLine_ID, String trxName, String... virtualColumns) {
		super(ctx, M_PromotionLine_ID, trxName, virtualColumns);
	}

}
