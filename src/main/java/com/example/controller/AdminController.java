package com.example.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.pojo.Admin;
import com.example.pojo.Student;
import com.example.service.AdminService;
import com.example.util.MD5;
import com.example.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "管理员控制器")
@RestController
@RequestMapping("sms/adminController")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @ApiOperation("删除单个或多个管理员信息")
    @DeleteMapping("/deleteAdmin")
    public Result deleteAdmin(
            @ApiParam("要删除的多个管理员的JSON数组") @RequestBody List<Integer> ids){
        adminService.removeByIds(ids);
        return Result.ok();
    }

    @ApiOperation("新增或修改管理员信息")
    @PostMapping("/saveOrUpdateAdmin")
    public Result saveOrUpdateAdmin(
            @ApiParam("JSON格式的管理员信息") @RequestBody Admin admin){

        Integer id = admin.getId();
        if(id == null || id == 0){
            admin.setPassword(MD5.encrypt(admin.getPassword()));
        }
        adminService.saveOrUpdate(admin);
        return Result.ok();
    }

    @ApiOperation("分页带条件查询管理员信息")
    @GetMapping("/getAllAdmin/{pageNo}/{pageSize}")
    public Result getAllAdmin(
            @ApiParam("分页查询的页码数") @PathVariable Integer pageNo,
            @ApiParam("分页查询的页大小") @PathVariable Integer pageSize,
            @ApiParam("分页查询的查询条件") String adminName){
        //分页信息封装Page对象
        Page<Admin> pageParam = new Page<>(pageNo,pageSize);
        //进行查询
        IPage<Admin> iPage = adminService.getAdminByOpr(pageParam,adminName);
        //封装Result对象返回
        return Result.ok(iPage);
    }
}
