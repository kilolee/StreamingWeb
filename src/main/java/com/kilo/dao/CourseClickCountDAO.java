package com.kilo.dao;

import com.kilo.domain.CourseClickCount;
import com.kilo.utils.HBaseUtils;
import org.jcodings.util.Hash;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 实战课程访问数量
 * 数据访问层
 * Created by kilo on 2018/4/12.
 */
@Component
public class CourseClickCountDAO {

    /**
     * 根据天查询
     *
     * @param day
     * @return
     */
    public List<CourseClickCount> query(String tableName, String day) throws Exception {
        List<CourseClickCount> list = new ArrayList<>();

        Map<String, Long> map = HBaseUtils.getInstance().query(tableName, day);
        for (Map.Entry<String, Long> entry : map.entrySet()) {
            CourseClickCount model = new CourseClickCount();
            model.setName(entry.getKey());
            model.setValue(entry.getValue());
            list.add(model);
        }
        return list;
    }


    public static void main(String[] args) throws Exception {
        CourseClickCountDAO dao = new CourseClickCountDAO();
        List<CourseClickCount> list = dao.query("imooc_course_clickcount", "20180412");
        for (CourseClickCount model : list) {
            System.out.println(model.getName() + ":" + model.getValue());
        }
    }

}
