/**
 * 
 */
package org.adempiere.model;

import org.adempiere.process.ImportProcess;

/**
 * Import Validator Interface
 * @author Teo Sarca, www.arhipac.ro
 * 			<li>FR [ 2788276 ] Data Import Validator
 * 				https://sourceforge.net/p/adempiere/feature-requests/712/
 */
public interface ImportValidator
{
	/** Event triggered before all import records are validated */
	public static final int TIMING_BEFORE_VALIDATE = 10;
	/** Event triggered after all import records are validated */
	public static final int TIMING_AFTER_VALIDATE = 20;
	/** Event triggered before an import record is processed */
	public static final int TIMING_BEFORE_IMPORT = 30;
	/** Event triggered after an import record is processed */
	public static final int TIMING_AFTER_IMPORT = 40;
	
	/**
	 * Handle import event
	 * @param process
	 * @param importModel
	 * @param targetModel
	 * @param timing {@link #TIMING_BEFORE_VALIDATE}, {@link #TIMING_AFTER_VALIDATE},
	 * {@link #TIMING_BEFORE_IMPORT} or {@link #TIMING_AFTER_IMPORT}.
	 */
	public void validate(ImportProcess process, Object importModel, Object targetModel, int timing);
}
