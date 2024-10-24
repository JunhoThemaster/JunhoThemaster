package project.ticketlink.model.travel.category;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="MainCategory")
public class MainCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "mac_seq")
    @SequenceGenerator(name = "mac_seq",sequenceName = "MAC_SEQ", allocationSize = 1)
    @Column(name="mac_no",nullable = false)
    private Long macNo;

    @Column(name="mac_name",nullable = false)
    private String macName;

}
