package idv.hsiehpinghan.neo4jassistant.relationship;

import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.Version;

public abstract class BaseRelationship {
	@Version
	private Long version_;
	@Id
	private String id;

	public BaseRelationship() {
	}

	public BaseRelationship(String id) {
		super();
		this.id = id;
	}

	public Long getVersion_() {
		return version_;
	}

	public void setVersion_(Long version_) {
		this.version_ = version_;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BaseRelationship other = (BaseRelationship) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
