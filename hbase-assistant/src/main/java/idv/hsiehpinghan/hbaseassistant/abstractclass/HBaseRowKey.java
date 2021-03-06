package idv.hsiehpinghan.hbaseassistant.abstractclass;

import idv.hsiehpinghan.collectionutility.utility.ArrayUtility;
import idv.hsiehpinghan.datatypeutility.utility.ByteUtility;

public abstract class HBaseRowKey extends HBaseBase implements
		Comparable<HBaseRowKey> {
	private HBaseTable entity;
	private byte[] bytes;

	@Override
	public int compareTo(HBaseRowKey o) {
		return ArrayUtility.compareTo(this.getBytes(), o.getBytes());
	}

	public HBaseRowKey(HBaseTable entity) {
		this.entity = entity;
		this.entity.setRowKey(this);
	}

	public HBaseTable getEntity() {
		return entity;
	}

	public String getTableName() {
		return entity.getTableName();
	}

	public String getHexString() {
		String prefix = "\\\\x";
		StringBuilder sb = new StringBuilder();
		for (byte b : bytes) {
			sb.append(prefix);
			sb.append(ByteUtility.getHexString(b));
		}
		return sb.toString();
	}

	public byte[] getBytes() {
		return bytes;
	}

	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}
}
