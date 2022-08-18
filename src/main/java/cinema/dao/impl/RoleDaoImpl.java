package cinema.dao.impl;

import cinema.dao.AbstractDao;
import cinema.dao.RoleDao;
import cinema.exception.DataProcessingException;
import cinema.model.Role;
import java.util.Optional;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class RoleDaoImpl extends AbstractDao<Role> implements RoleDao {
    public RoleDaoImpl(SessionFactory factory) {
        super(factory, Role.class);
    }

    @Override
    public Optional<Role> getByName(String roleName) {
        try (Session session = factory.openSession()) {
            Role.RoleName role = Role.RoleName.valueOf(roleName);
            Query<Role> getByNameQuery = session
                    .createQuery("FROM Role WHERE roleName = :role", Role.class);
            getByNameQuery.setParameter("role", role);
            return getByNameQuery.uniqueResultOptional();
        } catch (Exception e) {
            throw new DataProcessingException("Not found role with name " + roleName, e);
        }
    }
}
