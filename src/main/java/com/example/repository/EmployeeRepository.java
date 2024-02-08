package com.example.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.domain.Employee;

@Repository
public class EmployeeRepository {

    @Autowired
    private NamedParameterJdbcTemplate template;

    private static final RowMapper<Employee> EMPLOYEE_ROW_MAPPER = (rs, index) -> {
        Employee employee = new Employee();
        employee.setId(rs.getInt("id"));
        employee.setName(rs.getString("name"));
        employee.setImage(rs.getString("image"));
        employee.setGender(rs.getString("gender"));
        employee.setHireDate(rs.getDate("hire_date"));
        employee.setMailAddress(rs.getString("mail_address"));
        employee.setZipCode(rs.getString("zip_code"));
        employee.setAddress(rs.getString("address"));
        employee.setAddress(rs.getString("telephone"));
        employee.setSalary(rs.getInt("salary"));
        employee.setCharacteristics(rs.getString("characteristics"));
        employee.setDependentsCount(rs.getInt("dependents_count"));
        return employee;
    };

    private static final String FIND_ALL_SQL = """
            SELECT
                id
                ,name
                ,image
                ,gender
                ,hire_date
                ,mail_address
                ,zip_code
                ,address
                ,telephone
                ,salary
                ,characteristics
                ,dependents_count
            FROM
                employees
            ORDER BY
                hire_date DESC;
            """;

    private static final String FIND_BY_ID_SQL = """
            SELECT
                id
                ,name
                ,image
                ,gender
                ,hire_date
                ,mail_address
                ,zip_code
                ,address
                ,telephone
                ,salary
                ,characteristics
                ,dependents_count
            FROM
                employees
            WHERE
                id = :id;
            """;
    private static final String UPDATE_SQL = """
            UPDATE employees
            SET
            name= :name
            """;

    /**
     * 全従業員の一覧を入社日の降順で取得
     * 
     * @return 全従業員の一覧
     *         従業員が存在しない場合はサイズ０の従業員一覧
     */
    public List<Employee> findAll() {
        List<Employee> employeeList = template.query(FIND_ALL_SQL, EMPLOYEE_ROW_MAPPER);
        return employeeList;
    }

    /**
     * 主キーから従業員情報を取得
     * 
     * @param id 主キー
     * @return 取得した従業員情報
     *         見つからない場合はnull
     */
    public Employee load(Integer id) {
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource().addValue("id", id);
        Employee employee = null;
        try {
            employee = template.queryForObject(FIND_BY_ID_SQL, sqlParameterSource, EMPLOYEE_ROW_MAPPER);
        } catch (Exception e) {
            System.out.println("データを取得できませんでした");
        }
        return employee;

    }

    /**
     * 従業員情報を変更する
     * 
     * @param employee 更新する従業員情報
     */
    public void update(Employee employee) {
        StringBuilder sql = new StringBuilder();
        sql.append(UPDATE_SQL);
        if (employee.getName() == null) {
            sql.append("name = :name");
        }
        if (employee.getImage() == null) {
            sql.append("image = :image");
        }
        if (employee.getGender() == null) {
            sql.append("gender = :gender");
        }
        if (employee.getMailAddress() == null) {
            sql.append("mail_address = :mailAddress");
        }
        if (employee.getZipCode() == null) {
            sql.append("zip_code = :zipCode");
        }
        if (employee.getAddress() == null) {
            sql.append("address = :address");
        }
        if (employee.getTelephone() == null) {
            sql.append("telephone = :telephone");
        }
        if (employee.getSalary() == null) {
            sql.append("salary = :salary");
        }
        if (employee.getCharacteristics() == null) {
            sql.append("characteristics = :characteristics");
        }
        if (employee.getDependentsCount() == null) {
            sql.append("dependents_count = :dependentsCount");
        }
        sql.append("WHERE id = :id;");
        SqlParameterSource sqlParameterSource = new BeanPropertySqlParameterSource(employee);
        template.update(sql.toString(), sqlParameterSource);
    }
}
