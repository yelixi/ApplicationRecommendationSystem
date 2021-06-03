package com.example.demo.util;

import com.example.demo.enums.KnowNewCollegeArea;
import com.example.demo.enums.UnifiedExaminationArea;
import com.example.demo.enums.UnknowNewCollegeArea;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * @author 11834
 */
public class CommonUtil {

    public static int examArea(String province){
        for(UnifiedExaminationArea area: UnifiedExaminationArea.values()){
            if(province.equals(area.toString())){
                return CommUtil.UNIFIED_EXAMINATION_AREA;
            }
        }
        for(UnknowNewCollegeArea area: UnknowNewCollegeArea.values()){
            if(province.equals(area.toString())){
                return CommUtil.UNKNOW_NEW_EXAMINATION_AREA;
            }
        }
        for(KnowNewCollegeArea area: KnowNewCollegeArea.values()){
            if(province.equals(area.toString())){
                return CommUtil.KNOW_NEW_EXAMINATION_AREA;
            }
        }
        return 3;
    }


    public static String transMapToString(Map map){
        java.util.Map.Entry entry;
        StringBuffer sb = new StringBuffer();
        for(Iterator iterator = map.entrySet().iterator(); iterator.hasNext();)
        {
            entry = (java.util.Map.Entry)iterator.next();
            sb.append(entry.getKey().toString()).append( "'" ).append(null==entry.getValue()?"":
                    entry.getValue().toString()).append (iterator.hasNext() ? "^" : "");
        }
        return sb.toString();
    }

    /**
     * 方法名称:transStringToMap
     * 传入参数:mapString 形如 username'chenziwen^password'1234
     * 返回值:Map
     */
    public static Map transStringToMap(String mapString){
        Map map = new HashMap();
        java.util.StringTokenizer items;
        for(StringTokenizer entrys = new StringTokenizer(mapString, "^"); entrys.hasMoreTokens();
            map.put(items.nextToken(), items.hasMoreTokens() ? ((Object) (items.nextToken())) : null))
            items = new StringTokenizer(entrys.nextToken(), "'");
        return map;
    }


}
