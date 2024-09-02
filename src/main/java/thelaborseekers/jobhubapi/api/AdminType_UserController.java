package thelaborseekers.jobhubapi.api;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import thelaborseekers.jobhubapi.model.entity.Type_user;
import thelaborseekers.jobhubapi.service.AdminUser_TypeService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/admin/Type_user")
public class AdminType_UserController {
    private final AdminUser_TypeService adminUser_TypeService;

    @GetMapping
    public List<Type_user> list(){return adminUser_TypeService.findAll();}

    @GetMapping("/page")
    public Page<Type_user> paginate(@PageableDefault(size = 5,sort = "name") Pageable pageable){
        return adminUser_TypeService.paginate(pageable);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Type_user create(@RequestBody Type_user type_user){return adminUser_TypeService.create(type_user);}

    @GetMapping("/{id}")
    public Type_user get(@PathVariable Integer id){return adminUser_TypeService.findById(id);}

    @PutMapping("/{id}")
    public Type_user update(@PathVariable Integer id, @RequestBody Type_user typeU){
        return adminUser_TypeService.update(id, typeU);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id){adminUser_TypeService.delete(id);}

}
