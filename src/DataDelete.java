import java.io.IOException;

import org.apache.hadoop.conf.Configuration;

import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.util.Bytes;

public class DataDelete {

public static void main(String[] args) throws IOException {

Configuration c = HBaseConfiguration.create();// Instantiate Configuration class

HTable table = new HTable(c, "student");       // Instantiate HTable class

Delete delete = new Delete(Bytes.toBytes("row1"));       // Instantiating Delete class

delete.deleteColumn(Bytes.toBytes("college"), Bytes.toBytes("id"));

table.delete(delete);        // delete the data

table.close();        // close the HTable object

System.out.println("Data  is deleted");

}

}
