package com.cristik.sample.test;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.util.TablesNamesFinder;

/**
 * @author cristik
 */
public class Test {

    public static void main(String[] args) throws JSQLParserException {
        String sql = "SELECT sc2.*, sl2.label_value FROM stlr_case sc2 LEFT JOIN stlr_label sl2 ON sc2.case_id = " +
                "sl2.case_id WHERE sc2.case_id IN ( SELECT t2.* from ( SELECT DISTINCT (sc.case_id) FROM stlr_case sc " +
                "LEFT JOIN stlr_label sl ON sc.case_id = sl.case_id WHERE sl.label_value in ( 1,2,3 ) limit 0,10 ) t2 )";
        Statement statement = CCJSqlParserUtil.parse(sql);
        statement.accept(new TablesNamesFinder());

        System.out.println(statement);
    }

}
