package com.project.jdevone.jdev.repository.mybatis;

import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import com.project.jdevone.jdev.model.TestVo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import static org.assertj.core.api.Assertions.assertThat;

@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class TestRepositoryTest {

    @Autowired
    private TestRepository testRepository;

    @Test
    @DisplayName("Mybatis Test")
    public void mybatisTest() throws Exception {
        //given
        Integer idx = 1;
        //when
        TestVo testVo = testRepository.selectTest(idx);
        //then
        assertThat(testVo.getIdx()).isEqualTo(1);
    }
}