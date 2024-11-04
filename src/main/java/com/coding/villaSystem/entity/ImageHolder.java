package com.coding.villaSystem.entity;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class ImageHolder {

    //图片上传实体类！
    private FileStateEnum StateEnum;
    private String fileName;
    private MultipartFile multipartFile;
    private List<MultipartFile> list;

    public FileStateEnum getStateEnum() {
        return StateEnum;
    }

    public void setStateEnum(FileStateEnum stateEnum) {
        StateEnum = stateEnum;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public MultipartFile getMultipartFile() {
        return multipartFile;
    }

    public void setMultipartFile(MultipartFile multipartFile) {
        this.multipartFile = multipartFile;
    }

    public List<MultipartFile> getList() {
        return list;
    }

    public void setList(List<MultipartFile> list) {
        this.list = list;
    }

    //成功的构造器
    public ImageHolder(FileStateEnum stateEnum, MultipartFile multipartFile) {
        StateEnum = stateEnum;
        this.multipartFile = multipartFile;
    }

    public ImageHolder(FileStateEnum stateEnum, String fileName) {
        StateEnum = stateEnum;
        this.fileName = fileName;
    }

    //失败的构造器
    public ImageHolder(FileStateEnum stateEnum) {
        StateEnum = stateEnum;
    }
}
