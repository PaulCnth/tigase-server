/*
 * Tigase XMPP Server - The instant messaging server
 * Copyright (C) 2004 Tigase, Inc. (office@tigase.com)
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
package tigase.xmpp;

import tigase.annotations.TigaseDeprecated;

@Deprecated
@TigaseDeprecated(since = "8.2.0", removeIn = "9.0.0", note = "To be replaced with tigase.xmpp.InvalidPacketException and used tigase.xmpp.Authorization.getResponseMessage")
public class PacketErrorTypeException
		extends XMPPException {

	private static final long serialVersionUID = 1L;

	public PacketErrorTypeException(String message) {
		super(message);
	}

	public PacketErrorTypeException(String message, Throwable cause) {
		super(message, cause);
	}
}

