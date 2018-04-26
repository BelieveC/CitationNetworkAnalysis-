package communityNetwork;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.apache.hadoop.conf.Configuration;

import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;

public class DataEntryPapersTitle {

	public static void main(String[] args) throws IOException {
		
		BufferedReader br = new BufferedReader(new FileReader("/home/hadoop/Downloads/Chaitanya/project/PaperTitle.txt"));
		/*
		 * 03ef3421	AI
		 * 0053eaaf	ALGO
		 */

		// Instantiating Configuration class
		Configuration config = HBaseConfiguration.create();
		

		// Instantiating HTable class
		HTable hTableComputerScienceFieldsTable = new HTable(config, "MainPaper");
//		Table created as : create 'ComputerScienceFieldsTable', 'fieldId', 'communityCode'

		String line;
		while ((line = br.readLine()) != null) {
			Put p = new Put(Bytes.toBytes(line.split(" ")[0]));	// Instantiating Put class accepts a row name.
			p.add(Bytes.toBytes("title"), Bytes.toBytes("title"), Bytes.toBytes(line.split(" ")[1]));
			hTableComputerScienceFieldsTable.put(p);
//			System.out.println(line.split(" ")[0]);
//			System.out.println(line.split(" ")[1]);			
		}
		
	}
}