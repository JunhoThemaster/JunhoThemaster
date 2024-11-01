package project.ticketlink.model.travel.product;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import project.ticketlink.model.travel.category.MainCategory;
import project.ticketlink.model.travel.category.MiddleCategory;
import project.ticketlink.model.travel.category.SubCategory;
import project.ticketlink.model.travel.director.Director;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter

@Entity
@Table(name = "product")
public class Product {


        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "prod_seq")
        @SequenceGenerator(name = "prod_seq", sequenceName = "PROD_SEQ",allocationSize = 1)
        @Column(name = "prod_no", nullable = false)
        private Long prodNo;

        @ManyToOne
        @JoinColumn(name = "mac_no", nullable = false)
        private MainCategory macNo;

        @ManyToOne
        @JoinColumn(name="mic_no",nullable = false)
        private MiddleCategory micNo;

        @ManyToOne
        @JoinColumn(name="suc_no")
        private SubCategory sucNo;


        @JsonIgnore
        @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
        private List<Flight> flights = new ArrayList<>();

        @Column(name = "pack_period", nullable = false, length = 100)
        private String packPeriod;

        @Column(name = "pack_name", nullable = false, length = 200)
        private String packName;

        @Column(name = "img")
        private String img;

        @Column(name = "pack_img1")
        private String packImg1;

        @Column(name = "pack_img2")
        private String packImg2;

        @Column(name = "pack_img3")
        private String packImg3;


        @Column(name = "chk", nullable = false)
        private String chk;

        @Column(name = "chk_img1")
        private String chkImg1;

        @Column(name = "chk_img2")
        private String chkImg2;

        @Column(name = "in_detail")
        private String inDetail;

        @Column(name = "out_detail")
        private String outDetail;

        @Column(name = "etc")
        private String etc;

        @Enumerated(EnumType.STRING)
        @Column(name = "pack_type", nullable = false)
        private PackType packType;

        @Column(name = "pdc_name")
        private String pdcName;

        @Column(name = "num_people", nullable = false)
        private BigInteger numPeople;

        @ManyToOne
        @JoinColumn(name = "direc_no", nullable = false)
        private Director direcNo;

        @Column(name = "e_charge")
        private BigInteger eCharge;


        @Column(name= "views",nullable = false)
        private Long views = 0L;


        public void incrementViews(){
                this.views++;
        }
        // Getters and Setters


        public enum PackType {
                PACKAGE_TOUR, // 패키지여행
                FREE_TOUR,    // 자유여행
                BUSANLOC,    // 모험여행
                CRUISE    // 휴양여행
        }




}

