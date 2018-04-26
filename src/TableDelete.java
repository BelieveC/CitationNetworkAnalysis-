import java.io.IOException;

import org.apache.hadoop.conf.Configuration;

import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.HBaseAdmin;

public class TableDelete {

public static void main(String[] args) throws IOException {

Configuration conf = HBaseConfiguration.create();      // Instantiate configuration class

HBaseAdmin ad = new HBaseAdmin(conf);      // Instantiate HBaseAdmin class

ad.disableTable("student");       // disable the table

ad.deleteTable("student");  // Delete the table

System.out.println("Table is deleted which name is student");

}

}
