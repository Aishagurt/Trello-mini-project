package Trello.com.controllers;

import Trello.com.entities.Folders;
import Trello.com.entities.TaskCategories;
import Trello.com.entities.Tasks;
import Trello.com.repository.CategoryRepository;
import Trello.com.repository.CommentRepository;
import Trello.com.repository.FolderRepository;
import Trello.com.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.config.Task;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Controller
public class HomeController {
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private FolderRepository folderRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping(value="/")
    public String IndexPage(Model model){
        List<Folders> folders = folderRepository.findAll();
        model.addAttribute("folders", folders);
        return "home";
    }
    @PostMapping(value = "/addfolder")
    public String AddFolder(@RequestParam(name="folder") String name){
        Folders folder = new Folders();
        folder.setName(name);
        folderRepository.save(folder);
        return "redirect:/";
    }
    @GetMapping(value = "/details/{id}")
    public String FolderDetails(Model model, @PathVariable(name = "id") Long id){
        Folders folder = folderRepository.findById(id)
                .orElse(null);
        List<Tasks> alltasks = taskRepository.findAll();
        List<Tasks> tasks = new ArrayList<>();
        for (Tasks t : alltasks) {
            if (t.getFolder() != null && t.getFolder().equals(folder)) {
                tasks.add(t);
            }
        }
        if(folder!=null){
            List<TaskCategories> categories = folder.getCategories();
            model.addAttribute("categories", categories);
        }

        model.addAttribute("folder", folder);
        model.addAttribute("tasks", tasks);
        return "details";
    }
    @PostMapping(value="/addTask")
    public String AddTask(@RequestParam(name = "folderId") Long id,
                          @RequestParam(name = "title") String title,
                          @RequestParam(name = "description") String description){
        Tasks task = new Tasks();
        Folders folders = folderRepository.findById(id)
                .orElse(null);;
        task.setFolder(folders);
        task.setTitle(title);
        task.setDescription(description);
        taskRepository.save(task);
        return "redirect:/details/"+id;
    }
    @PostMapping(value="/updateTask")
    public String ViewTask(@RequestParam(name="folderId") Long folderId,
                           @RequestParam(name="taskId") Long taskId,
                           @RequestParam(name="title") String title,
                           @RequestParam(name="description") String description,
                           @RequestParam(name="status") int status){
        Tasks task = taskRepository.findById(taskId).orElse(null);
        if(task!=null){
            task.setTitle(title);
            task.setDescription(description);
            task.setStatus(status);
            taskRepository.save(task);
        }
        return "redirect:/details/"+folderId;
    }
    @PostMapping(value = "/addCategory")
    public String AddCategory(@RequestParam(name="folderId") Long folderId,
                              @RequestParam(name="CategoryName") String name){
        TaskCategories category = new TaskCategories();
        category.setName(name);
        categoryRepository.save(category);
        Folders folder = folderRepository.findById(folderId)
                .orElse(null);
        if(folder!=null){
            List<TaskCategories> categories = folder.getCategories();
            categories.add(category);
            folder.setCategories(categories);
            folderRepository.save(folder);
        }
        return "redirect:/details/"+folderId;
    }
    @PostMapping("/deleteCategory")
    public String deleteCategory(@RequestParam("categoryId") Long categoryId,
                                 @RequestParam("folderId") Long folderId) {
        TaskCategories category = categoryRepository.findById(categoryId).orElse(null);
        Folders folder = folderRepository.findById(folderId)
                .orElse(null);
        if(folder!=null){
            List<TaskCategories> categories = folder.getCategories();
            categories.remove(category);
            folder.setCategories(categories);
            folderRepository.save(folder);
        }
        categoryRepository.delete(category);
        return "redirect:/details/"+folderId;

    }

    @PostMapping("/deleteTask")
    public String DeleteTask(@RequestParam("taskId") Long taskId,
                             @RequestParam("folderId") Long folderId){
        taskRepository.findById(taskId).ifPresent(task -> taskRepository.delete(task));
        return "redirect:/details/"+folderId;
    }
    @PostMapping("/deleteFolder")
    public String DeleteFolder(@RequestParam("folderId") Long folderId){
        List<Tasks> alltasks = taskRepository.findAll();
        Folders folder = folderRepository.findById(folderId).orElse(null);
        for(Tasks t : alltasks){
            if(t.getFolder() == folder){
                taskRepository.delete(t);
            }
        }
        if(folder!=null)
            folderRepository.delete(folder);
        return "redirect:/";
    }
}
