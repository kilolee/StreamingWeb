package com.kilo.spark;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.kilo.dao.CourseClickCountDAO;
import com.kilo.domain.CourseClickCount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controller层
 * Created by kilo on 2018/4/12.
 */
@RestController
public class StatApp {
    private static Map<String, String> courses = new HashMap<>();
    static {
        courses.put("112", "Spark SQL慕课网日志分析");
        courses.put("128", "10小时入门大数据");
        courses.put("145", "深度学习之神经网络核心原理与算法");
        courses.put("146", "强大的Node.js在Web开发的应用");
        courses.put("131", "Vue+Django实战");
        courses.put("130", "Web前端性能优化");
    }
    @Autowired
    CourseClickCountDAO courseClickCountDAO;

//    @RequestMapping(value = "/course_clickcount_dynamic",method = RequestMethod.GET)
//    public ModelAndView courseClickCount() throws Exception {
//        List<CourseClickCount>list = courseClickCountDAO.query("imooc_course_clickcount","20180412");
//
//    for (CourseClickCount model : list){
//        model.setName(courses.get(model.getName().substring(9)));
//    }
//        JSONArray jsonArray = (JSONArray) JSONArray.toJSON(list);
//        ModelAndView mav=new ModelAndView("index");
//        mav.addObject("data_json",jsonArray);
//        return mav;
//    }

    @PostMapping(value = "/course_clickcount_dynamic")
    public List<CourseClickCount> courseClickCount() throws Exception {
        List<CourseClickCount>list = courseClickCountDAO.query("imooc_course_clickcount","20180412");

        for (CourseClickCount model : list){
            model.setName(courses.get(model.getName().substring(9)));
        }
        return list;
    }
    @GetMapping("/clickcount")
    public ModelAndView echarts(){
        return new ModelAndView("echarts");
    }

}
