package fr.afpa.pompey.cda08.demo.com.company.DAO;

import fr.afpa.pompey.cda08.demo.com.company.exception.metier.ExceptionMetier;
import fr.afpa.pompey.cda08.demo.com.company.metier.DaoSqlEx;

import java.sql.Connection;
import java.util.ArrayList;

/**
 * abstract class DAO pour obligé la classe dao à appliquer les methode
 * @param <T>
 */
public abstract class DAO <T>{
    abstract ArrayList<T> findAll()throws DaoSqlEx, ExceptionMetier, Exception;
    abstract T find( Integer id) throws DaoSqlEx, ExceptionMetier, Exception;
    abstract void delete(Integer IdClient) throws DaoSqlEx, Exception;
    abstract Integer save(T t) throws DaoSqlEx, Exception;
}
