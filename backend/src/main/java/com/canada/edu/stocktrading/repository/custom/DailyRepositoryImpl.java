package com.canada.edu.stocktrading.repository.custom;

import com.canada.edu.stocktrading.repository.CustomDailyRepository;
import com.canada.edu.stocktrading.model.Daily;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.sql.Timestamp;
import java.util.List;

@Repository
public class DailyRepositoryImpl implements CustomDailyRepository {
    private EntityManager entityManager;

    @Autowired
    public DailyRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager.getEntityManagerFactory().createEntityManager();
    }

    @Override
    public List<Daily> findDailiesBySymbolIds(Timestamp ts, List<Integer>symbolIds) {
        StringBuilder sql = new StringBuilder();
        if(symbolIds.size()==1){
            sql.append("SELECT * FROM (SELECT * FROM dailies WHERE timestamp <= :ts AND symbol_id = :id ORDER BY timestamp DESC LIMIT 1) AS aliasOne");
            Query q = entityManager.createNativeQuery(sql.toString(),Daily.class);
            q.setParameter("ts", ts);
            q.setParameter("id",symbolIds.get(0));
            return q.getResultList();
        }
        else if(symbolIds.size()>1){

            for(int i = 0; i < symbolIds.size(); i++){
                if(i>0){
                    sql.append(" UNION ");
                }
                sql.append("SELECT * FROM (SELECT * FROM dailies WHERE timestamp <= '");
                sql.append(ts);
                sql.append("' AND symbol_id = ");
                sql.append(symbolIds.get(i));
                sql.append(" ORDER BY timestamp DESC LIMIT 1) AS alias");
                sql.append(i);
            }
            Query q = entityManager.createNativeQuery(sql.toString(),Daily.class);
            return q.getResultList();
        }
        return null;
    }
}
