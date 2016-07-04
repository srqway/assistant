package idv.hsiehpinghan.mapreduceassistant2.job;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

import org.apache.avro.util.Utf8;
import org.apache.gora.mapreduce.GoraInputFormat;
import org.apache.gora.mapreduce.GoraMapper;
import org.apache.gora.mapreduce.GoraOutputFormat;
import org.apache.gora.mapreduce.GoraReducer;
import org.apache.gora.query.Query;
import org.apache.gora.store.DataStore;
import org.apache.hadoop.conf.Configurable;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Partitioner;
import org.apache.nutch.crawl.GeneratorJob;
import org.apache.nutch.crawl.URLPartitioner;
import org.apache.nutch.storage.Mark;
import org.apache.nutch.storage.WebPage;
import org.apache.nutch.util.TableUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import idv.hsiehpinghan.nutch.storage.Seed;

@Component
public class GoraInputFormatGoraOutputFormat1 {
	@Autowired
	private Configuration conf;
	@Autowired
	private DataStore<String, WebPage> webPageDataStore;
	@Autowired
	private DataStore<String, Seed> seedDataStore;

	public boolean inject(String batchId, Collection<Seed.Field> fields) throws Exception {
		conf.set(GeneratorJob.BATCH_ID, String.valueOf(batchId));
		Job job = Job.getInstance(conf, "goraInputFormatGoraOutputFormat1");
		Query<String, Seed> query = seedDataStore.newQuery();
		query.setFields(toStringArray(fields));
		job.setJarByClass(GoraInputFormatGoraOutputFormat1.class);
		job.setMapperClass(GeneratorMapper_.class);
		job.setMapOutputKeyClass(SelectorEntry_.class);
		job.setMapOutputValueClass(Seed.class);
		job.setReducerClass(GeneratorReducer_.class);
		job.setPartitionerClass(SelectorEntryPartitioner_.class);
		GoraInputFormat.setInput(job, query, seedDataStore, true);
		GoraOutputFormat.setOutput(job, webPageDataStore, true);
		return job.waitForCompletion(true);
	}

	private String[] toStringArray(Collection<Seed.Field> fields) {
		String[] arr = new String[fields.size()];
		Iterator<Seed.Field> iter = fields.iterator();
		for (int i = 0; i < arr.length; i++) {
			arr[i] = iter.next().getName();
		}
		return arr;
	}

	public static class GeneratorMapper_ extends GoraMapper<String, Seed, SelectorEntry_, Seed> {
		private SelectorEntry_ entry = new SelectorEntry_();

		@Override
		public void map(String url, Seed seed, Context context) throws IOException, InterruptedException {
			Float score = seed.getScore();
			entry.set(url, score);
			context.write(entry, seed);
		}
	}

	public static class GeneratorReducer_ extends GoraReducer<SelectorEntry_, Seed, String, WebPage> {
		private Utf8 batchId;

		@Override
		protected void setup(Context context) throws IOException, InterruptedException {
			Configuration conf = context.getConfiguration();
			batchId = new Utf8(conf.get(GeneratorJob.BATCH_ID));
		}

		@Override
		protected void reduce(SelectorEntry_ key, Iterable<Seed> values, Context context)
				throws IOException, InterruptedException {
			for (Seed seed : values) {
				WebPage webPage = WebPage.newBuilder().build();
				webPage.setFetchInterval(seed.getFetchInterval());
				webPage.setScore(seed.getScore());
				Mark.GENERATE_MARK.putMark(webPage, batchId);
				context.write(TableUtil.reverseUrl(key.url), webPage);
			}
		}

	}

	public static class SelectorEntryPartitioner_ extends Partitioner<SelectorEntry_, WebPage> implements Configurable {
		private URLPartitioner partitioner = new URLPartitioner();
		private Configuration conf;

		@Override
		public int getPartition(SelectorEntry_ selectorEntry, WebPage page, int numReduces) {
			return partitioner.getPartition(selectorEntry.url, numReduces);
		}

		@Override
		public Configuration getConf() {
			return conf;
		}

		@Override
		public void setConf(Configuration conf) {
			this.conf = conf;
			partitioner.setConf(conf);
		}
	}

	public static class SelectorEntry_ implements WritableComparable<SelectorEntry_> {
		String url;
		float score;

		public SelectorEntry_() {
		}

		public SelectorEntry_(String url, float score) {
			this.url = url;
			this.score = score;
		}

		public void readFields(DataInput in) throws IOException {
			url = Text.readString(in);
			score = in.readFloat();
		}

		public void write(DataOutput out) throws IOException {
			Text.writeString(out, url);
			out.writeFloat(score);
		}

		public int compareTo(SelectorEntry_ se) {
			if (se.score > score)
				return 1;
			else if (se.score == score)
				return url.compareTo(se.url);
			return -1;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + url.hashCode();
			result = prime * result + Float.floatToIntBits(score);
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			SelectorEntry_ other = (SelectorEntry_) obj;
			if (!url.equals(other.url))
				return false;
			if (Float.floatToIntBits(score) != Float.floatToIntBits(other.score))
				return false;
			return true;
		}

		public void set(String url, float score) {
			this.url = url;
			this.score = score;
		}
	}

}