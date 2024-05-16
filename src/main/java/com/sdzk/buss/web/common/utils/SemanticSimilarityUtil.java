package com.sdzk.buss.web.common.utils;

import com.sdzk.buss.api.model.ApiResultJson;
import com.sdzk.buss.web.dangersource.entity.TBDangerSourceEntity;
import org.apache.log4j.Logger;
import java.io.StringReader;
import java.io.IOException;
import java.util.*;

import org.jeecgframework.core.util.ApplicationContextUtil;
import org.jeecgframework.web.system.service.SystemService;
import org.wltea.analyzer.core.*;

/**
 * Created by hanxudong on 17-11-27.
 */
public class SemanticSimilarityUtil {

    private static final Logger logger = Logger.getLogger(SemanticSimilarityUtil.class);
    private static boolean dsDartDone = false;
    public static Map<String, Vector<String>> dsPartMap = new HashMap<>();
    //阈值
    public static double YUZHI = 0.05;


    //public void init
    /**
     * 返回百分比
     * @author: Administrator
     * @Date: 2015年1月22日
     * @param T1
     * @param T2
     * @return
     */
    public static double getSimilarity(Vector<String> T1, Vector<String> T2) throws Exception {
        int size = 0 , size2 = 0 ;
        if ( T1 != null && ( size = T1.size() ) > 0 && T2 != null && ( size2 = T2.size() ) > 0 ) {
            Map<String, double[]> T = new HashMap<String, double[]>();
            //T1和T2的并集T
            String index = null ;
            for ( int i = 0 ; i < size ; i++ ) {
                index = T1.get(i) ;
                if( index != null){
                    double[] c = T.get(index);
                    c = new double[2];
                    c[0] = 1;	//T1的语义分数Ci
                    c[1] = YUZHI;//T2的语义分数Ci
                    T.put( index, c );
                }
            }

            for ( int i = 0; i < size2 ; i++ ) {
                index = T2.get(i) ;
                if( index != null ){
                    double[] c = T.get( index );
                    if( c != null && c.length == 2 ){
                        c[1] = 1; //T2中也存在，T2的语义分数=1
                    }else {
                        c = new double[2];
                        c[0] = YUZHI; //T1的语义分数Ci
                        c[1] = 1; //T2的语义分数Ci
                        T.put( index , c );
                    }
                }
            }

            //开始计算，百分比
            Iterator<String> it = T.keySet().iterator();
            double s1 = 0 , s2 = 0, Ssum = 0;  //S1、S2
            while( it.hasNext() ){
                double[] c = T.get( it.next() );
                Ssum += c[0]*c[1];
                s1 += c[0]*c[0];
                s2 += c[1]*c[1];
            }
            //百分比
            return Ssum / Math.sqrt( s1*s2 );
        } else {
            throw new Exception("传入参数有问题！");
        }
    }

    /**
     * 返回百分比(按照分词出现次数)
     * @author: Administrator
     * @Date: 2015年1月22日
     * @param T1
     * @param T2
     * @return
     */
    public static double getSimilarity2(Vector<String> T1, Vector<String> T2) throws Exception {
        int size = 0 , size2 = 0 ;
        if ( T1 != null && ( size = T1.size() ) > 0 && T2 != null && ( size2 = T2.size() ) > 0 ) {
            Map<String, double[]> T = new HashMap<String, double[]>();
            //T1和T2的并集T
            String index = null ;
            int num = 0;
            for ( int i = 0 ; i < size ; i++ ) {
                index = T1.get(i) ;
                if( index != null&&T2.contains(index)){
                    num = num +1;
                }
            }


            //百分比
            return (float)num / size>YUZHI?(float)num / size:0;
        } else {
            return 0;
        }
    }

    public static Vector<String> participle( String str ) {

        Vector<String> str1 = new Vector<String>() ;//对输入进行分词
        try {
            StringReader reader = new StringReader( str );
            IKSegmenter ik = new IKSegmenter(reader,true);//当为true时，分词器进行最大词长切分
            Lexeme lexeme = null ;

            while( ( lexeme = ik.next() ) != null ) {
                str1.add( lexeme.getLexemeText() );
            }
            if( str1.size() == 0 ) {
                return null ;
            }
            //分词后
            //System.out.println("str分词后：" + str1);

        } catch ( IOException e1 ) {
            System.out.println("输入句子分词异常：["+str+"]");
        }
        return str1;
    }

