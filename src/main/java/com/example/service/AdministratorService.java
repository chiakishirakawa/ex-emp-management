package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.Administrator;
import com.example.repository.AdministratorRepository;

@Service
@Transactional
public class AdministratorService {
    @Autowired
    private AdministratorRepository administratorRepository;
    /**
     * 管理者情報を登録する
     * @param administrator 登録する管理者情報
     */
    public void insert(Administrator administrator){
        administratorRepository.insert(administrator);
    }
    /**
     * ログイン処理を行う
     * @param mailAddress ログインする管理者のメールアドレス
     * @param password ログインする管理者のパスワード
     * @return ログインする管理者情報
     */
    public Administrator login(String mailAddress, String password){
        return administratorRepository.findByMailAddressAndPassword(mailAddress, password);
    }
}
