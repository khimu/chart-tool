package com.icsm.dao.hibernate;

import com.icsm.dao.BaseDaoTestCase;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.persister.entity.EntityPersister;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

public class HibernateConfigurationTest extends BaseDaoTestCase {
    @Autowired
    SessionFactory sessionFactory;

    @Test
    public void testColumnMapping() throws Exception {
        Session session = sessionFactory.openSession();
        try {
            Map metadata = sessionFactory.getAllClassMetadata();
            for (Object o : metadata.values()) {
                EntityPersister persister = (EntityPersister) o;
                String className = persister.getEntityName();
                log.debug("Trying select * from: " + className);
                Query q = session.createQuery("from " + className + " c");
                q.iterate();
                log.debug("ok: " + className);
            }
        } finally {
            session.close();
        }
    }
}
