package com.example.webtodo.app.task;


import com.example.webtodo.entity.Task;
import com.example.webtodo.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;


@Controller
@RequestMapping("/task")
public class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public String task(TaskForm taskForm, Model model) {
        /* タスクの一覧を表示する */

        /* 新規登録か更新かを判断する処理 */
        taskForm.setNewTask(true); // trueであれば新規登録へ遷移

        /* Taskのリストを取得する処理 */
        List<Task> list = taskService.findAll();

        model.addAttribute("list", list);
        model.addAttribute("title", "タスク一覧");

        return "task/index";
    }

    @PostMapping("/insert")
    public String insert(@Valid @ModelAttribute TaskForm taskForm, BindingResult result, Model model) {
        /* タスクデータを一件挿入 */

        /* TaskFormのデータをTaskに格納する処理 */
//        Task task = new Task();
//
//        task.setUserId(1);
//        task.setTypeId(taskForm.getTypeId());
//        task.setTitle(taskForm.getTitle());
//        task.setDetail(taskForm.getDetail());
//        task.setDeadline(taskForm.getDeadline());

        /* TaskFormのデータをTaskに格納する処理 */
        /** makeTaskメソッドを使用する書き方 */
        Task task = makeTask(taskForm, 0);

        if (!result.hasErrors()) {
            /* 一件挿入後リダイレクト */
            taskService.insert(task);

            return "redirect:/task";
        } else {
            taskForm.setNewTask(true);
            model.addAttribute("taskForm", taskForm);
            List<Task> list = taskService.findAll();
            model.addAttribute("list", list);
            model.addAttribute("title", "タスク一覧（バリデーション）");

            return "task/index";
        }
    }

    @GetMapping("/{id}")
    public String showUpdate(TaskForm taskForm, @PathVariable int id, Model model) {
        /* 一件タスクデータを取得し、フォーム内に表示 */

        /* Taskを取得(Optionalでラップ) */
        Optional<Task> taskOpt = taskService.getTask(id);

        /* TaskからTaskFormへ詰め直す */
        Optional<TaskForm> taskFormOpt = taskOpt.map(t -> makeTaskForm(t));

        /* TaskFormがnullでなければ中身を取り出し */
        if (taskFormOpt.isPresent()) {
            taskForm = taskFormOpt.get();
        }

        model.addAttribute("taskForm", taskForm);
        List<Task> list = taskService.findAll();
        model.addAttribute("list", list);
        model.addAttribute("taskId", id);
        model.addAttribute("title", "更新用フォーム");

        return "task/index";
    }

    @PostMapping("/update")
    public String update(
            @Valid @ModelAttribute TaskForm taskForm, BindingResult result,
            @RequestParam("taskId") int taskId,
            Model model,
            RedirectAttributes redirectAttributes) {
        /* タスクidを取得し、一件のデータ更新 */

        /* TaskFormのデータをTaskに格納 */
        Task task = makeTask(taskForm, taskId);

        if (!result.hasErrors()) {
            /* 更新処理、フラッシュスコープの使用、リダイレクト（個々の編集ページ） */
            taskService.update(task);
            redirectAttributes.addFlashAttribute("complete", "変更が完了しました");

            return "redirect:/task/" + taskId;
        } else {
            model.addAttribute("taskForm", taskForm);
            model.addAttribute("title", "タスク一覧");

            return "task/index";
        }
    }

    @PostMapping("/delete")
    public String delete(@RequestParam("/taskId") int id, Model model) {
        /* タスクidを取得し、一件のデータ削除 */

        /* タスクを一件削除しリダイレクト */
        taskService.deleteById(id);

        return "redirect:/task";
    }

    public String duplicate(TaskForm taskForm, int id, Model model) {
        Optional<Task> taskOpt = null;

        Optional<TaskForm> taskFormOpt = taskOpt.map(t -> makeTaskForm(t));

        if (taskFormOpt.isPresent()) {
            taskForm = taskFormOpt.get();
        }

        taskForm.setNewTask(true);

        model.addAttribute("taskForm", taskForm);
        List<Task> list = taskService.findAll();
        model.addAttribute("list", list);
        model.addAttribute("title", "タスク一覧");

        return "task/index";
    }

    public String selectType(TaskForm taskForm, int id, Model model) {

        taskForm.setNewTask(true);

        List<Task> list = null;

        model.addAttribute("list", list);
        model.addAttribute("title", "タスク一覧");

        return "task/index";
    }

    private Task makeTask(TaskForm taskForm, int taskId) {
        /* TaskFormのデータをTaskに入れて返す */
        Task task = new Task();
        if (taskId !=0) {
            task.setId(taskId);
        }
        task.setUserId(1);
        task.setTypeId(taskForm.getTypeId());
        task.setTitle(taskForm.getTitle());
        task.setDetail(taskForm.getDetail());
        task.setDeadline(taskForm.getDeadline());

        return task;
    }

    private TaskForm makeTaskForm(Task task) {
        /* TaskのデータをTaskFormに入れて返す */

        TaskForm taskForm = new TaskForm();

        taskForm.setTypeId(task.getTypeId());
        taskForm.setTitle(task.getTitle());
        taskForm.setDetail(task.getDetail());
        taskForm.setDeadline(task.getDeadline());
        taskForm.setNewTask(false);

        return taskForm;
    }
}
