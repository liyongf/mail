package com.liyf.boot.mail.util;

import com.liyf.boot.entity.TestCase;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class ExclUtils {

    public static void main(String[] args) {
        ArrayList<TestCase> list = new ArrayList<>();

        File file = new File("E:\\360MoveData\\Users\\admin\\Desktop\\岗位信息导入模板.xls");
        String sheetName = "隐患数量";
        String code="";
        if(sheetName.equals("入井")){
            code="0";
        }else if(sheetName.equals("反三违数量")){
            code="1";
        }else if(sheetName.equals("带班数量")){
            code="2";
        }else if(sheetName.equals("隐患数量")){
            code="3";
        }

        try {
            //XSSFWorkbook 是读取2007以上版本的表格，及.xlsx结尾,所以使用XSSFWorkbook
            //HSSFWorkbook 是读取2003版本的表格，及.xls结尾
            HSSFWorkbook xssfWorkbook = new HSSFWorkbook(new FileInputStream(file));
            //获取表
            Sheet sheet = xssfWorkbook.getSheet(sheetName);
            //遍历表格
            //起始行，第一行是标题栏不需要读取
            int firstRowIndex = sheet.getFirstRowNum() + 1;
            //结束行
            int lastRowIndex = sheet.getLastRowNum();
            //遍历行
            for (int rIndex = firstRowIndex; rIndex <= lastRowIndex; rIndex++) {
                Row row = sheet.getRow(rIndex);
                if(row != null){
                    //存储遍历的每行用例的元素值
                    String[] listcase = new String[row.getLastCellNum() - row.getFirstCellNum()];
                    //起始列
                    int firstCellIndex = row.getFirstCellNum();
                    //结束列
                    int lastCellIndex = row.getLastCellNum();
                    for (int cIndex = firstCellIndex; cIndex <= lastCellIndex; cIndex++) {
                        Cell cell = row.getCell(cIndex);
                        if(cell != null){
                            listcase[cIndex] = cell.toString();
                        }
                    }
                    //将每条测试用例存储到一个testcase对象
                    TestCase testCase = new TestCase();
                    testCase.setDapartName(listcase[0]);
                    testCase.setPostName(listcase[1]);
                    testCase.setPastType(listcase[2]);
                    testCase.setQuantity(listcase[3]);
                    testCase.setDeduction(listcase[4]);
                    list.add(testCase);
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        for(int i = 0; i < list.size();i++) {
            TestCase testCase=list.get(i);
           String sql ="INSERT INTO t_b_assessment_target VALUES (  REPLACE (md5(UUID()), '-', ''),  (   SELECT    post.dept_name   FROM    t_b_post_manage post   LEFT JOIN t_s_depart depart ON post.dept_name = depart.id   left join (select typecode,typename from t_s_type where typegroupid='8280808185c344920185c367df270010') type on  post.post_type=type.typecode   WHERE    post.post_name ='"+testCase.getPostName()+"'   AND depart.departname ='"+testCase.getDapartName()+"'   and type.typename='"+testCase.getPastType()+"'   AND post.is_delete = '0'   AND post.belong_mine = 'ff80808171f309530171f315a6eb000f'   LIMIT 0,   1  ),  (   SELECT    post.id   FROM    t_b_post_manage post   LEFT JOIN t_s_depart depart ON post.dept_name = depart.id   left join (select typecode,typename from t_s_type where typegroupid='8280808185c344920185c367df270010') type on  post.post_type=type.typecode   WHERE    post.post_name ='"+testCase.getPostName()+"'   AND depart.departname ='"+testCase.getDapartName()+"'   and type.typename='"+testCase.getPastType()+"'   AND post.is_delete = '0'   AND post.belong_mine = 'ff80808171f309530171f315a6eb000f'   LIMIT 0,   1  ),  '"+code+"',  '"+testCase.getQuantity()+"',  '0',  '"+testCase.getDeduction()+"',  now(),  'admin',  '管理员',  NULL,  NULL,  NULL,  '0',  'ff80808171f309530171f315a6eb000f' );";
            System.out.println(sql);
        }
    }
}
