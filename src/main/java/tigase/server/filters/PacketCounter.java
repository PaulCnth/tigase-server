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
package tigase.server.filters;

import tigase.kernel.beans.Bean;
import tigase.server.Iq;
import tigase.server.Packet;
import tigase.server.PacketFilterIfc;
import tigase.server.QueueType;
import tigase.stats.StatisticsList;

import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Level;

@Bean(name = "packetCounter", parents = {PacketFiltersBean.IncomingPacketFiltersBean.class,
										 PacketFiltersBean.OutgoingPacketFiltersBean.class}, active = true)
public class PacketCounter
		implements PacketFilterIfc {

	private final static String DETAILED_OTHER_STATISTICS_KEY = "detailed-other-statistics";
	private final TypeCounter iqCounter = new TypeCounter("IQ");
	private final Map<String, TypeCounter> otherCounters = new ConcurrentHashMap<>();
	private long clusterCounter = 0;
	private boolean detailedOtherStat = true;
	private long msgCounter = 0;
	private String name = null;
	private long otherCounter = 0;
	private long presCounter = 0;
	private QueueType queueType = null;
	private long total = 0;

	public PacketCounter() {
		final String tmp = System.getProperty(DETAILED_OTHER_STATISTICS_KEY);
		if (null != tmp) {
			detailedOtherStat = Boolean.valueOf(tmp);
		}
	}

	public PacketCounter(boolean detailedOtherStat) {
		this.detailedOtherStat = detailedOtherStat;
	}

	@Override
	public Packet filter(Packet packet) {
		total++;
		final String elemName = packet.getElemName();
		if (elemName == "message") {
			++msgCounter;
		} else if (elemName == "presence") {
			++presCounter;
		} else if (elemName == "cluster") {
			++clusterCounter;
		} else if (elemName == "iq") {
			String xmlns = ((Iq) packet).getIQXMLNS();
			iqCounter.incrementCounter((xmlns != null) ? xmlns : ((Iq) packet).getIQChildName());
		} else {
			++otherCounter;

			if (detailedOtherStat) {
				String xmlns = packet.getXMLNS() != null ? packet.getXMLNS() : "no XMLNS";
				otherCounters.computeIfAbsent(xmlns, s -> new TypeCounter("other " + xmlns)).incrementCounter(elemName);
			}
		}
		return packet;
	}

	@Override
	public void getStatistics(StatisticsList list) {
		list.add(name, queueType.name() + " processed", total, Level.FINER);
		list.add(name, queueType.name() + " processed messages", msgCounter, Level.FINER);
		list.add(name, queueType.name() + " processed presences", presCounter, Level.FINER);
		list.add(name, queueType.name() + " processed cluster", clusterCounter, Level.FINER);
		list.add(name, queueType.name() + " processed other", otherCounter, Level.FINER);

		iqCounter.getStatistics(list, Level.FINER);

		final Level finer = Level.FINER;
		if (detailedOtherStat & list.checkLevel(finer)) {
			otherCounters.values().forEach(typeCounter -> typeCounter.getStatistics(list, finer));
		}
	}

	@Override
	public void init(String name, QueueType queueType) {
		this.name = name;
		this.queueType = queueType;
	}

	private class TypeCounter {

		private final Map<String, AtomicLong> counter = new ConcurrentHashMap<>();
		private final String counterName;
		private final AtomicLong total = new AtomicLong();

		public TypeCounter(String name) {
			this.counterName = name;
		}

		public Map<String, AtomicLong> getCounter() {
			return counter;
		}

		public void getStatistics(StatisticsList list) {
			getStatistics(list, Level.FINEST);
		}

		public void getStatistics(StatisticsList list, Level level) {
			list.add(name, queueType.name() + " processed " + counterName, total.get(), level);
			for (Entry<String, AtomicLong> xmlnsValues : counter.entrySet()) {
				list.add(name, queueType.name() + " processed " + counterName + " " + xmlnsValues.getKey(),
						 xmlnsValues.getValue().get(), level);
			}
		}

		public long getTotal() {
			return total.get();
		}

		synchronized public void incrementCounter(String param) {
			total.incrementAndGet();
			param = param == null ? "[no XMLNS/child]" : param;
			counter.computeIfAbsent(param, s -> new AtomicLong()).getAndIncrement();
		}
	}
}
