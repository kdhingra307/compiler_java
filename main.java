import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

public class main {
    public static void main( String[] args) throws Exception
    {
        String insert="Insert into table t{a,b,c} {a1,b1,c1;a2,b2,c2;a3,b3,c3}";
        ANTLRInputStream inputStream = new ANTLRInputStream(
                insert);
        mainLexer markupLexer = new mainLexer(inputStream);
        CommonTokenStream commonTokenStream = new CommonTokenStream(markupLexer);
        mainParser markupParser = new mainParser(commonTokenStream);


        mainParser.ChatContext fileContext = markupParser.chat();
        walker visitor = new walker();
        visitor.visit(fileContext);
    }
}