package com.near.friendly.repository;

import com.near.friendly.core.model.channel.Channel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/***********************************
 ** Created by Amghar on 24/10/2016. 
 ***********************************
 */
public interface ChannelRepository extends JpaRepository<Channel, Long> {

    Optional<Channel> findOneById(Long id);

}
