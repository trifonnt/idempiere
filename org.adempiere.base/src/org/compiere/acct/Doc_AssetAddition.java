package org.compiere.acct;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.compiere.model.I_C_Project;
import org.compiere.model.I_C_Project_Acct;
import org.compiere.model.MAccount;
import org.compiere.model.MAcctSchema;
import org.compiere.model.MAssetAcct;
import org.compiere.model.MAssetAddition;
import org.compiere.model.MCharge;
import org.compiere.model.MDocType;
import org.compiere.model.MProject;
import org.compiere.model.ProductCost;
import org.compiere.model.X_C_Project_Acct;
import org.compiere.util.DB;
import org.compiere.util.Env;


/**
 * Posting for {@link MAssetAddition} document. DOCBASETYPE_GLDocument.
 * @author Teo_Sarca, SC ARHIPAC SERVICE SRL
 */
public class Doc_AssetAddition extends Doc
{
	/**
	 * @param as
	 * @param rs
	 * @param trxName
	 */
	public Doc_AssetAddition (MAcctSchema as, ResultSet rs, String trxName)
	{
		super(as, MAssetAddition.class, rs, MDocType.DOCBASETYPE_GLDocument, trxName);
	}

	@Override
	protected String loadDocumentDetails()
	{
		return null;
	}

	@Override
	public BigDecimal getBalance()
	{
		return Env.ZERO;
	}

	/**
	 * Produce posting:
	 * <pre>
	 *	20.., 21..[A_Asset_Acct]			=	23..[P_Asset_Acct/Project Acct]
	 * </pre>
	 * @param as
	 * @return facts
	 */
	@Override
	public ArrayList<Fact> createFacts(MAcctSchema as)
	{
		MAssetAddition assetAdd = getAssetAddition();
		ArrayList<Fact> facts = new ArrayList<Fact>();
		Fact fact = new Fact(this, as, assetAdd.getPostingType());
		facts.add(fact);
		//
		if (MAssetAddition.A_SOURCETYPE_Imported.equals(assetAdd.getA_SourceType()) 
				|| MAssetAddition.A_CAPVSEXP_Expense.equals(assetAdd.getA_CapvsExp())) //@win prevent create journal if expense addition
		{
			// no accounting if is imported record
			return facts;
		}
		//
		BigDecimal assetValueAmt = assetAdd.getAssetSourceAmt();
		FactLine[] fls = FactUtil.createSimpleOperation(fact, null,
				getA_Asset_Acct(as), getP_Asset_Acct(as),
				getC_Currency_ID(),
				assetValueAmt,
				false);
		// Set BPartner and C_Project dimension for "Imobilizari in curs / Property Being"
		final int invoiceBP_ID = getInvoicePartner_ID();
		final int invoiceProject_ID = getInvoiceProject_ID();
		if (invoiceBP_ID > 0)
		{
			fls[1].setC_BPartner_ID(invoiceBP_ID);
		}
		if (invoiceProject_ID >0)
		{
			 fls[1].setC_Project_ID(invoiceProject_ID);
		}
		//
		return facts;
	}
	
	/**
	 * @return MAssetAddition
	 */
	private MAssetAddition getAssetAddition()
	{
		return (MAssetAddition)getPO();
	}
	
	/**
	 * @param as
	 * @return account for credit side (project, charge or expense)
	 */
	private MAccount getP_Asset_Acct(MAcctSchema as)
	{
		MAssetAddition assetAdd = getAssetAddition();
		// Source Account
		MAccount pAssetAcct = null;
		if (MAssetAddition.A_SOURCETYPE_Project.equals(assetAdd.getA_SourceType()))
		{
			I_C_Project prj = assetAdd.getC_Project();
			return getProjectAcct(prj, as);
		}
		else if (MAssetAddition.A_SOURCETYPE_Manual.equals(assetAdd.getA_SourceType())
				&& getC_Charge_ID() > 0) // backward compatibility: only if charge defined; if not fallback to product account 
		{	
			pAssetAcct = MCharge.getAccount(getC_Charge_ID(), as);
			return pAssetAcct;
		}	
		else if (MAssetAddition.A_SOURCETYPE_Invoice.equals(assetAdd.getA_SourceType())
				&& assetAdd.getC_InvoiceLine().getC_Project_ID() > 0)
		{
			I_C_Project prj = assetAdd.getC_InvoiceLine().getC_Project();
			return getProjectAcct(prj, as);
		}
		else
		{
			pAssetAcct = getP_Expense_Acct(assetAdd.getM_Product_ID(), as);
		}
		//
		return pAssetAcct;
	}
	
	/**
	 * @param M_Product_ID
	 * @param as
	 * @return expense account
	 */
	public MAccount getP_Expense_Acct(int M_Product_ID, MAcctSchema as)
	{
		ProductCost pc = new ProductCost(getCtx(), M_Product_ID, 0, null);
		return pc.getAccount(ProductCost.ACCTTYPE_P_Expense, as);
	}
	
	/**
	 * @param prj
	 * @param as
	 * @return project wip or asset account
	 */
	private MAccount getProjectAcct(I_C_Project prj, MAcctSchema as)
	{
		// TODO: keep in sync with org.compiere.acct.Doc_ProjectIssue.createFacts(MAcctSchema) logic
		String projectCategory = prj.getProjectCategory();
		String acctName = X_C_Project_Acct.COLUMNNAME_PJ_WIP_Acct;
		if (MProject.PROJECTCATEGORY_AssetProject.equals(projectCategory))
			acctName = X_C_Project_Acct.COLUMNNAME_PJ_Asset_Acct;
		//
		String sql = "SELECT "+acctName
					+ " FROM "+I_C_Project_Acct.Table_Name
					+ " WHERE "+I_C_Project_Acct.COLUMNNAME_C_Project_ID+"=?"
						+" AND "+I_C_Project_Acct.COLUMNNAME_C_AcctSchema_ID+"=?"
						;
		int acct_id = DB.getSQLValueEx(getTrxName(), sql, prj.getC_Project_ID(), as.get_ID());	
		return MAccount.get(getCtx(), acct_id);
	}

	/**
	 * @param as
	 * @return asset account
	 */
	private MAccount getA_Asset_Acct(MAcctSchema as)
	{
		MAssetAddition assetAdd = getAssetAddition();
		int acct_id = MAssetAcct
				.forA_Asset_ID(getCtx(), as.get_ID(), assetAdd.getA_Asset_ID(), assetAdd.getPostingType(), assetAdd.getDateAcct(), assetAdd.get_TrxName())
				.getA_Asset_Acct();
		return MAccount.get(getCtx(), acct_id);
	}

	/**
	 * @return C_BPartner_ID from invoice or 0
	 */
	public int getInvoicePartner_ID()
	{
		MAssetAddition assetAdd = getAssetAddition();
		if (MAssetAddition.A_SOURCETYPE_Invoice.equals(assetAdd.getA_SourceType())
				&& assetAdd.getC_Invoice_ID() > 0)
		{
			return assetAdd.getC_Invoice().getC_BPartner_ID();
		}
		else
		{
			return 0;
		}
	}
	
	/**
	 * @return C_Project_ID from invoice line or 0
	 */
	public int getInvoiceProject_ID()
	{
		MAssetAddition assetAdd = getAssetAddition();
		if (MAssetAddition.A_SOURCETYPE_Invoice.equals(assetAdd.getA_SourceType())
				&& assetAdd.getC_Invoice_ID() > 0)			
		{
			return assetAdd.getC_InvoiceLine().getC_Project_ID();
		}
		else
		{
			return 0;
		}
	}		
}
