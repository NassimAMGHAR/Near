package com.near.friendly.core.model.channel;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NotFound;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/***********************************
 ** Created by Amghar on 24/10/2016. 
 ***********************************
 */
@Data
@Entity
@Table(name = "nr_channel")
@EqualsAndHashCode(of = {"id"})
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Channel implements Serializable {

    @JsonIgnore
    @GeneratedValue
    private Long id;//real primary key

    @Id
    @NotNull
    @GeneratedValue
    private Long channelId;

    @Size(max = 50)
    @Column(name = "name", length = 50)
    private String name;

    @Size(max = 255)
    @Column(name = "description")
    private String description;
}
