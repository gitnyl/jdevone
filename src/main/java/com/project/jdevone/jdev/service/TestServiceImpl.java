package com.project.jdevone.jdev.service;

import com.project.jdevone.jdev.entity.ExEntity;
import com.project.jdevone.jdev.repository.mybatis.TestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestServiceImpl implements TestService {

    @Autowired
    TestRepository testRepository;

    @Override
    public List<ExEntity> getExList() {
        return null;
    }
}
