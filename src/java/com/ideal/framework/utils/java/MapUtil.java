package com.ideal.framework.utils.java;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
 
/**
 * hashmap工具类
 * <p>
 * <example> 
 * var m = {key1:"1",key2:"2"};//创建map对象 <BR>
 * map.put(m,"name","123");//添加到map <BR>
 * println(map.get(m,"name"));//根据key获取值 <BR>
 * map.remove(m,"name");//根据key获取值 <BR>
 * println(map.size(m));//获取map个数 <BR>
 * map.putAll(m,{a:2});//合并map <BR>
 * println(map.containsKey(m, "a"));//判断map是否存在键a <BR>
 * println(map.containsValue(m, 2));//判断map是否存在键2 <BR>
 * var values = map.values(m); //获取map所有值集合<BR>
 * for(var v in values){ <BR>
 * println(v); } <BR>
 * var keys = map.keySet(m); //获取map所有值集合<BR>
 * for(var k in keys){ <BR>
 * println(k); }<BR>
 * var sets = map.entrySet(m); //获取map所有值集合<BR>
 * for(var set in sets){ <BR>
 * println(set); }<BR>
 * map.clear(m);//清空map <BR>
 * 
 * print(map.isEmpty(m));//清空map <BR>
 * </example>
 * </p>
 * 
 * @author don
 * @mail gogo123150@qq.com
 * @date 2014-12-30
 */
public class MapUtil {
 
    public void put(Object map, Object key, Object value) {
        if (map instanceof Map) {
            Map m = (Map) map;
            m.put(key, value);
        }
    }
 
    public void putAll(Object map, Object newmap) {
        if (map instanceof Map) {
            Map m = (Map) map;
            if (newmap instanceof Map) {
                m.putAll((Map) newmap);
            }
        }
    }
 
    public void remove(Object map, Object key) {
        if (map instanceof Map) {
            Map m = (Map) map;
            m.remove(key);
        }
    }
 
    public boolean containsKey(Object map, Object key) {
        if (map instanceof Map) {
            Map m = (Map) map;
            return m.containsKey(key);
        }
        return false;
    }
 
    public boolean containsValue(Object map, Object value) {
        if (map instanceof Map) {
            Map m = (Map) map;
            return m.containsValue(value);
        }
        return false;
    }
 
    public void clear(Object map) {
        if (map instanceof Map) {
            Map m = (Map) map;
            m.clear();
        }
    }
 
    public Object get(Object map, Object key) {
        if (map instanceof Map) {
            Map m = (Map) map;
            return m.get(key);
        }
        return null;
    }
 
    public boolean isEmpty(Object map) {
        if (map instanceof Map) {
            Map m = (Map) map;
            return m.isEmpty();
        }
        return true;
    }
 
    public Set entrySet(Object map) {
        if (map instanceof Map) {
            Map m = (Map) map;
            return m.entrySet();
        }
        return null;
    }
 
    public Set keySet(Object map) {
        if (map instanceof Map) {
            Map m = (Map) map;
            return m.keySet();
        }
        return null;
    }
 
    public Collection values(Object map) {
        if (map instanceof Map) {
            Map m = (Map) map;
            return m.values();
        }
        return null;
    }
 
    public int size(Object map) {
        if (map instanceof Map) {
            Map m = (Map) map;
            return m.size();
        }
        return 0;
    }
}