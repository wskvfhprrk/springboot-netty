package com.hejz.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author:hejz 75412985@qq.com
 * @create: 2023-01-28 10:15
 * @Description: eachart图数据模形
 */
@Data
public class EchartsDto {

    /**
     * total : 20
     * items : [{"order_no":"3C55b661-aF5C-FdE2-7Db3-E61Bab9fa20d","timestamp":1389267532334,"username":"Dorothy Taylor","price":3187.7,"status":"success"},{"order_no":"Fe1b2019-Ba24-FB9c-bFe0-aBDdA8771beA","timestamp":1389267532334,"username":"Sarah Garcia","price":11759.3,"status":"success"},{"order_no":"AC6dc1CC-DBE4-1Ffd-Ba7F-ED7eEAbCc71a","timestamp":1389267532334,"username":"Edward Jones","price":11554.67,"status":"success"},{"order_no":"C681DFdc-CE59-E136-eEAA-9c64cD9dc8b9","timestamp":1389267532334,"username":"Cynthia Miller","price":9448,"status":"pending"},{"order_no":"48f5cC81-bC67-4D8f-EBD8-aeD33bFEE74D","timestamp":1389267532334,"username":"George Davis","price":10347.56,"status":"pending"},{"order_no":"E4CAcb52-FeFb-92e1-6e3d-2DEDfc597FE2","timestamp":1389267532334,"username":"Ronald Miller","price":8682,"status":"success"},{"order_no":"647CdbeC-19f8-25B1-b760-921AdD2dcBb7","timestamp":1389267532334,"username":"Jeffrey Hall","price":12900.83,"status":"success"},{"order_no":"3172A33e-65B5-DDE3-E9fC-E9F4F4448ebD","timestamp":1389267532334,"username":"Dorothy Anderson","price":9080.7,"status":"success"},{"order_no":"3DEb38e7-84dc-eCc8-379E-F6dd0Dd9Dbf8","timestamp":1389267532334,"username":"Cynthia Smith","price":5561.84,"status":"success"},{"order_no":"49b524ff-c5Dd-90DD-c3B3-Fd4E6Bf4FA50","timestamp":1389267532334,"username":"Thomas Davis","price":7215.2,"status":"success"},{"order_no":"198C5325-a21f-1EE7-b920-5dd01888dbdb","timestamp":1389267532334,"username":"Cynthia Miller","price":7830,"status":"pending"},{"order_no":"ac3b3EEe-ECF2-E858-aa1c-fcbb42eaCEC2","timestamp":1389267532334,"username":"Christopher Wilson","price":1830.4,"status":"pending"},{"order_no":"acB0352b-DdBF-A3aC-EEdD-b7f75B64F3Ed","timestamp":1389267532334,"username":"Larry Hall","price":8443,"status":"pending"},{"order_no":"52ffdd72-3693-65dc-aABD-2c778dc20B2b","timestamp":1389267532334,"username":"Susan Lopez","price":8968.84,"status":"pending"},{"order_no":"D5aFca7b-F715-9bC3-f5e8-b0dD0fF31B28","timestamp":1389267532334,"username":"Dorothy Moore","price":13534.74,"status":"pending"},{"order_no":"f8E83dae-3Bf6-8AB3-dfC3-9B9EAc21B8c2","timestamp":1389267532334,"username":"Ronald Clark","price":2193.3,"status":"pending"},{"order_no":"b2BC8aDA-66DA-81A3-8bBb-0cb6dcD1C131","timestamp":1389267532334,"username":"Betty Brown","price":7384.4,"status":"pending"},{"order_no":"31DBbAeC-3C19-3184-1e7D-736D7C7034dE","timestamp":1389267532334,"username":"William Anderson","price":13233,"status":"success"},{"order_no":"86fCb6fD-C1EE-6E8e-2eeB-bf1EAaF6Ccbf","timestamp":1389267532334,"username":"Daniel Thompson","price":7817.8,"status":"pending"},{"order_no":"d6b897c3-88Dc-Bc6B-aed9-6a320e6E49Ce","timestamp":1389267532334,"username":"Eric Perez","price":9683.35,"status":"success"}]
     */

    private int total;
    private List<ItemsBean> items;


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ItemsBean {
        /**
         * order_no : 3C55b661-aF5C-FdE2-7Db3-E61Bab9fa20d
         * timestamp : 1389267532334
         * username : Dorothy Taylor
         * price : 3187.7
         * status : success
         */

        private String order_no;
        private long timestamp;
        private String username;
        private double price;
        private String status;
    }
}
