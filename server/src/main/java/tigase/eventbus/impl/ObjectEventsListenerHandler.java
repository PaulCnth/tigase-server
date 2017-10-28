/*
 * ObjectEventsListenerHandler.java
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

package tigase.eventbus.impl;

import tigase.eventbus.EventListener;

public class ObjectEventsListenerHandler extends AbstractListenerHandler<EventListener> {

	public ObjectEventsListenerHandler(final String packageName, final String eventName, EventListener listener) {
		super(packageName, eventName, listener);
	}

	@Override
	public void dispatch(Object event, Object source, boolean remotelyGeneratedEvent) {
		listener.onEvent(event);
	}

	@Override
	public Type getRequiredEventType() {
		return Type.object;
	}
}
