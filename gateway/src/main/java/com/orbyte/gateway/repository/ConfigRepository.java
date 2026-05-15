package com.orbyte.gateway.repository;

import com.orbyte.gateway.dto.dtoimpl.ConfigKeyvalueDto;
import com.orbyte.gateway.entity.Config;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConfigRepository extends JpaRepository<Config, String> {
    @Query(value = "SELECT c.name, c.value FROM config c", nativeQuery = true)
    public List<ConfigKeyvalueDto> findAllConfigs();

    @Query(value = "SELECT c.name, c.value FROM config c where c.name=? ",nativeQuery = true)
    public ConfigKeyvalueDto findByName(String name);
}
