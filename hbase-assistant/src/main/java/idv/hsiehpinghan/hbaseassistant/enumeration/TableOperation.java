package idv.hsiehpinghan.hbaseassistant.enumeration;

public enum TableOperation {
	/**
	 * Do nothing.
	 */
	NONE,
	/**
	 * Drop and create table.
	 */
	DROP_CREATE,
	/**
	 * If table not exists, create it. 
	 */
	ADD_NONEXISTS
}
