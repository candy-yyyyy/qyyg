package cn.bysj.yty.qyyg.dao;

import cn.bysj.yty.qyyg.domain.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface DepartmentDao extends JpaRepository<Department, Long> {

    @Query(value = "select * from t_department where department_id=?1 and state=0", nativeQuery = true)
    Department getDepartmentById(int departmentId);

    @Modifying(clearAutomatically = true)
    @Query(value = "insert into t_department(department_name,department_desc,department_manager,state) values(?1,?2,?3,0)", nativeQuery = true)
    int addDepartment(String departmentName, String departmentDesc, String departmentManager);

    @Query(value = "select * from t_department where department_name=?1", nativeQuery = true)
    Department getDepartmentByDepartName(String departName);
}
