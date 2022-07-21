package com.example.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.pojo.Grade;
import com.example.service.GradeService;
import com.example.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "年级控制器")
@RestController
@RequestMapping("sms/gradeController")
public class GradeController {

    @Autowired
    private GradeService gradeService;

    @ApiOperation("获取全部年级")
    @GetMapping("/getGrades")
    public Result getGrades(){
        List<Grade> grades = gradeService.list();
        return Result.ok(grades);
    }

    @ApiOperation("删除grade信息")
    @DeleteMapping("/deleteGrade")
    public Result deleteGrade(
            @ApiParam("要删除的所有grade的id的JSON集合") @RequestBody List<Integer> ids){

        gradeService.removeByIds(ids);

        return Result.ok();
    }

    @ApiOperation("新增或修改grade,有id属性修改,没有则增加")
    @PostMapping("/saveOrUpdateGrade")
    public Result saveOrUpdateGrade(
            @ApiParam("JSON格式的grade对象") @RequestBody Grade grade){
        //接收参数
        //调用服务层方法完成增加或修改
        gradeService.saveOrUpdate(grade);
        return Result.ok();
    }

    @ApiOperation("根据年级名称模糊查询,带分页")
    @GetMapping("/getGrades/{pageNo}/{pageSize}")
    public Result getGrades(
            @ApiParam("分页查询的页码数") @PathVariable("pageNo") Integer pageNo,
            @ApiParam("分页查询的页大小") @PathVariable("pageSize") Integer pageSize,
            @ApiParam("分页查询的模糊匹配名称") String gradeName){

        //分页 带条件查询
        Page<Grade> page = new Page<>(pageNo,pageSize);
        //封装result对象并返回
        IPage<Grade> pageRs = gradeService.getGradeByOpr(page,gradeName);

         return Result.ok(pageRs);
    }
}
