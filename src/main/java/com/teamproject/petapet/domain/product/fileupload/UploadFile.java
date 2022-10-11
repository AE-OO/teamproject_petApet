package com.teamproject.petapet.domain.product.fileupload;

import lombok.Data;

import javax.persistence.Embeddable;

@Data
@Embeddable
public class UploadFile {
    private String uploadFileName;
    private String storeFileName;

    public UploadFile(String uploadFileName, String storeFileName) {
        this.uploadFileName = uploadFileName;
        this.storeFileName = storeFileName;
    }

    public UploadFile() {
    }
}
