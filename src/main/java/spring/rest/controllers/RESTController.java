package spring.rest.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import spring.rest.entity.Employee;
import spring.rest.exceptionHandling.IdNotFoundException;
import spring.rest.service.EmployeeService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class RESTController {

    @Autowired
    public EmployeeService employeeService;

    @GetMapping("/employees")
    public List<Employee> showAllEmployees() {
        List<Employee> allEmployees = employeeService.getAllEmployees();
        return allEmployees;
    }

    @GetMapping("/employees/{id}")
    public Employee getEmployee(@PathVariable int id) {
        Employee employee = employeeService.getEmployee(id);
        if (employee == null) {
            throw new IdNotFoundException("The employee with the ID = " + id + " was not found");
        }
        return employee;
    }

    @PostMapping("/employees")
    public Employee addEmployee(@RequestBody Employee employee) {
        employeeService.saveEmployee(employee);
        return employee;
    }

    @PutMapping("/employees")//
    public Employee updateEmployee(@RequestBody Employee employee) {
        employeeService.saveEmployee(employee);
        return employee;

    }

    @DeleteMapping("/employees/{id}")
    public String deleteEmployee(@PathVariable int id) {
        Employee employee = employeeService.getEmployee(id);
        if (employee == null) {
            throw new IdNotFoundException("The employee with the ID = " + id + " was not found");
        }
        employeeService.deleteEmployee(id);
        return "The employee with the ID = " + id + " was deleted";
    }

}
