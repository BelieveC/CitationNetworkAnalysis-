import java.io.IOException;

import org.apache.hadoop.conf.Configuration;

import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;

public class DataUpdate{

public static void main(String[] args) throws IOException {

Configuration c = HBaseConfiguration.create();      // Instantiate Configuration class

HTable hTable = new HTable(c, "student");       // Instantiate HTable class

Put P1 = new Put(Bytes.toBytes("row1"));   // Instantiate Put class

P1.add(Bytes.toBytes("id"),  Bytes.toBytes("name"),Bytes.toBytes("raj")); //update the data

hTable.put(P1);      // Save the put Instance to the HTable.

System.out.println("Data is updated");

hTable.close();      // close HTable

}

}