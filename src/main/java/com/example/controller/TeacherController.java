package com.example.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.pojo.Student;
import com.example.pojo.Teacher;
import com.example.service.TeacherService;
import com.example.util.MD5;
import com.example.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "教师控制器")
@RestController
@RequestMapping("sms/teacherController")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @ApiOperation("删除单个或多个教师信息")
    @DeleteMapping("/deleteTeacher")
    public Result deleteTeacher(
            @ApiParam("要删除的多个教师的JSON数组") @RequestBody List<Integer> ids){
        teacherService.removeByIds(ids);
        return Result.ok();
    }

    @ApiOperation("新增或修改教师信息")
    @PostMapping("saveOrUpdateTeacher")
    public Result saveOrUpdateTeacher(
            @ApiParam("JSON格式的教师信息") @RequestBody Teacher teacher){

        Integer id = teacher.getId();
        if(id == null || id == 0){
            teacher.setPassword(MD5.encrypt(teacher.getPassword()));
        }
        teacherService.saveOrUpdate(teacher);
        return Result.ok();
    }

    @ApiOperation("分页带条件查询教师信息")
    @GetMapping("/getTeachers/{pageNo}/{pageSize}")
    public Result getSTeachers(
            @ApiParam("分页查询的页码数") @PathVariable Integer pageNo,
            @ApiParam("分页查询的页大小") @PathVariable Integer pageSize,
            @ApiParam("分页查询的查询条件") Teacher teacher){
        //分页信息封装Page对象
        Page<Teacher> pageParam = new Page<>(pageNo,pageSize);
        //进行查询
        IPage<Teacher> iPage = teacherService.getTeacherByOpr(pageParam,teacher);
        //封装Result对象返回
        return Result.ok(iPage);
    }
}
