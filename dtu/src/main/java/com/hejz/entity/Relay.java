//package com.hejz.entity;
//
//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//import javax.persistence.*;
//import java.io.Serializable;
//import java.util.List;
//
///**
// * @author:hejz 75412985@qq.com
// * @create: 2023-01-04 06:50
// * @Description: 继电器
// */
//@Data
//@Entity(name = "relay")
//@org.hibernate.annotations.Table(appliesTo = "relay", comment = "继电器")
//@NoArgsConstructor
//@AllArgsConstructor
//@JsonIgnoreProperties(value = {"hibernateLazyInitializer"})
//public class Relay implements Serializable {
//    @Id
//    @SequenceGenerator(
//            name = "relay_sequence",
//            sequenceName = "relay_sequence",
//            allocationSize = 1
//    )
//    @GeneratedValue(
//            strategy = GenerationType.SEQUENCE,
//            generator = "relay_sequence"
//    )
//    @Column(
//            name = "id",
//            nullable = false,
//            columnDefinition = "bigint" + " COMMENT 'ID'"
//    )
//    private Long id;
//
//    @ManyToOne
//    @JoinColumn(name = "dtu_id", nullable = false)
//    private DtuInfo dtuInfo;
//
//    @Column(
//            name = "name",
//            nullable = true,
//            columnDefinition = "varchar(15)" + " COMMENT '名称'"
//    )
//    private String name;
//
//    /**
//     * 关联发出的链接
//     */
//    @Column(
//            name = "url",
//            nullable = true,
//            columnDefinition="varchar(255)"+" COMMENT '关联发出的链接'"
//    )
//    private String url;
//    /**
//     * 备注信息
//     */
//    @Column(
//            name = "remark",
//            nullable = true,
//            columnDefinition = "varchar(255)" + " COMMENT '备注信息'"
//    )
//    private String remark;
//
//    @JoinColumn(name = "instruction_definition_id",unique = true, nullable=false, updatable=false)
//    private InstructionDefinition instructionDefinition;
//
//    public Relay(Long dtuId, String name, String url, String remark,InstructionDefinition instructionDefinition) {
//        this.dtuInfo.setId(dtuId);
//        this.name = name;
//        this.url = url;
//        this.remark = remark;
//        this.instructionDefinition = instructionDefinition;
//    }
//}
