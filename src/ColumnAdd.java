import java.io.IOException;

import org.apache.hadoop.conf.Configuration;

import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.HBaseAdmin;

public class ColumnAdd{

public static void main(String args[]) throws IOException, MasterNotRunningException{

Configuration c = HBaseConfiguration.create();      // Instantiate configuration class.

HBaseAdmin ad = new HBaseAdmin(c); // Instantiate HBaseAdmin class.

HColumnDescriptor cDescriptor = new HColumnDescriptor("id"); //Instantiate columnDescriptor

ad.addColumn("student", cDescriptor);      // Add column family

System.out.println("coloumn is  added which name is id");

}

}
