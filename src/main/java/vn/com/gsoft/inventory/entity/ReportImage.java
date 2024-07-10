package vn.com.gsoft.inventory.entity;

import lombok.Data;

@Data
public class ReportImage {
    String nameImage;
    String pathImage;

    public ReportImage(String nameImage, String pathImage) {
        this.nameImage = nameImage;
        this.pathImage = pathImage;
    }
}
