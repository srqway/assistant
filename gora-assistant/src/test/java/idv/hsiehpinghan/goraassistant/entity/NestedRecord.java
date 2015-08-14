/**
 * Autogenerated by Avro
 * 
 * DO NOT EDIT DIRECTLY
 */
package idv.hsiehpinghan.goraassistant.entity;  
@SuppressWarnings("all")
public class NestedRecord extends org.apache.gora.persistency.impl.PersistentBase implements org.apache.avro.specific.SpecificRecord, org.apache.gora.persistency.Persistent {
  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"NestedRecord\",\"namespace\":\"idv.hsiehpinghan.goraassistant.entity\",\"fields\":[{\"name\":\"_boolean\",\"type\":\"boolean\",\"doc\":\"boolean doc\",\"default\":true,\"order\":\"ignore\"},{\"name\":\"_int\",\"type\":\"int\",\"doc\":\"int doc\",\"default\":1,\"order\":\"ignore\"}]}");

  /** Enum containing all data bean's fields. */
  public static enum Field {
    _BOOLEAN(0, "_boolean"),
    _INT(1, "_int"),
    ;
    /**
     * Field's index.
     */
    private int index;

    /**
     * Field's name.
     */
    private String name;

    /**
     * Field's constructor
     * @param index field's index.
     * @param name field's name.
     */
    Field(int index, String name) {this.index=index;this.name=name;}

    /**
     * Gets field's index.
     * @return int field's index.
     */
    public int getIndex() {return index;}

    /**
     * Gets field's name.
     * @return String field's name.
     */
    public String getName() {return name;}

    /**
     * Gets field's attributes to string.
     * @return String field's attributes to string.
     */
    public String toString() {return name;}
  };

  public static final String[] _ALL_FIELDS = {
  "_boolean",
  "_int",
  };

  /**
   * Gets the total field count.
   * @return int field count
   */
  public int getFieldsCount() {
    return NestedRecord._ALL_FIELDS.length;
  }

  /** boolean doc */
  private boolean _boolean;
  /** int doc */
  private int _int;
  public org.apache.avro.Schema getSchema() { return SCHEMA$; }
  // Used by DatumWriter.  Applications should not call. 
  public java.lang.Object get(int field$) {
    switch (field$) {
    case 0: return _boolean;
    case 1: return _int;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }
  
  // Used by DatumReader.  Applications should not call. 
  @SuppressWarnings(value="unchecked")
  public void put(int field$, java.lang.Object value) {
    switch (field$) {
    case 0: _boolean = (java.lang.Boolean)(value); break;
    case 1: _int = (java.lang.Integer)(value); break;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  /**
   * Gets the value of the '_boolean' field.
   * boolean doc   */
  public java.lang.Boolean getBoolean$1() {
    return _boolean;
  }

  /**
   * Sets the value of the '_boolean' field.
   * boolean doc   * @param value the value to set.
   */
  public void setBoolean$1(java.lang.Boolean value) {
    this._boolean = value;
    setDirty(0);
  }
  
  /**
   * Checks the dirty status of the '_boolean' field. A field is dirty if it represents a change that has not yet been written to the database.
   * boolean doc   * @param value the value to set.
   */
  public boolean isBoolean$1Dirty() {
    return isDirty(0);
  }

  /**
   * Gets the value of the '_int' field.
   * int doc   */
  public java.lang.Integer getInt$1() {
    return _int;
  }

  /**
   * Sets the value of the '_int' field.
   * int doc   * @param value the value to set.
   */
  public void setInt$1(java.lang.Integer value) {
    this._int = value;
    setDirty(1);
  }
  
  /**
   * Checks the dirty status of the '_int' field. A field is dirty if it represents a change that has not yet been written to the database.
   * int doc   * @param value the value to set.
   */
  public boolean isInt$1Dirty() {
    return isDirty(1);
  }

  /** Creates a new NestedRecord RecordBuilder */
  public static idv.hsiehpinghan.goraassistant.entity.NestedRecord.Builder newBuilder() {
    return new idv.hsiehpinghan.goraassistant.entity.NestedRecord.Builder();
  }
  
  /** Creates a new NestedRecord RecordBuilder by copying an existing Builder */
  public static idv.hsiehpinghan.goraassistant.entity.NestedRecord.Builder newBuilder(idv.hsiehpinghan.goraassistant.entity.NestedRecord.Builder other) {
    return new idv.hsiehpinghan.goraassistant.entity.NestedRecord.Builder(other);
  }
  
  /** Creates a new NestedRecord RecordBuilder by copying an existing NestedRecord instance */
  public static idv.hsiehpinghan.goraassistant.entity.NestedRecord.Builder newBuilder(idv.hsiehpinghan.goraassistant.entity.NestedRecord other) {
    return new idv.hsiehpinghan.goraassistant.entity.NestedRecord.Builder(other);
  }
  
  private static java.nio.ByteBuffer deepCopyToReadOnlyBuffer(
      java.nio.ByteBuffer input) {
    java.nio.ByteBuffer copy = java.nio.ByteBuffer.allocate(input.capacity());
    int position = input.position();
    input.reset();
    int mark = input.position();
    int limit = input.limit();
    input.rewind();
    input.limit(input.capacity());
    copy.put(input);
    input.rewind();
    copy.rewind();
    input.position(mark);
    input.mark();
    copy.position(mark);
    copy.mark();
    input.position(position);
    copy.position(position);
    input.limit(limit);
    copy.limit(limit);
    return copy.asReadOnlyBuffer();
  }
  
  /**
   * RecordBuilder for NestedRecord instances.
   */
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<NestedRecord>
    implements org.apache.avro.data.RecordBuilder<NestedRecord> {

    private boolean _boolean;
    private int _int;

    /** Creates a new Builder */
    private Builder() {
      super(idv.hsiehpinghan.goraassistant.entity.NestedRecord.SCHEMA$);
    }
    
    /** Creates a Builder by copying an existing Builder */
    private Builder(idv.hsiehpinghan.goraassistant.entity.NestedRecord.Builder other) {
      super(other);
    }
    
    /** Creates a Builder by copying an existing NestedRecord instance */
    private Builder(idv.hsiehpinghan.goraassistant.entity.NestedRecord other) {
            super(idv.hsiehpinghan.goraassistant.entity.NestedRecord.SCHEMA$);
      if (isValidValue(fields()[0], other._boolean)) {
        this._boolean = (java.lang.Boolean) data().deepCopy(fields()[0].schema(), other._boolean);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other._int)) {
        this._int = (java.lang.Integer) data().deepCopy(fields()[1].schema(), other._int);
        fieldSetFlags()[1] = true;
      }
    }

    /** Gets the value of the '_boolean' field */
    public java.lang.Boolean getBoolean$1() {
      return _boolean;
    }
    
    /** Sets the value of the '_boolean' field */
    public idv.hsiehpinghan.goraassistant.entity.NestedRecord.Builder setBoolean$1(boolean value) {
      validate(fields()[0], value);
      this._boolean = value;
      fieldSetFlags()[0] = true;
      return this; 
    }
    
    /** Checks whether the '_boolean' field has been set */
    public boolean hasBoolean$1() {
      return fieldSetFlags()[0];
    }
    
    /** Clears the value of the '_boolean' field */
    public idv.hsiehpinghan.goraassistant.entity.NestedRecord.Builder clearBoolean$1() {
      fieldSetFlags()[0] = false;
      return this;
    }
    
    /** Gets the value of the '_int' field */
    public java.lang.Integer getInt$1() {
      return _int;
    }
    
    /** Sets the value of the '_int' field */
    public idv.hsiehpinghan.goraassistant.entity.NestedRecord.Builder setInt$1(int value) {
      validate(fields()[1], value);
      this._int = value;
      fieldSetFlags()[1] = true;
      return this; 
    }
    
    /** Checks whether the '_int' field has been set */
    public boolean hasInt$1() {
      return fieldSetFlags()[1];
    }
    
    /** Clears the value of the '_int' field */
    public idv.hsiehpinghan.goraassistant.entity.NestedRecord.Builder clearInt$1() {
      fieldSetFlags()[1] = false;
      return this;
    }
    
    @Override
    public NestedRecord build() {
      try {
        NestedRecord record = new NestedRecord();
        record._boolean = fieldSetFlags()[0] ? this._boolean : (java.lang.Boolean) defaultValue(fields()[0]);
        record._int = fieldSetFlags()[1] ? this._int : (java.lang.Integer) defaultValue(fields()[1]);
        return record;
      } catch (Exception e) {
        throw new org.apache.avro.AvroRuntimeException(e);
      }
    }
  }
  
  public NestedRecord.Tombstone getTombstone(){
  	return TOMBSTONE;
  }

  public NestedRecord newInstance(){
    return newBuilder().build();
  }

  private static final Tombstone TOMBSTONE = new Tombstone();
  
  public static final class Tombstone extends NestedRecord implements org.apache.gora.persistency.Tombstone {
  
      private Tombstone() { }
  
	  		  /**
	   * Gets the value of the '_boolean' field.
	   * boolean doc	   */
	  public java.lang.Boolean getBoolean$1() {
	    throw new java.lang.UnsupportedOperationException("Get is not supported on tombstones");
	  }
	
	  /**
	   * Sets the value of the '_boolean' field.
	   * boolean doc	   * @param value the value to set.
	   */
	  public void setBoolean$1(java.lang.Boolean value) {
	    throw new java.lang.UnsupportedOperationException("Set is not supported on tombstones");
	  }
	  
	  /**
	   * Checks the dirty status of the '_boolean' field. A field is dirty if it represents a change that has not yet been written to the database.
	   * boolean doc	   * @param value the value to set.
	   */
	  public boolean isBoolean$1Dirty() {
	    throw new java.lang.UnsupportedOperationException("IsDirty is not supported on tombstones");
	  }
	
				  /**
	   * Gets the value of the '_int' field.
	   * int doc	   */
	  public java.lang.Integer getInt$1() {
	    throw new java.lang.UnsupportedOperationException("Get is not supported on tombstones");
	  }
	
	  /**
	   * Sets the value of the '_int' field.
	   * int doc	   * @param value the value to set.
	   */
	  public void setInt$1(java.lang.Integer value) {
	    throw new java.lang.UnsupportedOperationException("Set is not supported on tombstones");
	  }
	  
	  /**
	   * Checks the dirty status of the '_int' field. A field is dirty if it represents a change that has not yet been written to the database.
	   * int doc	   * @param value the value to set.
	   */
	  public boolean isInt$1Dirty() {
	    throw new java.lang.UnsupportedOperationException("IsDirty is not supported on tombstones");
	  }
	
		  
  }
  
}
