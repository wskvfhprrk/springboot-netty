package com.hejz.dtu.vo;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author:hejz 75412985@qq.com
 * @create: 2023-03-02 00:02
 * @Description: echartsVo
 */
@Data
public class EchartsVo {
    private String title;

    private List<String> labels;
    private List<DatasetsBean> datasets;

    @Data
    public static class DatasetsBean {
        /**
         * label : Data
         * data : [10,20,30,40,50,60,70]
         * backgroundColor : #3f51b5
         */

        private String label;
        private String backgroundColor;
        private List<String> data;
        private String type;
    }
}
