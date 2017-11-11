import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import print.ASCIITable;

import javax.net.ssl.SSLContext;
import java.io.*;
import java.util.List;

public class main_api {

    private String table_name;
    private File csv_file=new File("tmp.csv");

    private FileWriter csv_write_instance;

    public main_api() throws IOException { }

    public  boolean insert(String table_name,String[] field_values,String[][] values) throws IOException {

        csv_write_instance=new FileWriter(new File(table_name+".csv"));
        CSVFormat csvFileFormat = CSVFormat.DEFAULT;
        CSVPrinter printer=new CSVPrinter(csv_write_instance,csvFileFormat);
        printer.printRecord((Object[]) field_values);
        for(String[] a:values) {
            printer.printRecord((Object[]) a);
        }
        printer.flush();
        printer.close();
        return false;
    }
    public  boolean insert_header(String[] field_values) throws IOException {

        csv_write_instance=new FileWriter(csv_file);
        CSVFormat csvFileFormat = CSVFormat.DEFAULT;
        CSVPrinter printer=new CSVPrinter(csv_write_instance,csvFileFormat);
        printer.printRecord((Object[]) field_values);
        printer.flush();
        printer.close();
        return false;
    }
    public List get(String table){
        FileReader csv_read_instance= null;
        try {
            csv_read_instance = new FileReader(new File(table+".csv"));
            CSVFormat csvFileFormat = CSVFormat.DEFAULT;
            CSVParser csvFileParser = new CSVParser(csv_read_instance, csvFileFormat);
            return csvFileParser.getRecords();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean delete(String table) throws IOException {

        List values =get(table);
        CSVRecord header= (CSVRecord) values.get(0);
        String[] header_a=new String[header.size()];
        for(int i=0;i<header.size();i++){
            header_a[i]=header.get(i);
        }
        csv_file.delete();
        insert_header(header_a);
        return false;
    }



    public void print(List obj){
        CSVRecord ob_zero= (CSVRecord) obj.get(0);
        String[] headers=new String[ob_zero.size()];
        for(int i=0;i<ob_zero.size();i++){
            headers[i]=ob_zero.get(i);
        }
        String[][] data=new String[obj.size()-1][ob_zero.size()];
        for(int i=1;i<obj.size();i++){
            CSVRecord objj= (CSVRecord) obj.get(i);
            for(int j=0;j<ob_zero.size();j++){
                data[i-1][j]=objj.get(j);
            }
        }
        if(obj.size()==1){
            data=new String[1][ob_zero.size()];
            for(int i=0;i<ob_zero.size();i++){
                data[0][i]="";
            }
        }
        ASCIITable.getInstance().printTable(headers, data);
    }

}
