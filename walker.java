import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import org.apache.commons.csv.CSVRecord;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;
import print.ASCIITable;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class walker extends mainBaseVisitor<String>
{
    @Override
    public String visitChat(mainParser.ChatContext context)
    {
        visitChildren(context);


        String command=context.message().command().get(0).getText();
        String table_name=context.message().table_name().get(0).getText();
        if(command.equalsIgnoreCase("Select ")){
            try {
                main_api a=new main_api();
                List<mainParser.Column_nameContext> column_name = context.message().column_name();
                mainParser.Column_namesContext column_names = column_name.get(0).column_names();
                if(column_names==null){
                    a.print( a.get(table_name));
                }
                else{
                    String[] header_print=new String[column_names.WORD().size()];
                    List obj= a.get(table_name);
                    String[][] data=new String[obj.size()-1][header_print.length];
                    CSVRecord headder= (CSVRecord) obj.get(0);

                    for(int i=0;i<column_names.WORD().size();i++){
                        header_print[i]=column_names.WORD(i).getText();
                    }
                    int var=0;
                    for(int i=1;i<obj.size();i++){
                        CSVRecord that_d= (CSVRecord) obj.get(i);
                        for(int j=0;j<that_d.size();j++){
                            if(header_print[var].equals(headder.get(j))){
                                data[i-1][var]=that_d.get(j);
                                var++;
                            }
                            if(var==header_print.length)
                                break;
                        }
                        var=0;
                    }

                    ASCIITable.getInstance().printTable(header_print, data);

                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }else if(command.equals("Delete ")) {
            try {
                main_api a=new main_api();
                a.delete(table_name);
                a.print(a.get(table_name));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if(command.equals("Insert ")){
            try {
                main_api a=new main_api();
                List<TerminalNode> columnt = context.message().insert_table().get(0).column_names().WORD();
                String[] arr=new String[columnt.size()];
                  for(int i=0;i<columnt.size();i++){
                      arr[i]=columnt.get(i).getText();
                  }
                List<mainParser.Field_ValuesContext> field_Values = context.message().field_Values();
                String[][] arrr=new String[field_Values.get(0).column_names().size()][field_Values.get(0).column_names().get(0).WORD().size()];
                for(int i=0;i<arrr.length;i++){
                    List<TerminalNode> ll = field_Values.get(0).column_names().get(i).WORD();
                    for(int j=0;j<ll.size();j++){
                        arrr[i][j]=ll.get(j).getText();
                    }
                }
                a.insert(table_name,arr,arrr);
                a.print(a.get(table_name));
            } catch (IOException e) {
                e.printStackTrace();
            }

        }



        return null;
    }

    @Override
    public String visitInsert_table(mainParser.Insert_tableContext context)
    {
        return visitChildren(context);
    }
}