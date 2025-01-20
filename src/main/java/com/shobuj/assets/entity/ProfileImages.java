package com.shobuj.assets.entity;

import com.shobuj.entity.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileImages {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Lob
    @Column(length = 1000000)
    private byte[] displayPicture;

    @OneToOne(cascade = CascadeType.ALL)
    private User user;
}


