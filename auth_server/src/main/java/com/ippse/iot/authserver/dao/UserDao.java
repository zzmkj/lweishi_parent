package com.ippse.iot.authserver.dao;

import com.ippse.iot.authserver.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.criterion.*;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@Transactional
public class UserDao extends BaseDao<User> {

    public void save(User instance) {
        log.debug("saving User instance");
        try {
            getSession().save(instance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void update(User instance) {
        log.debug("update User instance");
        try {
            getSession().update(instance);
            log.debug("update successful");
        } catch (RuntimeException re) {
            log.error("update failed", re);
            throw re;
        }
    }

    public void delete(User instance) {
        log.debug("deleting User instance");
        try {
            getSession().delete(instance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public User findById(String id) {
        log.debug("getting User instance with id: " + id);
        try {
            User instance = (User) getSession().get(User.class, id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public User findById(String mid, String id) {
        log.debug("getting User instance with id: " + id);
        try {
            DetachedCriteria dc = DetachedCriteria.forClass(User.class);
            dc.add(Property.forName("id").eq(id));
            List<User> users = findAllByCriteria(getSession(), dc);

            try {
                return users.get(0);
            } catch (Exception e) {
                return null;
            }
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    /**
     * 按用户账户名或身份证号或email或手机号查询。
     * TODO Hibernate会在查询后自动update，原因不明，这里暂时将事务设置为只读。
     *
     * @param username 账户名或身份证号或email或手机号
     */
    @Transactional(readOnly = true)
    public User findByUsername(String username) {
        try {
            DetachedCriteria dc = DetachedCriteria.forClass(User.class);
            Disjunction dis = Restrictions.disjunction();
            dis.add(Property.forName("username").eq(username));
            dis.add(Property.forName("name").eq(username));
            dis.add(Property.forName("code").eq(username));
            dis.add(Property.forName("email").eq(username));
            dis.add(Property.forName("mobile").eq(username));
            dc.add(dis);
            List<User> list = findAllByCriteria(dc);
            try {
                User user = list.get(0);
                return user;
            } catch (Exception e) {
                return null;
            }
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public User merge(User instance) {
        log.debug("merging User instance");
        try {
            User result = (User) getSession().merge(instance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    /**
     * 远程应用查询用户。
     *
     * @param ids
     * @return
     */
    public List<User> findByIds(List<String> ids) {
        try {
            DetachedCriteria dc = DetachedCriteria.forClass(User.class);
            if (null != ids && ids.size() > 0) {
                dc.add(Property.forName("id").in(ids));
                dc.addOrder(Order.asc("username"));
                return this.findAllByCriteria(dc);
            } else
                return new ArrayList<User>();
        } catch (RuntimeException re) {
            log.error("findByIds failed", re);
            throw re;
        }
    }

}
