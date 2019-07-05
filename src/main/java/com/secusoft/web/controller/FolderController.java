package com.secusoft.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.secusoft.web.core.exception.BizExceptionEnum;
import com.secusoft.web.model.FolderBean;
import com.secusoft.web.model.ResultVo;
import com.secusoft.web.service.FolderService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

/**
 *  收藏文件夹相关接口
 *  @author huanghao
 */
@RestController
@CrossOrigin(value = "*", maxAge = 3600)
@Api(value="Folder-Controller" , description="收藏文件夹相关接口")
public class FolderController {
    @Autowired
    FolderService folderService;

    /**
     * 增加文件夹 后返回最新的 时间排序文件夹列表
     * @param folderBean folderName
     * @return
     */
    @PostMapping("/addFolder")
    @ApiOperation("增加收藏文件夹")
    @ApiResponses({
            @ApiResponse(code = 1001010, message = "成功"),
            @ApiResponse(code = 1001011, message = "数据重复")})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "folderName" ,value = "文件夹名称", required = true, dataType = "String",paramType = "query")
    })
    public ResponseEntity<ResultVo> addFolder(@RequestBody FolderBean folderBean){
        ResultVo resultVo = folderService.addFolder(folderBean);
        return new ResponseEntity<ResultVo>(resultVo, HttpStatus.OK);
    }

    /**
     * 删除文件夹
     * @param folderBean id
     * @return
     */
    @PostMapping("/removeFolder")
    @ApiOperation("删除收藏文件夹")
    @ApiResponses({
            @ApiResponse(code = 1001010, message = "成功")})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id" ,value = "文件夹Id", required = true, dataType = "String",paramType = "query")
    })
    public ResponseEntity<ResultVo> removeFolder(@RequestBody FolderBean folderBean){
        ResultVo resultVo = folderService.removeFolder(folderBean.getId());
        return  new ResponseEntity<ResultVo>(resultVo, HttpStatus.OK);
    }

    /**
     * 修改文件夹状态
     * @param folderBean id
     * @return
     */
    @PostMapping("setFolderStatus")
    @ApiOperation("修改收藏文件夹状态")
    @ApiResponses({
            @ApiResponse(code = 1001010, message = "成功")})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id" ,value = "文件夹Id", required = true, dataType = "String",paramType = "query")
    })
    public ResponseEntity<ResultVo> setFolderStatus(@RequestBody FolderBean folderBean){
        ResultVo resultVo = folderService.setFolderStatus(folderBean);
        return  new ResponseEntity<ResultVo>(resultVo, HttpStatus.OK);
    }

    /**
     * 修改文件夹名称
     * @param folderBean id 、folderName
     * @return
     */
    @PostMapping("setFolderName")
    @ApiOperation("修改收藏文件夹名称")
    @ApiResponses({
            @ApiResponse(code = 1001010, message = "成功"),
            @ApiResponse(code = 1001011, message = "数据重复")})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id" ,value = "文件夹Id", required = true, dataType = "String",paramType = "query"),
            @ApiImplicitParam(name = "folderName" ,value = "文件夹名称", required = true, dataType = "String",paramType = "query")
    })
    public ResponseEntity<ResultVo> setFolderName(@RequestBody FolderBean folderBean){
        ResultVo resultVo = folderService.setFolderName(folderBean);
        return new ResponseEntity<ResultVo>(resultVo, HttpStatus.OK);
    }

    /**
     * 根据状态展示相应文件夹
     * @param folderBean status 0待办结  1已结案
     * @return
     */
    @PostMapping("getFolderByStatus")
    @ApiOperation("根据状态展示文件夹")
    @ApiResponses({
            @ApiResponse(code = 1001010, message = "成功")})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "status" ,value = "状态", required = true, dataType = "String",paramType = "query"),
            @ApiImplicitParam(name = "pageSize" ,value = "分页大小", required = true, dataType = "String",paramType = "query"),
            @ApiImplicitParam(name = "pageNumber" ,value = "当前页数", required = true, dataType = "String",paramType = "query")
    })
    public ResponseEntity<ResultVo> getFolder(@RequestBody FolderBean folderBean){
        ResultVo resultVo = folderService.getFolderByStatus(folderBean);
        return new ResponseEntity<ResultVo>(resultVo, HttpStatus.OK);
    }

    /**
     * 根据文件夹名称模糊查询
     * @param folderBean
     * @return
     */
    @PostMapping("getFolderByName")
    @ApiOperation("名称模糊查询文件夹")
    @ApiResponses({
            @ApiResponse(code = 1001010, message = "成功")})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "status" ,value = "状态 0未结案  1结案", required = true, dataType = "String",paramType = "query"),
            @ApiImplicitParam(name = "name" ,value = "模糊输入的文件名", required = true, dataType = "String",paramType = "query")
    })
    public ResponseEntity<ResultVo> getFolderByName(@RequestBody FolderBean folderBean){
        ResultVo resultVo = folderService.getFolderByName(folderBean);
        return new ResponseEntity<ResultVo>(resultVo, HttpStatus.OK);
    }

    /**
     * 展示所有的文件夹 
     * @return
     */
    @PostMapping("readAllFolder")
    @ApiOperation("按照创建时间展示所有的文件夹")
    public ResponseEntity<ResultVo> readAllFolder(){
        ResultVo resultVo = folderService.getAllFolder();
        return new ResponseEntity<ResultVo>(resultVo,HttpStatus.OK);
    }

    /**
     * 展示文件夹详情
     * @return
     */
   @PostMapping("readFolder")
   @ApiOperation("展示某个文件夹详情")
    public ResponseEntity<ResultVo> readFolder(@RequestBody FolderBean folderBean){
       ResultVo resultVo = folderService.getFolder(folderBean.getId());
       return new ResponseEntity<>(resultVo, HttpStatus.OK);
   }
}
