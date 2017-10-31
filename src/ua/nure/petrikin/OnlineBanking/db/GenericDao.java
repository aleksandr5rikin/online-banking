package ua.nure.petrikin.OnlineBanking.db;

import java.io.Serializable;
import java.util.List;

import ua.nure.petrikin.OnlineBanking.db.entity.Entity;
import ua.nure.petrikin.OnlineBanking.exception.DBException;

public interface GenericDao<E extends Entity, PK extends Serializable> {

    public E save(E entity)  throws DBException;

    public E get(PK key) throws DBException;

    public void delete(E object) throws DBException;

    public List<E> getAll() throws DBException;
}

