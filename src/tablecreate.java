import java.io.IOException;

import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.conf.Configuration;

public class tablecreate {

public static void main(String[] args) throws IOException {

Configuration c = HBaseConfiguration.create(); // Instantiate configuration class

HBaseAdmin ad = new HBaseAdmin(c);      // Instantiate HbaseAdmin class

// Instantiate table descriptor class

HTableDescriptor tdescriptor = new HTableDescriptor(TableName.valueOf("student"));

tdescriptor.addFamily(new HColumnDescriptor("id"));  // Add column families to tdescriptor

 ad.createTable(tdescriptor);   // Execute table using ad

System.out.println(" Table is created "); // It will print “Table is created”

}

}
