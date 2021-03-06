package idv.hsiehpinghan.solrassistant.valuesource;

import java.io.IOException;
import java.util.Map;

import org.apache.lucene.index.AtomicReaderContext;
import org.apache.lucene.queries.function.FunctionValues;
import org.apache.lucene.queries.function.ValueSource;
import org.apache.lucene.queries.function.docvalues.StrDocValues;
import org.apache.solr.common.SolrException;

public class ConcatenateFunction extends ValueSource {
	private final ValueSource valueSource1;
	private final ValueSource valueSource2;
	private final String delimiter;

	public ConcatenateFunction(ValueSource valueSource1, ValueSource valueSource2, String delimiter) {
		if (valueSource1 == null || valueSource2 == null) {
			throw new SolrException(SolrException.ErrorCode.BAD_REQUEST,
					"One or more inputs missing for concatenate function");
		}
		this.valueSource1 = valueSource1;
		this.valueSource2 = valueSource2;
		if (delimiter != null) {
			this.delimiter = delimiter;
		} else {
			this.delimiter = "";
		}
	}

	@Override
	public String description() {
		return "Concatenates two values together with an optional delimiter";
	}

	@Override
	@SuppressWarnings("rawtypes")
	public FunctionValues getValues(Map context, AtomicReaderContext readerContext) throws IOException {
		final FunctionValues firstValues = valueSource1.getValues(context, readerContext);
		final FunctionValues secondValues = valueSource2.getValues(context, readerContext);
		return new StrDocValues(this) {
			@Override
			public String strVal(int doc) {
				return firstValues.strVal(doc).concat(delimiter).concat(secondValues.strVal(doc));
			}
		};
	}

	@Override
	public int hashCode() {
		long combinedHashes = (this.valueSource1.hashCode() + this.valueSource2.hashCode() + this.delimiter.hashCode());
		return (int) (combinedHashes ^ (combinedHashes >>> 32));

	}

	@Override
	public boolean equals(Object o) {
		if (this.getClass() != o.getClass())
			return false;
		ConcatenateFunction other = (ConcatenateFunction) o;
		return this.valueSource1.equals(other.valueSource1) && this.valueSource2.equals(other.valueSource2)
				&& this.delimiter == other.delimiter;
	}

}
