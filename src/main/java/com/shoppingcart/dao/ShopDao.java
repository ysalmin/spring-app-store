package com.shoppingcart.dao;

import com.shoppingcart.dao.entity.Application;
import com.shoppingcart.dao.entity.Category;
import com.shoppingcart.dao.entity.UserCredential;
import javassist.tools.rmi.ObjectNotFoundException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by ysalmin on 21.07.2014.
 * DAO Implementation.
 */
@Repository
@Transactional
public class ShopDao {
    @Autowired
    private SessionFactory sessionFactory;

    public List<Application> getAppsByCategory(Category category) throws ObjectNotFoundException {
        Query query = getSession().getNamedQuery("getAppsByCategory");
        query.setString("catName",  category.getName());
        List<Application> result = query.list();
        if(result.isEmpty()) {
            throw new ObjectNotFoundException("Games for category: '" + category.getName() + "' not found");
        }
        return result;
    }

    public List<Category> getAllCategories() throws ObjectNotFoundException {
        Query query = getSession().createQuery("FROM Category");
        List<Category> result = query.list();
        if(result.isEmpty()) {
            throw new ObjectNotFoundException("No categories found");
        }
        return result;
    }

    public List<Application> getMostPopularApps(Integer appsNum) throws ObjectNotFoundException {
        Query query = getSession().getNamedQuery("getMostPopularApps");
        query.setMaxResults(appsNum);
        List<Application> result = query.list();
        if(result.isEmpty()) {
            throw new ObjectNotFoundException("No apps found");
        }
        return result;
    }

    public void save(Application application) {
        getSession().saveOrUpdate(application);
    }

    public Category getCategoryByName(String categoryName) throws ObjectNotFoundException {
        Query query = getSession().getNamedQuery("getCategoryByName");
        query.setString("catName",  categoryName);
        List<Category> result = query.list();
        if(result.isEmpty()) {
            return null;
        }
        return result.get(0);
    }

    public Application getAppByName(String appName) throws ObjectNotFoundException {
        Query query = getSession().getNamedQuery("getAppByName");
        query.setString("appName",  appName);
        List<Application> result = query.list();
        if(result.isEmpty()) {
            return null;
        }
        return result.get(0);
    }

    public UserCredential getUserByName(String userName) throws ObjectNotFoundException {
        Query query = getSession().getNamedQuery("getUserByName");
        query.setString("ucLogin",  userName);
        List<UserCredential> result = query.list();
        if(result.isEmpty()) {
            throw new ObjectNotFoundException("No users with name: '" + userName + "' found");
        }
        return result.get(0);
    }

    public List<Application> getAllApps() throws ObjectNotFoundException {
        Query query = getSession().createQuery("FROM Application");
        List<Application> result = query.list();
        if(result.isEmpty()) {
            throw new ObjectNotFoundException("No apps found");
        }
        return result;
    }

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

}
