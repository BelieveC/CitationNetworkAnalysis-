import java.io.IOException;

import org.apache.hadoop.conf.Configuration;

import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.hbase.client.Result;

public class DataScan{

public static void main(String args[]) throws IOException{

Configuration c = HBaseConfiguration.create();        // Instantiate Configuration class

HTable table = new HTable(c, "student");        // Instantiate HTable class

Scan scan = new Scan();      // Instantiate the Scan class

scan.addColumn(Bytes.toBytes("id"), Bytes.toBytes("name"));  // Scan the required columns

ResultScanner scanner = table.getScanner(scan);      // Get scan result

// Reading values from scan result

for (Result result = scanner.next(); result != null; result = scanner.next())

System.out.println("Result Found: " + result);

scanner.close();      //close the scanner

}

}