package com.planify.main.api.roles.infrastructure;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.planify.main.api.roles.domain.Roles;

/* JPA 사용시 @Repository, Mybatis 사용시 @Mapper 사용 스텍별 파일명 변경 */
@Repository
public interface RolesRepository extends JpaRepository<Roles, Long> {
	Optional<Roles> findByRoleName(String roleName);
}
