package authorCommunityOverTime;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableMapReduceUtil;
import org.apache.hadoop.hbase.mapreduce.TableReducer;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import communityNetwork.DataEntryPaperAuthor;


public class DataEntryAuthorPaper {

	public static class TokenizerMapper extends Mapper<Object, Text, Text, Text> {

		HashMap<Text, Text> fieldToClass = new HashMap<>();
		
		private HTable htable;
		private Configuration config;
		
		// Following fuction will load entire ComputerScienceFieldsTable in a hashmap. 

		@Override
		protected void setup(Mapper<Object, Text, Text, Text>.Context context)
				throws IOException, InterruptedException {
			// TODO Auto-generated method stub
			Configuration config = HBaseConfiguration.create();
			htable = new HTable(config, "Main-Paper-Community");
			Scan scan = new Scan();
			scan.addColumn(Bytes.toBytes("community"), Bytes.toBytes("id"));
			ResultScanner scanner = htable.getScanner(scan);
			for (Result result = scanner.next(); result != null; result = scanner.next()) {
				String paperId = Bytes.toString(result.getRow());
				String paperCom = Bytes.toString(result.getValue(Bytes.toBytes("community"), Bytes.toBytes("id")));
				fieldToClass.put(new Text(paperId),  new Text(paperCom));	// 001AD081 -> AI & so on
			}
			
		}

		public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
			/*
				00000080        current density 0C61E031
				000000E1        flow velocity   09EF88C5
			*/
			String str = value.toString();
			String pid = "", fid = "";
			if (!str.equals("")) {
				pid = str.substring(0, str.indexOf(" "));						// 00000080
				fid = str.substring(str.lastIndexOf(" ") + 1).toLowerCase();	// 0C61E031
//				System.out.println(pid+" "+ fid);
				if (fieldToClass.containsKey(new Text(fid))) {					// if this field is a valid CS field.
//					System.out.println(new Text(pid));
//					System.out.println("Start Checking Pid");
					context.write(new Text(pid), fieldToClass.get(new Text(fid)));	// we will procees to reducer with paperId and corresponding community
				}
			}
		}
	}

	public static class PaperEntryReducer extends TableReducer<Text, Text, ImmutableBytesWritable> { 
		public void reduce(Text paperId, Iterable<Text> value, Context context) throws IOException, InterruptedException {
			// for each paper, concat all communities it belongs to with a comma (once for each community)
			String communityCSV = "";
			HashMap<Text, Integer> present = new HashMap<>();	// map to check if community already added for this paper
			for (Text community: value) {
				if (present.get(community) == null) {			// if this community has not been added
					communityCSV += "," + community.toString();	// add this community
					present.put(community, new Integer(1));		
				}
			}

			// put data into HBaseTable
			Put put = new Put(Bytes.toBytes(paperId.toString()));
			put.add(Bytes.toBytes("paper"), Bytes.toBytes("com"), Bytes.toBytes(communityCSV));
			context.write(new ImmutableBytesWritable(Bytes.toBytes(paperId.toString())), put);
		}
	}

	public static void main(String[] args) throws Exception {
		
		Configuration conf = HBaseConfiguration.create();
		Job job = Job.getInstance(conf, "DataEntryAuthorPaper");
		conf.setInt("mapred.tasktracker.map.tasks.maximum", 22);
		job.setJarByClass(DataEntryPaperAuthor.class);
		job.setMapperClass(TokenizerMapper.class);
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(Text.class);
		job.setReducerClass(PaperEntryReducer.class);
		
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);
		FileInputFormat.setInputPaths(job, new Path("/home/hadoop/Downloads/Chaitanya/project/author-paper.txt"));	// "PaperKeywords.txt"

		TableMapReduceUtil.initTableReducerJob("MainAuthorAbout", PaperEntryReducer.class, job);
		job.setReducerClass(PaperEntryReducer.class);
		job.waitForCompletion(true);
	}
}