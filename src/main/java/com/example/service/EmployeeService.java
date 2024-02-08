package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.Employee;
import com.example.repository.EmployeeRepository;

@Service
@Transactional
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    /**
     * 従業員の一覧を取得
     * 
     * @return 全従業員情報のリスト
     */
    public List<Employee> showList() {
        return employeeRepository.findAll();
    }

    /**
     * 従業員IDで従業員情報を取得する
     * @param id 従業員Id
     * @return 従業員情報
     */
    public Employee showDetail(Integer id){
        return employeeRepository.load(id);
    }

    /**
     * 従業員情報を更新する
     * @param employee 更新する従業員情報
     */
    public void update(Employee employee){
        employeeRepository.update(employee);
    }
}
