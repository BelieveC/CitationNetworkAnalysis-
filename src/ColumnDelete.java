import java.io.IOException;

import org.apache.hadoop.conf.Configuration;

import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.HBaseAdmin;

public class ColumnDelete{

public static void main(String args[])throws IOException, MasterNotRunningException{

Configuration c = HBaseConfiguration.create();      // Instantiate configuration class.

HBaseAdmin ad = new HBaseAdmin(c);      // Instantiate HBaseAdmin class.

ad.deleteColumn("student","id");  // Deleting a column family

System.out.println("coloumn  is deleted which name is id");

}

}