    public static String getIdWithMaxSimilarity(String src, List<Map<String, Object>> dsList ){
        double maxSimilarity = 0.0;
        String maxId = null;
        int maxI = 0;
        long startTime = System.currentTimeMillis();    //获取开始时间
        Vector<String> T1 = participle(src);
        for(int i = 0 ; i < dsList.size() ; i++) {
            String dsDesc = (String)dsList.get(i).get("description");
            if("暂无".equals(dsDesc)){
                continue;
            }
            Vector<String> T2 = participle(dsDesc);
            try{
                double similarity = getSimilarity2(T1,T2);
                if(similarity>maxSimilarity){
                    maxSimilarity = similarity;
                    maxId = (String)dsList.get(i).get("id");
                    maxI = i;
                }
            }catch(Exception e){

            }
        }
        if(null!=maxId){
            System.out.println(maxId +":"+ maxSimilarity);
            System.out.println(dsList.get(maxI).get("description"));
        }
        long endTime = System.currentTimeMillis();    //获取结束时间
        System.out.println("程序运行时间：" + (endTime - startTime) + "ms");
        return maxId;
    }

    public static String getMatchedDangerSourceId(String src){
        double maxSimilarity = 0.0;
        String maxId = null;
        long startTime = System.currentTimeMillis();    //获取开始时间
        System.out.println("输入句子："+src);
        Vector<String> T = participle(src);
        System.out.println("输入分词：" + T);
        for (String key : SemanticSimilarityUtil.dsPartMap.keySet()) {
            Vector<String> cur_T = SemanticSimilarityUtil.dsPartMap.get(key);
            try{
                double similarity = SemanticSimilarityUtil.getSimilarity2(T,cur_T);
                if(similarity>maxSimilarity){
                    maxSimilarity = similarity;
                    maxId = key;
                }
            }catch(Exception e){
                return null;
            }
        }
        if(null!=maxId){
            SystemService systemService = (SystemService) ApplicationContextUtil.getContext().getBean("systemService");
            TBDangerSourceEntity dse = systemService.getEntity(TBDangerSourceEntity.class,maxId);
            System.out.println("输出句子：" + dse.getYeMhazardDesc());
            System.out.println("输出分词：" + SemanticSimilarityUtil.dsPartMap.get(maxId));
            System.out.println("相似度：" + maxSimilarity);
        }
        long endTime = System.currentTimeMillis();    //获取结束时间
        System.out.println("运行时间：" + (endTime - startTime) + "ms");
        return maxId;
    }

    public static String getMatchedDangerSourceId(String src,Map<String, Vector<String>> dsPartMap){
        double maxSimilarity = 0.0;
        String maxId = null;
        long startTime = System.currentTimeMillis();    //获取开始时间
        //System.out.println("输入句子："+src);
        Vector<String> T = participle(src);
        //System.out.println("输入分词：" + T);
        if(null==T) {
            return null;
        }
        for (String key : dsPartMap.keySet()) {
            Vector<String> cur_T = dsPartMap.get(key);
            if(null==cur_T) {
                continue;
            }
            try{
                double similarity = SemanticSimilarityUtil.getSimilarity2(T,cur_T);
                if(similarity>maxSimilarity){
                    maxSimilarity = similarity;
                    maxId = key;
                }
            }catch(Exception e){
                return null;
            }
        }
        if(null!=maxId){
            SystemService systemService = (SystemService) ApplicationContextUtil.getContext().getBean("systemService");
            TBDangerSourceEntity dse = systemService.getEntity(TBDangerSourceEntity.class,maxId);
            //System.out.println("输出句子：" + dse.getYeMhazardDesc());
            //System.out.println("输出分词：" + SemanticSimilarityUtil.dsPartMap.get(maxId));
            //System.out.println("相似度：" + maxSimilarity);
        }
        long endTime = System.currentTimeMillis();    //获取结束时间
        //System.out.println("运行时间：" + (endTime - startTime) + "ms");
        return maxId;
    }

