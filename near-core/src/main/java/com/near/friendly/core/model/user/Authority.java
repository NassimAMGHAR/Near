package com.near.friendly.core.model.user;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 *
 */
@Data
@Entity
@Table(name = "nr_authority")
@EqualsAndHashCode(of = "name")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Authority implements Serializable {

    @Id
    @NotNull
    @Column(length = 50)
    @Size(min = 0, max = 50)
    private String name;

}
