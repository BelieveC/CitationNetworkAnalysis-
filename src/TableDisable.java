import java.io.IOException;

import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.conf.Configuration;


public class TableDisable{

public static void main(String args[]) throws IOException, MasterNotRunningException {

Configuration c= HBaseConfiguration.create();      // Instantiate configuration class

HBaseAdmin ad = new HBaseAdmin(c);      // Instantiate HBaseAdmin class

Boolean bool = new Boolean(ad.isTableDisabled("student"));      // Verifying weather the table is disabled

System.out.println(bool);

if(!bool){

ad.disableTable("student");      // Disabe the table using HBaseAdmin object

System.out.println("Table is disabled"); // It will print “Table is disabled”

}

}

}
