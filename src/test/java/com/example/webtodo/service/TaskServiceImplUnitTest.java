package com.example.webtodo.service;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.webtodo.entity.Task;
import com.example.webtodo.repository.TaskDao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
@DisplayName("TaskServiceImplの単体テスト")
class TaskServiceImplUnitTest {

    @Mock // モック(stub)クラス ダミーオブジェクト
    private TaskDao dao;

    @InjectMocks // テスト対象クラス モックを探す newする
    private TaskServiceImpl taskServiceimpl;

    @Test // テストケース
    @DisplayName("テーブルtaskの全権取得で0件の場合のテスト")
    /* テスト名 */
    void testFindAllReturnEmptyList() {

        /* 空のリスト */
        List<Task> list = new ArrayList<>();

        /* モッククラスのI/Oをセット（findAll()の型と異なる戻り値はNG） */
        when(dao.findAll()).thenReturn(list);

        /* サービスを実行 */
        List<Task> actualList = taskServiceimpl.findAll();

        /* モックの指定メソッドの実行回数を検査 */
        verify(dao, times(1)).findAll();

        /* 戻り値の検査（expected, actual） */
        Assertions.assertEquals(0, actualList.size());

    }

    @Test // テストケース
    @DisplayName("テーブルTaskの全権取得で2件の場合のテスト")
    /* テスト名 */
    void testFindAllReturnList() {

        /* モックから返すListに2つのTaskオブジェクトをセット */
        List<Task> list = new ArrayList<>();
        Task task1 = new Task();
        Task task2 = new Task();
        list.add(task1);
        list.add(task2);

        /* モッククラスのI/Oをセット（findAll()の型と異なる戻り値はNG） */
        when(dao.findAll()).thenReturn(list);

        /* サービスを実行 */
        List<Task> actualList = taskServiceimpl.findAll();

        /* モックの指定メソッドの実行回数を検査 */
        verify(dao, times(1)).findAll();

        /* 戻り値の検査（expected, actual） */
        Assertions.assertEquals(2, actualList.size());

    }

    @Test
    @DisplayName("タスクが取得できない場合のテスト")

    void testGetTaskThrowException() {

    }

    @Test
    @DisplayName("タスクを1件取得した場合のテスト")

    void testGetTaskReturnOne() {

    }

    @Test
    @DisplayName("削除対象が存在しない場合、例外が発生することを確認するテスト")

    void throwNotFoundException() {

    }
}