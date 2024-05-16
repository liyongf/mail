package com.sdzk.buss.web.aqbzh.util;

import com.google.common.base.Splitter;
import com.google.common.collect.Maps;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil extends StringUtils {
    /**
     * 已英文逗号分割字符串 存List<Integer>集合
     *
     * @param str
     * @return
     */
    public static List<Integer> splitToListInt(String str) {
        List<String> strList =  Splitter.on(",").trimResults().omitEmptyStrings().splitToList(str);
        List<Integer> list = new ArrayList<Integer>();
        for(int i=0;i<strList.size();i++){
            list.add(Integer.parseInt(strList.get(i)));
        }
        return list;
    }

    /**
     * 已英文逗号分割字符串 存List<String>集合
     *
     * @param str
     * @return
     */
    public static List<String> splitToListStr(String str) {
        List<String> strList = Splitter.on(",").trimResults().omitEmptyStrings().splitToList(str);
        return strList;
    }


    /**
     * 一次性判断多个或单个对象为空。
     *
     * @param objects
     * @return 只要有一个元素为Blank，则返回true
     * @author
     */
    public static boolean isBlank(Object... objects) {
        Boolean result = false;
        for (Object object : objects) {
            if (object == null || "".equals(object.toString()) || "null".equals(object.toString().trim())) {
                result = true;
                break;
            }
        }
        return result;
    }

    /**
     * 获取随机字符串
     *
     * @param length 长度
     * @return 随机字符串
     */
    public static String getRandom(int length) {
        String val = "";
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            //输出数字还是字母
            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
            //字符串
            if ("char".equalsIgnoreCase(charOrNum)) {
                //区大写还是小写
                int choice = random.nextInt(2) % 2 == 0 ? 65 : 97;
                val += (char) (choice + random.nextInt(26));
            } else if ("num".equalsIgnoreCase(charOrNum)) {
                val += String.valueOf(random.nextInt(10));
            }

        }
        return val.toLowerCase();
    }

    /**
     * 一次性判断多个或单个对象不为空。
     *
     * @param objects
     * @return 只要有一个元素不为Blank，则返回true
     * @author
     */
    public static boolean isNotBlank(Object... objects) {
        return !isBlank(objects);
    }

    public static boolean isBlank(String... objects) {
        Object[] object = objects;
        return isBlank(object);
    }

    public static boolean isNotBlank(String... objects) {
        Object[] object = objects;
        return isNotBlank(object);
    }

    public static boolean isBlank(String str) {
        Object object = str;
        return isBlank(object);
    }

    public static boolean isNotBlank(String str) {
        Object object = str;
        return isNotBlank(object);
    }

    /**
     * 判断一个字符串在数组中存在几个
     *
     * @param baseStr
     * @param strings
     * @return
     */
    public static int indexOf(String baseStr, String[] strings) {
        if (baseStr == null || baseStr.length() == 0 || strings == null || strings.length == 0) {
            return 0;
        }
        int i = 0;
        for (String string : strings) {
            boolean result = baseStr.equals(string);
            i = result ? ++i : i;
        }

        return i;
    }

    /**
     * 去掉url中的路径，留下请求参数部分
     *
     * @param strURL url地址
     * @return url请求参数部分
     */
    private static String TruncateUrlPage(String strURL) {
        String strAllParam = null;
        String[] arrSplit = null;
        strURL = strURL.trim();
        arrSplit = strURL.split("[?]");
        if (strURL.length() > 1) {
            if (arrSplit.length > 1) {
                for (int i = 1; i < arrSplit.length; i++) {
                    strAllParam = arrSplit[i];
                }
            }
        }
        return strAllParam;
    }

    /**
     * 解析出url参数中的键值对
     * 如 "index.jsp?Action=del&id=123"，解析出Action:del,id:123存入map中
     *
     * @param URL url地址
     * @return url请求参数部分
     */
    public static Map<String, String> urlSplit(String URL) {
        Map<String, String> mapRequest = Maps.newLinkedHashMap();
        String[] arrSplit = null;
        String strUrlParam = TruncateUrlPage(URL);
        if (strUrlParam == null) {
            return mapRequest;
        }
        arrSplit = strUrlParam.split("[&]");
        for (String strSplit : arrSplit) {
            String[] arrSplitEqual = null;
            arrSplitEqual = strSplit.split("[=]");
            //解析出键值
            if (arrSplitEqual.length > 1) {
                //正确解析
                mapRequest.put(arrSplitEqual[0], arrSplitEqual[1]);
            } else {
                if (!"".equals(arrSplitEqual[0])) {
                    //只有参数没有值，不加入
                    mapRequest.put(arrSplitEqual[0], "");
                }
            }
        }
        return mapRequest;
    }

    /**
     * 判断一个字符串是否为JSONObject,是返回JSONObject,不是返回null
     *
     * @param args
     * @return
     */
    public static JSONObject isJSONObject(String args) {
        JSONObject result = null;
        if (isBlank(args)) {
            return result;
        }
        try {
            return JSONObject.fromObject(args.trim());
        } catch (Exception e) {
            return result;
        }
    }

    /**
     * 判断一个字符串是否为JSONArray,是返回JSONArray,不是返回null
     *
     * @param args
     * @return
     */
    public static JSONArray isJSONArray(Object args) {
        JSONArray result = new JSONArray();
        if (isBlank(args)) {
            return null;
        }
        if (args instanceof JSONArray) {
            JSONArray arr = (JSONArray) args;
            for (Object json : arr) {
                if (json != null && json instanceof JSONObject) {
                    result.add(json);
                } else {
                    result.add(JSONObject.fromObject(json));
                }
            }
            return result;
        } else {
            return null;
        }

    }

    public static String trimToEmpty(Object str) {
        return (isBlank(str) ? "" : str.toString().trim());
    }



    /**
     * 把Map转换成get请求参数类型,如 {"name"=20,"age"=30} 转换后变成 name=20&age=30
     *
     * @param map
     * @return
     */
    public static String mapToGet(Map<? extends Object, ? extends Object> map) {
        String result = "";
        if (map == null || map.size() == 0) {
            return null;
        }

        Set<? extends Object> keys = map.keySet();

        for (Object key : keys) {
            result += ("&" + key.toString() + "=" + map.get(key).toString());
        }
        return isBlank(result) ? result : result.substring(1);
    }

    /**
     * 把一串参数字符串,转换成Map 如"?a=3&b=4" 转换为Map{a=3,b=4}
     *
     * @param args
     * @return
     */
    public static Map<String, ? extends Object> getToMap(String args) {
        if (isBlank(args)) {
            return null;
        }

        args = args.trim();
        //如果开头是问号 去掉
        if (args.startsWith("?")) {
            args.substring(1);
        }
        String[] arsdArray = args.split("&");

        Map<String, Object> result = Maps.newHashMap();
        for (String ag : arsdArray) {
            if (ag.contains("=")) {
                //如果value或者key值里包含 "="号,以第一个"="号为主 ,如  name=0=3  转换后,{"name":"0=3"}, 如果不满足需求,请勿修改,自行解决.
                String[] keyValue = ag.split("=");
                String key = keyValue[0];
                String value = "";
                for (int i = 1; i < keyValue.length; i++) {
                    value += keyValue[i] + "=";
                }
                value = value.length() > 0 ? value.substring(0, value.length() - 1) : value;
                result.put(key, value);
            }
        }
        return result;
    }

    /**
     * 字符串转换成Unicode
     *
     * @param str
     * @return
     */
    public static String toUnicode(String str) {
        String[] as = new String[str.length()];
        String s1 = "";
        for (int i = 0; i < str.length(); i++) {
            int v = str.charAt(i);
            if (v >= 19968 && v <= 171941) {
                as[i] = Integer.toHexString(str.charAt(i) & 0xffff);
                s1 = s1 + "\\u" + as[i];
            } else {
                s1 += s1 + str.charAt(i);
            }
        }
        return s1;
    }

    /**
     * unicode转换字符串
     *
     * @param str
     * @return
     */
    public static String unicodeToString(String str) {
        StringBuffer stringBuffer = new StringBuffer();
        String[] hex = str.split("\\\\u");
        for (int i = 0; i < hex.length; i++) {
            //转换出每一个代码点
            int data = Integer.parseInt(hex[i], 16);
            //追加成String
            stringBuffer.append((char) data);
        }
        return stringBuffer.toString();
    }

    /**
     * 合并数据
     *
     * @param v
     * @return
     */
    public static String merge(Object... v) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < v.length; i++) {
            sb.append(v[i]);
        }
        return sb.toString();
    }



    /**
     * 判断字符串是否包含汉字
     *
     * @param txt
     * @return
     */
    public static Boolean containsCN(String txt) {
        if (isBlank(txt)) {
            return false;
        }
        for (int i = 0; i < txt.length(); i++) {
            String bb = txt.substring(i, i + 1);
            boolean cc = Pattern.matches("[\u4E00-\u9FA5]", bb);
            if (cc) return cc;
        }
        return false;
    }


    /**
     * Unicode文件的时候，会统一把BOM变成“\uFEFF"
     *
     * @param str
     * @return
     */
    public static String BOMTO2Str(String str) {
        return str.replaceAll("\uFEFF", "");
    }

    /**
     * 去掉HTML代码
     *
     * @param news
     * @return
     */
    public static String removeHtml(String news) {
        String s = news.replaceAll("amp;", "").replaceAll("<", "<").replaceAll(">", ">");

        Pattern pattern = Pattern.compile("<(span)?\\sstyle.*?style>|(span)?\\sstyle=.*?>", Pattern.DOTALL);
        Matcher matcher = pattern.matcher(s);
        String str = matcher.replaceAll("");

        Pattern pattern2 = Pattern.compile("(<[^>]+>)", Pattern.DOTALL);
        Matcher matcher2 = pattern2.matcher(str);
        String strhttp = matcher2.replaceAll(" ");


        String regEx = "(((http|https|ftp)(\\s)*((\\:)|：))(\\s)*(//|//)(\\s)*)?"
                + "([\\sa-zA-Z0-9(\\.|．)(\\s)*\\-]+((\\:)|(:)[\\sa-zA-Z0-9(\\.|．)&%\\$\\-]+)*@(\\s)*)?"
                + "("
                + "(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9])"
                + "(\\.|．)(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)"
                + "(\\.|．)(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)"
                + "(\\.|．)(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[0-9])"
                + "|([\\sa-zA-Z0-9\\-]+(\\.|．)(\\s)*)*[\\sa-zA-Z0-9\\-]+(\\.|．)(\\s)*[\\sa-zA-Z]*"
                + ")"
                + "((\\s)*(\\:)|(：)(\\s)*[0-9]+)?"
                + "(/(\\s)*[^/][\\sa-zA-Z0-9\\.\\,\\?\\'\\\\/\\+&%\\$\\=~_\\-@]*)*";
        Pattern p1 = Pattern.compile(regEx, Pattern.DOTALL);
        Matcher matchhttp = p1.matcher(strhttp);
        String strnew = matchhttp.replaceAll("").replaceAll("(if[\\s]*\\(|else|elseif[\\s]*\\().*?;", " ");


        Pattern patterncomma = Pattern.compile("(&[^;]+;)", Pattern.DOTALL);
        Matcher matchercomma = patterncomma.matcher(strnew);
        String strout = matchercomma.replaceAll(" ");
        String answer = strout.replaceAll("[\\pP‘’“”]", " ")
                .replaceAll("\r", " ").replaceAll("\n", " ")
                .replaceAll("\\s", " ").replaceAll("　", "");


        return answer;
    }


    /**
     * serializable toString
     *
     * @param serializable
     * @return
     */
    public static String serializableToString(Serializable serializable) {
        if (serializable == null) {
            return null;
        }

        try {
            return (String) serializable;
        } catch (Exception e) {
            return serializable.toString();
        }
    }

    /**
     * 将emoji标签转换成utf8字符集保存进数据库
     *
     * @param str 需要转换的字符串
     * @return
     */
    public static String emojiConvert(String str) {
        String patternString = "([\\x{10000}-\\x{10ffff}\\ud800-\\udfff])";
        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            try {
                matcher.appendReplacement(sb, "[[" + URLEncoder.encode(matcher.group(1), "UTF-8") + "]]");
            } catch (UnsupportedEncodingException e) {
                return str;
            }
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    /**
     * @param str 转换后的字符串
     * @return 转换前的字符串
     * @Description 还原utf8数据库中保存的含转换后emoji表情的字符串
     */
    public static String emojiRecovery(String str) {
        String patternString = "\\[\\[(.*?)\\]\\]";

        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(str);

        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            try {
                matcher.appendReplacement(sb,
                        URLDecoder.decode(matcher.group(1), "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                return "";
            }
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    /**
     * 首字母转小写
     *
     * @param str
     * @return
     */
    public static String toFirstUpCase(String str) {
        if (StringUtils.isBlank(str)) {
            return str;
        }
        return str.substring(0, 1).toLowerCase() + str.substring(1, str.length());
    }

    public static boolean isjson(String string) {
        try {
            com.alibaba.fastjson.JSONObject jsonStr = com.alibaba.fastjson.JSONObject.parseObject(string);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static Map<String, String> getAliPayParams(Map<String, String[]> requestParams) {
        Map<String, String> conversionParams = Maps.newHashMap();
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
            //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
            conversionParams.put(name, valueStr);
        }

        return conversionParams;
    }



    /**
     * float
     *
     * @param str1
     * @param str2
     */
    public static float levenshtein(String str1, String str2) {
        //计算两个字符串的长度。
        int len1 = str1.length();
        int len2 = str2.length();
        //建立上面说的数组，比字符长度大一个空间
        int[][] dif = new int[len1 + 1][len2 + 1];
        //赋初值，步骤B。
        for (int a = 0; a <= len1; a++) {
            dif[a][0] = a;
        }
        for (int a = 0; a <= len2; a++) {
            dif[0][a] = a;
        }
        //计算两个字符是否一样，计算左上的值
        int temp;
        for (int i = 1; i <= len1; i++) {
            for (int j = 1; j <= len2; j++) {
                if (str1.charAt(i - 1) == str2.charAt(j - 1)) {
                    temp = 0;
                } else {
                    temp = 1;
                }
                //取三个值中最小的
                dif[i][j] = min(dif[i - 1][j - 1] + temp, dif[i][j - 1] + 1,
                        dif[i - 1][j] + 1);
            }
        }
        System.out.println("字符串\"" + str1 + "\"与\"" + str2 + "\"的比较");
        //取数组右下角的值，同样不同位置代表不同字符串的比较
        System.out.println("差异步骤：" + dif[len1][len2]);
        //计算相似度
        float similarity = 1 - (float) dif[len1][len2] / Math.max(str1.length(), str2.length());
        return similarity;
    }

    //得到最小值
    private static int min(int... is) {
        int min = Integer.MAX_VALUE;
        for (int i : is) {
            if (min > i) {
                min = i;
            }
        }
        return min;
    }

    /**
     * 检查邮箱是否合法
     */
    public static Boolean checkEmail(String email) {
        if (email.matches("^[a-z0-9A-Z]+[- | a-z0-9A-Z . _]+@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-z]{2,}$")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 检查手机是否合法
     */
    public static Boolean checkPhone(String phone) {
        if (phone.matches("^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(166)|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$")) {
            return true;
        } else {
            return false;
        }
    }

}