    public static List<Map<String,Object>> getMatchedDangerSourceIds(String src,Map<String, Vector<String>> dsPartMap, int topN){
        long startTime = System.currentTimeMillis();    //获取开始时间
        Vector<String> T = participle(src);
        if(null==T) {
            return null;
        }
        List<Map<String,Object>> mapList= new ArrayList<Map<String, Object>>();
        List<Map<String,Object>> retMapList= new ArrayList<Map<String, Object>>();
        for (String key : dsPartMap.keySet()) {
            Vector<String> cur_T = dsPartMap.get(key);
            if(null==cur_T) {
                continue;
            }
            try{
                double similarity = SemanticSimilarityUtil.getSimilarity2(T,cur_T);
                if(similarity>=YUZHI) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("id", key);
                    map.put("similarity", similarity);
                    mapList.add(map);
                }
            }catch(Exception e){
                return null;
            }
        }
        if(mapList.size()>0){
            Collections.sort(mapList, new Comparator<Map<String, Object>>() {
                public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                    Double name1 = Double.valueOf(o1.get("similarity").toString()) ;//name1是从你list里面拿出来的一个
                    Double name2 = Double.valueOf(o2.get("similarity").toString()) ; //name1是从你list里面拿出来的第二个name
                    return name2.compareTo(name1);  //从大到小排序
                }
            });
            int size = mapList.size();
            retMapList = mapList.subList(0,size>topN?topN:size);
        }

        return retMapList;
    }
    public static void addDSPartMap(String id, String description){
        SemanticSimilarityUtil.dsPartMap.put(id, SemanticSimilarityUtil.participle(description));
    }
    public static void delDSPartMap(String id){
        SemanticSimilarityUtil.dsPartMap.remove(id);
    }
    public static void clearDSPartMap(){
        SemanticSimilarityUtil.dsPartMap.clear();
    }

    public static void setDSDartDone(boolean bool){
        dsDartDone = bool;
    }
    public static boolean getDSDartDone(){
        return dsDartDone;
    }
    public static void main(String[] args) throws IOException{

/*       long startTime = System.currentTimeMillis();    //获取开始时间
        //String text = "小孩不听话怎么办？吃东坡肉啊！";
        String text = "工区技术人员定现场支护方式时，必须符合要求并且符合现场实际；现场支护施工严格安全规程措施的要求支护";
        //创建分词对象
        Analyzer anal = new IKAnalyzer(true);
        StringReader reader = new StringReader(text);
        //分词
        TokenStream ts = anal.tokenStream("", reader);
        CharTermAttribute term = ts.getAttribute(CharTermAttribute.class);
        //遍历分词数据
        while(ts.incrementToken()){
            System.out.print(term.toString() + "|");
        }
        reader.close();
        long endTime = System.currentTimeMillis();    //获取结束时间
        System.out.println("程序运行时间：" + (endTime - startTime) + "ms");
        System.out.println();
*/
        long startTime = System.currentTimeMillis();    //获取开始时间
        //String str1 = "这个中文分词可不可以，用着方不方便。";
        //String str2 = "这个中文分词比较方便，用着方便还可以。";
        String str1 = "风筒漏风，造成迎头风量不足。第二部皮带";
        String str2 = "拐弯处风筒漏风严重，第二部皮带机头处皮带磨涨紧车。";
        Vector<String> T1 = participle(str1);
        long p1 = System.currentTimeMillis();    //获取结束时间
        System.out.println("程序运行至p1时间：" + (p1 - startTime) + "ms");
        Vector<String> T2 = participle(str2);
        long p2 = System.currentTimeMillis();    //获取结束时间
        System.out.println("程序运行至p2时间：" + (p2 - startTime) + "ms");
        try{
            double similarity = getSimilarity2(T1,T2);
            System.out.println(similarity);
        }catch(Exception e){

        }
        long endTime = System.currentTimeMillis();    //获取结束时间
        System.out.println("程序运行时间：" + (endTime - startTime) + "ms");
    }
}
