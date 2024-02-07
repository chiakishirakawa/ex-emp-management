package com.example.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.domain.Administrator;

@Repository
public class AdministratorRepository {
    @Autowired
    private NamedParameterJdbcTemplate template;

    private static final RowMapper<Administrator> ADMINISTRATOR_ROW_MAPPER= (rs,index)->{
        Administrator administrator = new Administrator();
        administrator.setId(rs.getInt("id"));
        administrator.setName(rs.getString("name"));
        administrator.setMailAddress(rs.getString("mailAddress"));
        administrator.setPassword(rs.getString("password"));
        return administrator;
    };

    private static final String INSERT_SQL="""
        INSERT INTO administrators(
            name
            ,mail_address
            ,password
        ) VALUES (
            :name
            ,:mailAddress
            ,:password
        );
        """;

    private static final String FIND_BY_MAIL_ADDRESS_AND_PASSWORD = """
        SELECT 
            id
            ,name
            ,mail_address
            ,password 
        FROM administrators 
        WHERE 
            mail_address = :mailAddress 
        and 
            password = :password;
        """; 
    /**
     * 管理者情報を挿入する。
     * @param administrator 追加する管理者情報
     */
    public void insert(Administrator administrator){
        SqlParameterSource sqlParameterSource = new BeanPropertySqlParameterSource(administrator);
        template.update(INSERT_SQL, sqlParameterSource);
    }
    /**
     * メールアドレス、パスワードから管理者情報を取得する
     * @param mailAddress
     * @param password
     * @return 取得した管理者情報
     *         存在しない場合はnull
     */
    public Administrator findByMailAddressAndPassword(String mailAddress,String password){
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
        .addValue("mailAddress", mailAddress).addValue("password", password);
        Administrator administrator = null;
        try {
            administrator =  template.queryForObject(FIND_BY_MAIL_ADDRESS_AND_PASSWORD, sqlParameterSource,ADMINISTRATOR_ROW_MAPPER);
        } catch (Exception e) {
            System.out.println("データを取得できませんでした。");
        }
        return administrator;
    }


}
