package project.ticketlink.model.travel.director;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity

@Getter
@Setter
@Table(name="director")
public class Director {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "direc_seq")
    @SequenceGenerator(name="direc_seq",sequenceName = "DIREC_SEQ",allocationSize = 1)
    @Column(name="dir_no",nullable = false)
    private Long dirNo;

    @Column(name="dir_name")
    private String dirName;

    @Column(name="dir_email")
    private String dirEmail;

    @Column(name="dir_img")
    private String dirImg;

    @Column(name="dir_phone")
    private String dirPhone;




}
