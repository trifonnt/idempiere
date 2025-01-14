/***********************************************************************
 * This file is part of iDempiere ERP Open Source                      *
 * http://www.idempiere.org                                            *
 *                                                                     *
 * Copyright (C) Contributors                                          *
 *                                                                     *
 * This program is free software; you can redistribute it and/or       *
 * modify it under the terms of the GNU General Public License         *
 * as published by the Free Software Foundation; either version 2      *
 * of the License, or (at your option) any later version.              *
 *                                                                     *
 * This program is distributed in the hope that it will be useful,     *
 * but WITHOUT ANY WARRANTY; without even the implied warranty of      *
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the        *
 * GNU General Public License for more details.                        *
 *                                                                     *
 * You should have received a copy of the GNU General Public License   *
 * along with this program; if not, write to the Free Software         *
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,          *
 * MA 02110-1301, USA.                                                 *
 *                                                                     *
 * Contributors:                                                       *
 * - hengsin                         								   *
 **********************************************************************/
package org.adempiere.base.event.annotations.imp;

import org.adempiere.base.event.EventHelper;
import org.adempiere.base.event.ImportEventData;
import org.adempiere.base.event.annotations.EventDelegate;
import org.osgi.service.event.Event;

/**
 * Event delegate for import event.<br/>
 * To handle an import event, create a sub class of this and uses the import event annotation (AfterImport, BeforeImport, etc)
 * to annotate method for a specific import event topic. 
 * @author hengsin
 */
public class ImportEventDelegate extends EventDelegate {

	/**
	 * @param event
	 */
	public ImportEventDelegate(Event event) {
		super(event);
	}

	/**
	 * 
	 * @return {@link ImportEventData}
	 */
	protected ImportEventData getImportEventData() {
		return EventHelper.getEventData(event);
	}
}
