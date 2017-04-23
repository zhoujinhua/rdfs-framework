package com.rdfs.framework.workflow.constant;

public class Constants {


	/**
	 * Redis全局Key前缀
	 */
	public static final class KEYS {

		public static final String PROCESS_KEY = com.rdfs.framework.core.contants.Constants.KEYS.GLOBAL + "process_";
		public static final String PROCESS_NODE_KEY = com.rdfs.framework.core.contants.Constants.KEYS.GLOBAL + "process_node_";
		public static final String NODE_EVENT_KEY = com.rdfs.framework.core.contants.Constants.KEYS.GLOBAL + "node_event_";
		public static final String EVENT_MONITOR_KEY = com.rdfs.framework.core.contants.Constants.KEYS.GLOBAL + "event_monitor_";
	}
}
