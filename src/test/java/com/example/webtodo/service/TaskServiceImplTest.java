package com.example.webtodo.service;


import com.example.webtodo.entity.Task;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.Optional;


@SpringJUnitConfig //Junit5上でSpring TestContext Frameworkを利用することを示す
@SpringBootTest //毎回サーバ起動
@ActiveProfiles("unit") //application-unit.ymlのunitを対応（DBの設定を読み込む）
@DisplayName("TaskServiceImplの結合テスト")
class TaskServiceImplTest {

    @Autowired
    public TaskService taskService;

    @Test
    @DisplayName("タスクが取得できない場合のテスト")
    void testGetFormReturnNull() {

        try {
            Optional<Task> task =taskService.getTask(0);
        } catch (TaskNotFoundException e) {
            Assertions.assertEquals(e.getMessage(), "指定されたタスクが存在しません");
        }
    }

    @Test
    @DisplayName("全体検索のテスト")
    void testFindAllCheckCount() {

    }

    @Test
    @DisplayName("1件のタスクが取得できた場合のテスト")
    void testGetTaskFormReturnOne() {

    }
}
