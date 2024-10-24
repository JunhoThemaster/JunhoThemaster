package project.ticketlink.model.travel.category;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="SubCate")
public class SubCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "suc_seq")
    @SequenceGenerator(name="suc_seq",sequenceName = "SUC_SEQ",allocationSize = 1)
    @Column(name="suc_no",nullable = false)
    private Long sucNo;

    @ManyToOne
    @JoinColumn(name = "mic_no",nullable = false)
    private MiddleCategory middleCategory;

    @ManyToOne
    @JoinColumn(name = "mac_no", nullable = false)
    private MainCategory mainCategory;


    @Column(name="suc_name")
    private String sucName;


}
