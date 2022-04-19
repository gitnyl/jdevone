package com.project.jdevone.jdev.controller;

import com.project.jdevone.jdev.entity.ExEntity;
import com.project.jdevone.jdev.entity.QExEntity;
import com.project.jdevone.jdev.repository.ExRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class BaseControllerTest {

    @Autowired
    private ExRepository exRepository;

    //더미데이터 생성
    @Test
    public void insertDummies() {
        IntStream.rangeClosed(1, 100).forEach(i -> {
            ExEntity exEntity = ExEntity
                    .builder()
                    .title("Title..." + i)
                    .content("Content..." + i)
                    .writer("writer..." + i)
                    .build()
                    ;
            exRepository.save(exEntity);
        });
    }

    //데이터 수정 테스트
    @Test
    public void updateUpdtTest() {
        //idx=5인 데이터 선택
        Optional<ExEntity> result = exRepository.findById(5);

        //isPresent() : 존재함(!=null)
        if (result.isPresent()) {
            ExEntity exEntity = result.get();

            exEntity.changeTitle("Change Title...");
            exEntity.changeContent("Change Content...");

            exRepository.save(exEntity);
        }
    }

    //Querydsl 테스트 01 -단일항목 (title에 1이 포함된 항목 출력)
    @Test
    public void querydslTest01() {
        //페이징 처리
        Pageable pageable = PageRequest.of(0, 10, Sort.by("idx").descending());

        // findAll > JpaRepository but Querydsl >> QuerydslPredicateExecutor 사용

        //Predicate
        //Q도메인 클래스 > 엔티티 클래스에 선언된 필드를 변수로 사용 ㅇㅋ
        QExEntity qExEntity = QExEntity.exEntity;

        //쿼리의 where문에 들어가는 조건을 넣어주는 컨테이너
        BooleanBuilder builder = new BooleanBuilder();

        //title like %1%
        BooleanExpression expression = qExEntity.title.contains("1");

        //조건 결합
        builder.and(expression);

        //BooleanBuilder는 repository가 상속 받은 QuerydslPredicateExecutor 인터페이스의 findAll()을 사용할 수 있음
        Page<ExEntity> result = exRepository.findAll(builder, pageable);

        result.stream().forEach(el -> {
            System.out.println(el);
        });
    }

    //Querydsl 테스트 02 -다중항목 (title이나 content에 1이 포함된 항목 출력)
    @Test
    public void querydslTest02() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("idx").descending());
        QExEntity qExEntity = QExEntity.exEntity;
        BooleanBuilder builder = new BooleanBuilder();

        // title like %1% or content like %1%
        BooleanExpression expression = qExEntity.title.contains("1");
        BooleanExpression exAll = expression.or(qExEntity.content.contains("1"));

        builder.and(exAll);
        builder.and(qExEntity.idx.gt(0));

        Page<ExEntity> result = exRepository.findAll(builder, pageable);

        result.stream().forEach(el -> {
            System.out.println(el);
        });

    }




}