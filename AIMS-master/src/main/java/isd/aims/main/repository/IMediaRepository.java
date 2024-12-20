package isd.aims.main.repository;

import java.sql.SQLException;
import java.util.List;

public interface IMediaRepository<T> {
    T getById(int id) throws SQLException;
    List<T> getAll() throws SQLException;
}