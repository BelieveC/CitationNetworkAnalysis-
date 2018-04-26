import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.MasterNotRunningException;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.*;

public class TableEnable{

public static void main(String args[]) throws IOException, MasterNotRunningException{

Configuration c = HBaseConfiguration.create();      // Instantiate configuration class

HBaseAdmin ad = new HBaseAdmin(c);   // Instantiate HBaseAdmin class

Boolean bool = ad.isTableEnabled("student");      //check whether the table is enabled

System.out.println(bool);

if(!bool){

ad.enableTable("student");

System.out.println("Table is Enabled");     // Enable the table using HBaseAdmin object

}

}

}