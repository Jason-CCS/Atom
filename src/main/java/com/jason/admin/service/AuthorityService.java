package com.jason.admin.service;

import com.jason.admin.domain.Authority;

import java.util.Optional;

public interface AuthorityService {
    /**
     * 根据ID查询 Authority
     * @param id
     * @return
     */
    Optional<Authority> getAuthorityById(Long id);
}
