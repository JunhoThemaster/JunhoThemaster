package project.ticketlink.model.travel.category;


import com.sun.tools.javac.Main;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="MiddleCate")
public class MiddleCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "mic_seq")
    @SequenceGenerator(name="mic_seq",sequenceName = "MIC_SEQ",allocationSize = 1)
    @Column(name="mic_no",nullable = false)
    private Long micNO;

    @ManyToOne
    @JoinColumn(name = "mac_no",nullable = false)
    private MainCategory mainCategory;

    @Column(name="mic_name",nullable = false)
    private String name;


}
