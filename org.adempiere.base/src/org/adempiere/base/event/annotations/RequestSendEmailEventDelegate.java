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
package org.adempiere.base.event.annotations;

import org.adempiere.base.event.EventHelper;
import org.adempiere.base.event.RequestSendEMailEventData;
import org.osgi.service.event.Event;

/**
 * Event delegate for {@link RequestSendEMail} event topic. <br/>
 * To handle RequestSendEMail event, create a sub class of this and override the onRequestSendEmail method.
 * @author hengsin
 *
 */
public abstract class RequestSendEmailEventDelegate extends EventDelegate {

	/**
	 * 
	 * @param event
	 */
	public RequestSendEmailEventDelegate(Event event) {
		super(event);
	}

	/**
	 * 
	 * @return {@link RequestSendEMailEventData}
	 */
	protected RequestSendEMailEventData getEventData() {
		return EventHelper.getEventData(event);
	}
	
	@RequestSendEMail
	public final void onEvent() {
		onRequestSendEmail(getEventData());
	}

	/**
	 * Override this to handle event
	 * @param requestSendEMailEventData 
	 */
	protected abstract void onRequestSendEmail(RequestSendEMailEventData requestSendEMailEventData);
}
