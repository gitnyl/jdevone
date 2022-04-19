package com.project.jdevone.jdev.repository.mybatis;

import com.project.jdevone.jdev.model.TestVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface TestRepository {
    TestVo selectTest(@Param("idx") Integer idx);
}
