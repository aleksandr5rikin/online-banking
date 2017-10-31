package ua.nure.petrikin.OnlineBanking.db;

import ua.nure.petrikin.OnlineBanking.exception.DBException;

public interface DaoFactory {
    public <T extends GenericDao<?, ?>> T getDao(Class<T> clazz) throws DBException;
}
