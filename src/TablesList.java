import java.io.IOException;

import org.apache.hadoop.conf.Configuration;

import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.HBaseAdmin;

public class TablesList {

public static void main(String args[])throws IOException, MasterNotRunningException {

Configuration c = HBaseConfiguration.create();      // Instantiate a configuration class

HBaseAdmin ad = new HBaseAdmin(c);   // Instantiate HBaseAdmin class

HTableDescriptor[] tDescriptor = ad.listTables(); //  Give list of tables HBaseAdmin Object

for (int k=0; k<tDescriptor.length;k++ ){

System.out.println(tDescriptor[k].getNameAsString());      // Print all the table names.

}

}

}
