package fr.afpa.pompey.cda08.demo.com.company.DAO;

import fr.afpa.pompey.cda08.demo.com.company.exception.metier.ExceptionMetier;
import fr.afpa.pompey.cda08.demo.com.company.metier.Client;
import fr.afpa.pompey.cda08.demo.com.company.metier.Contrat;
import fr.afpa.pompey.cda08.demo.com.company.metier.Prospect;

import java.sql.Connection;
import java.util.ArrayList;

public abstract class DAO <T>{
    abstract ArrayList<T> findAll(Connection con)throws DaoSqlEx, ExceptionMetier;
    abstract T find(Connection con, Integer id) throws DaoSqlEx, ExceptionMetier;
    abstract void delete(Connection con, Integer IdClient) throws DaoSqlEx;
    abstract Integer save(Connection con, T t) throws DaoSqlEx;
    abstract ArrayList<Contrat> findByIdClient(Connection con, Integer idCilent)throws DaoSqlEx;
    abstract ArrayList<Client> findAllCilentQuiOntContrat(Connection con) throws DaoSqlEx, ExceptionMetier;
}
