package thelaborseekers.jobhubapi.api;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import thelaborseekers.jobhubapi.model.entity.User;
import thelaborseekers.jobhubapi.service.AdminUserService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/admin/Users")
public class AdminUserController {
    private final AdminUserService adminUserService;

    @GetMapping()
    public List<User> list(){return adminUserService.findAll();}


    @GetMapping("/page")
    public Page<User> paginate(@PageableDefault(size = 5,sort = "email") Pageable pageable) {
        return adminUserService.paginate(pageable);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public User create(@RequestBody User userForm) {return adminUserService.create(userForm);}

    @GetMapping("/{id}")
    public User get(@PathVariable Integer id) {return adminUserService.findById(id);}

    @PutMapping("/{id}")
    public User update(@PathVariable Integer id, @RequestBody User userForm) {
        return adminUserService.update(id, userForm);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {adminUserService.delete(id);}

}
