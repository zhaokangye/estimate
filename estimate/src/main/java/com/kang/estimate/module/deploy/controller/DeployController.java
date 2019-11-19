package com.kang.estimate.module.deploy.controller;

import com.kang.estimate.core.base.controller.BaseController;
import com.kang.estimate.core.error.BussinessException;
import com.kang.estimate.core.error.EmBussinessError;
import com.kang.estimate.core.response.CommonReturnType;
import com.kang.estimate.module.deploy.service.DeployService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author kang
 */
@RestController
@RequestMapping("/deploy")
public class DeployController extends BaseController {

    @Autowired
    private DeployService deployService;

    /**
     * 获得webapps的路径
     * @param host
     * @return
     */
    @PostMapping("/path")
    public CommonReturnType findPath(@RequestParam String host,@RequestParam String pattern){
        switch (pattern){
            case "webapps":
                return CommonReturnType.create(deployService.findWebappsPath(host));
            case "bin":
                return CommonReturnType.create(deployService.findBinPath(host));
            default:
                throw new BussinessException(EmBussinessError.PATH_NOT_FOUND);
        }

    }

    /**
     * 文件下拉框数据
     * @return
     */
    @GetMapping("/file/list")
    public CommonReturnType selectFileList(){
        return CommonReturnType.create(deployService.selectFileList());
    }

    /**
     * 上传到服务器
     * @param file
     * @return
     */
    @PostMapping("/upload")
    @ResponseBody
    public CommonReturnType upload(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return CommonReturnType.create("上传失败，请选择文件","fail");
        }
        return CommonReturnType.create(deployService.uploadFile(file));
    }

    /**
     * 部署到远端linux服务器
     * @param host
     * @param src
     * @param dst
     * @return
     */
    @PostMapping("/deploy")
    public CommonReturnType deployApp(@RequestParam String host,@RequestParam String src,@RequestParam String dst){
        return CommonReturnType.create(deployService.deployApp(host,src,dst));
    }

    /**
     * 上传进度
     * @param src
     * @return
     */
    @PostMapping("/progress")
    public CommonReturnType uploadProgress(@RequestParam String src){
        return CommonReturnType.create(deployService.uploadProgress(src));
    }

}
