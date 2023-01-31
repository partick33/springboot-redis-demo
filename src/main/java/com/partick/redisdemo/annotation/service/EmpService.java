package com.partick.redisdemo.annotation.service;

import com.partick.redisdemo.annotation.dao.EmpDAO;
import com.partick.redisdemo.annotation.entity.Emp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * @author partick_peng
 */
@Service
public class EmpService {
    @Autowired
    private EmpDAO empDAO;

    @Cacheable(value = "emp",key = "#empId",condition = "#empId!=1000",cacheManager = "cacheManagerOneHour")
    public Emp findById(Integer empId) {
        return empDAO.findById(empId);
    }

    @Cacheable(value = "emp:rank:salary")
    public List<Emp> getEmpRank() {
        return empDAO.selectByParams();
    }

    @CachePut(value = "emp",key = "#emp.empno")
    public Emp create(Emp emp) {
        return empDAO.insert(emp);
    }

    public Emp update(Emp emp) {
        return empDAO.update(emp);
    }

    @CacheEvict(value = "emp",key = "#empno")
    public void delete(Integer empno) {
        empDAO.delete(empno);
    }

}
