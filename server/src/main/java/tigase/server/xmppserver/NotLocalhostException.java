
/*
 * NotLocalhostException.java
 *
 * Tigase Jabber/XMPP Server
 * Copyright (C) 2004-2017 "Tigase, Inc." <office@tigase.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, version 3 of the License.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. Look for COPYING file in the top folder.
 * If not, see http://www.gnu.org/licenses/.
 */
package tigase.server.xmppserver;

//~--- non-JDK imports --------------------------------------------------------

import tigase.xmpp.XMPPException;

//~--- classes ----------------------------------------------------------------

/**
 * Created: Sep 2, 2010 4:11:34 PM
 *
 * @author <a href="mailto:artur.hefczyc@tigase.org">Artur Hefczyc</a>
 * @version $Rev$
 */
public class NotLocalhostException extends XMPPException {
	private static final long serialVersionUID = 1L;

	//~--- constructors ---------------------------------------------------------

	/**
	 * Constructs ...
	 *
	 */
	public NotLocalhostException() {
		super();
	}

	/**
	 * Constructs ...
	 *
	 *
	 * @param msg
	 */
	public NotLocalhostException(String msg) {
		super(msg);
	}

	/**
	 * Constructs ...
	 *
	 *
	 * @param cause
	 */
	public NotLocalhostException(Throwable cause) {
		super(cause);
	}

	/**
	 * Constructs ...
	 *
	 *
	 * @param msg
	 * @param cause
	 */
	public NotLocalhostException(String msg, Throwable cause) {
		super(msg, cause);
	}
}


//~ Formatted in Sun Code Convention


//~ Formatted by Jindent --- http://www.jindent.com
