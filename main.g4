grammar main;

/*
 * Parser Rules
 */

chat                : message EOF ;


message             : (command | column_name | type_of_command | table | table_name | insert_table | field_Values)+ ;



command             : (SELECT | INSERT | DELETE) WHITESPACE;

type_of_command     : (FROM | INTO) WHITESPACE;

table               : (TABLE) WHITESPACE;
table_name			: WORD;
insert_table		: '{' column_names  '}' WHITESPACE;
column_name 		: ('*' | column_names) WHITESPACE;
column_names		: (WORD ',')+ WORD;

field_Values        : '{' (column_names ';')+ column_names '}';


fragment S          : ('S'|'s') ;
fragment E          : ('E'|'e') ;
fragment L          : ('L'|'l') ;
fragment C          : ('C'|'c') ;
fragment T          : ('T'|'t') ;
fragment I          : ('I'|'i') ;
fragment N          : ('N'|'n') ;
fragment R          : ('R'|'r') ;
fragment D          : ('D'|'d') ;
fragment F          : ('F'|'f') ;
fragment O		    : ('O'|'o') ;
fragment M			: ('M'|'m') ;
fragment B			: ('B'|'b') ;
fragment A			: ('A'|'a') ;


fragment LOWERCASE  : [a-z] ;
fragment UPPERCASE  : [A-Z] ;
fragment DIGIT      : [0-9] ;


SELECT              : S E L E C T ;

INSERT              : I N S E R T ;

DELETE				: D E L E T E ;

FROM				: F R O M ;

INTO   				: I N T O ;

TABLE				: T A B L E ;

WORD                : (LOWERCASE | UPPERCASE | '_' | DIGIT)+ ;

WHITESPACE          : (' ' | '\t')+ ;

NEWLINE             : ('\r'? '\n' | '\r')+ ;

TEXT                : ('['|'(') ~[\])]+ (']'|')');
