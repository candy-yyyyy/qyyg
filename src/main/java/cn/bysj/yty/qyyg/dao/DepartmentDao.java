package cn.bysj.yty.qyyg.dao;

import cn.bysj.yty.qyyg.domain.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DepartmentDao extends JpaRepository<Department, Long> {

    @Query(value = "select * from t_department where department_id=?1 and state=0", nativeQuery = true)
    public Department getDepartmentById(int departmentId);
}
